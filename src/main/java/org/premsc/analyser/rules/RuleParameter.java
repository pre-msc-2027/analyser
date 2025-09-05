package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record representing a rule parameter with its attributes.
 *
 * @param name          the name of the parameter
 * @param default_value the default value of the parameter
 * @param type          the type of the parameter
 * @param description   a description of the parameter
 * @param options       possible options for the parameter (if applicable)
 */
public record RuleParameter(
        String name,
        @JsonProperty("default") String default_value,
        ParameterType type,
        String description,
        String options
) {
}
