package org.premsc.analyser.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public record RuleParameter(
        String name,
        String default_value
) {
    public RuleParameter(JsonElement data) {
        JsonObject obj = data.getAsJsonObject();
        this(
                obj.get("name").getAsString(),
                obj.get("default_value").getAsString()
        );
    }
}
