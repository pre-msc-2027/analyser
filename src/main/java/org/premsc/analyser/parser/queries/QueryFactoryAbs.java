package org.premsc.analyser.parser.queries;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;

public abstract class QueryFactoryAbs {

    protected final QueryBuilder<?> query;

    protected QueryFactoryAbs(QueryBuilder<?> query) {
        this.query = query;
    }

    public QueryBuilder<?> getQuery() {
        return this.query;
    }

    public String build() {
        return this.query.build();
    }
}
