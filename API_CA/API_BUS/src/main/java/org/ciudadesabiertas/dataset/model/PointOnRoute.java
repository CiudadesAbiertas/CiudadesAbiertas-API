/**
 * 
 */
package org.ciudadesabiertas.dataset.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bus_pointonroute")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "PointOnRoute")
@PathId(value="/autobus/pointonroute")
public class PointOnRoute implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del punto de ruta. Ejemplo: 6a-1918")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Orden de un Punto en una Secuencia de Enlaces (Link Sequence) y por tanto de un Punto en una Ruta o en cualquier otra estructura similar. Ejemplo: 1")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="orderPoint", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "orderPoint")
	private Integer orderPoint;
	
	@ApiModelProperty(value = "Para un Punto en una Secuencia de enlaces (o similares), esta propiedad representa la distancia desde el comienzo del correspondiente Patrón de Viaje o similares. Ejemplo: 0")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="distanceFromStart", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "distanceFromStart")
	private Double distanceFromStart;
	
	@ApiModelProperty(value = "Esta propiedad conecta el punto de ruta con la ruta en la que trabaja. Ejemplo: 6a")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="in", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "in")
	@RdfExternalURI(inicioURI="/autobus/route/",finURI="inId", urifyLevel = 1)
	private String inId;
	
	@ApiModelProperty(value = "Esta propiedad permite la conexión de un Punto con una Zona. Ejemplo: 1918")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="functionalCentroidFor", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "functionalCentroidFor")
	@RdfBlankNode(tipo=Context.TMJOURNEY_URI+"RoutePoint", propiedad=Context.TMJOURNEY_URI+"aFunctionalCentroidFor", nodoId="routePoint", inicioURI="/autobus/parada/")
	private String functionalCentroidFor;

	public PointOnRoute() {

	}
	
	public PointOnRoute(PointOnRoute copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.orderPoint = copia.orderPoint;
		this.distanceFromStart = copia.distanceFromStart;
		this.inId = copia.inId;
		this.functionalCentroidFor = copia.functionalCentroidFor;
	}
	
	public PointOnRoute(PointOnRoute copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("orderPoint")) {
			this.orderPoint = copia.orderPoint;
		}
		if (attributesToSet.contains("distanceFromStart")) {
			this.distanceFromStart = copia.distanceFromStart;
		}
		if (attributesToSet.contains("inId")) {
			this.inId = copia.inId;
		}
		if (attributesToSet.contains("functionalCentroidFor")) {
			this.functionalCentroidFor = copia.functionalCentroidFor;
		}
	}
	
	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "order_point")
	public Integer getOrderPoint() {
	  return this.orderPoint;
	}

	public void setOrderPoint(Integer orderPoint) {
	  this.orderPoint = orderPoint;
	}

	@Column(name = "distance_from_start", precision = 22, scale = 0)
	public Double getDistanceFromStart() {
	  return this.distanceFromStart;
	}

	public void setDistanceFromStart(Double distanceFromStart) {
	  this.distanceFromStart = distanceFromStart;
	}
	
	@Column(name = "in_id", unique = true, nullable = true, length = 50)
	public String getInId() {
		return inId;
	}

	public void setInId(String inId) {
		this.inId = inId;
	}
	
	@Column(name = "functional_centroid_for", unique = true, nullable = true, length = 50)
	public String getFunctionalCentroidFor() {
		return functionalCentroidFor;
	}

	public void setFunctionalCentroidFor(String functionalCentroidFor) {
		this.functionalCentroidFor = functionalCentroidFor;
	}

	@Override
	public String toString() {
		return "PointOnRoute [ikey=" + ikey + ", id=" + id + ", orderPoint=" + orderPoint + ", distanceFromStart="
				+ distanceFromStart + ", inId=" + inId + ", functionalCentroidFor=" + functionalCentroidFor + "]";
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((PointOnRoute) copia, listado);
	}
	
	public PointOnRoute cloneClass(PointOnRoute copia, List<String> attributesToSet) {

		PointOnRoute obj = new PointOnRoute(copia,attributesToSet);		

		return obj;

	}

	@Override
	public List<String> validate()
	{
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
		if (!Util.validValue(this.getInId())) {
			result.add("InId is not valid [LegalName:"+this.getInId()+"]");
		}
		if (!Util.validValue(this.getFunctionalCentroidFor())) {
			result.add("FunctionalCentroidFor is not valid [LegalName:"+this.getFunctionalCentroidFor()+"]");
		}
		
		return result;
	}
}
