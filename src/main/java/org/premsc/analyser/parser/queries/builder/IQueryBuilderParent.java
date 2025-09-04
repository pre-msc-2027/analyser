package org.premsc.analyser.parser.queries.builder;

public interface IQueryBuilderParent<This extends IQueryBuilderParent<This>> extends IQueryBuilder<This> {

    /**
     * Add a child query builder to the current builder.
     * @param node The child query builder to add.
     * @return The current query builder instance (for method chaining).
     */
    <Q extends IQueryBuilder<?>> This addChild(Q node);
}
