package org.premsc.analyser.parser.tree.html;

import org.premsc.analyser.parser.tree.TreeHelperAbs;
import org.premsc.analyser.repository.Source;

/**
 * Class representing a tree helper for HTML parsing.
 * It extends the TreeHelperAbs class and provides functionality specific to HTML.
 */
public class HTMLTree extends TreeHelperAbs {

    /**
     * Constructor for the HTMLTree class.
     * It initializes the tree helper with the given source.
     *
     * @param source the source to create the tree helper for
     */
    public HTMLTree(Source source) {
        super(source);
    }

}
