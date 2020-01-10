package org.ciudadesabiertas.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonPoint {

	private static final String POINT = "Point";

	private String type;
	
	private List<BigDecimal> coordinates;
		

	public GeoJsonPoint() {
		super();
		this.type = POINT;		
		this.coordinates=new ArrayList<BigDecimal>();	
	}
	
	public GeoJsonPoint(GeoJsonPoint copia) {
		super();
		this.type = POINT;
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
