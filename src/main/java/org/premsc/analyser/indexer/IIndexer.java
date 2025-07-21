package org.premsc.analyser.indexer;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;

/**
 * IIndexer is an interface for indexers that process source code and generate indices.
 * Each indexer is responsible for a specific programming language and type of indexing.
 */
public interface IIndexer {

    /**
     * Returns the language that this indexer is responsible for.
     * @return the LanguageEnum representing the programming language
     */
    LanguageEnum getLanguage();

    /**
     * Returns the type of indexing that this indexer performs.
     * @return a String representing the type of indexing
     */
    String getType();

    /**
     * Indexes the source code using the provided ITreeHelper.
     * @param treeHelper the ITreeHelper to assist in indexing
     * @return a List of IndexerManager.Index objects representing the indexed entries
     */
    List<IndexerManager.Index> index(ITreeHelper treeHelper);

}
