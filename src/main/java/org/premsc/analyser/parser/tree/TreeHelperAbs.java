package org.premsc.analyser.parser.tree;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.repository.Source;

import java.util.List;

public abstract class TreeHelperAbs implements ITreeHelper {

    protected final Source source;
    protected final Parser tsParser;
    protected final Tree tsTree;

    protected TreeHelperAbs(Source source) {
        this.source = source;

        this.tsParser = new Parser(source.getLanguageHelper().getTsLanguage());
        this.tsTree = this.tsParser.parse(source.getContent(), InputEncoding.UTF_8).orElseThrow();

    }

    @Override
    public Node getRoot() {
        return this.tsTree.getRootNode();
    }

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
