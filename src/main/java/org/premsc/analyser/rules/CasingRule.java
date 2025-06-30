package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.TreeHelperAbs;

import java.util.List;

public class CasingRule extends RuleAbs {

    public CasingRule(JsonObject data) {
        super(data);
    }

    public Casing getCasing() {
        return Casing.valueOf(this.getParameter("casing"));
    }

    protected QueryBuilder<?> getQuery() {
        return QueryBuilder
                .of("element")
                .addAlternation(
                        QueryBuilder
                                .of("start_tag")
                                .addChild("tag_name", "target"),
                        QueryBuilder
                                .of("end_tag")
                                .addChild("tag_name", "target")
                ).notMatch("target", this.getCasing().getRegex());
    }

    @Override
    public List<Node> test(TreeHelperAbs treeHelper) {
        QueryHelper queryHelper = treeHelper.query(this.getQuery());
        List<Node> nodes = queryHelper.streamNodes().toList();
        queryHelper.close();
        return nodes;
    }
}
