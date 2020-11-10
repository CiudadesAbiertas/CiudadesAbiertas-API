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



@Entity
@ApiModel
@Table(name = "bus_journeypattern")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "JourneyPattern")
@PathId(value="/autobus/journeypattern")
public class JourneyPattern implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = -1689254030959663864L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;


@ApiModelProperty(value = "Identificador del patrón de viaje. Ejemplo: JPAT01")
@CsvBindByPosition(position = 1)
@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre del patrón de viaje. Ejemplo: 138a2")
@CsvBindByPosition(position=2)
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;

@ApiModelProperty(value = "Ruta en la que se encuentra el patrón de viaje. Ejemplo: 138a")
@CsvBindByPosition(position=3)
@CsvBindByName(column="on", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "on")
@RdfExternalURI(inicioURI="/autobus/route/",finURI="on", urifyLevel = 1)
private String on;

@ApiModelProperty(value = "Relación de un patrón de viaje de una ruta que se ha creado por una incidencia. Ejemplo: BUSINCI01")
@CsvBindByPosition(position=4)
@CsvBindByName(column="generadoPorIncidencia", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "generadoPorIncidencia")
@RdfExternalURI(inicioURI="/autobus/incidencia/",finURI="generadoPorIncidencia", urifyLevel = 1)
private String generadoPorIncidencia;



@ApiModelProperty(value = "Distancia total del patrón de viaje. Ejemplo: 23")
@CsvBindByPosition(position=5)
@CsvBindByName(column="distance")
@Rdf(contexto = Context.TMJOURNEY, propiedad = "distance", typeURI=Context.XSD_URI+"double")
private Double distance;

@ApiModelProperty(value = "Texto que se muestra normalmente en la parte frontal de un vehículo del servicio público de transporte. Ejemplo: Benavente")
@CsvBindByPosition(position=6)
@CsvBindByName(column="frontText", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "frontText")
@RdfBlankNode(tipo=Context.TMJOURNEY_URI+"DestinationDisplay", propiedad=Context.TMJOURNEY_URI+"prescribing", nodoId="frontText")
private String frontText;

public JourneyPattern() {
}

public JourneyPattern(JourneyPattern copia) {
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.title = copia.title;
  this.generadoPorIncidencia = copia.generadoPorIncidencia;
  this.distance = copia.distance;
  this.frontText = copia.frontText;
  this.on = copia.on;
}

public JourneyPattern(JourneyPattern copia, List<String> attributesToSet) {
  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("title")) {
	this.title = copia.title;
  }
  if (attributesToSet.contains("generadoPorIncidencia")) {
	this.generadoPorIncidencia = copia.generadoPorIncidencia;
  }
 
  if (attributesToSet.contains("distance")) {
	this.distance = copia.distance;
  }
  if (attributesToSet.contains("frontText")) {
	this.frontText = copia.frontText;
  }
  if (attributesToSet.contains("frontText")) {
	this.frontText = copia.frontText;
  }
  if (attributesToSet.contains("on")) {
	  this.on = copia.on;
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

@Column(name = "title", length = 200)
public String getTitle() {
  return this.title;
}

public void setTitle(String title) {
  this.title = title;
}

@Column(name = "distance", precision = 22, scale = 0)
public Double getDistance() {
  return this.distance;
}

public void setDistance(Double distance) {
  this.distance = distance;
}

@Column(name = "front_text", length = 50)
public String getFrontText() {
  return this.frontText;
}

public void setFrontText(String frontText) {
  this.frontText = frontText;
}

@Column(name = "on_id", length = 50)
public String getOn() {
  return on;
}

public void setOn(String on) {
  this.on = on;
}

@Column(name = "generado_por_incidencia", length = 50)
public String getGeneradoPorIncidencia() {
  return generadoPorIncidencia;
}

public void setGeneradoPorIncidencia(String generadoPorIncidencia) {
  this.generadoPorIncidencia = generadoPorIncidencia;
}





@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((JourneyPattern) copia, listado);
}

//Constructor copia con lista de attributos a settear
public JourneyPattern cloneClass(JourneyPattern copia, List<String> attributesToSet) {

  JourneyPattern obj = new JourneyPattern(copia, attributesToSet);

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
	
	if (!Util.validValue(this.getTitle())) {
		result.add("Title is not valid [Description:"+this.getTitle()+"]");
	}	
	
	return result;
}



@Override
public Map<String, String> prefixes() {
  Map<String, String> prefixes = new HashMap<String, String>();
  prefixes.put(Context.XSD, Context.XSD_URI);
  prefixes.put(Context.DCT, Context.DCT_URI);
  prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
  prefixes.put(Context.ESCJR, Context.ESCJR_URI);
  prefixes.put(Context.TMORG, Context.TMORG_URI);
  prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
  prefixes.put(Context.TMCOMMONS, Context.TMCOMMONS_URI);
  prefixes.put(Context.ESAUTOB, Context.ESAUTOB_URI);
  prefixes.put(Context.ESTRAF, Context.ESTRAF_URI);
  prefixes.put(Context.GEO, Context.GEO_URI);
  prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);	

  return prefixes;
}

@Override
public String toString() {
  return "JourneyPattern [ikey=" + ikey + ", id=" + id + ", title=" + title + ", on=" + on + ", generadoPorIncidencia=" + generadoPorIncidencia + ", distance=" + distance + ", frontText=" + frontText + "]";
}



}
