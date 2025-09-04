package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class ClauseField extends SlangTokenAbs<IndexClause> implements IClauseTarget {
    public ClauseField(IndexClause parent, Node node) {
        super(parent, node);
    }

    @Override
    public String getName() {
        return "";
    }
}
