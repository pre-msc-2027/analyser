package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Abstract class for query rules.
 */
public abstract class QueryRuleAbs extends RuleAbs implements IQueryRule {

    /**
     * Constructor for QueryRuleAbs.
     * @param data A JsonObject containing the rule data, including parameters and language.
     */
    public QueryRuleAbs(JsonObject data) {
        super(data);
    }

    /**
     * Returns the query to be executed for this rule.
     * @return A QueryBuilder object representing the query.
     */
    protected abstract QueryBuilder<?> getQuery();

    /**
     * Tests the rule against a tree structure using the provided TreeHelper.
     *
     * @param treeHelper The TreeHelper instance to use for testing the rule.
     * @return A list of Node objects that match the rule criteria.
     */
    public Stream<Warning> test(ITreeHelper treeHelper) {

        List<Warning> warnings = new ArrayList<>();

        try(QueryHelper queryHelper = treeHelper.query(this.getQuery())) {
            queryHelper.streamNodes().map(
                    node -> new Warning(this, treeHelper.getSource(), node)
            ).forEach(warnings::add);
        }

        return warnings.stream();
    }
}
