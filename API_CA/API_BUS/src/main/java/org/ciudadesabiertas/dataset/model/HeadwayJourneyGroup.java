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
import org.ciudadesabiertas.utils.RegularExpressions;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "bus_headwayjourneygroup")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "HeadwayJourneyGroup")
@PathId(value="/autobus/headwayjourneygroup")
public class HeadwayJourneyGroup  implements java.io.Serializable, RDFModel{
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del operador. Ejemplo: 138a1-laborable")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	@ApiModelProperty(value = "La hora de inicio de un Viaje de un Vehículo. Ejemplo: 06:15:00")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="firstDepartureTime", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "firstDepartureTime", typeURI=Context.XSD_URI+"time")
	private String firstDepartureTime;
	
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	@ApiModelProperty(value = "La hora de fin de un Viaje de un Vehículo. Ejemplo: 23:30:00")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="lastDepartureTime", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "lastDepartureTime", typeURI=Context.XSD_URI+"time")
	private String lastDepartureTime;
	
	@ApiModelProperty(value = "Un grupo de viajes de cabecera de línea con un intervalo desde cabecera de línea Ejemplo: 138-laborable")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="madeUsing", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "madeUsing")
	@RdfExternalURI(inicioURI="/autobus/headwayinterval/",finURI="determinedBy", urifyLevel = 1)
	private String determinedBy;
	
	public HeadwayJourneyGroup() {

	}
	
	public HeadwayJourneyGroup(HeadwayJourneyGroup copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.firstDepartureTime = copia.firstDepartureTime;
		this.lastDepartureTime = copia.lastDepartureTime;
		this.determinedBy = copia.determinedBy;
	}
	
	public HeadwayJourneyGroup(HeadwayJourneyGroup copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("firstDepartureTime")) {
			this.firstDepartureTime = copia.firstDepartureTime;
		}
		if (attributesToSet.contains("lastDepartureTime")) {
			this.lastDepartureTime = copia.lastDepartureTime;
		}
		if (attributesToSet.contains("determinedBy")) {
			this.determinedBy = copia.determinedBy;
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
	
	@Column(name = "first_departure_time", length = 8)
	public String getFirstDepartureTime() {
	  return this.firstDepartureTime;
	}

	public void setFirstDepartureTime(String firstDepartureTime) {
	  this.firstDepartureTime = firstDepartureTime;
	}

	@Column(name = "last_departure_time", length = 8)
	public String getLastDepartureTime() {
	  return this.lastDepartureTime;
	}

	public void setLastDepartureTime(String lastDepartureTime) {
	  this.lastDepartureTime = lastDepartureTime;
	}
	
	@Column(name = "determined_by", length = 50)
	public String getDeterminedBy() {
		return determinedBy;
	}

	public void setDeterminedBy(String determinedBy) {
		this.determinedBy = determinedBy;
	}

	@Override
	public String toString() {
		return "HeadwayJourneyGroup [ikey=" + ikey + ", id=" + id + ", firstDepartureTime=" + firstDepartureTime
				+ ", lastDepartureTime=" + lastDepartureTime + ", determinedBy=" + determinedBy + "]";
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
		return (T) cloneClass((HeadwayJourneyGroup) copia, listado);
	}
	
	public HeadwayJourneyGroup cloneClass(HeadwayJourneyGroup copia, List<String> attributesToSet) {

		HeadwayJourneyGroup obj = new HeadwayJourneyGroup(copia,attributesToSet);		

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
	
	/*
	 * Metodo que valida los campos Hora
	 * Si existiera mas de uno se incluye aqui, en este caso solo exite FirstDepartureTime y LastDepartureTime
	 */
	public String validarParam() {
		String result=null;
		if (getFirstDepartureTime()!=null && !"".equals(getFirstDepartureTime())) {
			if (!RegularExpressions.isFormatTime(getFirstDepartureTime())) {
				result="Format Error (FirstDepartureTime:"+getFirstDepartureTime()+") Not valid Format (HH:mm:ss)";
			}
		}
		
		if (getLastDepartureTime()!=null && !"".equals(getLastDepartureTime())) {
			if (!RegularExpressions.isFormatTime(getLastDepartureTime())) {
				result=result+"\n"+"Format Error (LastDepartureTime:"+getLastDepartureTime()+") Not valid Format (HH:mm:ss)";
			}
		}
		
		return result;
	}
}
