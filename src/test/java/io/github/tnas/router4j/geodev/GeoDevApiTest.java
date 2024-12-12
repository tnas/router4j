package io.github.tnas.router4j.geodev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.tnas.router4j.model.Distance;
import io.github.tnas.router4j.model.Metric;
import io.github.tnas.router4j.model.geodev.GeoDevDistance;

class GeoDevApiTest {

	@Test
	void should_get_distance_from_Curitiba_to_Abatia() throws Exception {
		
		var request = HttpRequest.newBuilder()
                .uri(new URI("https://api.geo.dev/distance"))
                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .POST(HttpRequest.BodyPublishers.ofString("{\"from\":{\"lat\":-25.49509,\"lon\":-49.28433},\"to\":{\"lat\":-23.3045,\"lon\":-50.34063}}"))
                .build();
		 
		HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Request: " + request.toString());
        System.out.println("Response: " + response.body());

        var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
        Distance distance = mapper.readValue(response.body(), GeoDevDistance.class);
	     
	    assertEquals(266.02926599711407, distance.getValue());
	    assertEquals(-25.49509, distance.getFrom().getLatitude());
	    assertEquals(-49.28433, distance.getFrom().getLongitude());
	    assertEquals(-23.3045, distance.getTo().getLatitude());
	    assertEquals(-50.34063, distance.getTo().getLongitude());
	    assertEquals(Metric.KM, distance.getMetric());
	}
}
