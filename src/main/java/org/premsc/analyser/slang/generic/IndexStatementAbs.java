package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.db.selector.SelectorPredicateAbs;
import org.premsc.analyser.slang.tokens.IndexClause;
import org.premsc.analyser.slang.tokens.IndexIdentifier;

public abstract class IndexStatementAbs<P extends IFinderParent> extends FinderStatementAbs<P> {

    protected final IndexIdentifier indexIdentifier;

    public IndexStatementAbs(P parent, Node node) {
        super(parent, node);
        this.indexIdentifier = initIndexIdentifier(node);
    }

    protected IndexIdentifier initIndexIdentifier(Node node) {
        Node indexIdentifierNode = getChild(node, "index");
        if (indexIdentifierNode != null) return IndexIdentifier.of(this, indexIdentifierNode);
        return null;
    }

    public SelectorPredicateAbs<?> getSelectorPredicate() {

        SelectorPredicateAbs<?> predicate = null;
        for (ClauseAbs<?> clause: whereStatement.getClauses()) {
            IndexClause indexClause = (IndexClause) clause;
            SelectorPredicateAbs<?> current = indexClause.build();
            if (predicate == null) predicate = current;
            else predicate.and(current);
        }
        return predicate;
    }

    public Selector<?> getSelector() {
        return Selector
                .of("index_table")
                .setPredicate(getSelectorPredicate());
    }
}
