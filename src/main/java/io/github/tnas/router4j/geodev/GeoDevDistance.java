package io.github.tnas.router4j.geodev;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Metric;
import io.github.tnas.router4j.Point;

public class GeoDevDistance implements Distance {

    private GeoDevPoint from;

    private GeoDevPoint to;

    @JsonProperty("distance")
    private double value;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Metric metric;

    @Override
    public Point getFrom() {
        return from;
    }

    public void setFrom(GeoDevPoint from) {
        this.from = from;
    }

    @Override
    public Point getTo() {
        return to;
    }

    public void setTo(GeoDevPoint to) {
        this.to = to;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "from=" + from +
                ", to=" + to +
                ", value=" + value +
                ", metric=" + metric +
                '}';
    }
}
