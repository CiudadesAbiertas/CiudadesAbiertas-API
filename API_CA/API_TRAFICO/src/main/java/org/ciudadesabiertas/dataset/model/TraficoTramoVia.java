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
import javax.persistence.Transient;

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
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "trafico_tramo_via")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESTRAF, propiedad = "Tramo")
@PathId(value="/trafico/tramo-via")
public class TraficoTramoVia  implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAFTRAVIA01")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: TRAFTRAM01")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="idTramo", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String idTramo;
	
	@ApiModelProperty(value = "Identificador de la vía. Ejemplo: 496400")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="idVia")
	@Rdf(contexto = Context.ESTRAF, propiedad = "via")
	private String idVia;	
	
	@ApiModelProperty(value = "Nombre de la vía. Ejemplo: BRAVO MURILLO")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="titleVia")
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String titleVia;
	
	@ApiModelProperty(value = "Tipo de vía. Ejemplo: CALLE")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="tipoVia")
	@Rdf(contexto = Context.ESCJR, propiedad = "tipoVia")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/urbanismo-infraestructuras/tipo-via/", finURI="tipoVia")
	private String tipoVia;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
//	@RdfBlankNode(tipo=Context.ESCJR+"Via", propiedad=Context.ESTRAF+"via", nodoId="via")
	private String idViaIsolated;
	

	public TraficoTramoVia()
	{
	}


	public TraficoTramoVia(TraficoTramoVia copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.idTramo = copia.idTramo;
		this.idVia = copia.idVia;
		this.titleVia = copia.titleVia;
		this.tipoVia = copia.tipoVia;
	}

	public TraficoTramoVia(TraficoTramoVia copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("idTramo")) {
			this.idTramo = copia.idTramo;		
		}
		if (attributesToSet.contains("idVia")) {
			this.idVia = copia.idVia;
		}
		if (attributesToSet.contains("titleVia")) {
			this.titleVia = copia.titleVia;
		}
		if (attributesToSet.contains("tipoVia")) {
			this.tipoVia = copia.tipoVia;
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
	
	@Column(name = "id_tramo", nullable = false, length = 50)
	public String getIdTramo() {
		return this.idTramo;
	}

	public void setIdTramo(String idTramo) {
		this.idTramo = idTramo;
	}
	

	@Column(name = "id_via", length = 50)
	public String getIdVia() {
		return this.idVia;
	}

	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}

	@Column(name = "title_via", length = 400)
	public String getTitleVia() {
		return this.titleVia;
	}

	public void setTitleVia(String titleVia) {
		this.titleVia = titleVia;
	}

	@Column(name = "tipo_via", length = 50)
	public String getTipoVia() {
		return this.tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	
	@Transient
	public String getIdViaIsolated() {
		return idViaIsolated;
	}

	@Transient
	public void setIdViaIsolated(String idViaIsolated) {
		this.idViaIsolated = idViaIsolated;
	}


	@Override
	public String toString() {
		return "TraficoTramoVia [ikey=" + ikey + ", id=" + id + ", idTramo=" + idTramo + ", idVia="
				+ idVia + ", titleVia=" + titleVia + ", tipoVia=" + tipoVia + "]";
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);		
		prefixes.put(Context.ESTRAF, Context.ESTRAF_URI);
		prefixes.put(Context.SOSA, Context.SOSA_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((TraficoTramoVia) copia, listado);
	}
	
	public TraficoTramoVia cloneClass(TraficoTramoVia copia, List<String> attributesToSet) {

		TraficoTramoVia obj = new TraficoTramoVia(copia,attributesToSet);		

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

