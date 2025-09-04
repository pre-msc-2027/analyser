package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class NodeType extends SlangTokenAbs<Branch> {

    protected final String type;

    public NodeType(Branch parent, Node node) {
        super(parent, node);
        this.type = node.getText();
    }
}
