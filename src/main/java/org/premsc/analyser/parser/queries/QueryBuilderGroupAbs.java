package org.premsc.analyser.parser.queries;

/**
 * Abstract class for query builder groups, providing common functionality for building query groups.
 */
abstract class QueryBuilderGroupAbs<This extends QueryBuilderGroupAbs<This>> extends QueryBuilderNodeAbs<This> {

    protected final Character open;
    protected final Character close;

    /**
     * Creates a new QueryBuilderGroupAbs instance with the specified open and close characters.
     * @param open the character that opens the group
     * @param close the character that closes the group
     */
    public QueryBuilderGroupAbs(Character open, Character close) {
        this.open = open;
        this.close = close;
    }

    /**
     * Creates a new QueryBuilderGroupAbs instance with the specified open, close characters, and capture name.
     * @param open the character that opens the group
     * @param close the character that closes the group
     * @param capture the capture name for the query node
     */
    public QueryBuilderGroupAbs(Character open, Character close, String capture) {
        super(capture);
        this.open = open;
        this.close = close;
    }

    @Override
    protected void buildBefore(StringBuilder builder) {
        builder.append(open);
    }

    @Override
    protected void buildAfter(StringBuilder builder) {
        builder.append(close);
        builder.append(" ");
        super.buildAfter(builder);
    }

}
