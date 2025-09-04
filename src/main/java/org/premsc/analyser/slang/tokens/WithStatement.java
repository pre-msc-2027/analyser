package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.IndexStatementAbs;

public class WithStatement extends IndexStatementAbs<IndexStatement<?>> {

    public WithStatement(IndexStatement<?> parent, Node node) {
        super(parent, node);
    }


    public Selector<?> getJoint() {
        return Selector
                .of("index_table")
                .addColumn("value")
                .setPredicate(getSelectorPredicate());
    }

    @Override
    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {}

}
