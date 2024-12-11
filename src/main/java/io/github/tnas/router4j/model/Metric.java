package io.github.tnas.router4j.model;

public enum Metric {

    KM("km");

    private String unit;

    Metric(String unit) {
        this.unit = unit;
    }

    public String unit() {
        return this.unit;
    }
}
