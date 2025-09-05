package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * Represents a node type in the slang language.
 */
public class NodeType extends SlangTokenAbs<Branch<?>> {

    /**
     * Factory method to create a NodeType instance from a syntax tree node.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the node type
     * @param <P>    the type of the parent branch
     * @return a NodeType instance if the type node exists, otherwise null
     */
    public static <P extends Branch<?>> NodeType of(P parent, Node node) {
        Node typeNode = getChild(node, "type");
        if (typeNode != null) return new NodeType(parent, typeNode);
        return null;
    }

    protected final String type;

    /**
     * Constructor for NodeType.
     *
     * @param parent the parent branch
     * @param node   the syntax tree node representing the node type
     */
    protected NodeType(Branch<?> parent, Node node) {
        super(parent, node);
        this.type = node.getText();
    }
}
