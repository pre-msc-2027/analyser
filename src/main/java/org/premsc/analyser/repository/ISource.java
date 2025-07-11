package org.premsc.analyser.repository;

import org.premsc.analyser.parser.languages.ILanguageHelper;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.ITreeHelper;

import java.nio.file.Path;

/**
 * Interface representing a source file.
 * Provides methods to access the file path, content, language helper, and parse the source.
 */
public interface ISource {

    /**
     * Returns the file path of the source.
     * @return the file path as a String.
     */
    String getFilepath();

    /**
     * Returns the file extension of the source.
     * @return the file extension as a String.
     */
    String getExtension();

    /**
     * Returns the content of the source file.
     * @return the content as a String.
     */
    String getContent();

    /**
     * Returns the language of the source file based on its extension.
     * @return an instance of LanguageEnum representing the language.
     */
    LanguageEnum getLanguage();

    /**
     * Returns the language helper for the source file.
     * @return an instance of ILanguageHelper.
     */
    ILanguageHelper getLanguageHelper();

    /**
     * Parses the source file and returns a tree helper for further processing.
     * @return an instance of ITreeHelper.
     */
    ITreeHelper parse();

    /**
     * Checks if the given file path is supported by the parser based on its extension.
     * @param filepath the path to the file to check.
     * @return true if the file extension is supported, false otherwise.
     */
    static boolean isSupported(Path filepath) {
        String extension = filepath.getFileName().toString();
        int lastDotIndex = extension.lastIndexOf('.');

        if (lastDotIndex == -1 || lastDotIndex == extension.length() - 1) {
            return false;
        }

        String ext = extension.substring(lastDotIndex + 1);
        try {
            LanguageEnum.getByExtension(ext);
            return true;
        } catch (UnsupportedLanguage e) {
            return false;
        }
    }
}
