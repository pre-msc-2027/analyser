package org.premsc.analyser.indexer;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;

/**
 * IndexerAbs is an abstract class that implements the IIndexer interface.
 * It provides common functionality for indexers, such as storing the language and type,
 * and methods for indexing nodes and text.
 */
public abstract class IndexerAbs implements IIndexer {

    protected final LanguageEnum language;
    protected final String type;

    /**
     * Constructor for IndexerAbs.
     *
     * @param language The programming language that this indexer is responsible for.
     * @param type     The type of indexing that this indexer performs.
     */
    protected IndexerAbs(LanguageEnum language, String type) {
        this.language = language;
        this.type = type;
    }

    @Override
    public LanguageEnum getLanguage() {
        return this.language;
    }

    @Override
    public String getType() {
        return this.type;
    }

    /**
     * Indexes the source code using the provided ITreeHelper.
     *
     * @param treeHelper the ITreeHelper to assist in indexing
     * @param node       the Node to index
     * @return a List of IndexerManager.Index objects representing the indexed entries
     */
    protected List<IndexerManager.Index> index(ITreeHelper treeHelper, Node node) {
        return this.index(treeHelper, node.getText(), node.getStartPoint().row(), node.getStartByte(), node.getEndByte());
    }

    /**
     * Indexes the source code using the provided ITreeHelper.
     *
     * @param treeHelper the ITreeHelper to assist in indexing
     * @param value      the text value to index
     * @param line       the line number of the text
     * @param startByte  the starting byte position of the text
     * @param endByte    the ending byte position of the text
     * @return a List of IndexerManager.Index objects representing the indexed entries
     */
    protected List<IndexerManager.Index> index(ITreeHelper treeHelper, String value, int line, int startByte, int endByte) {
        return List.of(new IndexerManager.Index(value, line, startByte, endByte));
    }

}
