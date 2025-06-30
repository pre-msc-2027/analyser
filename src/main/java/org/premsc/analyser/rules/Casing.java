package org.premsc.analyser.rules;

public enum Casing {

    LOWER_CASE("lower_case", "^[a-z0-9]+$"),
    UPPER_CASE("upper_case", "^[A-Z0-9]+$"),
    SNAKE_CASE("snake_case", "^[a-z0-9]+(?:_[a-z0-9]+)*$"),
    SCREAMING_SNAKE_CASE("screaming_snake_case","^[A-Z0-9]+(?:_[A-Z0-9]+)*$"),
    CAMEL_CASE("camel_case","^[a-z]+(?:[A-Z][a-z0-9]*)*$"),
    PASCAL_CASE("pascal_case","^[A-Z][a-z0-9]*(?:[A-Z][a-z0-9]*)*$"),
    KEBAB_CASE("kebab_case","^[a-z0-9]+(?:-[a-z0-9]+)*$"),
    SCREAMING_KEBAB_CASE("screaming_kebab_case","^[A-Z0-9]+(?:-[A-Z0-9]+)*$"),
    TRAIN_CASE("train_case","^[A-Z][a-z0-9]*(?:-[A-Z][a-z0-9]*)*$"),
    DOT_CASE("dot_case","^[a-z0-9]+(?:\\.[a-z0-9]+)*$"),
    FLAT_CASE("flat_case","^[a-z0-9]+$"),
    ;

    private final String name;
    private final String regex;

    Casing(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

}
