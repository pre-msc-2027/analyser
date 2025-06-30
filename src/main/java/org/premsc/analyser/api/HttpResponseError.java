package org.premsc.analyser.api;

public class HttpResponseError extends RuntimeException {
    public HttpResponseError(int statusCode) {
        super("HTTP response error with status code: " + statusCode);
    }
}
