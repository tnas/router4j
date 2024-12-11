package io.github.tnas.router4j.model.ors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tnas.router4j.model.Point;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsPoint implements Point {

    private static final int LONGITUDE_INDEX = 0;
    private static final int LATITUDE_INDEX = 1;

    @JsonProperty("location")
    private List<Double> coordinates;

    public OrsPoint() {
        this.coordinates = new ArrayList<>();
    }

    public OrsPoint(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public double getLatitude() {
        return this.coordinates.get(LATITUDE_INDEX);
    }

    @Override
    public double getLongitude() {
        return this.coordinates.get(LONGITUDE_INDEX);
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
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
