package io.github.tnas.router4j.service;

import io.github.tnas.router4j.ApiQuota;
import io.github.tnas.router4j.ApiType;
import io.github.tnas.router4j.Distance;
import io.github.tnas.router4j.Locality;
import io.github.tnas.router4j.Point;
import io.github.tnas.router4j.RouterApi;
import io.github.tnas.router4j.geodev.GeoDevRouterApi;
import io.github.tnas.router4j.ors.OrsRouterApi;

import javax.ejb.Stateless;
import java.util.EnumMap;

@Stateless
public class RouterService {

    private final EnumMap<ApiType, RouterApi> routerApi;

    public RouterService() {
        this.routerApi = new EnumMap<>(ApiType.class);
        this.routerApi.put(ApiType.GEO_DEV, new GeoDevRouterApi());
        this.routerApi.put(ApiType.ORS, new OrsRouterApi());
    }

    public Distance getRoadDistance(Point from, Point to, String apiKey, ApiType type) {
        return this.routerApi.get(type).getRoadDistance(from, to, apiKey);
    }

    public Locality getLocality(String name, String region, String apiKey, ApiType type) {
        return this.routerApi.get(type).getLocality(name, region, apiKey);
    }
    
    public ApiQuota getQuota(ApiType type) {
    	return this.routerApi.get(type).getApiQuota();
    }
}
