package org.premsc.analyser;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;

/**
 * Utility class providing file and directory operations.
 */
public class Utils {

    /**
     * Recursively deletes a folder and its contents.
     * Attempts to handle read-only files on Windows by clearing attributes before deletion.
     *
     * @param root the root path of the folder to delete
     * @throws IOException if an I/O error occurs
     */
    public static void DeleteFolder(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            private void makeWritable(Path p) {
                try {
                    DosFileAttributeView view = Files.getFileAttributeView(p, DosFileAttributeView.class);
                    if (view != null) {
                        // Removing read-only/hidden/system flags helps on Windows
                        view.setReadOnly(false);
                        view.setHidden(false);
                        view.setSystem(false);
                    }
                } catch (IOException ignored) {
                }
            }

            private void deleteWithFixes(Path p) throws IOException {
                try {
                    Files.delete(p);
                } catch (AccessDeniedException e) {
                    // Clear attributes and retry
                    makeWritable(p);
                    Files.delete(p);
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                deleteWithFixes(file);
                return FileVisitResult.CONTINUE;
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                deleteWithFixes(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
