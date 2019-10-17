package org.ciudadesAbiertas.rdfGeneratorZ;




public class Formato  {
	private String nombre;
	private TransformadorGenerico transformador;
	private String responseContentType;
	private String extension;
	private boolean paraInforme;
	
	public Formato() {
		super();
	
	}
	
	
	public Formato(String nombre, TransformadorGenerico transformador,
			String responseContentType, String extesion, boolean paraInforme) {
		super();
		this.nombre = nombre;
		this.transformador = transformador;
		this.responseContentType = responseContentType;
		this.extension = extesion;
		this.paraInforme = paraInforme;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public TransformadorGenerico getTransformador() {
		return transformador;
	}
	public void setTransformador(TransformadorGenerico transformador) {
		this.transformador = transformador;
	}
	public String getResponseContentType() {
		return responseContentType;
	}
	public void setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public boolean isParaInforme() {
		return paraInforme;
	}
	public void setParaInforme(boolean paraInforme) {
		this.paraInforme = paraInforme;
	}
	@Override
	public String toString() {
		return "Formato [nombre=" + nombre 
				+ ", transformador=" + transformador
				+ ", responseContentType=" + responseContentType
				+ ", extension=" + extension + ", paraInforme=" + paraInforme
				+ "]";
	}
	
	
	
}
