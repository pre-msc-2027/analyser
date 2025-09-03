package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.rules.IRule;
import org.premsc.analyser.slang.generic.ISlangFindParent;
import org.premsc.analyser.slang.generic.IdentifierAbs;
import org.premsc.analyser.slang.generic.SlangObjectAbs;

import java.util.ArrayList;
import java.util.List;

public class RuleExpression extends SlangObjectAbs implements ISlangFindParent {

    protected final IRule rule;
    protected final List<IdentifierAbs<?>> identifiers;
    protected final DefinitionStatement definitionStatement;
    protected final FindStatement findStatement;

    public RuleExpression(IRule rule, Node node) {
        super(node);

        this.rule = rule;
        identifiers = new ArrayList<>();

        Node definitionNode = getNode(node, "definition");
        definitionStatement = (definitionNode!=null)?new DefinitionStatement(this, definitionNode):null;

        Node findNode = getNode(node, "find");
        findStatement = (findNode!=null)?new FindStatement(this, findNode):null;
    }

    @Override
    public RuleExpression getRuleStatement() {
        return this;
    }

    public void addIdentifier(IdentifierAbs<?> identifier) {
        this.identifiers.add(identifier);
    }

    public ParameterIdentifier getParameter(String name) {
        return this.identifiers
                .stream()
                .filter(ParameterIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(ParameterIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

}
