package org.premsc.analyser.parser.queries.builder;

/**
 * A builder for creating wildcard query components.
 *
 * @param <This> the type of the implementing class
 */
public class QueryBuilderWildcard<This extends QueryBuilderWildcard<This>> extends QueryBuilderSimpleAbs<This> {

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     */
    public QueryBuilderWildcard() {
        this(false);
    }

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     *
     * @param named If true, the value will be treated as a named wildcard.
     */
    public QueryBuilderWildcard(boolean named) {
        super(named ? "\"_\"" : "_");
    }

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     *
     * @param named If true, the value will be treated as a named wildcard.
     * @return a new QueryBuilderAnonymous instance
     */
    static public QueryBuilderWildcard<?> of(boolean named) {
        return new QueryBuilderWildcard<>(named);
    }

}
