package org.premsc.analyser.parser.queries.builder;

/**
 * A QueryBuilder that represents an alternation (logical OR) between multiple query nodes.
 * The alternation is enclosed in square brackets [].
 */
public class QueryBuilderAlternation extends QueryBuilderGroupAbs<QueryBuilderAlternation> {

    /**
     * Factory method to create a new QueryBuilderAlternation instance.
     *
     * @param nodes The query nodes to be included in the alternation.
     * @param <Q>   the type of the query nodes
     * @return A new QueryBuilderAlternation instance.
     */
    @SafeVarargs
    public static <Q extends IQueryBuilder<?>> QueryBuilderAlternation of(Q... nodes) {
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
     *
     * @param <Q>   the type of the query nodes
     * @param nodes The query nodes to be included in the alternation.
     */
    @SafeVarargs
    public <Q extends IQueryBuilder<?>> QueryBuilderAlternation(Q... nodes) {
        super('[', ']', nodes);
    }

    /**
     * Creates a new QueryBuilderAlternation instance with specified capture.
     *
     * @param capture The capture name for the query node.
     */
    public QueryBuilderAlternation(String capture) {
        super('[', ']', capture);
    }

    /**
     * Creates a new QueryBuilderAlternation instance with specified capture.
     *
     * @param capture The capture name for the query node.
     * @param nodes   The query nodes to be included in the alternation.
     * @param <Q>     the type of the query nodes
     */
    @SafeVarargs
    public <Q extends IQueryBuilder<?>> QueryBuilderAlternation(String capture, Q... nodes) {
        super('[', ']', capture, nodes);
    }


}
