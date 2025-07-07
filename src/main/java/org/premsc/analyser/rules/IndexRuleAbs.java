package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.repository.ISource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing an index rule.
 */
public abstract class IndexRuleAbs extends RuleAbs implements IIndexRule {

    /**
     * Constructor for QueryRuleAbs.
     * @param data A JsonObject containing the rule data, including parameters and language.
     */
    public IndexRuleAbs(JsonObject data) {
        super(data);
    }

    /**
     * Returns the SQL selector query for this rule.
     *
     * @param source The source to be used in the query.
     * @return A String representing the SQL selector query.
     */
    abstract protected Selector<?> getSelector(ISource source);

    @Override
    public List<Warning> test(DatabaseHandler handler, ISource source) throws SQLException {

        List<Warning> warnings = new ArrayList<>();

        for (IndexModel.Index index : handler.getIndexModel().queryMultiple(this.getSelector(source))) {
            warnings.add(new Warning(this, source, index));
        }

        return warnings;

    }
}
