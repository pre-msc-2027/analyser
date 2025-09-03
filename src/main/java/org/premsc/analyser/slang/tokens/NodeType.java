package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class NodeType extends SlangTokenAbs<Branch> {
    public NodeType(Branch parent, Node node) {
        super(parent, node);
    }
}
