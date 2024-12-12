package io.github.tnas.router4j;

import java.util.Optional;

public interface Locality {

	Location[] getLocations();
	
	default Optional<String[]> getErrors() {
		return Optional.empty();
	}
}
