package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.repository.Source;

/**
 * Interface for tree helper implementations.
 * Provides methods to access the root node of a parse tree.
 */
public interface ITreeHelper extends AutoCloseable {

    /**
     * Returns the source associated with this tree helper.
     * @return the source object
     */
    Source getSource();

    /**
     * Returns the root node of the parse tree.
     * @return the root node of the tree
     */
    Node getRoot();

}
