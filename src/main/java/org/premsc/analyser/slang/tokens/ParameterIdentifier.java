package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.IdentifierAbs;

public class ParameterIdentifier extends IdentifierAbs implements IClauseValue {

    public static <P extends ISlangObject> ParameterIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        ParameterIdentifier parameterIdentifier = ruleExpression.getParameterIdentifier(name);
        if (parameterIdentifier != null) return parameterIdentifier;
        parameterIdentifier = new ParameterIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(parameterIdentifier);
        return parameterIdentifier;
    }

    protected ParameterIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    @Override
    public String getValue() {
        return this.getRuleExpression().getRule().getParameter(name);
    }
}
