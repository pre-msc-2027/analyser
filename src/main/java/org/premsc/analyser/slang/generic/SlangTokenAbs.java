package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.RuleExpression;

/**
 * Abstract base class for slang tokens.
 *
 * @param <P> the type of the parent object, which must implement ISlangObject
 */
public abstract class SlangTokenAbs<P extends ISlangObject> extends SlangObjectAbs implements ISlangToken<P> {

    protected final P parent;
    protected final String path;

    /**
     * Constructor for SlangTokenAbs.
     *
     * @param parent the parent slang object
     * @param node   the syntax tree node representing the slang token
     */
    public SlangTokenAbs(P parent, Node node) {
        super(node);
        this.parent = parent;
        this.path = parent.getPath() + "." + this.getClass().getSimpleName();
    }

    @Override
    public P getParent() {
        return this.parent;
    }

    @Override
    public RuleExpression getRuleExpression() {
        return parent.getRuleExpression();
    }

    @Override
    public String getPath() {
        return this.path;
    }
}
