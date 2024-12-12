package io.github.tnas.router4j.ors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;

class OrsApiTest {

	private static String API_KEY;
	
	private static RouterApi orsRouterApi;
	
	@BeforeAll
	public static void setUp() throws IOException {
		API_KEY = Files.readString(Paths.get("src/test/resources/ors_api_key.txt"));
		orsRouterApi = new OrsRouterApi();
	}
	
	@Test
	void should_get_distance_from_Curitiba_to_Abatia() {
		
	     Point from = new OrsPoint(-49.279708, -25.46005);
	     Point to = new OrsPoint(-50.311719, -23.302293);
	     Distance distance = orsRouterApi.getRoadDistance(from, to, API_KEY);
	     
	     assertEquals(382.56, distance.getValue());
	     assertEquals(-25.46005, distance.getFrom().getLatitude());
	     assertEquals(-49.279708, distance.getFrom().getLongitude());
	     assertEquals(-23.302293, distance.getTo().getLatitude());
	     assertEquals(-50.311719, distance.getTo().getLongitude());
	     assertEquals(Metric.KM, distance.getMetric());
	}
	
	@Test
	void should_get_one_location_for_BeloHorizonte_MinasGerais_BR() throws Exception {
		
		var uri = String.format("https://api.openrouteservice.org/geocode/search/structured?"
				+ "api_key=%s&region=Minas%%20Gerais&locality=Belo%%20Horizonte&boundary.country=%s", 
				API_KEY, "BR");
		
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
	
	@Test
	void should_return_error_invalid_isocode_country() throws Exception {
		
		var invalidCountryIsoCode = "ZZ";
		
		var uri = String.format("https://api.openrouteservice.org/geocode/search/structured?"
				+ "api_key=%s&locality=Curitiba&boundary.country=%s", 
				API_KEY, "ZZ");
		
		var request = HttpRequest.newBuilder().uri(new URI(uri)).GET().build();
		
		HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
		
		System.out.println("Request: " + request.toString());
	    System.out.println("Response: " + response.body());
	    
	    var mapper = new JsonMapper();
	    Locality locality = mapper.readValue(response.body(), OrsLocality.class);
	    
	    assertEquals(0, locality.getLocations().length);
	    assertEquals(1, locality.getErrors().get().length);
	    assertEquals(String.format("%s is not a valid ISO2/ISO3 country code", invalidCountryIsoCode), 
	    		locality.getErrors().get()[0]);
	}
	
}
