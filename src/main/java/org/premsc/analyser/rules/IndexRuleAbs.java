package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.repository.ISource;

import java.util.List;

public abstract class IndexRuleAbs extends RuleAbs implements IIndexRule {

    /**
     * Constructor for QueryRuleAbs.
     * @param data A JsonObject containing the rule data, including parameters and language.
     */
    public IndexRuleAbs(JsonObject data) {
        super(data);
    }

    @Override
    abstract public List<Warning> test(DatabaseHandler handler, ISource source);
}
