package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.rules.Warning;
import org.premsc.analyser.slang.tokens.RuleExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class TargetIdentifierAbs extends IdentifierAbs {

    protected TargetIdentifierAbs(RuleExpression parent, Node node, String name) {
        super(parent, node, name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public boolean isTarget() {
        return Objects.equals(this.name, "target");
    }

    public abstract List<Warning> getResults(ITreeHelper treeHelper);
}
