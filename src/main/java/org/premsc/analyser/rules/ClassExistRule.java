package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.db.DatabaseHandler;
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
    public List<Warning> test(DatabaseHandler handler, ISource source) {

        List<Warning> warnings = new ArrayList<>();

        String queryImport = """
                SELECT value FROM index_table
                WHERE source = '%s' AND language = 'HTML' AND type = 'link_stylesheet';
                """.formatted(source.getFilepath());

        List<String> cssSources = new ArrayList<>();

        try (Statement statement = handler.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(queryImport);
            while (result.next()) {
                cssSources.add("'" + result.getString("value") + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (cssSources.isEmpty()) return List.of();

        String cssSourcesInClause = String.join(",", cssSources);

        String queryHtml = """
                SELECT * FROM index_table
                WHERE source = '%s' AND language = 'HTML' AND type = 'class';
                """.formatted(source.getFilepath());

        try (Statement statement = handler.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(queryHtml);
            while (result.next()) {
                String className = result.getString("value");

                String queryCss = """
                        SELECT EXISTS(SELECT 1 FROM index_table
                        WHERE source IN (%s)
                        AND language = 'CSS' AND type = 'class' AND value = '%s');
                        """.formatted(cssSourcesInClause, className);

                try (Statement cssCheck = handler.getConnection().createStatement()) {
                    ResultSet rs = cssCheck.executeQuery(queryCss);
                    if (rs.next() && rs.getInt(1) == 0) {
                        warnings.add(new Warning(this, source, handler.getIndexModel().fromResult(result)));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return warnings;

    }

}
