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
 *
 */
@Entity
@ApiModel
@Table(name = "callejero_portal")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCJR, propiedad = "Portal")
@PathId(value = "/callejero/portal")
public class CallejeroPortal implements java.io.Serializable, RDFModel, GeoModel
{
	@JsonIgnore
	private static final long serialVersionUID = 7914312089246811989L;	

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del portal. Ejemplo: PORTAL000001")
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;	
	
	@ApiModelProperty(value = "Vía a la que pertenece un fenómeno. Ejemplo: 114600")
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "via", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "via")
	@RdfExternalURI(inicioURI= "/callejero/via/", finURI="via")	
	private String via;
	
	@ApiModelProperty(value = "Calle del portal. Ejemplo: CALLE DE BRAVO MURILLO NUMERO 1")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;	
	
	@ApiModelProperty(value = "Código postal del portal. Ejemplo: 28015")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;

	@ApiModelProperty(value = "Coordenada X del portal. Ejemplo: 440242.56")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="xETRS89", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	@ApiModelProperty(value = "Coordenada Y del portal. Ejemplo: 4476174.2")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="yETRS89", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Identificador del municipio del portal. Ejemplo: 28079")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio del portal. Ejemplo: Madrid")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del barrio del portal. Ejemplo: 280796062")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="barrioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")	
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId", urifyLevel = 1)	
	private String barrioId;
	
	@ApiModelProperty(value = "Identificador del distrito del portal. Ejemplo: 28079606")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Identificador de la provincia del portal. Ejemplo: Madrid")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="provinciaId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "provincia")
	@RdfExternalURI(inicioURI="/territorio/provincia/",finURI="provinciaId" ,urifyLevel = 1)	
	private String provinciaId;
	
	@ApiModelProperty(value = "Identificador de la comunidad autónoma del portal. Ejemplo: Comunidad-Madrid")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="autonomiaId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "autonomia")		
	@RdfExternalURI(inicioURI="/territorio/autonomia/",finURI="autonomiaId" , urifyLevel = 1)	
	private String autonomiaId;
	
	private Double distance;

	public CallejeroPortal()
	{
	}


	public CallejeroPortal(CallejeroPortal copia)
	{
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.streetAddress = copia.streetAddress;
		this.postalCode = copia.postalCode;
		this.x = copia.x;
		this.y = copia.y;
		this.barrioId = copia.barrioId;
		this.distritoId = copia.distritoId;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.provinciaId = copia.provinciaId;
		this.autonomiaId = copia.autonomiaId;
		this.via=copia.via;
	}

	public CallejeroPortal(CallejeroPortal copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY))
		{
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR))
		{
			this.id = copia.id;
		}
		if (attributesToSet.contains("streetAddress"))
		{
			this.streetAddress = copia.streetAddress;
		}
		if (attributesToSet.contains("postalCode"))
		{
			this.postalCode = copia.postalCode;
		}
		if (attributesToSet.contains("postalCode"))
		{
			this.postalCode = copia.postalCode;
		}
		if (attributesToSet.contains("xETRS89"))
		{
			this.x = copia.x;
		}
		if (attributesToSet.contains("yETRS89"))
		{
			this.y = copia.y;
		}
		if (attributesToSet.contains("barrioId"))
		{
			this.barrioId = copia.barrioId;
		}
		if (attributesToSet.contains("distritoId"))
		{
			this.distritoId = copia.distritoId;
		}
		if (attributesToSet.contains("municipioId"))
		{
			this.municipioId = copia.municipioId;
		}
		if (attributesToSet.contains("municipioTitle"))
		{
			this.municipioTitle = copia.municipioTitle;
		}
		if (attributesToSet.contains("provinciaId"))
		{
			this.provinciaId = copia.provinciaId;
		}
		if (attributesToSet.contains("autonomiaId"))
		{
			this.autonomiaId = copia.autonomiaId;
		}
		if (attributesToSet.contains("via"))
		{
			this.via = copia.via;
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
	
	

	@Column(name = "via", nullable = true, length = 50)
	public String getVia()
	{
		return via;
	}

	public void setVia(String via)
	{
		this.via = via;
	}

	@Column(name = "street_address", nullable = false, length = 200)
	public String getStreetAddress()
	{
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", nullable = false, length = 10)
	public String getPostalCode()
	{
		return this.postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89", precision = 13, scale = 5)
	public BigDecimal getX()
	{
		return this.x;
	}

	public void setX(BigDecimal x)
	{
		this.x = x;
	}

	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89", precision = 13, scale = 5)
	public BigDecimal getY()
	{
		return this.y;
	}

	public void setY(BigDecimal y)
	{
		this.y = y;
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


	@Column(name = "barrio_id", length = 50)
	public String getBarrioId()
	{
		return this.barrioId;
	}

	public void setBarrioId(String barrio)
	{
		this.barrioId = barrio;
	}

	@Column(name = "distrito_id", length = 50)
	public String getDistritoId()
	{
		return this.distritoId;
	}

	public void setDistritoId(String distrito)
	{
		this.distritoId = distrito;
	}

	@Column(name = "municipio_id", length = 50)
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

	@Column(name = "provincia_id", length = 50)
	public String getProvinciaId()
	{
		return this.provinciaId;
	}

	public void setProvinciaId(String provincia)
	{
		this.provinciaId = provincia;
	}

	@Column(name = "autonomia_id", length = 50)
	public String getAutonomiaId()
	{
		return this.autonomiaId;
	}

	public void setAutonomiaId(String autonomia)
	{
		this.autonomiaId = autonomia;
	}
	
	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {		
		return  (T) cloneClass( (CallejeroPortal) copia,listado) ;
	}

	public CallejeroPortal cloneClass(CallejeroPortal copia, List<String> attributesToSet) {		
		CallejeroPortal obj = new CallejeroPortal(copia,attributesToSet);
		return obj;	
	}



	@Override
	public List<String> validate()
	{
		List<String> result= new ArrayList<String>();
	
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}
		
		if (!Util.validValue(this.getVia())) {
			result.add("via is not valid [via:"+this.getVia()+"]");
		}
		
		if (!Util.validValue(this.getStreetAddress())) {
			result.add("streetAddress is not valid [streetAddress:"+this.getStreetAddress()+"]");
		}
		
		if (!Util.validValue(this.getPostalCode())) {
			result.add("postalCode is not valid [postalCode:"+this.getPostalCode()+"]");
		}
		
		return result;
	}


	@Override
	public Map<String, String> prefixes()
	{
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);		
		prefixes.put(Context.FOAF, Context.FOAF_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		return prefixes;
	}


	@Override
	public String toString()
	{
		return "CallejeroPortal [ikey=" + ikey + ", id=" + id + ", streetAddress=" + streetAddress + ", postalCode=" + postalCode 
				+ ", x=" + x + ", y=" + y + ", barrioId=" + barrioId + ", distritoId=" + distritoId + ", municipioId=" + municipioId 
				+ ", municipioTitle=" + municipioTitle + ", provinciaId="
				+ provinciaId + ", autonomiaId=" + autonomiaId + "]";
	}



	
	
	
	

}
