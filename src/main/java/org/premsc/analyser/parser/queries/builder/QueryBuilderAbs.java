package org.premsc.analyser.parser.queries.builder;

import java.util.stream.Stream;

/**
 * Abstract base class for query builders.
 * This class provides a common structure for building queries.
 * It is designed to be extended by specific query builder implementations.
 */
abstract class QueryBuilderAbs<This extends QueryBuilderAbs<This>> implements IQueryBuilder<This> {

    @SuppressWarnings("unchecked")
    protected final This self = (This) this;

    /**
     * Builds the query represented by this QueryBuilderNode.
     * @param builder The StringBuilder to which the query will be appended.
     */
    protected abstract void build(StringBuilder builder);

    /**
     * Returns a stream of predicates associated with this QueryBuilderNode and its children.
     * This includes both the predicates added directly to this node and those from its child nodes.
     * @return A stream of QueryBuilderPredicate objects.
     */
    protected Stream<QueryBuilderPredicate<?>> getPredicates() {
        return Stream.empty();
    }
}
