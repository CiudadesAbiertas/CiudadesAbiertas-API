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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
import org.ciudadesabiertas.model.IEquipamiento;
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
@Table(name = "agenda")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAGENDA, propiedad = "Evento")
@PathId(value="/agenda-cultural/evento")
public class Agenda implements java.io.Serializable,RDFModel, IEquipamiento {
	

	@JsonIgnore
	private static final long serialVersionUID = -5080412527095370127L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del evento. Ejemplo: AG0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del evento. Ejemplo: Programación cultural Revista El Público. Octubre 2018-Enero2019")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Imagen del evento. Ejemplo: https://terrigen-cdn-dev.marvel.com/content/prod/1x/cm_payoff_1-sht_v6_lg_3.jpg")
	@CsvBindByPosition(position=53)
	@CsvBindByName(column="image", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "image")
	@IsUri
	private String image;
	
	@ApiModelProperty(value = "Fecha y hora  de inicio de un evento. Ejemplo: 2018-11-03T00:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "fechaInicio")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple({@Rdf(contexto = Context.ESAGENDA, propiedad = "fechaInicio",typeURI=Context.XSD_URI+"date" ),@Rdf(contexto = Context.ESAGENDA, propiedad = "horaInicio",typeURI=Context.XSD_URI+"time")})
	private Date fechaInicio;
	
	@ApiModelProperty(value = "Fecha y hora de fin de un evento. Ejemplo: 2018-12-22T23:59:59")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "fechaFin")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple({@Rdf(contexto = Context.ESAGENDA, propiedad = "fechaFin",typeURI=Context.XSD_URI+"date" ),@Rdf(contexto = Context.ESAGENDA, propiedad = "horaFin",typeURI=Context.XSD_URI+"time")})
	private Date fechaFin;
	
	@ApiModelProperty(value = "Tipo de evento. Ejemplo: Otros Cultura y Ocio,Juventud,Otros,Imagina - Juventud")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="tipoEvento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGENDA, propiedad = "tipoEvento")
	private String tipoEvento;
	
	@ApiModelProperty(value = "Tipo de público. Ejemplo: Jóvenes")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="tipoPublico", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGENDA, propiedad = "tipoPublico")
	private String tipoPublico;
	
	@ApiModelProperty(value = "El rango de edad esperado. Ejemplo: Todas las edades")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="ageRange", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "typicalAgeRange")
	private String ageRange;
	
	@ApiModelProperty(value = "Número máximo de participantes que puede tener un evento, si hay alguna restricción. Ejemplo: 100")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="maximoParticipantes")
	@Rdf(contexto = Context.ESAGENDA, propiedad = "numeroMaximoParticipantes", typeURI=Context.XSD_URI+"int")
	private Integer maximoParticipantes;
	
	@ApiModelProperty(hidden = true)	
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoIdIsolated;
	
	@ApiModelProperty(value = "Lugar de inscripción de un evento. Ejemplo: Imagina - Casa de la Juventud")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="lugarInscripcionTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"Place", propiedad=Context.ESAGENDA_URI+"lugarInscripcion", nodoId="lugarInscripcion")
	private String lugarInscripcionTitle;
	
	@ApiModelProperty(value = "Medio de transporte que se puede utilizar para acceder al evento. Ejemplo: Bus 27")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="medioTransporteTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESTRN_URI+"MedioTransporte", propiedad=Context.ESAGENDA_URI+"medioTransporte", nodoId="medioTransporte")
	private String medioTransporteTitle;
	
	@ApiModelProperty(value = "Servicio municipal encargado del evento. Ejemplo: Juventud")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="servicioMunicipalTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESAGENDA_URI+"servicioMunicipal", nodoId="servicioMunicipal")
	private String servicioMunicipalTitle;
	
	@ApiModelProperty(value = "Propiedad que representa si el evento está en un lugar accesible o no. Ejemplo: true")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="accesible", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGENDA, propiedad = "accesible", typeURI=Context.XSD_URI+"boolean")
	private Boolean accesible;
	
	@ApiModelProperty(value = "Propiedad que representa el tipo de accesibilidad del evento. Ejemplo: Total")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="tipoAccesibilidad", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAGENDA, propiedad = "tipoAccesibilidad")
	private String tipoAccesibilidad;
	
	@ApiModelProperty(value = "Identificador del equipamiento (por ejemplo, edificio de la ciudad) en el que se realiza un evento. Ejemplo: EQ0002")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="equipamientoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "equipamiento")
	@RdfExternalURI(inicioURI="/equipamiento/equipamiento/",finURI="equipamientoId", urifyLevel = 1)
	private String equipamientoId;
	
	@ApiModelProperty(value = "Nombre del equipamiento (por ejemplo, edificio de la ciudad) en el que se realiza un evento. Ejemplo: Teatro Auditorio Ciudad de Alcobendas")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="equipamientoTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoTitle;
	
	@ApiModelProperty(value = "Identificador del municipio del evento. Ejemplo: 28006")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio del evento. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)	
	private String municipioTitle;
	
	@ApiModelProperty(value = "Descripción del evento. Ejemplo: Puede consultar el folleto con todos los espectáculos")
	@CsvBindByPosition(position=19)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	public Agenda() {
	}



	public Agenda(Agenda obj) {
		
		this.ikey = obj.ikey;
		this.id = obj.id;
		this.title = obj.title;
		this.municipioId = obj.municipioId;
		this.municipioTitle = obj.municipioTitle;
		this.description = obj.description;
		this.image = obj.image;
		this.fechaInicio = obj.fechaInicio;
		this.fechaFin = obj.fechaFin;
		this.tipoEvento = obj.tipoEvento;
		this.tipoPublico = obj.tipoPublico;
		this.ageRange = obj.ageRange;
		this.maximoParticipantes = obj.maximoParticipantes;
		this.equipamientoTitle = obj.equipamientoTitle;
		this.equipamientoId = obj.equipamientoId;
		this.lugarInscripcionTitle = obj.lugarInscripcionTitle;
		this.medioTransporteTitle = obj.medioTransporteTitle;
		this.servicioMunicipalTitle = obj.servicioMunicipalTitle;
		this.accesible = obj.accesible;
		this.tipoAccesibilidad = obj.tipoAccesibilidad;
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

	@Column(name = "municipio_id", length = 10)
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

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "image", length = 200)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
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
	@Column(name = "fecha_fin", length = 19)
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "tipo_evento", length = 200)
	public String getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Column(name = "tipo_publico", length = 200)
	public String getTipoPublico() {
		return this.tipoPublico;
	}

	public void setTipoPublico(String tipoPublico) {
		this.tipoPublico = tipoPublico;
	}

	@Column(name = "age_range", length = 200)
	public String getAgeRange() {
		return this.ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	@Column(name = "maximo_participantes")
	public Integer getMaximoParticipantes() {
		return this.maximoParticipantes;
	}

	public void setMaximoParticipantes(Integer maximoParticipantes) {
		this.maximoParticipantes = maximoParticipantes;
	}

	@Column(name = "equipamiento_title", length = 200)
	public String getEquipamientoTitle() {
		return this.equipamientoTitle;
	}

	public void setEquipamientoTitle(String equipamientoTitle) {
		this.equipamientoTitle = equipamientoTitle;
	}
	
	@Column(name = "equipamiento_id", length = 20)
	public String getEquipamientoId() {
		return this.equipamientoId;
	}

	public void setEquipamientoId(String equipamientoId) {
		this.equipamientoId = equipamientoId;
	}

	@Column(name = "lugar_inscripcion_title", length = 200)
	public String getLugarInscripcionTitle() {
		return this.lugarInscripcionTitle;
	}

	public void setLugarInscripcionTitle(String lugarInscripcionTitle) {
		this.lugarInscripcionTitle = lugarInscripcionTitle;
	}

	@Column(name = "medio_transporte_title", length = 200)
	public String getMedioTransporteTitle() {
		return this.medioTransporteTitle;
	}

	public void setMedioTransporteTitle(String medioTransporteTitle) {
		this.medioTransporteTitle = medioTransporteTitle;
	}

	@Column(name = "servicio_municipal_title", length = 200)
	public String getServicioMunicipalTitle() {
		return this.servicioMunicipalTitle;
	}

	public void setServicioMunicipalTitle(String servicioMunicipalTitle) {
		this.servicioMunicipalTitle = servicioMunicipalTitle;
	}

	@Column(name = "accesible")
	public Boolean getAccesible() {
		return this.accesible;
	}

	public void setAccesible(Boolean accesible) {
		this.accesible = accesible;
	}

	@Column(name = "tipo_accesibilidad", length = 200)
	public String getTipoAccesibilidad() {
		return this.tipoAccesibilidad;
	}

	public void setTipoAccesibilidad(String tipoAccesibilidad) {
		this.tipoAccesibilidad = tipoAccesibilidad;
	}
	
	@Transient
	public String getEquipamientoIdIsolated() {
		return equipamientoIdIsolated;
	}

	public void setEquipamientoIdIsolated(String equipamientoIdIsolated) {
		this.equipamientoIdIsolated = equipamientoIdIsolated;
	}



	@Override
	public String toString() {
		return "Agenda [ikey=" + ikey + ", id=" + id + ", title=" + title 
				+ ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle + ", description="
				+ description + ", image=" + image + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", tipoEvento=" + tipoEvento + ", tipoPublico=" + tipoPublico + ", ageRange=" + ageRange
				+ ", maximoParticipantes=" + maximoParticipantes + ", equipamientoTitle=" + equipamientoTitle
				+ ", equipamientoId=" + equipamientoId + ", lugarInscripcionTitle=" + lugarInscripcionTitle + ", medioTransporteTitle="
				+ medioTransporteTitle + ", servicioMunicipalTitle=" + servicioMunicipalTitle + ", accesible="
				+ accesible + ", tipoAccesibilidad=" + tipoAccesibilidad + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);				
		prefixes.put(Context.ESEQUIP, Context.ESEQUIP_URI);
		prefixes.put(Context.ESAGENDA, Context.ESAGENDA_URI);
		prefixes.put(Context.ORG, Context.ORG_URI);
		prefixes.put(Context.ESTRN, Context.ESTRN_URI);
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Agenda) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public Agenda cloneClass(Agenda copia, List<String> attributesToSet) {
		
		Agenda obj = new Agenda(copia,attributesToSet);
		
		return obj;

	}
	
	public Agenda(Agenda copia, List<String> attributesToSet) {
		
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

		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}

		if (attributesToSet.contains("image")) {
			this.image = copia.image;
		}

		if (attributesToSet.contains("fechaInicio")) {
			this.fechaInicio = copia.fechaInicio;
		}

		if (attributesToSet.contains("fechaFin")) {
			this.fechaFin = copia.fechaFin;
		}

		if (attributesToSet.contains("tipoEvento")) {
			this.tipoEvento = copia.tipoEvento;
		}

		if (attributesToSet.contains("tipoPublico")) {
			this.tipoPublico = copia.tipoPublico;
		}

		if (attributesToSet.contains("ageRange")) {
			this.ageRange = copia.ageRange;
		}

		if (attributesToSet.contains("maximoParticipantes")) {
			this.maximoParticipantes = copia.maximoParticipantes;
		}

		if (attributesToSet.contains("equipamientoTitle")) {
			this.equipamientoTitle = copia.equipamientoTitle;
		}
		
		if (attributesToSet.contains("equipamientoId")) {
			this.equipamientoId = copia.equipamientoId;
		}

		if (attributesToSet.contains("lugarInscripcionTitle")) {
			this.lugarInscripcionTitle = copia.lugarInscripcionTitle;
		}

		if (attributesToSet.contains("medioTransporteTitle")) {
			this.medioTransporteTitle = copia.medioTransporteTitle;
		}

		if (attributesToSet.contains("servicioMunicipalTitle")) {
			this.servicioMunicipalTitle = copia.servicioMunicipalTitle;
		}

		if (attributesToSet.contains("accesible")) {
			this.accesible = copia.accesible;
		}

		if (attributesToSet.contains("tipoAccesibilidad")) {
			this.tipoAccesibilidad = copia.tipoAccesibilidad;
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

		if (!Util.validValue(this.getFechaInicio())) {
			result.add("FechaInicio is not valid [FechaInicio:" + this.getFechaInicio() + "]");
		}

		if (!Util.validValue(this.getFechaFin())) {
			result.add("FechaFin is not valid [FechaFin:" + this.getFechaFin() + "]");
		}

		if (!Util.validValue(this.getTipoEvento())) {
			result.add("TipoEvento is not valid [TipoEvento:" + this.getTipoEvento() + "]");
		}

		return result;
	}

}
