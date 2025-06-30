package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.repository.Source;

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

    @Override
    public Source getSource() {
        return this.source;
    }

    /**
     * Executes a query on the parse tree using a QueryBuilder and returns the matches.
     *
     * @param queryBuilder the QueryBuilder to build the query
     * @return a list of Node objects representing the matches found
     */
    public QueryHelper query(QueryBuilder<?> queryBuilder) {
        return new QueryHelper(this, queryBuilder.build());
    }

    @Override
    public void close() {
        tsTree.close();
        tsParser.close();
    }

}
