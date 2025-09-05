package org.premsc.analyser.rules;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum ParameterType {
    @JsonAlias({"enum", "ENUM"})
    ENUM;

    @JsonCreator
    public static ParameterType forName(String name) {
        for(ParameterType c: values())
            if(c.name().equals(name)) return c;
        return null;
    }
}
