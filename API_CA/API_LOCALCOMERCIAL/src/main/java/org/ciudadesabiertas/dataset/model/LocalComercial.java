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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.model.GeoModel;
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
@ApiModel
@Table(name = "local_comercial", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCOM, propiedad = "LocalComercial")
@PathId(value="/localComercial/localComercial")
public class LocalComercial implements java.io.Serializable, GeoModel, RDFModel
{
	@JsonIgnore
	private static final long serialVersionUID = 8909539156599176499L;
	
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
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")	
	private String municipioId;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")
	private String municipioTitle;
	

	@CsvBindByPosition(position=5)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="streetAddress")
	private String streetAddress;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="streetAddress")	
	private String postalCode;
	
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")		
	private String barrio;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	private String distrito;
	
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="xETRS89")	
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="telephone", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;
	
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;	
		 
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="tipoActividadEconomica")
	@Rdf(contexto = Context.ESCOM, propiedad = "tipoActividadEconomica")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/cnae/Division/", finURI="tipoActividadEconomica", urifyLevel=0)
	private String tipoActividadEconomica;
	
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="nombreComercial")
	@Rdf(contexto = Context.ESCOM, propiedad = "nombreComercial")
	private String nombreComercial;
		
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="rotulo")
	@Rdf(contexto = Context.ESCOM, propiedad = "rotulo")
	private String rotulo;
	
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="aforo")
	@Rdf(contexto = Context.ESCOM, propiedad = "aforo", typeURI=Context.XSD_URI+"int")
	private Integer aforo;
	
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="tipoSituacion")
	@Rdf(contexto = Context.ESCOM, propiedad = "tipoSituacion")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/tipo-situacion/", finURI="tipoSituacion", urifyLevel=1)
	private String tipoSituacion;

	@CsvBindByPosition(position=20)
	@CsvBindByName(column="tipoAcceso")
	@Rdf(contexto = Context.ESCOM, propiedad = "tipoAcceso")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/tipo-acceso/", finURI="tipoAcceso", urifyLevel=1)
	private String tipoAcceso;
		 
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="referenciaCatastral")
	@Rdf(contexto = Context.ESCOM, propiedad = "referenciaCatastral")
	private String referenciaCatastral;
	
	
	
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="tieneLicenciaApertura")
	@Rdf(contexto = Context.ESCOM, propiedad = "tieneLicenciaApertura")
	@RdfExternalURI(inicioURI= "/localComercial/licenciaActividad/", finURI="tieneLicenciaApertura")	
	private String tieneLicenciaApertura;
	
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="tieneTerraza")
	@Rdf(contexto = Context.ESCOM, propiedad = "tieneTerraza")
	@RdfExternalURI(inicioURI= "/localComercial/terraza/", finURI="tieneTerraza")
	private String tieneTerraza;
	
	
	@CsvBindByPosition(position=24)
	@CsvBindByName(column="agrupacionComercial")
	@Rdf(contexto = Context.ESCOM, propiedad = "agrupacionComercial")
	@RdfExternalURI(inicioURI= "/localComercial/agrupacionComercial/", finURI="agrupacionComercial")
	private String agrupacionComercial;
	
	
	@CsvBindByPosition(position=25)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	private Double distance;

	public LocalComercial()
	{
	}

	public LocalComercial(String ikey, String id)
	{
		this.ikey = ikey;
		this.id = id;
	}

	public LocalComercial(LocalComercial lc)
	{
		this.ikey = lc.ikey;
		this.id = lc.id;
		this.title = lc.title;
		this.description = lc.description;
		this.municipioId = lc.municipioId;
		this.municipioTitle = lc.municipioTitle;
		this.streetAddress = lc.streetAddress;
		this.postalCode = lc.postalCode;
		this.distrito = lc.distrito;
		this.barrio = lc.barrio;
		this.latitud = lc.latitud;
		this.longitud = lc.longitud;
		this.telephone = lc.telephone;
		this.url = lc.url;
		this.tipoActividadEconomica = lc.tipoActividadEconomica;
		this.nombreComercial = lc.nombreComercial;
		this.rotulo = lc.rotulo;
		this.aforo = lc.aforo;
		this.tipoSituacion = lc.tipoSituacion;
		this.tipoAcceso = lc.tipoAcceso;
		this.referenciaCatastral = lc.referenciaCatastral;
		this.tieneLicenciaApertura = lc.tieneLicenciaApertura;
		this.tieneTerraza = lc.tieneTerraza;
		this.agrupacionComercial = lc.agrupacionComercial;
		this.x=lc.x;
		this.y=lc.y;
	}
	
	
	public LocalComercial(LocalComercial lc, List<String> attributesToSet)
	{
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = lc.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = lc.id;
		}
		if (attributesToSet.contains("title")) {
			this.title = lc.title;
		}
		if (attributesToSet.contains("description")) {
			this.description = lc.description;
		}
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = lc.municipioId;
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = lc.municipioTitle;
		}
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = lc.streetAddress;
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = lc.postalCode;
		}
		
		if (attributesToSet.contains("distrito")) {
			this.distrito = lc.distrito;
		}

		if (attributesToSet.contains("barrio")) {
			this.barrio = lc.barrio;
		}

		if (attributesToSet.contains("latitud")) {
			this.latitud = lc.latitud;
		}
		
		if (attributesToSet.contains("longitud")) {
			this.longitud = lc.longitud;
		}
		
		if (attributesToSet.contains("telephone")) {
			this.telephone = lc.telephone;
		}
		
		if (attributesToSet.contains("url")) {
			this.url = lc.url;
		}
		
		if (attributesToSet.contains("tipoActividadEconomica")) {
			this.tipoActividadEconomica = lc.tipoActividadEconomica;
		}
		
		if (attributesToSet.contains("nombreComercial")) {
			this.nombreComercial = lc.nombreComercial;
		}
		
		if (attributesToSet.contains("rotulo")) {
			this.rotulo = lc.rotulo;
		}
		
		if (attributesToSet.contains("aforo")) {
			this.aforo = lc.aforo;
		}
		
		if (attributesToSet.contains("tipoSituacion")) {
			this.tipoSituacion = lc.tipoSituacion;
		}
		
		if (attributesToSet.contains("tipoAcceso")) {
			this.tipoAcceso = lc.tipoAcceso;
		}
		
		
		if (attributesToSet.contains("tipoSituacion")) {
			this.tipoSituacion = lc.tipoSituacion;
		}
		
		if (attributesToSet.contains("referenciaCatastral")) {
			this.referenciaCatastral = lc.referenciaCatastral;
		}
		
		if (attributesToSet.contains("tieneLicenciaApertura")) {
			this.tieneLicenciaApertura = lc.tieneLicenciaApertura;
		}


		if (attributesToSet.contains("tieneTerraza")) {
			this.tieneTerraza = lc.tieneTerraza;
		}
		
		if (attributesToSet.contains("agrupacionComercial")) {
			this.agrupacionComercial = lc.agrupacionComercial;
		}
		
		if (attributesToSet.contains("xETRS89")) {
			this.x = lc.x;
		}
		
		if (attributesToSet.contains("yETRS89")) {
			this.y = lc.y;
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

	@Column(name = "title", length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "description", length = 400)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "municipio_id", length = 10)
	public String getMunicipioId()
	{
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId)
	{
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle()
	{
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle)
	{
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "street_address", length = 200)
	public String getStreetAddress()
	{
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode()
	{
		return this.postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	@Column(name = "distrito", length = 200)
	public String getDistrito()
	{
		return this.distrito;
	}

	public void setDistrito(String distrito)
	{
		this.distrito = distrito;
	}

	@Column(name = "barrio", length = 200)
	public String getBarrio()
	{
		return this.barrio;
	}

	public void setBarrio(String barrio)
	{
		this.barrio = barrio;
	}

	
	@Transient
	public BigDecimal getLatitud()
	{
		return this.latitud;
	}

	public void setLatitud(BigDecimal latitud)
	{
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud()
	{
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud)
	{
		this.longitud = longitud;
	}

	@Column(name = "telephone", length = 200)
	public String getTelephone()
	{
		return this.telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
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

	@Column(name = "tipo_actividad_economica", length = 10)
	public String getTipoActividadEconomica()
	{
		return this.tipoActividadEconomica;
	}

	public void setTipoActividadEconomica(String tipoActividadEconomica)
	{
		this.tipoActividadEconomica = tipoActividadEconomica;
	}

	@Column(name = "nombre_comercial", length = 200)
	public String getNombreComercial()
	{
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial)
	{
		this.nombreComercial = nombreComercial;
	}

	@Column(name = "rotulo", length = 200)
	public String getRotulo()
	{
		return this.rotulo;
	}

	public void setRotulo(String rotulo)
	{
		this.rotulo = rotulo;
	}

	@Column(name = "aforo")
	public Integer getAforo()
	{
		return this.aforo;
	}

	public void setAforo(Integer aforo)
	{
		this.aforo = aforo;
	}

	@Column(name = "tipo_situacion", length = 200)
	public String getTipoSituacion()
	{
		return this.tipoSituacion;
	}

	public void setTipoSituacion(String tipoSituacion)
	{
		this.tipoSituacion = tipoSituacion;
	}

	@Column(name = "tipo_acceso", length = 200)
	public String getTipoAcceso()
	{
		return this.tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso)
	{
		this.tipoAcceso = tipoAcceso;
	}

	@Column(name = "referencia_catastral", length = 100)
	public String getReferenciaCatastral()
	{
		return this.referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral)
	{
		this.referenciaCatastral = referenciaCatastral;
	}

	@Column(name = "tiene_licencia_apertura", length = 50)
	public String getTieneLicenciaApertura()
	{
		return this.tieneLicenciaApertura;
	}

	public void setTieneLicenciaApertura(String tieneLicenciaApertura)
	{
		this.tieneLicenciaApertura = tieneLicenciaApertura;
	}

	@Column(name = "tiene_terraza", length = 50)
	public String getTieneTerraza()
	{
		return this.tieneTerraza;
	}

	public void setTieneTerraza(String tieneTerraza)
	{
		this.tieneTerraza = tieneTerraza;
	}

	@Column(name = "agrupacion_comercial", length = 50)
	public String getAgrupacionComercial()
	{
		return this.agrupacionComercial;
	}

	public void setAgrupacionComercial(String agrupacionComercial)
	{
		this.agrupacionComercial = agrupacionComercial;
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
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}
	
	

	@Override
	public String toString()
	{
		return "LocalComercial [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle + ", streetAddress=" + streetAddress + ", postalCode=" + postalCode + ", barrio=" + barrio + ", distrito=" + distrito + ", latitud="
				+ latitud + ", longitud=" + longitud + ", telephone=" + telephone + ", url=" + url + ", tipoActividadEconomica=" + tipoActividadEconomica + ", nombreComercial=" + nombreComercial + ", rotulo=" + rotulo + ", aforo=" + aforo + ", tipoSituacion=" + tipoSituacion + ", tipoAcceso=" + tipoAcceso
				+ ", referenciaCatastral=" + referenciaCatastral + ", x=" + x + ", y=" + y + ", tieneLicenciaApertura=" + tieneLicenciaApertura + ", tieneTerraza=" + tieneTerraza + ", agrupacionComercial=" + agrupacionComercial + "]";
	}

	@Override
	public Map<String, String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);	
		
		prefixes.put(Context.ESCOM, Context.ESCOM_URI);		
		
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (LocalComercial) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public LocalComercial cloneClass(LocalComercial lc, List<String> attributesToSet) {
		
		LocalComercial obj = new LocalComercial(lc,attributesToSet);
				
		return obj;
		
	}

	//LocalComercial
	// TODO valiaciones de equipamientos ver si es posible realizar mediante
	// anotaciones en model el saber los campos obligatorios
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getTitle())) {
			result.add("title is not valid [Nombre:" + this.getTitle() + "]");
		}
		
		if (!Util.validValue(this.getStreetAddress())) {
			result.add("streetAddress is not valid [streetAddress:" + this.getStreetAddress() + "]");
		}

		
		return result;
	}
	
}
