package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;

import java.util.Arrays;

/**
 * Abstract base class for slang objects, providing common functionality.
 */
public abstract class SlangObjectAbs implements ISlangObject {

    /**
     * Constructor for SlangObjectAbs.
     *
     * @param node the syntax tree node representing the slang object
     */
    protected SlangObjectAbs(Node node) {
    }

    /**
     * Gets the first child node of the specified type from the parent node.
     *
     * @param parent the parent node
     * @param type   the type of the child node to retrieve
     * @return the first child node of the specified type, or null if none found
     */
    public static Node getChild(Node parent, String type) {
        return Arrays.stream(getChildren(parent, type)).findFirst().orElse(null);
    }

    /**
     * Gets all child nodes of the specified type from the parent node.
     *
     * @param parent the parent node
     * @param type   the type of the child nodes to retrieve
     * @return an array of child nodes of the specified type
     */
    public static Node[] getChildren(Node parent, String type) {
        return parent
                .getNamedChildren()
                .stream()
                .filter(child -> child.getType().equals(type))
                .toArray(Node[]::new);
    }

    @Override
    public String toString() {
        return this.getPath();
    }
}
