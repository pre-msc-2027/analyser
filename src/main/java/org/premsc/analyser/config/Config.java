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

    public static Config fromJson(final JsonObject data) {

        return new Config(
                TargetType.valueOf(data.get("target_type").getAsString().toUpperCase()),
                data.get("repo_url").getAsString(),
                Utils.JsonArrayMapper(data.get("target_files"), JsonElement::getAsString, String[]::new),
                Severity.valueOf(data.get("severity_min").getAsString().toUpperCase()),
                data.get("branch").getAsString(),
                data.get("commit").getAsString(),
                data.get("use_ai_assistance").getAsBoolean(),
                data.get("max_depth").getAsInt(),
                data.get("follow_symlinks").getAsBoolean()
        );

    }
}
