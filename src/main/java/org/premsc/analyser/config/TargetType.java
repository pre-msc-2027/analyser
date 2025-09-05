package org.premsc.analyser.config;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Enum representing the types of targets that can be indexed.
 * This is used to categorize the targets in the indexing process.
 */
public enum TargetType {

    @JsonAlias({"repository", "REPOSITORY"})
    REPOSITORY,
    @JsonAlias({"file", "FILE"})
    FILE,
    @JsonAlias({"file_list", "FILE_LIST"})
    FILE_LIST

}
