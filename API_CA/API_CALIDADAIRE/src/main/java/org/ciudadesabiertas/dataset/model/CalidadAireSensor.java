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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ciudadesAbiertas.rdfGeneratorZ.PathIdComplex;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.EnumTipoEquipamiento;
import org.ciudadesabiertas.utils.Util;
import org.ciudadesabiertas.dataset.util.CalidadAireConstants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import io.swagger.annotations.ApiModel;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "calidad_aire_sensor")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@RdfMultiple({@Rdf(contexto = Context.SOSA, propiedad = "Sensor"),@Rdf(contexto = Context.ESAIR, propiedad = "AirQualitySensor")})
@PathId(value= CalidadAireConstants.sensorIdPath)
public class CalidadAireSensor implements java.io.Serializable, RDFModel, PathIdComplex
{	
	@JsonIgnore
	private static final long serialVersionUID = 3834643563854471365L;

	@JsonIgnore
	private String ikey;
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column="id", format=Constants.STRING_FORMAT)
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="isHostedBy", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "isHostedBy")
	@RdfExternalURI(inicioURI= "/calidadAire/estacion/", finURI="isHostedBy")
	private String isHostedBy;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="observesTitle", format=Constants.STRING_FORMAT)	
	private String observesTitle;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="observesId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "observes")
	@RdfExternalURI(inicioURI= Context.ESAIR_URI, finURI="observesId")
	private String observesId;
	

	public CalidadAireSensor()
	{
	}	

	public CalidadAireSensor(CalidadAireSensor copia)
	{
		super();
		this.ikey = copia.ikey;
		this.id =  copia.id;
		this.isHostedBy = copia.isHostedBy;
		this.observesTitle = copia.observesTitle;
		this.observesId = copia.observesId;
	}


	public CalidadAireSensor(CalidadAireSensor copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("isHostedBy")) {
			this.isHostedBy = copia.isHostedBy;		
		}
		
		if (attributesToSet.contains("observesId")) {
			this.observesId = copia.observesId;	
		}
		
		if (attributesToSet.contains("observesTitle")) {
			this.observesTitle = copia.observesTitle;	
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
		return id;
	}

	
	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "is_hosted_by", nullable = false, length = 50)
	public String getIsHostedBy()
	{
		return this.isHostedBy;
	}

	public void setIsHostedBy(String isHostedBy)
	{
		this.isHostedBy = isHostedBy;
	}

	@Column(name = "observes_title", nullable = false, length = 100)
	public String getObservesTitle()
	{
		return this.observesTitle;
	}

	public void setObservesTitle(String observesTitle)
	{
		this.observesTitle = observesTitle;
	}

	@Column(name = "observes_id", nullable = false, length = 100)
	public String getObservesId()
	{
		return this.observesId;
	}

	public void setObservesId(String observesId)
	{
		this.observesId = observesId;
	}
	
	
	


	@Override
	public String toString()
	{
		return "CalidadAireSensor [ikey=" + ikey + ", isHostedBy=" + isHostedBy + ", observesTitle=" + observesTitle + ", observesId=" + observesId + "]";
	}



	@Override
	public Map<String, String> prefixes()
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (CalidadAireSensor) copia,listado) ;
	}
	
	
	
	public CalidadAireSensor cloneClass (CalidadAireSensor copia, List<String> attributesToSet)
	{
		
		CalidadAireSensor obj= new CalidadAireSensor(copia,attributesToSet);		
		
		return obj;
		
	}
	
	public  List<String> validate() {
		List<String> result= new ArrayList<String>();
		
		if (!Util.validValue(this.getId())) {
			result.add("id is not valid [id:"+this.getId()+"]");
		}
			
		
		if (!Util.validValue(this.getIsHostedBy())) {
			result.add("IsHostedBy is not valid [IsHostedBy:"+this.getIsHostedBy()+"]");
		}
								
		if (!Util.validValue(this.getObservesTitle())) {
			result.add("ObservesTitle is not valid [getObservesTitle:"+this.getObservesTitle()+"]");
		}
		
		if (!Util.validValue(this.getObservesId())) {
			result.add("ObservesId is not valid [getObservesId:"+this.getObservesId()+"]");
		}				
		
		return result;
	}
	
	
	public String obtainURLPath()
	{
		String path=CalidadAireConstants.sensorIdPath;
		String host=this.getIsHostedBy();
		String observesId=this.getObservesId();
		
		path=path.replace("{isHostedBy}", host);
		path=path.replace("{observesId}", observesId);
		
		return path;
	}

}
