package org.premsc.analyser.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.premsc.analyser.Utils;
import org.premsc.analyser.rules.Severity;

public record Config(
        TargetType targetType,
        String repoUrl,
        String[] targetFiles,
        Severity minimumSeverity,
        String branch,
        String commit,
        boolean useAiAssistance,
        int maxDepth,
        boolean followSymlinks) {

    public static Config fromJson(final JsonElement data) {

        JsonObject scan_options = data.getAsJsonObject().getAsJsonObject("properties");

        return new Config(
                TargetType.valueOf(scan_options.get("targetType").getAsString()),
                scan_options.get("repoUrl").getAsString(),
                Utils.JsonArrayMapper(scan_options.get("targetFiles"), JsonElement::getAsString, String[]::new),
                Severity.valueOf(scan_options.get("minimumSeverity").getAsString()),
                scan_options.get("branch").getAsString(),
                scan_options.get("commit").getAsString(),
                scan_options.get("useAiAssistance").getAsBoolean(),
                scan_options.get("maxDepth").getAsInt(),
                scan_options.get("followSymlinks").getAsBoolean()
        );

    }
}
