package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.IClauseValue;
import org.premsc.analyser.slang.generic.ISlangObject;
import org.premsc.analyser.slang.generic.IdentifierAbs;

/**
 * Represents a parameter identifier in the slang language.
 */
public class ParameterIdentifier extends IdentifierAbs implements IClauseValue {

    /**
     * Constructor for ParameterIdentifier.
     *
     * @param parent the parent rule expression
     * @param node   the syntax tree node
     * @param name   the name of the parameter identifier
     */
    protected ParameterIdentifier(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    /**
     * Factory method to create or retrieve a ParameterIdentifier instance.
     *
     * @param parent the parent slang object
     * @param node   the syntax tree node
     * @param <P>    the type of the parent slang object
     * @return a ParameterIdentifier instance
     */
    public static <P extends ISlangObject> ParameterIdentifier of(P parent, Node node) {
        RuleExpression ruleExpression = parent.getRuleExpression();
        String name = node.getText();
        ParameterIdentifier parameterIdentifier = ruleExpression.getParameterIdentifier(name);
        if (parameterIdentifier != null) return parameterIdentifier;
        parameterIdentifier = new ParameterIdentifier(parent.getRuleExpression(), node, name);
        ruleExpression.addIdentifier(parameterIdentifier);
        return parameterIdentifier;
    }

    @Override
    public String getValue() {
        return this.getRuleExpression().getRule().getParameter(name);
    }
}
