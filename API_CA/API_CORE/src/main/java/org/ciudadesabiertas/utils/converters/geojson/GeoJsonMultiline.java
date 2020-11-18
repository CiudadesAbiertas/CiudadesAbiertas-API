package org.ciudadesabiertas.utils.converters.geojson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonMultiline {

	private static final String MULTILINE = "MultiLine";

	private String type;
	
	private List<BigDecimal> coordinates;
		

	public GeoJsonMultiline() {
		super();
		this.type = MULTILINE;		
		this.coordinates=new ArrayList<BigDecimal>();	
	}
	
	public GeoJsonMultiline(GeoJsonMultiline copia) {
		super();
		this.type = MULTILINE;
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
