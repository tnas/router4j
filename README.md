# router4j
A Java library for route distance calculator supported by free APIs

## Usage in a Maven Project

Add this dependency to the `pom.xml`:

```xml
<dependency>
    <groupId>io.github.tnas</groupId>
    <artifactId>router4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Under the hood
The lib relies on two free APIs:

[Geospatial API](https://geo.dev) and [Open Route Service](https://openrouteservice.org/).

The Geospatial API does not require a key (token) to call its endpoints.

Open Route Service, on the other hand, requires a registration.

## Geospatial API

```java
Point from = PointBuilder.newBuilder()
	.apiType(ApiType.GEO_DEV)
	.latitude(-25.49509).longitude(-49.28433)
	.build();

Point to = PointBuilder.newBuilder()
	.apiType(ApiType.GEO_DEV)
	.latitude(-23.3045).longitude(-50.34063)
	.build();

Distance distance = geoDevRouterApi.getRoadDistance(from, to, null);

assertEquals(266.02926599711407, distance.getValue());
assertEquals(-25.49509, distance.getFrom().getLatitude());
assertEquals(-49.28433, distance.getFrom().getLongitude());
assertEquals(-23.3045, distance.getTo().getLatitude());
assertEquals(-50.34063, distance.getTo().getLongitude());
assertEquals(Metric.KM, distance.getMetric());
```

## Open Route Service API

```java
Locality locality = orsRouterApi.getLocality("Belo Horizonte", "Minas Gerais", apiKey);

assertEquals(1, locality.getLocations().length);
assertEquals(-43.959502, locality.getLocations()[0].getPoint().getLongitude());
assertEquals(-19.914319, locality.getLocations()[0].getPoint().getLatitude());
assertEquals("Belo Horizonte", locality.getLocations()[0].getName());
assertEquals("Minas Gerais", locality.getLocations()[0].getRegion());
```