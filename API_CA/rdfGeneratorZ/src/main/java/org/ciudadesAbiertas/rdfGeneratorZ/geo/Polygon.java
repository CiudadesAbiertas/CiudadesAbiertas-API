package org.ciudadesAbiertas.rdfGeneratorZ.geo;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicUpdate;
import org.ciudadesAbiertas.rdfGeneratorZ.CheckeoParametros;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;




@XmlRootElement(name="geometry")
@Entity
@DynamicUpdate
@XmlAccessorType(XmlAccessType.FIELD)
@org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf
public class Polygon extends Geometria {
	
	
	@RdfMultiple({@Rdf(uri = "@id"), @Rdf(contexto = Context.RDF, propiedad = "type")})
	private String type = Geometria.POLIGON;
	
//	@Rdf(contexto = Context.GEO, propiedad = "lat_lon")
	Double[][] coordinates;
	
//	@RdfMultiple({@Rdf(contexto = Context.XSD, propiedad = "double"), @Rdf(contexto = Context.GEO, propiedad = "lat")})
//	private String lat = Geometria.POINT;
	
//	@RdfMultiple({@Rdf(contexto = Context.XSD, propiedad = "double"), @Rdf(contexto = Context.GEO, propiedad = "long")})	
//	private String lon = Geometria.POINT;
	
	@RdfMultiple({@Rdf(contexto = Context.GEOSPARQL, propiedad = "wktLiteral"), @Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT")})	
	private String asWKT = Geometria.POLIGON;
	
	@RdfMultiple({@Rdf(contexto = Context.GEOSPARQL, propiedad = "asGML"), @Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT")})	
	private String asGML = Geometria.POLIGON;
	
	public Polygon() {
		super();
	}
	public Polygon(Double[][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
	public Polygon(String type, Double[][] coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double[][] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Double[][] coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "Geometry [type=" + type + ", coordinates=" + coordinates + "]";
	}
	
	@Override
	public String asJson(String srsname) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("{\"type\":\"Polygon\",\"coordinates\":[[");
		conCaracterYComas(srsname, retorno, "[", "]");
		retorno.append("]]}");
		return retorno.toString();
	}
	private void conCaracterYComas(String srsname, StringBuilder retorno, String caracterInicio, String caracterFin) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			for(int i = 0; i < getCoordinates().length; i++) {
				if (i > 0) {
					retorno.append(",");
				}
				retorno.append(caracterInicio + Polygon.pasarAWgs84(getCoordinates()[i][0], getCoordinates()[i][1]) + caracterFin);
			}
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			for(int i = 0; i < getCoordinates().length; i++) {
				if (i > 0) {
					retorno.append(",");
				}
				retorno.append(caracterInicio + Polygon.pasarAETRS89(getCoordinates()[i][0], getCoordinates()[i][1]) + caracterFin);
			}
		} else {
			for(int i = 0; i < getCoordinates().length; i++) {
				if (i > 0) {
					retorno.append(",");
				}
				retorno.append(caracterInicio + getCoordinates()[i][0] + "," + getCoordinates()[i][1] + caracterFin);
			}
		}
	}
	
	private void sinCaracterYComas(String srsname, StringBuilder retorno) {
		
		for(int i = 0; i < getCoordinates().length; i++) {
			if (i > 0) {
				retorno.append(" ");
			}
			retorno.append(Polygon.pasarAWgs84(getCoordinates()[i][0], getCoordinates()[i][1]));
		}
			
	}
	
	@Override
	public String asJsonLD(String srsname) {
		StringBuilder retorno = new StringBuilder();
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			retorno.append("{\"type\":\"http://www.opengis.net/ont/gml#Polygon\",\"asGml\":\"<gml:LineString srsName='EPSG:4258' xmlns:gml='http://www.opengis.net/gml\'><gml:coordinates decimal='.' cs=',' ts=' '>");
			sinCaracterYComas(srsname, retorno);
			retorno.append("</gml:coordinates></gml:LineString>\"}");
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			retorno.append("{\"type\":\"http://www.opengis.net/ont/sf#Polygon\",\"asWKT\":\"<http://www.opengis.net/def/crs/EPSG/0/25830>Polygon(");
			conCaracterYComas(srsname, retorno, "(", ")");
			retorno.append(")\"}");
		} else{
			retorno.append("{\"type\":\"http://www.opengis.net/ont/sf#Polygon\",\"asWKT\":\"<http://www.opengis.net/def/crs/EPSG/0/23030>Polygon(");
			conCaracterYComas(srsname, retorno, "(", ")");
			retorno.append(")\"}");
		}
		return retorno.toString();
	}
	
	@Override
	public String asXML(String srsname) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("<type>Polygon</type><coordinates>");
		conCaracterYComas(srsname, retorno, " ", "");
		retorno.append("</coordinates>");
		return retorno.toString();
	}
	
	@Override
	public String asGEORSS(String srsname) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("<georss:polygon>");
		conCaracterYComas(srsname, retorno, " ", "");
		retorno.append("</georss:polygon>");
		return retorno.toString();

	}
	@Override
	public String asCSV(String srsname) {
		StringBuilder retorno = new StringBuilder();
		conCaracterYComas(srsname, retorno, "[", "]");
		return retorno.toString();
	}
	@Override
	public String asHtml(String srsname) {
		StringBuilder retorno = new StringBuilder();
		conCaracterYComas(srsname, retorno, "[", "]");
		return retorno.toString();
	}
	
	public boolean formatoWgs84() {
		if (getCoordinates()[0][0] < 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
