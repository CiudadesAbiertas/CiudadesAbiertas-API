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
import javax.persistence.UniqueConstraint;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "empleo_boletin_oficial", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "BoletinOficial")
@PathId(value = "/empleo/boletin-oficial")
public class BoletinOficial implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = 2159594434131469119L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador del Boletín. Ejemplo: boletin001")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre del Boletín. Ejemplo: Boletín Oficial de la Comunidad de Madrid: jueves 12 noviembre 2020")
@CsvBindByPosition(position=2)	
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@RdfMultiple({@Rdf(contexto = Context.SCHEMA, propiedad = "name"), @Rdf(contexto = Context.DCT, propiedad = "title")})
private String title;

@ApiModelProperty(value = "Descripción del Boletín. Ejemplo: Descripcion OGAME")
@CsvBindByPosition(position=3)	
@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ELI, propiedad = "id_local")
private String id_local;

@ApiModelProperty(value = "Descripción del Boletín. Ejemplo: Descripcion OGAME")
@CsvBindByPosition(position=5)	
@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "description")
private String description;

@ApiModelProperty(value = "Fecha de la publicación. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=4)	
@CsvBindByName(column="datePublished")
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "datePublished", typeURI=Context.XSD_URI+"date")
private Date datePublished;


public BoletinOficial() {
}

public BoletinOficial(BoletinOficial copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.description = copia.description;
  this.id_local = copia.id_local;
  this.title = copia.title;
  this.datePublished = copia.datePublished;
}

public BoletinOficial(BoletinOficial copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("description")) {
	this.description = copia.description;
  }
  if (attributesToSet.contains("id_local")) {
	this.id_local = copia.id_local;
  }
  if (attributesToSet.contains("title")) {
	this.title = copia.title;
  }
  if (attributesToSet.contains("datePublished")) {
	this.datePublished = copia.datePublished;
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

@Column(name = "id", unique = true, length = 50)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}

@Column(name = "description", length = 400)
public String getDescription() {
  return this.description;
}

public void setDescription(String description) {
  this.description = description;
}


@Column(name = "id_local", length = 200)
public String getId_local() {
  return id_local;
}

public void setId_local(String id_local) {
  this.id_local = id_local;
}

@Column(name = "title", length = 200)
public String getTitle() {
  return this.title;
}

public void setTitle(String title) {
  this.title = title;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "date_published", length = 26)
public Date getDatePublished() {
  return this.datePublished;
}

public void setDatePublished(Date datePublished) {
  this.datePublished = datePublished;
}

@Override
public String toString() {
  return "BoletinOficial [ikey=" + ikey + ", id=" + id + ", description=" + description + ", id_local=" + id_local + ", title=" + title + ", datePublished=" + datePublished + "]";
}

@Override
public Map<String, String> prefixes() {
  Map<String, String> prefixes = new HashMap<String, String>();
  prefixes.put(Context.XSD, Context.XSD_URI);
  prefixes.put(Context.DCT, Context.DCT_URI);
  prefixes.put(Context.ESADM, Context.ESADM_URI);
  prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);  
  prefixes.put(Context.ELI, Context.ELI_URI);
  prefixes.put(Context.ESEMPLEO, Context.ESEMPLEO_URI);
  return prefixes;
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((BoletinOficial) copia, listado);
}

// Constructor copia con lista de attributos a settear
public BoletinOficial cloneClass(BoletinOficial lc, List<String> attributesToSet) {

  BoletinOficial obj = new BoletinOficial(lc, attributesToSet);

  return obj;

}

// TODO valiaciones de equipamientos ver si es posible realizar mediante
// anotaciones en model el saber los campos obligatorios
public List<String> validate() {
  List<String> result = new ArrayList<String>();

  // Validamos campos Obligatorios ver si es posible obtener esta información
  // mediante anotaciones del modelo
  if (!Util.validValue(this.getId())) {
	result.add("id is not valid [Id:" + this.getId() + "]");
  }

  if (!Util.validValue(this.getTitle())) {
	result.add("title is not valid [Title:" + this.getTitle() + "]");
  }

  if (!Util.validValue(this.getDatePublished())) {
	result.add("datePublished is not valid [datePublished:" + this.getDatePublished() + "]");
  }

  return result;
}

}
