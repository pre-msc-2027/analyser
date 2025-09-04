package org.premsc.analyser.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.premsc.analyser.AnalyserApplication;
import org.premsc.analyser.IHasModule;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * Abstract base class for API interactions.
 */
public class Api {

    private static final String ENV_URL = "API_URL";

    private static final String URL = System.getenv(ENV_URL);

    private final AnalyserApplication app;
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Constructor for Api.
     *
     * @param app The AnalyserApplication instance associated with this API.
     */
    public Api(AnalyserApplication app) {
        this.app = app;
    }

    /**
     * Returns the AnalyserApplication instance associated with this API.
     *
     * @return The AnalyserApplication instance.
     */
    public HttpClient getClient() {
        return client;
    }

    /**
     * Returns the URL of the API.
     *
     * @return The API URL.
     */
    private URI getUri(String route) {
        return URI.create("http://" + URL + "/" + route + "/" + this.app.getId());
    }

    /**
     * Returns the AnalyserApplication instance associated with this API.
     *
     * @return The AnalyserApplication instance.
     */
    private HttpRequest.Builder getBuilder(String route) {
        return HttpRequest
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(this.getUri(route));
    }

    /**
     * Sends an HTTP request using the provided builder.
     *
     * @param builder The HttpRequest.Builder to use for the request.
     * @return The HttpResponse<String> received from the server.
     */
    private HttpResponse<String> send(HttpRequest.Builder builder) throws IOException, InterruptedException {

        HttpResponse<String> response;

        if (Objects.equals(this.app.getId(), "0")) response = Api.mock(builder);
        else response = this.client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new HttpResponseError(response);
        }

        return response;

    }

    /**
     * Sends a GET request to the specified route and returns the response as a JsonElement.
     *
     * @param route The API route to send the GET request to.
     * @return The response body parsed as a JsonElement.
     */
    public JsonElement get(String route) throws IOException, InterruptedException {

        HttpResponse<String> response = this.send(this.getBuilder(route).GET());

        return JsonParser.parseString(response.body());

    }

    /**
     * Sends a GET request to the specified route and maps the response to the provided schema class.
     *
     * @param route The API route to send the GET request to.
     * @param schema The class to map the response to.
     * @return An instance of the schema class populated with the response data.
     */
    public <S> S get(String route, Class<S> schema) throws IOException, InterruptedException {

        HttpResponse<String> response = this.send(this.getBuilder(route).GET());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), schema);
    }

    public <C> void post(String route, C obj) {
        ObjectMapper mapper = new ObjectMapper();
        if (obj instanceof IHasModule hasModuleObj) mapper.registerModule(hasModuleObj.getModule());
        try {
            this.post(route, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a POST request to the specified route with the provided data.
     *
     * @param route The API route to send the POST request to.
     * @param data  The data to be sent in the request body as a JsonElement.
     */
    public void post(String route, JsonElement data) {

        this.post(route, data.toString());

    }

    private void post(String route, String data) {

        HttpResponse<String> response;
        try {
            this.send(this.getBuilder(route)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data)));
        } catch (Exception ex) {
            this.app.log(ex);
        }
    }

    static HttpResponse<String> mock(HttpRequest.Builder builder) {

        HttpRequest request = builder.build();

        return new HttpResponse<>() {

            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public HttpRequest request() {
                return request;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {

                if (request.uri().toString().contains("scans/options")) {
                    return """
                            {
                                "repo_url": "",
                                "use_ai_assistance": false,
                                "max_depth": -1,
                                "follow_symlinks": true,
                                "target_type": "REPOSITORY",
                                "target_files": [],
                                "severity_min": "LOW",
                                "branch_id": "main",
                                "commit_hash": "HEAD"
                            }
                            """;
                } else if (request.uri().toString().contains("rules")) {
                    return """
                            [
                                    {
                                      "rule_id": 0,
                                      "language": "html",
                                      "tags": [],
                                      "parameters": [
                                        {
                                          "name": "casing",
                                          "default": "lower_case"
                                        }
                                      ],
                                      "slang": "node (element [\\n    (start_tag (tag_name @target))\\n    (end_tag (tag_name @target))\\n    ])\\nwhere @target !* $casing"
                                    },
                                    {
                                      "rule_id": 1,
                                      "language": "html",
                                      "tags": [],
                                      "parameters": [],
                                      "slang": "index &target\\n    where source = filepath()\\n        type = \\"class\\"\\n        value != &other\\n    with &other\\n        where type = \\"class\\"\\n            source = &style\\n    with &style\\n        where type = \\"link_stylesheet\\"\\n            source = filepath()\\n"
                                    }
                            ]
                            """;
                }

                return "{}";

            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return request.uri();
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

    }

}

