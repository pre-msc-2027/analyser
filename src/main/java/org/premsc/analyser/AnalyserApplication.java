package org.premsc.analyser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.premsc.analyser.api.Api;
import org.premsc.analyser.api.HttpResponseError;
import org.premsc.analyser.config.Config;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.indexer.IIndexer;
import org.premsc.analyser.indexer.IndexerManager;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.repository.ISource;
import org.premsc.analyser.repository.Repository;
import org.premsc.analyser.rules.*;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;
import java.util.Objects;

/**
 * AnalyserApplication is the main class for the application that performs analysis on code repositories.
 * It initializes the application, runs indexing, and performs analysis based on rules defined in the ruleset.
 */
public class AnalyserApplication {

    private static final boolean DEBUG = true;

    private final String id;
    private final String token;
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
     * @param id the unique identifier for the scan
     * @param token the user token for authentication
     */
    private AnalyserApplication(String id, String token) {
        this.id = id;
        this.token = token;
    }

    /**
     * Returns the unique identifier of the scan.
     *
     * @return the unique identifier
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the user token for authentication.
     *
     * @return the user token
     */
    public String getToken() {
        return this.token;
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

        this.log("Starting AnalyserApplication with ID: " + this.id);

        NativeLib.openGrammar("");

        try {
            this.init();
        } catch (Exception e) {
            this.log(e);
			System.exit(1);
        }

        this.log("Indexing sources.");
        try {
            this.runIndexing();
        } catch (Exception e) {
            this.log(e);
            System.exit(1);
        }

        this.log("Analysing sources.");
        try {
            this.runAnalysis();
        } catch (Exception e) {
            this.log(e);
            System.exit(1);
        }

        this.log("Posting results.");
        this.api.post("", this.analysis);
        if (Objects.equals(this.getId(), "0")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(this.analysis.getModule());
            try {
                System.out.println(mapper.writeValueAsString(this.analysis));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        this.log("Cleaning folder.");
        try {
            Utils.DeleteFolder(this.getRepository().getPath());
        } catch (IOException e) {
            this.log(e);
        }

        this.log("Done.");

    }

    /**
     * Initializes the application by loading configuration, repository, ruleset, and database handler.
     */
    private void init() throws Exception {

        this.log("Fetching configuration.");
        this.config = this.api.get("scans/options", Config.class);

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

            } catch (MalformedInputException _) {
                this.log("Skipping file with invalid encoding: " + source.getFilepath());
            } catch (Exception e) {
                this.logError(e);
            }
        }
    }

    /**
     * Runs the analysis process based on the rules defined in the ruleset.
     */
    private void runAnalysis() throws Exception {

        for (ISource source : this.getRepository().list()) {

            this.analysis.totalFiles += 1;

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

                    this.analysis.warningsFound += results.size();

                    this.analysis.warnings.addAll(results);

                }

                if (found) this.analysis.filesWithWarnings += 1;

            } catch (MalformedInputException _) {
                this.log("Skipping file with invalid encoding: " + source.getFilepath());
            } catch (Exception e) {
                this.logError(e);
            }

        }

    }

    /**
     * Logs a message with a timestamp and posts it to the API.
     *
     * @param message the message to log
     */
    private void log(String message) {
        Log log = new Log(message);
        this.log(log);
        this.api.post("logs", log);
    }

    /**
     * Logs an exception with its message and type, then posts it to the API.
     *
     * @param error the exception to log
     */
    private void log(Exception error) {
        Log log = new Log(error);
        this.log(log);
        if (AnalyserApplication.DEBUG) throw new RuntimeException(error);
    }

    /**
     * Logs a Log object by printing it to the console and posting it to the API.
     *
     * @param log the Log object to log
     */
    private void log(Log log) {
        System.out.println(log);
        this.api.post("logs", log);
    }

    /**
     * Main method to start the AnalyserApplication.
     *
     * @param args command line arguments, where the first argument is the unique identifier for the application instance
     */
    public static void main(String[] args) {

        String id = args[0];
        String token = args[1];

        AnalyserApplication app = new AnalyserApplication(id, token);

        app.start();

    }
}
