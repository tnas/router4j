package io.github.tnas.router4j;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tnas.router4j.exception.RouterException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractRouterApi implements RouterApi {

    protected <R> R sendRequest(HttpRequest.Builder requestBuilder, String uri, Class<R> responseClass) {

        HttpResponse<String> response;

        try {
            var request = requestBuilder.uri(new URI(uri)).build();
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {
            throw new RouterException(e);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new RouterException("API communication failure", e);
        }

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RouterException(response.body());
        }
        try {
            var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
            return mapper.readValue(response.body(), responseClass);
        } catch (JacksonException e) {
            throw new RouterException("Failure to unmarshall API response", e);
        }
    }
}
