package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.RuleExpression;

public abstract class IdentifierAbs extends SlangTokenAbs<RuleExpression> implements IIdentifier{

    protected final String name;

    protected IdentifierAbs(RuleExpression parent, Node node, String name) {
        super(parent, node);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
