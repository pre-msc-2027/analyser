package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.FinderStatementAbs;

public class IndexStatement extends FinderStatementAbs<FindStatement> {

    protected IndexStatement(FindStatement parent, Node node) {
        super(parent, node);
    }
}
