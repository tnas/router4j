package io.github.tnas.router4j.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tnas.router4j.model.geodev.GeoDevDistance;
import io.github.tnas.router4j.model.ors.OrsDistance;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        geoDev();
    }

    /**
     * Response:
     * {"from":{"lat":-25.49509,"lon":-49.28433},"to":{"lat":-23.3045,"lon":-50.34063},"distance":266.02926599711407,"metric":"km"}
     */
    private static void geoDev() throws URISyntaxException, IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .uri(new URI("https://api.geo.dev/distance"))
                .headers("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"from\":{\"lat\":-25.49509,\"lon\":-49.28433},\"to\":{\"lat\":-23.3045,\"lon\":-50.34063}}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Request: " + request.toString());
        System.out.println(response.body());

        var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
        Distance distance = mapper.readValue(response.body(), GeoDevDistance.class);
        System.out.println(distance.toString());

    }
}
