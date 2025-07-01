package org.premsc.analyser.repository;

import org.premsc.analyser.Visualizer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MockSource extends Source {

    /**
     * Constructor for the Source class.
     *
     * @param filepath the path to the source file.
     */
    protected MockSource(String filepath) {
        super(filepath);
    }

    @Override
    public String getContent() {
        InputStream inputStream = Visualizer.class.getResourceAsStream("/" + this.filepath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + this.filepath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + this.filepath, e);
        }
    }
}
