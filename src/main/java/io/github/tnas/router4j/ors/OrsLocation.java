package io.github.tnas.router4j.ors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.tnas.router4j.Location;
import io.github.tnas.router4j.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsLocation implements Location {

	private Geometry geometry;
	
	private Properties properties;
	
	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Point getPoint() {
		return new OrsPoint(this.geometry.coordinates[OrsPoint.LONGITUDE_INDEX], 
				this.geometry.coordinates[OrsPoint.LATITUDE_INDEX]);
	}

	@Override
	public String getName() {
		return this.properties.name;
	}

	@Override
	public String getRegion() {
		return this.properties.region;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Geometry {
		
		private double[] coordinates;

		public double[] getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(double[] coordinates) {
			this.coordinates = coordinates;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Properties {
		
		private String name;
		
		private String country;
		
		private String region;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}
	}

}
