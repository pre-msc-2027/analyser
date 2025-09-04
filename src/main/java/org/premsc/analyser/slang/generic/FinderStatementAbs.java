package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.tokens.Branch;
import org.premsc.analyser.slang.tokens.WhereStatement;

import java.util.ArrayList;
import java.util.List;

public abstract class FinderStatementAbs<P extends IFinderParent> extends SlangTokenAbs<P> {

    protected final WhereStatement<FinderStatementAbs<P>> whereStatement;

    protected FinderStatementAbs(P parent, Node node) {
        super(parent, node);
        this.whereStatement = initWhereStatement(node);
    }

    private WhereStatement<FinderStatementAbs<P>> initWhereStatement(Node node) {
        Node whereNode = getChild(node, "where");
        if (whereNode != null) return new WhereStatement<>(this, whereNode);
        return null;
    }

    public abstract void execute(DatabaseHandler handler, ITreeHelper treeHelper);
}
