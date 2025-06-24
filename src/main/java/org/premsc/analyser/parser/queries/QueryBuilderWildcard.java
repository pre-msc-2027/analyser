package org.premsc.analyser.parser.queries;


public class QueryBuilderWildcard<This extends QueryBuilderWildcard<This>> extends QueryBuilderSimpleAbs<This> {

    static public QueryBuilderWildcard<?> of(boolean named) {
        return new QueryBuilderWildcard<>(named);
    }

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     */
    public QueryBuilderWildcard() {
        this(false);
    }

    /**
     * Creates a new QueryBuilderAnonymous instance with the specified value.
     * @param named If true, the value will be treated as a named wildcard.
     */
    public QueryBuilderWildcard(boolean named) {
        super(named?"\"_\"":"_");
    }

}
