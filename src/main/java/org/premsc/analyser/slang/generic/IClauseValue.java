package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.*;

/**
 * IClauseValue represents a value used in a clause, such as in a WHERE clause.
 */
public interface IClauseValue extends ISlangObject {

    /**
     * Factory method to create an IClauseValue instance from a syntax tree node.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the clause
     * @param <P>    the type of the parent branch
     * @return an IClauseValue instance if a value node exists, otherwise null
     */
    static <P extends ISlangObject> IClauseValue of(P parent, Node node) {
        Node valueNode = SlangObjectAbs.getChild(node, "value");
        if (valueNode == null) throw new IllegalStateException("Value is missing");

        Node parameterNode = SlangObjectAbs.getChild(valueNode, "parameter");
        if (parameterNode != null) return ParameterIdentifier.of(parent, parameterNode);

        Node literalNode = SlangObjectAbs.getChild(valueNode, "literal");
        if (literalNode != null) return new LiteralValue<>(parent, literalNode);

        Node functionNode = SlangObjectAbs.getChild(valueNode, "function");
        if (functionNode != null) return new FunctionValue<>(parent, functionNode);

        if (parent instanceof IndexClause) {
            Node indexNode = SlangObjectAbs.getChild(valueNode, "index");
            if (indexNode != null) return IndexIdentifier.of(parent, indexNode);
        }

        throw new IllegalStateException("No valid clause value found");
    }

    /**
     * Gets the value as a string.
     *
     * @return the value
     */
    String getValue();

}
