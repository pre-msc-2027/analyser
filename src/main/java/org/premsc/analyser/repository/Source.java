package org.premsc.analyser.repository;

import org.premsc.analyser.parser.languages.ILanguageHelper;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.parser.tree.TreeHelperFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract class representing a source file.
 * Provides methods to access the file path, content, language helper, and parse the source.
 */
public abstract class Source {

    protected final String filepath;

    /**
     * Constructor for the Source class.
     * @param filepath the path to the source file.
     */
    protected Source(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Returns the file path of the source.
     * @return the file path as a String.
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Returns the file extension of the source.
     * @return the file extension as a String.
     */
    public String getExtension() {
        int lastDotIndex = filepath.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filepath.length() - 1) {
            return "";
        }
        return filepath.substring(lastDotIndex + 1);
    }

    /**
     * Returns the content of the source file.
     * @return the content as a String.
     */
    public String getContent() {

        try {
            return Files.readString(Path.of(this.filepath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Returns the language helper for the source file.
     * @return an instance of ILanguageHelper.
     */
    public ILanguageHelper getLanguageHelper() {
        try {
            return LanguageEnum.getByExtension(this.getExtension()).getHelper();
        } catch (UnsupportedLanguage e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses the source file and returns a tree helper for further processing.
     * @return an instance of ITreeHelper.
     */
    public ITreeHelper parse() {
        return TreeHelperFactory.from(this);
    }

    @Override
    public String toString() {
        return "Source{" +
                "filepath='" + filepath + '\'' +
                '}';
    }

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
