package org.premsc.analyser;

import com.google.gson.JsonObject;
import org.premsc.analyser.api.Api;
import org.premsc.analyser.config.Config;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.indexer.IIndexer;
import org.premsc.analyser.indexer.IndexerManager;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.repository.ISource;
import org.premsc.analyser.repository.Repository;
import org.premsc.analyser.rules.*;

import java.util.List;
import java.util.Objects;

/**
 * AnalyserApplication is the main class for the application that performs analysis on code repositories.
 * It initializes the application, runs indexing, and performs analysis based on rules defined in the ruleset.
 */
public class AnalyserApplication {

    private static final boolean DEBUG = true;

    private final String id;
    private Config config;

    private final Api api = new Api(this);
    private final Repository repository = new Repository(this);
    private final Ruleset ruleset = new Ruleset(this);
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private final IndexerManager indexerManager = new IndexerManager();
    private final Analysis analysis = new Analysis();

    /**
     * Constructor for AnalyserApplication.
     *
     * @param id the unique identifier for the application instance
     */
    private AnalyserApplication(String id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier of the application instance.
     *
     * @return the unique identifier
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the API instance used for communication.
     *
     * @return the API instance
     */
    public Api getApi() {
        return this.api;
    }

    /**
     * Returns the configuration used by the application.
     *
     * @return the configuration instance
     */
    public Config getConfig() {
        return this.config;
    }

    /**
     * Returns the repository instance used for managing sources.
     *
     * @return the repository instance
     */
    public Repository getRepository() {
        return this.repository;
    }

    /**
     * Returns the ruleset used for analysis.
     *
     * @return the ruleset instance
     */
    public DatabaseHandler getDbHandler() {
        return this.dbHandler;
    }

    /**
     * Starts the application by initializing the configuration, repository, ruleset, and database handler,
     */
    private void start() {

        System.loadLibrary("tree-sitter");

        this.log("Starting AnalyserApplication with ID: " + this.id);

        try {
            this.init();
        } catch (Exception e) {
            this.logError(e);
			System.exit(1);
        }

        this.log("Indexing sources.");
        try {
            this.runIndexing();
        } catch (Exception e) {
            this.logError(e);
            System.exit(1);
        }

        this.log("Analysing sources.");
        try {
            this.runAnalysis();
        } catch (Exception e) {
            this.logError(e);
            System.exit(1);
        }

        this.log("Posting results.");
        this.api.post("", this.analysis.getOutput());
        if (Objects.equals(this.getId(), "0")) System.out.println(this.analysis.getOutput());

        this.log("Done.");

    }

    /**
     * Initializes the application by loading configuration, repository, ruleset, and database handler.
     */
    private void init() throws Exception {

        this.log("Fetching configuration.");
        this.config = Config.fromJson(this.api.get("scans/options"));

        this.log("Cloning repository.");
        this.repository.init();

        this.log("Initializing database.");
        this.dbHandler.init();

        this.log("Fetching rules.");
        this.ruleset.init();
    }

    /**
     * Runs the indexing process for all sources in the repository.
     */
    private void runIndexing() throws Exception {

        for (ISource source : this.getRepository().list()) {

            try (ITreeHelper treeHelper = source.parse()) {

                for (IIndexer indexer : this.indexerManager.getIndexers()) {

                    if (!indexer.getLanguage().equals(source.getLanguage())) continue;

                    List<IndexerManager.Index> indexes = indexer.index(treeHelper);

                    for (IndexerManager.Index index : indexes) {

                        this.getDbHandler().getIndexModel().insert(
                                treeHelper.getSource(),
                                indexer.getType(),
                                index
                        );
                    }

                }

            }
        }
    }

    /**
     * Runs the analysis process based on the rules defined in the ruleset.
     */
    private void runAnalysis() throws Exception {

        for (ISource source : this.getRepository().list()) {

            this.analysis.total_files += 1;

            try (ITreeHelper treeHelper = source.parse()) {

                boolean found = false;

                for (IRule rule : this.ruleset.getRules()) {

                    if (!rule.getLanguage().equals(source.getLanguage())) continue;

                    List<Warning> results = switch (rule) {
                        case IQueryRule queryRule -> queryRule.test(treeHelper).toList();

                        case IIndexRule indexRule -> indexRule.test(this.dbHandler, source);

                        default -> throw new IllegalStateException("Unexpected value: " + rule);
                    };

                    if (!results.isEmpty()) found = true;

                    this.analysis.warnings_found += results.size();

                    this.analysis.warnings.addAll(results);

                }

                if (found) this.analysis.files_with_warnings += 1;

            }

        }

    }

    /**
     * Logs a message with a timestamp and posts it to the API.
     *
     * @param message the message to log
     */
    private void log(String message) {

        String datetime = java.time.LocalDateTime.now().toString();
        long timestamp = System.currentTimeMillis();

        System.out.printf("[%s] %s%n", datetime, message);

        JsonObject log = new JsonObject();
        log.addProperty("timestamp", timestamp);
        log.addProperty("message", message);

        this.api.post("logs", log);
    }

    private void logError(Exception error) {

        String datetime = java.time.LocalDateTime.now().toString();
        long timestamp = System.currentTimeMillis();

        System.err.printf("[%s] ERROR: (%s) %s%n", datetime, error.getClass().getSimpleName(), error.getMessage());

        JsonObject log = new JsonObject();
        log.addProperty("timestamp", timestamp);
        log.addProperty("message", error.getMessage());
        log.addProperty("error", error.getClass().getSimpleName());

        this.api.post("logs", log);

        if (AnalyserApplication.DEBUG) throw new RuntimeException(error);
    }

    /**
     * Main method to start the AnalyserApplication.
     *
     * @param args command line arguments, where the first argument is the unique identifier for the application instance
     */
    public static void main(String[] args) {

        String id = args[0];

        AnalyserApplication app = new AnalyserApplication(id);

        app.start();

    }
}
