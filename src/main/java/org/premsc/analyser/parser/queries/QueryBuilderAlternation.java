package org.premsc.analyser.parser.queries;

public class QueryBuilderAlternation extends QueryBuilderGroupAbs<QueryBuilderAlternation> {

    /**
     * Creates a new QueryBuilderAlternation instance with default brackets.
     */
    public QueryBuilderAlternation() {
        super('[', ']');
    }

    /**
     * Creates a new QueryBuilderAlternation instance with specified capture.
     * @param capture The capture name for the query node.
     */
    public QueryBuilderAlternation(String capture) {
        super('[', ']', capture);
    }


}
