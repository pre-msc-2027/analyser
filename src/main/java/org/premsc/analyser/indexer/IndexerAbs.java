package org.premsc.analyser.indexer;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.List;
import java.util.stream.Stream;

public abstract class IndexerAbs implements IIndexer {

    protected final LanguageEnum language;
    protected final String type;

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

    protected List<IndexerManager.Index> index(ITreeHelper treeHelper, Node node) {
        return this.index(treeHelper, node.getText(), node.getStartPoint().row(), node.getStartByte(), node.getEndByte());
    }

    protected List<IndexerManager.Index> index(ITreeHelper treeHelper, String value, int line, int startByte, int endByte) {
        return List.of(new IndexerManager.Index(value, line, startByte, endByte));
    }

}
