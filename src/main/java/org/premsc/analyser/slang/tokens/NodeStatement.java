package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.*;

import java.util.List;

public class NodeStatement extends FinderStatementAbs<RuleExpression> implements ISlangBranchParent {

    protected final Branch<NodeStatement> branch;

    public NodeStatement(RuleExpression parent, Node node) {
        super(parent, node);
        this.branch = initBranch(node);
    }

    protected Branch<NodeStatement> initBranch(Node node) {
        Node branchNode = getChild(node, "branch");
        if (branchNode != null) return new Branch<>(this, branchNode);
        return null;
    }

    public Branch<NodeStatement> getBranch() {
        return branch;
    }

    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {
        QueryBuilder<?> builder = (QueryBuilder<?>) branch.build();

        for (ClauseAbs<?> clause : whereStatement.getClauses()) {
            NodeClause nodeClause = (NodeClause) clause;
            nodeClause.build(builder);
        }

        String query = builder.build();

        QueryHelper queryHelper;
        try {
            queryHelper = treeHelper.query(builder);
        } catch (UnsupportedLanguage e) {
            throw new RuntimeException(e);
        }

        for (NodeCapture capture: branch.getCaptures()) {
            NodeIdentifier nodeIdentifier = capture.getNodeIdentifier();
            List<Node> nodes = queryHelper.getNodes(nodeIdentifier.getName());
            nodeIdentifier.addCaptures(nodes);
        }
    }
}
