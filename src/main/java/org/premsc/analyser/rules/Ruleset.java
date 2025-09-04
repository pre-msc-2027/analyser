package org.premsc.analyser.rules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.premsc.analyser.AnalyserApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Ruleset class that manages a collection of rules for analysis.
 */
public class Ruleset {

    private final AnalyserApplication app;
    private final List<IRule> rules = new ArrayList<>();

    /**
     * Constructor for Ruleset.
     * @param app the AnalyserApplication instance
     */
    public Ruleset(AnalyserApplication app) {
        this.app = app;
    }

    /**
     * Returns the list of rules defined in the ruleset.
     * @return a list of IRule objects
     */
    public List<IRule> getRules() {
        return rules;
    }

    /**
     * Initializes the ruleset by fetching rules from the API and populating the rules list.
     */
    public void init() throws Exception {

        JsonArray rules = this.app.getApi().get("rules/by_scan").getAsJsonArray();

        for (JsonElement ruleElement : rules) {
            JsonObject rule = ruleElement.getAsJsonObject();
            this.rules.add(new Rule(rule));
        }
    }

    /**
     * Streams the rules defined in the ruleset.
     * @return a Stream of IRule objects
     */
    public Stream<IRule> stream() {
        return this.rules.stream();
    }

}
