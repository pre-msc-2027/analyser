package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * ClauseField represents a field used as the target in a WHERE clause.
 */
public class ClauseField extends SlangTokenAbs<IndexClause> implements IClauseTarget {

    protected final String name;

    /**
     * Constructor for ClauseField.
     *
     * @param parent the parent IndexClause
     * @param node   the syntax tree node representing the clause field
     */
    public ClauseField(IndexClause parent, Node node) {
        super(parent, node);
        this.name = node.getText();
    }

    @Override
    public String getName() {
        return name;
    }
}
