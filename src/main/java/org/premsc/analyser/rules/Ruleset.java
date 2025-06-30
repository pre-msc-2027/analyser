package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.AnalyserApplication;
import org.premsc.analyser.parser.languages.LanguageEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Ruleset {

    private final AnalyserApplication app;
    private final List<IRule> rules = new ArrayList<>();

    public Ruleset(AnalyserApplication app) {
        this.app = app;
    }

    public void init() {

        JsonObject data = this.app.getApi().get("rules").getAsJsonObject();

        data.getAsJsonArray("rules")
                .forEach(ruleElement -> this.rules.add(new CasingRule(ruleElement.getAsJsonObject())));
    }

    public Stream<IRule> stream() {
        return this.rules.stream();
    }

}
