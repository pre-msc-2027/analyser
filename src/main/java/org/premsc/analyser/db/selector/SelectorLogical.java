package org.premsc.analyser.db.selector;

/**
 * Represents a logical condition in a SQL-like SELECT statement builder.
 * @param <This> the type of the current SelectorLogical instance, used for method chaining
 */
public class SelectorLogical<This extends SelectorLogical<This>> extends SelectorPredicateAbs<This> {

    protected String column;

    /**
     * Creates a logical condition for a column.
     * @param column the column to apply the logical condition on
     */
    public SelectorLogical(String column) {
        this.column = column;
    }

    @Override
    protected void buildComparison(StringBuilder builder) {
        builder.append(" ").append(this.inverted?"NOT ":"").append(column);
    }
}
