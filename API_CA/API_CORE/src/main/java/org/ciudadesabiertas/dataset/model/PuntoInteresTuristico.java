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

import org.ciudadesAbiertas.rdfGeneratorZ.MultiURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.EnumCategoryPuntoInteres;
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
@Table(name = "punto_interes_turistico")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESTURISMO, propiedad = "LugarInteresTuristico")
@PathId(value="/puntoInteresTuristico/puntoInteresTuristico")
public class PuntoInteresTuristico implements java.io.Serializable, GeoModel, RDFModel,MultiURI {

	@JsonIgnore
	private static final long serialVersionUID = -4486812743090306716L;
	
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
	@CsvBindByName(column="category", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "category")
	private String category;
	
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
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")	
	private String municipioId;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")
	private String municipioTitle;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")		
	private String barrio;
	
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	private String distrito;
		
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal x;	
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="email", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	private String email;
	
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="telephone", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;
	
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="siglo", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "siglo")
	private String siglo;
	
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="estiloArtistico", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "estiloArtistico")
	private String estiloArtistico;
	
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="costeMantenimiento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "costeMantenimiento", typeURI=Context.XSD_URI+"double")
	private BigDecimal costeMantenimiento;
	
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="ingresosObtenidos", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "ingresosObtenidos", typeURI=Context.XSD_URI+"double")
	private BigDecimal ingresosObtenidos;
	
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="afluenciaPublico", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "afluenciaPublico")
	private String afluenciaPublico;
	
	@CsvBindByPosition(position=24)
	@CsvBindByName(column="fechaDeclaracionBien", format=Constants.DATE_FORMAT)		
	@Rdf(contexto = Context.ESTURISMO, propiedad = "fechaDeclaracionBien",typeURI=Context.XSD_URI+"date" )
	private Date fechaDeclaracionBien;
	
	@CsvBindByPosition(position=25)
	@CsvBindByName(column="modoAcceso", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "modoAcceso")
	private String modoAcceso;
	
	@CsvBindByPosition(position=26)
	@CsvBindByName(column="medioTransporte", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "medioTransporte")
	private String medioTransporte;
	
	@CsvBindByPosition(position=27)
	@CsvBindByName(column="notaHistorica", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTURISMO, propiedad = "notaHistorica")
	private String notaHistorica;
	
	@CsvBindByPosition(position=28)	
	@CsvBindByName(column="openingHours", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "openingHours")
	private String openingHours;
	
	@CsvBindByPosition(position=29)
	@CsvBindByName(column="image", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "image")
	@IsUri
	private String image;
	
	@CsvBindByPosition(position=30)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	private Double distance;


	public PuntoInteresTuristico() {
	}

	public PuntoInteresTuristico(String ikey) {
		this.ikey = ikey;
	}

	public PuntoInteresTuristico(String ikey, String id, String category, String title, String description, Boolean accesible, String tipoAccesibilidad, String municipioId, String municipioTitle, String streetAddress, String postalCode, String barrio, String distrito, BigDecimal x, BigDecimal y, String email, String telephone, String url, String siglo, String estiloArtistico, BigDecimal costeMantenimiento, BigDecimal ingresosObtenidos, String afluenciaPublico, Date fechaDeclaracionBien, String modoAcceso, String medioTransporte, String notaHistorica, String openingHours, String image) {
       this.ikey = ikey;
       this.id = id;
       this.category = category;
       this.title = title;
       this.description = description;
       this.accesible = accesible;
       this.tipoAccesibilidad = tipoAccesibilidad;
       this.municipioId = municipioId;
       this.municipioTitle = municipioTitle;
       this.streetAddress = streetAddress;
       this.postalCode = postalCode;
       this.barrio = barrio;
       this.distrito = distrito;
       this.x = x;
       this.y = y;
       this.email = email;
       this.telephone = telephone;
       this.url = url;
       this.siglo = siglo;
       this.estiloArtistico = estiloArtistico;
       this.costeMantenimiento = costeMantenimiento;
       this.ingresosObtenidos = ingresosObtenidos;
       this.afluenciaPublico = afluenciaPublico;
       this.fechaDeclaracionBien = fechaDeclaracionBien;
       this.modoAcceso = modoAcceso;
       this.medioTransporte = medioTransporte;
       this.notaHistorica = notaHistorica;
       this.openingHours = openingHours;
       this.image = image;
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

	@Column(name = "category", length = 400)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	@Column(name = "barrio", length = 200)
	public String getBarrio() {
		return this.barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	@Column(name = "distrito", length = 200)
	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
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

	@Column(name = "siglo", length = 5)
	public String getSiglo() {
		return this.siglo;
	}

	public void setSiglo(String siglo) {
		this.siglo = siglo;
	}

	@Column(name="estilo_artistico", length=200)
    public String getEstiloArtistico() {
        return this.estiloArtistico;
    }

	public void setEstiloArtistico(String estiloArtistico) {
        this.estiloArtistico = estiloArtistico;
    }

	@Column(name = "coste_mantenimiento", precision = 12)
	public BigDecimal getCosteMantenimiento() {
		return this.costeMantenimiento;
	}

	public void setCosteMantenimiento(BigDecimal costeMantenimiento) {
		this.costeMantenimiento = costeMantenimiento;
	}

	@Column(name = "ingresos_obtenidos", precision = 12)
	public BigDecimal getIngresosObtenidos() {
		return this.ingresosObtenidos;
	}

	public void setIngresosObtenidos(BigDecimal ingresosObtenidos) {
		this.ingresosObtenidos = ingresosObtenidos;
	}

	@Column(name = "afluencia_publico", length = 200)
	public String getAfluenciaPublico() {
		return this.afluenciaPublico;
	}

	public void setAfluenciaPublico(String afluenciaPublico) {
		this.afluenciaPublico = afluenciaPublico;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_declaracion_bien", length = 19)
	public Date getFechaDeclaracionBien() {
		return this.fechaDeclaracionBien;
	}

	public void setFechaDeclaracionBien(Date fechaDeclaracionBien) {
		this.fechaDeclaracionBien = fechaDeclaracionBien;
	}

	@Column(name = "modo_acceso", length = 200)
	public String getModoAcceso() {
		return this.modoAcceso;
	}

	public void setModoAcceso(String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	@Column(name = "medio_transporte", length = 200)
	public String getMedioTransporte() {
		return this.medioTransporte;
	}

	public void setMedioTransporte(String medioTransporte) {
		this.medioTransporte = medioTransporte;
	}

	@Column(name = "nota_historica", length = 4000)
	public String getNotaHistorica() {
		return this.notaHistorica;
	}

	public void setNotaHistorica(String notaHistorica) {
		this.notaHistorica = notaHistorica;
	}

	@Column(name = "opening_hours", length = 400)
	public String getOpeningHours() {
		return this.openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	
	@Column(name = "image", length = 200)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
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
	
	@Override
	public String toString() {
		return "PuntoInteresTuristico [ikey=" + ikey + ", id=" + id + ", title=" + title + ", category=" + category
				+ ", accesible=" + accesible + ", tipoAccesibilidad=" + tipoAccesibilidad + ", description="
				+ description + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", streetAddress=" + streetAddress + ", postalCode=" + postalCode + ", barrio=" + barrio
				+ ", distrito=" + distrito + ", email=" + email + ", telephone=" + telephone
				+ ", url=" + url + ", siglo=" + siglo + ", estiloArtistico=" + estiloArtistico + ", costeMantenimiento="
				+ costeMantenimiento + ", ingresosObtenidos=" + ingresosObtenidos + ", afluenciaPublico="
				+ afluenciaPublico + ", fechaDeclaracionBien=" + fechaDeclaracionBien + ", modoAcceso=" + modoAcceso
				+ ", medioTransporte=" + medioTransporte + ", notaHistorica=" + notaHistorica + ", openingHours="
				+ openingHours + ", image=" + image + ", x=" + x + ", y=" + y + "]";
	}

	public PuntoInteresTuristico(PuntoInteresTuristico obj) {
	       this.ikey = obj.ikey;
	       this.id = obj.id;
	       this.category = obj.category;
	       this.title = obj.title;
	       this.description = obj.description;
	       this.accesible = obj.accesible;
	       this.tipoAccesibilidad = obj.tipoAccesibilidad;
	       this.municipioId = obj.municipioId;
	       this.municipioTitle = obj.municipioTitle;
	       this.streetAddress = obj.streetAddress;
	       this.postalCode = obj.postalCode;
	       this.barrio = obj.barrio;
	       this.distrito = obj.distrito;
	       this.x = obj.x;
	       this.y = obj.y;
	       this.email = obj.email;
	       this.telephone = obj.telephone;
	       this.url = obj.url;
	       this.siglo = obj.siglo;
	       this.estiloArtistico = obj.estiloArtistico;
	       this.costeMantenimiento = obj.costeMantenimiento;
	       this.ingresosObtenidos = obj.ingresosObtenidos;
	       this.afluenciaPublico = obj.afluenciaPublico;
	       this.fechaDeclaracionBien = obj.fechaDeclaracionBien;
	       this.modoAcceso = obj.modoAcceso;
	       this.medioTransporte = obj.medioTransporte;
	       this.notaHistorica = obj.notaHistorica;
	       this.openingHours = obj.openingHours;
	       this.image = obj.image;
	    }
	
	public PuntoInteresTuristico(PuntoInteresTuristico copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("category")) {
			this.category = copia.category;		
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
		if (attributesToSet.contains("barrio")) {
			this.barrio = copia.barrio;		
		}
		if (attributesToSet.contains("distrito")) {
			this.distrito = copia.distrito;		
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
		if (attributesToSet.contains("siglo")) {
			this.siglo = copia.siglo;		
		}
		if (attributesToSet.contains("estiloArtistico")) {
			this.estiloArtistico = copia.estiloArtistico;		
		} 
		if (attributesToSet.contains("costeMantenimiento")) {
			this.costeMantenimiento = copia.costeMantenimiento;		
		} 
		if (attributesToSet.contains("ingresosObtenidos")) {
			this.ingresosObtenidos = copia.ingresosObtenidos;		
		} 
		if (attributesToSet.contains("afluenciaPublico")) {
			this.afluenciaPublico = copia.afluenciaPublico;		
		}
		if (attributesToSet.contains("fechaDeclaracionBien")) {
			this.fechaDeclaracionBien = copia.fechaDeclaracionBien;		
		}
		if (attributesToSet.contains("modoAcceso")) {
			this.modoAcceso = copia.modoAcceso;		
		}
		if (attributesToSet.contains("medioTransporte")) {
			this.medioTransporte = copia.medioTransporte;		
		}
		if (attributesToSet.contains("notaHistorica")) {
			this.notaHistorica = copia.notaHistorica;		
		}
		if (attributesToSet.contains("openingHours")) {
			this.openingHours = copia.openingHours;		
		}
		
		if (attributesToSet.contains("image")) {
			this.image = copia.image;		
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
		
		prefixes.put(Context.ESTURISMO, Context.ESTURISMO_URI);
		
		
		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (PuntoInteresTuristico) copia,listado) ;
	}

	
	
	public PuntoInteresTuristico cloneClass (PuntoInteresTuristico copia, List<String> attributesToSet)
	{
		PuntoInteresTuristico obj = new PuntoInteresTuristico(copia,attributesToSet);
		
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

		if (!Util.validValue(this.getCategory())) {
			result.add("Category is not valid [FechaInicio:" + this.getCategory() + "]");
		}

		if (!Util.validValue(this.getAccesible())) {
			result.add("Accesible is not valid [FechaFin:" + this.getAccesible() + "]");
		}

		return result;
	}
	
	/** METODO NECESARIO PARA CONFIGURAR EL PATHID CORRECTO EN EL RDFGENERATOR **/
	public String obtainURLPathFromType()
	{
		String path="";
		for (EnumCategoryPuntoInteres values : EnumCategoryPuntoInteres.values()) {
			String comp1=Util.prepareStringToCompare(getCategory());
			String comp2=Util.prepareStringToCompare(values.getCategory());
			
			if (comp1.equals(comp2))
			{
				path=values.getPath();
				break;
			}         
        } 
		return path;
	}
}

