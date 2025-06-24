package org.premsc.analyser.parser.languages;

import io.github.treesitter.jtreesitter.Language;

import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;


public abstract class LanguageHelperAbs implements ILanguageHelper {

    protected final LanguageEnum language;
    protected final Language tsLanguage;

    protected LanguageHelperAbs(LanguageEnum language) {

        this.language = language;
        this.tsLanguage = load(language);

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
