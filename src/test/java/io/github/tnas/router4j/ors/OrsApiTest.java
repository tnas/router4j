package io.github.tnas.router4j.ors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.tnas.router4j.model.Distance;
import io.github.tnas.router4j.model.Locality;
import io.github.tnas.router4j.model.Metric;
import io.github.tnas.router4j.model.ors.OrsDistance;
import io.github.tnas.router4j.model.ors.OrsLocality;

class OrsApiTest {

	private static String API_KEY;
	
	@BeforeAll
	public static void setUp() throws IOException {
		API_KEY = Files.readString(Paths.get("src/test/resources/ors_api_key.txt"));
	}
	
	@Test
	void should_get_distance_from_Curitiba_to_Abatia() throws Exception {
		
		 var request = HttpRequest.newBuilder()
	                .uri(new URI("https://api.openrouteservice.org/v2/matrix/driving-car"))
	                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType(),
	                        HttpHeaders.AUTHORIZATION, API_KEY)
	                .POST(HttpRequest.BodyPublishers.ofString("{\"locations\":[[-49.280018,-25.459935],[-50.31173,-23.302276]],\"metrics\":[\"distance\"],\"units\":\"km\"}"))
	                .build();
		 
		 HttpResponse<String> response = HttpClient.newHttpClient()
	                .send(request, HttpResponse.BodyHandlers.ofString());

	     System.out.println("Request: " + request.toString());
	     System.out.println("Response: " + response.body());
	     
	     var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
	     Distance distance = mapper.readValue(response.body(), OrsDistance.class);
	     
	     assertEquals(382.56, distance.getValue());
	     assertEquals(-25.46005, distance.getFrom().getLatitude());
	     assertEquals(-49.279708, distance.getFrom().getLongitude());
	     assertEquals(-23.302293, distance.getTo().getLatitude());
	     assertEquals(-50.311719, distance.getTo().getLongitude());
	     assertEquals(Metric.KM, distance.getMetric());
	}
	
	@Test
	void should_get_one_location_for_BeloHorizonte_MinasGerais_BR() throws Exception {
		
		var uri = String.format("https://api.openrouteservice.org/geocode/search/structured?api_key=%s&region=Minas%%20Gerais&locality=Belo%%20Horizonte&boundary.country=BR", API_KEY);
		
		var request = HttpRequest.newBuilder().uri(new URI(uri)).GET().build();
		
		HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
		
		System.out.println("Request: " + request.toString());
	    System.out.println("Response: " + response.body());
	    
	    var mapper = new JsonMapper();
	    Locality locality = mapper.readValue(response.body(), OrsLocality.class);
	    
	    assertEquals(1, locality.getLocations().length);
	    assertEquals(-43.959502, locality.getLocations()[0].getPoint().getLongitude());
	    assertEquals(-19.914319, locality.getLocations()[0].getPoint().getLatitude());
	    assertEquals("Belo Horizonte", locality.getLocations()[0].getName());
	    assertEquals("Minas Gerais", locality.getLocations()[0].getRegion());
	}

}
