/**
 * 
 */
package org.ciudadesabiertas.dataset.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
import com.opencsv.bean.CsvDate;

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
@Table(name = "bus_daytypeassignment")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "DayTypeAssignment")
@PathId(value="/autobus/daytypeassignment")
public class DayTypeAssignment implements java.io.Serializable, RDFModel{
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del operador. Ejemplo: EMT")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Fecha de una Asignación de Día Tipo. Ejemplo: 2020-01-02")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="date")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "date", typeURI=Context.XSD_URI+"date")
	private Date date;
	
	@ApiModelProperty(value = "Propiedad que determina si una Asignación de Día Tipo está disponible o no. Ejemplo: true")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="isAvailable", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "isAvailable", typeURI=Context.XSD_URI+"boolean")
	private Boolean isAvailable;
	
	@ApiModelProperty(value = "La asignación de características operativas expresadas en Día Tipo a un Día Operativo particular dentro de un Calendario de Servicio. Ejemplo: laborable")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="specifying", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "specifying")
	@RdfExternalURI(inicioURI="/autobus/daytype/",finURI="specifying", urifyLevel = 1)
	private String specifying;
	
	@ApiModelProperty(value = "Establece la relación entre un Calendario de Servicio y la Asignación de Día Tipo. Ejemplo: 2020")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="forTheDefinitionOf", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "forTheDefinitionOf")
	@RdfExternalURI(inicioURI="/autobus/servicecalendar/",finURI="forTheDefinitionOf", urifyLevel = 1)
	private String forTheDefinitionOf;

	public DayTypeAssignment() {

	}
	
	public DayTypeAssignment(DayTypeAssignment copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.date = copia.date;
		this.isAvailable = copia.isAvailable;
		this.specifying = copia.specifying;
		this.forTheDefinitionOf = copia.forTheDefinitionOf;
	}
	
	public DayTypeAssignment(DayTypeAssignment copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("date")) {
			this.date = copia.date;
		}
		if (attributesToSet.contains("isAvailable")) {
			this.isAvailable = copia.isAvailable;
		}
		if (attributesToSet.contains("specifying")) {
			this.specifying = copia.specifying;
		}
		if (attributesToSet.contains("forTheDefinitionOf")) {
			this.forTheDefinitionOf = copia.forTheDefinitionOf;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_assignment", length = 26)
	public Date getDate() {
	  return this.date;
	}

	public void setDate(Date date) {
	  this.date = date;
	}

	@Column(name = "is_available")
	public Boolean getIsAvailable() {
	  return this.isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
	  this.isAvailable = isAvailable;
	}
	
	@Column(name = "specifying", length = 50)
	public String getSpecifying() {
		return specifying;
	}

	public void setSpecifying(String specifying) {
		this.specifying = specifying;
	}
	
	@Column(name = "for_the_definition_of", length = 50)
	public String getForTheDefinitionOf() {
		return forTheDefinitionOf;
	}

	public void setForTheDefinitionOf(String forTheDefinitionOf) {
		this.forTheDefinitionOf = forTheDefinitionOf;
	}

	@Override
	public String toString() {
		return "DayTypeAssignment [ikey=" + ikey + ", id=" + id + ", date=" + date + ", isAvailable=" + isAvailable
				+ ", specifying=" + specifying + ", forTheDefinitionOf=" + forTheDefinitionOf + "]";
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
		return (T) cloneClass((DayTypeAssignment) copia, listado);
	}
	
	public DayTypeAssignment cloneClass(DayTypeAssignment copia, List<String> attributesToSet) {

		DayTypeAssignment obj = new DayTypeAssignment(copia,attributesToSet);		

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
		
		
		return result;
	}
	
}
