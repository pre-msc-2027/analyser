package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.IClauseTarget;

public class NodeClause extends ClauseAbs<NodeStatement> {

    public NodeClause(WhereStatement<NodeStatement> parent, Node node) {
        super(parent, node);
    }

    @Override
    protected IClauseTarget initTarget(Node node) {
        Node nodeNode = getChild(node, "node");
        if (nodeNode != null) return NodeIdentifier.of(this, nodeNode);
        return null;
    }

    public void build(QueryBuilder<?> builder) {
        switch (this.operator.getOperator()) {
            case "=": builder.equal(this.target.getName(), this.value.getValue()); break;
            case "!=": builder.notEqual(this.target.getName(), this.value.getValue()); break;
            case "*": builder.match(this.target.getName(), this.value.getValue()); break;
            case "!*": builder.notMatch(this.target.getName(), this.value.getValue()); break;
        }
    }
}
