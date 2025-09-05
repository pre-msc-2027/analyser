package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * Represents a literal value in the slang language.
 *
 * @param <P> the type of the parent object, which must extend ISlangObject
 */
public class LiteralValue<P extends ISlangObject> extends SlangTokenAbs<P> implements IClauseValue {

    private final String value;

    /**
     * Constructor for LiteralValue.
     *
     * @param parent the parent slang object
     * @param node   the syntax tree node representing the literal value
     */
    public LiteralValue(P parent, Node node) {
        super(parent, node);
        this.value = node.getText();
    }

    @Override
    public String getValue() {
        return value;
    }

}
