package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.IdentifierAbs;

public class ParameterIdentifier extends IdentifierAbs<RuleExpression> {

    protected final String type;

    public static <P extends ISlangObject> ParameterIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleStatement();
        String name = ParameterIdentifier.getNode(node, "value").getText();
        ParameterIdentifier parameterIdentifier = ruleExpression.getParameter(name);
        if (parameterIdentifier != null) return parameterIdentifier;
        String type = ParameterIdentifier.getNode(node, "type").getText();
        return new ParameterIdentifier(parent.getRuleStatement(), node, name, type);
    }

    protected ParameterIdentifier(RuleExpression parent, Node node, String name, String type) {
        super(parent, node, name);
        this.type = type;
    }
}
