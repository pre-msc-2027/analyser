package org.premsc.analyser.slang.generic;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.slang.tokens.ClauseField;
import org.premsc.analyser.slang.tokens.IndexClause;
import org.premsc.analyser.slang.tokens.NodeClause;
import org.premsc.analyser.slang.tokens.NodeIdentifier;

/**
 * IClauseTarget represents an identifier that can be used as a target in a clause.
 */
public interface IClauseTarget extends IIdentifier {

    /**
     * Factory method to create an IClauseTarget based on the parent clause type and syntax tree node.
     *
     * @param parent the parent clause
     * @param node   the syntax tree node representing the clause target
     * @param <P>    the type of the parent clause
     * @return the created IClauseTarget, or null if not applicable
     */
    static <P extends ClauseAbs<?>> IClauseTarget of(P parent, Node node) {

        if (parent instanceof IndexClause indexClause) {
            Node fieldNode = SlangObjectAbs.getChild(node, "field");
            if (fieldNode != null) return new ClauseField(indexClause, fieldNode);
            return null;
        }

        if (parent instanceof NodeClause nodeClause) {
            Node nodeNode = SlangObjectAbs.getChild(node, "node");
            if (nodeNode != null) return NodeIdentifier.of(nodeClause, nodeNode);
            return null;
        }

        throw new IllegalArgumentException("Unsupported parent type: " + parent.getClass().getName());
    }
}
