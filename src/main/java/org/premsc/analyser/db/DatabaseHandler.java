package org.premsc.analyser.db;

import java.sql.*;

public class DatabaseHandler implements AutoCloseable {

    private static final String URL = "jdbc:sqlite:index.db";

    private final IndexModel INDEX_MODEL = new IndexModel(this);

    private Connection connection;

    public DatabaseHandler() {}

    public Connection getConnection() {
        return connection;
    }

    public IndexModel getIndexModel() {
        return this.INDEX_MODEL;
    }

    public void init() {

        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setup();

    }

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

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.init();

        try {

            try (PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement("INSERT INTO index_table VALUES (0, 'HTML', 'public/index.html', 'class', 'test_class', 52, 56);")) {
                //preparedStatement.executeUpdate();
            }

            ResultSet result;

            try (Statement stmt = databaseHandler.getConnection().createStatement()) {
                result = stmt.executeQuery("SELECT * FROM index_table");

                while (result.next()) {
                    System.out.println(result.getInt("id") + " - " + result.getString("language"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        databaseHandler.close();


    }
}
