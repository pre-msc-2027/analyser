package org.premsc.analyser.repository;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a repository of source files.
 * This class is responsible for managing a collection of source files
 * and providing access to their paths and contents.
 */
public class Repository {

    private final Path path;
    private final List<Source> sources = new ArrayList<>();

    public Repository(Path path) {
        this.path = path;
        this.read();
    }

    /**
     * Returns the path of the repository.
     * @return the path of the repository
     */
    public Path getPath() {
        return path;
    }

    /**
     * Stream the sources in the repository.
     * @return a stream of sources in the repository
     */
    public Stream<Source> stream() {
        return sources.stream();
    }

    /**
     * Reads the files from the repository.
     */
    private void read() {
        try {
            Files.walkFileTree(this.path, new FileVisitor(this.sources));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static class FileVisitor extends SimpleFileVisitor<Path> {

        private final List<Source> sources;

        public FileVisitor(List<Source> sources) {
            this.sources = sources;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (Source.isSupported(file)) {
                this.sources.add(new Source(file.toString()) {});
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }
    }



}
