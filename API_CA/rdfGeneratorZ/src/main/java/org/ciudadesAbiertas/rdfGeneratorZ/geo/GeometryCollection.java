package org.ciudadesAbiertas.rdfGeneratorZ.geo;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicUpdate;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;




@XmlRootElement(name="geometries")
@Entity
@DynamicUpdate
@XmlAccessorType(XmlAccessType.FIELD)
@org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf
public class GeometryCollection extends Geometria {
	
	
	@RdfMultiple({@Rdf(uri = "@id"), @Rdf(contexto = Context.RDF, propiedad = "type")})
	private String type = Geometria.GEOMETRYCOLLECTION;

	Geometria[] geometries;
	
//	@RdfMultiple({@Rdf(contexto = Context.GEOSPARQL, propiedad = "wktLiteral"), @Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT")})	
//	private String asWKT = Geometria.POLIGON;
//	
//	@RdfMultiple({@Rdf(contexto = Context.GEOSPARQL, propiedad = "asGML"), @Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT")})	
//	private String asGML = Geometria.POLIGON;
	
	public GeometryCollection() {
		super();
	}
	public GeometryCollection(Geometria[] geometries) {
		super();
		this.geometries = geometries;
	}
	public GeometryCollection(String type, Geometria[] geometries) {
		super();
		this.type = type;
		this.geometries = geometries;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Geometria[] getGeometries() {
		return geometries;
	}
	public void setGeometries(Geometria[] geometries) {
		this.geometries = geometries;
	}
	@Override
	public String toString() {
		return "Geometry [type=" + type + ", geometries=" + geometries + "]";
	}
	
	@Override
	public String asJson(String srsname) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("{\"type\":\"GeometryCollection\",\"geometries\":[");
		conCaracterYComas(srsname, retorno);
		retorno.append("]}");
		return retorno.toString();
	}
	private void conCaracterYComas(String srsname, StringBuilder retorno) {
		boolean primero = true;
		for (Geometria g : this.geometries) {
			if (!primero) {
				retorno.append(",");
			}
			primero = false;
			if (Geometria.POINT.equals(g.getType())) {
				if (g.getCoordinates() instanceof ArrayList) {
					ArrayList a = (ArrayList) g.getCoordinates();
					Punto p = new Punto(new Double[]{(Double)a.get(0), (Double)a.get(1)});
					retorno.append(p.asJson(srsname));
				}
			} else if (Geometria.LINESTRING.equals(g.getType())) {
				if (g.getCoordinates() instanceof ArrayList) {
					ArrayList a = (ArrayList) g.getCoordinates();
					
					Double[][] d = new Double[a.size()][2];
					for (int i = 0; i < d.length; i ++) {
						ArrayList coord = (ArrayList)a.get(i);
						d[i] = new Double[]{(Double)coord.get(0), (Double)coord.get(1)};
					}
					LineString l = new LineString(d);
					retorno.append(l.asJson(srsname));
				}
			} else if (Geometria.POLIGON.equals(g.getType())) {
				if (g.getCoordinates() instanceof ArrayList) {
					ArrayList a = (ArrayList) g.getCoordinates();
					
					Double[][] d = new Double[a.size()][2];
					for (int i = 0; i < d.length; i ++) {
						ArrayList coord = (ArrayList)a.get(i);
						d[i] = new Double[]{(Double)coord.get(0), (Double)coord.get(1)};
					}
					Polygon l = new Polygon(d);
					retorno.append(l.asJson(srsname));
				}
			}  
		}
	}

	
}
