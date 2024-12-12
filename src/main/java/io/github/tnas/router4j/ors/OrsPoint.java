package io.github.tnas.router4j.ors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.tnas.router4j.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsPoint implements Point {

    static final int LONGITUDE_INDEX = 0;
    static final int LATITUDE_INDEX = 1;

    @JsonProperty("location")
    private double[] coordinates;
    
    public OrsPoint() { }
    
    public OrsPoint(double lon, double lat) {
    	this.coordinates = new double[]{ lon, lat};
    }

    @Override
    public double getLatitude() {
        return this.coordinates[LATITUDE_INDEX];
    }

    @Override
    public double getLongitude() {
        return this.coordinates[LONGITUDE_INDEX];
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                '}';
    }
}
