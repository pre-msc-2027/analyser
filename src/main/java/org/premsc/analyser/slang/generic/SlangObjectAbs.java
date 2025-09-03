package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;

import java.util.Arrays;

public abstract class SlangObjectAbs implements ISlangObject {

    public SlangObjectAbs(Node node) {}

    protected static Node getNode(Node parent, String type) {
        return Arrays.stream(getNodes(parent, type)).findFirst().orElse(null);
    }

    protected static Node[] getNodes(Node parent, String type) {
        return parent
                .getChildren()
                .stream()
                .filter(child -> child.getType().equals(type))
                .toArray(Node[]::new);
    }

}
