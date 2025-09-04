package org.premsc.analyser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;

/**
 * Log is a record that represents a log entry with a timestamp, message, and optional error information.
 * It provides multiple constructors for creating log entries with different levels of detail.
 */
public record Log(
    @JsonFormat(shape = JsonFormat.Shape.NUMBER) Instant timestamp,
    String message,
    String error
) implements IHasModule {

    /**
     * Constructor for Log with current timestamp and message only.
     * @param message The log message.
     */
    public Log(String message) {
        this(Instant.now(), message, "");
    }

    /**
     * Constructor for Log with specified timestamp, message, and error.
     * @param message The log message.
     * @param error The error information.
     */
    public Log(String message, String error) {
        this(Instant.now(), message, error);
    }

    /**
     * Constructor for Log with specified timestamp, message, and error.
     * @param error The exception to log.
     */
    public Log(Exception error) {
        this(Instant.now(), error.getMessage(), error.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        if (error.isEmpty()) return "[%s] %s".formatted(timestamp, message);
        else return "[%s] ERROR: (%s) %s".formatted(this.timestamp, this.error, this.message);
    }

    @Override
    public JavaTimeModule getModule() {
        return new JavaTimeModule();
    }
}
