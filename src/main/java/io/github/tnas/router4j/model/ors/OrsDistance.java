package io.github.tnas.router4j.model.ors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tnas.router4j.model.Distance;
import io.github.tnas.router4j.model.Metric;
import io.github.tnas.router4j.model.Point;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsDistance implements Distance {

    @JsonProperty("distances")
    private double[][] values;

    private List<OrsPoint> destinations;

    private List<OrsPoint> sources;

    private Metadata metadata;

    @Override
    public Point getFrom() {
        return this.sources.get(0);
    }

    @Override
    public Point getTo() {
        return this.destinations.get(1);
    }

    @Override
    public double getValue() {
        return this.values[0][1];
    }

    @Override
    public Metric getMetric() {
        return this.metadata.query.units;
    }

    public double[][] getValues() {
        return values;
    }

    public void setValues(double[][] values) {
        this.values = values;
    }

    public List<OrsPoint> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<OrsPoint> destinations) {
        this.destinations = destinations;
    }

    public List<OrsPoint> getSources() {
        return sources;
    }

    public void setSources(List<OrsPoint> sources) {
        this.sources = sources;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "from=" + getFrom() +
                ", to=" + getTo() +
                ", value=" + getValue() +
                ", metric=" + getMetric() +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {

        private Query query;

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Query {

            @JsonFormat(shape = JsonFormat.Shape.OBJECT)
            private Metric units;

            public Metric getUnits() {
                return units;
            }

            public void setUnits(Metric units) {
                this.units = units;
            }
        }
    }
}
