package org.premsc.analyser.parser.languages;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Enum representing different programming languages supported by the parser.
 * Each language has a name, associated DLL file, and file extension.
 */
public enum LanguageEnum {

    @JsonAlias({"html", "HTML"})
    HTML("html","html"),

    @JsonAlias({"css", "HTML"})
    CSS("css", "css"),
    //JAVASCRIPT,
    //TYPESCRIPT
    ;

    private final String name;
    private final String extension;

    /**
     * Constructor for the LanguageEnum.
     * @param name the name of the language
     * @param extension the file extension associated with the language
     */
    LanguageEnum(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    /**
     * Get the string value of the language.
     * @return the string value of the language
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the file extension associated with the language.
     * @return the file extension
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Get the language helper for this language.
     * @return the ILanguageHelper instance for this language
     */
    public ILanguageHelper getHelper() {
        return LanguageHelperFactory.get(this);
    }

    /**
     * Get the language helper class for this language.
     * @param extension the file extension to check
     * @return the corresponding LanguageEnum if the extension matches
     * @throws UnsupportedLanguage if the extension is not supported
     */
    public static LanguageEnum getByExtension(String extension) throws UnsupportedLanguage {
        for (LanguageEnum language : LanguageEnum.values()) {
            if (language.getExtension().equalsIgnoreCase(extension)) {
                return language;
            }
        }
        throw new UnsupportedLanguage("Unsupported file extension: " + extension + ".");
    }

    /**
     * Get the language enum by its name.
     * @param name the name of the language
     * @return the corresponding LanguageEnum
     * @throws UnsupportedLanguage if the name is not supported
     */
    public static LanguageEnum getByName(String name) throws UnsupportedLanguage {
        for (LanguageEnum language : LanguageEnum.values()) {
            if (language.getName().equalsIgnoreCase(name)) {
                return language;
            }
        }
        throw new UnsupportedLanguage("Unsupported language: " + name);
    }

}
