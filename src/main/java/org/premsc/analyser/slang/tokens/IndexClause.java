package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;

public class IndexClause extends ClauseAbs<IndexStatement> {

    public IndexClause(WhereStatement<IndexStatement> parent, Node node) {
        super(parent, node);
    }
}
