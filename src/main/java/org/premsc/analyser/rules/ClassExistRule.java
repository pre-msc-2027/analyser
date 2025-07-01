package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.db.IndexModel;
import org.premsc.analyser.repository.ISource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClassExistRule extends IndexRuleAbs {

    /**
     * Constructor for QueryRuleAbs.
     *
     * @param data A JsonObject containing the rule data, including parameters and language.
     */
    public ClassExistRule(JsonObject data) {
        super(data);
    }

    @Override
    public List<Warning> test(DatabaseHandler handler, ISource source) throws SQLException {

        List<String> cssSources = new ArrayList<>();

        for (IndexModel.Index index: handler.getIndexModel().getWithType(source, "link_stylesheet")) {
            cssSources.add(index.value());
        }

        if (cssSources.isEmpty()) return List.of();

        String cssSourcesInClause = String.join(",", "'%s'".formatted(cssSources));

        List<Warning> warnings = new ArrayList<>();

        for (IndexModel.Index index: handler.getIndexModel().getWithType(source, "class")) {

            String queryCss = """
                        SELECT EXISTS(SELECT 1 FROM index_table
                        WHERE source IN (%s)
                        AND language = 'CSS' AND type = 'class' AND value = '%s');
                        """.formatted(cssSourcesInClause, index.value());

            try (Statement cssCheck = handler.getConnection().createStatement()) {
                ResultSet rs = cssCheck.executeQuery(queryCss);
                if (rs.next() && rs.getInt(1) == 0) {
                    warnings.add(new Warning(this, source, index));
                }
            }

        }

        return warnings;

    }

}
