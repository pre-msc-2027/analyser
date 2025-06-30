package org.premsc.analyser.parser.queries.builder;

/**
 * A builder for constructing Tree-sitter queries.
 */
public class QueryBuilder<This extends QueryBuilder<This>> extends QueryBuilderNode<This> {

    /**
     * Creates a new QueryBuilder instance with the specified type.
     * @param type The type of the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder<?> of(String type) {
        return new QueryBuilder<>(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     * @return A new QueryBuilder instance.
     */
    public static QueryBuilder<?> of(String type, String capture) {
        return new QueryBuilder<>(type, capture);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type.
     * @param type The type of the query node.
     */
    public QueryBuilder(String type) {
        super(type);
    }

    /**
     * Creates a new QueryBuilder instance with the specified type and capture.
     * @param type The type of the query node.
     * @param capture The capture name for the query node.
     */
    public QueryBuilder(String type, String capture) {
        super(type, capture);
    }

    public This equal(String target, String value) {
        return this.addValuePredicate(target, "eq", value);
    }

    public This notEqual(String target, String value) {
        return this.addValuePredicate(target, "not-eq", value);
    }

    public This match(String target, String regex) {
        return this.addValuePredicate(target, "match", regex);
    }

    public This notMatch(String target, String regex) {
        return this.addValuePredicate(target, "not-match", regex);
    }

    public This equalOther(String target, String capture) {
        return this.addComparePredicate(target, "eq", capture);
    }

    public This notEqualOther(String target, String capture) {
        return this.addComparePredicate(target, "not-eq", capture);
    }

    protected This addValuePredicate(String target, String operator, String value) {
        return this.addPredicate(target, operator, "\"%s\"".formatted(value));
    }

    protected This addComparePredicate(String target, String operator, String capture) {
        return this.addPredicate(target, operator, "@%s".formatted(capture));
    }

    /**
     * Builds the query string.
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
