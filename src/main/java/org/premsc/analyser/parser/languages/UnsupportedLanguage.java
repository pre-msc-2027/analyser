package org.premsc.analyser.parser.languages;

/**
 * Exception thrown when an unsupported language is encountered.
 */
public class UnsupportedLanguage extends Exception {

    /**
     * Constructor for the UnsupportedLanguage exception.
     *
     * @param message the detail message
     */
    public UnsupportedLanguage(String message) {
        super(message);
    }
}
