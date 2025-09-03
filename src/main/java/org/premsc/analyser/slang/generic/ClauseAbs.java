package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.WhereStatement;

public abstract class ClauseAbs<S extends FinderStatementAbs<?>> extends SlangTokenAbs<WhereStatement<S>> {
    protected ClauseAbs(WhereStatement<S> parent, Node node) {
        super(parent, node);
    }
}
