package io.github.tnas.router4j.ors;

import io.github.tnas.router4j.AbstractRouterApi;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.exception.RouterException;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

public class OrsRouterApi extends AbstractRouterApi {

	private static final String ISO_CODE_BRAZIL = "BR";
	private static final String DISTANCE_ENDPOINT = "https://api.openrouteservice.org/v2/matrix/driving-car";
	private static final String DISTANCE_PARAMS = "{\"locations\":[[%f,%f],[%f,%f]],\"metrics\":[\"distance\"],\"units\":\"km\"}";
	private static final String SEARCH_ENDPOINT = "https://api.openrouteservice.org/geocode/search/structured?";

	@Override
	public Distance getRoadDistance(Point from, Point to, String apiKey) {
		
		if (Objects.isNull(apiKey)) {
			throw new RouterException("API Key not found");
		}

		var requestBuilder = HttpRequest.newBuilder()
	                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType(),
	                        HttpHeaders.AUTHORIZATION, apiKey)
	                .POST(HttpRequest.BodyPublishers.ofString(
	                		String.format(Locale.US, DISTANCE_PARAMS, from.getLongitude(), from.getLatitude(),
	                				to.getLongitude(), to.getLatitude())));

		return this.sendRequest(requestBuilder, DISTANCE_ENDPOINT, OrsDistance.class);
	}

	@Override
	public Locality getLocality(String name, String region, String apiKey) {
		return this.searchLocality(name, region, ISO_CODE_BRAZIL, apiKey);
	}

	@Override
	public Locality getLocality(String name, String region, String country, String apiKey) {
		return this.searchLocality(name, region, country, apiKey);
	}

	private Locality searchLocality(String name, String region, String country, String apiKey) {

		if (Objects.isNull(apiKey)) {
			throw new RouterException("API Key not found");
		}

		var uriBuilder = new StringBuilder(SEARCH_ENDPOINT);

		uriBuilder.append(String.format("api_key=%s", apiKey));

		if (Objects.nonNull(country)) {
			uriBuilder.append(String.format("&country=%s", URLEncoder.encode(country, StandardCharsets.UTF_8)));
		}

		if (Objects.nonNull(region)) {
			uriBuilder.append(String.format("&region=%s", URLEncoder.encode(region, StandardCharsets.UTF_8)));
		}

		if (Objects.nonNull(name)) {
			uriBuilder.append(String.format("&locality=%s", URLEncoder.encode(name, StandardCharsets.UTF_8)));
		}

		return this.sendRequest(HttpRequest.newBuilder().GET(), uriBuilder.toString(), OrsLocality.class);
	}

	@Override
	public int getRoadDistancePerMinute() {
		return 40;
	}

	@Override
	public int getRoadDistancePerDay() {
		return 500;
	}

	@Override
	public int getLocalityPerMinute() {
		return 100;
	}

	@Override
	public int getLocalityPerDay() {
		return 1000;
	}
	
	
}
