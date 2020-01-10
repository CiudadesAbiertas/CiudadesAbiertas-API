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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.ICallejero;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@Table(name = "alojamiento")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESTURISMO, propiedad = "Alojamiento")
@PathId(value="/alojamiento/alojamiento")
public class Alojamiento implements java.io.Serializable, GeoModel, RDFModel, ICallejero {


	
	@JsonIgnore
	private static final long serialVersionUID = 1359450199129002564L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
		  
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="categoria", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESALOJ, propiedad = "categoria")
	private String categoria; 	 
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="accesible", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "accesible", typeURI=Context.XSD_URI+"boolean")
	private Boolean accesible;	
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="tipoAccesibilidad", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "tipoAccesibilidad")
	private String tipoAccesibilidad;
			
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)	
	private String municipioId;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="barrioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId", urifyLevel = 1)		
	private String barrioId;
	
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;

	@CsvBindByPosition(position=11)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
		
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal x;	
	
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
		
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="email", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	private String email;
	
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="telephone", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;
	
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="faxNumber", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "faxNumber")
	private String faxNumber;
	
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="numHabitaciones", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESALOJ, propiedad = "numHabitaciones", typeURI=Context.XSD_URI+"int")
	private Integer numHabitaciones;
	
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="numCamas", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESALOJ, propiedad = "numCamas", typeURI=Context.XSD_URI+"int")
	private Integer numCamas;
  
		
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="modified", format=Constants.DATE_FORMAT)		
	@Rdf(contexto = Context.ESALOJ, propiedad = "modified",typeURI=Context.XSD_URI+"date" )
	private Date modified;	
	
	
	@CsvBindByPosition(position=24)
	@CsvBindByName(column="image", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "image")
	@IsUri
	private String image;
	
	@CsvBindByPosition(position=25)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	private Double distance;	
	

	public Alojamiento() {
	}

	public Alojamiento(String ikey) {
		this.ikey = ikey;
	}

	public Alojamiento(String ikey, String id, String categoria, String title, String description, Boolean accesible, String tipoAccesibilidad, String municipioId, String municipioTitle, String streetAddress, String postalCode, String barrio, String distrito, BigDecimal x, BigDecimal y, String email, String telephone,String faxNumber, String url, Integer numHabitaciones,Integer numCamas,  Date modified,  String image, String portalId, String barrioId,String distritoId) {
       this.ikey = ikey;
       this.id = id;
       this.categoria = categoria;
       this.title = title;
       this.description = description;
       this.accesible = accesible;
       this.tipoAccesibilidad = tipoAccesibilidad;
       this.municipioId = municipioId;
       this.municipioTitle = municipioTitle;
       this.streetAddress = streetAddress;
       this.postalCode = postalCode;
       this.barrioId = barrioId;
       this.distritoId = distritoId;
       this.x = x;
       this.y = y;
       this.email = email;
       this.telephone = telephone;
       this.faxNumber = faxNumber;
       this.url = url;
       this.numHabitaciones = numHabitaciones;
       this.numCamas = numCamas;
       this.modified = modified; 
       this.image = image;
       this.portalId = portalId;
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

	@Column(name = "categoria", length = 400)
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	@Column(name = "barrio_id", length = 50)
	public String getBarrioId() {
		return this.barrioId;
	}

	public void setBarrioId(String barrioId) {
		this.barrioId = barrioId;
	}

	@Column(name = "distrito_id", length = 50)
	public String getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89", precision = 13, scale = 5)
	public BigDecimal getX()
	{
		return x;
	}
	
	public void setX(BigDecimal x)
	{
		this.x = x;
	}
	
	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89", precision = 13, scale = 5)
	public BigDecimal getY()
	{
		return y;
	}
	
	public void setY(BigDecimal y)
	{
		this.y = y;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telephone", length = 200)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	@Column(name = "faxNumber", length = 200)
	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Column(name = "numHabitaciones", precision = 5)
	public Integer getNumHabitaciones() {
		return numHabitaciones;
	}

	public void setNumHabitaciones(Integer numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}

	@Column(name = "numCamas", precision = 5)
	public Integer getNumCamas() {
		return numCamas;
	}

	public void setNumCamas(Integer numCamas) {
		this.numCamas = numCamas;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 19)
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	
	@Column(name = "image", length = 200)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	@Transient
	public BigDecimal getLatitud() {
		return this.latitud;
	}
	
	@Transient
	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud() {
		return this.longitud;
	}

	@Transient
	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}
	
	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}
	
	@Column(name = "portal_id", length = 20)
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	
	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated=portalIdIsolated;
		
	}

	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
	}

	@Override
	public String toString() {
		return "Alojamiento [ikey=" + ikey + ", id=" + id + ", title=" + title + ", categoria=" + categoria
				+ ", accesible=" + accesible + ", tipoAccesibilidad=" + tipoAccesibilidad + ", municipioId="
				+ municipioId + ", municipioTitle=" + municipioTitle + ", streetAddress=" + streetAddress
				+ ", postalCode=" + postalCode + ", portalId=" + portalId + ", barrioId=" + barrioId
				+ ", distritoId=" + distritoId + ", x=" + x + ", y=" + y + ", latitud=" + latitud + ", longitud="
				+ longitud + ", email=" + email + ", telephone=" + telephone + ", faxNumber=" + faxNumber + ", url="
				+ url + ", numHabitaciones=" + numHabitaciones + ", numCamas=" + numCamas + ", modified=" + modified
				+ ", image=" + image + ", description=" + description + ", distance=" + distance + "]";
	}

	public Alojamiento(Alojamiento obj) {
	       this.ikey = obj.ikey;
	       this.id = obj.id;
	       this.categoria = obj.categoria;
	       this.title = obj.title;
	       this.description = obj.description;
	       this.accesible = obj.accesible;
	       this.tipoAccesibilidad = obj.tipoAccesibilidad;
	       this.municipioId = obj.municipioId;
	       this.municipioTitle = obj.municipioTitle;
	       this.streetAddress = obj.streetAddress;
	       this.postalCode = obj.postalCode;
	       this.barrioId = obj.barrioId;
	       this.distritoId = obj.distritoId;
	       this.x = obj.x;
	       this.y = obj.y;
	       this.email = obj.email;
	       this.telephone = obj.telephone;
	       this.url = obj.url;
	       this.faxNumber = obj.faxNumber;	       
	       this.numHabitaciones = obj.numHabitaciones;
	       this.numCamas = obj.numCamas;
	       this.modified = obj.modified; 
	       this.image = obj.image;
	       //Nuevo
	       this.portalId = obj.portalId;
	    }
	
	

	public Alojamiento(Alojamiento copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("categoria")) {
			this.categoria = copia.categoria;		
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;		
		}
		if (attributesToSet.contains("accesible")) {
			this.accesible = copia.accesible;		
		}
		if (attributesToSet.contains("tipoAccesibilidad")) {
			this.tipoAccesibilidad = copia.tipoAccesibilidad;		
		}
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;		
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;		
		}
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;		
		}
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;		
		}
		if (attributesToSet.contains("barrioId")) {
			this.barrioId = copia.barrioId;		
		}
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;		
		}	
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;		
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;		
		}
		if (attributesToSet.contains("email")) {
			this.email = copia.email;		
		}
		if (attributesToSet.contains("telephone")) {
			this.telephone = copia.telephone;		
		}
		if (attributesToSet.contains("url")) {
			this.url = copia.url;		
		}		
		if (attributesToSet.contains("faxNumber")) {
			 this.faxNumber = copia.faxNumber;		
		}
		if (attributesToSet.contains("numHabitaciones")) {
			 this.numHabitaciones = copia.numHabitaciones;		
		}
		if (attributesToSet.contains("numCamas")) {
			 this.numCamas = copia.numCamas;		
		}
		if (attributesToSet.contains("modified")) {
			 this.modified = copia.modified;		
		}

		if (attributesToSet.contains("image")) {
			this.image = copia.image;		
		}
		
		//Nuevo
		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;		
		}
		
	}
	
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.ESALOJ, Context.ESALOJ_URI);
		prefixes.put(Context.ESTURISMO, Context.ESTURISMO_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		
		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Alojamiento) copia,listado) ;
	}

	
	
	public Alojamiento cloneClass (Alojamiento copia, List<String> attributesToSet)
	{
		Alojamiento obj = new Alojamiento(copia,attributesToSet);
		
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

		if (!Util.validValue(this.getCategoria())) {
			result.add("Categoria is not valid [FechaInicio:" + this.getCategoria() + "]");
		}

		if (!Util.validValue(this.getAccesible())) {
			result.add("Accesible is not valid [FechaFin:" + this.getAccesible() + "]");
		}

		return result;
	}

	
	
	
}

