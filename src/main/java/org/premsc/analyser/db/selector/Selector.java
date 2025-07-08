package org.premsc.analyser.db.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a SQL-like SELECT statement builder.
 * @param <This> The type of the current Selector instance, used for method chaining.
 */
public class Selector<This extends Selector<This>> extends StatementAbs<This> {

    /**
     * Factory method to create a Selector instance for a given table.
     * @param table The name of the table to select from.
     * @return A new Selector instance.
     */
    public static Selector<?> of(String table) {
        return new Selector<>(table);
    }

    protected final String table;
    protected final List<String> columns = new ArrayList<>();
    protected SelectorPredicateAbs<?> predicate;

    /**
     * Constructor for Selector.
     *
     * @param table The name of the table to select from.
     */
    public Selector(String table) {
        this.table = table;
    }

    /**
     * Adds a column to the select statement.
     *
     * @param column The name of the column to add.
     * @return The current Selector instance for method chaining.
     */
    public This addColumn(String column) {
        if (column != null && !column.trim().isEmpty()) {
            columns.add(column);
        }
        return me;
    }

    public This setPredicate(SelectorPredicateAbs<?> predicate) {
        this.predicate = predicate;
        return me;
    }

    /**
     * Builds the select statement.
     * @return The select statement as a String.
     */
    public String build() {
        StringBuilder builder = new StringBuilder();
        build(builder);
        builder.append(";");
        return builder.toString();
    }

    /**
     * Gets the number of columns in the select statement.
     * @return The number of columns, or -1 if no columns are specified.
     */
    public int getColumnCount() {
        return columns.isEmpty()?-1:columns.size();
    }

    /**
     * Builds the select statement into the provided StringBuilder.
     *
     * @param builder The StringBuilder to append the select statement to.
     */
    protected void build(StringBuilder builder) {

        builder.append("SELECT ");

        buildColumns(builder);

        builder.append(" FROM ");

        builder.append(table);

        this.buildPredicate(builder);

    }

    /**
     * Builds the columns for the select statement.
     * @param builder The StringBuilder to append the columns to.
     */
    private void buildColumns(StringBuilder builder) {
        if (columns.isEmpty()) {
            builder.append("*");
        } else {
            builder.append(String.join(", ", columns));
        }
    }

    /**
     * Builds the predicates for the select statement.
     * @param builder The StringBuilder to append the predicates to.
     */
    private void buildPredicate(StringBuilder builder) {
        if (predicate == null) return;
        builder.append(" WHERE");
        predicate.build(builder);
    }


}
