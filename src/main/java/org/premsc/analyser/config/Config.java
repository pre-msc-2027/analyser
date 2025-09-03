package org.premsc.analyser.config;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("target_type") TargetType targetType,
        @JsonProperty("repo_url") String repoUrl,
        @JsonProperty("target_files") String[] targetFiles,
        @JsonProperty("severity_min") IRule.Severity minimumSeverity,
        @JsonProperty("branch_id") String branch,
        @JsonProperty("commit_hash") String commit,
        @JsonProperty("use_ai_assistance") boolean useAiAssistance,
        @JsonProperty("max_depth") int maxDepth,
        @JsonProperty("follow_symlinks") boolean followSymlinks
) {}
