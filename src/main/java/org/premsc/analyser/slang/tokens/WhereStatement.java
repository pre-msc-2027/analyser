package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class WhereStatement<S extends FinderStatementAbs<?>> extends SlangTokenAbs<S> {
    public WhereStatement(S parent, Node node) {
        super(parent, node);
    }
}
