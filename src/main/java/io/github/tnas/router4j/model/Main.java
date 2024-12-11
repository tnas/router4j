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
        openRouteService();
        geoDev();
    }

    /**
     * Response:
     * {"distances":[[0.0,382.56],[383.1,0.0]],
     *  "destinations":[{"location":[-49.279708,-25.46005],"snapped_distance":33.64},{"location":[-50.311719,-23.302293],"snapped_distance":2.2}],
     *  "sources":[{"location":[-49.279708,-25.46005],"snapped_distance":33.64},{"location":[-50.311719,-23.302293],"snapped_distance":2.2}],
     *  "metadata":{"attribution":"openrouteservice.org | OpenStreetMap contributors","service":"matrix","timestamp":1733931863437,
     *              "query":{"locations":[[-49.280018,-25.459935],[-50.31173,-23.302276]],"profile":"driving-car","responseType":"json",
     *                       "metrics":["distance"],"units":"km"},"engine":{"version":"8.2.0","build_date":"2024-10-09T09:23:42Z","graph_date":"2024-09-23T14:39:49Z"}}}
     */
    private static void openRouteService() throws URISyntaxException, IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openrouteservice.org/v2/matrix/driving-car"))
                .headers("Content-type", "application/json",
                        "Authorization", "api_key")
                .POST(HttpRequest.BodyPublishers.ofString("{\"locations\":[[-49.280018,-25.459935],[-50.31173,-23.302276]],\"metrics\":[\"distance\"],\"units\":\"km\"}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Request: " + request.toString());
        System.out.println(response.body());

        var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
        Distance distance = mapper.readValue(response.body(), OrsDistance.class);
        System.out.println(distance.toString());
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
