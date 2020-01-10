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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfNode;
import org.ciudadesabiertas.dataset.utils.Territorio;
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

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */


@Entity
@ApiModel
@Table(name = "territorio_municipio")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESADM, propiedad = "Municipio")
@PathId(value="/territorio/municipio")
public class Municipio implements java.io.Serializable, RDFModel, Territorio {

	@JsonIgnore
	private static final long serialVersionUID = 6935850887763616111L;


	@JsonIgnore
	private String ikey;
	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)	
	private String id;
	
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;		
		
	@JsonIgnore
	private Provincia provinciaObject;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="provincia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "provincia")
	@RdfExternalURI(inicioURI="/territorio/provincia/", finURI="provincia", urifyLevel=1)
	private String provincia;
	
	
	@Transient
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="autonomia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "autonomia")
	@RdfExternalURI(inicioURI="/territorio/autonomia/", finURI="autonomia", urifyLevel=1)
	private String autonomia;
	
	@Transient
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="pais", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "pais")
	@RdfExternalURI(inicioURI="/territorio/pais/", finURI="pais", urifyLevel=1)
	private String pais;

	@Transient
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="hasGeometry", format=Constants.STRING_FORMAT)
	@RdfNode(inicioURI="/territorio/municipio/",valorURI="id", finURI="/geometry", 
			propiedad = Context.GEOSPARQL_URI+"hasGeometry", 
			nodoType=Context.SF_URI+"MultiPolygon", 
			nodoPropiedad = Context.GEOSPARQL_URI+"asWKT",
			nodoPropiedadTipo = Context.GEOSPARQL_URI+"wktLiteral")
	private Object hasGeometry;
	
	
	@JsonIgnore
	private Pais paisObject;
	
	@JsonIgnore
	private Autonomia autonomiaObject;

	public Municipio() {
	}
	
	public Municipio(Municipio copia) {
		this.ikey = copia.ikey;		
		this.provinciaObject = copia.provinciaObject;	
		this.id = copia.id;
		this.title = copia.title;
		this.provincia = copia.provincia;
		this.autonomia = copia.autonomia;
		this.pais = copia.pais;
	}

	public Municipio(String ikey, Provincia provinciaObject, String id, String title) {
		this.ikey = ikey;

		this.provinciaObject = provinciaObject;
		this.id = id;
		this.title = title;
	}

	

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "provincia", nullable = false)
	public Provincia getProvinciaObject() {
		return this.provinciaObject;
	}

	public void setProvinciaObject(Provincia provinciaObject) {
		this.provinciaObject = provinciaObject;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public Object getHasGeometry() {
		return hasGeometry;
	}
	
	@Transient
	public void setHasGeometry(Object hasGeometry) {
		 this.hasGeometry=hasGeometry;		 
	}
	
	
	@Transient
	public String getProvincia() {
		return provincia;
	}

	@Transient
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Transient
	public String getAutonomia() {
		return autonomia;
	}

	@Transient
	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	@Transient
	public String getPais() {
		return pais;
	}

	@Transient
	public void setPais(String pais) {
		this.pais = pais;
	}

	public Municipio(Municipio copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
		}
		
		if (attributesToSet.contains("provincia")) {
			this.provincia = copia.provincia;		
		}		
		
		if (attributesToSet.contains("autonomia")) {
			this.autonomia = copia.autonomia;		
		}		
		
		if (attributesToSet.contains("pais")) {
			this.pais = copia.pais;		
		}
		
		if (attributesToSet.contains("provinciaObject")) {
			this.provinciaObject = copia.provinciaObject;		
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((Municipio) copia, listado);
	}

	public Municipio cloneClass(Municipio copia, List<String> attributesToSet) {

		Municipio obj = new Municipio(copia,attributesToSet);		

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
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		return result;
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		prefixes.put(Context.ESADM, Context.ESADM_URI);		
		//prefixes.put(Context.GEO, Context.GEO_URI);	
		//prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);		
		//prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.SF, Context.SF_URI);
		prefixes.put(Context.GEOSPARQL, Context.GEOSPARQL_URI);		
		return prefixes;
	}

	@Override
	public String toString() {
		return "Municipio [ikey=" + ikey + ", id=" + id + ", title=" + title + ", provincia=" + provincia
				+ ", hasGeometry=" + hasGeometry + "]";
	}

	@Override
	public void showFieldTerritorio() {
		if (this.provinciaObject!=null) {
			this.provincia = this.provinciaObject.getTitle();
			if (this.provinciaObject.getAutonomiaObject()!=null) {
				this.autonomia = this.provinciaObject.getAutonomiaObject().getTitle();
				if (this.provinciaObject.getAutonomiaObject().getPaisObject()!=null) {
					this.pais = this.provinciaObject.getAutonomiaObject().getPaisObject().getTitle();
				}
			}
		}
	}
	
	//Metodos añadidos para las busqueds genericas
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pais", nullable = false)
	public Pais getPaisObject() {
		return this.paisObject;
	}

	public void setPaisObject(Pais paisObject) {
		this.paisObject = paisObject;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autonomia", nullable = false)
	public Autonomia getAutonomiaObject() {
		return this.autonomiaObject;
	}

	public void setAutonomiaObject(Autonomia autonomiaObject) {
		this.autonomiaObject = autonomiaObject;
	}
	
	

}
