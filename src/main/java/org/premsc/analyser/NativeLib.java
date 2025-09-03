package org.premsc.analyser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for loading native Tree-sitter libraries.
 */
public final class NativeLib {
    private NativeLib() {}

    private static volatile boolean runtimeLoaded = false;
    private static final Set<String> loadedGrammars = ConcurrentHashMap.newKeySet();
    private static final Map<String, String> extractedPaths = new HashMap<>();

    /**
     * Ensure the Tree-sitter runtime library is loaded only once.
     */
    public static synchronized void ensureRuntimeLoaded() {
        if (runtimeLoaded) return;
        String abs = extractToTemp(resourcePathForMappedName(mappedName("tree-sitter")));
        System.load(abs);
        runtimeLoaded = true;
    }

    /**
     * Open a Tree-sitter grammar for the specified language.
     * @param language the programming language (e.g., "javascript", "python")
     * @return the SymbolLookup for the loaded grammar
     */
    public static SymbolLookup openGrammar(String language) {
        ensureRuntimeLoaded();

        String lang = language.toLowerCase(Locale.ROOT);
        if (loadedGrammars.contains(lang)) {
            return SymbolLookup.libraryLookup(alreadyExtractedPath(lang), Arena.global());
        }

        String base = getNativeFileName(language);
        String abs = extractToTemp(resourcePathForMappedName(mappedName(base)));

        loadedGrammars.add(lang);
        extractedPaths.put(lang, abs);

        return SymbolLookup.libraryLookup(abs, Arena.global());
    }


    /**
     * Get the class representing the language helper for this language.
     * @return the class of the language helper
     */
    public static String getNativeFileName(String lang) {
        return "tree-sitter" + (lang.isEmpty()?"":"-") + lang;
    }

    /**
     * Get the class representing the language helper for this language.
     * @return the class of the language helper
     */
    public static String getNativeName(String lang) {
        return NativeLib.getNativeFileName(lang).replace('-', '_');
    }

    /**
     * Get the already extracted path for a given language.
     * @param lang the programming language
     * @return the path to the extracted native library
     */
    private static String alreadyExtractedPath(String lang) {
        return extractedPaths.get(lang);
    }

    /**
     * Map a base name to the appropriate native library file name based on the operating system.
     * @param base the base name of the library (e.g., "tree-sitter")
     * @return the mapped file name (e.g., "tree-sitter.dll", "libtree-sitter.so")
     */
    private static String mappedName(String base) {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("win"))  return base + ".dll";
        if (os.contains("mac"))  return "lib" + base + ".dylib";
        return "lib" + base + ".so";
    }

    /**
     * Get the directory name for the current operating system.
     * @return the OS directory name (e.g., "windows", "macos", "linux")
     */
    private static String osDir() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("win"))  return "windows";
        if (os.contains("mac"))  return "macos";
        return "linux";
    }

    /**
     * Get the directory name for the current architecture.
     * @return the architecture directory name (e.g., "x86_64", "aarch64")
     */
    private static String archDir() {
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        if (arch.equals("amd64") || arch.equals("x86-64")) return "x86_64";
        if (arch.startsWith("aarch64") || arch.startsWith("arm64")) return "aarch64";
        return arch;
    }

    /**
     * Get the resource path for a given mapped file name.
     * @param fileName the mapped file name
     * @return the resource path (e.g., "/native/linux/x86_64/libtree-sitter.so")
     */
    private static String resourcePathForMappedName(String fileName) {
        return "/native/" + osDir() + "/" + archDir() + "/" + fileName;
    }

    /**
     * Extract a resource to a temporary file.
     * @param resourcePath the path to the resource within the JAR
     * @return the absolute path to the extracted temporary file
     */
    private static String extractToTemp(String resourcePath) {
        try (InputStream in = NativeLib.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new UnsatisfiedLinkError("Native resource not found: " + resourcePath);
            }
            String suffix = "-" + Paths.get(resourcePath).getFileName();
            Path tmp = Files.createTempFile("ts-", suffix);
            tmp.toFile().deleteOnExit();
            try (OutputStream out = Files.newOutputStream(tmp, StandardOpenOption.TRUNCATE_EXISTING)) {
                in.transferTo(out);
            }
            return tmp.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to extract: " + resourcePath, e);
        }
    }
}
