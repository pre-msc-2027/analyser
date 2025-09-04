package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.IQueryBuilderParent;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.slang.generic.BranchAbs;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

import java.util.ArrayList;
import java.util.List;

public class Branch<P extends ISlangBranchParent> extends BranchAbs<P> {

    protected final NodeType nodeType;
    protected final NodeCapture capture;

    public Branch(P parent, Node node) {
        super(parent, node);
        this.nodeType = initNodeType(node);
        this.capture = initCapture(node);
    }

    private NodeType initNodeType(Node node) {
        Node typeNode = getChild(node, "type");
        if (typeNode != null) return new NodeType(this, typeNode);
        return null;
    }

    private NodeCapture initCapture(Node node) {
        Node captureNode = getChild(node, "capture");
        if (captureNode != null) return new NodeCapture(this, captureNode);
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
