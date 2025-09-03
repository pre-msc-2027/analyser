package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;

public abstract class IdentifierAbs<P extends ISlangObject> extends SlangTokenAbs<P> {

    protected final String name;

    protected IdentifierAbs(P parent, Node node, String name) {
        super(parent, node);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
