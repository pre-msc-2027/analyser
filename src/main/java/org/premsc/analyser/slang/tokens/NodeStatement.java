package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

public class NodeStatement extends FinderStatementAbs<FindStatement> implements ISlangBranchParent {

    protected NodeStatement(FindStatement parent, Node node) {
        super(parent, node);
    }

    @Override
    public Branch getBranch() {
        return null;
    }
}
