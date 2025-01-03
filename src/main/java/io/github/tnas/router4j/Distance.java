package io.github.tnas.router4j;

public interface Distance {

    Point getFrom();

    Point getTo();

    double getValue();

    Metric getMetric();
}
