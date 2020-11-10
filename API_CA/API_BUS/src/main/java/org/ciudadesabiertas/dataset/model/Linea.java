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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
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
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata) 
 */
@Entity
@ApiModel
@Table(name = "bus_linea")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAUTOB, propiedad = "Linea")
@PathId(value="/autobus/linea")
public class Linea implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = -607294817254888761L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador de la linea. Ejemplo: 138")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre de la línea. Ejemplo: Línea 6")
@CsvBindByPosition(position=2)
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;

@ApiModelProperty(value = "Descripción de la linea. Ejemplo: La línea 6 de la EMT de Madrid une la plaza de Jacinto Benavente con el barrio de Orcasitas, en el distrito de Usera.")
@CsvBindByPosition(position=3)	
@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "description")
private String description;



@ApiModelProperty(value = "Web con información de la línea. Ejemplo: https://www.emtmadrid.es/Bloques-EMT/EMT-BUS/Mi-linea-(1).aspx?linea=6&lang=es-ES")
@CsvBindByPosition(position=4)
@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "url")
@IsUri
private String url;

@ApiModelProperty(value = "Nombre corto de la línea. Ejemplo: 6")
@CsvBindByPosition(position=5)
@CsvBindByName(column="shortName", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMCOMMONS, propiedad = "shortName")
private String shortName;

@ApiModelProperty(value = "Distancia total de la línea. Ejemplo: 6")
@CsvBindByPosition(position=6)
@CsvBindByName(column="distancia")
@Rdf(contexto = Context.TMJOURNEY, propiedad = "distance", typeURI=Context.XSD_URI+"double")
private Double distance;

@ApiModelProperty(value = "Color de fondo de la línea. Ejemplo: gris")
@CsvBindByPosition(position=7)
@CsvBindByName(column="colour", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMCOMMONS, propiedad = "colour")
private String colour;

@ApiModelProperty(value = "Color del texto de la línea. Ejemplo: azul")
@CsvBindByPosition(position=8)
@CsvBindByName(column="textColour", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMCOMMONS, propiedad = "textColour")
private String textColour;


@ApiModelProperty(value = "El operador que opera la línea. Ejemplo: emt")
@CsvBindByPosition(position=9)
@CsvBindByName(column="operating", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMORG, propiedad = "operating")
@RdfExternalURI(inicioURI="/autobus/operator/",finURI="operating", urifyLevel = 1)
private String operating;


@ApiModelProperty(value = "Indica la parada de cabecera de la línea. Ejemplo: 1918")
@CsvBindByPosition(position=10)
@CsvBindByName(column="cabeceraLinea", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "cabeceraLinea")
@RdfExternalURI(inicioURI="/autobus/parada/",finURI="cabeceraLinea", urifyLevel = 1)
private String cabeceraLinea;

@ApiModelProperty(value = "Indica la parada de fin de la línea. Ejemplo: 418")
@CsvBindByPosition(position=11)
@CsvBindByName(column="finalLinea", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "finalLinea")
@RdfExternalURI(inicioURI="/autobus/parada/",finURI="finalLinea", urifyLevel = 1)
private String finalLinea;

public Linea() {
}

public Linea(Linea copia) {

  this.ikey = copia.ikey;
  this.id = copia.id;
  this.description = copia.description;
  this.title = copia.title;
  this.url = copia.url;
  this.shortName = copia.shortName;
  this.distance = copia.distance;
  this.colour = copia.colour;
  this.textColour = copia.textColour;
  this.operating = copia.operating;
  this.cabeceraLinea = copia.cabeceraLinea;
  this.finalLinea = copia.finalLinea;
}

public Linea(Linea copia, List<String> attributesToSet) {

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

  if (attributesToSet.contains("url")) {
	this.url = copia.url;
  }

  if (attributesToSet.contains("shortName")) {
	this.shortName = copia.shortName;
  }

  if (attributesToSet.contains("distance")) {
	this.distance = copia.distance;
  }

  if (attributesToSet.contains("colour")) {
	this.colour = copia.colour;
  }

  if (attributesToSet.contains("textColour")) {
	this.textColour = copia.textColour;
  }

  if (attributesToSet.contains("operating")) {
	this.operating = copia.operating;
  }

  if (attributesToSet.contains("cabeceraLinea")) {
	this.cabeceraLinea = copia.cabeceraLinea;
  }

  if (attributesToSet.contains("finalLinea")) {
	this.finalLinea = copia.finalLinea;
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

@Column(name = "url", length = 400)
public String getUrl() {
  return this.url;
}

public void setUrl(String url) {
  this.url = url;
}

@Column(name = "short_name", length = 200)
public String getShortName() {
  return this.shortName;
}

public void setShortName(String shortName) {
  this.shortName = shortName;
}

@Column(name = "distance", precision = 12)
public Double getDistance() {
  return this.distance;
}

public void setDistance(Double distance) {
  this.distance = distance;
}

@Column(name = "colour", length = 200)
public String getColour() {
  return this.colour;
}

public void setColour(String colour) {
  this.colour = colour;
}

@Column(name = "text_colour", length = 200)
public String getTextColour() {
  return this.textColour;
}

public void setTextColour(String textColour) {
  this.textColour = textColour;
}

@Column(name = "operating", length = 50)
public String getOperating() {
  return operating;
}

public void setOperating(String operating) {
  this.operating = operating;
}

@Column(name = "cabecera_linea", length = 50)
public String getCabeceraLinea() {
  return cabeceraLinea;
}

public void setCabeceraLinea(String cabeceraLinea) {
  this.cabeceraLinea = cabeceraLinea;
}

@Column(name = "final_linea", length = 50)
public String getFinalLinea() {
  return finalLinea;
}

public void setFinalLinea(String finalLinea) {
  this.finalLinea = finalLinea;
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
	prefixes.put(Context.ESCJR, Context.ESCJR_URI);
	prefixes.put(Context.TMORG, Context.TMORG_URI);
	prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
	prefixes.put(Context.TMCOMMONS, Context.TMCOMMONS_URI);
	prefixes.put(Context.ESAUTOB, Context.ESAUTOB_URI);
	
	return prefixes;
}

@Override
public String toString() {
  return "Linea [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description + ", url=" + url + ", shortName=" + shortName + ", distance=" + distance + ", colour=" + colour + ", textColour=" + textColour
	  + ", operating=" + operating + ", cabeceraLinea=" + cabeceraLinea + ", finalLinea=" + finalLinea + "]";
}



}
