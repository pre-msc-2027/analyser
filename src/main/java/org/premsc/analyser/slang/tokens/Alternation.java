package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.queries.builder.IQueryBuilderParent;
import org.premsc.analyser.parser.queries.builder.QueryBuilderAlternation;
import org.premsc.analyser.slang.generic.BranchAbs;
import org.premsc.analyser.slang.generic.ISlangBranchParent;

public class Alternation<P extends ISlangBranchParent> extends BranchAbs<P> implements ISlangBranchParent {

    public Alternation(P parent, Node node) {
        super(parent, node);
    }

    @Override
    public IQueryBuilderParent<?> build() {
        QueryBuilderAlternation builder = new QueryBuilderAlternation();
        for (BranchAbs<?> branch : this.branches) builder.addChild(branch.build());
        return builder;
    }
}
