package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.*;

public abstract class ClauseAbs<S extends FinderStatementAbs<?>> extends SlangTokenAbs<WhereStatement<S>> {

    protected final IClauseTarget target;
    protected final ClauseOperator operator;
    protected final IClauseValue value;

    protected ClauseAbs(WhereStatement<S> parent, Node node) {
        super(parent, node);
        this.target = initTarget(node);
        this.operator = initOperator(node);
        this.value = initValue(node);
    }

    protected abstract IClauseTarget initTarget(Node node);

    protected ClauseOperator initOperator(Node node) {
        Node clauseOperatorNode = getChild(node, "operator");
        if (clauseOperatorNode != null) return new ClauseOperator(this, clauseOperatorNode);
        return null;
    }

    protected IClauseValue initValue(Node node) {
        Node valueNode = getChild(node, "value");
        if (valueNode == null) return null;

        Node parameterNode = getChild(valueNode, "parameter");
        if (parameterNode != null) return ParameterIdentifier.of(this, parameterNode);

        Node literalNode = getChild(valueNode, "literal");
        if (literalNode != null) return new LiteralValue<>(this, literalNode);

        Node functionNode = getChild(valueNode, "function");
        if (functionNode != null) return new FunctionValue<>(this, functionNode);

        return null;
    }
}
