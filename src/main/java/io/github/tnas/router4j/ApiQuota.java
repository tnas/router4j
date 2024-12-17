package io.github.tnas.router4j;

public interface ApiQuota {

	default int roadDistancePerMinute() { return Integer.MAX_VALUE; }
	
	default int roadDistancePerDay() { return Integer.MAX_VALUE; }
	
	default int localityPerMinute() { return Integer.MAX_VALUE; }
	
	default int localityPerDay() { return Integer.MAX_VALUE; }
}
