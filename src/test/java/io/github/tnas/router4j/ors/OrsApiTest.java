package io.github.tnas.router4j.ors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.tnas.router4j.ApiType;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.PointBuilder;
import io.github.tnas.router4j.RouterApi;
import io.github.tnas.router4j.exception.RouterException;

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

		Point from = PointBuilder.newBuilder().apiType(ApiType.ORS).longitude(-49.279708).latitude(-25.46005).build();
		Point to = PointBuilder.newBuilder().apiType(ApiType.ORS).longitude(-50.311719).latitude(-23.302293).build();

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
	void should_get_error_from_distance_endpoint_moreThan_40_requests_per_minute() throws JsonProcessingException {

		Point from = PointBuilder.newBuilder().apiType(ApiType.ORS).longitude(-49.279708).latitude(-25.46005).build();
		Point to = PointBuilder.newBuilder().apiType(ApiType.ORS).longitude(-50.311719).latitude(-23.302293).build();

		for (int count = 1; count <= orsRouterApi.getRoadDistancePerMinute();) {
			System.out.println("Sending request number " + count++);
			orsRouterApi.getRoadDistance(from, to, apiKey);
		}

		try {
			orsRouterApi.getRoadDistance(from, to, apiKey);
			fail("Should throw RouterException");
		} catch (RouterException e) {
			assertEquals("Rate limit exceeded", new JsonMapper().readValue(e.getMessage(), RateLimitError.class).getError());
		}
	}

	public static class RateLimitError {
		
		private String error;

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}
	
}
