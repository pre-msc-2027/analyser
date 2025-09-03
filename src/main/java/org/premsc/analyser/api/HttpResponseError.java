package org.premsc.analyser.api;

import java.net.http.HttpResponse;

/**
 * Exception class for handling HTTP response errors.
 * This exception is thrown when an HTTP response has a status code indicating an error.
 */
public class HttpResponseError extends RuntimeException {
    public HttpResponseError(HttpResponse<?> response) {
        super("HTTP response error (%s): %s".formatted(response.statusCode(), response.body()));
    }
}
