package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;

public class NodeClause extends ClauseAbs<NodeStatement> {

    public NodeClause(WhereStatement<NodeStatement> parent, Node node) {
        super(parent, node);
    }
}
