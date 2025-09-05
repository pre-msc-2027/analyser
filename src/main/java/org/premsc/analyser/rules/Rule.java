package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.slang.tokens.RuleExpression;

/**
 * Abstract base class for rules in the analysis.
 */
@JsonIgnoreProperties({"name", "description", "tags"})
public class Rule implements IRule {

    private final String id;
    private final LanguageEnum language;
    private final RuleParameter[] parameters;
    private final String slang;
    private final RuleExpression expression;

    /**
     * Constructs a Rule instance with specified parameters.
     *
     * @param id         The unique identifier for the rule.
     * @param language   The language associated with the rule.
     * @param parameters A map of parameters for the rule.
     * @param slang      The slang expression defining the rule.
     */
    @JsonCreator
    protected Rule(
            @JsonProperty("rule_id") String id,
            @JsonProperty("language") LanguageEnum language,
            @JsonProperty("parameters") RuleParameter[] parameters,
            @JsonProperty("slang") String slang
    ) {
        this.id = id;
        this.language = language;
        this.parameters = parameters;
        this.slang = slang;
        this.expression = new RuleExpression(this);
    }

    @Override
    public String getId() {
        return this.id;
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
    public String getSlang() {
        return slang;
    }
}
