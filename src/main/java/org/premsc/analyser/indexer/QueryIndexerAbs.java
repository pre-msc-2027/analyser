package org.premsc.analyser.indexer;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for indexers that use a query to find nodes in the tree.
 */
public abstract class QueryIndexerAbs extends IndexerAbs implements IQueryIndexer {

    /**
     * Constructor for QueryIndexerAbs.
     *
     * @param language the programming language of the code being indexed
     * @param type the type of indexer
     */
    protected QueryIndexerAbs(LanguageEnum language, String type) {
        super(language, type);
    }

    @Override
    public List<IndexerManager.Index> index(ITreeHelper treeHelper) {

        List<IndexerManager.Index> indexes = new ArrayList<>();

        try(QueryHelper queryHelper = treeHelper.query(this.getQuery())) {
            for (Node node: queryHelper.findNodes()) {
                indexes.addAll(this.index(treeHelper, node));
            }
        }

        return indexes;

    }

}
