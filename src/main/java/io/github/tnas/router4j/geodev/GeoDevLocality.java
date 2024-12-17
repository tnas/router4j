package io.github.tnas.router4j.geodev;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoDevLocality implements Locality {

    private GeoDevLocation[] results;

    @Override
    public Location[] getLocations() {
        return this.results;
    }

    public void setResults(GeoDevLocation[] results) {
        this.results = results;
    }
}
