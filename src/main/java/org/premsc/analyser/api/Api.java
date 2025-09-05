package org.premsc.analyser.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.premsc.analyser.AnalyserApplication;
import org.premsc.analyser.IHasModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

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
     * Returns the URL of the API.
     *
     * @return The API URL.
     */
    private URI getUri(String route) {
        //noinspection HttpUrlsUsage
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

        HttpResponse<String> response = this.client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpResponseError(response);
        }
        return response;

    }

    /**
     * Sends a GET request to the specified route and maps the response to a collection of the provided schema class.
     *
     * @param route           The API route to send the GET request to.
     * @param collectionClass The class of the collection to map the response to.
     * @param schemaClass     The class of the schema to map the response elements to.
     * @param <C>             The type of the collection (e.g., List, Set).
     * @param <S>             The type of the schema class.
     * @return A collection of the specified type populated with the response data.
     * @throws IOException          If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public <C extends Collection<S>, S> C get(String route, Class<C> collectionClass, Class<S> schemaClass) throws IOException, InterruptedException {

        HttpResponse<String> response = this.send(this.getBuilder(route).GET());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                response.body(),
                mapper.getTypeFactory().constructCollectionType(collectionClass, schemaClass)
        );
    }

    /**
     * Sends a GET request to the specified route and maps the response to an instance of the provided schema class.
     *
     * @param route       The API route to send the GET request to.
     * @param schemaClass The class of the schema to map the response to.
     * @param <S>         The type of the schema class.
     * @return An instance of the specified schema class populated with the response data.
     * @throws IOException          If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public <S> S get(String route, Class<S> schemaClass) throws IOException, InterruptedException {

        HttpResponse<String> response = this.send(this.getBuilder(route).GET());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), schemaClass);
    }

    /**
     * Sends a POST request to the specified route with the provided object serialized as JSON.
     *
     * @param route the API route to send the POST request to
     * @param obj   the object to be serialized and sent in the request body
     * @param <C>   the type of the object being sent
     */
    public <C> void post(String route, C obj) {
        ObjectMapper mapper = new ObjectMapper();
        if (obj instanceof IHasModule hasModuleObj) mapper.registerModule(hasModuleObj.getModule());
        try {
            this.post(route, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void post(String route, String data) {

        try {
            this.send(this.getBuilder(route)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data)));
        } catch (Exception ex) {
            this.app.log(ex);
        }
    }

}

