package org.premsc.analyser.parser.queries;

/**
 * Abstract class representing a query builder anchor.
 */
class QueryBuilderAnchor<This extends QueryBuilderAnchor<This>> extends QueryBuilderAbs<This> {

    protected void build(StringBuilder builder) {
        builder.append(".");
    }

}
