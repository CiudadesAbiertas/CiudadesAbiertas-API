
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
import javax.persistence.UniqueConstraint;

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
@Table(name = "empleo_oferta_publica", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "OfertaEmpleoPublico")
@PathId(value = "/empleo/oferta-empleo-publica")
public class OfertaEmpleoPublico implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -6981267575138184787L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del Convenio. Ejemplo: OFE001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del Boletín. Ejemplo: Oferta Informatica")
	@CsvBindByPosition(position=2)	
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Descripción de la oferta. Ejemplo: Oferta Informatica para el Ayuntamiento")
	@CsvBindByPosition(position=13)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	@ApiModelProperty(value = "Fecha de la modificación. Ejemplo: 2016-04-25T00:00:00")
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="datePublished")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "dateModified", typeURI=Context.XSD_URI+"date")
	private Date dateModified;
	
	@ApiModelProperty(value = "Fecha de la publicación. Ejemplo: 2016-04-25T00:00:00")
	@CsvBindByPosition(position=4)	
	@CsvBindByName(column="datePublished")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "datePublished", typeURI=Context.XSD_URI+"date")
	private Date datePublished;
	
	@ApiModelProperty(value = "Fecha de la publicación. Ejemplo: 2016-04-25T00:00:00")
	@CsvBindByPosition(position=5)	
	@CsvBindByName(column="datePublished")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESEMPLEO, propiedad = "fechaAprobacion", typeURI=Context.XSD_URI+"date")
	private Date fechaAprobacion;
	
	@ApiModelProperty(value = "Identificador del municipio de la oferta. Ejemplo: 28006")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la oferta. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=7)
    @CsvBindByName (column = "municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador de la provincia. Ejemplo: 28")
	@CsvBindByPosition(position=8)
	@CsvBindByName (column = "provinciaId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "provincia")
	@RdfExternalURI(inicioURI="/territorio/provincia/",finURI="provinciaId", urifyLevel = 1)
	private String provinciaId;
	
	@ApiModelProperty(value = "Nombre de la provincia de la oferta. Ejemplo: Madrid")
	@CsvBindByPosition(position=9)
    @CsvBindByName (column = "provinciaTitle", format=Constants.STRING_FORMAT)
	private String provinciaTitle;
	
	@ApiModelProperty(value = "Identificador de la autonomia. Ejemplo: Comunidad-Madrid")
	@CsvBindByPosition(position=10)
	@CsvBindByName (column = "autonomiaId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "autonomia")
	@RdfExternalURI(inicioURI="/territorio/autonomia/",finURI="autonomiaId", urifyLevel = 1)
	private String autonomiaId;
	
	@ApiModelProperty(value = "Nombre de la autonomia de la oferta. Ejemplo: Comunidad de Madrid")
	@CsvBindByPosition(position=11)
    @CsvBindByName (column = "autonomiaTitle", format=Constants.STRING_FORMAT)
	private String autonomiaTitle;
	
	@ApiModelProperty(value = "Boletin de publicación (id) de la oerta. Ejemplo: BOE0001")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="boletinOficialId",format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEMPLEO, propiedad = "sePublicaEn")
	@RdfExternalURI(inicioURI="/empleo/boletin-oficial/",finURI="boletinOficialId", urifyLevel = 1)
	private String boletinOficialId;

	
	public OfertaEmpleoPublico() {
	}
	
	public OfertaEmpleoPublico(String ikey, String id, String title, String description, Date dateModified,
			Date datePublished, Date fechaAprobacion, String municipioId, String municipioTitle, String provinciaId,
			String provinciaTitle, String autonomiaId, String autonomiaTitle, String boletinOficialId) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.title = title;
		this.description = description;
		this.dateModified = dateModified;
		this.datePublished = datePublished;
		this.fechaAprobacion = fechaAprobacion;
		this.municipioId = municipioId;
		this.municipioTitle = municipioTitle;
		this.provinciaId = provinciaId;
		this.provinciaTitle = provinciaTitle;
		this.autonomiaId = autonomiaId;
		this.autonomiaTitle = autonomiaTitle;
		this.boletinOficialId = boletinOficialId;
	}



	public OfertaEmpleoPublico(OfertaEmpleoPublico obj) {
		this.ikey = obj.ikey;
		this.id = obj.id;
		this.title = obj.title;
		this.description = obj.description;
		this.dateModified = obj.dateModified;
		this.datePublished = obj.datePublished;
		this.fechaAprobacion = obj.fechaAprobacion;
		this.municipioId = obj.municipioId;
		this.municipioTitle = obj.municipioTitle;
		this.provinciaId = obj.provinciaId;
		this.provinciaTitle = obj.provinciaTitle;
		this.autonomiaId = obj.autonomiaId;
		this.autonomiaTitle = obj.autonomiaTitle;
		this.boletinOficialId = obj.boletinOficialId;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@Column(name = "boletin_oficial_id", unique = true, length = 50)
	public String getBoletinOficialId() {
		return this.boletinOficialId;
	}

	public void setBoletinOficialId(String boletinOficialId) {
		this.boletinOficialId = boletinOficialId;
	}

	@Column(name = "id", unique = true, length = 50)
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

	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", length = 26)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_published", length = 26)
	public Date getDatePublished() {
		return this.datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_aprobacion", length = 26)
	public Date getFechaAprobacion() {
		return this.fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
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

	@Column(name = "provincia_id", length = 50)
	public String getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(String provinciaId) {
		this.provinciaId = provinciaId;
	}

	@Column(name = "provincia_title", length = 200)
	public String getProvinciaTitle() {
		return this.provinciaTitle;
	}

	public void setProvinciaTitle(String provinciaTitle) {
		this.provinciaTitle = provinciaTitle;
	}

	@Column(name = "autonomia_id", length = 50)
	public String getAutonomiaId() {
		return this.autonomiaId;
	}

	public void setAutonomiaId(String autonomiaId) {
		this.autonomiaId = autonomiaId;
	}

	@Column(name = "autonomia_title", length = 200)
	public String getAutonomiaTitle() {
		return this.autonomiaTitle;
	}

	public void setAutonomiaTitle(String autonomiaTitle) {
		this.autonomiaTitle = autonomiaTitle;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (OfertaEmpleoPublico) copia,listado) ;
	}
	
	
	

	// Constructor copia con lista de attributos a settear
	public OfertaEmpleoPublico cloneClass(OfertaEmpleoPublico copia, List<String> attributesToSet) {
		
		OfertaEmpleoPublico obj = new OfertaEmpleoPublico(copia,attributesToSet);
		
		return obj;

	}
	
	public OfertaEmpleoPublico(OfertaEmpleoPublico copia,List<String> attributesToSet) {
		
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
		
		if (attributesToSet.contains("dateModified")) {
			this.dateModified = copia.dateModified;
		}
		
		if (attributesToSet.contains("datePublished")) {
			this.datePublished = copia.datePublished;
		}
		
		if (attributesToSet.contains("fechaAprobacion")) {
			this.fechaAprobacion = copia.fechaAprobacion;
		}
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}
		if (attributesToSet.contains("provinciaId")) {
			this.provinciaId = copia.provinciaId;
		}
		if (attributesToSet.contains("provinciaTitle")) {
			this.provinciaTitle = copia.provinciaTitle;
		}
		if (attributesToSet.contains("autonomiaId")) {
			this.autonomiaId = copia.autonomiaId;
		}
		if (attributesToSet.contains("autonomiaTitle")) {
			this.autonomiaTitle = copia.autonomiaTitle;
		}
		if (attributesToSet.contains("boletinOficialId")) {
			this.boletinOficialId = copia.boletinOficialId;
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
			
		if (!Util.validValue(this.getDescription())) {
			result.add("Description is not valid [Title:" + this.getDescription() + "]");
		}
		
		if (!Util.validValue(this.getDatePublished())) {
			result.add("DatePublished is not valid [Title:" + this.getDatePublished() + "]");
		}
		
		if (!Util.validValue(this.getFechaAprobacion())) {
			result.add("FechaAprobacion is not valid [Title:" + this.getFechaAprobacion() + "]");
		}

		return result;
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESEMPLEO, Context.ESEMPLEO_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);							
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		
		
		return prefixes;
	}



	@Override
	public String toString() {
		return "OfertaPublica [id=" + id + ", title=" + title + ", description=" + description + ", dateModified="
				+ dateModified + ", datePublished=" + datePublished + ", fechaAprobacion=" + fechaAprobacion
				+ ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle + ", provinciaId=" + provinciaId
				+ ", provinciaTitle=" + provinciaTitle + ", autonomiaId=" + autonomiaId + ", autonomiaTitle="
				+ autonomiaTitle + ", boletinOficialId=" + boletinOficialId + "]";
	}




}
