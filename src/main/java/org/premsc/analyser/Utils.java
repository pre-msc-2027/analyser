package org.premsc.analyser;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Maps a JsonElement representing a JSON array to an array of type T using the provided mapper function.
     *
     * @param element   The JsonElement to map.
     * @param mapper    A function that maps each JsonElement to an instance of type T.
     * @param generator A function that generates an array of type T with the specified size.
     * @param <T>       The type of the elements in the resulting array.
     * @return An array of type T containing the mapped elements.
     */
    public static <T> T[] JsonArrayMapper(JsonElement element, Function<JsonElement, T> mapper, IntFunction<T[]> generator) {
        return element
                .getAsJsonArray()
                .asList()
                .stream()
                .map(mapper)
                .toArray(generator);
    }

    /**
     * Maps a JsonElement representing a JSON object to a Map with String keys and values of type T using the provided mapper function.
     *
     * @param element The JsonElement to map.
     * @param mapper  A function that maps each JsonElement to an instance of type T.
     * @param <T>     The type of the values in the resulting Map.
     * @return A Map with String keys and values of type T.
     */
    public static <T> Map<String, T> JsonObjectMapper(JsonElement element, Function<JsonElement, T> mapper) {
        return element
                .getAsJsonObject()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (Map.Entry<String, JsonElement> entry) -> mapper.apply(entry.getValue())));
    }

    public static void DeleteFolder(String path) throws IOException {
        Path folder = Paths.get(path);

        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (NoSuchFileException _) {

        }

    }

}
