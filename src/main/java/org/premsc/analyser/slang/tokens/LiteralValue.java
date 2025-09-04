package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.*;

public class LiteralValue<P extends ISlangObject> extends SlangTokenAbs<P> implements IClauseValue {

    private final String value;

    public LiteralValue(P parent, Node node) {
        super(parent, node);
        this.value = node.getText();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getClauseValue() {
        return "";
    }
}
