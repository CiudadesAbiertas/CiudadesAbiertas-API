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

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
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
@Table(name = "tramite")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSRVC, propiedad = "Procedimiento")
@PathId(value="/tramite/tramite")
public class Tramite implements java.io.Serializable, RDFModel {


	@JsonIgnore
	private static final long serialVersionUID = 1359450167129002564L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del trámite. Ejemplo: TR0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del trámite. Ejemplo: Apoyo a la Formación en las Asociaciones juveniles")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Web del trámite. Ejemplo: https://sede.mjusticia.gob.es/cs/Satellite/Sede/es/tramites/certificado-nacimiento")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
		  
	@ApiModelProperty(value = "Esta propiedad vincula un servicio público con sus formularios de solicitud. Ejemplo: https://www.zaragoza.es/sedeservicio/tramite/impreso-instancia/36")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="impreso", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "impreso")	
	private String impreso;
	
	@ApiModelProperty(value = "La propiedad detalleTramitacionEnLinea vincula un servicio telefónico a las Reglas bajo las cuales opera. Ejemplo: <p>Los datos se dan por tel&eacute;fono bajo la responsabilidad del solicitante.</p>")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="detalleTramitacionTelefono", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "detalleTramitacionTelefono")		
	private String detalleTramitacionTelefono;
	
	@ApiModelProperty(value = "La propiedad detalleTramitacionPresencial vincula un servicio basado en la presencia a la (s) Regla (s) bajo las cuales opera. Ejemplo: <p>Se exigir&aacute; la presentaci&oacute;n de los documentos acreditativos de los datos solicitados.</p>")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="detalleTramitacionPresencial", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "detalleTramitacionPresencial")	
	private String detalleTramitacionPresencial;
	
	@ApiModelProperty(value = "La propiedad detalleTramitacionEnLinea vincula un servicio en línea con las Reglas bajo las cuales opera. Ejemplo: <strong>Por el interesado:</strong> documento de identidad (DNI / NIE / tarjeta de residencia / pasaporte).")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="detalleTramitacionEnLinea", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "detalleTramitacionEnLinea")
	private String detalleTramitacionEnLinea;
	
	@ApiModelProperty(value = "La propiedad detalleTramitacionEnLinea vincula un servicio en línea con las Reglas bajo las cuales opera. Ejemplo: Escrito o instancia modelo general al efecto en el que figuren los datos identificativos de la entidad beneficiaria")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="detalleTramitacionCorreoPostal", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "detalleTramitacionCorreoPostal")
	private String detalleTramitacionCorreoPostal;
	
	@ApiModelProperty(value = "Indica si permite la tramitación en linea. Ejemplo: false")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="permiteTramitacionEnLinea", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "permiteTramitacionEnLinea", typeURI=Context.XSD_URI+"boolean")	
	private Boolean permiteTramitacionEnLinea;
	
	@ApiModelProperty(value = "Indica si permite la tramitación presencial. Ejemplo: false")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="permiteTramitacionPresencial", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "permiteTramitacionPresencial", typeURI=Context.XSD_URI+"boolean")	
	private Boolean permiteTramitacionPresencial;
	
	@ApiModelProperty(value = "Indica si permite la tramitación por teléfono. Ejemplo: false")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="permiteTramitacionTelefono", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "permiteTramitacionTelefono", typeURI=Context.XSD_URI+"boolean")	
	private Boolean permiteTramitacionTelefono;
	
	@ApiModelProperty(value = "Indica si permite la tramitación por correo postal. Ejemplo: false")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="permiteTramitacionTelefono", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "permiteTramitationCorreoPostal", typeURI=Context.XSD_URI+"boolean")
	private Boolean permiteTramitationCorreoPostal;
	
	@ApiModelProperty(value = "Representa una regla que debe ser seguida por un servicio público. Ejemplo: https://www.zaragoza.es/sede/servicio/normativa/145")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="normativa", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "normativa")
	private String normativa;
	
	@ApiModelProperty(value = "Órgano de un trámite. Ejemplo: Organo superior")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="organo", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "organo")
	private String organo;
	
	@ApiModelProperty(value = "Información sobre el pago (fecha, monto, lugar) para un Servicio Público. Ejemplo: El alta en el servicio lleva consigo la exigencia de las tasas por &quot;establecimiento de puntos de suministro&quot;")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="pago", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "pago")
	private String pago;
	
	@ApiModelProperty(value = "Esta propiedad vincula un servicio público con sus requisitos. Ejemplo: Los servicios de consulta y de reproducci&oacute;n de fondos est&aacute;n sujetos a la normativa vigente")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="requisitos", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "requisitos")
	private String requisitos;
	
	@ApiModelProperty(value = "La propiedad que determina que dos individuos dados son iguales. Ejemplo: http://www.zaragoza.es/api/recurso/sector-publico/procedimiento-tramite/5503")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="sameAs", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OWL, propiedad = "sameAs")
	private String sameAs;
	
	@ApiModelProperty(value = "Materia de un trámite. Ejemplo: Salud")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="materia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "materia")
	private String materia;
	
	@ApiModelProperty(value = "Esta propiedad representa la fecha límite para comenzar un Servicio Público. Ejemplo: 2019-01-08T23:59:59")
	@CsvBindByPosition(position=19)
	@CsvBindByName (column = "fechaPlazoInicio")	
	@CsvDate(Constants.DATE_FORMAT)		
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaPlazoInicio",typeURI=Context.XSD_URI+"date" )
	private Date fechaPlazoInicio;
	
	@ApiModelProperty(value = "Esta propiedad representa la fecha límite para terminar un servicio público.. Ejemplo: 2019-12-08T23:59:59")
	@CsvBindByPosition(position=20)
	@CsvBindByName (column = "fechaPlazoFin")	
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaPlazoFin",typeURI=Context.XSD_URI+"date" )
	private Date fechaPlazoFin;
	
	@ApiModelProperty(value = "Fecha en la que se deben resolver las solicitudes (en formato fecha/duración). Ejemplo: 2019-06-11T09:00:00")
	@CsvBindByPosition(position=21)
	@CsvBindByName (column = "fechaRespuesta")	
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaRespuesta",typeURI=Context.XSD_URI+"date" )
	private Date fechaRespuesta;

	@ApiModelProperty(value = "Fecha en la que se deben resolver las solicitudes (en formato textual). Ejemplo: Once de mayo de dos mil diecinueve a las nueve en punto")
	@CsvBindByPosition(position=22)
	@CsvBindByName (column = "fechaRespuestaTexto")
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaRespuestaTexto" )
	private String fechaRespuestaTexto;
	
	@ApiModelProperty(value = "Fecha en la que se deben presentar las solicitudes (en formato fecha). Ejemplo: 2019-05-04T11:05:00")
	@CsvBindByPosition(position=23)
	@CsvBindByName (column = "fechaPresentacion")	
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaPresentacion",typeURI=Context.XSD_URI+"date" )	
	private Date fechaPresentacion;

	@ApiModelProperty(value = "Fecha en la que se deben presentar las solicitudes (en formato textual). Ejemplo: Cuatro de abril de dos mil diecinueve a las once y cinco")
	@CsvBindByPosition(position=24)
	@CsvBindByName (column = "fechaPresentacionTexto")
	@Rdf(contexto = Context.ESSRVC, propiedad = "fechaPresentacionTexto" )
	private String fechaPresentacionTexto;
	
	@ApiModelProperty(value = "Situación que ocurre si no hay respuesta de la entidad que está a cargo de un Servicio Público. Ejemplo: NEGATIVO")
	@CsvBindByPosition(position=25)
	@CsvBindByName (column = "efectoSilencioAdministrativo")
	@Rdf(contexto = Context.ESSRVC, propiedad = "efectoSilencioAdministrativo" )
	private String efectoSilencioAdministrativo;
	
	@ApiModelProperty(value = "Descripción de un trámite. Ejemplo: <p>Solicitud de permiso para realizar acampadas en suelo municipal.</p>")
	@CsvBindByPosition(position=26)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	public Tramite() {
	}

	
	public Tramite(Tramite copia) {
	    this.ikey = copia.ikey;
	    this.id = copia.id;	       
	    this.title = copia.title;
	    this.description = copia.description;	       
		this.url = copia.url;
		this.impreso = copia.impreso;
		this.detalleTramitacionTelefono = copia.detalleTramitacionTelefono;
		this.detalleTramitacionPresencial = copia.detalleTramitacionPresencial;
		this.detalleTramitacionEnLinea = copia.detalleTramitacionEnLinea;
		this.detalleTramitacionCorreoPostal = copia.detalleTramitacionCorreoPostal;
		this.permiteTramitacionEnLinea = copia.permiteTramitacionEnLinea;
		this.permiteTramitacionPresencial = copia.permiteTramitacionPresencial;
		this.permiteTramitacionTelefono = copia.permiteTramitacionTelefono;
		this.permiteTramitationCorreoPostal = copia.permiteTramitationCorreoPostal;
		this.normativa = copia.normativa;
		this.organo = copia.organo;
		this.pago = copia.pago;
		this.requisitos = copia.requisitos;
		this.sameAs = copia.sameAs;
		this.materia = copia.materia;
		this.fechaPlazoInicio = copia.fechaPlazoInicio;
		this.fechaPlazoFin = copia.fechaPlazoFin;
		this.fechaPresentacion=copia.fechaPresentacion;
		this.fechaRespuesta=copia.fechaRespuesta;
		this.fechaPresentacionTexto=copia.fechaPresentacionTexto;
		this.fechaRespuestaTexto=copia.fechaRespuestaTexto;
		this.efectoSilencioAdministrativo=copia.efectoSilencioAdministrativo;
    }
	

	

	public Tramite(Tramite copia, List<String> attributesToSet)
	{
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
		if (attributesToSet.contains("impreso")) {
			this.impreso = copia.impreso;		
		}
		if (attributesToSet.contains("detalleTramitacionTelefono")) {
			this.detalleTramitacionTelefono = copia.detalleTramitacionTelefono;		
		}
		if (attributesToSet.contains("detalleTramitacionPresencial")) {
			this.detalleTramitacionPresencial = copia.detalleTramitacionPresencial;		
		}
		if (attributesToSet.contains("detalleTramitacionEnLinea")) {
			this.detalleTramitacionEnLinea = copia.detalleTramitacionEnLinea;		
		}
		if (attributesToSet.contains("detalleTramitacionCorreoPostal")) {
			this.detalleTramitacionCorreoPostal = copia.detalleTramitacionCorreoPostal;		
		}
		if (attributesToSet.contains("permiteTramitacionEnLinea")) {
			this.permiteTramitacionEnLinea = copia.permiteTramitacionEnLinea;		
		}
		if (attributesToSet.contains("permiteTramitacionTelefono")) {
			this.permiteTramitacionTelefono = copia.permiteTramitacionTelefono;		
		}
		if (attributesToSet.contains("permiteTramitacionPresencial")) {
			this.permiteTramitacionPresencial = copia.permiteTramitacionPresencial;		
		}
		if (attributesToSet.contains("permiteTramitationCorreoPostal")) {
			this.permiteTramitationCorreoPostal = copia.permiteTramitationCorreoPostal;		
		}
		if (attributesToSet.contains("normativa")) {
			this.normativa = copia.normativa;		
		}
		if (attributesToSet.contains("organo")) {
			this.organo = copia.organo;		
		}
		if (attributesToSet.contains("pago")) {
			this.pago = copia.pago;		
		}
		if (attributesToSet.contains("requisitos")) {
			this.requisitos = copia.requisitos;		
		}
		if (attributesToSet.contains("sameAs")) {
			this.sameAs = copia.sameAs;		
		}
		if (attributesToSet.contains("materia")) {
			this.materia = copia.materia;		
		}
		if (attributesToSet.contains("fechaPlazoInicio")) {
			this.fechaPlazoInicio = copia.fechaPlazoInicio;		
		}
		if (attributesToSet.contains("fechaPlazoFin")) {
			this.fechaPlazoFin = copia.fechaPlazoFin;		
		}
		if (attributesToSet.contains("fechaPresentacion")) {
			this.fechaPresentacion = copia.fechaPresentacion;		
		}
		if (attributesToSet.contains("fechaRespuesta")) {
			this.fechaRespuesta = copia.fechaRespuesta;		
		}
		if (attributesToSet.contains("fechaPresentacionTexto")) {
			this.fechaPresentacionTexto = copia.fechaPresentacionTexto;		
		}
		if (attributesToSet.contains("fechaRespuestaTexto")) {
			this.fechaRespuestaTexto = copia.fechaRespuestaTexto;		
		}
		if (attributesToSet.contains("efectoSilencioAdministrativo")) {
			this.efectoSilencioAdministrativo = copia.efectoSilencioAdministrativo;		
		}
	}
	
	

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey()
	{
		return this.ikey;
	}

	public void setIkey(String ikey)
	{
		this.ikey = ikey;
	}

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "description", length = 4000)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "url", length = 400)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Column(name = "impreso", length = 400)
	public String getImpreso()
	{
		return this.impreso;
	}

	public void setImpreso(String impreso)
	{
		this.impreso = impreso;
	}

	@Column(name = "detalle_telefono", length = 4000)
	public String getDetalleTramitacionTelefono()
	{
		return this.detalleTramitacionTelefono;
	}

	public void setDetalleTramitacionTelefono(String detalleTramitacionTelefono)
	{
		this.detalleTramitacionTelefono = detalleTramitacionTelefono;
	}

	@Column(name = "detalle_presencial", length = 4000)
	public String getDetalleTramitacionPresencial()
	{
		return this.detalleTramitacionPresencial;
	}

	public void setDetalleTramitacionPresencial(String detalleTramitacionPresencial)
	{
		this.detalleTramitacionPresencial = detalleTramitacionPresencial;
	}

	@Column(name = "detalle_en_linea", length = 4000)
	public String getDetalleTramitacionEnLinea()
	{
		return this.detalleTramitacionEnLinea;
	}

	public void setDetalleTramitacionEnLinea(String detalleTramitacionEnLinea)
	{
		this.detalleTramitacionEnLinea = detalleTramitacionEnLinea;
	}

	@Column(name = "detalle_correo_postal", length = 4000)
	public String getDetalleTramitacionCorreoPostal()
	{
		return this.detalleTramitacionCorreoPostal;
	}

	public void setDetalleTramitacionCorreoPostal(String detalleTramitacionCorreoPostal)
	{
		this.detalleTramitacionCorreoPostal = detalleTramitacionCorreoPostal;
	}

	@Column(name = "permite_en_linea")
	public Boolean getPermiteTramitacionEnLinea()
	{
		return this.permiteTramitacionEnLinea;
	}

	public void setPermiteTramitacionEnLinea(Boolean permiteTramitacionEnLinea)
	{
		this.permiteTramitacionEnLinea = permiteTramitacionEnLinea;
	}

	@Column(name = "permite_presencial")
	public Boolean getPermiteTramitacionPresencial()
	{
		return this.permiteTramitacionPresencial;
	}

	public void setPermiteTramitacionPresencial(Boolean permiteTramitacionPresencial)
	{
		this.permiteTramitacionPresencial = permiteTramitacionPresencial;
	}

	@Column(name = "permite_telefono")
	public Boolean getPermiteTramitacionTelefono()
	{
		return this.permiteTramitacionTelefono;
	}

	public void setPermiteTramitacionTelefono(Boolean permiteTramitacionTelefono)
	{
		this.permiteTramitacionTelefono = permiteTramitacionTelefono;
	}

	@Column(name = "permite_correo_postal")
	public Boolean getPermiteTramitationCorreoPostal()
	{
		return this.permiteTramitationCorreoPostal;
	}

	public void setPermiteTramitationCorreoPostal(Boolean permiteTramitationCorreoPostal)
	{
		this.permiteTramitationCorreoPostal = permiteTramitationCorreoPostal;
	}

	@Column(name = "normativa", length = 400)
	public String getNormativa()
	{
		return this.normativa;
	}

	public void setNormativa(String normativa)
	{
		this.normativa = normativa;
	}

	@Column(name = "organo", length = 400)
	public String getOrgano()
	{
		return this.organo;
	}

	public void setOrgano(String organo)
	{
		this.organo = organo;
	}

	@Column(name = "pago", length = 4000)
	public String getPago()
	{
		return this.pago;
	}

	public void setPago(String pago)
	{
		this.pago = pago;
	}

	@Column(name = "requisitos", length = 4000)
	public String getRequisitos()
	{
		return this.requisitos;
	}

	public void setRequisitos(String requisitos)
	{
		this.requisitos = requisitos;
	}

	@Column(name = "same_as", length = 400)
	public String getSameAs()
	{
		return this.sameAs;
	}

	public void setSameAs(String sameAs)
	{
		this.sameAs = sameAs;
	}

	@Column(name = "materia", length = 400)
	public String getMateria()
	{
		return this.materia;
	}

	public void setMateria(String materia)
	{
		this.materia = materia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_plazo_inicio", length = 19)
	public Date getFechaPlazoInicio()
	{
		return this.fechaPlazoInicio;
	}

	public void setFechaPlazoInicio(Date fechaPlazoInicio)
	{
		this.fechaPlazoInicio = fechaPlazoInicio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_plazo_fin", length = 19)
	public Date getFechaPlazoFin()
	{
		return this.fechaPlazoFin;
	}

	public void setFechaPlazoFin(Date fechaPlazoFin)
	{
		this.fechaPlazoFin = fechaPlazoFin;
	}

	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_respuesta", length = 19)
	public Date getFechaRespuesta()
	{
		return fechaRespuesta;
	}


	public void setFechaRespuesta(Date fechaRespuesta)
	{
		this.fechaRespuesta = fechaRespuesta;
	}


	@Column(name = "fecha_respuesta_texto", length = 100)
	public String getFechaRespuestaTexto()
	{
		return fechaRespuestaTexto;
	}


	public void setFechaRespuestaTexto(String fechaRespuestaTexto)
	{
		this.fechaRespuestaTexto = fechaRespuestaTexto;
	}


	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_presentacion", length = 19)
	public Date getFechaPresentacion()
	{
		return fechaPresentacion;
	}


	public void setFechaPresentacion(Date fechaPresentacion)
	{
		this.fechaPresentacion = fechaPresentacion;
	}

	@Column(name = "fecha_presentacion_texto", length = 100)
	public String getFechaPresentacionTexto()
	{
		return fechaPresentacionTexto;
	}


	public void setFechaPresentacionTexto(String fechaPresentacionTexto)
	{
		this.fechaPresentacionTexto = fechaPresentacionTexto;
	}


	@Column(name = "efecto_silencio_administrativo", length = 100)
	public String getEfectoSilencioAdministrativo()
	{
		return efectoSilencioAdministrativo;
	}


	public void setEfectoSilencioAdministrativo(String efectoSilencioAdministrativo)
	{
		this.efectoSilencioAdministrativo = efectoSilencioAdministrativo;
	}


	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.ESSRVC, Context.ESSRVC_URI);
		prefixes.put(Context.OWL, Context.OWL_URI);
		
		
		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Tramite) copia,listado) ;
	}

	
	
	public Tramite cloneClass (Tramite copia, List<String> attributesToSet)
	{
		Tramite obj = new Tramite(copia,attributesToSet);
		
		return obj;
	}
	
	
	
	public List<String> validate() {
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

	
	
	
}

