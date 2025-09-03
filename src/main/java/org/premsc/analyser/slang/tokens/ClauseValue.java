package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class ClauseValue extends SlangTokenAbs<ClauseAbs> {

    public ClauseValue(ClauseAbs parent, Node node) {
        super(parent, node);
    }
}
