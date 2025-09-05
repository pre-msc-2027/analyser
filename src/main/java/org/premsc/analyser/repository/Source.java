package org.premsc.analyser.repository;

import org.premsc.analyser.parser.languages.ILanguageHelper;
import org.premsc.analyser.parser.languages.LanguageEnum;
import org.premsc.analyser.parser.languages.UnsupportedLanguage;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.parser.tree.TreeHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract class representing a source file.
 * Provides methods to access the file path, content, language helper, and parse the source.
 */
public class Source implements ISource {

    protected final String filepath;

    /**
     * Constructor for the Source class.
     *
     * @param filepath the path to the source file.
     */
    protected Source(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String getFilepath() {
        return filepath;
    }

    @Override
    public String getExtension() {
        int lastDotIndex = filepath.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filepath.length() - 1) {
            return "";
        }
        return filepath.substring(lastDotIndex + 1);
    }

    @Override
    public String getContent() throws IOException {

        return Files.readString(Path.of(this.filepath), StandardCharsets.UTF_8);

    }

    @Override
    public LanguageEnum getLanguage() throws UnsupportedLanguage {

        return LanguageEnum.getByExtension(this.getExtension());

    }

    @Override
    public ILanguageHelper getLanguageHelper() throws UnsupportedLanguage {
        return this.getLanguage().getHelper();
    }

    @Override
    public ITreeHelper parse() throws UnsupportedLanguage, IOException {
        return new TreeHelper(this);
    }

    @Override
    public String toString() {
        return "Source{" +
               "filepath='" + filepath + '\'' +
               '}';
    }

}
