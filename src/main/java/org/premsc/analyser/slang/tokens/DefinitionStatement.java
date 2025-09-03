package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStatement extends SlangTokenAbs<RuleExpression> {

    protected final ParameterDefinition[] parameterDefinitions;

    public DefinitionStatement(RuleExpression parent, Node node) {
        super(parent, node);

        List<ParameterDefinition> parameterDefinitionList = new ArrayList<>();
        for (Node child : getNodes(node, "parameter")) {
            parameterDefinitionList.add(new ParameterDefinition(this, child));
        }
        parameterDefinitions = parameterDefinitionList.toArray(ParameterDefinition[]::new);
    }
}
