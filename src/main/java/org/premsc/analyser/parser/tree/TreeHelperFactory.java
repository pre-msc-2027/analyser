package org.premsc.analyser.parser.tree;

import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.html.HTMLTree;
import org.premsc.analyser.repository.Source;

/**
 * Factory class for creating instances of ITreeHelper based on the specified source.
 */
public class TreeHelperFactory {

    /**
     * Returns an instance of ITreeHelper for the specified source.
     * @param source the source to create the tree helper for
     * @return an instance of ITreeHelper for the specified source
     * @throws RuntimeException if the language is unsupported
     */
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
