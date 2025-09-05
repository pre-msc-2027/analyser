package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.IFinderParent;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

import java.util.List;

/**
 * Class representing a node statement in the slang language.
 * @param <P> the type of the parent rule expression
 */
public class NodeStatement<P extends IFinderParent> extends FinderStatementAbs<P> implements ISlangBranchParent {

    protected final Branch<NodeStatement<?>> branch;

    /**
     * Constructor for NodeStatement.
     *
     * @param parent the parent rule expression
     * @param node   the syntax tree node representing the node statement
     */
    public NodeStatement(P parent, Node node) {
        super(parent, node);
        this.branch = Branch.of(this, node);
    }

    /**
     * Gets the query builder for this node statement.
     *
     * @return the constructed query builder
     */
    protected QueryBuilder<?> getBuilder() {
        QueryBuilder<?> builder = (QueryBuilder<?>) branch.build();

        for (ClauseAbs<?> clause : whereStatement.getClauses()) {
            NodeClause nodeClause = (NodeClause) clause;
            nodeClause.build(builder);
        }

        return builder;
    }

    /**
     * Executes the node statement using the provided database handler and tree helper.
     *
     * @param handler    the database handler to execute the query
     * @param treeHelper the tree helper to access the parse tree
     */
    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {

        QueryHelper queryHelper;
        try {
            queryHelper = treeHelper.query(this.getBuilder());
        } catch (UnsupportedLanguage e) {
            throw new RuntimeException(e);
        }

        NodeIdentifier nodeIdentifier = (NodeIdentifier) this.getRuleExpression().getTarget();
        List<Node> nodes = queryHelper.getNodes(nodeIdentifier.getName());
        nodeIdentifier.addCaptures(nodes);

    }
}
