package org.premsc.analyser.parser.queries.html;

import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.queries.QueryFactoryAbs;
import org.premsc.analyser.rules.Casing;

public class HTMLQueryFactory extends QueryFactoryAbs {

    protected HTMLQueryFactory(QueryBuilder<?> query) {
        super(query);
    }

    public HTMLQueryFactory checkCasing(Casing casing) {
        this.query.notMatch("target", casing.getRegex());
        return this;
    }

    static public HTMLQueryFactory allTagNames() {
        QueryBuilder<?> query =
                QueryBuilder
                        .of("element")
                        .addAlternation(
                                QueryBuilder
                                        .of("start_tag")
                                        .addChild("tag_name", "target"),
                                QueryBuilder
                                        .of("end_tag")
                                        .addChild("tag_name", "target")
                        );
        return new HTMLQueryFactory(query);
    }

    static public HTMLQueryFactory allWithAttributeName(String attributeName) {
        QueryBuilder<?> query =
                QueryBuilder
                        .of("element")
                        .addChild(QueryBuilder
                                .of("start_tag")
                                .addChild(QueryBuilder
                                        .of("attribute")
                                        .addChild(QueryBuilder
                                                .of("attribute_name", "attribute.name")
                                                .equal(attributeName))
                                        .addChild(QueryBuilder
                                                .of("quoted_attribute_value")
                                                .addChild("attribute_value", "target")
                                        )
                                )
                        );
        return new HTMLQueryFactory(query);
    }



}
