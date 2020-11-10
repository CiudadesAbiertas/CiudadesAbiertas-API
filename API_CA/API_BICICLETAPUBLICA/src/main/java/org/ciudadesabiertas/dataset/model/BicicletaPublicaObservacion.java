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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bici_observacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "ObservacionEstacion")
@PathId(value="/bicicleta-publica/observacion")
public class BicicletaPublicaObservacion  implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="madeBySensor", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "madeBySensor")
	@RdfExternalURI(inicioURI="/bicicleta-publica/estacion/",finURI="madeBySensor", urifyLevel = 1)
	private String madeBySensor;	
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="observedPropertyTitle")
	private String observedPropertyTitle;	
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="observedPropertyId")
	@Rdf(contexto = Context.SOSA, propiedad = "observedProperty")
	@RdfExternalURI(inicioURI= "http://vocab.ciudadesabiertas.es/def/transporte/bicicleta-publica#", finURI="observedPropertyId")
	private String observedPropertyId;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="resultTime")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "resultTime",typeURI=Context.XSD_URI+"dateTime" )
	private Date resultTime;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "hasSimpleResult")
	@Rdf(contexto = Context.SOSA, propiedad = "hasSimpleResult",typeURI=Context.XSD_URI+"int" )
	private BigDecimal hasSimpleResult;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName (column = "quality")
	@Rdf(contexto = Context.DUL, propiedad = "quality")	
	private String quality;
	
	public BicicletaPublicaObservacion()
	{
	}
	
	

	

	public BicicletaPublicaObservacion(BicicletaPublicaObservacion copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.madeBySensor = copia.madeBySensor;
		this.observedPropertyTitle = copia.observedPropertyTitle;
		this.observedPropertyId = copia.observedPropertyId;
		this.resultTime = copia.resultTime;
		this.hasSimpleResult = copia.hasSimpleResult;
		this.quality = copia.quality;

	}

	
	public BicicletaPublicaObservacion(BicicletaPublicaObservacion copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("madeBySensor")) {
			this.madeBySensor = copia.madeBySensor;		
		}
		if (attributesToSet.contains("observedPropertyTitle")) {
			this.observedPropertyTitle = copia.observedPropertyTitle;
		}
		if (attributesToSet.contains("observedPropertyId")) {
			this.observedPropertyId = copia.observedPropertyId;
		}
		if (attributesToSet.contains("resultTime")) {
			this.resultTime = copia.resultTime;
		}
		if (attributesToSet.contains("hasSimpleResult")) {
			this.hasSimpleResult = copia.hasSimpleResult;
		}
		if (attributesToSet.contains("quality")) {
			this.quality = copia.quality;
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

	@Column(name = "made_by_sensor", length = 50)
	public String getMadeBySensor()
	{
		return this.madeBySensor;
	}

	public void setMadeBySensor(String madeBySensor)
	{
		this.madeBySensor = madeBySensor;
	}

	@Column(name = "observed_property_title", length = 100)
	public String getObservedPropertyTitle()
	{
		return observedPropertyTitle;
	}

	
	public void setObservedPropertyTitle(String observedPropertyTitle)
	{
		this.observedPropertyTitle = observedPropertyTitle;
	}
	
	@Column(name = "observed_property_id", length = 100)
	public String getObservedPropertyId()
	{
		return observedPropertyId;
	}

	
	public void setObservedPropertyId(String observedPropertyId)
	{
		this.observedPropertyId = observedPropertyId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "result_time", nullable = false, length = 19)
	public Date getResultTime()
	{
		return this.resultTime;
	}

	public void setResultTime(Date resultTime)
	{
		this.resultTime = resultTime;
	}
	
	@Column(name = "has_simple_result", nullable = false, precision = 12)
	public BigDecimal getHasSimpleResult()
	{
		return this.hasSimpleResult;
	}

	public void setHasSimpleResult(BigDecimal hasSimpleResult)
	{
		this.hasSimpleResult = hasSimpleResult;
	}

	@Column(name = "quality", length = 50)
	public String getQuality()
	{
		return this.quality;
	}

	public void setQuality(String quality)
	{
		this.quality = quality;
	}
	
	

	@Override
	public String toString() {
		return "BicicletaPublicaObservacion [ikey=" + ikey + ", id=" + id + ", madeBySensor=" + madeBySensor
				+ ", observedPropertyTitle=" + observedPropertyTitle + ", observedPropertyId=" + observedPropertyId
				+ ", resultTime=" + resultTime + ", hasSimpleResult=" + hasSimpleResult + ", quality=" + quality + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SOSA, Context.SOSA_URI);		
		prefixes.put(Context.DUL, Context.DUL_URI);	
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaObservacion) copia, listado);
	}
	
	public BicicletaPublicaObservacion cloneClass(BicicletaPublicaObservacion copia, List<String> attributesToSet) {

		BicicletaPublicaObservacion obj = new BicicletaPublicaObservacion(copia,attributesToSet);		

		return obj;

	}

	@Override
	public List<String> validate()
	{
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getMadeBySensor())) {
			result.add("MadeBySensor is not valid [Id:"+this.getMadeBySensor()+"]");
		}		
		
		return result;
	}

}
