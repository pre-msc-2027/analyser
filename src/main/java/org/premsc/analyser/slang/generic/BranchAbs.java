package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.IQueryBuilderParent;
import org.premsc.analyser.slang.tokens.Alternation;
import org.premsc.analyser.slang.tokens.Branch;
import org.premsc.analyser.slang.tokens.NodeCapture;

import java.util.ArrayList;
import java.util.List;

public abstract class BranchAbs<P extends ISlangBranchParent> extends SlangTokenAbs<P> implements ISlangBranchParent {

    protected final BranchAbs<?>[] branches;

    public BranchAbs(P parent, Node node) {
        super(parent, node);
        this.branches = initBranches(node);
    }

    private BranchAbs<?>[] initBranches(Node node) {
        List<BranchAbs<?>> brancheList = new ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            if (node.getChild(i).isEmpty()) continue;
            Node child = node.getChild(i).get();
            if (child.getType().equals("alternation"))
                brancheList.add(new Alternation<>(this, child));
            if (child.getType().equals("branch"))
                brancheList.add(new Branch<>(this, child));
        }
        return brancheList.toArray(BranchAbs[]::new);
    }

    public List<NodeCapture> getCaptures() {
        List<NodeCapture> captures = new ArrayList<>();
        for (BranchAbs<?> branch : this.branches) captures.addAll(branch.getCaptures());
        return captures;
    }

    public abstract IQueryBuilderParent<?> build();
}
