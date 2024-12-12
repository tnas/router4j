package io.github.tnas.router4j.model.ors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.tnas.router4j.model.Locality;
import io.github.tnas.router4j.model.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsLocality implements Locality {

	private OrsLocation[] features;
	
	@Override
	public Location[] getLocations() {
		return this.features;
	}

	public OrsLocation[] getFeatures() {
		return features;
	}

	public void setFeatures(OrsLocation[] features) {
		this.features = features;
	}

}
