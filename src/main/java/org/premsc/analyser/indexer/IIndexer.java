package org.premsc.analyser.indexer;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;

public interface IIndexer {

    LanguageEnum getLanguage();

    String getType();

    List<IndexerManager.Index> index(ITreeHelper treeHelper);

}
