package io.github.tnas.router4j;

public interface RouterApi {

	Distance getRoadDistance(Point from, Point to, String... apiKey);
}
