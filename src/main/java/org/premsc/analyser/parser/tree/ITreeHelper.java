package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.Node;

public interface ITreeHelper extends AutoCloseable {

    Node getRoot();

}
