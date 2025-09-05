package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.selector.Predicate;
import org.premsc.analyser.db.selector.SelectorPredicateAbs;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.IndexStatementAbs;

/**
 * Represents a clause in an index statement, which includes a target field, an operator, and a value.
 */
public class IndexClause extends ClauseAbs<IndexStatementAbs<?>> {

    /**
     * Constructs an IndexClause with the specified parent and syntax tree node.
     *
     * @param parent the parent WhereStatement of this clause
     * @param node   the syntax tree node representing this clause
     */
    public IndexClause(WhereStatement<IndexStatementAbs<?>> parent, Node node) {
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

    /**
     * Gets the parent IndexStatement of this clause.
     *
     * @return the parent IndexStatement
     */
    public IndexStatement<?> getIndexStatement() {
        if (this.getParent().getParent() instanceof IndexStatement<?> indexStatement)
            return indexStatement;
        else if (this.getParent().getParent() instanceof WithStatement withStatement)
            return withStatement.getParent();
        throw new IllegalStateException("Unexpected parent type");
    }

    /**
     * Builds a SelectorPredicate based on the target, operator, and value of this clause.
     *
     * @return the constructed SelectorPredicate
     */
    public SelectorPredicateAbs<?> build() {

        if (this.value instanceof IndexIdentifier identifier)
            return switch (this.operator.getOperator()) {
                case "=" -> Predicate.in(this.target.getName(), this.getIndexStatement().getJoint(identifier));
                case "!=" -> Predicate.in(this.target.getName(), this.getIndexStatement().getJoint(identifier)).not();
                default -> throw new IllegalStateException("Unexpected operator: " + this.operator.getOperator());
            };

        return switch (this.operator.getOperator()) {
            case "=" -> Predicate.equal(this.target.getName(), this.value.getValue());
            case "!=" -> Predicate.notEqual(this.target.getName(), this.value.getValue());
            default -> throw new IllegalStateException("Unexpected operator: " + this.operator.getOperator());
        };
    }
}
