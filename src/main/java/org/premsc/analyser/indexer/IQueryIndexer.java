package org.premsc.analyser.indexer;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;

public interface IQueryIndexer extends IIndexer {

    QueryBuilder<?> getQuery();

}
