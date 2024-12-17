package io.github.tnas.router4j.ors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrsDistance implements Distance {

	private static final int SOURCE_INDEX = 0;
	private static final int DESTINATION_INDEX = 1;
	
    @JsonProperty("distances")
    private double[][] values;

    private OrsPoint[] destinations;

    private OrsPoint[] sources;

    private Metadata metadata;

    @Override
    public Point getFrom() {
        return this.sources[SOURCE_INDEX];
    }

    @Override
    public Point getTo() {
        return this.destinations[DESTINATION_INDEX];
    }

    @Override
    public double getValue() {
        return this.values[SOURCE_INDEX][DESTINATION_INDEX];
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

    public OrsPoint[] getDestinations() {
        return destinations;
    }

    public void setDestinations(OrsPoint[] destinations) {
        this.destinations = destinations;
    }

    public OrsPoint[] getSources() {
        return sources;
    }

    public void setSources(OrsPoint[] sources) {
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
