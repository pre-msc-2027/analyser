package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

import java.util.List;

/**
 * Class representing a node statement in the slang language.
 */
public class NodeStatement extends FinderStatementAbs<RuleExpression> implements ISlangBranchParent {

    protected final Branch<NodeStatement> branch;

    /**
     * Constructor for NodeStatement.
     *
     * @param parent the parent rule expression
     * @param node   the syntax tree node representing the node statement
     */
    public NodeStatement(RuleExpression parent, Node node) {
        super(parent, node);
        this.branch = initBranch(node);
    }

    /**
     * Initializes the branch from the syntax tree node.
     *
     * @param node the syntax tree node
     * @return the initialized branch, or null if not found
     */
    protected Branch<NodeStatement> initBranch(Node node) {
        Node branchNode = getChild(node, "branch");
        if (branchNode != null) return new Branch<>(this, branchNode);
        return null;
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
