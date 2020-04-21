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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@Table(name = "local_comercial_agrupacion")@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCOM, propiedad = "AgrupacionComercial")
@PathId(value="/local-comercial/agrupacion-comercial")
public class AgrupacionComercial implements java.io.Serializable, RDFModel
{

	@JsonIgnore
	private static final long serialVersionUID = -7859043075442172899L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
		
	@ApiModelProperty(value = "Identificador de la agrupación comercial. Ejemplo: 99000214")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la agrupación comercial. Ejemplo: GALERIA DE ALIMENTACION EL CARMEN")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Tipo de agrupación comercial. Ejemplo: galeria de alimentacion")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="tipoAgrupacionComercial", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "tipoAgrupacionComercial")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/tipo-agrupacion/", finURI="tipoAgrupacionComercial", urifyLevel=1)
	private String tipoAgrupacionComercial;

	public AgrupacionComercial()
	{
	}

	public AgrupacionComercial(String ikey, String id)
	{
		this.ikey = ikey;
		this.id = id;
	}

	public AgrupacionComercial(AgrupacionComercial ac)
	{
		this.ikey = ac.ikey;
		this.id = ac.id;
		this.title = ac.title;
		this.tipoAgrupacionComercial = ac.tipoAgrupacionComercial;
	}
	
	
	public AgrupacionComercial(AgrupacionComercial ac, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = ac.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = ac.id;
		}
		if (attributesToSet.contains("title")) {
			this.title = ac.title;
		}
		if (attributesToSet.contains("tipoAgrupacionComercial")) {
			this.tipoAgrupacionComercial = ac.tipoAgrupacionComercial;
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

	@Column(name = "tipo_agrupacion_comercial", length = 400)
	public String getTipoAgrupacionComercial()
	{
		return this.tipoAgrupacionComercial;
	}

	public void setTipoAgrupacionComercial(String tipoAgrupacionComercial)
	{
		this.tipoAgrupacionComercial = tipoAgrupacionComercial;
	}


	@Override
	public Map<String, String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESCOM, Context.ESCOM_URI);
		return prefixes;
	}

	@Override
	public String toString()
	{
		return "AgrupacionComercial [ikey=" + ikey + ", id=" + id + ", title=" + title + ", tipoAgrupacionComercial=" + tipoAgrupacionComercial + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (AgrupacionComercial) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public AgrupacionComercial cloneClass(AgrupacionComercial copia, List<String> attributesToSet) {
		
		AgrupacionComercial obj = new AgrupacionComercial(copia,attributesToSet);
		
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
			result.add("title is not valid [Nombre:" + this.getTitle() + "]");
		}

		if (!Util.validValue(this.getTipoAgrupacionComercial())) {
			result.add("tipoAgrupacionComercial is not valid [Latitud:" + this.getTipoAgrupacionComercial() + "]");
		}

		return result;
	}
	
}
