package org.premsc.analyser.parser.languages;

import io.github.treesitter.jtreesitter.Language;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;

/**
 * Abstract class for language helper implementations.
 */
public class LanguageHelper implements ILanguageHelper {

    protected final LanguageEnum language;
    protected final Language tsLanguage;

    /**
     * Constructor for the LanguageHelper class.
     * @param language the language enum representing the language to be used
     */
    protected LanguageHelper(LanguageEnum language) {

        this.language = language;
        this.tsLanguage = load(language);

    }

    @Override
    public LanguageEnum getLanguage() {
        return this.language;
    }

    @Override
    public Language getTsLanguage() {
        return this.tsLanguage;
    }

    /**
     * Load the Tree-sitter language from the specified library.
     * @param language the language enum representing the language to load
     * @return the loaded Tree-sitter language
     */
    private static Language load(LanguageEnum language) {
        String library = System.mapLibraryName(language.getLibraryName());
        SymbolLookup symbols = SymbolLookup.libraryLookup(library, Arena.global());
        return Language.load(symbols, language.getDllName());
    }
}
