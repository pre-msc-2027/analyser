package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * ClauseOperator represents the operator used in a WHERE clause.
 */
public class ClauseOperator extends SlangTokenAbs<ClauseAbs<?>> {

    /**
     * Factory method to create a ClauseOperator instance from a syntax tree node.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the node type
     * @param <P>    the type of the parent branch
     * @return a ClauseOperator instance if the type node exists, otherwise null
     */
    public static <P extends ClauseAbs<?>> ClauseOperator of(P parent, Node node) {
        Node typeNode = getChild(node, "operator");
        if (typeNode != null) return new ClauseOperator(parent, typeNode);
        return null;
    }

    protected final String operator;

    /**
     * Constructor for ClauseOperator.
     *
     * @param parent the parent clause
     * @param node   the syntax tree node representing the operator
     */
    protected ClauseOperator(ClauseAbs<?> parent, Node node) {
        super(parent, node);
        this.operator = node.getText();
    }

    /**
     * Gets the operator as a string.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }
}
