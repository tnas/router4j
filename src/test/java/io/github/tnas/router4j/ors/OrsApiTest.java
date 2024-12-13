package io.github.tnas.router4j.ors;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrsApiTest {

	private static String apiKey;
	
	private static RouterApi orsRouterApi;
	
	@BeforeAll
	public static void setUp() throws IOException {
		apiKey = Files.readString(Paths.get("src/test/resources/ors_api_key.txt"));
		orsRouterApi = new OrsRouterApi();
	}
	
	@Test
	void should_get_distance_from_Curitiba_to_Abatia() {
		
	     Point from = new OrsPoint(-49.279708, -25.46005);
	     Point to = new OrsPoint(-50.311719, -23.302293);
	     Distance distance = orsRouterApi.getRoadDistance(from, to, apiKey);
	     
	     assertEquals(382.56, distance.getValue());
	     assertEquals(-25.46005, distance.getFrom().getLatitude());
	     assertEquals(-49.279708, distance.getFrom().getLongitude());
	     assertEquals(-23.302293, distance.getTo().getLatitude());
	     assertEquals(-50.311719, distance.getTo().getLongitude());
	     assertEquals(Metric.KM, distance.getMetric());
	}
	
	@Test
	void should_get_one_location_for_BeloHorizonte_MinasGerais_BR() {
		
		Locality locality = orsRouterApi.getLocality("Belo Horizonte", "Minas Gerais", apiKey);
	    
	    assertEquals(1, locality.getLocations().length);
	    assertEquals(-43.959502, locality.getLocations()[0].getPoint().getLongitude());
	    assertEquals(-19.914319, locality.getLocations()[0].getPoint().getLatitude());
	    assertEquals("Belo Horizonte", locality.getLocations()[0].getName());
	    assertEquals("Minas Gerais", locality.getLocations()[0].getRegion());
	}
	
	@Test
	void should_return_error_invalid_isoCode_country() {
		
		var invalidCountryIsoCode = "ZZ";
		
		Locality locality = orsRouterApi.getLocality("Curitiba", null, invalidCountryIsoCode, apiKey);

		var errors = locality.getErrors().isPresent() ? locality.getErrors().get() : null;

	    assertEquals(0, locality.getLocations().length);
		assertNotNull(errors);
	    assertEquals(1, errors.length);
	    assertEquals(String.format("%s is not a valid ISO2/ISO3 country code", invalidCountryIsoCode), 
	    		locality.getErrors().get()[0]);
	}
	
}
