package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;

public abstract class FinderStatementAbs<P extends ISlangObject> extends SlangTokenAbs<P> {

    protected FinderStatementAbs(P parent, Node node) {
        super(parent, node);
    }
}
