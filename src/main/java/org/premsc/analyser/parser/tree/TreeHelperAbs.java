package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.repository.Source;

import java.util.List;

/**
 * Abstract class for tree helper implementations.
 * Provides methods to access the root node of a parse tree and perform queries on it.
 */
public abstract class TreeHelperAbs implements ITreeHelper {

    protected final Source source;
    protected final Parser tsParser;
    protected final Tree tsTree;

    /**
     * Constructor for the TreeHelperAbs class.
     * Initializes the parser and parses the source content to create a parse tree.
     *
     * @param source the source object containing the content to be parsed
     */
    protected TreeHelperAbs(Source source) {
        this.source = source;

        this.tsParser = new Parser(source.getLanguageHelper().getTsLanguage());
        this.tsTree = this.tsParser.parse(source.getContent(), InputEncoding.UTF_8).orElseThrow();

    }

    @Override
    public Node getRoot() {
        return this.tsTree.getRootNode();
    }

    /**
     * Executes a query on the parse tree and returns the matches.
     *
     * @param queryString the query string to be executed
     * @return a list of QueryMatch objects representing the matches found
     */
    public List<QueryMatch> query(String queryString) {

        Query query = new Query(this.source.getLanguageHelper().getTsLanguage(), queryString);
        QueryCursor cursor = new QueryCursor(query);

        List<QueryMatch> matches = cursor.findMatches(this.getRoot()).toList();

        query.close();
        cursor.close();

        return matches;

    }

    @Override
    public void close() {
        tsTree.close();
        tsParser.close();
    }
}
