package io.github.tnas.router4j.ors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsLocality implements Locality {

	private OrsLocation[] features;
	
	@Override
	public Location[] getLocations() {
		return this.features;
	}
	
	public void setFeatures(OrsLocation[] features) {
		this.features = features;
	}
	
}
