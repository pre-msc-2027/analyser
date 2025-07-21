package org.premsc.analyser.rules;

import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.stream.Stream;

/**
 * Interface representing a query rule in the analysis framework.
 */
public interface IQueryRule extends IRule {

    /**
     * Tests the rule against a tree structure using the provided TreeHelper.
     *
     * @param treeHelper The TreeHelper instance to use for testing the rule.
     * @return A list of Node objects that match the rule criteria.
     */
    Stream<Warning> test(ITreeHelper treeHelper) throws UnsupportedLanguage;
}
