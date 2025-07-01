package org.premsc.analyser.parser.queries;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class QueryHelper implements AutoCloseable {

    static Predicate<QueryCapture> CaptureFilter = (QueryCapture capture) -> capture.name().equals("target");
    static Function<QueryMatch, Stream<Node>> NodeFlatMapper = (QueryMatch match) -> match.captures().stream().filter(CaptureFilter).map(QueryCapture::node);

    private final Query query;
    private final QueryCursor cursor;
    private final ITreeHelper tree;

    public QueryHelper(ITreeHelper tree, String queryString) {
        this.tree = tree;
        this.query = new Query(this.tree.getSource().getLanguageHelper().getTsLanguage(), queryString);
        this.cursor = new QueryCursor(this.query);
    }

    public Stream<Node> streamNodes() {
        return cursor.findMatches(tree.getRoot()).flatMap(NodeFlatMapper);
    }

    public List<Node> findNodes() {
        return cursor.findMatches(tree.getRoot()).flatMap(NodeFlatMapper).toList();
    }

    @Override
    public void close() {
        this.cursor.close();
        this.query.close();
    }
}
