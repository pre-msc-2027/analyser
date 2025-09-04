package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class LiteralValue<P extends ISlangObject> extends SlangTokenAbs<P> implements IClauseValue {

    private final String value;

    public LiteralValue(P parent, Node node) {
        super(parent, node);
        this.value = node.getText();
    }

    @Override
    public String getValue() {
        return value;
    }

}
