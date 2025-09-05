package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.IQueryBuilderParent;
import org.premsc.analyser.slang.tokens.Alternation;
import org.premsc.analyser.slang.tokens.Branch;
import org.premsc.analyser.slang.tokens.NodeCapture;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a branch in the syntax tree.
 *
 * @param <P> the type of the parent token
 */
public abstract class BranchAbs<P extends ISlangBranchParent> extends SlangTokenAbs<P> implements ISlangBranchParent {

    protected final BranchAbs<?>[] branches;

    /**
     * Constructor for BranchAbs.
     *
     * @param parent the parent token
     * @param node   the syntax tree node representing the branch
     */
    protected BranchAbs(P parent, Node node) {
        super(parent, node);
        this.branches = listOf(this, node);
    }

    /**
     * Factory method to create a BranchAbs instance based on the node type.
     *
     * @param parent the parent token
     * @param node   the syntax tree node
     * @param <P>    the type of the parent token
     * @return an instance of BranchAbs
     * @throws IllegalArgumentException if the node type is unknown
     */
    public static <P extends ISlangBranchParent> BranchAbs<?> of(P parent, Node node) {
        if (node.getType().equals("alternation"))
            return new Alternation<>(parent, node);
        if (node.getType().equals("branch"))
            return new Branch<>(parent, node);
        throw new IllegalArgumentException("Unknown branch type: " + node.getType());
    }

    /**
     * Creates an array of BranchAbs instances for each child node.
     *
     * @param parent the parent token
     * @param node   the syntax tree node
     * @param <P>    the type of the parent token
     * @return an array of BranchAbs instances
     */
    public static <P extends ISlangBranchParent> BranchAbs<?>[] listOf(P parent, Node node) {
        List<BranchAbs<?>> brancheList = new ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChild(i).orElse(null);
            if (child == null) continue;
            brancheList.add(BranchAbs.of(parent, child));
        }
        return brancheList.toArray(BranchAbs[]::new);
    }

    /**
     * Gets all captures from this branch and its sub-branches.
     *
     * @return a list of NodeCapture objects
     */
    public List<NodeCapture> getCaptures() {
        List<NodeCapture> captures = new ArrayList<>();
        for (BranchAbs<?> branch : this.branches) captures.addAll(branch.getCaptures());
        return captures;
    }

    /**
     * Builds the query builder representation of this branch.
     *
     * @return the query builder parent
     */
    public abstract IQueryBuilderParent<?> build();
}
