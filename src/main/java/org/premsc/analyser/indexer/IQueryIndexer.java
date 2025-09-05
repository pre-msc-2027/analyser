package org.premsc.analyser.indexer;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;

/**
 * IQueryIndexer is an interface for indexers that process queries and generate indices.
 */
public interface IQueryIndexer extends IIndexer {

    /**
     * Returns the QueryBuilder that this indexer uses to build queries.
     *
     * @return the QueryBuilder instance used by this indexer
     */
    QueryBuilder<?> getQuery();

}
