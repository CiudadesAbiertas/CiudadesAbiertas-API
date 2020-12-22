package org.ciudadesabiertas.dataset.model;


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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfTripleExtenal;
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
@Table(name = "empleo_rel_oferta_convoca", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value = "/empleo/relacion-oferta-convocatoria")
public class RelOfertaConvoca implements java.io.Serializable, RDFModel {

@ApiModelProperty(hidden = true)
private static final long serialVersionUID = -7959855010569361125L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador de la relación entre oferta y convocatoria. Ejemplo: oferta001")
@CsvBindByPosition(position = 1)
@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
@RdfTripleExtenal(sujetoInicioURI = "/empleo/boletin-convocatoria/", sujetoFinURI = "convocatoriaId", propiedadURI = Context.ESEMPLEO_URI
+ "plazasOEP", objetoInicioURI = "/empleo/oferta-empleo-publica/", objetoFinURI = "ofertaId")
private String id;

@ApiModelProperty(value = "Identificador de la convocatoria. Ejemplo: convocatoria001")
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "convocatoriaId", format = Constants.STRING_FORMAT)
private String convocatoriaId;

@ApiModelProperty(value = "Identificador de la oferta. Ejemplo: oferta001")
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "ofertaId", format = Constants.STRING_FORMAT)
private String ofertaId;




public RelOfertaConvoca() {
}



public RelOfertaConvoca(RelOfertaConvoca copia) {
  super();
  this.ikey = copia.ikey;
  this.convocatoriaId =  copia.convocatoriaId;
  this.ofertaId =  copia.ofertaId;
  this.id =  copia.id;
}

public RelOfertaConvoca(RelOfertaConvoca copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("convocatoriaId")) {
	this.convocatoriaId = copia.convocatoriaId;
  }
  if (attributesToSet.contains("ofertaId")) {
	this.ofertaId = copia.ofertaId;
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

@Column(name = "convocatoria_id", unique = true, length = 50)
public String getConvocatoriaId() {
  return this.convocatoriaId;
}

public void setConvocatoriaId(String convocatoriaId) {
  this.convocatoriaId = convocatoriaId;
}

@Column(name = "oferta_id", unique = true, length = 50)
public String getOfertaId() {
  return this.ofertaId;
}

public void setOfertaId(String ofertaId) {
  this.ofertaId = ofertaId;
}

@Column(name = "id", unique = true, length = 50)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}




@Override
public String toString() {
  return "RelOfertaConvoca [ikey=" + ikey + ", convocatoriaId=" + convocatoriaId + ", ofertaId=" + ofertaId + ", id=" + id + "]";
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

  if (!Util.validValue(this.getConvocatoriaId())) {
	result.add("Convocatoria is not valid [Convocatoria:" + this.getConvocatoriaId() + "]");
  }

  if (!Util.validValue(this.getOfertaId())) {
	result.add("Oferta is not valid [Oferta:" + this.getOfertaId() + "]");
  }

  return result;
}



}
