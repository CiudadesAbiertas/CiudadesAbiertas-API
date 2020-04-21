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
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Territorio;
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
@Table(name = "territorio_seccion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESADM, propiedad = "SeccionCensal")
@PathId(value="/territorio/seccion-censal")
public class SeccionCensal  implements java.io.Serializable, RDFModel, Territorio {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la sección censal. Ejemplo: 2800601001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la sección censal. Ejemplo: Sección Censal 1")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;	
	
	@JsonIgnore
	private Distrito distritoObject;
	
	@ApiModelProperty(value = "Identificador del distrito al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: 2800601")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: Distrito 1")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI="/territorio/distrito/", finURI="distritoId", urifyLevel=1)
	private String distritoTitle;
	
	@ApiModelProperty(value = "Identificador del municipio al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: 28006")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/", finURI="municipioId", urifyLevel=1)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Cada una de las grandes divisiones de un territorio o Estado, sujeta por lo común a una autoridad administrativa. Ejemplo: Madrid")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="provincia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "provincia")
	@RdfExternalURI(inicioURI="/territorio/provincia/", finURI="provincia", urifyLevel=1)
	private String provincia;
	
	@ApiModelProperty(value = "Autonomía (Comunidad Autónoma o Ciudad Autónoma) a la que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: Comunidad-Madrid")
	@Transient
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="autonomia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "autonomia")
	@RdfExternalURI(inicioURI="/territorio/autonomia/", finURI="autonomia", urifyLevel=1)
	private String autonomia;
	
	@ApiModelProperty(value = "País al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: España")
	@Transient
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="pais", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "pais")
	@RdfExternalURI(inicioURI="/territorio/pais/", finURI="pais", urifyLevel=1)
	private String pais;

	@Transient
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="hasGeometry", format=Constants.STRING_FORMAT)
	@RdfNode(inicioURI="/territorio/seccioncensal/",valorURI="id", finURI="/geometry", 
			propiedad = Context.GEOSPARQL_URI+"hasGeometry", 
			nodoType=Context.SF_URI+"MultiPolygon", 
			nodoPropiedad = Context.GEOSPARQL_URI+"asWKT",
			nodoPropiedadTipo = Context.GEOSPARQL_URI+"wktLiteral")
	private Object hasGeometry;
	
	@JsonIgnore
	private Pais paisObject;
	
	@JsonIgnore
	private Autonomia autonomiaObject;
	
	@JsonIgnore
	private Provincia provinciaObject;
	
	@JsonIgnore
	private Municipio municipioObject;
	
	public SeccionCensal()
	{
	}
	
	
	public SeccionCensal(SeccionCensal copia)
	{		
		this.ikey = copia.ikey;		
		this.distritoObject = copia.distritoObject;	
		this.id = copia.id;
		this.title = copia.title;
		this.distritoId = copia.distritoId;
		this.distritoTitle = copia.distritoTitle;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.provincia = copia.provincia;
		this.autonomia = copia.autonomia;
		this.pais = copia.pais;		
		
		this.paisObject = copia.paisObject;
		this.autonomiaObject = copia.autonomiaObject;
		this.provinciaObject = copia.provinciaObject;
		this.municipioObject = copia.municipioObject;
		this.distritoObject = copia.distritoObject;	
	}

	
	public SeccionCensal(SeccionCensal copia, List<String> attributesToSet)
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
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;		
		}		
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;		
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
		
		if (attributesToSet.contains("distritoTitle")) {
			this.distritoTitle = copia.distritoTitle;		
		}
		
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;		
		}
		
		if (attributesToSet.contains("paisObject")) {
			this.paisObject = copia.paisObject;		
		}
		
		if (attributesToSet.contains("autonomiaObject")) {
			this.autonomiaObject = copia.autonomiaObject;		
		}
		
		if (attributesToSet.contains("provinciaObject")) {
			this.provinciaObject = copia.provinciaObject;		
		}
		
		if (attributesToSet.contains("municipioObject")) {
			this.municipioObject = copia.municipioObject;		
		}
		
		if (attributesToSet.contains("distritoObject")) {
			this.distritoObject = copia.distritoObject;		
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

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "distrito", nullable = false)
	public Distrito getDistritoObject() {
		return this.distritoObject;
	}

	public void setDistritoObject(Distrito distritoObject) {
		this.distritoObject = distritoObject;
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
	
	@Transient
	public String getMunicipioTitle() {
		return municipioTitle;
	}

	@Transient
	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
	}
	
	@Transient
	public String getMunicipioId() {
		return municipioId;
	}

	@Transient
	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}
	
	@Transient
	public String getDistritoTitle() {
		return distritoTitle;
	}

	@Transient
	public void setDistritoTitle(String distritoTitle) {
		this.distritoTitle = distritoTitle;
	}
	
	@Transient
	public String getDistritoId() {
		return distritoId;
	}

	@Transient
	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	@Override
	public String toString() {
		return "SeccionCensal [ikey=" + ikey + ", id=" + id + ", title=" + title + "]";
	}



	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((SeccionCensal) copia, listado);
	}
	
	public SeccionCensal cloneClass(SeccionCensal copia, List<String> attributesToSet) {

		SeccionCensal obj = new SeccionCensal(copia,attributesToSet);		

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
	


	@Override
	public void showFieldTerritorio() {
		if (this.distritoObject!=null) {
			this.distritoTitle = this.distritoObject.getTitle();
			this.distritoId = this.distritoObject.getId();
			if (this.distritoObject.getMunicipioObject()!=null) {
				this.municipioTitle = this.distritoObject.getMunicipioObject().getTitle();
				this.municipioId = this.distritoObject.getMunicipioObject().getId();
				if (this.distritoObject.getMunicipioObject().getProvinciaObject()!=null) {
					this.provincia = this.distritoObject.getMunicipioObject().getProvinciaObject().getTitle();
					if (this.distritoObject.getMunicipioObject().getProvinciaObject().getAutonomiaObject()!=null) {
						this.autonomia = this.distritoObject.getMunicipioObject().getProvinciaObject().getAutonomiaObject().getTitle();
						if (this.distritoObject.getMunicipioObject().getProvinciaObject().getAutonomiaObject().getPaisObject()!=null) {
							this.pais = this.distritoObject.getMunicipioObject().getProvinciaObject().getAutonomiaObject().getPaisObject().getTitle();
						}
					}
				}
			}
		}
	}
	

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);	
//		prefixes.put(Context.GEO, Context.GEO_URI);	
//		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);		
		prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.SF, Context.SF_URI);
		prefixes.put(Context.GEOSPARQL, Context.GEOSPARQL_URI);
		
		return prefixes;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "provincia", nullable = false)
	public Provincia getProvinciaObject() {
		return this.provinciaObject;
	}

	public void setProvinciaObject(Provincia provinciaObject) {
		this.provinciaObject = provinciaObject;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "municipio", nullable = false)
	public Municipio getMunicipioObject() {
		return this.municipioObject;
	}

	public void setMunicipioObject(Municipio municipioObject) {
		this.municipioObject = municipioObject;
	}


}
