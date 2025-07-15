package org.premsc.analyser.parser.queries;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * QueryHelper is a utility class for executing queries on a tree structure.
 * It provides methods to find nodes in the tree that match a specified query.
 * The class implements AutoCloseable to ensure resources are released properly.
 */
public class QueryHelper implements AutoCloseable {

    static final Predicate<QueryCapture> CaptureFilter = (QueryCapture capture) -> capture.name().equals("target");
    static final Function<QueryMatch, Stream<Node>> NodeFlatMapper = (QueryMatch match) -> match.captures().stream().filter(CaptureFilter).map(QueryCapture::node);

    private final Query query;
    private final QueryCursor cursor;
    private final ITreeHelper tree;

    /**
     * Constructs a QueryHelper with the specified tree and query string.
     *
     * @param tree        The tree to query.
     * @param queryString The query string to execute on the tree.
     */
    public QueryHelper(ITreeHelper tree, String queryString) throws UnsupportedLanguage {
        this.tree = tree;
        this.query = new Query(this.tree.getSource().getLanguageHelper().getTsLanguage(), queryString);
        this.cursor = new QueryCursor(this.query);
    }

    /**
     * Executes the query on the tree and returns a stream of nodes that match the query.
     * @return A stream of nodes that match the query.
     */
    public Stream<Node> streamNodes() {
        return cursor.findMatches(tree.getRoot()).flatMap(NodeFlatMapper);
    }

    /**
     * Finds nodes in the tree that match the query and returns them as a list.
     * @return A list of nodes that match the query.
     */
    public List<Node> findNodes() {
        return cursor.findMatches(tree.getRoot()).flatMap(NodeFlatMapper).toList();
    }

    @Override
    public void close() {
        this.cursor.close();
        this.query.close();
    }
}
