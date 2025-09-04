package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class FunctionValue<P extends ISlangObject> extends SlangTokenAbs<P> implements IClauseValue {

    private final String name;

    public FunctionValue(P parent, Node node) {
        super(parent, node);
        this.name = node.getText();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return switch (this.name) {
            case "filepath" -> this.getRuleExpression().getCurrentFile().getFilepath();
            default -> throw new IllegalStateException("Unexpected function: " + this.name);
        };
    }
}
