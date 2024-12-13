package io.github.tnas.router4j.geodev;

import io.github.tnas.router4j.ApiType;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.PointBuilder;
import io.github.tnas.router4j.RouterApi;
import io.github.tnas.router4j.exception.RouterException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeoDevApiTest {

	private static RouterApi geoDevRouterApi;

	@BeforeAll
	public static void setUp() {
		geoDevRouterApi = new GeoDevRouterApi();
	}

	@Test
	void should_get_distance_from_Curitiba_to_Abatia() {
		
		Point from = new GeoDevPoint(-25.49509, -49.28433);
		Point to = new GeoDevPoint(-23.3045, -50.34063);

//		Point from = PointBuilder.newBuilder()
//				.apiType(ApiType.GEO_DEV)
//				.longitude(-49.28433).latitude(-25.49509)
//				.build();
//
//		Point to = PointBuilder.newBuilder()
//				.apiType(ApiType.GEO_DEV)
//				.longitude(-23.3045).latitude(-50.34063)
//				.build();

		Distance distance = geoDevRouterApi.getRoadDistance(from, to, null);

	    assertEquals(266.02926599711407, distance.getValue());
	    assertEquals(-25.49509, distance.getFrom().getLatitude());
	    assertEquals(-49.28433, distance.getFrom().getLongitude());
	    assertEquals(-23.3045, distance.getTo().getLatitude());
	    assertEquals(-50.34063, distance.getTo().getLongitude());
	    assertEquals(Metric.KM, distance.getMetric());
	}

	@Test
	void should_get_seven_locations_for_Curitiba_Parana_BR() {

		Locality locality = geoDevRouterApi.getLocality("Curitiba", "Paraná", null);

		assertEquals(7, locality.getLocations().length);

		var location = Stream.of(locality.getLocations())
				.filter(l -> l.getName().equals("Curitiba"))
				.findFirst()
				.orElse(null);

		assertNotNull(location);
		assertEquals(-49.28433, location.getPoint().getLongitude());
		assertEquals(-25.49509, location.getPoint().getLatitude());
		assertEquals("Curitiba", location.getName());
		assertEquals("South Region", location.getRegion());
	}

	@Test
	void should_throw_exception_when_search_less_than_3_characters() {
		assertThrows(RouterException.class, () -> geoDevRouterApi.getLocality("Be", null, null),
		"Need to post more than 2 characters");
	}
}
