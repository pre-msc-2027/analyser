package org.premsc.analyser.rules;

import io.github.treesitter.jtreesitter.Language;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.TreeHelperAbs;

import java.util.List;
import java.util.Map;

/**
 * Interface for defining rules in the analysis.
 * Each rule has an ID, associated tags, a language, and parameters.
 * The rule can be tested against a tree structure using the provided TreeHelper.
 */
public interface IRule {

    /**
     * Returns the unique identifier for the rule.
     * @return The ID of the rule.
     */
    int getId();

    /**
     * Returns the language associated with the rule.
     * @return The LanguageEnum representing the language.
     */
    LanguageEnum getLanguage();

    /**
     * Returns the tags associated with the rule.
     * @return An array of tags.
     */
    String[] getTags();

    /**
     * Returns the parameters associated with the rule.
     * @return A map of parameter names to their values.
     */
    Map<String, String> getParameters();

    /**
     * Checks if the rule has a specific tag.
     * @param tag The tag to check for.
     * @return True if the rule has the specified tag, false otherwise.
     */
    boolean hasTag(String tag);

    /**
     * Tests the rule against a tree structure using the provided TreeHelper.
     * @param treeHelper The TreeHelper instance to use for testing the rule.
     * @return A list of Node objects that match the rule criteria.
     */
    List<Node> test(TreeHelperAbs treeHelper);
}
