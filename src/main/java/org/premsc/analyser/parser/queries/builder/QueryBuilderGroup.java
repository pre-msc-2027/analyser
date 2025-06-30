package org.premsc.analyser.parser.queries.builder;

public class QueryBuilderGroup extends QueryBuilderGroupAbs<QueryBuilderGroup> {

    /**
     * Factory method to create a new QueryBuilderGroup instance.
     * @param nodes The query nodes to be included in the group.
     * @return A new QueryBuilderGroup instance.
     */
    @SafeVarargs
    public static <Q extends QueryBuilderAbs<?>> QueryBuilderGroup of(Q... nodes) {
        return new QueryBuilderGroup(nodes);
    }

    /**
     * Creates a new QueryBuilderGroup
     */
    public QueryBuilderGroup() {
        super('(', ')');
    }

    /**
     * Creates a new QueryBuilderGroup instance with default brackets.
     */
    public <Q extends QueryBuilderAbs<?>> QueryBuilderGroup(Q[] nodes) {
        super('(', ')', nodes);
    }

    /**
     * Creates a new QueryBuilderGroup instance with specified capture.
     * @param capture The capture name for the query node.
     */
    public QueryBuilderGroup(String capture) {
        super('(', ')', capture);
    }

    /**
     * Creates a new QueryBuilderGroup instance with specified capture.
     * @param capture The capture name for the query node.
     */
    public <Q extends QueryBuilderAbs<?>> QueryBuilderGroup(String capture, Q[] nodes) {
        super('(', ')', capture, nodes);
    }

}
