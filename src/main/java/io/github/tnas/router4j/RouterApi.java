package io.github.tnas.router4j;

import io.github.tnas.router4j.model.Distance;
import io.github.tnas.router4j.model.Point;

public interface RouterApi {

	Distance getRoadDistance(Point from, Point to, String... apiKey);
}
