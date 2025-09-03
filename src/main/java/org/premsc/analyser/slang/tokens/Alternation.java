package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ISlangBranchParent;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class Alternation extends SlangTokenAbs<Branch> implements ISlangBranchParent {

    public Alternation(Branch parent, Node node) {
        super(parent, node);
    }

    @Override
    public Branch getBranch() {
        return null;
    }
}
