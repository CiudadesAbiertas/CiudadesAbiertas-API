package org.ciudadesabiertas.dataset.model;


import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.dataset.model.Subvencion;
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
@Table(name = "subvencion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSUBV, propiedad = "Subvencion")
@PathId(value="/subvencion/subvencion")
public class Subvencion implements java.io.Serializable, RDFModel, IGestionadoPor, IConvenio {

@ApiModelProperty(hidden = true)
private static final long serialVersionUID = 2683751453835610340L;
  
@ApiModelProperty(hidden = true)
@JsonIgnore	
private String ikey;


@ApiModelProperty(value = "Identificador de la subvención. Ejemplo: SUB1")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre de la subvención. Ejemplo: SUBVENCIÓN NOMINATIVA UCCI ACTIVIDADES GENERALES")
@CsvBindByPosition(position=2)
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;	 

@ApiModelProperty(value = "Bases reguladoras de la subvención. Ejemplo: https://www.bocm.es/boletin/CM_Orden_BOCM/2015/12/30/BOCM-20151230-29.PDF")
@CsvBindByPosition(position=3)
@Rdf(contexto = Context.ESSUBV, propiedad = "basesReguladoras")
@IsUri()
@CsvBindByName (column = "basesReguladoras", format=Constants.STRING_FORMAT)
private String basesReguladoras;


@ApiModelProperty(value = "Tipo de instrumento utilizado por la subvención. Ejemplo: GARANTÍA")
@CsvBindByPosition(position=4)
@CsvBindByName (column = "tipoInstrumento", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "tipoInstrumento")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-instrumento/", finURI="tipoInstrumento", urifyLevel=2)
private String tipoInstrumento;


@ApiModelProperty(value = "Identifica si una subvención es nominativa o no. Ejemplo: true")
@CsvBindByPosition(position=5)
@CsvBindByName(column="nominativa", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "nominativa", typeURI=Context.XSD_URI+"boolean")
private Boolean nominativa;

@ApiModelProperty(value = "Tipo de procedimiento utilizado por la subvención. Ejemplo: subvencion-nominativa")
@CsvBindByPosition(position=6)
@CsvBindByName (column = "tipoProcedimiento", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "tipoProcedimiento")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-procedimiento/", finURI="tipoProcedimiento", urifyLevel=2)
private String tipoProcedimiento;

@ApiModelProperty(value = "Nombre del proyecto al que está asociada la subvención. Ejemplo: Proyecto rehabilitación Plaza Mayor")
@CsvBindByPosition(position=7)
@CsvBindByName (column = "name", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "name")
private String name;

@ApiModelProperty(value = "Finalidad de la subvención. Ejemplo: Rehabilitación Plaza Mayor")
@CsvBindByPosition(position=8)
@CsvBindByName (column = "objeto", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "objeto")
private String objeto;


@ApiModelProperty(value = "Importe total de la subvención. Ejemplo: 150000.00")
@CsvBindByPosition(position=9)
@CsvBindByName (column = "importeTotalConcedido")
@Rdf(contexto = Context.ESSUBV, propiedad = "importeTotalConcedido", typeURI=Context.XSD_URI+"double")
private BigDecimal importeTotalConcedido;

@ApiModelProperty(value = "Fecha de acuerdo de iniciación de la subvención. Ejemplo: 2016-04-25T00:00:00")
@CsvBindByPosition(position=10)
@CsvBindByName (column = "fechaAcuerdo")	
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESSUBV, propiedad = "fechaAcuerdo", typeURI=Context.XSD_URI+"date")
private Date fechaAcuerdo;

@ApiModelProperty(value = "La clasificación por programa de gasto. Ejemplo: 23270")
@CsvBindByPosition(position=11)
@CsvBindByName (column = "clasificacionPrograma", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionPrograma")
@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/programas-gasto/madrid/", finURI="clasificacionPrograma", urifyLevel=2)
private String clasificacionPrograma;

@ApiModelProperty(value = "La clasificación económica de lasubvención. Ejemplo: 60900")
@CsvBindByPosition(position=12)
@CsvBindByName (column = "clasificacionEconomicaGasto")	
@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionEconomicaGasto")
@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-gasto/madrid/", finURI="clasificacionEconomicaGasto", urifyLevel=2)
private String clasificacionEconomicaGasto;


@ApiModelProperty(value = "Identificador del convenio que es instrumentado por una subvención. Ejemplo: CONV001")
@CsvBindByPosition(position=13)
@CsvBindByName (column = "instrumentaId")	
@Rdf(contexto = Context.ESSUBV, propiedad = "instrumenta")
@RdfExternalURI(inicioURI="/convenio/convenio/", finURI="instrumentaId", propiedad=Context.ESSUBV_URI+"instrumenta")
private String instrumentaId;


@ApiModelProperty(value = "Título del convenio que es instrumentado por una subvención. Ejemplo: CONVENIO PRUEBAS 1")
@CsvBindByPosition(position=14)
@CsvBindByName (column = "instrumentaTitle")	
@Rdf(contexto = Context.DCT, propiedad = "title")
@RdfBlankNode(tipo=Context.ESCONV_URI+"Convenio", propiedad=Context.ESSUBV_URI+"instrumenta", nodoId="convenio")
private String instrumentaTitle;


@Transient
@ApiModelProperty(hidden = true)
@Rdf(contexto = Context.DCT, propiedad = "identifier")
@RdfBlankNode(tipo=Context.ESCONV_URI+"Convenio", propiedad=Context.ESSUBV_URI+"instrumenta", nodoId="convenio")
private String instrumentaIdIsolated;


@ApiModelProperty(value = "Temática asociada a una subvención. Ejemplo: deporte")
@CsvBindByPosition(position=15)
@CsvBindByName (column = "tieneTematica")	
@Rdf(contexto = Context.ESSUBV, propiedad = "tieneTematica")
@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/page/datosabiertos/kos/sector-publico/subvencion/tematica-ayuntamiento/", finURI="tieneTematica", urifyLevel=2)
private String tieneTematica;

@ApiModelProperty(value = "Indica si la subvención esta gestionada por una organización. Ejemplo: true")
@CsvBindByPosition(position=16)
@CsvBindByName(column="gestionadoPorOrganization", format=Constants.STRING_FORMAT)
private Boolean gestionadoPorOrganization;

@ApiModelProperty(value = "Organización que gestiona la subvención. Ejemplo: deporte")
@CsvBindByPosition(position=17)
@CsvBindByName (column = "organizationId")	
@Rdf(contexto = Context.ESCONV, propiedad = "gestionadoPor")
@RdfExternalURI(inicioURI= "/subvencion/organization/", finURI="organizationId", urifyLevel=2)
private String organizationId;

@ApiModelProperty(value = "Indica si la subvención esta gestionada por un distrito. Ejemplo: true")
@CsvBindByPosition(position=18)
@CsvBindByName(column="gestionadoPorDistrito", format=Constants.STRING_FORMAT)
private Boolean gestionadoPorDistrito;

@ApiModelProperty(value = "Identificador del distrito de la subvencion. Ejemplo: 2800307")
@CsvBindByPosition(position=19)
@CsvBindByName (column = "distritoId", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESCONV, propiedad = "gestionadoPor")
@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
private String distritoId;

@ApiModelProperty(value = "Nombre del distrito de la subvencion. Ejemplo: Distrito 7")
@CsvBindByPosition(position=20)
@CsvBindByName (column = "distritoTitle", format=Constants.STRING_FORMAT)
private String distritoTitle;

@ApiModelProperty(value = "Area que concede subvención. Ejemplo: A05003355")
@CsvBindByPosition(position=21)
@CsvBindByName (column = "areaId", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESADM, propiedad = "area")
@RdfExternalURI(inicioURI="/subvencion/organization/",finURI="areaId", urifyLevel = 1)
private String areaId;

@ApiModelProperty(value = "Servicio del ayuntamiento que ofrece la subvención. Ejemplo: A05003355")
@CsvBindByPosition(position=22)
@CsvBindByName (column = "servicioId", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESADM, propiedad = "servicio")
@RdfExternalURI(inicioURI="/subvencion/organization/",finURI="servicioId", urifyLevel = 1)
private String servicioId;

@ApiModelProperty(value = "Entidad que financia subvención. Ejemplo: A05003355")
@CsvBindByPosition(position=23)
@CsvBindByName (column = "entidadFinanciadoraId", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESADM, propiedad = "distrito")
@RdfExternalURI(inicioURI="/subvencion/organization/",finURI="entidadFinanciadoraId", urifyLevel = 1)
private String entidadFinanciadoraId;

public Subvencion() {
}

public Subvencion(Subvencion copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.title = copia.title;
  this.basesReguladoras = copia.basesReguladoras;
  this.tipoInstrumento = copia.tipoInstrumento;
  this.nominativa = copia.nominativa;
  this.tipoProcedimiento = copia.tipoProcedimiento;
  this.name = copia.name;
  this.objeto = copia.objeto;
  this.importeTotalConcedido = copia.importeTotalConcedido;
  this.fechaAcuerdo = copia.fechaAcuerdo;
  this.clasificacionPrograma = copia.clasificacionPrograma;
  this.clasificacionEconomicaGasto = copia.clasificacionEconomicaGasto;
  this.instrumentaId = copia.instrumentaId;
  this.instrumentaTitle = copia.instrumentaTitle;
  this.tieneTematica = copia.tieneTematica;
  this.gestionadoPorOrganization = copia.gestionadoPorOrganization;
  this.organizationId = copia.organizationId;
  this.gestionadoPorDistrito = copia.gestionadoPorDistrito;
  this.distritoId = copia.distritoId;
  this.distritoTitle = copia.distritoTitle;
  this.areaId = copia.areaId;
  this.servicioId = copia.servicioId;
  this.entidadFinanciadoraId = copia.entidadFinanciadoraId;
}

//Constructor copia con lista de attributos a settear
public Subvencion(Subvencion copia, List<String> attributesToSet) {
  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("title")) {
	this.title = copia.title;
  }
  if (attributesToSet.contains("areaId")) {
	this.areaId = copia.areaId;
  }

  if (attributesToSet.contains("basesReguladoras")) {
	this.basesReguladoras = copia.basesReguladoras;
  }
  if (attributesToSet.contains("tipoInstrumento")) {
	this.tipoInstrumento = copia.tipoInstrumento;
  }

  if (attributesToSet.contains("nominativa")) {
	this.nominativa = copia.nominativa;
  }
  if (attributesToSet.contains("tipoProcedimiento")) {
	this.tipoProcedimiento = copia.tipoProcedimiento;
  }

  if (attributesToSet.contains("name")) {
	this.name = copia.name;
  }

  if (attributesToSet.contains("objeto")) {
	this.objeto = copia.objeto;
  }

  if (attributesToSet.contains("importeTotalConcedido")) {
	this.importeTotalConcedido = copia.importeTotalConcedido;
  }

  if (attributesToSet.contains("fechaAcuerdo")) {
	this.fechaAcuerdo = copia.fechaAcuerdo;
  }

  if (attributesToSet.contains("clasificacionPrograma")) {
	this.clasificacionPrograma = copia.clasificacionPrograma;
  }

  if (attributesToSet.contains("clasificacionEconomicaGasto")) {
	this.clasificacionEconomicaGasto = copia.clasificacionEconomicaGasto;
  }

  if (attributesToSet.contains("instrumentaId")) {
	this.instrumentaId = copia.instrumentaId;
  }

  if (attributesToSet.contains("instrumentaTitle")) {
	this.instrumentaTitle = copia.instrumentaTitle;
  }

  if (attributesToSet.contains("tieneTematica")) {
	this.tieneTematica = copia.tieneTematica;
  }

  if (attributesToSet.contains("gestionadoPorOrganization")) {
	this.gestionadoPorOrganization = copia.gestionadoPorOrganization;
  }

  if (attributesToSet.contains("organizationId")) {
	this.organizationId = copia.organizationId;
  }

  if (attributesToSet.contains("gestionadoPorDistrito")) {
	this.gestionadoPorDistrito = copia.gestionadoPorDistrito;
  }

  if (attributesToSet.contains("distritoId")) {
	this.distritoId = copia.distritoId;
  }

  if (attributesToSet.contains("distritoTitle")) {
	this.distritoTitle = copia.distritoTitle;
  }

  if (attributesToSet.contains("servicioId")) {
	this.servicioId = copia.servicioId;
  }

  if (attributesToSet.contains("entidadFinanciadoraId")) {
	this.entidadFinanciadoraId = copia.entidadFinanciadoraId;
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


@Column(name = "area_id")
public String getAreaId() {
  return this.areaId;
}

public void setAreaId(String areaId) {
  this.areaId = areaId;
}


@Column(name = "servicio_id")
public String getServicioId() {
  return this.servicioId;
}

public void setServicioId(String servicioId) {
  this.servicioId = servicioId;
}


@Column(name = "entidad_financiadora_id")
public String getEntidadFinanciadoraId() {
  return this.entidadFinanciadoraId;
}

public void setEntidadFinanciadoraId(String entidadFinanciadoraId) {
  this.entidadFinanciadoraId = entidadFinanciadoraId;
}

@Column(name = "id", length = 50)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}

@Column(name = "title", length = 400)
public String getTitle() {
  return this.title;
}

public void setTitle(String title) {
  this.title = title;
}

@Column(name = "bases_reguladoras", length = 400)
public String getBasesReguladoras() {
  return this.basesReguladoras;
}

public void setBasesReguladoras(String basesReguladoras) {
  this.basesReguladoras = basesReguladoras;
}

@Column(name = "tipo_instrumento", length = 100)
public String getTipoInstrumento() {
  return this.tipoInstrumento;
}

public void setTipoInstrumento(String tipoInstrumento) {
  this.tipoInstrumento = tipoInstrumento;
}

@Column(name = "nominativa")
public Boolean getNominativa() {
  return this.nominativa;
}

public void setNominativa(Boolean nominativa) {
  this.nominativa = nominativa;
}

@Column(name = "tipo_procedimiento", length = 100)
public String getTipoProcedimiento() {
  return this.tipoProcedimiento;
}

public void setTipoProcedimiento(String tipoProcedimiento) {
  this.tipoProcedimiento = tipoProcedimiento;
}

@Column(name = "name", length = 400)
public String getName() {
  return this.name;
}

public void setName(String name) {
  this.name = name;
}

@Column(name = "objeto", length = 2000)
public String getObjeto() {
  return this.objeto;
}

public void setObjeto(String objeto) {
  this.objeto = objeto;
}

@Column(name = "importe_total_concedido", precision = 12)
public BigDecimal getImporteTotalConcedido() {
  return this.importeTotalConcedido;
}

public void setImporteTotalConcedido(BigDecimal importeTotalConcedido) {
  this.importeTotalConcedido = importeTotalConcedido;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "fecha_acuerdo", length = 19)
public Date getFechaAcuerdo() {
  return this.fechaAcuerdo;
}

public void setFechaAcuerdo(Date fechaAcuerdo) {
  this.fechaAcuerdo = fechaAcuerdo;
}

@Column(name = "clasificacion_programa", length = 100)
public String getClasificacionPrograma() {
  return this.clasificacionPrograma;
}

public void setClasificacionPrograma(String clasificacionPrograma) {
  this.clasificacionPrograma = clasificacionPrograma;
}

@Column(name = "clasificacion_eco_gasto", length = 100)
public String getClasificacionEconomicaGasto() {
  return this.clasificacionEconomicaGasto;
}

public void setClasificacionEconomicaGasto(String clasificacionEconomicaGasto) {
  this.clasificacionEconomicaGasto = clasificacionEconomicaGasto;
}

@Column(name = "instrumenta_id", length = 50)
public String getInstrumentaId() {
  return this.instrumentaId;
}

public void setInstrumentaId(String instrumentaId) {
  this.instrumentaId = instrumentaId;
}

@Column(name = "instrumenta_title", length = 200)
public String getInstrumentaTitle() {
  return this.instrumentaTitle;
}

public void setInstrumentaTitle(String instrumentaTitle) {
  this.instrumentaTitle = instrumentaTitle;
}

@Column(name = "tiene_tematica", length = 100)
public String getTieneTematica() {
  return this.tieneTematica;
}

public void setTieneTematica(String tieneTematica) {
  this.tieneTematica = tieneTematica;
}

@Column(name = "gestionado_por_organization")
public Boolean getGestionadoPorOrganization() {
  return this.gestionadoPorOrganization;
}

public void setGestionadoPorOrganization(Boolean gestionadoPorOrganization) {
  this.gestionadoPorOrganization = gestionadoPorOrganization;
}

@Column(name = "organization_id", length = 50)
public String getOrganizationId() {
  return this.organizationId;
}

public void setOrganizationId(String organizationId) {
  this.organizationId = organizationId;
}

@Column(name = "gestionado_por_distrito")
public Boolean getGestionadoPorDistrito() {
  return this.gestionadoPorDistrito;
}

public void setGestionadoPorDistrito(Boolean gestionadoPorDistrito) {
  this.gestionadoPorDistrito = gestionadoPorDistrito;
}

@Column(name = "distrito_id", length = 50)
public String getDistritoId() {
  return this.distritoId;
}

public void setDistritoId(String distritoId) {
  this.distritoId = distritoId;
}

@Column(name = "distrito_title", length = 200)
public String getDistritoTitle() {
  return this.distritoTitle;
}

public void setDistritoTitle(String distritoTitle) {
  this.distritoTitle = distritoTitle;
}



@Transient
public String getInstrumentaIdIsolated() {
  return instrumentaIdIsolated;
}
@Transient
public void setInstrumentaIdIsolated(String instrumentaIdIsolated) {
  this.instrumentaIdIsolated = instrumentaIdIsolated;
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
  return "Subvencion [ikey=" + ikey + ", id=" + id + ", title=" + title + ", basesReguladoras=" + basesReguladoras + ", tipoInstrumento=" + tipoInstrumento + ", nominativa=" + nominativa + ", tipoProcedimiento=" + tipoProcedimiento
	  + ", name=" + name + ", objeto=" + objeto + ", importeTotalConcedido=" + importeTotalConcedido + ", fechaAcuerdo=" + fechaAcuerdo + ", clasificacionPrograma=" + clasificacionPrograma + ", clasificacionEcoGasto="
	  + clasificacionEconomicaGasto + ", instrumentaId=" + instrumentaId + ", instrumentaTitle=" + instrumentaTitle + ", tieneTematica=" + tieneTematica + ", gestionadoPorOrganization=" + gestionadoPorOrganization + ", organizationId="
	  + organizationId + ", gestionadoPorDistrito=" + gestionadoPorDistrito + ", distritoId=" + distritoId + ", distritoTitle=" + distritoTitle + ", areaId=" + areaId + ", servicioId=" + servicioId + ", entidadFinanciadoraId="
	  + entidadFinanciadoraId + "]";
}





@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {
	
	return  (T) cloneClass( (Subvencion) copia,listado) ;
}


// Constructor copia con lista de attributos a settear
public Subvencion cloneClass(Subvencion copia, List<String> attributesToSet) {
	
	Subvencion obj = new Subvencion(copia,attributesToSet);		
	
	return obj;
	
}

//Subvencion
// TODO valiaciones de subvenciones ver si es posible realizar mediante
// anotaciones en model el saber los campos obligatorios
public  List<String> validate() {
	List<String> result = new ArrayList<String>();

	// Validamos campos Obligatorios ver si es posible obtener esta información
	// mediante anotaciones del modelo
	if (!Util.validValue(this.getId())) {
		result.add("Id is not valid [Id:" + this.getId() + "]");
	}

	if (!Util.validValue(this.getTitle())) {
		result.add("Nombre is not valid [Nombre:" + this.getTitle() + "]");
	}

	
	if (!Util.validValue(this.getAreaId())) {
		result.add("AreaId is not valid [AreaId:" + this.getAreaId() + "]");
	}

	if (!Util.validValue(this.getEntidadFinanciadoraId())) {
		result.add("EntidadFinanciadoraId is not valid [EntidadFinanciadoraId:" + this.getEntidadFinanciadoraId()
				+ "]");
	}

	if (!Util.validValue(this.getImporteTotalConcedido())) {
		result.add("Importe is not valid [Importe:" + this.getImporteTotalConcedido() + "]");
	}

	

	return result;
}


}
