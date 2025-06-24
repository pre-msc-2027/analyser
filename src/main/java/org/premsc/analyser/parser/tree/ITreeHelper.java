package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.Node;

/**
 * Interface for tree helper implementations.
 * Provides methods to access the root node of a parse tree.
 */
public interface ITreeHelper extends AutoCloseable {

    /**
     * Closes the tree helper, releasing any resources held by it.
     */
    Node getRoot();

}
