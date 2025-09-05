package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * ClauseOperator represents the operator used in a WHERE clause.
 */
public class ClauseOperator extends SlangTokenAbs<ClauseAbs<?>> {

    protected final String operator;

    /**
     * Constructor for ClauseOperator.
     *
     * @param parent the parent clause
     * @param node   the syntax tree node representing the operator
     */
    public ClauseOperator(ClauseAbs<?> parent, Node node) {
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
