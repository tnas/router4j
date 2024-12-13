package io.github.tnas.router4j.geodev;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;

public class GeoDevRouterApi implements RouterApi {

    @Override
    public Distance getRoadDistance(Point from, Point to, String apiKey) {
        return null;
    }

    @Override
    public Locality getLocality(String name, String region, String apiKey) {
        return null;
    }

    @Override
    public Locality getLocality(String name, String region, String country, String apiKey) {
        return null;
    }
}
