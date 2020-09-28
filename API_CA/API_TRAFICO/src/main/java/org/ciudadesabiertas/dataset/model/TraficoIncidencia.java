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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.GeoModel;
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
@Table(name = "trafico_incidencia")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESTRAF, propiedad = "Incidencia")
@PathId(value="/trafico/incidencia")
public class TraficoIncidencia  implements java.io.Serializable, GeoModel, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAFINCI01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	//private TraficoTramo traficoTramo;
	
	@ApiModelProperty(value = "Una descripción del recurso dentro de un contexto dado. Ejemplo: C. GRAN VIA;San Bernardo-Garcia Molinas;San Bernardo")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;	
	
	@ApiModelProperty(value = "Tipo de incidencia, pueden ser planificadas o no planificadas. Ejemplo: obra")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="tipoIncidencia")
	@Rdf(contexto = Context.ESTRAF, propiedad = "tipoIncidencia")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/transporte/trafico/tipo-incidencia/", finURI="tipoIncidencia", urifyLevel=2)
	private String tipoIncidencia;
	
	@ApiModelProperty(value = "La fecha y hora de publicación de una incidencia (en formato fecha ISO 8601). Ejemplo: 2020-03-31 08:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="datePosted")
	@Rdf(contexto = Context.SCHEMA, propiedad = "datePosted" ,typeURI=Context.XSD_URI+"dateTime")
	private Date datePosted;
	
	@ApiModelProperty(value = "Número de sentidos de circulación. Ejemplo: 2")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="numSentidos", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "numSentidos", typeURI=Context.XSD_URI+"integer")
	private Integer numSentidos;
	
	@ApiModelProperty(value = "Número de carriles de circulación. Ejemplo: 8")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="numCarriles", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "numCarriles", typeURI=Context.XSD_URI+"integer")
	private Integer numCarriles;
	
	@ApiModelProperty(value = "Esta propiedad permite describir si la incidencia es recurrente o no. Ejemplo: 0")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="esRecurrente", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESTRAF, propiedad = "esRecurrente", typeURI=Context.XSD_URI+"boolean")
	private Boolean esRecurrente;
	
	@ApiModelProperty(value = "La fecha y hora prevista de finalización de una incidencia planificada (en formato fecha ISO 8601). Ejemplo: 2020-05-03 23:59:00")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="fechaFinPrevista")
	@Rdf(contexto = Context.ESTRAF, propiedad = "fechaFinPrevista" ,typeURI=Context.XSD_URI+"dateTime")
	private Date fechaFinPrevista;
	
	@ApiModelProperty(value = "Esta propiedad permite describir la recurrencia. Ejemplo: Sin recurrencia")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="recurrencia")
	@Rdf(contexto = Context.ESTRAF, propiedad = "recurrencia")
	private String recurrencia;
	
	@ApiModelProperty(value = "Relación de la Incidencia con el Tramo donde se produce la misma. Ejemplo: TRAFTRAM01")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="incidenciaEnTramo")
	@Rdf(contexto = Context.ESTRAF, propiedad = "incidenciaEnTramo")
	@RdfExternalURI(inicioURI="/trafico/tramo/",finURI="incidenciaEnTramo", urifyLevel = 1)
	private String incidenciaEnTramo;
		
	@ApiModelProperty(value = "Coordenada X del equipo de tráfico. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="xETRS89")
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
	
	private Double distance;
	

	public TraficoIncidencia()
	{
	}

	public TraficoIncidencia(TraficoIncidencia copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.tipoIncidencia = copia.tipoIncidencia;
		this.datePosted = copia.datePosted;
		this.numSentidos = copia.numSentidos;
		this.numCarriles = copia.numCarriles;
		this.esRecurrente = copia.esRecurrente;
		this.fechaFinPrevista = copia.fechaFinPrevista;
		this.recurrencia = copia.recurrencia;
		this.x = copia.x;
		this.y = copia.y;
		this.latitud = copia.latitud;
		this.longitud = copia.longitud;
		this.distance = copia.distance;
	}

	public TraficoIncidencia(TraficoIncidencia copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("tipoIncidencia")) {
			this.tipoIncidencia = copia.tipoIncidencia;		
		}
		if (attributesToSet.contains("datePosted")) {
			this.datePosted = copia.datePosted;		
		}
		if (attributesToSet.contains("numSentidos")) {
			this.numSentidos = copia.numSentidos;		
		}
		if (attributesToSet.contains("numCarriles")) {
			this.numCarriles = copia.numCarriles;		
		}
		if (attributesToSet.contains("esRecurrente")) {
			this.esRecurrente = copia.esRecurrente;		
		}
		if (attributesToSet.contains("fechaFinPrevista")) {
			this.fechaFinPrevista = copia.fechaFinPrevista;		
		}
		if (attributesToSet.contains("recurrencia")) {
			this.recurrencia = copia.recurrencia;		
		}
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
		}
		
		
	}


	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "incidencia_tramo", nullable = false)
	public TraficoTramo getTraficoTramo() {
		return this.traficoTramo;
	}

	public void setTraficoTramo(TraficoTramo traficoTramo) {
		this.traficoTramo = traficoTramo;
	}*/ 

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "description", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "tipo_incidencia", length = 200)
	public String getTipoIncidencia() {
		return this.tipoIncidencia;
	}

	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_posted", length = 19)
	public Date getDatePosted() {
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	@Column(name = "num_sentidos")
	public Integer getNumSentidos() {
		return this.numSentidos;
	}

	public void setNumSentidos(Integer numSentidos) {
		this.numSentidos = numSentidos;
	}

	@Column(name = "num_carriles")
	public Integer getNumCarriles() {
		return this.numCarriles;
	}

	public void setNumCarriles(Integer numCarriles) {
		this.numCarriles = numCarriles;
	}

	@Column(name = "es_recurrente")
	public Boolean getEsRecurrente() {
		return this.esRecurrente;
	}

	public void setEsRecurrente(Boolean esRecurrente) {
		this.esRecurrente = esRecurrente;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin_prevista", length = 19)
	public Date getFechaFinPrevista() {
		return this.fechaFinPrevista;
	}

	public void setFechaFinPrevista(Date fechaFinPrevista) {
		this.fechaFinPrevista = fechaFinPrevista;
	}

	@Column(name = "recurrencia", length = 200)
	public String getRecurrencia() {
		return this.recurrencia;
	}

	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}
	
	@Column(name = "incidencia_tramo", unique = true, nullable = false, length = 50)
	public String getIncidenciaEnTramo() {
		return incidenciaEnTramo;
	}

	public void setIncidenciaEnTramo(String incidenciaEnTramo) {
		this.incidenciaEnTramo = incidenciaEnTramo;
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
	public Double getDistance() {
		return distance;
	}


	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "TraficoIncidencia [ikey=" + ikey + ", id=" + id + ", description=" + description + ", tipoIncidencia="
				+ tipoIncidencia + ", datePosted=" + datePosted + ", numSentidos=" + numSentidos + ", numCarriles="
				+ numCarriles + ", esRecurrente=" + esRecurrente + ", fechaFinPrevista=" + fechaFinPrevista
				+ ", recurrencia=" + recurrencia + ", incidenciaEnTramo=" + incidenciaEnTramo + ", x=" + x + ", y=" + y
				+ ", latitud=" + latitud + ", longitud=" + longitud + ", distance=" + distance + "]";
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
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((TraficoIncidencia) copia, listado);
	}
	
	public TraficoIncidencia cloneClass(TraficoIncidencia copia, List<String> attributesToSet) {

		TraficoIncidencia obj = new TraficoIncidencia(copia,attributesToSet);		

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

