package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.parser.queries.QueryHelper;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface IQueryRule extends IRule {

    /**
     * Tests the rule against a tree structure using the provided TreeHelper.
     *
     * @param treeHelper The TreeHelper instance to use for testing the rule.
     * @return A list of Node objects that match the rule criteria.
     */
    Stream<Warning> test(ITreeHelper treeHelper);
}
