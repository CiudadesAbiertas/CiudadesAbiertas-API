/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
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
@Table(name = "convenio")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCONV, propiedad = "Convenio")
@PathId(value="/convenio/convenio")
public class Convenio implements java.io.Serializable, RDFModel, IGestionadoPor {

	@JsonIgnore
	private static final long serialVersionUID = 7319492074359677904L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del Convenio. Ejemplo: CONV001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del convenio. Ejemplo: CONVENIO PRUEBAS 1")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Descripción del convenio. Ejemplo: DESC CONVENIO PRUEBAS 1")
	@CsvBindByPosition(position=27)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	@ApiModelProperty(value = "Objeto del convenio. Ejemplo: TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="objeto", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "objeto")
	private String objeto;
	
	@ApiModelProperty(value = "Fecha de inicio del convenio. Ejemplo: 2020-01-01T00:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="fechaInicio")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.ESCONV, propiedad = "fechaInicio",typeURI=Context.XSD_URI+"dateTime" ))
	private Date fechaInicio;
	
	@ApiModelProperty(value = "Fecha de finalización del convenio. Ejemplo: 2021-01-01T00:00:00")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="fechaFinalizacion")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.ESCONV, propiedad = "fechaFinalizacion",typeURI=Context.XSD_URI+"dateTime" ))
	private Date fechaFinalizacion;
	
	@ApiModelProperty(value = "Fecha de suscripción del convenio. Ejemplo: 2020-02-01T00:00:00")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="fechaSuscripcion")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "fechaSuscripcion", typeURI=Context.XSD_URI+"dateTime")
	private Date fechaSuscripcion;
	
	@ApiModelProperty(value = "Fecha de resolución del convenio. Ejemplo: 2020-06-01T00:00:00")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="fechaResolucionFin")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "fechaResolucionFinalizacion", typeURI=Context.XSD_URI+"dateTime")	
	private Date fechaResolucionFin;
	
	@ApiModelProperty(value = "Fecha de incorporación del convenio. Ejemplo: 2020-03-01T00:00:00")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="fechaIncorporacion")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "fechaIncorporacion", typeURI=Context.XSD_URI+"dateTime")
	private Date fechaIncorporacion;
	
	@ApiModelProperty(value = "Obligación económiaca del convenio (Euros). Ejemplo: 1200000.80")
	@CsvBindByPosition(position=9)
	@CsvBindByName (column = "obligacionEconomicaAyto")
	@Rdf(contexto = Context.ESCONV, propiedad = "obligacionEconomicaAyto", typeURI=Context.XSD_URI+"float")
	private BigDecimal obligacionEconomicaAyto;
	
	@ApiModelProperty(value = "Tipo del convenio. Ejemplo: tipo-convenio-2")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="tipoConvenio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "tipoConvenio")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/convenios/tipo-convenio/", finURI="tipoConvenio", urifyLevel=2)	
	private String tipoConvenio;
	
	@ApiModelProperty(value = "Tipo Variación del convenio. Ejemplo: prorroga")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="tipoVariacion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "tipoVariacion")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/convenio/tipo-variacion/", finURI="tipoVariacion", urifyLevel=2)	
	private String tipoVariacion;
	
	@ApiModelProperty(value = "Modalidad del convenio. Ejemplo: modalidad-convenio-2")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="modalidad", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "modalidad")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/convenios/modalidad/", finURI="modalidad", urifyLevel=2)
	private String modalidad;
	
	@ApiModelProperty(value = "Materia del convenio (Euros). Ejemplo: tipo-materia-2")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="materia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "materia")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/page/datosabiertos/kos/hacienda/presupuesto/programa-gasto/", finURI="materia", urifyLevel=2)
	private String materia;
	
	@ApiModelProperty(value = "Fecha de Adjudicación del convenio. Ejemplo: 2020-01-10T00:00:00")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="fechaAdjudicacionSub")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.ESSUBV, propiedad = "fechaAdjudicacion", typeURI=Context.XSD_URI+"dateTime")
	@RdfBlankNode(tipo=Context.ESSUBV_URI+"Subvencion", propiedad=Context.ESCONV_URI+"instrumenta", nodoId="instrumenta")
	private Date fechaAdjudicacionSub;
	
	@ApiModelProperty(value = "Importe (subvención) del convenio (Euros). Ejemplo: 400000.80")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="importeSubv")	
	@Rdf(contexto = Context.ESSUBV, propiedad = "importeSubv", typeURI=Context.XSD_URI+"float")
	@RdfBlankNode(tipo=Context.ESSUBV_URI+"Subvencion", propiedad=Context.ESCONV_URI+"instrumenta", nodoId="instrumenta")
	private BigDecimal importeSubv;
	
	@ApiModelProperty(value = "Adjudicatario (subvención) del convenio. Ejemplo: Pedro Ruiz Gomez")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="adjudicatario", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSUBV, propiedad = "adjudicatario")
	@RdfBlankNode(tipo=Context.ESSUBV_URI+"Subvencion", propiedad=Context.ESCONV_URI+"instrumenta", nodoId="instrumenta")
	private String adjudicatarioTitleSub;
	
	@ApiModelProperty(value = "Subveción (id) del convenio. Ejemplo: SUBV001")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="subvencionId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "instrumenta")
	@RdfExternalURI(inicioURI="/subvencion/subvencion/",finURI="subvencionId", urifyLevel = 1)	
	private String subvencionId;
	
	@ApiModelProperty(value = "Organización (id) del convenio. Ejemplo: SUBV001")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="organizationId",format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "gestionadoPor")
	@RdfExternalURI(inicioURI="/convenio/organization/",finURI="organizationId", urifyLevel = 1)
	private String organizationId;
	
	@ApiModelProperty(value = "Organización obligado prestación (id) del convenio. Ejemplo: ORG0002")
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="orgIdObligadoPrestacion",format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "obligadoPrestacionesAyuntamiento")
	@RdfExternalURI(inicioURI="/convenio/organization/",finURI="organizationId", urifyLevel = 1)
	private String orgIdObligadoPrestacion;
	
	@ApiModelProperty(value = "Gestionado por organizacion (true / false) del convenio. Ejemplo: true")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="gestionadoPorOrganization", format=Constants.STRING_FORMAT)
	private Boolean gestionadoPorOrganization;
	
	@ApiModelProperty(value = "Gestionado por distrito (true / false) del convenio. Ejemplo: true")
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="gestionadoPorDistrito", format=Constants.STRING_FORMAT)
	private Boolean gestionadoPorDistrito;
	
	@ApiModelProperty(value = "Identificador del municipio del evento. Ejemplo: 28006")
	@CsvBindByPosition(position=22)
	@CsvBindByName (column = "municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio del convenio. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=23)
    @CsvBindByName (column = "municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito del convenio. Ejemplo: 28006011")
	@CsvBindByPosition(position=24)
	@CsvBindByName (column = "distritoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "gestionadoPor")
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito del convenio. Ejemplo: Unico")
	@CsvBindByPosition(position=25)
    @CsvBindByName (column = "distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@ApiModelProperty(value = "Variación (id) del convenio. Ejemplo: CONV001")
	@CsvBindByPosition(position=26)
	@CsvBindByName (column = "esVariacionId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "esVariacionDe")
	@RdfExternalURI(inicioURI="/convenio/convenio/",finURI="esVariacionId", urifyLevel = 1)	
	private String esVariacionId;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")	
	@RdfBlankNode(tipo=Context.ESSUBV_URI+"Subvencion", propiedad=Context.ESCONV_URI+"instrumenta", nodoId="instrumenta")
	private String subvencionIdIsolated;


	public Convenio() {
	}

	public Convenio(String ikey, String id) {
		this.ikey = ikey;
		this.id = id;
	}

	public Convenio(String ikey, String id, String title, String description, 
			String objeto, Date fechaInicio, Date fechaFinalizacion, Date fechaSuscripcion, Date fechaResolucionFin,
			Date fechaIncorporacion, BigDecimal obligacionEconomicaAyto, String tipoConvenio,String tipoVariacion, String modalidad,
			String materia, Date fechaAdjudicacionSub, BigDecimal importeSubv, String adjudicatarioTitleSub,
			String subvencionId, String organizationId, String orgIdObligadoPrestacion, Boolean gestionadoPorOrganization,
			Boolean gestionadoPorDistrito,  String municipioId, String municipioTitle,
			String distritoId, String distritoTitle,String esVariacionId) {
		
		this.ikey = ikey;		
		this.id = id;
		this.title = title;
		this.description = description;
		this.objeto = objeto;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.fechaSuscripcion = fechaSuscripcion;
		this.fechaResolucionFin = fechaResolucionFin;
		this.fechaIncorporacion = fechaIncorporacion;
		this.obligacionEconomicaAyto = obligacionEconomicaAyto;
		this.tipoConvenio = tipoConvenio;
		this.tipoVariacion = tipoVariacion;
		this.modalidad = modalidad;
		this.materia = materia;
		this.fechaAdjudicacionSub = fechaAdjudicacionSub;
		this.importeSubv = importeSubv;
		this.adjudicatarioTitleSub = adjudicatarioTitleSub;
		this.subvencionId = subvencionId;
		this.organizationId = organizationId;
		this.orgIdObligadoPrestacion = orgIdObligadoPrestacion;
		this.gestionadoPorOrganization = gestionadoPorOrganization;
		this.gestionadoPorDistrito = gestionadoPorDistrito;
		
		this.municipioId = municipioId;
		this.municipioTitle = municipioTitle;
		this.distritoId = distritoId;
		this.distritoTitle = distritoTitle;		
		this.esVariacionId = esVariacionId;
	}
	
	
	public Convenio(Convenio obj) {
		this.ikey = obj.ikey;		
		this.id = obj.id;
		this.title = obj.title;
		this.description = obj.description;
		this.objeto = obj.objeto;
		this.fechaInicio = obj.fechaInicio;
		this.fechaFinalizacion = obj.fechaFinalizacion;
		this.fechaSuscripcion = obj.fechaSuscripcion;
		this.fechaResolucionFin = obj.fechaResolucionFin;
		this.fechaIncorporacion = obj.fechaIncorporacion;
		this.obligacionEconomicaAyto = obj.obligacionEconomicaAyto;
		this.tipoConvenio = obj.tipoConvenio;
		this.tipoVariacion = obj.tipoVariacion;
		this.modalidad = obj.modalidad;
		this.materia = obj.materia;
		this.fechaAdjudicacionSub = obj.fechaAdjudicacionSub;
		this.importeSubv = obj.importeSubv;
		this.adjudicatarioTitleSub = obj.adjudicatarioTitleSub;
		this.subvencionId = obj.subvencionId;
		this.organizationId = obj.organizationId;
		this.orgIdObligadoPrestacion = obj.orgIdObligadoPrestacion;
		this.gestionadoPorOrganization = obj.gestionadoPorOrganization;
		this.gestionadoPorDistrito = obj.gestionadoPorDistrito;
		
		this.municipioId = obj.municipioId;
		this.municipioTitle = obj.municipioTitle;
		this.distritoId = obj.distritoId;
		this.distritoTitle = obj.distritoTitle;		
		this.esVariacionId = obj.esVariacionId;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	
	@Column(name = "id", nullable = false, length = 50)
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

	@Column(name = "description", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name = "objeto", length = 400)
	public String getObjeto() {
		return this.objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio", length = 19)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_finalizacion", length = 19)
	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_suscripcion", length = 19)
	public Date getFechaSuscripcion() {
		return this.fechaSuscripcion;
	}

	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_resolucion_fin", length = 19)
	public Date getFechaResolucionFin() {
		return this.fechaResolucionFin;
	}

	public void setFechaResolucionFin(Date fechaResolucionFin) {
		this.fechaResolucionFin = fechaResolucionFin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_incorporacion", length = 19)
	public Date getFechaIncorporacion() {
		return this.fechaIncorporacion;
	}

	public void setFechaIncorporacion(Date fechaIncorporacion) {
		this.fechaIncorporacion = fechaIncorporacion;
	}

	@Column(name = "obligacion_economica_ayto", precision = 12)
	public BigDecimal getObligacionEconomicaAyto() {
		return this.obligacionEconomicaAyto;
	}

	public void setObligacionEconomicaAyto(BigDecimal obligacionEconomicaAyto) {
		this.obligacionEconomicaAyto = obligacionEconomicaAyto;
	}

	@Column(name = "tipo_convenio", length = 100)
	public String getTipoConvenio() {
		return this.tipoConvenio;
	}

	public void setTipoConvenio(String tipoConvenio) {
		this.tipoConvenio = tipoConvenio;
	}
	
	@Column(name = "tipo_variacion", length = 100)
	public String getTipoVariacion() {
		return this.tipoVariacion;
	}

	public void setTipoVariacion(String tipoVariacion) {
		this.tipoVariacion = tipoVariacion;
	}

	@Column(name = "modalidad", length = 100)
	public String getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	@Column(name = "materia", length = 100)
	public String getMateria() {
		return this.materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_adjudicacion_sub", length = 19)
	public Date getFechaAdjudicacionSub() {
		return this.fechaAdjudicacionSub;
	}

	public void setFechaAdjudicacionSub(Date fechaAdjudicacionSub) {
		this.fechaAdjudicacionSub = fechaAdjudicacionSub;
	}

	@Column(name = "importe_subv", precision = 12)
	public BigDecimal getImporteSubv() {
		return this.importeSubv;
	}

	public void setImporteSubv(BigDecimal importeSubv) {
		this.importeSubv = importeSubv;
	}

	@Column(name = "adjudicatario_title_sub", length = 200)
	public String getAdjudicatarioTitleSub() {
		return this.adjudicatarioTitleSub;
	}

	public void setAdjudicatarioTitleSub(String adjudicatarioTitleSub) {
		this.adjudicatarioTitleSub = adjudicatarioTitleSub;
	}

	@Column(name = "subvencion_id", length = 50)
	public String getSubvencionId() {
		return this.subvencionId;
	}

	public void setSubvencionId(String subvencionId) {
		this.subvencionId = subvencionId;
	}

	@Column(name = "organization_id", length = 50)
	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	@Column(name = "gestionado_por_organization")
	public Boolean getGestionadoPorOrganization() {
		return this.gestionadoPorOrganization;
	}

	public void setGestionadoPorOrganization(Boolean gestionadoPorOrganization) {
		this.gestionadoPorOrganization = gestionadoPorOrganization;
	}

	@Column(name = "gestionado_por_distrito")
	public Boolean getGestionadoPorDistrito() {
		return this.gestionadoPorDistrito;
	}

	public void setGestionadoPorDistrito(Boolean gestionadoPorDistrito) {
		this.gestionadoPorDistrito = gestionadoPorDistrito;
	}


	@Column(name = "municipio_id", length = 50)
	public String getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle() {
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
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

	
	@Column(name = "es_variacion_id", length = 50)
	public String getEsVariacionId() {
		return esVariacionId;
	}

	public void setEsVariacionId(String esVariacionId) {
		this.esVariacionId = esVariacionId;
	}
	
	@Column(name = "org_id_obligado_presta", length = 50)
	public String getOrgIdObligadoPrestacion() {
		return orgIdObligadoPrestacion;
	}

	public void setOrgIdObligadoPrestacion(String orgIdObligadoPrestacion) {
		this.orgIdObligadoPrestacion = orgIdObligadoPrestacion;
	}
	
	//METODOS TRANSIENT

	@Transient
	public String getSubvencionIdIsolated() {
		return subvencionIdIsolated;
	}
	
	@Transient
	public void setSubvencionIdIsolated(String subvencionIdIsolated) {
		this.subvencionIdIsolated = subvencionIdIsolated;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Convenio) copia,listado) ;
	}
	
	
	

	// Constructor copia con lista de attributos a settear
	public Convenio cloneClass(Convenio copia, List<String> attributesToSet) {
		
		Convenio obj = new Convenio(copia,attributesToSet);
		
		return obj;

	}
	
	public Convenio(Convenio copia,List<String> attributesToSet) {
		
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
		
		if (attributesToSet.contains("objeto")) {
			this.objeto = copia.objeto;
		}
		if (attributesToSet.contains("fechaInicio")) {
			this.fechaInicio = copia.fechaInicio;
		}
		
		if (attributesToSet.contains("fechaFinalizacion")) {
			this.fechaFinalizacion = copia.fechaFinalizacion;
		}
		
		if (attributesToSet.contains("fechaSuscripcion")) {
			this.fechaSuscripcion = copia.fechaSuscripcion;
		}
		
		if (attributesToSet.contains("fechaResolucionFin")) {
			this.fechaResolucionFin = copia.fechaResolucionFin;
		}
		if (attributesToSet.contains("fechaIncorporacion")) {
			this.fechaIncorporacion = copia.fechaIncorporacion;
		}
		if (attributesToSet.contains("obligacionEconomicaAyto")) {
			this.obligacionEconomicaAyto = copia.obligacionEconomicaAyto;
		}
		if (attributesToSet.contains("tipoConvenio")) {
			this.tipoConvenio = copia.tipoConvenio;
		}
		if (attributesToSet.contains("tipoVariacion")) {
			this.tipoVariacion = copia.tipoVariacion;
		}
		if (attributesToSet.contains("modalidad")) {
			this.modalidad = copia.modalidad;
		}
		
		if (attributesToSet.contains("modalidad")) {
			this.materia = copia.materia;
		}
		
		if (attributesToSet.contains("fechaAdjudicacionSub")) {
			this.fechaAdjudicacionSub = copia.fechaAdjudicacionSub;
		}
		
		if (attributesToSet.contains("importeSubv")) {
			this.importeSubv = copia.importeSubv;
		}
		
		if (attributesToSet.contains("adjudicatarioTitleSub")) {
			this.adjudicatarioTitleSub = copia.adjudicatarioTitleSub;
		}
		
		if (attributesToSet.contains("subvencionId")) {
			this.subvencionId = copia.subvencionId;
		}
		
		if (attributesToSet.contains("organizationId")) {
			this.organizationId = copia.organizationId;
		}	
		
		if (attributesToSet.contains("orgIdObligadoPrestacion")) {
			this.orgIdObligadoPrestacion = copia.orgIdObligadoPrestacion;
		}
		
		if (attributesToSet.contains("gestionadoPorOrganization")) {
			this.gestionadoPorOrganization = copia.gestionadoPorOrganization;
		}
		
		if (attributesToSet.contains("gestionadoPorDistrito")) {
			this.gestionadoPorDistrito = copia.gestionadoPorDistrito;
		}
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}
		
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;
		}
		
		if (attributesToSet.contains("distritoId")) {
			this.distritoTitle = copia.distritoTitle;
		}
		
		if (attributesToSet.contains("esVariacionId")) {
			this.esVariacionId = copia.esVariacionId;
		}
				
	}
	
	public  List<String> validate() {
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
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESSUBV, Context.ESSUBV_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);							
		prefixes.put(Context.ORG, Context.ORG_URI);				
		prefixes.put(Context.ESCONV, Context.ESCONV_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "Convenio [id=" + id + ", title=" + title + ", description=" + description + ", objeto=" + objeto
				+ ", fechaInicio=" + fechaInicio + ", fechaFinalizacion=" + fechaFinalizacion + ", fechaSuscripcion="
				+ fechaSuscripcion + ", fechaResolucionFin=" + fechaResolucionFin + ", fechaIncorporacion="
				+ fechaIncorporacion + ", obligacionEconomicaAyto=" + obligacionEconomicaAyto + ", tipoConvenio="
				+ tipoConvenio + ", tipoVariacion=" + tipoVariacion + ", modalidad=" + modalidad + ", materia="
				+ materia + ", fechaAdjudicacionSub=" + fechaAdjudicacionSub + ", importeSubv=" + importeSubv
				+ ", adjudicatarioTitleSub=" + adjudicatarioTitleSub + ", subvencionId=" + subvencionId
				+ ", organizationId=" + organizationId + ", orgIdObligadoPrestacion=" + orgIdObligadoPrestacion
				+ ", gestionadoPorOrganization=" + gestionadoPorOrganization + ", gestionadoPorDistrito="
				+ gestionadoPorDistrito + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", distritoId=" + distritoId + ", distritoTitle=" + distritoTitle + ", esVariacionId=" + esVariacionId
				+ ", subvencionIdIsolated=" + subvencionIdIsolated + "]";
	}

	

	

	
	
	
}
