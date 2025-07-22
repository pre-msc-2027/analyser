package org.premsc.analyser.db;

import org.premsc.analyser.db.selector.Predicate;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.indexer.IndexerManager;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.repository.ISource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IndexModel is responsible for managing index entries in the database.
 */
public class IndexModel {

    private final DatabaseHandler dbHandler;

    /**
     * Constructor for IndexModel.
     *
     * @param dbHandler The DatabaseHandler instance to interact with the database.
     */
    public IndexModel(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    /**
     * Returns the DatabaseHandler associated with this IndexModel.
     *
     * @return The DatabaseHandler instance.
     */
    public DatabaseHandler getHandler() {
        return this.dbHandler;
    }

    /**
     * Queries the database for a single index entry based on the provided SQL query.
     *
     * @param query The SQL query to execute.
     * @return An Optional containing the Index if found, or empty if not found.
     * @throws SQLException If there is an error executing the SQL query.
     */
    private Optional<Index> queryOne(String query) throws SQLException {
        try (Statement statement = this.dbHandler.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) return Optional.empty();
            return Optional.of(new Index(this, result));
        }
    }

    /**
     * Queries the database for multiple index entries based on the provided SQL query.
     *
     * @param query The SQL query to execute.
     * @return An array of Index objects containing the results.
     * @throws SQLException If there is an error executing the SQL query.
     */
    private Index[] queryMultiple(String query) throws SQLException {

        List<Index> results = new ArrayList<>();

        try (Statement statement = this.dbHandler.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                results.add(new Index(this, result));
            }
        }

        return results.toArray(Index[]::new);
    }

    /**
     * Queries the database for multiple index entries based on the provided SQL query.
     *
     * @param selector The Selector object containing the SQL query to execute.
     * @return An array of Index objects containing the results.
     * @throws SQLException If there is an error executing the SQL query.
     */
    public Index[] queryMultiple(Selector<?> selector) throws SQLException {
        return this.queryMultiple(selector.build());
    }

    /**
     * Retrieves an Index by its ID.
     *
     * @param id The ID of the index to retrieve.
     * @return The Index object corresponding to the given ID.
     * @throws SQLException If the index with the specified ID is not found or if there is an error executing the query.
     */
    public Index getWithId(int id) throws SQLException {

        Selector<?> selector = Selector.of("index_table")
                .setPredicate(Predicate.equal("id", id));

        Optional<Index> result = this.queryOne(selector.build());
        if (result.isEmpty()) throw new SQLException("Index with id " + id + " not found.");
        return result.get();
    }

    /**
     * Retrieves an Index based on the specified language, source, type, and value.
     *
     * @param source The source of the index.
     * @param type   The type of the index.
     * @return An array of Index objects that match the specified criteria.
     * @throws SQLException If there is an error executing the SQL query or if no index is found.
     */
    public Index[] getTypeInSource(ISource source, String type) throws SQLException {

        Selector<?> selector = Selector
                .of("index_table")
                .setPredicate(
                        Predicate
                                .equal("source", source.getFilepath())
                                .and(Predicate.equal("type", type)));

        return this.queryMultiple(selector);
    }

    /**
     * Retrieves an Index based on the specified language, type, and value.
     *
     * @param language The language of the index.
     * @param type     The type of the index.
     * @param value    The value of the index.
     * @return An array of Index objects that match the specified criteria.
     * @throws SQLException If there is an error executing the SQL query or if no index is found.
     */
    public Index[] getAllValues(LanguageEnum language, String type, String value) throws SQLException {

        Selector<?> selector = Selector
                .of("index_table")
                .setPredicate(
                        Predicate
                                .equal("language", language.name())
                                .and(Predicate.equal("type", type))
                                .and(Predicate.equal("value", value)));

        return this.queryMultiple(selector);
    }

    /**
     * Retrieves an Index based on the specified source, type, and value.
     *
     * @param source the source of the index.
     * @param type   the type of the index.
     * @param value  the value of the index.
     * @return an array of Index objects that match the specified criteria.
     * @throws SQLException if there is an error executing the SQL query or if no index is found.
     */
    public Index[] getAllValuesInSource(ISource source, String type, String value) throws SQLException {

        Selector<?> selector = Selector
                .of("index_table")
                .setPredicate(
                        Predicate
                                .equal("source", source.getFilepath())
                                .and(Predicate.equal("type", type))
                                .and(Predicate.equal("value", value)));

        return this.queryMultiple(selector);
    }

    /**
     * Inserts a new index entry into the database.
     * @param source the source of the index entry.
     * @param type the type of the index entry (e.g., class, method).
     * @param value the value of the index entry (e.g., class name, method name).
     * @param line the line number in the source file where the index entry is located.
     * @param startByte the starting position of the index entry in the source file.
     * @param endByte the ending position of the index entry in the source file.
     * @throws Exception if there is an error executing the SQL query or if the source language is unsupported.
     */
    public void insert(ISource source, String type, String value, int line, int startByte, int endByte) throws Exception {
        try (Statement statement = this.dbHandler.getConnection().createStatement()) {
            statement.executeUpdate(
                    """
                            INSERT INTO index_table (language, source, type, value, line, startByte, endByte)
                            VALUES ('%s', '%s', '%s', '%s', %d, %d, %d)
                            """.formatted(source.getLanguage(), source.getFilepath(), type, value, line, startByte, endByte)
            );
        }
    }

    /**
     * Inserts a new index entry into the database using an IndexerManager.Index object.
     * @param source the source of the index entry.
     * @param type the type of the index entry (e.g., class, method).
     * @param index the IndexerManager.Index object containing the index entry details.
     * @throws Exception if there is an error executing the SQL query or if the source language is unsupported.
     */
    public void insert(ISource source, String type, IndexerManager.Index index) throws Exception {
        this.insert(source, type, index.value(), index.line(), index.startByte(), index.endByte());
    }

    /**
     * Creates an Index object from a ResultSet.
     *
     * @param result The ResultSet containing the index data.
     * @return An Index object populated with the data from the ResultSet.
     * @throws SQLException If there is an error retrieving data from the ResultSet.
     */
    public Index fromResult(ResultSet result) throws SQLException {
        return new Index(this, result);
    }

    /**
     * Represents an index entry in the database.
     *
     * @param model     the IndexModel instance managing this index.
     * @param id        the unique identifier for the index entry.
     * @param language  the language of the index entry.
     * @param source    the source file of the index entry.
     * @param type      the type of the index entry (e.g., class, method).
     * @param value     the value of the index entry (e.g., class name, method name).
     * @param line      the line number in the source file where the index entry is located.
     * @param startByte the starting position of the index entry in the source file.
     * @param endByte   the ending position of the index entry in the source file.
     */
    public record Index(
            IndexModel model,
            int id,
            LanguageEnum language,
            String source,
            String type,
            String value,
            int line,
            int startByte,
            int endByte
    ) {

        /**
         * Constructor for creating an Index object.
         *
         * @param model  the IndexModel instance managing this index.
         * @param result the ResultSet containing the index data.
         * @throws SQLException if there is an error retrieving data from the ResultSet.
         */
        Index(IndexModel model, ResultSet result) throws SQLException {
            this(model,
                    result.getInt("id"),
                    LanguageEnum.valueOf(result.getString("language")),
                    result.getString("source"),
                    result.getString("type"),
                    result.getString("value"),
                    result.getInt("line"),
                    result.getInt("startByte"),
                    result.getInt("endByte")
            );
        }

    }

}
