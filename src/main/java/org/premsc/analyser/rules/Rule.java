package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.slang.tokens.RuleExpression;

/**
 * Abstract base class for rules in the analysis.
 */
public class Rule implements IRule {

    private final String name;
    private final String id;
    private final String[] tags;
    private final LanguageEnum language;
    private final RuleParameter[] parameters;
    private final String slang;
    private final RuleExpression expression;

    /**
     * Constructs a Rule instance with specified parameters.
     *
     * @param id         The unique identifier for the rule.
     * @param language   The language associated with the rule.
     * @param tags       An array of tags associated with the rule.
     * @param parameters A map of parameters for the rule.
     * @param slang      The slang expression defining the rule.
     */
    protected Rule(
            @JsonProperty("name") String name,
            @JsonProperty("rule_id") String id,
            @JsonProperty("language") LanguageEnum language,
            @JsonProperty("tags") String[] tags,
            @JsonProperty("parameters") RuleParameter[] parameters,
            @JsonProperty("slang") String slang
    ) {
        this.name = name;
        this.id = id;
        this.tags = tags;
        this.language = language;
        this.parameters = parameters;
        this.slang = slang;
        this.expression = new RuleExpression(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String[] getTags() {
        return this.tags;
    }

    @Override
    public LanguageEnum getLanguage() {
        return this.language;
    }

    @Override
    public RuleParameter[] getParameters() {
        return this.parameters;
    }

    /**
     * Retrieves the value of a specific parameter by its key.
     *
     * @param key The key of the parameter to retrieve.
     * @return The value of the parameter as a String.
     * @throws UnknownParameter If the parameter with the specified key does not exist.
     */
    @Override
    public String getParameter(String key) throws UnknownParameter {
        for (RuleParameter parameter : this.parameters) {
            if (parameter.name().equalsIgnoreCase(key)) {
                return parameter.default_value();
            }
        }
        throw new UnknownParameter(key);
    }

    @Override
    public RuleExpression getExpression() {
        return this.expression;
    }

    @Override
    public boolean hasTag(String tag) {

        for (String t : this.tags) {
            if (t.equalsIgnoreCase(tag)) return true;
        }

        return false;
    }


    @Override
    public String getSlang() {
        return slang;
    }
}
