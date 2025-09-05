package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.tokens.IndexStatement;
import org.premsc.analyser.slang.tokens.NodeStatement;
import org.premsc.analyser.slang.tokens.WhereStatement;

import java.util.Objects;

/**
 * Abstract class representing a finder statement in the DSL.
 *
 * @param <P> the type of the parent token
 */
public abstract class FinderStatementAbs<P extends IFinderParent> extends SlangTokenAbs<P> {

    protected final WhereStatement<FinderStatementAbs<P>> whereStatement;

    /**
     * Constructor for FinderStatementAbs.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the finder statement
     */
    protected FinderStatementAbs(P parent, Node node) {
        super(parent, node);
        this.whereStatement = initWhereStatement(node);
    }

    /**
     * Factory method to create a FinderStatementAbs instance from a syntax tree node.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the finder statement
     * @param <P>    the type of the parent token
     * @return a FinderStatementAbs instance if a valid statement is found, otherwise null
     */
    public static <P extends IFinderParent> FinderStatementAbs<P> of(P parent, Node node) {
        Node finderStatementNode = getChild(node, "statement");
        Node childNode = finderStatementNode.getChild(0).orElseThrow(() -> new IllegalStateException("No child in statement"));
        if (Objects.equals(childNode.getText(), "node")) return new NodeStatement<>(parent, finderStatementNode);
        if (Objects.equals(childNode.getText(), "index")) return new IndexStatement<>(parent, finderStatementNode);
        throw new IllegalStateException("No valid finder statement found");
    }

    /**
     * Initializes the WHERE statement from the syntax tree node.
     *
     * @param node the syntax tree node
     * @return the initialized WHERE statement, or null if not present
     */
    private WhereStatement<FinderStatementAbs<P>> initWhereStatement(Node node) {
        Node whereNode = getChild(node, "where");
        if (whereNode != null) return new WhereStatement<>(this, whereNode);
        return null;
    }

    /**
     * Executes the finder statement using the provided database handler and tree helper.
     *
     * @param handler    the database handler
     * @param treeHelper the tree helper
     */
    public abstract void execute(DatabaseHandler handler, ITreeHelper treeHelper);
}
