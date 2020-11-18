package org.ciudadesabiertas.utils.converters.geojson;

import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONObject;

public class GeoJsonFeatureMultiline {

	private static final String FEATURE = "Feature";

	private String type;
	
	private JSONObject geometry;
	
	private Map<String, Object> properties;

	public GeoJsonFeatureMultiline() {
		super();
		this.type = FEATURE;		
		this.geometry=new JSONObject();
		this.properties=new TreeMap<String, Object>();
	}
	
	public GeoJsonFeatureMultiline(GeoJsonFeatureMultiline copia) {
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

	public JSONObject getGeometry() {
		return geometry;
	}

	public void setGeometry(JSONObject geometry) {
		this.geometry = geometry;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	
	
}
