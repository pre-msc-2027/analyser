package org.premsc.analyser.rules;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    class LanguagePredicate implements Predicate<IRule> {

        private final LanguageEnum language;

        public LanguagePredicate(LanguageEnum language) {
            this.language = language;
        }
        @Override
        public boolean test(IRule rule) {
            return rule.getLanguage() == this.language;
        }
    }

}
