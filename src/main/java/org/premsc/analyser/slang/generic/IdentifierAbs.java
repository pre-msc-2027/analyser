package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.RuleExpression;

/**
 * Abstract class for identifiers in the slang language.
 */
public abstract class IdentifierAbs extends SlangTokenAbs<RuleExpression> implements IIdentifier {

    protected final String name;

    /**
     * Constructor for IdentifierAbs.
     *
     * @param parent the parent RuleExpression
     * @param node   the syntax tree node
     * @param name   the name of the identifier
     */
    protected IdentifierAbs(RuleExpression parent, Node node, String name) {
        super(parent, node);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
