package org.premsc.analyser.parser.queries;

/**
 * A builder for constructing Tree-sitter queries.
 */
public class QueryBuilder extends QueryBuilderNode<QueryBuilder> {

    /**
     * Creates a new QueryBuilder instance with the specified type.
     * @param type The type of the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder of(String type) {
        return new QueryBuilder(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder of(String type, String capture) {
        return new QueryBuilder(type, capture);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type.
     * @param type The type of the query node.
     */
    public QueryBuilder(String type) {
        super(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     */
    public QueryBuilder(String type, String capture) {
        super(type, capture);
    }

    /**
     * Builds the query string.
     * @return The constructed query string.
     */
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");

        this.build(builder);

        builder.append(")");

        this.buildPredicates(builder);

        return builder.toString();
    }

}
