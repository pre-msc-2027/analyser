package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.IndexStatementAbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a WITH statement in the slang language.
 */
public class WithStatement extends IndexStatementAbs<IndexStatement<?>> {

    /**
     * Constructor for WithStatement.
     *
     * @param parent the parent index statement
     * @param node   the syntax tree node
     */
    public WithStatement(IndexStatement<?> parent, Node node) {
        super(parent, node);
    }

    /**
     * Factory method to create an array of WithStatement instances from the children of a syntax tree node.
     *
     * @param parent the parent index statement
     * @param node   the syntax tree node containing WITH statement children
     * @param <P>    the type of the parent index statement
     * @return an array of WithStatement instances
     */
    public static <P extends IndexStatement<?>> WithStatement[] listOf(P parent, Node node) {
        List<WithStatement> withStatementList = new ArrayList<>();
        for (Node child : getChildren(node, "with")) {
            withStatementList.add(new WithStatement(parent, child));
        }
        return withStatementList.toArray(WithStatement[]::new);
    }

    /**
     * Gets the joint selector for the WITH statement.
     *
     * @return the joint selector
     */
    public Selector<?> getJoint() {
        return Selector
                .of("index_table")
                .addColumn("value")
                .setPredicate(getSelectorPredicate());
    }

    @Override
    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {
    }

}
