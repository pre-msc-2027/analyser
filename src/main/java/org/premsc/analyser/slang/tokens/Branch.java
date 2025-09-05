package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.IQueryBuilderParent;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.slang.generic.BranchAbs;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

import java.util.List;

/**
 * Branch represents a branching structure in the syntax tree.
 *
 * @param <P> the type of the parent token
 */
public class Branch<P extends ISlangBranchParent> extends BranchAbs<P> {

    protected final NodeType nodeType;
    protected final NodeCapture capture;
    /**
     * Constructor for Branch.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the branch
     */
    public Branch(P parent, Node node) {
        super(parent, node);
        this.nodeType = NodeType.of(this, node);
        this.capture = NodeCapture.of(this, node);
    }

    /**
     * Factory method to create a Branch instance from a syntax tree node.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the branch
     * @param <P>    the type of the parent token
     * @return a Branch instance if the branch node exists, otherwise null
     */
    public static <P extends ISlangBranchParent> Branch<P> of(P parent, Node node) {
        Node branchNode = getChild(node, "branch");
        if (branchNode != null) return new Branch<>(parent, branchNode);
        return null;
    }

    @Override
    public IQueryBuilderParent<?> build() {
        QueryBuilder<?> builder = QueryBuilder.of(this.nodeType.type);
        if (this.capture != null) builder.capture(this.capture.nodeIdentifier.getName());
        for (BranchAbs<?> branch : this.branches) builder.addChild(branch.build());
        return builder;
    }

    @Override
    public List<NodeCapture> getCaptures() {
        List<NodeCapture> captures = super.getCaptures();
        if (this.capture != null) captures.add(this.capture);
        return captures;
    }
}
