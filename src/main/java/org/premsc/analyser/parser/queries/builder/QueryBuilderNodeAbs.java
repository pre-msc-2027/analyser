package org.premsc.analyser.parser.queries.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


abstract class QueryBuilderNodeAbs<This extends QueryBuilderNodeAbs<This>> extends QueryBuilderAbs<This> implements IQueryBuilderParent<This> {

    protected String capture = "";
    protected String quantity = "";

    protected final List<QueryBuilderAbs<?>> nodes = new ArrayList<>();
    private final List<QueryBuilderPredicate<?>> predicates = new ArrayList<>();

    /**
     * Default constructor for QueryBuilderNodeAbs.
     * Initializes an empty QueryBuilderNode without a capture name.
     */
    protected QueryBuilderNodeAbs() {}

    /**
     * Constructs a QueryBuilderNode with a specified capture name.
     * @param capture The capture name for this QueryBuilderNode.
     */
    protected QueryBuilderNodeAbs(String capture) {
        this.capture = capture;
    }

    /**
     * Sets the capture name for this QueryBuilderNode.
     * @param capture The capture name to set.
     * @return This QueryBuilder instance for method chaining.
     * @throws RuntimeException if the capture is already set.
     */
    public This capture(String capture) {
        if (!this.capture.isEmpty()) throw new RuntimeException("capture already set");
        this.capture = capture;
        return self;
    }

    /**
     * Adds an optional quantifier to this QueryBuilderNode.
     * @return This QueryBuilder instance for method chaining.
     */
    public This optional() {
        return this.quantify("?");
    }

    /**
     * Adds a quantifier that indicates this QueryBuilderNode can appear one or more times.
     * @return This QueryBuilder instance for method chaining.
     */
    public This oneOrMore() {
        return this.quantify("+");
    }

    /**
     * Adds a quantifier that indicates this QueryBuilderNode can appear zero or more times.
     * @return This QueryBuilder instance for method chaining.
     */
    public This zeroOrMore() {
        return this.quantify("*");
    }

    /**
     * Adds a quantifier to this QueryBuilderNode.
     * @param quantity The quantifier to set (e.g., "?", "+", "*").
     * @return This QueryBuilder instance for method chaining.
     */
    protected This quantify(String quantity) {
        this.quantity = quantity;
        return self;
    }

    /**
     * Adds an anchor to this QueryBuilderNode.
     * An anchor is a special node that can be used to mark the start or end of a query.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addAnchor() {
        return this.addChild(new QueryBuilderAnchor<>());
    }

    /**
     * Adds a wildcard node to this QueryBuilderNode.
     * A wildcard can match any sequence of characters.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addWildcard() {
        return this.addWildcard(false);
    }

    /**
     * Adds a named wildcard node to this QueryBuilderNode.
     * A named wildcard can match any sequence of characters and is identified by a name.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addNamedWildcard() {
        return this.addWildcard(true);
    }

    /**
     * Adds a wildcard node to this QueryBuilderNode, optionally named.
     * @param named If true, the wildcard will be named; otherwise, it will be anonymous.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addWildcard(boolean named) {
        return this.addChild(new QueryBuilderWildcard<>(named));
    }

    /**
     * Adds a child node to this QueryBuilderNode.
     * @param node The child node to add.
     * @return This QueryBuilder instance for method chaining.
     */
    public <Q extends IQueryBuilder<?>> This addChild(Q node) {
        this.nodes.add((QueryBuilderAbs<?>) node);
        return self;
    }

    /**
     * Adds a child node of the specified type to this QueryBuilderNode.
     * @param type The type of the child node to add.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addChild(String type) {
        return this.addChild(new QueryBuilderNode<>(type));
    }

    /**
     * Adds a child node of the specified type and capture to this QueryBuilderNode.
     * @param type The type of the child node to add.
     * @param capture The capture name for the child node.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addChild(String type, String capture) {
        return this.addChild(new QueryBuilderNode<>(type, capture));
    }

    /**
     * Adds an anonymous child node with the specified value to this QueryBuilderNode.
     * @param value The value for the anonymous child node.
     * @return This QueryBuilder instance for method chaining.
     */
    public This addAnonymousChild(String value) {
        return this.addChild(new QueryBuilderAnonymous<>(value));
    }

    /**
     * Adds a group of nodes to this QueryBuilderNode.
     * @param nodes The nodes to add to the group.
     * @return This QueryBuilder instance for method chaining.
     */
    @SafeVarargs
    public final <Q extends QueryBuilderAbs<?>> This addGroup(Q... nodes) {
        return this.addChild(new QueryBuilderGroup(nodes));
    }

    /**
     * Adds a group of nodes to this QueryBuilderNode with a specified capture.
     * @param capture The capture name for the group.
     * @param nodes The nodes to add to the group.
     * @return This QueryBuilder instance for method chaining.
     */
    @SafeVarargs
    public final <Q extends QueryBuilderAbs<?>> This addGroup(String capture, Q... nodes) {
        return this.addChild(new QueryBuilderGroup(capture, nodes));
    }

    /**
     * Adds an alternation group to this QueryBuilderNode.
     * @param nodes The nodes to add to the alternation group.
     * @return This QueryBuilder instance for method chaining.
     */
    @SafeVarargs
    public final <Q extends QueryBuilderAbs<?>> This addAlternation(Q... nodes) {
        return this.addChild(new QueryBuilderAlternation(nodes));
    }

    /**
     * Adds an alternation group to this QueryBuilderNode with a specified capture.
     * @param capture The capture name for the alternation group.
     * @param nodes The nodes to add to the alternation group.
     * @return This QueryBuilder instance for method chaining.
     */
    @SafeVarargs
    public final <Q extends QueryBuilderAbs<?>> This addAlternation(String capture, Q... nodes) {
        return this.addChild(new QueryBuilderAlternation(capture, nodes));
    }

    /**
     * Adds multiple child nodes to this QueryBuilderNode.
     * @param nodes The child nodes to add.
     * @return This QueryBuilder instance for method chaining.
     */
    @SafeVarargs
    public final <Q extends QueryBuilderAbs<?>> This addChildren(Q... nodes) {
        this.nodes.addAll(List.of(nodes));
        return self;
    }

    /**
     * Adds a predicate to this QueryBuilder that checks for equality with the specified value.
     * @param value The value to check for equality.
     * @return This QueryBuilder instance for method chaining.
     */
    public This equal(String value) {
        return this.addValuePredicate("eq", value);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks for inequality with the specified value.
     * @param value The value to check for inequality.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notEqual(String value) {
        return this.addValuePredicate("not-eq", value);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks if the value matches the specified regular expression.
     * @param regex The regular expression to match.
     * @return This QueryBuilder instance for method chaining.
     */
    public This match(String regex) {
        return this.addValuePredicate("match", regex);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks if the value does not match the specified regular expression.
     * @param regex The regular expression to not match.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notMatch(String regex) {
        return this.addValuePredicate("not-match", regex);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks for equality with another capture.
     * @param capture The capture name to compare with.
     * @return This QueryBuilder instance for method chaining.
     */
    public This equalOther(String capture) {
        return this.addComparePredicate("eq", capture);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks for inequality with another capture.
     * @param capture The capture name to compare with.
     * @return This QueryBuilder instance for method chaining.
     */
    public This notEqualOther(String capture) {
        return this.addComparePredicate("not-eq", capture);
    }

    /**
     * Adds a predicate to this QueryBuilder that checks if the value matches another capture.
     * @param operator The operator to use for the match (e.g., "eq").
     * @param value The value to match against the capture.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addValuePredicate(String operator, String value) {
        return this.addPredicate(operator, "\"%s\"".formatted(value));
    }

    /**
     * Adds a predicate to this QueryBuilder that compares the current capture with another capture.
     * @param operator The operator to use for the comparison (e.g., "eq").
     * @param capture The capture name to compare with.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addComparePredicate(String operator, String capture) {
        return this.addPredicate(operator, "@%s".formatted(capture));
    }

    /**
     * Adds a predicate to this QueryBuilder with the specified operator and target.
     * @param operator The operator to use for the predicate (e.g., "eq", "not-eq").
     * @param target The target value or capture for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addPredicate(String operator, String target) {
        if (this.capture.isEmpty()) throw new RuntimeException("Missing capture for predicate");
        return this.addPredicate(this.capture, operator, target);
    }

    /**
     * Adds a predicate to this QueryBuilder with the specified capture, operator, and target.
     * @param capture The capture name to use for the predicate.
     * @param operator The operator to use for the predicate (e.g., "eq", "not-eq").
     * @param target The target value or capture for the predicate.
     * @return This QueryBuilder instance for method chaining.
     */
    protected This addPredicate(String capture, String operator, String target) {
        this.predicates.add(new QueryBuilderPredicate<>(operator, capture, target));
        return self;
    }

    @Override
    protected void build(StringBuilder builder) {
        this.buildBefore(builder);
        this.buildChildren(builder);
        this.buildAfter(builder);
    }

    /**
     * Builds the query string before the children are processed.
     * This method can be overridden by subclasses to add custom logic before the children are built.
     * @param builder The StringBuilder to append the query string to.
     */
    protected void buildBefore(StringBuilder builder) {
        //
    }

    /**
     * Builds the query string after the children and predicates are processed.
     * Overrides must call this method to ensure the capture is appended correctly.
     * @param builder The StringBuilder to append the query string to.
     */
    protected void buildAfter(StringBuilder builder) {
        builder.append(this.quantity);
        this.buildCapture(builder);
    }

    /**
     * Builds the children of this QueryBuilder, appending their query strings to the provided StringBuilder.
     * @param builder The StringBuilder to append the children query strings to.
     */
    protected void buildChildren(StringBuilder builder) {

        if (this.nodes.isEmpty()) return;

        for (QueryBuilderAbs<?> child : this.nodes) {
            builder.append(" ");
            child.build(builder);
        }

    }

    /**
     * Builds the capture part of the query string if it is set.
     * @param builder The StringBuilder to append the capture to.
     */
    protected void buildCapture(StringBuilder builder) {

        if (this.capture.isEmpty()) return;

        builder.append(" @")
                .append(this.capture);
    }

    /**
     * Builds the predicates of this QueryBuilder, appending them to the provided StringBuilder.
     * Each predicate is appended in the order they were added.
     * @param builder The StringBuilder to append the predicates to.
     */
    protected void buildPredicates(StringBuilder builder) {

        List<QueryBuilderPredicate<?>> predicates = this.getPredicates().toList();

        if (predicates.isEmpty()) return;

        for (QueryBuilderPredicate<?> predicate : predicates) {
            builder.append(" ");
            predicate.build(builder);
        }

    }

    @Override
    protected Stream<QueryBuilderPredicate<?>> getPredicates() {
        return Stream.concat(
            this.predicates.stream(),
            this.nodes.stream().flatMap(QueryBuilderAbs::getPredicates)
        );
    }
}
