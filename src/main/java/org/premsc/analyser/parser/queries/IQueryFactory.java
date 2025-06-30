package org.premsc.analyser.parser.queries;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;

public interface IQueryFactory {

    QueryBuilder<?> getQuery();

    String build();

}
