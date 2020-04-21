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
import org.ciudadesabiertas.model.ICallejero;
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
@Table(name = "agenda_m_evento")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAGM, propiedad = "Evento")
@PathId(value="/agenda-municipal/evento")
public class AgendaMEvento implements java.io.Serializable, RDFModel, ICallejero {

	@JsonIgnore
	private static final long serialVersionUID = 4694265619899971301L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del evento. Ejemplo: AGM0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del evento. Ejemplo: Acto de entrega de los Premios Los Leones de EL ESPAÑOL. Global")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Web del evento. Ejemplo: https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@ApiModelProperty(value = "Fecha de comienzo del evento. Ejemplo: 2019-12-06T10:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "startDate")	
	@CsvDate(Constants.DATE_FORMAT)
	//@RdfMultiple({@Rdf(contexto = Context.ESAGM, propiedad = "startDate",typeURI=Context.XSD_URI+"date" ),@Rdf(contexto = Context.ESAGENDA, propiedad = "horaInicio",typeURI=Context.XSD_URI+"time")})
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "startDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date startDate;
	
	@ApiModelProperty(value = "Fecha de fin del evento. Ejemplo: 2019-12-10T20:00:00")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "endDate")	
	@CsvDate(Constants.DATE_FORMAT)
	//@RdfMultiple({@Rdf(contexto = Context.ESAGM, propiedad = "endDate",typeURI=Context.XSD_URI+"date" ),@Rdf(contexto = Context.ESAGENDA, propiedad = "horaFin",typeURI=Context.XSD_URI+"time")})
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "endDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date endDate;
	
	@ApiModelProperty(value = "Nombre del lugar del evento. Ejemplo: Auditorio del Comité Olímpico Español")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="locationTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "locationTitle")
	private String locationTitle;
	
	@ApiModelProperty(value = "Nombre del equipamiento del evento. Ejemplo: Teatro Auditorio Ciudad de Alcobendas")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="equipamientoTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoTitle;
	
	@ApiModelProperty(value = "Identificador del equipamiento del evento. Ejemplo: EQ0044")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="equipamientoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "equipamiento")
	@RdfExternalURI(inicioURI="/equipamiento/equipamiento/",finURI="equipamientoId", urifyLevel = 1)
	private String equipamientoId;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoIdIsolated;
	
	@ApiModelProperty(value = "Un evento del que este evento es parte. Por ejemplo, una colección de actuaciones musicales individuales podría tener un festival de música como su superevento.. Ejemplo: AGM0001")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="superEventId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "superEvent")
	@RdfExternalURI(inicioURI="/agenda-municipal/evento/",finURI="superEventId", urifyLevel = 1)
	private String superEventId;
	
	@ApiModelProperty(value = "Reuniones con carácter de lobby en donde una persona física, jurídica y entidades sin personalidad que pretenda influir en la normativa y en las políticas municipales y en las decisiones de impacto general, lo hacen a través de encuentros con concejales, directivos y personal eventual municipal. Ejemplo: false")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="reunionLobby", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGENDA, propiedad = "reunionLobby", typeURI=Context.XSD_URI+"boolean")
	private Boolean reunionLobby;
	
	@ApiModelProperty(value = "Tipología que define a qué categoría pertenece el evento. Los tipos de evento se representan mediante una lista de conceptos. Ejemplo: evento-con-medio-de-comunicacion")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="tipoEvento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "tipoEvento")		
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/agenda-municipal/tipo-evento/", finURI="tipoEvento", urifyLevel=2)
	private String tipoEvento;
	
	@ApiModelProperty(value = "Tipo de asistencia al evento: público, privado o restringido. Ejemplo: privado")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="tipoAcceso", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "tipoAcceso")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/agenda-municipal/tipo-acceso/", finURI="tipoAcceso", urifyLevel=2)
	private String tipoAcceso;
	
	@ApiModelProperty(value = "Para los eventos de órganos colegiados, tipo de sesión puede ser ordinaria, extraordinaria o extraordinaria urgente. Ejemplo: extraordinaria")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="tipoSesion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "tipoSesion")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/agenda-municipal/tipo-sesion/", finURI="tipoSesion", urifyLevel=2)
	private String tipoSesion;
		
	@ApiModelProperty(value = "Canal del evento. Ejemplo: presencial")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="canal", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "canal")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/agenda-municipal/canal/", finURI="canal", urifyLevel=2)
	private String canal;
	
	@ApiModelProperty(value = "Calle del evento. Ejemplo: CL BLAS DE OTERO 4")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")		
	private String streetAddress;
	
	@ApiModelProperty(value = "Código postal del evento. Ejemplo: 28100")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@ApiModelProperty(value = "Identificador de la calle del evento. Ejemplo: PORTAL000119")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	
	@ApiModelProperty(value = "Identificador del municipio del evento. Ejemplo: 28006")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId")	
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio del evento. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del barrio del evento. Ejemplo: 28006011")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")	
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId",capitalize=true, urifyLevel = 1)
	private String barrioId;
	
	@ApiModelProperty(value = "Nombre del barrio del evento. Ejemplo: 28006011")
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="barrioTitle", format=Constants.STRING_FORMAT)
	private String barrioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito del evento. Ejemplo: 2800601")
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")	
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId",capitalize=true, urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito del evento. Ejemplo: Unico")
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@ApiModelProperty(value = "Descripción del evento. Ejemplo: Descripcion Acto de entrega de los Premios Los Leones de EL ESPAÑOL. Global")
	@CsvBindByPosition(position=24)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	public AgendaMEvento() {
	}

	public AgendaMEvento(String ikey) {
		this.ikey = ikey;
	}
	


	public AgendaMEvento(String ikey, String id, String title, String description, String url, Date startDate,
			Date endDate, String locationTitle, String streetAddress, String postalCode, String portalId,
			String municipioId, String municipioTitle, String distritoId, String distritoTitle, String equipamientoId,
			String equipamientoTitle, String superEventId, Boolean reunionLobby, String tipoEvento, String tipoAcceso,
			String tipoSesion, String canal) {
		this.ikey = ikey;
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationTitle = locationTitle;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.portalId = portalId;
		this.municipioId = municipioId;
		this.municipioTitle = municipioTitle;
		this.distritoId = distritoId;
		this.distritoTitle = distritoTitle;
		this.equipamientoId = equipamientoId;
		this.equipamientoTitle = equipamientoTitle;
		this.superEventId = superEventId;
		this.reunionLobby = reunionLobby;
		this.tipoEvento = tipoEvento;
		this.tipoAcceso = tipoAcceso;
		this.tipoSesion = tipoSesion;
		this.canal = canal;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
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

	@Column(name = "description", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "location_title", length = 400)
	public String getLocationTitle() {
		return this.locationTitle;
	}

	public void setLocationTitle(String locationTitle) {
		this.locationTitle = locationTitle;
	}

	@Column(name = "street_address", length = 200)
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "portal_id", length = 50)
	public String getPortalId() {
		return this.portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	@Transient
	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated=portalIdIsolated;
		
	}

	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
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

	@Column(name = "equipamiento_id", length = 50)
	public String getEquipamientoId() {
		return this.equipamientoId;
	}

	public void setEquipamientoId(String equipamientoId) {
		this.equipamientoId = equipamientoId;
	}

	@Column(name = "equipamiento_title", length = 400)
	public String getEquipamientoTitle() {
		return this.equipamientoTitle;
	}

	public void setEquipamientoTitle(String equipamientoTitle) {
		this.equipamientoTitle = equipamientoTitle;
	}

	@Column(name = "super_event_id", length = 50)
	public String getSuperEventId() {
		return this.superEventId;
	}

	public void setSuperEventId(String superEventId) {
		this.superEventId = superEventId;
	}

	@Column(name = "reunion_lobby")
	public Boolean getReunionLobby() {
		return this.reunionLobby;
	}

	public void setReunionLobby(Boolean reunionLobby) {
		this.reunionLobby = reunionLobby;
	}

	@Column(name = "tipo_evento", length = 200)
	public String getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Column(name = "tipo_acceso", length = 200)
	public String getTipoAcceso() {
		return this.tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	@Column(name = "tipo_sesion", length = 200)
	public String getTipoSesion() {
		return this.tipoSesion;
	}

	public void setTipoSesion(String tipoSesion) {
		this.tipoSesion = tipoSesion;
	}

	@Column(name = "canal", length = 200)
	public String getCanal() {
		return this.canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}
	
	@Column(name = "barrio_title", length = 400)
	public String getBarrioTitle() {
		return this.barrioTitle;
	}

	public void setBarrioTitle(String barrioTitle) {
		this.barrioTitle = barrioTitle;
	}
	
	@Column(name = "barrio_id", length = 50)
	public String getBarrioId() {
		return this.barrioId;
	}

	public void setBarrioId(String barrioId) {
		this.barrioId = barrioId;
	}

	@Transient
	public String getEquipamientoIdIsolated() {
		return equipamientoIdIsolated;
	}

	@Transient
	public void setEquipamientoIdIsolated(String equipamientoIdIsolated) {
		this.equipamientoIdIsolated = equipamientoIdIsolated;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (AgendaMEvento) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public AgendaMEvento cloneClass(AgendaMEvento copia, List<String> attributesToSet) {
		
		AgendaMEvento obj = new AgendaMEvento(copia,attributesToSet);
		
		return obj;

	}
	
	public AgendaMEvento(AgendaMEvento copia, List<String> attributesToSet) {
		
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

		if (attributesToSet.contains("url")) {
			this.url = copia.url;
		}
		
		if (attributesToSet.contains("startDate")) {
			this.startDate = copia.startDate;
		}
		
		if (attributesToSet.contains("endDate")) {
			this.endDate = copia.endDate;
		}
		
		if (attributesToSet.contains("locationTitle")) {
			this.locationTitle = copia.locationTitle;
		}
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;
		}
		
		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;
		}		
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}
		
		if (attributesToSet.contains("barrioId")) {
			this.barrioId = copia.barrioId;
		}
		
		if (attributesToSet.contains("barrioTitle")) {
			this.barrioTitle = copia.barrioTitle;
		}

		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;
		}
		
		if (attributesToSet.contains("distritoTitle")) {
			this.distritoTitle = copia.distritoTitle;
		}
		
		if (attributesToSet.contains("equipamientoId")) {
			this.equipamientoId = copia.equipamientoId;
		}
		
		if (attributesToSet.contains("equipamientoTitle")) {
			this.equipamientoTitle = copia.equipamientoTitle;
		}
		
		if (attributesToSet.contains("superEventId")) {
			this.superEventId = copia.superEventId;
		}
		
		if (attributesToSet.contains("reunionLobby")) {
			this.reunionLobby = copia.reunionLobby;
		}
		
		if (attributesToSet.contains("tipoEvento")) {
			this.tipoEvento = copia.tipoEvento;
		}
		
		if (attributesToSet.contains("tipoAcceso")) {
			this.tipoAcceso = copia.tipoAcceso;
		}
		
		if (attributesToSet.contains("tipoSesion")) {
			this.tipoSesion = copia.tipoSesion;
		}
		
		if (attributesToSet.contains("canal")) {
			this.canal = copia.canal;
		}

		
	}
	
	
	public AgendaMEvento(AgendaMEvento obj) {

		this.ikey = obj.ikey;
		this.id = obj.id;
		this.title = obj.title;
		this.description = obj.description;
		this.url = obj.url;
		this.startDate = obj.startDate;
		this.endDate = obj.endDate;
		this.locationTitle = obj.locationTitle;
		this.streetAddress = obj.streetAddress;
		this.postalCode = obj.postalCode;
		this.portalId = obj.portalId;
		this.municipioId = obj.municipioId;
		this.municipioTitle = obj.municipioTitle;
		this.barrioId = obj.barrioId;
		this.barrioTitle = obj.barrioTitle;
		this.distritoId = obj.distritoId;
		this.distritoTitle = obj.distritoTitle;
		this.equipamientoId = obj.equipamientoId;
		this.equipamientoTitle = obj.equipamientoTitle;
		this.superEventId = obj.superEventId;
		this.reunionLobby = obj.reunionLobby;
		this.tipoEvento = obj.tipoEvento;
		this.tipoAcceso = obj.tipoAcceso;
		this.tipoSesion = obj.tipoSesion;
		this.canal = obj.canal;
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

		if (!Util.validValue(this.getStartDate())) {
			result.add("StartDate is not valid [StartDate:" + this.getStartDate() + "]");
		}
				

		return result;
	}
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);				
		prefixes.put(Context.ESEQUIP, Context.ESEQUIP_URI);
		prefixes.put(Context.ESAGM, Context.ESAGM_URI);
		prefixes.put(Context.ESAGENDA, Context.ESAGENDA_URI);
		prefixes.put(Context.ORG, Context.ORG_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.TIME, Context.TIME_URI);
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "AgendaMEvento [ikey=" + ikey + ", id=" + id + ", title=" + title + ", url=" + url + ", startDate="
				+ startDate + ", endDate=" + endDate + ", locationTitle=" + locationTitle + ", streetAddress="
				+ streetAddress + ", postalCode=" + postalCode + ", portalId=" + portalId + ", municipioId="
				+ municipioId + ", municipioTitle=" + municipioTitle + ", barrioId=" + barrioId + ", barrioTitle="
				+ barrioTitle + ", distritoId=" + distritoId + ", distritoTitle=" + distritoTitle
				+ ", equipamientoTitle=" + equipamientoTitle + ", equipamientoId=" + equipamientoId + ", superEventId="
				+ superEventId + ", reunionLobby=" + reunionLobby + ", tipoEvento=" + tipoEvento + ", tipoAcceso="
				+ tipoAcceso + ", tipoSesion=" + tipoSesion + ", canal=" + canal + ", description=" + description + "]";
	}
	
	

}
