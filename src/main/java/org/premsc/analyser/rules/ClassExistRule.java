package org.premsc.analyser.rules;

import com.google.gson.JsonObject;
import org.premsc.analyser.db.selector.Predicate;
import org.premsc.analyser.db.selector.Selector;
import org.premsc.analyser.repository.ISource;


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
    protected Selector<?> getSelector(ISource source) {
        return Selector
                .of("index_table")
                .setPredicate(Predicate
                        .equal("source", source.getFilepath())
                        .and(Predicate.equal("type", "class"))
                        .and(Predicate.in("value",
                                Selector.of("index_table")
                                        .addColumn("value")
                                        .setPredicate(Predicate
                                                .equal("type", "class")
                                                .and(Predicate.in("source",
                                                        Selector.of("index_table")
                                                                .addColumn("value")
                                                                .setPredicate(Predicate
                                                                        .equal("type", "link_stylesheet")
                                                                        .and(Predicate.equal("source", source.getFilepath()))
                                                                ))))
                        ).not())
                );
    }
}
