package io.github.tnas.router4j;

public interface RouterApi {

	Distance getRoadDistance(Point from, Point to, String apiKey);

	Locality getLocality(String name, String region, String apiKey);

	Locality getLocality(String name, String region, String country, String apiKey);
	
	default int getRoadDistancePerMinute() { return Integer.MAX_VALUE; }
	
	default int getRoadDistancePerDay() { return Integer.MAX_VALUE; }
	
	default int getLocalityPerMinute() { return Integer.MAX_VALUE; }
	
	default int getLocalityPerDay() { return Integer.MAX_VALUE; }
	
}
