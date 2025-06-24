package org.premsc.analyser.parser.queries;

public class QueryBuilderGroup extends QueryBuilderGroupAbs<QueryBuilderGroup> {

    /**
     * Creates a new QueryBuilderGroup instance with default brackets.
     */
    public QueryBuilderGroup() {
        super('(', ')');
    }

    /**
     * Creates a new QueryBuilderGroup instance with specified capture.
     * @param capture The capture name for the query node.
     */
    public QueryBuilderGroup(String capture) {
        super('(', ')', capture);
    }

}
