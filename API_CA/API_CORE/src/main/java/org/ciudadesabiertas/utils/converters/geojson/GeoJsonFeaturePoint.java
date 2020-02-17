package org.ciudadesabiertas.utils.converters.geojson;

import java.util.Map;
import java.util.TreeMap;

public class GeoJsonFeaturePoint {

	private static final String FEATURE = "Feature";

	private String type;
	
	private GeoJsonPoint geometry;
	
	private Map<String, Object> properties;

	public GeoJsonFeaturePoint() {
		super();
		this.type = FEATURE;		
		this.geometry=new GeoJsonPoint();
		this.properties=new TreeMap<String, Object>();
	}
	
	public GeoJsonFeaturePoint(GeoJsonFeaturePoint copia) {
		super();
		this.type = FEATURE;
		this.geometry = copia.geometry;
		this.properties = copia.properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GeoJsonPoint getGeometry() {
		return geometry;
	}

	public void setGeometry(GeoJsonPoint geometry) {
		this.geometry = geometry;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	
	
}
