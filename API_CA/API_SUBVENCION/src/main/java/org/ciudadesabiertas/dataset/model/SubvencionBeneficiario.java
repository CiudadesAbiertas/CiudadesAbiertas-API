package org.ciudadesabiertas.dataset.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "subvencion_beneficiario")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSUBV, propiedad = "Subvencion")
@PathId(value="/subvencion/beneficiario")
public class SubvencionBeneficiario implements java.io.Serializable, RDFModel {

@ApiModelProperty(hidden = true)
private static final long serialVersionUID = -2514637648736500453L;
  
@ApiModelProperty(hidden = true)
@JsonIgnore	
private String ikey;

@ApiModelProperty(value = "Identificador de la subvención. Ejemplo: SUB1")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Importe que se requiere al solicitar la subvención. Ejemplo: 2000")
@CsvBindByPosition(position=2)
@CsvBindByName(column="importeSolicitado")
@Rdf(contexto = Context.ESSUBV, propiedad = "importeSolicitado", typeURI=Context.XSD_URI+"double")
private BigDecimal importeSolicitado;

@ApiModelProperty(value = "Importe de subvención concedido a una organización. Ejemplo: 2000")
@CsvBindByPosition(position=3)
@CsvBindByName(column="importeAdjudicado")
@Rdf(contexto = Context.ESSUBV, propiedad = "importeAdjudicado", typeURI=Context.XSD_URI+"double")
private BigDecimal importeAdjudicado;

@ApiModelProperty(value = "Fecha en la que se solicita la subvención. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=4)
@CsvBindByName (column = "fechaSolicitud")	
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "fechaSolicitud", typeURI=Context.XSD_URI+"date")
private Date fechaSolicitud;

@ApiModelProperty(value = "Fecha en que se concede la subvención. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=5)
@CsvBindByName (column = "fechaAdjudicacion")	
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "fechaAdjudicacion", typeURI=Context.XSD_URI+"date")
private Date fechaAdjudicacion;

@ApiModelProperty(value = "Identificador de la subvención. Ejemplo: SUB1")
@CsvBindByPosition(position=6)
@CsvBindByName (column = "tieneSubvencion",format=Constants.STRING_FORMAT)	
@Rdf(contexto = Context.ESSUBV, propiedad = "tieneSubvencion")
@RdfExternalURI(inicioURI= "/subvencion/subvencion/", finURI="tieneSubvencion", urifyLevel=2)
private String tieneSubvencion;

@ApiModelProperty(value = "Identificador de la organización. Ejemplo: 03401397L")
@CsvBindByPosition(position=7)
@CsvBindByName (column = "tieneBeneficiario",format=Constants.STRING_FORMAT)	
@Rdf(contexto = Context.ESSUBV, propiedad = "tieneBeneficiario")
@RdfExternalURI(inicioURI= "/subvencion/organization/", finURI="tieneBeneficiario", urifyLevel=2)
private String tieneBeneficiario;

public SubvencionBeneficiario() {
}

public SubvencionBeneficiario(SubvencionBeneficiario copia) {
  super();
  this.ikey = copia.ikey;
  this.tieneSubvencion = copia.tieneSubvencion;
  this.tieneBeneficiario= copia.tieneBeneficiario;
  this.id = copia.id;
  this.importeSolicitado = copia.importeSolicitado;
  this.importeAdjudicado = copia.importeAdjudicado;
  this.fechaSolicitud = copia.fechaSolicitud;
  this.fechaAdjudicacion = copia.fechaAdjudicacion;
}

public SubvencionBeneficiario(SubvencionBeneficiario copia, List<String> attributesToSet) {
  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }

  if (attributesToSet.contains("tieneSubvencion")) {
	this.tieneSubvencion = copia.tieneSubvencion;
  }

  if (attributesToSet.contains("tieneBeneficiario")) {
	this.tieneBeneficiario = copia.tieneBeneficiario;
  }

  
  if (attributesToSet.contains("importeSolicitado")) {
	this.importeSolicitado = copia.importeSolicitado;
  }

  if (attributesToSet.contains("importeAdjudicado")) {
	this.importeAdjudicado = copia.importeAdjudicado;
  }

  if (attributesToSet.contains("fechaSolicitud")) {
	this.fechaSolicitud = copia.fechaSolicitud;
  }

  if (attributesToSet.contains("fechaAdjudicacion")) {
	this.fechaAdjudicacion = copia.fechaAdjudicacion;
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

@Column(name = "tiene_subvencion")
public String getTieneSubvencion() {
  return this.tieneSubvencion;
}

public void setTieneSubvencion(String tieneSubvencion) {
  this.tieneSubvencion = tieneSubvencion;
}

@Column(name = "tiene_beneficiario")
public String getTieneBeneficiario() {
  return this.tieneBeneficiario;
}

public void setTieneBeneficiario(String tieneBeneficiario) {
  this.tieneBeneficiario = tieneBeneficiario;
}

@Column(name = "id", length = 50)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}

@Column(name = "importe_solicitado", precision = 12)
public BigDecimal getImporteSolicitado() {
  return this.importeSolicitado;
}

public void setImporteSolicitado(BigDecimal importeSolicitado) {
  this.importeSolicitado = importeSolicitado;
}

@Column(name = "importe_adjudicado", precision = 12)
public BigDecimal getImporteAdjudicado() {
  return this.importeAdjudicado;
}

public void setImporteAdjudicado(BigDecimal importeAdjudicado) {
  this.importeAdjudicado = importeAdjudicado;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "fecha_solicitud", length = 19)
public Date getFechaSolicitud() {
  return this.fechaSolicitud;
}

public void setFechaSolicitud(Date fechaSolicitud) {
  this.fechaSolicitud = fechaSolicitud;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "fecha_adjudicacion", length = 19)
public Date getFechaAdjudicacion() {
  return this.fechaAdjudicacion;
}

public void setFechaAdjudicacion(Date fechaAdjudicacion) {
  this.fechaAdjudicacion = fechaAdjudicacion;
}


public Map<String,String> prefixes()
{
	Map<String,String> prefixes=new HashMap<String,String>();
	prefixes.put(Context.RDF, Context.RDF_URI);		
	prefixes.put(Context.XSD, Context.XSD_URI);
	prefixes.put(Context.OWL, Context.OWL_URI);
	prefixes.put(Context.DCT, Context.DCT_URI);
	prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
	prefixes.put(Context.ESADM, Context.ESADM_URI);
	prefixes.put(Context.ESSUBV, Context.ESSUBV_URI);
	prefixes.put(Context.ESPRESUP, Context.ESPRESUP_URI);		
	prefixes.put(Context.ORG, Context.ORG_URI);
	prefixes.put(Context.ESCONV, Context.ESCONV_URI);
	
		
	return prefixes;
}


@Override
public String toString() {
  return "SubvencionBeneficiario [ikey=" + ikey + ", id=" + id + ", importeSolicitado=" + importeSolicitado + ", importeAdjudicado=" + importeAdjudicado + ", fechaSolicitud=" + fechaSolicitud + ", fechaAdjudicacion=" + fechaAdjudicacion
	  + ", tieneSubvencion=" + tieneSubvencion + ", tieneBeneficiario=" + tieneBeneficiario + "]";
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {
	
	return  (T) cloneClass( (SubvencionBeneficiario) copia,listado) ;
}


// Constructor copia con lista de attributos a settear
public SubvencionBeneficiario cloneClass(SubvencionBeneficiario copia, List<String> attributesToSet) {
	
  	SubvencionBeneficiario obj = new SubvencionBeneficiario(copia,attributesToSet);		
	
	return obj;
	
}


public  List<String> validate() {
	List<String> result = new ArrayList<String>();

	// Validamos campos Obligatorios ver si es posible obtener esta información
	// mediante anotaciones del modelo
	if (!Util.validValue(this.getId())) {
		result.add("Id is not valid [Id:" + this.getId() + "]");
	}

	if (!Util.validValue(this.getTieneSubvencion())) {
		result.add("tieneSubvencion is not valid [tieneSubvencion:" + this.getTieneSubvencion() + "]");
	}
	
	if (!Util.validValue(this.getTieneBeneficiario())) {
		result.add("tieneBeneficiario is not valid [tieneBeneficiario:" + this.getTieneBeneficiario() + "]");
	}

	if (!Util.validValue(this.getImporteSolicitado())) {
		result.add("ImporteSolicitado is not valid [ImporteSolicitado:" + this.getImporteSolicitado() + "]");
	}
	
	if (!Util.validValue(this.getImporteAdjudicado())) {
		result.add("ImporteAdjudicado is not valid [ImporteAdjudicado:" + this.getImporteAdjudicado() + "]");
	}

	

	return result;
}


}
