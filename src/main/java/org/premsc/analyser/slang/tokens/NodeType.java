package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * Represents a node type in the slang language.
 */
public class NodeType extends SlangTokenAbs<Branch<?>> {

    protected final String type;

    /**
     * Constructor for NodeType.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the node type
     */
    public NodeType(Branch<?> parent, Node node) {
        super(parent, node);
        this.type = node.getText();
    }
}
