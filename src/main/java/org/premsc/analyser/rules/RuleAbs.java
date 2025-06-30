package org.premsc.analyser.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.TreeHelperAbs;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class RuleAbs implements IRule {

    private static final Function<Map.Entry<String, JsonElement>, String> ParamMapper = (Map.Entry<String, JsonElement> entry) -> entry.getValue().getAsString();

    private final int id;
    private final String[] tags;
    private final LanguageEnum language;
    private final Map<String, String> parameters;

    public RuleAbs(JsonObject data) {
        this(
                data.get("id").getAsInt(),
                LanguageEnum.valueOf(data.get("language").getAsString()),
                data.get("tags").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toArray(String[]::new),
                data.get("parameters").getAsJsonObject().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ParamMapper))
        );
    }

    protected RuleAbs(int id, LanguageEnum language, String[] tags, Map<String, String> parameters) {
        this.id = id;
        this.tags = tags;
        this.language = language;
        this.parameters = parameters;
    }

    @Override
    public int getId() {
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
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    protected String getParameter(String key) {
        if (!this.parameters.containsKey(key)) throw new UnknownParameter(key);
        return this.parameters.get(key);
    }

    @Override
    public boolean hasTag(String tag) {

        for (String t : this.tags) {
            if (t.equalsIgnoreCase(tag)) return true;
        }

        return false;
    }


}
