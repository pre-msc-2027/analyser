package org.premsc.analyser.db.selector;

import java.util.Arrays;

/**
 * Represents a SQL-like IN clause for checking membership of a value in a set or subquery.
 * This class allows for creating predicates that check if a column's value is in a specified set of values
 * or the result of a subquery.
 *
 * @param <T> The type of the values being checked for membership.
 */
class SelectorMembership<This extends SelectorMembership<This, T>, T> extends SelectorPredicateAbs<This> {

    protected final String column;
    protected T[] value;
    protected Selector<?> selector;

    /**
     * Creates a membership predicate for a column with a set of values.
     *
     * @param column the column to check membership against
     * @param value  the array of values to check for membership
     */
    SelectorMembership(String column, T[] value) {
        this.column = column;
        this.value = value;
    }

    /**
     * Creates a membership predicate for a column with a subquery.
     *
     * @param column   the column to check membership against
     * @param selector the subquery selector to check for membership
     */
    SelectorMembership(String column, Selector<?> selector) {
        this.column = column;
        this.selector = selector;
        if (selector.getColumnCount() != 1) {
            throw new IllegalArgumentException("Subquery must return exactly one column for IN clause.");
        }
    }

    @Override
    protected void buildComparison(StringBuilder builder) {

        builder.append(" ")
                .append(column)
                .append(this.inverted ? " NOT IN" : " IN");

        builder.append(" (");

        if (value != null) {
            this.buildValue(builder);
        } else if (this.selector != null) {
            this.buildQuery(builder);
        }
        builder.append(")");

    }

    /**
     * Builds the value part of the IN clause.
     *
     * @param builder the StringBuilder to append the values to
     */
    private void buildValue(StringBuilder builder) {
        builder.append(String.join(", ", Arrays.stream(value).map(Object::toString).toArray(String[]::new)));
    }

    /**
     * Builds the subquery part of the IN clause.
     *
     * @param builder the StringBuilder to append the subquery to
     */
    private void buildQuery(StringBuilder builder) {
        this.selector.build(builder);
    }

}
