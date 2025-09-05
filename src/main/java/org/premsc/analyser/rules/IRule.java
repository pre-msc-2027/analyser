package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.slang.tokens.RuleExpression;

import java.util.function.Predicate;

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
    String getId();

    /**
     * Returns the language associated with the rule.
     * @return The LanguageEnum representing the language.
     */
    LanguageEnum getLanguage();

    /**
     * Returns the parameters associated with the rule.
     *
     * @return A map of parameter names to their values.
     */
    RuleParameter[] getParameters();

    /**
     * Returns the slang expression defining the rule.
     * @return The slang expression as a string.
     */
    String getSlang();

    String getParameter(String key) throws UnknownParameter;

    RuleExpression getExpression();

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

    enum Severity {
        @JsonAlias({"low","LOW"})
        LOW,
        @JsonAlias({"medium","MEDIUM"})
        MEDIUM,
        @JsonAlias({"high","HIGH"})
        HIGH,
        @JsonAlias({"critical","CRITICAL"})
        CRITICAL
    }
}
