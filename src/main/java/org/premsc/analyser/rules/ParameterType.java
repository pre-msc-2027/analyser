package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum representing different parameter types.
 */
public enum ParameterType {

    @JsonAlias({"enum", "ENUM"})
    ENUM;

    /**
     * Get the ParameterType corresponding to the given name.
     *
     * @param name the name of the parameter type
     * @return the corresponding ParameterType, or null if not found
     */
    @JsonCreator
    public static ParameterType forName(String name) {
        for (ParameterType c : values())
            if (c.name().equals(name)) return c;
        return null;
    }
}
