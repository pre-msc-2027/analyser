package org.premsc.analyser.rules;

import io.github.treesitter.jtreesitter.Language;
import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.tree.TreeHelperAbs;

import java.util.List;
import java.util.Map;

public interface IRule {

    int getId();
    String[] getTags();
    LanguageEnum getLanguage();
    Map<String, String> getParameters();

    boolean hasTag(String tag);

    List<Node> test(TreeHelperAbs treeHelper);
}
