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
@Table(name = "agenda_m_rolintegranteevento")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto =  Context.ESAGM, propiedad = "RolIntegranteEvento")
@PathId(value="/agenda-municipal/rol-evento")
public class AgendaMRolEvento implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 4691260619899971301L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del rol integrante evento. Ejemplo: AGMROL0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre completo del integrante evento. Ejemplo: JOSE ANIORTE RUEDA")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="agentName", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.FOAF, propiedad = "name")
	@RdfBlankNode(tipo = Context.FOAF_URI + "Agent", propiedad = Context.ESAGM_URI + "integra", nodoId = "agentNode")
	private String agentName;
	
	@ApiModelProperty(value = "Nombre del integrante evento. Ejemplo: JOSE")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="nombre", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "nombre")	
	@RdfBlankNode(tipo = Context.ESAGM_URI + "Persona", propiedad = Context.ESAGM_URI + "integra", nodoId = "agentNode")
	private String nombre;
	
	@ApiModelProperty(value = "Apellido primero del integrante evento. Ejemplo: ANIORTE")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="apellido1", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "apellido1")	
	@RdfBlankNode(tipo = Context.ESAGM_URI + "Persona", propiedad = Context.ESAGM_URI + "integra", nodoId = "agentNode")
	private String apellido1;
	
	@ApiModelProperty(value = "Apellido segundo del integrante evento. Ejemplo: RUEDA")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="apellido2", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "apellido2")	
	@RdfBlankNode(tipo = Context.ESAGM_URI + "Persona", propiedad = Context.ESAGM_URI + "integra", nodoId = "agentNode")
	private String apellido2;
	
	@ApiModelProperty(value = "Identificador de la organización a la que pertenece el agente en calidad de miembro. Ejemplo: 61028")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="organizationId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "organization")
	@RdfExternalURI(inicioURI="/organigrama/organizacion/",finURI="organizationId", urifyLevel = 1)
	private String organizationId;
	
	@ApiModelProperty(value = "Nombre de la organización a la que pertenece el agente en calidad de miembro. Ejemplo: UNIDAD DE COORDINACION DE PROGRAMAS Y CENTROS")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="organizationTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ORG_URI+"organization", nodoId="organization")
	private String organizationTitle;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ORG_URI+"organization", nodoId="organization")
	private String organizationIdIsolated;
	
	@ApiModelProperty(value = "Función que una persona o agente desempeña en el seno de una organización. Ejemplo: DELEGADO/A DE AREA DE GOBIERNO")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="role", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "role")
	private String role;
	
	@ApiModelProperty(value = "El rol que puede tener un integrante de un evento. Ejemplo: titular")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="rol", format=Constants.STRING_FORMAT)			
	@Rdf(contexto = Context.ESAGM, propiedad = "rol")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/agenda-municipal/rol-integrante/", finURI="rol", urifyLevel=2)	
	private String rol;
	
	@ApiModelProperty(value = "Fecha y hora de inicio de asistencia de un integrante de un evento. Ejemplo: 2019-12-06T10:10:00")
	@CsvBindByPosition(position=10)
	@CsvBindByName (column = "inicioAsistencia")	
	@CsvDate(Constants.DATE_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "inicioAsistencia",typeURI=Context.XSD_URI+"dateTime" ))
	private Date inicioAsistencia;
	
	@ApiModelProperty(value = "Fecha y hora de fin de asistencia de un integrante de un evento. Ejemplo: 2019-12-06T11:15:00")
	@CsvBindByPosition(position=11)
	@CsvBindByName (column = "finAsistencia")	
	@CsvDate(Constants.DATE_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "finAsistencia",typeURI=Context.XSD_URI+"dateTime" ))
	private Date finAsistencia;
	
	@ApiModelProperty(value = "Evento en el que participa un integrante con un rol. Ejemplo: AGM0001")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="eventId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGM, propiedad = "evento")
	@RdfExternalURI(inicioURI="/agenda-municipal/evento/",finURI="eventId", urifyLevel = 1)
	private String eventId;

	public AgendaMRolEvento() {
	}

	public AgendaMRolEvento(String ikey) {
		this.ikey = ikey;
	}

	

	public AgendaMRolEvento(String ikey, String id, String agentName, String nombre, String apellido1, String apellido2,
			String organizationTitle, String organizationId, String organizationIdIsolated, String role, String rol,
			Date inicioAsistencia, Date finAsistencia, String eventId) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.agentName = agentName;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.organizationTitle = organizationTitle;
		this.organizationId = organizationId;
		this.organizationIdIsolated = organizationIdIsolated;
		this.role = role;
		this.rol = rol;
		this.inicioAsistencia = inicioAsistencia;
		this.finAsistencia = finAsistencia;
		this.eventId = eventId;
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

	@Column(name = "agent_name", length = 400)
	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Column(name = "organization_title", length = 400)
	public String getOrganizationTitle() {
		return this.organizationTitle;
	}

	public void setOrganizationTitle(String organizationTitle) {
		this.organizationTitle = organizationTitle;
	}

	@Column(name = "organization_id", length = 50)
	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "role", length = 200)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "rol", length = 200)
	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Column(name = "event_id", length = 400)
	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	
	@Transient
	public String getOrganizationIdIsolated() {
		return organizationIdIsolated;
	}

	@Transient
	public void setOrganizationIdIsolated(String organizationIdIsolated) {
		this.organizationIdIsolated = organizationIdIsolated;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inicio_asistencia", length = 19)
	public Date getInicioAsistencia() {
		return inicioAsistencia;
	}

	public void setInicioAsistencia(Date inicioAsistencia) {
		this.inicioAsistencia = inicioAsistencia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fin_asistencia", length = 19)
	public Date getFinAsistencia() {
		return finAsistencia;
	}

	public void setFinAsistencia(Date finAsistencia) {
		this.finAsistencia = finAsistencia;
	}
	
	
	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido1", length = 100)
	public String getApellido1() {
		return apellido1;
	}

	
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "apellido2", length = 100)
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (AgendaMRolEvento) copia,listado) ;
	}
	
	
	
	public AgendaMRolEvento(AgendaMRolEvento copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("agentName")) {
			this.agentName = copia.agentName;
		}
		
		if (attributesToSet.contains("organizationId")) {
			this.organizationId = copia.organizationId;
		}
		
		if (attributesToSet.contains("organizationTitle")) {
			this.organizationTitle = copia.organizationTitle;
		}
		
		if (attributesToSet.contains("organizationIdIsolated")) {
			this.organizationIdIsolated = copia.organizationIdIsolated;
		}
		
		if (attributesToSet.contains("eventId")) {
			this.eventId = copia.eventId;
		}
		
		if (attributesToSet.contains("rol")) {
			this.rol = copia.rol;
		}
		
		if (attributesToSet.contains("role")) {
			this.role = copia.role;
		}
		
		if (attributesToSet.contains("inicioAsistencia")) {
			this.inicioAsistencia = copia.inicioAsistencia;
		}
		
		if (attributesToSet.contains("finAsistencia")) {
			this.finAsistencia = copia.finAsistencia;
		}
		
		if (attributesToSet.contains("nombre")) {
			this.nombre = copia.nombre;
		}
		
		if (attributesToSet.contains("apellido1")) {
			this.apellido1 = copia.apellido1;
		}
		
		if (attributesToSet.contains("apellido2")) {
			this.apellido2 = copia.apellido2;
		}
		
	}
	
	
	public AgendaMRolEvento(AgendaMRolEvento obj) {

		this.ikey = obj.ikey;
		this.id = obj.id;
		this.eventId = obj.eventId;
		this.organizationId = obj.organizationId;
		this.organizationTitle = obj.organizationTitle;
		this.organizationIdIsolated = obj.organizationIdIsolated;
		this.rol = obj.rol;
		this.role = obj.role;
		this.agentName = obj.agentName;
		this.inicioAsistencia = obj.inicioAsistencia;
		this.finAsistencia = obj.finAsistencia;
		this.nombre = obj.nombre;
		this.apellido1 = obj.apellido1;
		this.apellido2 = obj.apellido2;
	}
	
	// Constructor copia con lista de attributos a settear
	public AgendaMRolEvento cloneClass(AgendaMRolEvento copia, List<String> attributesToSet) {
		
		AgendaMRolEvento obj = new AgendaMRolEvento(copia,attributesToSet);
		
		return obj;

	}

	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getAgentName())) {
			result.add("AgentName is not valid [AgentName:" + this.getAgentName() + "]");
		}
		
		
		if (!Util.validValue(this.getEventId())) {
			result.add("EventId is not valid [EventId:" + this.getEventId() + "]");
		}
				

		return result;
	}
	
	
	@Override
	public String toString() {
		return "AgendaMRolEvento [id=" + id + ", agentName=" + agentName + ", nombre=" + nombre + ", apellido1="
				+ apellido1 + ", apellido2=" + apellido2 + ", organizationTitle=" + organizationTitle
				+ ", organizationId=" + organizationId + ", organizationIdIsolated=" + organizationIdIsolated
				+ ", role=" + role + ", rol=" + rol + ", inicioAsistencia=" + inicioAsistencia + ", finAsistencia="
				+ finAsistencia + ", eventId=" + eventId + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESAGM, Context.ESAGM_URI);		
		prefixes.put(Context.ORG, Context.ORG_URI);	
		prefixes.put(Context.FOAF, Context.FOAF_URI);
		prefixes.put(Context.TIME, Context.TIME_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);
	
		return prefixes;
	}

	

	
	
	
}
