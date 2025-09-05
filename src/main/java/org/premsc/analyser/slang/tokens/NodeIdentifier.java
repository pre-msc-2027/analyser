package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.rules.Warning;
import org.premsc.analyser.slang.generic.IClauseTarget;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.TargetIdentifierAbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a node identifier in the slang language.
 */
public class NodeIdentifier extends TargetIdentifierAbs implements IClauseTarget {

    /**
     * Factory method to create or retrieve a NodeIdentifier.
     *
     * @param parent the parent slang object
     * @param node   the syntax tree node representing the node identifier
     * @param <P>    the type of the parent slang object
     * @return the NodeIdentifier instance
     */
    public static <P extends ISlangObject> NodeIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        NodeIdentifier nodeIdentifier = ruleExpression.getNodeIdentifier(name);
        if (nodeIdentifier != null) return nodeIdentifier;
        nodeIdentifier = new NodeIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(nodeIdentifier);
        return nodeIdentifier;
    }

    protected final List<Node> captures = new ArrayList<>();

    /**
     * Constructor for NodeIdentifier.
     *
     * @param parent the parent rule expression
     * @param node   the syntax tree node
     * @param name   the name of the node identifier
     */
    protected NodeIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    /**
     * Adds captured nodes to this identifier.
     *
     * @param captures the list of captured nodes
     */
    public void addCaptures(List<Node> captures) {
        this.captures.addAll(captures);
    }

    /**
     * Gets the results of the node identifier analysis.
     *
     * @param treeHelper the tree helper to use for analysis
     * @return a list of warnings generated from the analysis
     */
    public List<Warning> getResults(ITreeHelper treeHelper) {
        List<Warning> results = new ArrayList<>();
        RuleExpression ruleExpression = getRuleExpression();
        for (Node node : captures)
            results.add(new Warning(ruleExpression.getRule(), treeHelper.getSource(), node));
        return results;
    }
}
