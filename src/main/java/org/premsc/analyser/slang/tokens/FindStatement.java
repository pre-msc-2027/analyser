package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.ISlangFindParent;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class FindStatement extends SlangTokenAbs<ISlangFindParent> {

    protected final FinderStatementAbs<FindStatement> finderStatement;

    public FindStatement(ISlangFindParent parent, Node node) {
        super(parent, node);
        finderStatement = null;
    }
}
