package org.premsc.analyser.parser.languages;

/**
 * Enum representing different programming languages supported by the parser.
 * Each language has a name, associated DLL file, and file extension.
 */
public enum LanguageEnum {

    HTML("html", "tree-sitter-html", "html"),
    //CSS,
    //JAVASCRIPT,
    //TYPESCRIPT
    ;

    private final String name;
    private final String dll;
    private final String extension;

    LanguageEnum(String name, String dll, String extension) {
        this.name = name;
        this.dll = dll;
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
     * Get the name of the DLL file for the language.
     * @return the name of the DLL file
     */
    public String getDll() {
        return this.dll;
    }

    /**
     * Get the file extension associated with the language.
     * @return the file extension
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Get the class representing the language helper for this language.
     * @return the class of the language helper
     */
    public String getDllName() {
        return this.dll.replace('-', '_');
    }

    public ILanguageHelper getHelper() {
        return LanguageHelperBuilder.get(this);
    }

    /**
     * Get the library name for the language, formatted as "libs/{dll}/{dll}".
     * @return the formatted library name
     */
    public String getLibraryName() {
        return "libs/%s/%s".formatted(this.dll, this.dll);
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
