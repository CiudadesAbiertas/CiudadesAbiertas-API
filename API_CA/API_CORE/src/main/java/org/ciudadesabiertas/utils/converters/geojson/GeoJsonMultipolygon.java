package org.ciudadesabiertas.utils.converters.geojson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonMultipolygon {

	private static final String MULTIPOLYGON = "MultiPolygon";

	private String type;
	
	private List<BigDecimal> coordinates;
		

	public GeoJsonMultipolygon() {
		super();
		this.type = MULTIPOLYGON;		
		this.coordinates=new ArrayList<BigDecimal>();	
	}
	
	public GeoJsonMultipolygon(GeoJsonMultipolygon copia) {
		super();
		this.type = MULTIPOLYGON;
		this.coordinates = copia.coordinates;		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BigDecimal> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<BigDecimal> coordinates) {
		this.coordinates = coordinates;
	}

	

	
	
	
}
