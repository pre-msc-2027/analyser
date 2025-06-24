package org.premsc.analyser.parser.languages;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating instances of LanguageHelper based on the specified language or file extension.
 */
public class LanguageHelperFactory {

    static private final Map<LanguageEnum, ILanguageHelper> instances = new HashMap<>();

    /**
     * Returns the instance of ILanguageHelper for the specified language.
     *
     * @param language the language enum representing the language to from
     * @return the instance of ILanguageHelper for the specified language
     */
    static public ILanguageHelper get(LanguageEnum language) {
        if (!instances.containsKey(language)) instances.put(language, getInstance(language));
        return instances.get(language);
    }

    /**
     * Returns the instance of ILanguageHelper for the specified file extension.
     *
     * @param extension the file extension to from the language helper for
     * @return the instance of ILanguageHelper for the specified file extension
     * @throws UnsupportedLanguage if the extension is not supported
     */
    static public ILanguageHelper get(String extension) throws UnsupportedLanguage {
        return get(LanguageEnum.getByExtension(extension));
    }

    /**
     * Returns the instance of ILanguageHelper for the specified language.
     * @param language the language enum representing the language to from
     * @return the instance of ILanguageHelper for the specified language
     */
    static private ILanguageHelper getInstance(LanguageEnum language) {

        return switch (language) {
            case HTML -> null;
        };

    }
}
