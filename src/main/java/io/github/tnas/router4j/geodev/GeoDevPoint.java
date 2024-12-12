package io.github.tnas.router4j.geodev;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.tnas.router4j.Point;

public class GeoDevPoint implements Point {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    public GeoDevPoint() { }

    public GeoDevPoint(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
