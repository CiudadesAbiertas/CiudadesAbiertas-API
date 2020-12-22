package org.ciudadesabiertas.dataset.model;
// Generated 30 nov. 2020 11:06:05 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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




@Entity
@ApiModel
@Table(name = "empleo_plaza_turno", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "PlazaPorTurno")
@PathId(value = "/empleo/plaza-por-turno")
public class PlazaPorTurno implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = 111490696706499327L;
    
@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador de la Plaza por turno. Ejemplo: plazas01")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Número de plazas por turno. Ejemplo: 5")
@CsvBindByPosition(position=2)
@CsvBindByName(column="plazasPorTurno", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "plazasPorTurno")
private Integer plazasPorTurno;

@ApiModelProperty(value = "Turno de presentación para una plaza: libre, estabilización, promoción interna, reservado por algún motivo. Ejemplo: libre")
@CsvBindByPosition(position=3)
@CsvBindByName(column="turnoPlaza", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "turnoPlaza")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/empleo/grupo-profesional/", finURI="turnoPlaza", urifyLevel=2)
private String turnoPlaza;

@ApiModelProperty(value = "Identificador de la convocatoria a la que pertence la plaza. Ejemplo: convocatoria001")
@CsvBindByPosition(position=4)
@CsvBindByName(column="convocatoriaId", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "convocatoriaId")
@RdfExternalURI(inicioURI="/empleo/convocatoria-empleo-publico/", finURI="turnoPlaza")
private String convocatoriaId;

public PlazaPorTurno() {
}




public PlazaPorTurno(PlazaPorTurno copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.plazasPorTurno = copia.plazasPorTurno;
  this.turnoPlaza = copia.turnoPlaza;
  this.convocatoriaId = copia.convocatoriaId;
}

public PlazaPorTurno(PlazaPorTurno copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("plazasPorTurno")) {
	this.plazasPorTurno = copia.plazasPorTurno;
  }
  if (attributesToSet.contains("turnoPlaza")) {
	this.turnoPlaza = copia.turnoPlaza;
  }
  if (attributesToSet.contains("convocatoriaId")) {
	this.convocatoriaId = copia.convocatoriaId;
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

@Column(name = "plazas_por_turno")
public Integer getPlazasPorTurno() {
  return this.plazasPorTurno;
}

public void setPlazasPorTurno(Integer plazasPorTurno) {
  this.plazasPorTurno = plazasPorTurno;
}

@Column(name = "turno_plaza", length = 200)
public String getTurnoPlaza() {
  return this.turnoPlaza;
}

public void setTurnoPlaza(String turnoPlaza) {
  this.turnoPlaza = turnoPlaza;
}

@Column(name = "convocatoria_id", length = 50)
public String getConvocatoriaId() {
  return convocatoriaId;
}

public void setConvocatoriaId(String convocatoriaId) {
  this.convocatoriaId = convocatoriaId;
}




@Override
public String toString() {
  return "PlazaPorTurno [ikey=" + ikey + ", id=" + id + ", plazasPorTurno=" + plazasPorTurno + ", turnoPlaza=" + turnoPlaza + ", convocatoriaId=" + convocatoriaId + "]";
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

  if (!Util.validValue(this.getPlazasPorTurno())) {
	result.add("PlazasPorTurno is not valid [PlazasPorTurno:" + this.getPlazasPorTurno() + "]");
  }

  if (!Util.validValue(this.getConvocatoriaId())) {
	result.add("ConvocatoriaId is not valid [ConvocatoriaId:" + this.getConvocatoriaId() + "]");
  }
 
  return result;
}


}
