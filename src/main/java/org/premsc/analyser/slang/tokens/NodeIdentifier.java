package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.IdentifierAbs;

public class NodeIdentifier<P extends ISlangObject> extends IdentifierAbs<P> {

    protected NodeIdentifier(P parent, Node node) {
        super(parent, node);
    }
}
