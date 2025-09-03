package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.RuleExpression;

public abstract class SlangTokenAbs<P extends ISlangObject> extends SlangObjectAbs implements ISlangToken<P> {

    protected final P parent;

    public SlangTokenAbs(P parent, Node node) {
        super(node);
        this.parent = parent;
    }

    @Override
    public P getParent() {
        return this.parent;
    }

    @Override
    public RuleExpression getRuleStatement() {
        return parent.getRuleStatement();
    }
}
