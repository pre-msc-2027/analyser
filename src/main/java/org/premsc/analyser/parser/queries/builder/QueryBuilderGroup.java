package org.premsc.analyser.parser.queries.builder;

/**
 * A query builder for creating groups of query nodes.
 */
public class QueryBuilderGroup extends QueryBuilderGroupAbs<QueryBuilderGroup> {

    /**
     * Creates a new QueryBuilderGroup
     */
    public QueryBuilderGroup() {
        super('(', ')');
    }

    /**
     * Creates a new QueryBuilderGroup instance with default brackets.
     *
     * @param <Q>   the type of the query nodes
     * @param nodes The query nodes to be included in the group.
     */
    public <Q extends IQueryBuilder<?>> QueryBuilderGroup(Q[] nodes) {
        super('(', ')', nodes);
    }

    /**
     * Creates a new QueryBuilderGroup instance with specified capture.
     *
     * @param capture The capture name for the query node.
     */
    public QueryBuilderGroup(String capture) {
        super('(', ')', capture);
    }

    /**
     * Creates a new QueryBuilderGroup instance with specified capture.
     *
     * @param capture The capture name for the query node.
     * @param nodes   The query nodes to be included in the group.
     * @param <Q>     the type of the query nodes
     */
    public <Q extends IQueryBuilder<?>> QueryBuilderGroup(String capture, Q[] nodes) {
        super('(', ')', capture, nodes);
    }

    /**
     * Factory method to create a new QueryBuilderGroup instance.
     *
     * @param nodes The query nodes to be included in the group.
     * @param <Q>   the type of the query nodes
     * @return A new QueryBuilderGroup instance.
     */
    @SafeVarargs
    public static <Q extends IQueryBuilder<?>> QueryBuilderGroup of(Q... nodes) {
        return new QueryBuilderGroup(nodes);
    }

}
