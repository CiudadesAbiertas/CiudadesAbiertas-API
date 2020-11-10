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
@Table(name = "bus_servicecalendar")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAUTOB, propiedad = "ServiceCalendar")
@PathId(value="/autobus/service-calendar")
public class ServiceCalendar implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = -603294809254888761L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador del ServiceCalendar. Ejemplo: 138")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre del ServiceCalendar. Ejemplo: Línea 6")
@CsvBindByPosition(position=2)
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;

@ApiModelProperty(value = "Descripción del ServiceCalendar. Ejemplo: La línea 6 de la EMT de Madrid une la plaza de Jacinto Benavente con el barrio de Orcasitas, en el distrito de Usera.")
@CsvBindByPosition(position=3)	
@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "description")
private String description;



@ApiModelProperty(value = "Nombre corto de la línea. Ejemplo: 6")
@CsvBindByPosition(position=4)
@CsvBindByName(column="shortName", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMCOMMONS, propiedad = "shortName")
private String shortName;



@ApiModelProperty(value = "La fecha y hora (en formato fecha ISO 8601). Ejemplo: 2020-03-31 08:00:00")
@CsvBindByPosition(position=5)
@CsvBindByName(column="from")
@CsvDate(Constants.DATE_TIME_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "from" ,typeURI=Context.XSD_URI+"dateTime")
private Date from;

@ApiModelProperty(value = "La fecha y hora  (en formato fecha ISO 8601). Ejemplo: 2020-03-31 08:00:00")
@CsvBindByPosition(position=6)
@CsvBindByName(column="to")
@CsvDate(Constants.DATE_TIME_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "to" ,typeURI=Context.XSD_URI+"dateTime")
private Date to;




public ServiceCalendar() {
}

public ServiceCalendar(ServiceCalendar copia) {

  this.ikey = copia.ikey;
  this.id = copia.id;
  this.description = copia.description;
  this.title = copia.title;
  this.shortName = copia.shortName;
  this.from = copia.from;
  this.to = copia.to;
}

public ServiceCalendar(ServiceCalendar copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("title")) {
	this.title = copia.title;
  }
  if (attributesToSet.contains("description")) {
	this.description = copia.description;
  }

  if (attributesToSet.contains("description")) {
	this.description = copia.description;
  }

  if (attributesToSet.contains("shortName")) {
	this.shortName = copia.shortName;
  }
  
  if (attributesToSet.contains("from")) {
		this.from = copia.from;
  }
  
  if (attributesToSet.contains("to")) {
		this.to = copia.to;
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

@Column(name = "description", length = 4000)
public String getDescription() {
  return this.description;
}

public void setDescription(String description) {
  this.description = description;
}

@Column(name = "title", length = 200)
public String getTitle() {
  return this.title;
}

public void setTitle(String title) {
  this.title = title;
}



@Column(name = "short_name", length = 200)
public String getShortName() {
  return this.shortName;
}

public void setShortName(String shortName) {
  this.shortName = shortName;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "from_day", length = 19)
public Date getFrom() {
	return from;
}

public void setFrom(Date from) {
	this.from = from;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "to_day", length = 19)
public Date getTo() {
	return to;
}

public void setTo(Date to) {
	this.to = to;
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
  
  if (!Util.validValue(this.getTitle())) {
	result.add("Title is not valid [Title:" + this.getTitle() + "]");
  }

  return result;
}

@Override
public Map<String, String> prefixes() {
  Map<String,String> prefixes=new HashMap<String,String>();				
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
	return "ServiceCalendar [id=" + id + ", title=" + title + ", description=" + description + ", shortName="
			+ shortName + ", from=" + from + ", to=" + to + "]";
}





}
