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
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.IConvenio;
import org.ciudadesabiertas.model.IGestionadoPor;
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
@Table(name = "subvencion_concesion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSUBV, propiedad = "Concesion")
@PathId(value="/subvencion/concesion")
public class SubvencionConcesion implements java.io.Serializable, RDFModel {

	
	
@ApiModelProperty(hidden = true)
private static final long serialVersionUID = -2524637648736500453L;
  
@ApiModelProperty(hidden = true)
@JsonIgnore	
private String ikey;

@ApiModelProperty(value = "Identificador de la concesión. Ejemplo: SUB1")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre de la concesión. Ejemplo: SUBVENCIÓN NOMINATIVA UCCI ACTIVIDADES GENERALES")
@CsvBindByPosition(position=2)
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;	

@ApiModelProperty(value = "Importe que se requiere al solicitar la subvención. Ejemplo: 2000")
@CsvBindByPosition(position=3)
@CsvBindByName(column="importeSolicitado")
@Rdf(contexto = Context.ESSUBV, propiedad = "importeSolicitado", typeURI=Context.XSD_URI+"double")
private BigDecimal importeSolicitado;

@ApiModelProperty(value = "Importe de subvención concedido a una organización. Ejemplo: 2000")
@CsvBindByPosition(position=4)
@CsvBindByName(column="importeConcedido")
@Rdf(contexto = Context.ESSUBV, propiedad = "importeConcedido", typeURI=Context.XSD_URI+"double")
private BigDecimal importeConcedido;

@ApiModelProperty(value = "Fecha en la que se solicita la subvención. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=5)
@CsvBindByName (column = "fechaSolicitud")	
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "fechaSolicitud", typeURI=Context.XSD_URI+"datetime")
private Date fechaSolicitud;

@ApiModelProperty(value = "Fecha en que se concede la subvención. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=6)
@CsvBindByName (column = "fechaConcesion")	
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "fechaConcesion", typeURI=Context.XSD_URI+"datetime")
private Date fechaConcesion;

@ApiModelProperty(value = "Propiedad que identifica la subvención de una asignación. Ejemplo: SUB1")
@CsvBindByPosition(position=7)
@CsvBindByName (column = "convocatoria",format=Constants.STRING_FORMAT)	
@Rdf(contexto = Context.ESSUBV, propiedad = "convocatoria")
@RdfExternalURI(inicioURI= "/subvencion/convocatoria/", finURI="convocatoria", urifyLevel=2)
private String convocatoria;

@ApiModelProperty(value = "Propiedad que identifica al adjudicatario de una subvención. Ejemplo: 03401397L")
@CsvBindByPosition(position=8)
@CsvBindByName (column = "beneficiario",format=Constants.STRING_FORMAT)	
@Rdf(contexto = Context.ESSUBV, propiedad = "beneficiario")
@RdfExternalURI(inicioURI= "/subvencion/beneficiario/", finURI="beneficiario", urifyLevel=2)
private String beneficiario;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String clasificacionProgramaSimple;


@ApiModelProperty(value = "La clasificación por programa de gasto. Ejemplo: 23270")
@CsvBindByPosition(position=9)
@CsvBindByName (column = "clasificacionPrograma", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionPrograma")
@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/programas-gasto/madrid/", finURI="clasificacionPrograma", urifyLevel=2)
private List<String> clasificacionPrograma;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String clasificacionEconomicaGastoSimple;


@ApiModelProperty(value = "La clasificación económica de la subvención. Ejemplo: 60900")
@CsvBindByPosition(position=10)
@CsvBindByName (column = "clasificacionEconomicaGasto", format=Constants.STRING_FORMAT)	
@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionEconomicaGasto")
@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-gasto/madrid/", finURI="clasificacionEconomicaGasto", urifyLevel=2)
private List<String> clasificacionEconomicaGasto;


@ApiModelProperty(value = "La clasificación por Programa como texto. Ejemplo: 23270")
@CsvBindByPosition(position=11)
@CsvBindByName (column = "programaAsText", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESPRESUP, propiedad = "programaAsText")
private String programaAsText;

@ApiModelProperty(value = "La clasificación económica como texto. Ejemplo: 60900")
@CsvBindByPosition(position=12)
@CsvBindByName (column = "gastoAsText")	
@Rdf(contexto = Context.ESPRESUP, propiedad = "gastoAsText")
private String gastoAsText;


public SubvencionConcesion() {
}

public SubvencionConcesion(SubvencionConcesion copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.convocatoria = copia.convocatoria;
  this.beneficiario = copia.beneficiario;
  this.importeSolicitado = copia.importeSolicitado;
  this.importeConcedido = copia.importeConcedido;
  this.fechaSolicitud = copia.fechaSolicitud;
  this.fechaConcesion = copia.fechaConcesion;
  this.clasificacionPrograma= copia.clasificacionPrograma;
  this.clasificacionEconomicaGasto= copia.clasificacionEconomicaGasto;
  this.programaAsText= copia.programaAsText;
  this.gastoAsText= copia.gastoAsText;
  this.title=copia.title;
}

public SubvencionConcesion(SubvencionConcesion copia, List<String> attributesToSet) {
  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }

  if (attributesToSet.contains("convocatoria")) {
	this.convocatoria = copia.convocatoria;
  }

  if (attributesToSet.contains("beneficiario")) {
	this.beneficiario = copia.beneficiario;
  }

  
  if (attributesToSet.contains("importeSolicitado")) {
	this.importeSolicitado = copia.importeSolicitado;
  }

  if (attributesToSet.contains("importeConcedido")) {
	this.importeConcedido = copia.importeConcedido;
  }

  if (attributesToSet.contains("fechaSolicitud")) {
	this.fechaSolicitud = copia.fechaSolicitud;
  }

  if (attributesToSet.contains("fechaConcesion")) {
	this.fechaConcesion = copia.fechaConcesion;
  }

  if (attributesToSet.contains("clasificacionPrograma")) {
	  this.clasificacionPrograma= copia.clasificacionPrograma;
  }
  
  if (attributesToSet.contains("clasificacionEconomicaGasto")) {
	  this.clasificacionEconomicaGasto= copia.clasificacionEconomicaGasto;
  }

  if (attributesToSet.contains("programaAsText")) {
	  this.programaAsText= copia.programaAsText;
  }
  if (attributesToSet.contains("gastoAsText")) {
	  this.gastoAsText= copia.gastoAsText;
  }
  if (attributesToSet.contains("title")) {
	  this.title= copia.title;
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

@Column(name = "convocatoria", length = 50)
public String getConvocatoria() {
  return this.convocatoria;
}

public void setConvocatoria(String convocatoria) {
  this.convocatoria = convocatoria;
}

@Column(name = "beneficiario", length = 50)
public String getBeneficiario() {
  return this.beneficiario;
}

public void setBeneficiario(String beneficiario) {
  this.beneficiario = beneficiario;
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

@Column(name = "importe_concedido", precision = 12)
public BigDecimal getImporteConcedido() {
  return this.importeConcedido;
}

public void setImporteConcedido(BigDecimal importeConcedido) {
  this.importeConcedido = importeConcedido;
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
@Column(name = "fecha_concesion", length = 19)
public Date getFechaConcesion() {
  return this.fechaConcesion;
}

public void setFechaConcesion(Date fechaConcesion) {
  this.fechaConcesion = fechaConcesion;
}



@Column(name = "clasificacion_programa", length = 400)
public String getClasificacionProgramaSimple() {
	return clasificacionProgramaSimple;
}


public void setClasificacionProgramaSimple(String clasificacionProgramaSimple) {
	this.clasificacionProgramaSimple = clasificacionProgramaSimple;
}

@Transient
public List<String> getClasificacionPrograma() {
	return clasificacionPrograma;
}

@Transient
public void setClasificacionPrograma(List<String> clasificacionPrograma) {
	this.clasificacionPrograma = clasificacionPrograma;
}


@Column(name = "clasificacion_eco_gasto", length = 400)
public String getClasificacionEconomicaGastoSimple() {
	return clasificacionEconomicaGastoSimple;
}

public void setClasificacionEconomicaGastoSimple(String clasificacionEconomicaGastoSimple) {
	this.clasificacionEconomicaGastoSimple = clasificacionEconomicaGastoSimple;
}

@Transient
public List<String> getClasificacionEconomicaGasto() {
	return clasificacionEconomicaGasto;
}

@Transient
public void setClasificacionEconomicaGasto(List<String> clasificacionEconomicaGasto) {
	this.clasificacionEconomicaGasto = clasificacionEconomicaGasto;
}

@Column(name = "programa_as_text", length = 400)
public String getProgramaAsText() {
	return programaAsText;
}

public void setProgramaAsText(String programaAsText) {
	this.programaAsText = programaAsText;
}

@Column(name = "gasto_as_text", length = 400)
public String getGastoAsText() {
	return gastoAsText;
}


public void setGastoAsText(String gastoAsText) {
	this.gastoAsText = gastoAsText;
}



@Column(name = "title", length = 400)
public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
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
  return "SubvencionBeneficiario [ikey=" + ikey + ", id=" + id + ", importeSolicitado=" + importeSolicitado + ", importeConcedido=" + importeConcedido + ", fechaSolicitud=" + fechaSolicitud + ", fechaConcesion=" + fechaConcesion
	  + ", convocatoria=" + convocatoria + ", beneficiario=" + beneficiario + "]";
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {
	
	return  (T) cloneClass( (SubvencionConcesion) copia,listado) ;
}


// Constructor copia con lista de attributos a settear
public SubvencionConcesion cloneClass(SubvencionConcesion copia, List<String> attributesToSet) {
	
  	SubvencionConcesion obj = new SubvencionConcesion(copia,attributesToSet);		
	
	return obj;
	
}


public  List<String> validate() {
	List<String> result = new ArrayList<String>();

	// Validamos campos Obligatorios ver si es posible obtener esta información
	// mediante anotaciones del modelo
	if (!Util.validValue(this.getId())) {
		result.add("Id is not valid [Id:" + this.getId() + "]");
	}

	if (!Util.validValue(this.getConvocatoria())) {
		result.add("tieneSubvencion is not valid [tieneSubvencion:" + this.getBeneficiario() + "]");
	}
	
	if (!Util.validValue(this.getBeneficiario())) {
		result.add("tieneBeneficiario is not valid [tieneBeneficiario:" + this.getBeneficiario() + "]");
	}

	if (!Util.validValue(this.getImporteSolicitado())) {
		result.add("ImporteSolicitado is not valid [ImporteSolicitado:" + this.getImporteSolicitado() + "]");
	}
	
	if (!Util.validValue(this.getImporteConcedido())) {
		result.add("ImporteAdjudicado is not valid [ImporteAdjudicado:" + this.getImporteConcedido() + "]");
	}

	

	return result;
}


}
