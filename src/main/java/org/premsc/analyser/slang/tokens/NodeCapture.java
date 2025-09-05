package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.BranchAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * Represents a node capture in the slang language.
 */
public class NodeCapture extends SlangTokenAbs<BranchAbs<?>> {

    protected final NodeIdentifier nodeIdentifier;

    /**
     * Constructor for NodeCapture.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the node capture
     */
    public NodeCapture(BranchAbs<?> parent, Node node) {
        super(parent, node);
        this.nodeIdentifier = NodeIdentifier.of(this, node);
    }

}
