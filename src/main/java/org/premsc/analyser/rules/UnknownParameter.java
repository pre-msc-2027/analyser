package org.premsc.analyser.rules;

/**
 * Exception thrown when an unknown parameter is requested from a rule.
 */
public class UnknownParameter extends RuntimeException {

    /**
     * Constructs an UnknownParameter exception with a message indicating the unknown key.
     *
     * @param key The key of the unknown parameter.
     */
    public UnknownParameter(String key) {
        super("Unknown parameter: " + key);
    }
}
