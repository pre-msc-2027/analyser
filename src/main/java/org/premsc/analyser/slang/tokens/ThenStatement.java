package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ISlangFindParent;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class ThenStatement extends SlangTokenAbs<FindStatement> implements ISlangFindParent {
    public ThenStatement(FindStatement parent, Node node) {
        super(parent, node);
    }
}
