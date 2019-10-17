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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
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
@Table(name = "calidad_aire_observacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@RdfMultiple({@Rdf(contexto = Context.SOSA, propiedad = "Observation"),@Rdf(contexto = Context.ESAIR, propiedad = "AirQualityObservation")})
@PathId(value="/calidadAire/observacion")
public class CalidadAireObservacion implements java.io.Serializable, RDFModel
{	
	
	@JsonIgnore
	private static final long serialVersionUID = -6318703025584174370L;
	
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
	@RdfExternalURI(inicioURI= "/calidadAire/estacion/", finURI="madeBySensor")
	private String madeBySensor;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="observedPropertyTitle", format=Constants.STRING_FORMAT)	
	private String observedPropertyTitle;	

	@CsvBindByPosition(position=4)
	@CsvBindByName(column="observedPropertyId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "observedProperty")
	@RdfExternalURI(inicioURI= Context.SOSA_URI, finURI="observedPropertyId")
	private String observedPropertyId;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="resultTime", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "resultTime",typeURI=Context.XSD_URI+"dateTime" )
	private Date resultTime;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "hasDataValue")
	@Rdf(contexto = Context.DUL, propiedad = "hasDataValue",typeURI=Context.XSD_URI+"float" )
	@RdfBlankNode(tipo=Context.SOSA_URI+"Result", propiedad=Context.SOSA_URI+"hasResult", nodoId="result")
	private BigDecimal hasDataValue;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName (column = "quality")
	@Rdf(contexto = Context.DUL, propiedad = "quality")	
	private String quality;

	public CalidadAireObservacion()
	{
	}
	
	

	public CalidadAireObservacion(CalidadAireObservacion copia)
	{
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.madeBySensor = copia.madeBySensor;
		this.observedPropertyTitle = copia.observedPropertyTitle;
		this.observedPropertyId = copia.observedPropertyId;
		this.resultTime = copia.resultTime;
		this.hasDataValue = copia.hasDataValue;
		this.quality = copia.quality;
	}



	public CalidadAireObservacion(CalidadAireObservacion copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("hasDataValue")) {
			this.hasDataValue = copia.hasDataValue;
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

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "made_by_sensor", nullable = false, length = 50)
	public String getMadeBySensor()
	{
		return this.madeBySensor;
	}

	public void setMadeBySensor(String madeBySensor)
	{
		this.madeBySensor = madeBySensor;
	}

	@Column(name = "observed_property_title", nullable = false, length = 100)
	public String getObservedPropertyTitle()
	{
		return this.observedPropertyTitle;
	}

	public void setObservedPropertyTitle(String observedPropertyTitle)
	{
		this.observedPropertyTitle = observedPropertyTitle;
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

	@Column(name = "has_data_value", nullable = false, precision = 12)
	public BigDecimal getHasDataValue()
	{
		return this.hasDataValue;
	}

	public void setHasDataValue(BigDecimal hasDataValue)
	{
		this.hasDataValue = hasDataValue;
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
	
	
	
	@Column(name = "observed_property_id", nullable = false, length = 100)
	public String getObservedPropertyId()
	{
		return observedPropertyId;
	}



	public void setObservedPropertyId(String observedPropertyId)
	{
		this.observedPropertyId = observedPropertyId;
	}



	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.SOSA, Context.SOSA_URI);		
		prefixes.put(Context.DUL, Context.DUL_URI);
		
		return prefixes;
	}



	@Override
	public String toString()
	{
		return "CalidadAireObservacion [ikey=" + ikey + ", id=" + id + ", madeBySensor=" + madeBySensor + ", observedPropertyTitle=" + observedPropertyTitle + ", resultTime=" + resultTime + ", hasDataValue=" + hasDataValue + ", quality=" + quality + "]";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (CalidadAireObservacion) copia,listado) ;
	}
	
	
	
	public CalidadAireObservacion cloneClass (CalidadAireObservacion copia, List<String> attributesToSet)
	{
		
		CalidadAireObservacion obj= new CalidadAireObservacion(copia,attributesToSet);		
		
		return obj;
		
	}
	
	
	public  List<String> validate() {
		List<String> result= new ArrayList<String>();
		

		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
		
		if (!Util.validValue(this.getMadeBySensor())) {
			result.add("MadeBySensor is not valid [Id:"+this.getMadeBySensor()+"]");
		}
								
		if (!Util.validValue(this.getObservedPropertyTitle())) {
			result.add("observedPropertyTitle is not valid [Id:"+this.getObservedPropertyTitle()+"]");
		}
		
		if (!Util.validValue(this.getObservedPropertyId())) {
			result.add("getObservedPropertyId is not valid [Id:"+this.getObservedPropertyId()+"]");
		}
		
		if (!Util.validValue(this.getResultTime())) {
			result.add("resultTime is not valid [Id:"+this.getResultTime()+"]");
		}
		
		if (!Util.validValue(this.getHasDataValue())) {
			result.add("hasDataValue is not valid [Id:"+this.getHasDataValue()+"]");
		}
		
		if (!Util.validValue(this.getQuality())) {
			result.add("quality is not valid [Id:"+this.getQuality()+"]");
		}
		
		return result;
	}


}
