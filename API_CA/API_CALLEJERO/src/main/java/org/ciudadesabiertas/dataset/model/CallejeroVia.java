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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
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
@ApiModel
@Table(name = "callejero_via")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCJR, propiedad = "Via")
@PathId(value = "/callejero/via")
public class CallejeroVia implements java.io.Serializable, RDFModel
{
	@JsonIgnore
	private static final long serialVersionUID = 3254116450110749613L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "title", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "tipoVia", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "tipoVia")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/urbanismo-infraestructuras/tipo-via/", finURI="tipoVia")
	private String tipoVia;
	
	
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "estado", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "estado")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/territorio/tipoEstado/", finURI="estado")
	private String estado;
	
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "municipioTitle", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo = Context.ESADM_URI + "Municipio", propiedad = Context.ESADM_URI + "municipio", nodoId = "municipio")
	private String municipioTitle;

	@CsvBindByPosition(position = 8)
	@CsvBindByName(column = "municipioId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo = Context.ESADM_URI + "Municipio", propiedad = Context.ESADM_URI + "municipio", nodoId = "municipio")
	private String municipioId;


	

	public CallejeroVia()
	{
	}

	public CallejeroVia(CallejeroVia copia)
	{
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.estado=copia.estado;
		this.tipoVia=copia.tipoVia;		
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		
	}

	public CallejeroVia(CallejeroVia copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY))
		{
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR))
		{
			this.id = copia.id;
		}
		if (attributesToSet.contains("title"))
		{
			this.title = copia.title;
		}
		
		if (attributesToSet.contains("estado"))
		{
			this.estado = copia.estado;
		}
		
		if (attributesToSet.contains("tipoVia"))
		{
			this.tipoVia = copia.tipoVia;
		}

		if (attributesToSet.contains("municipioId"))
		{
			this.municipioId = copia.municipioId;
		}

		if (attributesToSet.contains("municipioTitle"))
		{
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

	

	

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}


	@Column(name = "municipio_id", nullable = false, length = 10)
	public String getMunicipioId()
	{
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId)
	{
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", nullable = false, length = 200)
	public String getMunicipioTitle()
	{
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle)
	{
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "tipo_via", nullable = false, length = 50)
	public String getTipoVia()
	{
		return tipoVia;
	}

	public void setTipoVia(String tipoVia)
	{
		this.tipoVia = tipoVia;
	}

	@Column(name = "estado", nullable = false, length = 10)
	public String getEstado()
	{
		return estado;
	}

	public void setEstado(String estado)
	{
		this.estado = estado;
	}

	public Map<String, String> prefixes()
	{
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);		
		prefixes.put(Context.FOAF, Context.FOAF_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		
		
		return prefixes;
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (CallejeroVia) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public CallejeroVia cloneClass(CallejeroVia copia, List<String> attributesToSet) {
		
		CallejeroVia obj = new CallejeroVia(copia,attributesToSet);
		
		
		return obj;
		
	}

	//Organigrama
	public  List<String> validate() {
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		if (!Util.validValue(this.getEstado())) {
			result.add("estado is not valid [estado:"+this.getEstado()+"]");
		}
		
		if (!Util.validValue(this.getTipoVia())) {
			result.add("tipoVia is not valid [tipoVia:"+this.getTipoVia()+"]");
		}
		

		
		return result;
	}

	@Override
	public String toString()
	{
		return "CallejeroVia [ikey=" + ikey + ", id=" + id + ", title=" + title + ", tipoVia=" + tipoVia + ", estado=" + estado + ", municipioTitle=" + municipioTitle + ", municipioId=" + municipioId + "]";
	}

	
	
	
	
}
