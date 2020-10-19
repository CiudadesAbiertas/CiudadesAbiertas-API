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
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.ITrafico;
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
@Table(name = "trafico_tramo")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESTRAF, propiedad = "Tramo")
@PathId(value="/trafico/tramo")
public class TraficoTramo  implements java.io.Serializable, GeoModel, ITrafico, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAFTRAM01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Una descripción del tramo. Ejemplo: Calles entre el cruce de Alcalá con Gran Vía y la Plaza de la Independencia")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	@ApiModelProperty(value = "Coordenada X del tramo. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"inicioTramo", nodoId="inicioTramo")
	private BigDecimal x;	
	
	@ApiModelProperty(value = "Coordenada Y del tramo. Ejemplo: 4474637.17000")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"inicioTramo", nodoId="inicioTramo")
	private BigDecimal y;
		
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"inicioTramo", nodoId="inicioTramo")
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"inicioTramo", nodoId="inicioTramo")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Coordenada xETRS89Fin del tramo. Ejemplo: 440124.43000")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="xETRS89Fin")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"finTramo", nodoId="finTramo")
	private BigDecimal finX;	
	
	@ApiModelProperty(value = "Coordenada yETRS89Fin del tramo. Ejemplo: 4474637.27000")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="yETRS89Fin")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"finTramo", nodoId="finTramo")
	private BigDecimal finY;
		
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="latitudFin")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"finTramo", nodoId="finTramo")
	private BigDecimal finLatitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="longitudFin")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.ESTRAF_URI+"finTramo", nodoId="finTramo")
	private BigDecimal finLongitud;
	
	@Transient
	@ApiModelProperty(value = "Coordenada X de la ubicación del tramo de tráfico. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal ubicacionX;	
	
	@Transient
	@ApiModelProperty(value = "Coordenada Y de la ubicación del tramo de tráfico. Ejemplo: 4474637.17000")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal ubicacionY;
		
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal ubicacionLatitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.SF_URI+"Point", propiedad=Context.GEOSPARQL_URI+"hasGeometry", nodoId="hasGeometry")
	private BigDecimal ubicacionLongitud;
	
	@ApiModelProperty(value = "Identificador del municipio del tramo. Ejemplo: 28006")
	@CsvBindByPosition(position = 15)
	@CsvBindByName(column = "municipioId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI = "/territorio/municipio/", finURI = "municipioId")
	private String municipioId;

	@ApiModelProperty(value = "Nombre del municipio del tramo. Ejemplo: Alcobendas")
	@CsvBindByPosition(position = 16)
	@CsvBindByName(column = "municipioTitle", format = Constants.STRING_FORMAT)
	private String municipioTitle;
	
	private Double distance;
		

	public TraficoTramo()
	{
	}


	public TraficoTramo(TraficoTramo copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.x = copia.x;
		this.y = copia.y;
		this.latitud = copia.latitud;
		this.longitud = copia.longitud;
		this.finX = copia.finX;
		this.finY = copia.finY;
		this.finLatitud = copia.finLatitud;
		this.finLongitud = copia.finLongitud;	
		this.distance = copia.distance;
		this.ubicacionX = copia.x;
		this.ubicacionY = copia.y;
		this.ubicacionLatitud = copia.latitud;
		this.ubicacionLongitud = copia.longitud;
		
		  this.municipioId = copia.municipioId;
		  this.municipioTitle = copia.municipioTitle;
		  		 
	}

	public TraficoTramo(TraficoTramo copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
			this.ubicacionX = copia.x;
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
			this.ubicacionY = copia.y;
		}
		if (attributesToSet.contains("xETRS89Fin")) {
			this.finX = copia.finX;
		}
		if (attributesToSet.contains("yETRS89Fin")) {
			this.finY = copia.finY;
		}
		
		
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
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
	
	@Column(name = "description", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89_inicio_tramo", precision = 13, scale = 5)
	public BigDecimal getX()
	{
		return x;
	}

	
	public void setX(BigDecimal x)
	{
		this.x = x;
	}

	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89_inicio_tramo", precision = 13, scale = 5)
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

	@JsonProperty("xETRS89Fin")
	@Column(name = "x_etrs89_fin_tramo", precision = 13, scale = 5)
	public BigDecimal getFinX()
	{
		return finX;
	}
	
	
	public void setFinX(BigDecimal finX)
	{
		this.finX = finX;
	}
	
	@JsonProperty("yETRS89Fin")
	@Column(name = "y_etrs89_fin_tramo", precision = 13, scale = 5)
	public BigDecimal getFinY()
	{
		return finY;
	}
	
	
	public void setFinY(BigDecimal finY)
	{
		this.finY = finY;
	}
	
	@Transient
	public BigDecimal getFinLatitud() {
		return this.finLatitud;
	}
	
	@Transient
	public void setFinLatitud(BigDecimal finLatitud) {
		this.finLatitud = finLatitud;
	}
	
	@Transient
	public BigDecimal getFinLongitud() {
		return this.finLongitud;
	}
	
	@Transient
	public void setFinLongitud(BigDecimal finLongitud) {
		this.finLongitud = finLongitud;
	}
	
	@Transient
	public BigDecimal getUbicacionX() {
		return ubicacionX;
	}

	@Transient
	public void setUbicacionX(BigDecimal ubicacionX) {
		this.ubicacionX = ubicacionX;
	}

	@Transient
	public BigDecimal getUbicacionY() {
		return ubicacionY;
	}

	@Transient
	public void setUbicacionY(BigDecimal ubicacionY) {
		this.ubicacionY = ubicacionY;
	}

	@Transient
	public BigDecimal getUbicacionLatitud() {
		return ubicacionLatitud;
	}

	@Transient
	public void setUbicacionLatitud(BigDecimal ubicacionLatitud) {
		this.ubicacionLatitud = ubicacionLatitud;
	}

	@Transient
	public BigDecimal getUbicacionLongitud() {
		return ubicacionLongitud;
	}

	@Transient
	public void setUbicacionLongitud(BigDecimal ubicacionLongitud) {
		this.ubicacionLongitud = ubicacionLongitud;
	}


	@Transient
	public Double getDistance() {
		return distance;
	}


	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
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

	

	@Override
	public String toString() {
		return "TraficoTramo [id=" + id + ", description=" + description + ", x=" + x + ", y=" + y + ", latitud="
				+ latitud + ", longitud=" + longitud + ", finX=" + finX + ", finY=" + finY + ", finLatitud="
				+ finLatitud + ", finLongitud=" + finLongitud + ", ubicacionX=" + ubicacionX + ", ubicacionY="
				+ ubicacionY + ", ubicacionLatitud=" + ubicacionLatitud + ", ubicacionLongitud=" + ubicacionLongitud
				+ ", distance=" + distance + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ "]";
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
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
	
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((TraficoTramo) copia, listado);
	}
	
	public TraficoTramo cloneClass(TraficoTramo copia, List<String> attributesToSet) {

		TraficoTramo obj = new TraficoTramo(copia,attributesToSet);		

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

