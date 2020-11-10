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
@Table(name = "bus_vehiclejourney")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "Vehiclejourney")
@PathId(value="/autobus/vehiclejourney")
public class VehicleJourney  implements java.io.Serializable, RDFModel{
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del Vehicle journey. Ejemplo: 6a1")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Duración programada del viaje de un vehículo. Ejemplo: P1D")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="journeyDuration", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "journeyDuration")
	private String journeyDuration;
	
	@JsonFormat(pattern = Constants.TIME_FORMAT)
	@ApiModelProperty(value = "Hora de salida programada del viaje de un vehículo. Ejemplo: 09:00:00")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="departureTime", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "departureTime", typeURI=Context.XSD_URI+"time")
	private String departureTime;
	
	@ApiModelProperty(value = "Esta propiedad permite describir el tipo de dirección para una Ruta. Ejemplo: outbound")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="directionType", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "directionType")
	@RdfExternalURI(inicioURI="http://w3id.org/transmodel/kos/journeys/direction-type/",finURI="directionType", urifyLevel = 1)
	private String directionType;
	
	@ApiModelProperty(value = "Esta propiedad conecta algunas clases con otras que hacen posible su funcionamiento. Ejemplo: 6a2")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="madeUsing", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "madeUsing")
	@RdfExternalURI(inicioURI="/autobus/journeypattern/",finURI="madeUsing", urifyLevel = 1)
	private String madeUsing;
	
	@ApiModelProperty(value = "Relación entre el Viaje de un Vehículo y el Día Tipo. Ejemplo: laborable")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="workedOn", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "workedOn")
	@RdfExternalURI(inicioURI="/autobus/daytype/",finURI="workedOn", urifyLevel = 1)
	private String workedOn;
	
	@ApiModelProperty(value = "Un Viaje de Vehículo compuesto de indicaciones de viaje en cabecera de línea donde se indican las salidas del primero y último viaje. Ejemplo: 6a1-laborable")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="composedOf", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "composedOf")
	@RdfExternalURI(inicioURI="/autobus/headwayjourneygroup/",finURI="composedOf", urifyLevel = 1)
	private String composedOf;

	public VehicleJourney() {

	}
	
	public VehicleJourney(VehicleJourney copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.journeyDuration = copia.journeyDuration;
		this.departureTime = copia.departureTime;
		this.directionType = copia.directionType;
		this.departureTime = copia.departureTime;
		this.madeUsing = copia.madeUsing;
		this.workedOn = copia.workedOn;
		this.composedOf = copia.composedOf;
	}
	
	public VehicleJourney(VehicleJourney copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("journeyDuration")) {
			this.journeyDuration = copia.journeyDuration;
		}
		if (attributesToSet.contains("departureTime")) {
			this.departureTime = copia.departureTime;
		}
		if (attributesToSet.contains("directionType")) {
			this.directionType = copia.directionType;
		}
		if (attributesToSet.contains("departureTime")) {
			this.departureTime = copia.departureTime;
		}
		if (attributesToSet.contains("madeUsing")) {
			this.madeUsing = copia.madeUsing;
		}
		if (attributesToSet.contains("workedOn")) {
			this.workedOn = copia.workedOn;
		}
		if (attributesToSet.contains("composedOf")) {
			this.composedOf = copia.composedOf;
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
	
	@Column(name = "journey_duration", length = 50)
	public String getJourneyDuration() {
	  return this.journeyDuration;
	}

	public void setJourneyDuration(String journeyDuration) {
	  this.journeyDuration = journeyDuration;
	}
	
	@Column(name = "departure_time", length = 8)
	public String getDepartureTime() {
	  return this.departureTime;
	}

	public void setDepartureTime(String departureTime) {
	  this.departureTime = departureTime;
	}

	@Column(name = "direction_type", length = 50)
	public String getDirectionType() {
	  return this.directionType;
	}

	public void setDirectionType(String directionType) {
	  this.directionType = directionType;
	}
	
	@Column(name = "made_using", length = 50)
	public String getMadeUsing() {
		return madeUsing;
	}

	public void setMadeUsing(String madeUsing) {
		this.madeUsing = madeUsing;
	}
	
	@Column(name = "worked_on", length = 50)
	public String getWorkedOn() {
		return workedOn;
	}

	public void setWorkedOn(String workedOn) {
		this.workedOn = workedOn;
	}
	
	@Column(name = "composed_of", length = 50)
	public String getComposedOf() {
		return composedOf;
	}

	public void setComposedOf(String composedOf) {
		this.composedOf = composedOf;
	}

	@Override
	public String toString() {
		return "Vehiclejourney [ikey=" + ikey + ", id=" + id + ", journeyDuration=" + journeyDuration
				+ ", departureTime=" + departureTime + ", directionType=" + directionType + ", madeUsing=" + madeUsing
				+ ", workedOn=" + workedOn + ", composedOf=" + composedOf + "]";
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
		return (T) cloneClass((VehicleJourney) copia, listado);
	}
	
	public VehicleJourney cloneClass(VehicleJourney copia, List<String> attributesToSet) {

		VehicleJourney obj = new VehicleJourney(copia,attributesToSet);		

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
	 * Si existiera mas de uno se incluye aqui, en este caso solo exite earliestTime
	 */
	public String validarParam() {
		String result=null;
		if (getDepartureTime()!=null && !"".equals(getDepartureTime())) {
			if (!RegularExpressions.isFormatTime(getDepartureTime())) {
				result="Format Error (earliestTime:"+getDepartureTime()+") Not valid Format (HH:mm:ss)";
			}
		}
		
		return result;
	}
}
