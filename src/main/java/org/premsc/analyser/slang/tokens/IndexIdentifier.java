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

public class IndexIdentifier extends TargetIdentifierAbs implements IClauseValue {

    protected final List<IndexModel.Index> captures = new ArrayList<>();

    public static <P extends ISlangObject> IndexIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        IndexIdentifier indexIdentifier = ruleExpression.getIndexIdentifier(name);
        if (indexIdentifier != null) return indexIdentifier;
        indexIdentifier = new IndexIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(indexIdentifier);
        return indexIdentifier;
    }

    protected IndexIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    @Override
    public List<Warning> getResults(ITreeHelper treeHelper) {
        return List.of();
    }

    @Override
    public String getClauseValue() {
        return "";
    }

    public void addCapture(IndexModel.Index index) {
        captures.add(index);
    }
}
