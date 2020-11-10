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
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 */

@Entity
@ApiModel
@Table(name = "bus_realtime_passing_time")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAUTOB, propiedad = "RealTimePassingTime")
@PathId(value = "/autobus/realtimepassingtime")
public class RealTimePassingTime implements java.io.Serializable, RDFModel {

private static final long serialVersionUID = 5135615753881075096L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador del Tipo Día. Ejemplo: BUSDAYTYP01")
@CsvBindByPosition(position = 1)
@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Fecha hora de la observación. Ejemplo: 2020-05-13 12:45:05")
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "resultTime")
@CsvDate(Constants.DATE_TIME_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "resultTime",typeURI=Context.XSD_URI+"date")
private Date resultTime;

@ApiModelProperty(value = "Tiempo de llegada previsto. Ejemplo: PT20M")
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "expectedArrivalTime", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "expectedArrivalTime", typeURI = Context.XSD_URI + "duration")
private String expectedArrivalTime;

@ApiModelProperty(value = "Patron de viaje al aque pertenece. Ejemplo: 6a2-1918")
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "hasFeatureOfInterest", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.SOSA, propiedad = "hasFeatureOfInterest")
@RdfExternalURI(inicioURI = "/autobus/stoppointinjourneypattern/", finURI = "hasFeatureOfInterest")
private String hasFeatureOfInterest;

public RealTimePassingTime() {
}

public RealTimePassingTime(RealTimePassingTime copia) {
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.resultTime = copia.resultTime;
  this.expectedArrivalTime = copia.expectedArrivalTime;
  this.hasFeatureOfInterest = copia.hasFeatureOfInterest;
}

public RealTimePassingTime(RealTimePassingTime copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("resultTime")) {
	this.resultTime = copia.resultTime;
  }
  if (attributesToSet.contains("expectedArrivalTime")) {
	this.expectedArrivalTime = copia.expectedArrivalTime;
  }
  if (attributesToSet.contains("hasFeatureOfInterest")) {
	this.hasFeatureOfInterest = copia.hasFeatureOfInterest;
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
@Column(name = "result_time", length = 26)
public Date getResultTime() {
  return this.resultTime;
}

public void setResultTime(Date resultTime) {
  this.resultTime = resultTime;
}

@Column(name = "expected_arrival_time", length = 50)
public String getExpectedArrivalTime() {
  return this.expectedArrivalTime;
}

public void setExpectedArrivalTime(String expectedArrivalTime) {
  this.expectedArrivalTime = expectedArrivalTime;
}

@Column(name = "has_feature_of_interest", length = 50)
public String getHasFeatureOfInterest() {
  return hasFeatureOfInterest;
}

public void setHasFeatureOfInterest(String hasFeatureOfInterest) {
  this.hasFeatureOfInterest = hasFeatureOfInterest;
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((Authority) copia, listado);
}

// Constructor copia con lista de attributos a settear
public Authority cloneClass(Authority copia, List<String> attributesToSet) {

  Authority obj = new Authority(copia, attributesToSet);

  return obj;

}

@Override
public List<String> validate() {
  List<String> result = new ArrayList<String>();

  // Validamos campos Obligatorios ver si es posible obtener esta información
  // mediante anotaciones del modelo
  if (!Util.validValue(this.getId())) {
	result.add("Id is not valid [Id:" + this.getId() + "]");
  }

  if (!Util.validValue(this.getResultTime())) {
	result.add("ResultTime is not valid [ResultTime:" + this.getResultTime() + "]");
  }
  return result;
}

@Override
public Map<String, String> prefixes() {
  Map<String, String> prefixes = new HashMap<String, String>();
  prefixes.put(Context.XSD, Context.XSD_URI);
  prefixes.put(Context.DCT, Context.DCT_URI);
  prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
  prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
  prefixes.put(Context.TMCOMMONS, Context.TMCOMMONS_URI);
  prefixes.put(Context.ESAUTOB, Context.ESAUTOB_URI);
  prefixes.put(Context.SOSA, Context.SOSA_URI);
  return prefixes;
}

}
