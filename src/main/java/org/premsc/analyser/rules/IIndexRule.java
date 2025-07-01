package org.premsc.analyser.rules;

import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.repository.ISource;

import java.util.List;

/**
 * IIndexRule is an interface that extends the IRule interface.
 */
public interface IIndexRule extends IRule {

    /**
     * Tests the rule against a given source using the provided database handler.
     *
     * @param handler The database handler to use for accessing the database.
     * @param source  The source to test the rule against.
     * @return A stream of Warning objects representing any warnings found during the test.
     */
    List<Warning> test(DatabaseHandler handler, ISource source);
}
