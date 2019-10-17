package org.ciudadesAbiertas.rdfGeneratorZ.geo;

import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
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
@Rdf
public class Punto extends Geometria {
	
	
	@RdfMultiple({@Rdf(uri = "@id"), @Rdf(contexto = Context.RDF, propiedad = "type")})
	private String type = Geometria.POINT;
	
	@Rdf(contexto = Context.GEO, propiedad = "lat_lon")
	Double[] coordinates;
	
	@RdfMultiple({@Rdf(contexto = Context.XSD, propiedad = "double"), @Rdf(contexto = Context.GEO, propiedad = "lat")})
	private String lat = Geometria.POINT;
	
//	@Rdf(contexto = Context.GEO, propiedad = "long")
	@RdfMultiple({@Rdf(contexto = Context.XSD, propiedad = "double"), @Rdf(contexto = Context.GEO, propiedad = "long")})	
	private String lon = Geometria.POINT;
	
	@RdfMultiple({@Rdf(contexto = Context.GEOSPARQL, propiedad = "wktLiteral"), @Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT")})	
	private String asWKT = Geometria.POINT;
	
	public Punto() {
		super();
	}
	public Punto(Double[] coordinates) {
		super();
		this.coordinates = coordinates;
	}
	public Punto(String type, Double[] coordinates) {
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
	public Double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "Geometry [type=" + type + ", coordinates=" + coordinates + "]";
	}
	
	@Override
	public String asJson(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			return "{\"type\":\"Point\",\"coordinates\":[" + Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]) + "]}";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			return "{\"type\":\"Point\",\"coordinates\":[" + Punto.pasarAETRS89(getCoordinates()[0], getCoordinates()[1]) + "]}";
		} else {
			return "{\"type\":\"Point\",\"coordinates\":[" + getCoordinates()[0] + "," + getCoordinates()[1] + "]}";
		}
	}
	@Override
	public String asJsonLD(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			String wgs84 = Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]);
			StringTokenizer st = new StringTokenizer(wgs84, ",");
			return "{\"type\":\"http://www.w3.org/2003/01/geo/wgs84_pos#Point\",\"long\": " + st.nextToken() + ", \"lat\": " + st.nextToken() + "}";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			String etrs89 = Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]);
			StringTokenizer st = new StringTokenizer(etrs89, ",");
			return "{\"type\":\"http://www.opengis.net/ont/sf#Point\",\"asWKT\":\"<http://www.opengis.net/def/crs/EPSG/0/25830>Point("+ st.nextToken()+" "+ st.nextToken()+")"+"\"}";
		} else {
			return "{\"type\":\"http://www.opengis.net/ont/sf#Point\",\"asWKT\":\"<http://www.opengis.net/def/crs/EPSG/0/23030>Point("+ getCoordinates()[0]+" "+ getCoordinates()[1]+")"+"\"}";
		}
	}
	
	@Override
	public String asXML(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			return "<type>Point</type><coordinates>" + Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]) + "</coordinates>";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			return "<type>Point</type><coordinates>" + Punto.pasarAETRS89(getCoordinates()[0], getCoordinates()[1]) + "</coordinates>";
		} else {
			return "<type>Point</type><coordinates>" + getCoordinates()[0] + "," + getCoordinates()[1] + "</coordinates>";
		}
	}
	
	@Override
	public String asGEORSS(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			return "<georss:point>" + Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]) + "</georss:point>";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			return "<georss:point>" + Punto.pasarAETRS89(getCoordinates()[0], getCoordinates()[1]) + "</georss:point>";
		} else {
			return "<georss:point>" + getCoordinates()[0] + " " + getCoordinates()[1] + "</georss:point>";
		}
	}
	@Override
	public String asCSV(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			return "" + Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]) + "";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
				return "" + Punto.pasarAETRS89(getCoordinates()[0], getCoordinates()[1]) + "";
		} else {
			return "" + getCoordinates()[0] + " " + getCoordinates()[1] + "";
		}
	}
	@Override
	public String asHtml(String srsname) {
		if (srsname.equals(CheckeoParametros.SRSWGS84)) {
			return "" + Punto.pasarAWgs84(getCoordinates()[0], getCoordinates()[1]) + "";
		} else if (srsname.equals(CheckeoParametros.SRSETRS89)) {
			return "" + Punto.pasarAETRS89(getCoordinates()[0], getCoordinates()[1]) + "";
		} else {
			return "" + getCoordinates()[0] + " " + getCoordinates()[1] + "";
		}
	}
	
	public boolean formatoWgs84() {
		if (getCoordinates()[0] < 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getRdf() {
		return getRdf("true");
	}
	
	public Double[] getWgs84() {
		
		double[] coord = (double[])Geometria.pasarAWgs84Double(getCoordinates()[0], getCoordinates()[1]);	
		return new Double[]{coord[0], coord[1]};
	}
	
	public String getRdf(String property) {
	
		if (!formatoWgs84()) {
			double[] coord;
			if (CheckeoParametros.SRSETRS89.equals(this.getType())) {
				coord = (double[])Geometria.pasarEtrs89aAWgs84Double(getCoordinates()[0], getCoordinates()[1]);
			} else {
				coord = (double[])Geometria.pasarAWgs84Double(getCoordinates()[0], getCoordinates()[1]);	
			}
			
			this.setCoordinates(new Double[]{coord[0], coord[1]});
		}
		return "<div" + ("true".equals(property) ? " property=\"geo\"" : "") + " typeof=\"GeoCoordinates\">"
                + "<meta property=\"latitude\" content=\"" + getCoordinates()[1] + "\"/>"
                + "<meta property=\"longitude\" content=\"" + getCoordinates()[0] + "\"/>"
            + "</div>";
	}
}
