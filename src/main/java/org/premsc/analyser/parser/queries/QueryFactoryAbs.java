package org.premsc.analyser.parser.queries;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;

/**
 * Abstract class for query factories that encapsulate a QueryBuilder.
 * This class provides a common structure for building queries.
 */
public abstract class QueryFactoryAbs {

    protected final QueryBuilder<?> query;

    /**
     * Creates a new QueryFactoryAbs instance with the specified QueryBuilder.
     * @param query The QueryBuilder to be used for building queries.
     */
    protected QueryFactoryAbs(QueryBuilder<?> query) {
        this.query = query;
    }

    /**
     * Returns the QueryBuilder associated with this factory.
     * @return The QueryBuilder instance.
     */
    public QueryBuilder<?> getQuery() {
        return this.query;
    }

    /**
     * Builds the query string using the QueryBuilder.
     * @return The built query string.
     */
    public String build() {
        return this.query.build();
    }
}
