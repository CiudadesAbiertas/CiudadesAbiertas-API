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

import java.util.Date;
import java.util.ArrayList;
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
 * @author Oscar Corcho (UPM, Localidata)
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "trafico_proper_interval")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TIME, propiedad = "ProperInterval")
@PathId(value="/trafico/proper-interval")
public class TraficoProperInterval  implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAPHETIM01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Comienzo de una entidad temporal. Ejemplo: 2020-03-31 23:00:00")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="hasBeginning")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.TIME, propiedad = "inXSDDateTimeStamp" ,typeURI=Context.XSD_URI+"dateTimeStamp")
	@RdfBlankNode(tipo=Context.TIME_URI+"Instant", propiedad=Context.TIME_URI+"hasBeginning", nodoId="hasBeginning")
	private Date hasBeginning;
	
	@ApiModelProperty(value = "Final de una entidad temporal. Ejemplo: 2020-03-31 23:01:00")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="hasEnd")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.TIME, propiedad = "inXSDDateTimeStamp" ,typeURI=Context.XSD_URI+"dateTimeStamp")
	@RdfBlankNode(tipo=Context.TIME_URI+"Instant", propiedad=Context.TIME_URI+"hasEnd", nodoId="hasEnd")
	private Date hasEnd;

	public TraficoProperInterval()
	{
	}

	public TraficoProperInterval(TraficoProperInterval copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.hasBeginning = copia.hasBeginning;
		this.hasEnd = copia.hasEnd;
	}


	public TraficoProperInterval(TraficoProperInterval copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("hasBeginning")) {
			this.hasBeginning = copia.hasBeginning;		
		}
		if (attributesToSet.contains("hasEnd")) {
			this.hasEnd = copia.hasEnd;		
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "has_beginning", length = 19)
	public Date getHasBeginning() {
		return hasBeginning;
	}

	public void setHasBeginning(Date hasBeginning) {
		this.hasBeginning = hasBeginning;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "has_end", length = 19)
	public Date getHasEnd() {
		return hasEnd;
	}

	public void setHasEnd(Date hasEnd) {
		this.hasEnd = hasEnd;
	}

	@Override
	public String toString() {
		return "TraficoProperInterval [ikey=" + ikey + ", id=" + id + ", hasBeginning=" + hasBeginning + ", hasEnd="
				+ hasEnd + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();	
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.TIME, Context.TIME_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((TraficoProperInterval) copia, listado);
	}
	
	public TraficoProperInterval cloneClass(TraficoProperInterval copia, List<String> attributesToSet) {

		TraficoProperInterval obj = new TraficoProperInterval(copia,attributesToSet);		

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

