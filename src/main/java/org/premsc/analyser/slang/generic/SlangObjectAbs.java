package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;

import java.util.Arrays;

public abstract class SlangObjectAbs implements ISlangObject {

    public SlangObjectAbs(Node node) {}

    protected static Node getChild(Node parent, String type) {
        return Arrays.stream(getChildren(parent, type)).findFirst().orElse(null);
    }

    protected static Node[] getChildren(Node parent, String type) {
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
