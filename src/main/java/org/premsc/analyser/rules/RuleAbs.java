package org.premsc.analyser.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.premsc.analyser.Utils;
import org.premsc.analyser.parser.languages.LanguageEnum;

/**
 * Abstract base class for rules in the analysis.
 */
public abstract class RuleAbs implements IRule {

    private final String id;
    private final String[] tags;
    private final LanguageEnum language;
    private final RuleParameter[] parameters;

    public RuleAbs(JsonObject data) {
        this(
                data.get("rule_id").getAsString(),
                LanguageEnum.valueOf(data.get("language").getAsString().toUpperCase()),
                Utils.JsonArrayMapper(data.get("tags"), JsonElement::getAsString, String[]::new),
                Utils.JsonArrayMapper(data.get("parameters"), RuleParameter::new, RuleParameter[]::new)
        );
    }

    /**
     * Constructs a RuleAbs instance with specified parameters.
     *
     * @param id         The unique identifier for the rule.
     * @param language   The language associated with the rule.
     * @param tags       An array of tags associated with the rule.
     * @param parameters A map of parameters for the rule.
     */
    protected RuleAbs(String id, LanguageEnum language, String[] tags, RuleParameter[] parameters) {
        this.id = id;
        this.tags = tags;
        this.language = language;
        this.parameters = parameters;
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
     * @param key The key of the parameter to retrieve.
     * @return The value of the parameter as a String.
     * @throws UnknownParameter If the parameter with the specified key does not exist.
     */
    protected String getParameter(String key) throws UnknownParameter {
        for (RuleParameter parameter : this.parameters) {
            if (parameter.name().equalsIgnoreCase(key)) {
                return parameter.default_value();
            }
        }
        throw new UnknownParameter(key);
    }

    @Override
    public boolean hasTag(String tag) {

        for (String t : this.tags) {
            if (t.equalsIgnoreCase(tag)) return true;
        }

        return false;
    }


}
