package org.premsc.analyser.indexer;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * IndexerManager is responsible for managing different indexers.
 */
public class IndexerManager {

    private final IIndexer[] indexers = new IIndexer[] {
            new HtmlLinkStylesheetIndexer(),
            new HtmlClassIndexer(),
            new CssClassIndexer(),
    };

    /**
     * Returns the array of indexers managed by this IndexerManager.
     * @return an array of IIndexer instances
     */
    public IIndexer[] getIndexers() {
        return indexers;
    }

    /**
     * Represents an indexed entry with its value, line number, start byte, and end byte.
     */
    public record Index(String value, int line, int startByte, int endByte) {
        public static Index[] of(String value, int line, int startByte, int endByte) {
            return new Index[] {new Index(value, line, startByte, endByte)};
        }
    }

    /**
     * Indexer for HTML link elements with rel="stylesheet".
     */
    public static class HtmlLinkStylesheetIndexer extends QueryIndexerAbs {


        public HtmlLinkStylesheetIndexer() {
            super(LanguageEnum.HTML, "link_stylesheet");
        }
        @Override
        public QueryBuilder<?> getQuery() {
            return QueryBuilder
                    .of("element")
                    .addChild(QueryBuilder
                            .of("start_tag")
                            .addChild("tag_name", "tag.name")
                            .addChild(QueryBuilder
                                    .of("attribute")
                                    .addChild("attribute_name", "attribute1.name")
                                    .addChild(QueryBuilder
                                            .of("quoted_attribute_value")
                                            .addChild("attribute_value", "attribute1.value")))
                            .addChild(QueryBuilder
                                    .of("attribute")
                                    .addChild("attribute_name", "attribute2.name")
                                    .addChild(QueryBuilder
                                            .of("quoted_attribute_value")
                                            .addChild("attribute_value", "target"))))
                    .equal("tag.name", "link")
                    .equal("attribute1.name", "rel")
                    .equal("attribute1.value", "stylesheet")
                    .equal("attribute2.name", "href");
        }

    }

    /**
     * Indexer for HTML class attributes.
     */
    public static class HtmlClassIndexer extends QueryIndexerAbs {

        public HtmlClassIndexer() {
            super(LanguageEnum.HTML, "class");
        }

        @Override
        public QueryBuilder<?> getQuery() {
            return QueryBuilder
                    .of("element")
                    .addChild(QueryBuilder
                            .of("start_tag")
                            .addChild(QueryBuilder
                                    .of("attribute")
                                    .addChild("attribute_name", "attribute.name")
                                    .addChild(QueryBuilder
                                            .of("quoted_attribute_value")
                                            .addChild("attribute_value", "target"))))
                    .equal("attribute.name", "class");
        }

        @Override
        public List<Index> index(ITreeHelper treeHelper, String value, int line, int startByte, int endByte) {

            List<Index> indexes = new ArrayList<>();

            for (String className : value.split("\\s+")) {
                indexes.addAll(super.index(treeHelper, className, line, startByte, endByte));
            }

            return indexes;

        }

    }

    /**
     * Indexer for CSS class selectors.
     */
    public static class CssClassIndexer extends QueryIndexerAbs {

        public CssClassIndexer() {
            super(LanguageEnum.CSS, "class");
        }

        @Override
        public QueryBuilder<?> getQuery() {
            return QueryBuilder
                    .of("rule_set")
                    .addChild(QueryBuilder
                        .of("selectors")
                        .addChild(QueryBuilder
                            .of("class_selector")
                            .addChild(QueryBuilder
                                .of("class_name")
                                .addChild("identifier", "target"))));
        }

    }
}
