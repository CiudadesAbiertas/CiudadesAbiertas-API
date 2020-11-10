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
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 */
@Entity
@ApiModel
@Table(name = "bus_scheduled_stop_point")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "ScheduledStopPoint")
@PathId(value = "/autobus/scheduledstoppoint")
public class ScheduledStopPoint implements java.io.Serializable, RDFModel {

private static final long serialVersionUID = 2154181112220445744L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador del punto de ruta. Ejemplo: BUSDAYTYP01")
@CsvBindByPosition(position = 1)
@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Origen del Centroide. Ejemplo: 4608 (parada)")
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "functionalCentroidFor", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMCOMMONS, propiedad = "aFunctionalCentroidFor")
@RdfExternalURI(inicioURI="/autobus/parada/", finURI="functionalCentroidFor")
private String functionalCentroidFor;

@ApiModelProperty(value = "Propiedad que indica si la parada es de subida. Ejemplo: true")
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "alighting", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "alighting", typeURI=Context.XSD_URI+"boolean")
private Boolean alighting;

@ApiModelProperty(value = "Propiedad que indica si la parada es de bajada. Ejemplo: true")
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "boarding", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "boarding", typeURI=Context.XSD_URI+"boolean")
private Boolean boarding;

@ApiModelProperty(value = "Nombre del area de parada. Ejemplo: Cristo Rey")
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "titleStopArea", format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "titleStopArea")
@RdfBlankNode(tipo = Context.TMJOURNEY_URI + "StopArea", propiedad = Context.TMCOMMONS_URI + "includedIn", nodoId = "stopArea")
private String titleStopArea;

public ScheduledStopPoint() {
}

public ScheduledStopPoint(ScheduledStopPoint copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.functionalCentroidFor = copia.functionalCentroidFor;
  this.alighting = copia.alighting;
  this.boarding = copia.boarding;
  this.titleStopArea = copia.titleStopArea;
}

public ScheduledStopPoint(ScheduledStopPoint copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }

  if (attributesToSet.contains("functionalCentroidFor")) {
	this.functionalCentroidFor = copia.functionalCentroidFor;

  }

  if (attributesToSet.contains("alighting")) {
	this.alighting = copia.alighting;

  }

  if (attributesToSet.contains("boarding")) {
	this.boarding = copia.boarding;

  }

  if (attributesToSet.contains("titleStopArea")) {
	this.titleStopArea = copia.titleStopArea;
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

@Column(name = "alighting")
public Boolean getAlighting() {
  return this.alighting;
}

public void setAlighting(Boolean alighting) {
  this.alighting = alighting;
}

@Column(name = "boarding")
public Boolean getBoarding() {
  return this.boarding;
}

public void setBoarding(Boolean boarding) {
  this.boarding = boarding;
}

@Column(name = "title_stop_area", length = 200)
public String getTitleStopArea() {
  return this.titleStopArea;
}

public void setTitleStopArea(String titleStopArea) {
  this.titleStopArea = titleStopArea;
}

@Column(name = "functional_centroid_for", length = 50)
public String getFunctionalCentroidFor() {
  return functionalCentroidFor;
}

public void setFunctionalCentroidFor(String functionalCentroidFor) {
  this.functionalCentroidFor = functionalCentroidFor;
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((ScheduledStopPoint) copia, listado);
}

// Constructor copia con lista de attributos a settear
public ScheduledStopPoint cloneClass(ScheduledStopPoint copia, List<String> attributesToSet) {

  ScheduledStopPoint obj = new ScheduledStopPoint(copia, attributesToSet);

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

  return prefixes;
}

@Override
public String toString() {
  return "ScheduledStopPoint [ikey=" + ikey + ", id=" + id + ", functionalCentroidFor=" + functionalCentroidFor + ", alighting=" + alighting + ", boarding=" + boarding + ", titleStopArea=" + titleStopArea + "]";
}

}
