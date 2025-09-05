package org.premsc.analyser.parser.queries.builder;

/**
 * A builder for constructing Tree-sitter queries.
 *
 * @param <This>
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class QueryBuilder<This extends QueryBuilder<This>> extends QueryBuilderNode<This> {

    /**
     * Creates a new QueryBuilder instance with the specified type.
     *
     * @param type The type of the query node.
     */
    public QueryBuilder(String type) {
        super(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     *
     * @param type    The type of the query node.
     * @param capture The capture name for the query node.
     */
    public QueryBuilder(String type, String capture) {
        super(type, capture);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type.
     *
     * @param type The type of the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder<?> of(String type) {
        return new QueryBuilder<>(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     *
     * @param type    The type of the query node.
     * @param capture The capture name for the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder<?> of(String type, String capture) {
        return new QueryBuilder<>(type, capture);
    }

    /**
     * Adds an equal predicate to the query.
     *
     * @param target The target of the predicate.
     * @param value  The value for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    public This equal(String target, String value) {
        return this.addValuePredicate(target, "eq", value);
    }

    /**
     * Adds a not equal predicate to the query.
     *
     * @param target The target of the predicate.
     * @param value  The value for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notEqual(String target, String value) {
        return this.addValuePredicate(target, "not-eq", value);
    }

    /**
     * Adds a match predicate to the query.
     *
     * @param target The target of the predicate.
     * @param regex  The regular expression for the match.
     * @return This QueryBuilder instance for method chaining.
     */
    public This match(String target, String regex) {
        return this.addValuePredicate(target, "match", regex);
    }

    /**
     * Adds a not match predicate to the query.
     *
     * @param target The target of the predicate.
     * @param regex  The regular expression for the not match.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notMatch(String target, String regex) {
        return this.addValuePredicate(target, "not-match", regex);
    }

    /**
     * Adds an equal other predicate to the query for a capture.
     *
     * @param target  The target of the predicate.
     * @param capture The capture name for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    public This equalOther(String target, String capture) {
        return this.addComparePredicate(target, "eq", capture);
    }

    /**
     * Adds a not equal other predicate to the query for a capture.
     *
     * @param target  The target of the predicate.
     * @param capture The capture name for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notEqualOther(String target, String capture) {
        return this.addComparePredicate(target, "not-eq", capture);
    }

    /**
     * Adds a value predicate to the query.
     *
     * @param target   The target of the predicate.
     * @param operator The operator for the predicate (e.g., "eq", "not-eq", "match", "not-match").
     * @param value    The value for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addValuePredicate(String target, String operator, String value) {
        return this.addPredicate(target, operator, "\"%s\"".formatted(value));
    }

    /**
     * Adds a compare predicate to the query for a capture.
     *
     * @param target   The target of the predicate.
     * @param operator The operator for the predicate (e.g., "eq", "not-eq").
     * @param capture  The capture name for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addComparePredicate(String target, String operator, String capture) {
        return this.addPredicate(target, operator, "@%s".formatted(capture));
    }

    /**
     * Builds the query string.
     *
     * @return The constructed query string.
     */
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");

        this.build(builder);

        this.buildPredicates(builder);

        builder.append(")");

        return builder.toString();
    }

}
