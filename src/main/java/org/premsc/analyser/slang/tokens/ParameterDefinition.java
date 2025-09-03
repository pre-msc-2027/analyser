package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.generic.SlangTokenAbs;

public class ParameterDefinition extends SlangTokenAbs<DefinitionStatement> {

    protected final ParameterIdentifier<ParameterDefinition> value;

    public ParameterDefinition(DefinitionStatement parent, Node node) {
        super(parent, node);
        value = new ParameterIdentifier(this, getNode(node, "identifier"));
        dataType = new DataType(this, node);
        this.getRuleStatement().addIdentifier()
    }
}
