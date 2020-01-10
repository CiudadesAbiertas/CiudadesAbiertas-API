package org.ciudadesabiertas.dataset.utils;

public class PairIdEPSG {

	private String id;
	private String EPSG;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEPSG() {
		return EPSG;
	}
	public void setEPSG(String ePSG) {
		EPSG = ePSG;
	}
	
	
	@Override
	public String toString() {
		return id + "," + EPSG;
	}
	
	
}
