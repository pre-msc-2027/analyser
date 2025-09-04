package org.premsc.analyser.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void init() throws SQLException {

        connection = DriverManager.getConnection(URL);

        this.setup();

    }

    /**
     * Sets up the database by creating the necessary tables and indexes.
     * This method is called during initialization.
     */
    private void setup() throws SQLException {

        try (Statement stmt = connection.createStatement()) {

            stmt.execute("DROP TABLE index_table;");

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS index_table
                    (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                    language TEXT,
                    source TEXT,
                    type TEXT,
                    value TEXT,
                    line INTEGER,
                    startByte INTEGER,
                    endByte INTEGER);
                    CREATE UNIQUE INDEX idx ON index_table(id);
                    CREATE INDEX idx1 ON index_table(language, type, value);
                    CREATE INDEX idx2 ON index_table(source, type, value);
                    CREATE INDEX idx3 ON index_table(language, source, type, value);
                    """);

        }


    }

    /**
     * Closes the database connection.
     * This method should be called when the database is no longer needed.
     */
    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

}
