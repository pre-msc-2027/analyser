package org.premsc.analyser.parser.queries;


class QueryBuilderPredicate<This extends QueryBuilderPredicate<This>> extends QueryBuilderAbs<This> {

    private final String operator;
    private final String capture;
    private final String value;

    /**
     * Creates a new QueryBuilderPredicate instance with the specified operator, capture, and value.
     *
     * @param operator The operator for the predicate.
     * @param capture  The capture name for the predicate.
     * @param value    The value to compare against.
     */
    protected QueryBuilderPredicate(String operator, String capture, String value) {
        this.operator = operator;
        this.capture = capture;
        this.value = value;
    }

    @Override
    protected void build(StringBuilder builder) {
        builder.append("(#")
                .append(operator)
                .append("? @")
                .append(capture)
                .append(" ")
                .append(value)
                .append(")");
    }
}
