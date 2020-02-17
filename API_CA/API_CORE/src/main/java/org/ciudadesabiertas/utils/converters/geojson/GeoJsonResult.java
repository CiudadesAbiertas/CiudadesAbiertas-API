package org.ciudadesabiertas.utils.converters.geojson;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonResult<T> {

	private static final String FEATURE_COLLECTION = "FeatureCollection";

	private String type;
	
	private List<T> features;
		

	public GeoJsonResult() {
		super();
		this.type = FEATURE_COLLECTION;		
		this.features=new ArrayList<T>();	
	}
	
	public GeoJsonResult(GeoJsonResult<T> copia) {
		super();
		this.type = FEATURE_COLLECTION;
		this.features = copia.features;		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<T> getFeatures() {
		return features;
	}

	public void setFeatures(List<T> features) {
		this.features = features;
	}

	
	
	
}
