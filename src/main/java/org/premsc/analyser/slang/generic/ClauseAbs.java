package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a clause in a WHERE statement.
 *
 * @param <S> the type of the parent WHERE statement
 */
public abstract class ClauseAbs<S extends FinderStatementAbs<?>> extends SlangTokenAbs<WhereStatement<S>> {

    /**
     * Factory method to create a ClauseAbs instance from a syntax tree node.
     *
     * @param parent the parent WHERE statement
     * @param node   the syntax tree node representing the clause
     * @param <S>    the type of the parent finder statement
     * @param <P>    the type of the parent WHERE statement
     * @param <C>    the type of the clause
     * @return a ClauseAbs instance if a valid clause type is found, otherwise null
     */
    @SuppressWarnings("unchecked")
    public static <S extends FinderStatementAbs<?>, P extends WhereStatement<S>, C extends ClauseAbs<S>> C of(P parent, Node node) {
        if (parent.getParent() instanceof IndexStatementAbs<?>)
            return (C) new IndexClause((WhereStatement<IndexStatementAbs<?>>) parent, node);
        if (parent.getParent() instanceof NodeStatement)
            return (C) new NodeClause((WhereStatement<NodeStatement<?>>) parent, node);
        return null;
    }
    /**
     * Factory method to create an array of ClauseAbs instances from the children of a syntax tree node.
     *
     * @param parent the parent WHERE statement
     * @param node   the syntax tree node containing clause children
     * @param <S>    the type of the parent finder statement
     * @param <P>    the type of the parent WHERE statement
     * @return an array of ClauseAbs instances
     */
    @SuppressWarnings("unchecked")
    public static <S extends FinderStatementAbs<?>, P extends WhereStatement<S>> ClauseAbs<S>[] listOf(P parent, Node node) {
        List<ClauseAbs<S>> clauseList = new ArrayList<>();
        for (Node child : getChildren(node, "clause")) clauseList.add(ClauseAbs.of(parent, child));
        return (ClauseAbs<S>[]) clauseList.toArray(ClauseAbs[]::new);
    }

    protected final IClauseTarget target;
    protected final ClauseOperator operator;
    protected final IClauseValue value;

    /**
     * Constructor for ClauseAbs.
     *
     * @param parent the parent WHERE statement
     * @param node   the syntax tree node representing the clause
     */
    protected ClauseAbs(WhereStatement<S> parent, Node node) {
        super(parent, node);
        this.target = IClauseTarget.of(this, node);
        this.operator = ClauseOperator.of(this, node);
        this.value = IClauseValue.of(this, node);
    }

}
