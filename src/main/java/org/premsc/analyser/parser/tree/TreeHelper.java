package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.InputEncoding;
import io.github.treesitter.jtreesitter.Node;
import io.github.treesitter.jtreesitter.Parser;
import io.github.treesitter.jtreesitter.Tree;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.repository.ISource;

import java.io.IOException;

/**
 * Abstract class for tree helper implementations.
 * Provides methods to access the root node of a parse tree and perform queries on it.
 */
public class TreeHelper implements ITreeHelper {

    protected final ISource source;
    protected final Parser tsParser;
    protected final Tree tsTree;

    /**
     * Constructor for the TreeHelper class.
     * Initializes the parser and parses the source content to create a parse tree.
     *
     * @param source the source object containing the content to be parsed
     */
    public TreeHelper(ISource source) throws UnsupportedLanguage, IOException {
        this.source = source;

        this.tsParser = new Parser(source.getLanguageHelper().getTsLanguage());
        this.tsTree = this.tsParser.parse(source.getContent(), InputEncoding.UTF_8).orElseThrow();

    }

    @Override
    public Node getRoot() {
        return this.tsTree.getRootNode();
    }

    @Override
    public ISource getSource() {
        return this.source;
    }

    @Override
    public QueryHelper query(QueryBuilder<?> queryBuilder) throws UnsupportedLanguage {
        return new QueryHelper(this, queryBuilder.build());
    }

    @Override
    public void close() {
        tsTree.close();
        tsParser.close();
    }

}
