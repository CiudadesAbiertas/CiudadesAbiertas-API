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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfTripleExtenal;
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
@Table(name = "convenio_documentacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.SCHEMA, propiedad = "DigitalDocument")
@PathId(value="/convenio/documento")
public class ConvenioDocumento implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 7605654834265875116L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la documentación. Ejemplo: CONVDOC004")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/convenio/convenio/", sujetoFinURI="convenioId", propiedadURI=Context.ORG_URI+"documentacion", objetoInicioURI="/convenio/documento/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Formato de la codificación de la documentación. Ejemplo: image/png")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="encodingFormat", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "encodingFormat")	
	private String encodingFormat;
	
	@ApiModelProperty(value = "Nombre de la documentación. Ejemplo: Imagen del borrador 2")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="name", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "name")	
	private String name;
	
	@ApiModelProperty(value = "URL de la documentación. Ejemplo: https://datos.madrid.es/FwFront/portal_egob/new/img/portal_logo_h.png")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	@IsUri
	private String url;
	
	@ApiModelProperty(value = "Evento relacionado con la documentación. Ejemplo: CONV003")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="convenioId", format=Constants.STRING_FORMAT)
	//@Rdf(contexto = Context.ESAGM, propiedad = "event")
	//@RdfExternalURI(inicioURI="/agendaMunicipal/evento/",finURI="eventId", urifyLevel = 1)
	private String convenioId;

	public ConvenioDocumento() {
	}

	public ConvenioDocumento(String ikey) {
		this.ikey = ikey;
	}

	

	public ConvenioDocumento(String id, String encodingFormat, String name, String url, String convenioId) {
		super();
		this.id = id;
		this.encodingFormat = encodingFormat;
		this.name = name;
		this.url = url;
		this.convenioId = convenioId;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@Column(name = "id", length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "encoding_format", length = 400)
	public String getEncodingFormat() {
		return this.encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	@Column(name = "name", length = 400)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "convenio_id", length = 50)
	public String getConvenioId() {
		return this.convenioId;
	}

	public void setConvenioId(String convenioId) {
		this.convenioId = convenioId;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (ConvenioDocumento) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public ConvenioDocumento cloneClass(ConvenioDocumento copia, List<String> attributesToSet) {
		
		ConvenioDocumento obj = new ConvenioDocumento(copia,attributesToSet);
		
		return obj;

	}
	
	public ConvenioDocumento(ConvenioDocumento copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("encodingFormat")) {
			this.encodingFormat = copia.encodingFormat;
		}
		
		if (attributesToSet.contains("name")) {
			this.name = copia.name;
		}
		
		if (attributesToSet.contains("convenioId")) {
			this.convenioId = copia.convenioId;
		}
		
		if (attributesToSet.contains("url")) {
			this.url = copia.url;
		}
		
	}
	
	
	public ConvenioDocumento(ConvenioDocumento obj) {

		this.ikey = obj.ikey;
		this.id = obj.id;
		this.convenioId = obj.convenioId;
		this.encodingFormat = obj.encodingFormat;
		this.name = obj.name;
		this.url = obj.url;
		
	}
	

	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getEncodingFormat())) {
			result.add("Formato is not valid [encodingFormat:" + this.getEncodingFormat() + "]");
		}
		
		if (!Util.validValue(this.getName())) {
			result.add("NombreDoc is not valid [Name:" + this.getName() + "]");
		}
		
		if (!Util.validValue(this.getConvenioId())) {
			result.add("ConvenioId is not valid [ConvenioId:" + this.getConvenioId() + "]");
		}

		
				

		return result;
	}
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESCONV, Context.ESCONV_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		//prefixes.put(Context.ESCONV, Context.ESCONV_URI);	
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "AgendaMDocumento [id=" + id + ", encodingFormat=" + encodingFormat + ", name=" + name + ", url=" + url
				+ ", convenioId=" + convenioId + "]";
	}

	

	
}
