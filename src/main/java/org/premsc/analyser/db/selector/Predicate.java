package org.premsc.analyser.db.selector;

public class Predicate {

    /**
     * Creates a comparison predicate for equality.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> equal(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.EQUALS, column, value);
    }

    /**
     * Creates a comparison predicate for inequality.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> notEqual(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.NOT_EQUALS, column, value);
    }

    /**
     * Creates a comparison predicate for greater than.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> greaterThan(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.GREATER_THAN, column, value);
    }

    /**
     * Creates a comparison predicate for less than.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> lessThan(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.LESS_THAN, column, value);
    }

    /**
     * Creates a comparison predicate for greater than or equal to.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> greaterThanOrEqualTo(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.GREATER_THAN_OR_EQUAL_TO, column, value);
    }

    /**
     * Creates a comparison predicate for less than or equal to.
     * @param column the column to compare
     * @param value the value to compare against
     * @return a SelectorComparison instance
     * @param <T> the type of the value being compared
     */
    public static <T> SelectorPredicateAbs<?> lessThanOrEqualTo(String column, T value) {
        return new SelectorComparison<>(SelectorComparison.Comparator.LESS_THAN_OR_EQUAL_TO, column, value);
    }

    /**
     * Creates a membership predicate to check if a value is in a list.
     * @param column the column to check
     * @param value the list of values to check against
     * @return a SelectorMembership instance
     * @param <T> the type of the values being checked
     */
    public static <T> SelectorPredicateAbs<?> in(String column, T[] value) {
        return new SelectorMembership<>(column, value);
    }

    /**
     * Creates a membership predicate to check if a value is in the result of a subquery.
     * @param column the column to check
     * @param subquery the subquery to check against
     * @return a SelectorMembership instance
     * @param <T> the type of the values being checked
     */
    public static <T> SelectorPredicateAbs<?> in(String column, Selector<?> subquery) {
        return new SelectorMembership<>(column, subquery);
    }

    public static SelectorLogical<?> isTrue(String column) {
        return new SelectorLogical<>(column);
    }

    public static SelectorLogical<?> isFalse(String column) {
        return new SelectorLogical<>(column).not();
    }

}
