package io.github.tnas.router4j.model.ors;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.tnas.router4j.model.Locality;
import io.github.tnas.router4j.model.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsLocality implements Locality {

	private OrsLocation[] features;
	
	private Geocoding geocoding;
	
	@Override
	public Location[] getLocations() {
		return this.features;
	}
	
	@Override
	public Optional<String[]> getErrors() {
		return Objects.isNull(this.geocoding) ? Optional.empty() 
				: Optional.of(this.geocoding.errors);
	}

	public OrsLocation[] getFeatures() {
		return features;
	}

	public void setFeatures(OrsLocation[] features) {
		this.features = features;
	}
	
	public Geocoding getGeocoding() {
		return geocoding;
	}

	public void setGeocoding(Geocoding geocoding) {
		this.geocoding = geocoding;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Geocoding {
		
		private String[] errors;
		
		public String[] getErrors() {
			return this.errors;
		}
		
		public void setErrors(String[] errors) {
			this.errors = errors;
		}
	}

}
