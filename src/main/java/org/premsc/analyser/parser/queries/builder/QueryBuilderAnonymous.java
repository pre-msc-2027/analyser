package org.premsc.analyser.parser.queries.builder;

/**
 * A query builder for creating anonymous query nodes.
 *
 * @param <This> the type of the implementing class
 */
public class QueryBuilderAnonymous<This extends QueryBuilderAnonymous<This>> extends QueryBuilderSimpleAbs<This> {

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     *
     * @param value The value to be used in the query.
     */
    public QueryBuilderAnonymous(String value) {
        super("\"" + value + "\"");
    }

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     *
     * @param value The value to be used in the query.
     * @return A new instance of QueryBuilderAnonymous.
     */
    static public QueryBuilderAnonymous<?> of(String value) {
        return new QueryBuilderAnonymous<>(value);
    }

}
