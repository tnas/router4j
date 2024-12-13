package io.github.tnas.router4j.ors;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

import io.github.tnas.router4j.Locality;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;
import io.github.tnas.router4j.exception.RouterException;

public class OrsRouterApi implements RouterApi {

	private static final String ISO_CODE_BRAZIL = "BR";
	private static final String DISTANCE_ENDPOINT = "https://api.openrouteservice.org/v2/matrix/driving-car";
	private static final String DISTANCE_PARAMS = "{\"locations\":[[%f,%f],[%f,%f]],\"metrics\":[\"distance\"],\"units\":\"km\"}";
	private static final String SEARCH_ENDPOINT = "https://api.openrouteservice.org/geocode/search/structured?";

	@Override
	public Distance getRoadDistance(Point from, Point to, String apiKey) {
		
		if (Objects.isNull(apiKey)) {
			throw new RouterException("Open Route Service API Key not found");
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
			throw new RouterException("Open Route Service API Key not found");
		}

		var uriBuilder = new StringBuilder(SEARCH_ENDPOINT);

		uriBuilder.append(String.format("api_key=%s", apiKey));

		if (Objects.nonNull(country)) {
			uriBuilder.append(String.format("&boundary.country=%s", URLEncoder.encode(country, StandardCharsets.UTF_8)));
		}

		if (Objects.nonNull(region)) {
			uriBuilder.append(String.format("&region=%s", URLEncoder.encode(region, StandardCharsets.UTF_8)));
		}

		if (Objects.nonNull(name)) {
			uriBuilder.append(String.format("&locality=%s", URLEncoder.encode(name, StandardCharsets.UTF_8)));
		}

		var requestBuilder = HttpRequest.newBuilder().GET();
		return this.sendRequest(requestBuilder, uriBuilder.toString(), OrsLocality.class);
	}

	private <R> R sendRequest(HttpRequest.Builder requestBuilder, String uri, Class<R> responseClass) {

		HttpResponse<String> response;

		try {
			var request = requestBuilder.uri(new URI(uri)).build();
			response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (URISyntaxException e) {
			throw new RouterException(e);
		} catch (InterruptedException | IOException e) {
			Thread.currentThread().interrupt();
			throw new RouterException("Open Route Service API communication failure", e);
		}

		try {
			var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
			return mapper.readValue(response.body(), responseClass);
		} catch (JacksonException e) {
			throw new RouterException("Failure to unmarshall Open Route Service API response", e);
		}
	}

}
