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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.CustomId;
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
@Table(name = "bus_rel_linea_incidencia")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAUTOB, propiedad = "Linea")
@PathId(value="/autobus/linea")
public class RelLineaIncidencia implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = 8689310550746401350L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador de la linea. Ejemplo: 138")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@CustomId(id = "linea")	
private String id;

@ApiModelProperty(value = "Identificador de la linea. Ejemplo: 138")
@CsvBindByPosition(position=2)
@CsvBindByName(column="afectadaPorIncidencia", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESAUTOB, propiedad = "afectadaPorIncidencia")
@RdfExternalURI(inicioURI="/autobus/incidencia/",finURI="afectadaPorIncidencia", urifyLevel = 1)
private String afectadaPorIncidencia;



@ApiModelProperty(value = "Identificador de la linea. Ejemplo: 138")
@CsvBindByPosition(position=3)
@CsvBindByName(column="linea", format=Constants.STRING_FORMAT)
private String linea;



public RelLineaIncidencia() {
}



public RelLineaIncidencia(RelLineaIncidencia copia) {
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.afectadaPorIncidencia = copia.afectadaPorIncidencia;
  this.linea = copia.linea;
}

public RelLineaIncidencia(RelLineaIncidencia copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  
  if (attributesToSet.contains("afectadaPorIncidencia")) {
	this.afectadaPorIncidencia = copia.afectadaPorIncidencia;
  }

  if (attributesToSet.contains("linea")) {
	this.linea = copia.linea;
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

@Column(name = "afectada_incidencia", length = 50)
public String getAfectadaPorIncidencia() {
  return afectadaPorIncidencia;
}



public void setAfectadaPorIncidencia(String afectadaPorIncidencia) {
  this.afectadaPorIncidencia = afectadaPorIncidencia;
}



@Column(name = "linea", length = 50)
public String getLinea() {
  return linea;
}

public void setLinea(String linea) {
  this.linea = linea;
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

  if (!Util.validValue(this.getId())) {
	result.add("Id is not valid [Id:" + this.getId() + "]");
  }
  
  if (!Util.validValue(this.getLinea())) {
	result.add("Linea is not valid [Linea:" + this.getLinea() + "]");
  }
  
  if (!Util.validValue(this.getAfectadaPorIncidencia())) {
	result.add("AfectadaIncidencia is not valid [AfectadaIncidencia:" + this.getAfectadaPorIncidencia() + "]");
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
  return "RelLineaIncidencia [ikey=" + ikey + ", id=" + id + ", afectadaIncidencia=" + afectadaPorIncidencia + ", linea=" + linea + "]";
}








}
