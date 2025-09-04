package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.rules.Warning;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.TargetIdentifierAbs;

import java.util.ArrayList;
import java.util.List;

public class NodeIdentifier extends TargetIdentifierAbs implements IClauseTarget {

    protected final List<Node> captures = new ArrayList<>();

    public static <P extends ISlangObject> NodeIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        NodeIdentifier nodeIdentifier = ruleExpression.getNodeIdentifier(name);
        if (nodeIdentifier != null) return nodeIdentifier;
        nodeIdentifier = new NodeIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(nodeIdentifier);
        return nodeIdentifier;
    }

    protected NodeIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    public void addCaptures(List<Node> captures) {
        this.captures.addAll(captures);
    }

    public List<Warning> getResults(ITreeHelper treeHelper) {
        List<Warning> results = new ArrayList<>();
        RuleExpression ruleExpression = getRuleExpression();
        for (Node node : captures)
            results.add(new Warning(ruleExpression.getRule(), treeHelper.getSource(), node));
        return results;
    };
}
