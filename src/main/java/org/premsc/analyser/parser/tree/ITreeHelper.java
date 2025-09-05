package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.repository.ISource;

/**
 * Interface for tree helper implementations.
 * Provides methods to access the root node of a parse tree.
 */
public interface ITreeHelper extends AutoCloseable {

    /**
     * Returns the source associated with this tree helper.
     *
     * @return the source object
     */
    ISource getSource();

    /**
     * Returns the root node of the parse tree.
     *
     * @return the root node of the tree
     */
    Node getRoot();

    /**
     * Executes a query on the parse tree using a QueryBuilder and returns the matches.
     *
     * @param queryBuilder the QueryBuilder to build the query
     * @return a list of Node objects representing the matches found
     * @throws UnsupportedLanguage if the language of the source is not supported
     */
    QueryHelper query(QueryBuilder<?> queryBuilder) throws UnsupportedLanguage;

}
