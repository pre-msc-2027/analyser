package org.premsc.analyser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.premsc.analyser.rules.Warning;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of an analysis performed by the Analyser.
 */
public class Analysis implements IHasModule {

    /**
     * Summary of the analysis results.
     */
    public final Summary summary = new Summary();
    /**
     * List of warnings found during the analysis.
     */
    @JsonProperty("warnings")
    public final List<Warning> warnings = new ArrayList<>();
    /**
     * List of vulnerabilities found during the analysis (currently unused).
     */
    @SuppressWarnings("unused")
    @JsonProperty("vulnerabilities")
    public final List<Warning> vulnerabilities = new ArrayList<>();
    /**
     * Status of the analysis (e.g., "completed").
     */
    @SuppressWarnings("unused")
    @JsonProperty("status")
    public String status = "completed";

    @Override
    public Warning.WarningModule getModule() {
        return new Warning.WarningModule();
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(this.getModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Summary class representing a summary of the analysis results.
     */
    public static class Summary {

        /**
         * Total number of files analyzed.
         */
        @JsonProperty("total_files")
        public int totalFiles;

        /**
         * Number of files with warnings.
         */
        @JsonProperty("files_with_vulnerabilities")
        public int filesWithWarnings;

        /**
         * Total number of warnings found.
         */
        @JsonProperty("vulnerabilities_found")
        public int warningsFound;

    }
}
