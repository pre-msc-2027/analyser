package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.db.selector.SelectorPredicateAbs;
import org.premsc.analyser.slang.tokens.IndexClause;
import org.premsc.analyser.slang.tokens.IndexIdentifier;

/**
 * Abstract class representing an index statement in the slang language.
 *
 * @param <P> the type of the parent finder construct
 */
public abstract class IndexStatementAbs<P extends IFinderParent> extends FinderStatementAbs<P> {

    protected final IndexIdentifier indexIdentifier;

    /**
     * Constructor for IndexStatementAbs.
     *
     * @param parent the parent finder construct
     * @param node   the syntax tree node representing the index statement
     */
    protected IndexStatementAbs(P parent, Node node) {
        super(parent, node);
        this.indexIdentifier = initIndexIdentifier(node);
    }

    /**
     * Initializes the index identifier from the syntax tree node.
     *
     * @param node the syntax tree node
     * @return the initialized index identifier, or null if not present
     */
    protected IndexIdentifier initIndexIdentifier(Node node) {
        Node indexIdentifierNode = getChild(node, "index");
        if (indexIdentifierNode != null) return IndexIdentifier.of(this, indexIdentifierNode);
        return null;
    }

    /**
     * Gets the index identifier associated with this index statement.
     *
     * @return the index identifier
     */
    public IndexIdentifier getIndexIdentifier() {
        return indexIdentifier;
    }

    /**
     * Builds the selector predicate from the where statement clauses.
     *
     * @return the constructed selector predicate
     */
    public SelectorPredicateAbs<?> getSelectorPredicate() {

        SelectorPredicateAbs<?> predicate = null;
        for (ClauseAbs<?> clause : whereStatement.getClauses()) {
            IndexClause indexClause = (IndexClause) clause;
            SelectorPredicateAbs<?> current = indexClause.build();
            if (predicate == null) predicate = current;
            else predicate.and(current);
        }
        return predicate;
    }

    /**
     * Gets the selector for this index statement.
     *
     * @return the selector
     */
    public Selector<?> getSelector() {
        return Selector
                .of("index_table")
                .setPredicate(getSelectorPredicate());
    }

    @Override
    public String toString() {
        return "IndexStatementAbs{" +
               "indexIdentifier=" + indexIdentifier +
               '}';
    }
}
