package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.BranchAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class NodeCapture extends SlangTokenAbs<BranchAbs<?>> {

    protected final NodeIdentifier nodeIdentifier;

    public NodeCapture(BranchAbs<?> parent, Node node) {
        super(parent, node);
        this.nodeIdentifier = NodeIdentifier.of(this, node);
    }

    public NodeIdentifier getNodeIdentifier() {
        return nodeIdentifier;
    }
}
