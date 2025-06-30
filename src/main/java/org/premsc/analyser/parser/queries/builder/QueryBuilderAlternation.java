package org.premsc.analyser.parser.queries.builder;

public class QueryBuilderAlternation extends QueryBuilderGroupAbs<QueryBuilderAlternation> {

    /**
     * Factory method to create a new QueryBuilderAlternation instance.
     * @param nodes The query nodes to be included in the alternation.
     * @return A new QueryBuilderAlternation instance.
     */
    @SafeVarargs
    public static <Q extends QueryBuilderAbs<?>> QueryBuilderAlternation of(Q... nodes) {
        return new QueryBuilderAlternation(nodes);
    }

    /**
     * Creates a new QueryBuilderAlternation.
     */
    public QueryBuilderAlternation() {
        super('[', ']');
    }

    /**
     * Creates a new QueryBuilderAlternation instance with default brackets.
     */
    @SafeVarargs
    public <Q extends QueryBuilderAbs<?>> QueryBuilderAlternation(Q... nodes) {
        super('[', ']', nodes);
    }

    /**
     * Creates a new QueryBuilderAlternation instance with specified capture.
     * @param capture The capture name for the query node.
     */
    public QueryBuilderAlternation(String capture) {
        super('[', ']', capture);
    }

    /**
     * Creates a new QueryBuilderAlternation instance with specified capture.
     * @param capture The capture name for the query node.
     */
    @SafeVarargs
    public <Q extends QueryBuilderAbs<?>> QueryBuilderAlternation(String capture, Q... nodes) {
        super('[', ']', capture, nodes);
    }


}
