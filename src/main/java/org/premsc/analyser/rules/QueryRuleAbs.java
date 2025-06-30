package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.TreeHelperAbs;

import java.util.List;

public abstract class QueryRuleAbs extends RuleAbs {

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

    @Override
    public List<Node> test(TreeHelperAbs treeHelper) {
        QueryHelper queryHelper = treeHelper.query(this.getQuery());
        List<Node> nodes = queryHelper.streamNodes().toList();
        queryHelper.close();
        return nodes;
    }
}
