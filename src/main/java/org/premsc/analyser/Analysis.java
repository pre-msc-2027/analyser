package org.premsc.analyser;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.premsc.analyser.rules.Warning;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of an analysis performed by the Analyser.
 */
public class Analysis implements IHasModule{

    @JsonProperty("total_files")
    public int totalFiles;

    @JsonProperty("files_with_warnings")
    public int filesWithWarnings;

    @JsonProperty("warnings_found")
    public int warningsFound;

    public final List<Warning> warnings = new ArrayList<>();

    @Override
    public Warning.WarningModule getModule() {
        return new Warning.WarningModule();
    }
}
