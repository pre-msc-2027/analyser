package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.IdentifierAbs;

public class IndexIdentifier<P extends ISlangObject> extends IdentifierAbs<P> {

    protected IndexIdentifier(P parent, Node node) {
        super(parent, node);
    }
}
