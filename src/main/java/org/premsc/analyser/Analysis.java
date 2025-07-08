package org.premsc.analyser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.premsc.analyser.rules.Warning;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of an analysis performed by the Analyser.
 */
public class Analysis {

    public int total_files;
    public int files_with_warnings;
    public int warnings_found;

    public final List<Warning> warnings = new ArrayList<>();

    /**
     * Returns the output of the analysis in JSON format.
     * @return a JsonElement representing the analysis output
     */
    public JsonElement getOutput() {
        JsonObject root = new JsonObject();

        root.addProperty("status", "completed");

        JsonObject summary = new JsonObject();
        root.add("summary", summary);
        summary.addProperty("total_files", total_files);
        summary.addProperty("files_with_warnings", files_with_warnings);
        summary.addProperty("warnings_found", warnings_found);

        JsonArray warnings = new JsonArray();
        root.add("warnings", warnings);
        int warning_id = 0;
        for (Warning warning : this.warnings) {
            JsonObject warningObject = new JsonObject();
            warningObject.addProperty("id", warning_id++);
            warningObject.addProperty("rule_id", warning.rule().getId());
            warningObject.addProperty("file", warning.source().getFilepath());
            warningObject.addProperty("line", warning.line());
            warnings.add(warningObject);
        }

        return root;

    }

}
