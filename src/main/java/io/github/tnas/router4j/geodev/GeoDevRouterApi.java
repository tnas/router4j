package io.github.tnas.router4j.geodev;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;
import io.github.tnas.router4j.exception.RouterException;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class GeoDevRouterApi implements RouterApi {

    private static final String DISTANCE_ENDPOINT = "https://api.geo.dev/distance";
    private static final String DISTANCE_PARAMS = "{\"from\":{\"lat\":%f,\"lon\":%f},\"to\":{\"lat\":%f,\"lon\":%f}}";
    private static final String SEARCH_ENDPOINT = "https://api.geo.dev/search";
    private static final String SEARCH_PARAMS = "{\"term\":\"%s\"}";
    private static final String SEARCH_SEPARATOR = ", ";

    @Override
    public Distance getRoadDistance(Point from, Point to, String apiKey) {

        HttpResponse<String> response;

        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(DISTANCE_ENDPOINT))
                    .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                    .POST(HttpRequest.BodyPublishers.ofString(String.format(DISTANCE_PARAMS,
                            from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude())))
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {
            throw new RouterException(e);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new RouterException("Geo Dev API communication failure", e);
        }
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RouterException(response.body());
        }

        try {
            var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
            return mapper.readValue(response.body(), GeoDevDistance.class);
        } catch (JacksonException e) {
            throw new RouterException("Failure to unmarshall Geo Dev API response", e);
        }
    }

    @Override
    public Locality getLocality(String name, String region, String apiKey) {

        HttpResponse<String> response;

        var paramBuilder = new StringBuilder(name);

        if (Objects.nonNull(region)) {
            paramBuilder.append(SEARCH_SEPARATOR).append(region);
        }

        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(SEARCH_ENDPOINT))
                    .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                    .POST(HttpRequest.BodyPublishers.ofString(String.format(SEARCH_PARAMS, paramBuilder)))
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {
            throw new RouterException(e);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new RouterException("Geo Dev API communication failure", e);
        }

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RouterException(response.body());
        }

        try {
            var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
            return mapper.readValue(response.body(), GeoDevLocality.class);
        } catch (JacksonException e) {
            throw new RouterException("Failure to unmarshall Geo Dev API response", e);
        }
    }

    @Override
    public Locality getLocality(String name, String region, String country, String apiKey) {
        throw new UnsupportedOperationException("GeoDev API does not search using country param");
    }
}
