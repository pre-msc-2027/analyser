package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class NodeCapture extends SlangTokenAbs<Branch> {

    public NodeCapture(Branch parent, Node node) {
        super(parent, node);
    }
}
