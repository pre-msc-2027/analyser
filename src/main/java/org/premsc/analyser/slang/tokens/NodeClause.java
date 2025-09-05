package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.slang.generic.ClauseAbs;

/**
 * Represents a node clause in the slang language.
 */
public class NodeClause extends ClauseAbs<NodeStatement<?>> {

    /**
     * Constructor for NodeClause.
     *
     * @param parent the parent WHERE statement
     * @param node   the syntax tree node representing the node clause
     */
    public NodeClause(WhereStatement<NodeStatement<?>> parent, Node node) {
        super(parent, node);
    }

    /**
     * Builds the query using the provided QueryBuilder.
     *
     * @param builder the query builder to use
     */
    public void build(QueryBuilder<?> builder) {
        switch (this.operator.getOperator()) {
            case "=":
                builder.equal(this.target.getName(), this.value.getValue());
                break;
            case "!=":
                builder.notEqual(this.target.getName(), this.value.getValue());
                break;
            case "*":
                builder.match(this.target.getName(), this.value.getValue());
                break;
            case "!*":
                builder.notMatch(this.target.getName(), this.value.getValue());
                break;
        }
    }
}
