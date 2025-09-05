package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * FunctionValue represents a function used as a value in a WHERE clause.
 * Currently, supports functions like 'filepath'.
 *
 * @param <P> the type of the parent token
 */
public class FunctionValue<P extends ISlangObject> extends SlangTokenAbs<P> implements IClauseValue {

    private final String name;

    /**
     * Constructor for FunctionValue.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the function
     */
    public FunctionValue(P parent, Node node) {
        super(parent, node);
        this.name = node.getText();
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public String getValue() {
        return switch (this.name) {
            case "filepath" -> this.getRuleExpression().getCurrentFile().getFilepath();
            default -> throw new IllegalStateException("Unexpected function: " + this.name);
        };
    }
}
