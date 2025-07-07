package org.premsc.analyser.db.selector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a SQL-like comparison predicate for a column.
 * This class allows for creating various types of comparison predicates such as equality, inequality,
 * greater than, less than, and membership in a list or subquery.
 *
 * @param <T> the type of the value being compared
 */
class SelectorComparison<This extends SelectorComparison<This, T>, T> extends SelectorPredicateAbs<This> {

    protected Comparator comparator;
    protected String column;
    protected T value;

    /**
     * Constructor for SelectorComparison.
     * @param comparator the comparator to use for the comparison
     * @param column the column to compare
     * @param value the value to compare against
     */
    protected SelectorComparison(Comparator comparator, String column, T value) {
        this.comparator = comparator;
        this.column = column;
        this.value = value;
    }

    @Override
    protected void buildComparison(StringBuilder builder) {

        builder.append(" ")
                .append(column)
                .append(" ")
                .append(this.inverted?comparator.opposite:comparator.symbol)
                .append(" ")
                .append(value instanceof String ? "'" + value + "'" : value);

    }

    /**
     * Enum representing the different comparison operators used in SQL.
     */
    enum Comparator {
        EQUALS("=", "!="),
        NOT_EQUALS("!=", "="),
        GREATER_THAN(">", "<="),
        LESS_THAN("<", ">="),
        GREATER_THAN_OR_EQUAL_TO(">=", "<"),
        LESS_THAN_OR_EQUAL_TO("<=", ">");

        private final String symbol;
        private final String opposite;

        Comparator(String symbol, String opposite) {
            this.symbol = symbol;
            this.opposite = opposite;
        }

    }

}
