package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RuleParameter(
        String name,
        @JsonProperty("default") String default_value,
        ParameterType type,
        String description,
        String options
) {}
