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

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.ICallejero;
import org.ciudadesabiertas.model.RDFModel;
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
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "trafico_dispositivo_medicion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@RdfMultiple({@Rdf(contexto = Context.ESTRAF, propiedad = "DispositivoMedicionTrafico"), @Rdf(contexto = Context.ESTRAF, propiedad = "EquipoTrafico"), @Rdf(contexto = Context.SOSA, propiedad = "Sensor")})
@PathId(value="/trafico/dispositivo-medicion")
public class TraficoDispositivoMedicion  implements java.io.Serializable, GeoModel, RDFModel, ICallejero {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAFDISMED01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Una descripción del recurso dentro de un contexto dado. Ejemplo: C. GRAN VIA;San Bernardo-Garcia Molinas;San Bernardo")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;	
	
	@ApiModelProperty(value = "Número de sentidos de circulación. Ejemplo: 2")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="numSentidos")
	@Rdf(contexto = Context.ESTRAF, propiedad = "numSentidos", typeURI=Context.XSD_URI+"integer")
	private Integer numSentidos;	
	
	@ApiModelProperty(value = "Número de carriles de circulación. Ejemplo: 2")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="numCarriles")
	@Rdf(contexto = Context.ESTRAF, propiedad = "numCarriles", typeURI=Context.XSD_URI+"integer")
	private Integer numCarriles;
	
	@ApiModelProperty(value = "Esta propiedad permite describir si el dispositivo es urbano o no. Ejemplo: true")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="urbano", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "urbano", typeURI=Context.XSD_URI+"boolean")
	private Boolean urbano;
	
	@ApiModelProperty(value = "Tipo de equipo de tráfico, equipos de control y dispositivos de medición. Ejemplo: lazo-magnetico")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="tipoEquipoTrafico", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "tipoEquipoTrafico")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/transporte/trafico/tipo-equipo-trafico/", finURI="tipoEquipoTrafico", urifyLevel=2)
	private String tipoEquipoTrafico;
	
	@ApiModelProperty(value = "Relación del Equipo de Tráfico con el tramo que monitorea. Ejemplo: TRAFTRAM01")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="monitorea", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "monitorea")
	@RdfExternalURI(inicioURI="/trafico/tramo/",finURI="monitorea", urifyLevel = 1)
	private String monitorea;
	
	@ApiModelProperty(value = "Esta propiedad permite conocer si el dispositivo funciona o no. Ejemplo: true")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="enServicio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "enServicio", typeURI=Context.XSD_URI+"boolean")
	private Boolean enServicio;
	
	@ApiModelProperty(value = "Esta propiedad permite conocer la frecuencia de medición del dispositivo. Ejemplo: 5 minutos")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="frecuenciaMedicion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "frecuenciaMedicion")
	private String frecuenciaMedicion;
	
	@ApiModelProperty(value = "Relación entre un sensor y una propiedad observable que es capaz de detectar. Ejemplo: carga")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="observes", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "observes")
	@RdfExternalURI(inicioURI="/trafico/propiedad-medicion/",finURI="observes", urifyLevel = 1)
	private String observes;
	
	@ApiModelProperty(value = "Coordenada X del equipo de tráfico. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="xETRS89Inicio")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal x;	
	
	@ApiModelProperty(value = "Coordenada Y del equipo de tráfico. Ejemplo: 4474637.17000")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal y;
		
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Identificador de la calle de la autoridad. Ejemplo: PORTAL000098")
	@CsvBindByPosition(position = 15)
	@CsvBindByName(column = "portalId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI = "/callejero/portal/", finURI = "portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(value = "Calle del equipo de tráfico. Ejemplo: CL ORENSE 7")
	@CsvBindByPosition(position = 16)
	@CsvBindByName(column = "streetAddress", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI + "address", nodoId = "address")
	private String streetAddress;

	@ApiModelProperty(value = "Código postal del equipo de tráfico. Ejemplo: 28100")
	@CsvBindByPosition(position = 17)
	@CsvBindByName(column = "postalCode", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI
			+ "address", nodoId = "address")
	private String postalCode;

	@ApiModelProperty(value = "Identificador del municipio del equipo de tráfico. Ejemplo: 28006")
	@CsvBindByPosition(position = 18)
	@CsvBindByName(column = "municipioId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI = "/territorio/municipio/", finURI = "municipioId")
	private String municipioId;

	@ApiModelProperty(value = "Nombre del municipio del equipo de tráfico. Ejemplo: Alcobendas")
	@CsvBindByPosition(position = 19)
	@CsvBindByName(column = "municipioTitle", format = Constants.STRING_FORMAT)
	private String municipioTitle;

	@ApiModelProperty(value = "Identificador del barrio del equipo de tráfico. Ejemplo: 28006011")
	@CsvBindByPosition(position = 20)
	@CsvBindByName(column = "barrio", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")
	@RdfExternalURI(inicioURI = "/territorio/barrio/", finURI = "barrioId", urifyLevel = 1)
	private String barrioId;

	@ApiModelProperty(value = "Nombre del barrio del equipo de tráfico. Ejemplo: 28006011")
	@CsvBindByPosition(position = 21)
	@CsvBindByName(column = "barrioTitle", format = Constants.STRING_FORMAT)
	private String barrioTitle;

	@ApiModelProperty(value = "Identificador del distrito del equipo de tráfico. Ejemplo: 2800601")
	@CsvBindByPosition(position = 22)
	@CsvBindByName(column = "distrito", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI = "/territorio/distrito/", finURI = "distritoId", urifyLevel = 1)
	private String distritoId;

	@ApiModelProperty(value = "Nombre del distrito del equipo de tráfico. Ejemplo: Unico")
	@CsvBindByPosition(position = 23)
	@CsvBindByName(column = "distritoTitle", format = Constants.STRING_FORMAT)
	private String distritoTitle;
	
	private Double distance;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	

	public TraficoDispositivoMedicion()
	{
	}

	public TraficoDispositivoMedicion(TraficoDispositivoMedicion copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.numSentidos = copia.numSentidos;
		this.numCarriles = copia.numCarriles;
		this.urbano = copia.urbano;
		this.tipoEquipoTrafico = copia.tipoEquipoTrafico;
		this.monitorea = copia.monitorea;
		this.enServicio = copia.enServicio;
		this.frecuenciaMedicion = copia.frecuenciaMedicion;
		this.observes = copia.observes;
		this.x = copia.x;
		this.y = copia.y;
		this.latitud = copia.latitud;
		this.longitud = copia.longitud;	
		  this.streetAddress = copia.streetAddress;
		  this.postalCode = copia.postalCode;
		  this.municipioId = copia.municipioId;
		  this.municipioTitle = copia.municipioTitle;
		  this.barrioId = copia.barrioId;
		  this.barrioTitle = copia.barrioTitle;
		  this.distritoId = copia.distritoId;
		  this.distritoTitle = copia.distritoTitle;
		  this.portalId = copia.portalId;
	}

	
	
	


	public TraficoDispositivoMedicion(TraficoDispositivoMedicion copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;		
		}
		if (attributesToSet.contains("numSentidos")) {
			this.numSentidos = copia.numSentidos;		
		}
		if (attributesToSet.contains("numCarriles")) {
			this.numCarriles = copia.numCarriles;		
		}
		if (attributesToSet.contains("urbano")) {
			this.urbano = copia.urbano;		
		}
		if (attributesToSet.contains("tipoEquipoTrafico")) {
			this.tipoEquipoTrafico = copia.tipoEquipoTrafico;		
		}
		if (attributesToSet.contains("monitorea")) {
			this.monitorea = copia.monitorea;		
		}
		if (attributesToSet.contains("enServicio")) {
			this.enServicio = copia.enServicio;		
		}
		if (attributesToSet.contains("frecuenciaMedicion")) {
			this.frecuenciaMedicion = copia.frecuenciaMedicion;		
		}
		if (attributesToSet.contains("observes")) {
			this.observes = copia.observes;		
		}
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
		}
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;
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

		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;
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

	@Column(name = "id", nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	@Column(name = "description", nullable = true, length = 4000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "num_sentidos", nullable = true)
	public Integer getNumSentidos() {
		return numSentidos;
	}

	public void setNumSentidos(Integer numSentidos) {
		this.numSentidos = numSentidos;
	}
	
	@Column(name = "num_carriles", nullable = true)
	public Integer getNumCarriles() {
		return numCarriles;
	}

	public void setNumCarriles(Integer numCarriles) {
		this.numCarriles = numCarriles;
	}
	
	@Column(name = "urbano")
	public Boolean getUrbano() {
		return urbano;
	}

	public void setUrbano(Boolean urbano) {
		this.urbano = urbano;
	}
	
	@Column(name = "tipo_equipo_trafico", nullable = true, length = 200)
	public String getTipoEquipoTrafico() {
		return tipoEquipoTrafico;
	}

	public void setTipoEquipoTrafico(String tipoEquipoTrafico) {
		this.tipoEquipoTrafico = tipoEquipoTrafico;
	}
	
	@Column(name = "monitorea", nullable = true, length = 50)
	public String getMonitorea() {
		return monitorea;
	}

	public void setMonitorea(String monitorea) {
		this.monitorea = monitorea;
	}
	
	@Column(name = "en_servicio")
	public Boolean getEnServicio() {
		return enServicio;
	}

	public void setEnServicio(Boolean enServicio) {
		this.enServicio = enServicio;
	}
	
	@Column(name = "frecuencia_medicion", nullable = true, length = 200)
	public String getFrecuenciaMedicion() {
		return frecuenciaMedicion;
	}

	public void setFrecuenciaMedicion(String frecuenciaMedicion) {
		this.frecuenciaMedicion = frecuenciaMedicion;
	}

	@Column(name = "observes", nullable = true, length = 200)
	public String getObserves() {
		return observes;
	}

	public void setObserves(String observes) {
		this.observes = observes;
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
	
	
	@Column(name = "street_address", length = 200)
	public String getStreetAddress() {
	  return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
	  this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode() {
	  return postalCode;
	}

	public void setPostalCode(String postalCode) {
	  this.postalCode = postalCode;
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
	public Double getDistance() {
		return distance;
	}


	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Column(name = "portal_id", length = 50)
	public String getPortalId() {
		return this.portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
	}


	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated = portalIdIsolated;
	}

	

	@Override
	public String toString() {
		return "TraficoDispositivoMedicion [id=" + id + ", description=" + description + ", numSentidos=" + numSentidos
				+ ", numCarriles=" + numCarriles + ", urbano=" + urbano + ", tipoEquipoTrafico=" + tipoEquipoTrafico
				+ ", monitorea=" + monitorea + ", enServicio=" + enServicio + ", frecuenciaMedicion="
				+ frecuenciaMedicion + ", observes=" + observes + ", x=" + x + ", y=" + y + ", latitud=" + latitud
				+ ", longitud=" + longitud + ", portalId=" + portalId + ", streetAddress=" + streetAddress
				+ ", postalCode=" + postalCode + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", barrioId=" + barrioId + ", barrioTitle=" + barrioTitle + ", distritoId=" + distritoId
				+ ", distritoTitle=" + distritoTitle + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.GEOSPARQL, Context.GEOSPARQL_URI);	
		prefixes.put(Context.SF, Context.SF_URI);
		prefixes.put(Context.ESTRAF, Context.ESTRAF_URI);
		prefixes.put(Context.SOSA, Context.SOSA_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((TraficoDispositivoMedicion) copia, listado);
	}
	
	public TraficoDispositivoMedicion cloneClass(TraficoDispositivoMedicion copia, List<String> attributesToSet) {

		TraficoDispositivoMedicion obj = new TraficoDispositivoMedicion(copia,attributesToSet);		

		return obj;

	}

	@Override
	public List<String> validate()
	{
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
		
		return result;
	}


	
	


	
	




}

