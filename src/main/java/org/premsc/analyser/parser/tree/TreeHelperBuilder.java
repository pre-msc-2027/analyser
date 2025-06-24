package org.premsc.analyser.parser.tree;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.html.HTMLTree;
import org.premsc.analyser.repository.Source;

public class TreeHelperBuilder {

    static public ITreeHelper from(Source source) {

        LanguageEnum language;

        try {
            language = LanguageEnum.getByExtension(source.getExtension());
        } catch (UnsupportedLanguage e) {
            throw new RuntimeException(e);
        }

        return switch (language) {
            case HTML -> new HTMLTree(source);
        };

    }

}
