package org.premsc.analyser;

import org.premsc.analyser.api.Api;
import org.premsc.analyser.api.HttpResponseError;
import org.premsc.analyser.config.Config;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.indexer.IIndexer;
import org.premsc.analyser.indexer.IndexerManager;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.repository.ISource;
import org.premsc.analyser.repository.Repository;
import org.premsc.analyser.rules.IRule;
import org.premsc.analyser.rules.Ruleset;
import org.premsc.analyser.rules.Warning;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * AnalyserApplication is the main class for the application that performs analysis on code repositories.
 * It initializes the application, runs indexing, and performs analysis based on rules defined in the ruleset.
 */
public class AnalyserApplication {

    private static final boolean DEBUG = false;

    private final String id;
    private final String token;
    private Config config;

    private final Api api = new Api(this);
    private final Repository repository = new Repository(this);
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private final IndexerManager indexerManager = new IndexerManager();
    private final Ruleset ruleset = new Ruleset(this);
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
        this.api.post("scans/analyse", this.analysis);
        if (this.id.equals("0")) System.out.println(this.analysis);

        Optional<String> aiDirectoryPath = Optional.ofNullable(System.getenv("AI_DIRECTORY_PATH"));
        if (aiDirectoryPath.isPresent()) {
            this.log("Running AI analysis.");
            this.runAiAnalysis(this.id, aiDirectoryPath.get());
        }

        this.log("Cleaning folder.");
        try {
            if (!this.id.equals("0")) Utils.DeleteFolder(this.getRepository().getPath());
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
    private void runIndexing() {

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
                this.log(e);
            }
        }
    }

    /**
     * Runs the analysis process based on the rules defined in the ruleset.
     */
    private void runAnalysis() {

        for (ISource source : this.getRepository().list()) {

            this.analysis.summary.totalFiles += 1;

            try (ITreeHelper treeHelper = source.parse()) {

                boolean found = false;

                for (IRule rule : this.ruleset.getRules()) {

                    if (!rule.getLanguage().equals(source.getLanguage())) continue;

                    List<Warning> results = rule.getExpression().execute(dbHandler, treeHelper);

                    if (!results.isEmpty()) found = true;

                    this.analysis.summary.warningsFound += results.size();

                    this.analysis.warnings.addAll(results);

                }

                if (found) this.analysis.summary.filesWithWarnings += 1;

            } catch (MalformedInputException _) {
                this.log("Skipping file with invalid encoding: " + source.getFilepath());
            } catch (Exception e) {
                this.log(e);
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
        this.api.post("scans/logs", log);
    }

    /**
     * Logs an exception with its message and type, then posts it to the API.
     *
     * @param error the exception to log
     */
    public void log(Exception error) {
        Log log = new Log(error);
        if (!(error instanceof HttpResponseError)) {
            this.log(log);
            if (AnalyserApplication.DEBUG) throw new RuntimeException(error);
        }
    }

    /**
     * Logs a Log object by printing it to the console and posting it to the API.
     *
     * @param log the Log object to log
     */
    private void log(Log log) {
        System.out.println(log);
        try {
            this.api.post("scans/logs", log);
        } catch (Exception ex) {
            System.out.println(ex);
        }
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

    void runAiAnalysis(String scanId, String aiDirectoryPath) {

        ProcessBuilder pb = new ProcessBuilder(
                "python", "-m", "src.main", "--scan-id", scanId
        );
        pb.directory(new java.io.File(aiDirectoryPath));
        pb.redirectErrorStream(true);

        CompletableFuture.runAsync(() -> {
            try {
                Process process = pb.start();
                int exitCode = process.waitFor();
                log("Python exited with code: " + exitCode);

            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                log(e);
            }
        });
    }

}
