package org.premsc.analyser.parser.languages;

import io.github.treesitter.jtreesitter.Language;

public interface ILanguageHelper {

    /**
     * Get the LanguageEnum representing with this helper.
     * @return the LanguageEnum representing the language
     */
    LanguageEnum getLanguage();

    /**
     * Get the TreeSitter Language object associated with this helper.
     * @return the TreeSitter Language object
     */
    Language getTsLanguage();

}
