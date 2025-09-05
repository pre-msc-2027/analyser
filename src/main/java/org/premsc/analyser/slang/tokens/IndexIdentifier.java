package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.rules.Warning;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.TargetIdentifierAbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an index identifier in the slang language.
 */
public class IndexIdentifier extends TargetIdentifierAbs implements IClauseValue {

    protected final List<IndexModel.Index> captures = new ArrayList<>();

    /**
     * Factory method to create or retrieve an IndexIdentifier.
     *
     * @param parent the parent slang object
     * @param node   the syntax tree node representing the index identifier
     * @param <P>    the type of the parent slang object
     * @return the IndexIdentifier instance
     */
    public static <P extends ISlangObject> IndexIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        IndexIdentifier indexIdentifier = ruleExpression.getIndexIdentifier(name);
        if (indexIdentifier != null) return indexIdentifier;
        indexIdentifier = new IndexIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(indexIdentifier);
        return indexIdentifier;
    }

    /**
     * Constructor for IndexIdentifier.
     *
     * @param parent the parent rule expression
     * @param node   the syntax tree node
     * @param name   the name of the index identifier
     */
    protected IndexIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    @Override
    public List<Warning> getResults(ITreeHelper treeHelper) {
        List<Warning> results = new ArrayList<>();
        RuleExpression ruleExpression = getRuleExpression();
        for (IndexModel.Index node : captures)
            results.add(new Warning(ruleExpression.getRule(), treeHelper.getSource(), node));
        return results;
    }

    /**
     * Adds a captured index to this identifier.
     *
     * @param index the captured index
     */
    public void addCapture(IndexModel.Index index) {
        captures.add(index);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "IndexIdentifier{" +
               "name='" + name + '\'' +
               '}';
    }
}
