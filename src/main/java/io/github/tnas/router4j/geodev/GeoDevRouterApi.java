package io.github.tnas.router4j.geodev;

import io.github.tnas.router4j.AbstractRouterApi;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Point;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.net.http.HttpRequest;
import java.util.Objects;

public class GeoDevRouterApi extends AbstractRouterApi {

    private static final String DISTANCE_ENDPOINT = "https://api.geo.dev/distance";
    private static final String DISTANCE_PARAMS = "{\"from\":{\"lat\":%f,\"lon\":%f},\"to\":{\"lat\":%f,\"lon\":%f}}";
    private static final String SEARCH_ENDPOINT = "https://api.geo.dev/search";
    private static final String SEARCH_PARAMS = "{\"term\":\"%s\"}";
    private static final String SEARCH_SEPARATOR = ", ";

    @Override
    public Distance getRoadDistance(Point from, Point to, String apiKey) {

        var requestBuilder = HttpRequest.newBuilder()
                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .POST(HttpRequest.BodyPublishers.ofString(String.format(DISTANCE_PARAMS,
                        from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude())));

        return this.sendRequest(requestBuilder, DISTANCE_ENDPOINT, GeoDevDistance.class);
    }

    @Override
    public Locality getLocality(String name, String region, String apiKey) {

        var paramBuilder = new StringBuilder(name);

        if (Objects.nonNull(region)) {
            paramBuilder.append(SEARCH_SEPARATOR).append(region);
        }

        var requestBuilder = HttpRequest.newBuilder()
                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .POST(HttpRequest.BodyPublishers.ofString(String.format(SEARCH_PARAMS, paramBuilder)));

        return this.sendRequest(requestBuilder, SEARCH_ENDPOINT, GeoDevLocality.class);
    }

    @Override
    public Locality getLocality(String name, String region, String country, String apiKey) {
        throw new UnsupportedOperationException("GeoDev API does not search using country param");
    }
}
