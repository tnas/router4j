package io.github.tnas.router4j.ors;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

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

	private static final String DISTANCE_ENDPOINT = "https://api.openrouteservice.org/v2/matrix/driving-car";
	private static final String DISTANCE_POST_BODY_FORMAT = "{\"locations\":[[%f,%f],[%f,%f]],\"metrics\":[\"distance\"],\"units\":\"km\"}";
	
	@Override
	public Distance getRoadDistance(Point from, Point to, String... apiKeys) {
		
		if (apiKeys.length == 0) {
			throw new RouterException("Open Route Service API Key not found");
		}

		HttpResponse<String> response = null;
		
		try {
			var request = HttpRequest.newBuilder()
	                .uri(new URI(DISTANCE_ENDPOINT))
	                .headers(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType(),
	                        HttpHeaders.AUTHORIZATION, apiKeys[0])
	                .POST(HttpRequest.BodyPublishers.ofString(
	                		String.format(Locale.US, DISTANCE_POST_BODY_FORMAT, from.getLongitude(), from.getLatitude(),
	                				to.getLongitude(), to.getLatitude())))
	                .build();
	 
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
			 return mapper.readValue(response.body(), OrsDistance.class);
		} catch (JacksonException e) {
			throw new RouterException("Failure to unmarshall Open Route Service API response", e);
		}
	}


}
