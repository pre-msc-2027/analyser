package org.premsc.analyser.rules;

import com.google.gson.JsonElement;

public record RuleParameter(
        String name,
        String default_value
) {
    public RuleParameter(JsonElement data) {
        this(
                data.getAsJsonObject().get("name").getAsString(),
                data.getAsJsonObject().get("default").getAsString()
        );
    }
}
