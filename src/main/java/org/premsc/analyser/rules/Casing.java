package org.premsc.analyser.rules;

/**
 * Enum representing different casing styles with their corresponding regex patterns.
 */
@SuppressWarnings("unused")
public enum Casing {

    LOWER_CASE("lower_case", "^[a-z0-9]+$"),
    UPPER_CASE("upper_case", "^[A-Z0-9]+$"),
    SNAKE_CASE("snake_case", "^[a-z0-9]+(?:_[a-z0-9]+)*$"),
    SCREAMING_SNAKE_CASE("screaming_snake_case", "^[A-Z0-9]+(?:_[A-Z0-9]+)*$"),
    CAMEL_CASE("camel_case", "^[a-z]+(?:[A-Z][a-z0-9]*)*$"),
    PASCAL_CASE("pascal_case", "^[A-Z][a-z0-9]*(?:[A-Z][a-z0-9]*)*$"),
    KEBAB_CASE("kebab_case", "^[a-z0-9]+(?:-[a-z0-9]+)*$"),
    SCREAMING_KEBAB_CASE("screaming_kebab_case", "^[A-Z0-9]+(?:-[A-Z0-9]+)*$"),
    TRAIN_CASE("train_case", "^[A-Z][a-z0-9]*(?:-[A-Z][a-z0-9]*)*$"),
    DOT_CASE("dot_case", "^[a-z0-9]+(?:\\.[a-z0-9]+)*$"),
    FLAT_CASE("flat_case", "^[a-z0-9]+$"),
    ;

    private final String name;
    private final String regex;

    /**
     * Constructs a Casing enum with the specified name and regex pattern.
     *
     * @param name  The name of the casing style.
     * @param regex The regex pattern associated with the casing style.
     */
    Casing(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }

    /**
     * Returns the name of the casing style.
     *
     * @return The name of the casing style.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the regex pattern associated with the casing style.
     *
     * @return The regex pattern for the casing style.
     */
    public String getRegex() {
        return regex;
    }

}
