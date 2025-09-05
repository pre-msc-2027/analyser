package org.premsc.analyser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.Module;

/**
 * Interface for classes that have a Jackson Module for serialization/deserialization.
 */
public interface IHasModule {

    /**
     * Gets the Jackson Module associated with this class.
     *
     * @return the Jackson Module
     */
    @JsonIgnore
    Module getModule();
}
