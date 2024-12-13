package io.github.tnas.router4j.geodev;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.tnas.router4j.Location;
import io.github.tnas.router4j.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoDevLocation implements Location {

    private GeoDevObj obj;

    @Override
    public Point getPoint() {
        return this.obj.location;
    }

    @Override
    public String getName() {
        return this.obj.city;
    }

    @Override
    public String getRegion() {
        return this.obj.state;
    }

    public void setObj(GeoDevObj obj) {
        this.obj = obj;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoDevObj {

        private GeoDevPoint location;

        private String city;

        private String state;

        public GeoDevPoint getLocation() {
            return location;
        }

        public void setLocation(GeoDevPoint location) {
            this.location = location;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
