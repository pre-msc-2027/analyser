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
public class Analysis implements IHasModule{

    public final Summary summary = new Summary();

    @JsonProperty("status")
    public String status = "completed";

    @JsonProperty("warnings")
    public final List<Warning> warnings = new ArrayList<>();

    @JsonProperty("vulnerabilities")
    public final List<Warning> vulnerabilities = new ArrayList<>();

    @Override
    public Warning.WarningModule getModule() {
        return new Warning.WarningModule();
    }

    public static class Summary {

        @JsonProperty("total_files")
        public int totalFiles;

        @JsonProperty("files_with_vulnerabilities")
        public int filesWithWarnings;

        @JsonProperty("vulnerabilities_found")
        public int warningsFound;

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
}
