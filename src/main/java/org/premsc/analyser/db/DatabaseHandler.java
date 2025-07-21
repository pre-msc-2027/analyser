package org.premsc.analyser.db;

import java.sql.*;

/**
 * DatabaseHandler is responsible for managing the connection to the SQLite database.
 */
public class DatabaseHandler implements AutoCloseable {

    private static final String URL = "jdbc:sqlite:index.db";

    private final IndexModel INDEX_MODEL = new IndexModel(this);

    private Connection connection;

    /**
     * Returns the connection to the SQLite database.
     * @return the connection to the database
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Returns the IndexModel instance associated with this DatabaseHandler.
     * @return the IndexModel instance
     */
    public IndexModel getIndexModel() {
        return this.INDEX_MODEL;
    }

    /**
     * Initializes the database connection and sets up the necessary tables.
     */
    public void init() {

        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setup();

    }

    /**
     * Sets up the database by creating the necessary tables and indexes.
     * This method is called during initialization.
     */
    private void setup() {

        try (Statement stmt = connection.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS index_table
                    (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                    language TEXT,
                    source TEXT,
                    type TEXT,
                    value TEXT,
                    startByte INTEGER,
                    endByte INTEGER);
                    CREATE UNIQUE INDEX idx ON index_table(id);
                    CREATE INDEX idx1 ON index_table(language, type, value);
                    CREATE INDEX idx2 ON index_table(source, type, value);
                    CREATE INDEX idx3 ON index_table(language, source, type, value);
                    """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the database connection.
     * This method should be called when the database is no longer needed.
     */
    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
