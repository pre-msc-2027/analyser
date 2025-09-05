package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.IndexStatementAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

import java.util.ArrayList;
import java.util.List;

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
        this.clauses = initClauses(node);
    }

    /**
     * Initializes the clauses of the WHERE statement from the syntax tree node.
     *
     * @param node the syntax tree node
     * @return an array of initialized clauses
     */
    private ClauseAbs<P>[] initClauses(Node node) {
        List<ClauseAbs<P>> clauseList = new ArrayList<>();
        for (Node child : getChildren(node, "clause")) clauseList.add(initClause(child));
        @SuppressWarnings("unchecked")
        ClauseAbs<P>[] clauses = clauseList.toArray(ClauseAbs[]::new);
        return clauses;
    }

    /**
     * Initializes a clause from the syntax tree node.
     *
     * @param node the syntax tree node
     * @param <C>  the type of the clause
     * @return the initialized clause
     */
    @SuppressWarnings("unchecked")
    private <C extends ClauseAbs<P>> C initClause(Node node) {
        if (this.getParent() instanceof IndexStatementAbs<?>)
            return (C) new IndexClause((WhereStatement<IndexStatementAbs<?>>) this, node);
        if (this.getParent() instanceof NodeStatement)
            return (C) new NodeClause((WhereStatement<NodeStatement>) this, node);
        return null;
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
