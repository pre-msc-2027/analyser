package org.premsc.analyser.parser.queries.builder;

/**
 * Marker interface for query builders that can have child query builders.
 *
 * @param <This> the type of the implementing class
 */
public interface IQueryBuilderParent<This extends IQueryBuilderParent<This>> extends IQueryBuilder<This> {

    /**
     * Add a child query builder to the current builder.
     *
     * @param node The child query builder to add.
     * @param <Q>  the type of the child query builder
     * @return The current query builder instance (for method chaining).
     */
    <Q extends IQueryBuilder<?>> This addChild(Q node);
}
