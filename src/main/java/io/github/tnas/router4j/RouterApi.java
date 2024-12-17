package io.github.tnas.router4j;

public interface RouterApi {

	Distance getRoadDistance(Point from, Point to, String apiKey);

	Locality getLocality(String name, String region, String apiKey);

	Locality getLocality(String name, String region, String country, String apiKey);
	
	ApiQuota getApiQuota();
}
