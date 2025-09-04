package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

import java.util.ArrayList;
import java.util.List;

public class WhereStatement<P extends FinderStatementAbs<?>> extends SlangTokenAbs<P> {

    protected ClauseAbs<P>[] clauses;

    public WhereStatement(P parent, Node node) {
        super(parent, node);
        this.clauses = initClauses(node);
    }

    private ClauseAbs<P>[] initClauses(Node node) {
        List<ClauseAbs<P>> clauseList = new ArrayList<ClauseAbs<P>>();
        for (Node child : getChildren(node, "clause")) clauseList.add(initClause(child));
        return clauseList.toArray(ClauseAbs[]::new);
    }

    private <C extends ClauseAbs<P>> C initClause(Node node) {
        if (this.getParent() instanceof IndexStatement)
                return (C) new IndexClause((WhereStatement<IndexStatement<?>>) this, node);
        if (this.getParent() instanceof NodeStatement)
                return (C) new NodeClause((WhereStatement<NodeStatement>) this, node);
        return null;
    }

    public ClauseAbs<P>[] getClauses() {
        return clauses;
    }
}
