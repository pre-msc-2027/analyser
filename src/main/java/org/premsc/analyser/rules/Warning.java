package org.premsc.analyser.rules;

import io.github.treesitter.jtreesitter.Node;
import org.apache.commons.codec.language.bm.Rule;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.repository.ISource;
import org.premsc.analyser.repository.Source;

public record Warning(
        IRule rule,
        ISource source,
        int line,
        int startByte,
        int endByte
){

    public Warning(IRule rule, ISource source, Node node) {
        this(rule, source, node.getStartPoint().row(), node.getStartByte(), node.getEndByte());
    }

    public Warning(IRule rule, ISource source, IndexModel.Index index) {
        this(rule, source, index.line(), index.startByte(), index.endByte());
    }
}
