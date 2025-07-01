package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;
import java.util.stream.Stream;

/**
 * CasingRule is a rule that checks if the casing of tag names in a tree structure matches a specified casing.
 * It extends RuleAbs and implements the test method to return nodes that do not match the specified casing.
 */
public class CasingRule extends QueryRuleAbs {

    /**
     * Constructor for CasingRule.
     * @param data A JsonObject containing the rule data.
     */
    public CasingRule(JsonObject data) {
        super(data);
    }

    /**
     * Returns the casing type specified in the rule parameters.
     * The casing is expected to be one of the values defined in the Casing enum.
     *
     * @return The Casing enum value corresponding to the "casing" parameter.
     */
    public Casing getCasing() {
        return Casing.valueOf(this.getParameter("casing").toUpperCase());
    }

    /**
     * Constructs a query to find elements in the tree that do not match the specified casing.
     * The query looks for both start and end tags with the tag name "target" and checks if they do not match the specified casing.
     *
     * @return A QueryBuilder object representing the query.
     */
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

}
