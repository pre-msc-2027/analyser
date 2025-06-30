package org.premsc.analyser.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.premsc.analyser.AnalyserApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Abstract base class for API interactions.
 */
public class Api {

    private static final String ENV_URL = "api_url";

    private static final String URL = System.getenv(ENV_URL);

    private final AnalyserApplication app;
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Constructor for Api.
     * @param app The AnalyserApplication instance associated with this API.
     */
    public Api(AnalyserApplication app) {
        this.app = app;
    }

    /**
     * Returns the AnalyserApplication instance associated with this API.
     * @return The AnalyserApplication instance.
     */
    public HttpClient getClient() {
        return client;
    }

    /**
     * Returns the URL of the API.
     * @return The API URL.
     */
    private URI getUri(String route) {
        return URI.create(URL + "/" + this.app.getId() + "/" + route);
    }

    /**
     * Returns the AnalyserApplication instance associated with this API.
     * @return The AnalyserApplication instance.
     */
    private HttpRequest.Builder getBuilder(String route) {
        return HttpRequest
                .newBuilder()
                .uri(this.getUri(route));
    }

    /**
     * Sends an HTTP request using the provided builder.
     * @param builder The HttpRequest.Builder to use for the request.
     * @return The HttpResponse<String> received from the server.
     */
    private HttpResponse<String> send(HttpRequest.Builder builder) {

        HttpResponse<String> response;

        try {
            response = this.client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200) {
            throw new HttpResponseError(response.statusCode());
        }

        return response;

    }

    /**
     * Sends a GET request to the specified route and returns the response as a JsonElement.
     * @param route The API route to send the GET request to.
     * @return The response body parsed as a JsonElement.
     */
    public JsonElement get(String route) {

        HttpResponse<String> response = this.send(this.getBuilder(route).GET());

        return JsonParser.parseString(response.body());

    }

}
