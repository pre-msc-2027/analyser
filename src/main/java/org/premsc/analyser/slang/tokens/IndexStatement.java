package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.IFinderParent;
import org.premsc.analyser.slang.generic.IndexStatementAbs;

import java.sql.SQLException;

/**
 * Class representing an index statement in the slang language.
 *
 * @param <P> the type of the parent finder construct
 */
public class IndexStatement<P extends IFinderParent> extends IndexStatementAbs<P> implements IFinderParent {

    protected final WithStatement[] withStatements;

    /**
     * Constructor for IndexStatement.
     *
     * @param parent the parent finder construct
     * @param node   the syntax tree node representing the index statement
     */
    public IndexStatement(P parent, Node node) {
        super(parent, node);
        this.withStatements = WithStatement.listOf(this, node);
    }

    /**
     * Gets the joint selector associated with the given index identifier.
     *
     * @param indexIdentifier the index identifier
     * @return the joint selector, or null if not found
     */
    public Selector<?> getJoint(IndexIdentifier indexIdentifier) {
        for (WithStatement withStatement : withStatements) {
            if (withStatement.getIndexIdentifier().getName().equals(indexIdentifier.getName())) {
                return withStatement.getJoint();
            }
        }
        return null;
    }

    /**
     * Executes the index statement using the provided database handler and tree helper.
     *
     * @param handler    the database handler to execute the query
     * @param treeHelper the tree helper for additional context
     */
    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {
        try {
            for (IndexModel.Index index : handler.getIndexModel().queryMultiple(this.getSelector())) {
                indexIdentifier.addCapture(index);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
