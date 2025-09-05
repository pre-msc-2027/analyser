package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

/**
 * Represents a WHERE statement in the slang language.
 *
 * @param <P> the type of the parent finder statement
 */
public class WhereStatement<P extends FinderStatementAbs<?>> extends SlangTokenAbs<P> {

    protected final ClauseAbs<P>[] clauses;

    /**
     * Constructor for WhereStatement.
     *
     * @param parent the parent finder statement
     * @param node   the syntax tree node representing the WHERE statement
     */
    public WhereStatement(P parent, Node node) {
        super(parent, node);
        this.clauses = ClauseAbs.listOf(this, node);
    }

    /**
     * Gets the clauses of the WHERE statement.
     *
     * @return an array of clauses
     */
    public ClauseAbs<P>[] getClauses() {
        return clauses;
    }
}
