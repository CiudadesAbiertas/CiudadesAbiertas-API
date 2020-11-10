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
@Table(name = "bus_route")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "Route")
@PathId(value="/autobus/route")
public class Route implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador de la ruta. Ejemplo: BUSROU01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Una descripción del recurso dentro de un contexto dado. Ejemplo: Línea 6, comienzo en plaza de Jacinto Benavente y final en Orcasitas")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "description")
	private String description;
	
	@ApiModelProperty(value = "Esta propiedad permite describir el tipo de dirección para una Ruta. Ejemplo: outbound")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="directionType", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "directionType")
	@RdfExternalURI(inicioURI="http://w3id.org/transmodel/kos/journeys/direction-type/",finURI="directionType",urifyLevel = 1)
	private String directionType;
	
	@ApiModelProperty(value = "Esta propiedad conecta la ruta con la linea en la que trabaja. Ejemplo: 138")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="on", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "on")
	@RdfExternalURI(inicioURI="/autobus/linea/",finURI="onId", urifyLevel = 1)
	private String onId;

	public Route() {

	}
	
	public Route(Route copia) {
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.directionType = copia.directionType;
		this.onId = copia.onId;
	}
	
	
	public Route(Route copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;
		}
		if (attributesToSet.contains("directionType")) {
			this.directionType = copia.directionType;
		}
		if (attributesToSet.contains("onId")) {
			this.onId = copia.onId;
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
	
	@Column(name = "description", unique = true, nullable = true, length = 4000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "direction_type", unique = true, nullable = true, length = 200)
	public String getDirectionType() {
		return directionType;
	}

	public void setDirectionType(String directionType) {
		this.directionType = directionType;
	}
	
	@Column(name = "on_id", unique = true, nullable = true, length = 50)
	public String getOnId() {
		return onId;
	}

	public void setOnId(String onId) {
		this.onId = onId;
	}
	
	@Override
	public String toString() {
		return "Route [ikey=" + ikey + ", id=" + id + ", description=" + description + ", directionType="
				+ directionType + ", onId=" + onId + "]";
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
		return (T) cloneClass((Route) copia, listado);
	}
	
	public Route cloneClass(Route copia, List<String> attributesToSet) {

		Route obj = new Route(copia,attributesToSet);		

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
		if (!Util.validValue(this.getOnId())) {
			result.add("LegalName is not valid [LegalName:"+this.getOnId()+"]");
		}
		
		return result;
	}
}
