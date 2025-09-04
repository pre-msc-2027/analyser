package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.db.selector.SelectorPredicateAbs;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.slang.generic.ClauseAbs;
import org.premsc.analyser.slang.generic.FinderStatementAbs;
import org.premsc.analyser.slang.generic.IFinderParent;
import org.premsc.analyser.slang.generic.IndexStatementAbs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndexStatement<P extends IFinderParent> extends IndexStatementAbs<P> implements IFinderParent {

    protected final WithStatement[] withStatements;

    public IndexStatement(P parent, Node node) {
        super(parent, node);
        this.withStatements = initWithStatement(node);
    }

    public Selector<?> getJoint(IndexIdentifier indexIdentifier) {
        for (WithStatement withStatement : withStatements) {
            IndexStatement<?> indexStatement = withStatement.getParent();
            if (indexStatement.getIndexIdentifier().getName().equals(indexIdentifier.getName())) {
                return indexStatement.getSelector();
            }
        }
        return null;
    }

    protected WithStatement[] initWithStatement(Node node) {
        List<WithStatement> withStatementList = new ArrayList<>();
        for (Node child: getChildren(node, "with")) {
            withStatementList.add(new WithStatement(this, child));
        }
        return withStatementList.toArray(WithStatement[]::new);
    }

    public IndexIdentifier getIndexIdentifier() {
        return indexIdentifier;
    }

    public void execute(DatabaseHandler handler, ITreeHelper treeHelper) {
        try {
            for (IndexModel.Index index : handler.getIndexModel().queryMultiple(this.getSelector())) {
                indexIdentifier.addCapture(index);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
