package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.selector.Predicate;
import org.premsc.analyser.db.selector.SelectorPredicateAbs;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.IClauseValue;

public class IndexClause extends ClauseAbs<IndexStatement<?>> {

    public IndexClause(WhereStatement<IndexStatement<?>> parent, Node node) {
        super(parent, node);
    }

    @Override
    protected IClauseTarget initTarget(Node node) {
        Node fieldNode = getChild(node, "field");
        if (fieldNode != null) return new ClauseField(this, fieldNode);
        return null;
    }

    @Override
    protected IClauseValue initValue(Node node) {
        Node valueNode = getChild(node, "value");
        if (valueNode == null) return null;

        Node indexNode = getChild(valueNode, "index");
        if (indexNode != null) return IndexIdentifier.of(this, indexNode);

        return super.initValue(node);

    }

    public SelectorPredicateAbs<?> build() {

        if (this.value instanceof IndexIdentifier identifier)
            return switch (this.operator.getOperator()) {
                case "=" -> Predicate.in(this.target.getName(), this.getParent().getParent().getJoint(identifier));
                case "!="-> Predicate.in(this.target.getName(), this.getParent().getParent().getJoint(identifier)).not();
                default -> throw new IllegalStateException("Unexpected value: " + this.operator.getOperator());
            };

        return switch (this.operator.getOperator()) {
            case "=" -> Predicate.equal(this.target.getName(), this.value.getClauseValue());
            case "!="-> Predicate.notEqual(this.target.getName(), this.value.getClauseValue());
            default -> throw new IllegalStateException("Unexpected value: " + this.operator.getOperator());
        };
    }
}
