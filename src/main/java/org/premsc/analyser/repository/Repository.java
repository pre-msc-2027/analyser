package org.premsc.analyser.repository;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.premsc.analyser.AnalyserApplication;
import org.premsc.analyser.Utils;

import java.io.File;
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

    private static final String PATH = "folders";

    private final AnalyserApplication app;
    private final List<ISource> sources = new ArrayList<>();

    /**
     * Constructor for the Repository class.
     *
     * @param app the main application instance
     */
    public Repository(AnalyserApplication app) {
        this.app = app;
    }

    /**
     * Returns the path of the repository.
     *
     * @return the path of the repository
     */
    public Path getPath() {
        return Path.of(PATH);
    }

    /**
     * Stream the sources in the repository.
     *
     * @return a stream of sources in the repository
     */
    public Stream<ISource> stream() {
        return sources.stream();
    }

    /**
     * Lists all sources in the repository.
     *
     * @return a list of sources in the repository
     */
    public List<ISource> list() {
        return this.sources;
    }

    /**
     * Initializes the repository by cloning the git repository and reading its contents.
     * This method is typically called at the start of the application to set up the repository.
     *
     * @throws Exception if an error occurs during cloning or reading the repository
     */
    public void init() throws Exception {

        this.gitClone();
        this.read();

    }

    /**
     * Clones the repository from the configured URL using the provided access token.
     * The repository is cloned into a specified directory, and if a branch or commit is specified,
     * it checks out that branch or commit after cloning.
     */
    private void gitClone() throws Exception {

        try {
            Utils.deleteFolder(this.getPath());
        } catch (IOException _) {
            //
        }

        String url = this.app.getConfig().repoUrl();
        String branch = this.app.getConfig().branch();
        String commit = this.app.getConfig().commit();
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(this.app.getToken(), "");
        File directory = new File(PATH);

        CloneCommand command = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(directory);

        if (!branch.isEmpty()) {
            command.setBranch(branch);
        }

        try (Git git = command.call()) {

            if (!commit.isEmpty()) {
                git.checkout()
                        .setName(commit)
                        .call();
            }

        }

    }

    /**
     * Reads the files from the repository.
     */
    private void read() throws IOException {

        Files.walkFileTree(this.getPath(), new FileVisitor(this.sources));

    }

    /**
     * Visitor class for traversing files in a directory.
     */
    static class FileVisitor extends SimpleFileVisitor<Path> {

        private final List<ISource> sources;

        /**
         * Constructor for the FileVisitor class.
         *
         * @param sources the list of sources to populate
         */
        public FileVisitor(List<ISource> sources) {
            this.sources = sources;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (ISource.isSupported(file)) this.sources.add(new Source(file.toString()));
            return FileVisitResult.CONTINUE;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }
    }

}
