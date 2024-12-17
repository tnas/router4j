package io.github.tnas.router4j.ors;

import io.github.tnas.router4j.ApiQuota;

public class OrsApiQuota implements ApiQuota {

	@Override
	public int roadDistancePerMinute() {
		return 40;
	}

	@Override
	public int roadDistancePerDay() {
		return 500;
	}

	@Override
	public int localityPerMinute() {
		return 100;
	}

	@Override
	public int localityPerDay() {
		return 1000;
	}

}
