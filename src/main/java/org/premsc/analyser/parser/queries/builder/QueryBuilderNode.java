package org.premsc.analyser.parser.queries.builder;


public class QueryBuilderNode<This extends QueryBuilderNode<This>> extends QueryBuilderNodeAbs<This> {

    protected final String type;

    /**
     * Creates a new QueryBuilder instance with the specified type.
     * @param type The type of the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilderNode<?> of(String type) {
        return new QueryBuilderNode<>(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilderNode<?> of(String type, String capture) {
        return new QueryBuilderNode<>(type, capture);
    }

    /**
     * Creates a new QueryBuilderNode instance with the specified type.
     * @param type The type of the query node.
     */
    protected QueryBuilderNode(String type) {
        this.type = type;
    }

    /**
     * Creates a new QueryBuilderNode instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     */
    protected QueryBuilderNode(String type, String capture) {
        super(capture);
        this.type = type;
    }

    @Override
    protected void buildBefore(StringBuilder builder) {
        builder.append("(")
                .append(this.type);
    }

    @Override
    protected void buildAfter(StringBuilder builder) {
        builder.append(")");
        super.buildAfter(builder);
    }
}
