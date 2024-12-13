package io.github.tnas.router4j;

import io.github.tnas.router4j.geodev.GeoDevPoint;
import io.github.tnas.router4j.ors.OrsPoint;

public class PointBuilder {

    private ApiType apiType;

    private double longitude;

    private double latitude;

    private PointBuilder() { }

    public static PointBuilder newBuilder() {
        return new PointBuilder();
    }

    public PointBuilder apiType(ApiType type) {
        this.apiType = type;
        return this;
    }

    public PointBuilder longitude(double lon) {
        this.longitude = lon;
        return this;
    }

    public PointBuilder latitude(double lat) {
        this.latitude = lat;
        return this;
    }

    public Point build() {

        switch (this.apiType) {

            case GEO_DEV: {
                return new GeoDevPoint(this.latitude, this.longitude);
            }

            case ORS: {
                return new OrsPoint(this.longitude, this.latitude);
            }

            default: {
                return null;
            }
        }
    }
}
