package org.premsc.analyser.parser.queries;

/**
 * Abstract class representing a simple query builder node.
 */
class QueryBuilderSimpleAbs<This extends QueryBuilderSimpleAbs<This>> extends QueryBuilderAbs<This> {

    protected final String value;

    /**
     * Creates a new QueryBuilderSimpleAbs instance with the specified value.
     * @param value The literal value to be used in the query.
     */
    public QueryBuilderSimpleAbs(String value) {
        this.value = value;
    }

    @Override
    protected void build(StringBuilder builder) {
        builder.append(this.value);
    }
}
