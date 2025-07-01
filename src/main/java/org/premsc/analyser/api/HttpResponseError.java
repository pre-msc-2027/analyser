package org.premsc.analyser.api;

/**
 * Exception class for handling HTTP response errors.
 * This exception is thrown when an HTTP response has a status code indicating an error.
 */
public class HttpResponseError extends RuntimeException {
    public HttpResponseError(int statusCode) {
        super("HTTP response error with status code: " + statusCode);
    }
}
