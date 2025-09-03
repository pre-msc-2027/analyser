package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class Branch extends SlangTokenAbs<NodeStatement> {

    public Branch(NodeStatement parent, Node node) {
        super(parent, node);
    }
}
