package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum ParameterType {
    @JsonAlias({"enum", "ENUM"})
    ENUM;
}
