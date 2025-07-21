package org.premsc.analyser.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.premsc.analyser.Utils;
import org.premsc.analyser.rules.IRule;

/**
 * Configuration class for the Analyser application.
 * @param targetType the type of target to be indexed (repository, file, or file list)
 * @param repoUrl the URL of the repository to be indexed
 * @param targetFiles an array of target files to be indexed
 * @param minimumSeverity the minimum severity level for issues to be reported
 * @param branch the branch of the repository to be indexed
 * @param commit the specific commit to be indexed
 * @param useAiAssistance indicates whether AI assistance should be used in the analysis
 * @param maxDepth the maximum depth to traverse in the repository or file structure
 * @param followSymlinks indicates whether symbolic links should be followed during indexing
 */
public record Config(
        TargetType targetType,
        String repoUrl,
        String[] targetFiles,
        IRule.Severity minimumSeverity,
        String branch,
        String commit,
        boolean useAiAssistance,
        int maxDepth,
        boolean followSymlinks) {

    /**
     * Creates a Config object from a JSON object.
     * @param data the JSON object containing configuration data
     * @return a Config object populated with the data from the JSON object
     */
    public static Config fromJson(final JsonElement data) {

        JsonObject dataObject = data.getAsJsonObject();

        return new Config(
                TargetType.valueOf(dataObject.get("target_type").getAsString().toUpperCase()),
                dataObject.get("repo_url").getAsString(),
                Utils.JsonArrayMapper(dataObject.get("target_files"), JsonElement::getAsString, String[]::new),
                IRule.Severity.valueOf(dataObject.get("severity_min").getAsString().toUpperCase()),
                dataObject.get("branch").getAsString(),
                dataObject.get("commit").getAsString(),
                dataObject.get("use_ai_assistance").getAsBoolean(),
                dataObject.get("max_depth").getAsInt(),
                dataObject.get("follow_symlinks").getAsBoolean()
        );

    }
}
