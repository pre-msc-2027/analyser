package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class ValidateStatement extends SlangTokenAbs<ThenStatement> {

    public ValidateStatement(ThenStatement parent, Node node) {
        super(parent, node);
    }
}
