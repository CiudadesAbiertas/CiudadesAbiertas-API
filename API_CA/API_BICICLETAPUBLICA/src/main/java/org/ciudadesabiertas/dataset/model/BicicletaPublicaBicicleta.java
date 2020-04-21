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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bici_bicicleta")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "Bicicleta")
@PathId(value="/bicicleta-publica/bicicleta")
public class BicicletaPublicaBicicleta  implements java.io.Serializable, RDFModel {
	
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
	@CsvBindByName(column="matricula", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "matricula")
	private String matricula;	
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="estadoBicicleta")
	@Rdf(contexto = Context.ESBICI, propiedad = "estadoBicicleta")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/transporte/bicicleta-publica/tipo-estado-bicicleta/", finURI="estadoBicicleta", urifyLevel=2)
	private String estadoBicicleta;	
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="modeloBicicleta")
	@Rdf(contexto = Context.ESBICI, propiedad = "modeloBicicleta")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/transporte/bicicleta-publica/tipo-modelo-bicicleta/", finURI="modeloBicicleta", urifyLevel=2)
	private String modeloBicicleta;	
				

	public BicicletaPublicaBicicleta()
	{
	}
	
	

	

	public BicicletaPublicaBicicleta(BicicletaPublicaBicicleta copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.matricula = copia.matricula;
		this.estadoBicicleta = copia.estadoBicicleta;
		this.modeloBicicleta = copia.modeloBicicleta;
	}

	
	public BicicletaPublicaBicicleta(BicicletaPublicaBicicleta copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("matricula")) {
			this.matricula = copia.matricula;		
		}
		if (attributesToSet.contains("estadoBicicleta")) {
			this.estadoBicicleta = copia.estadoBicicleta;
		}
		
		if (attributesToSet.contains("modeloBicicleta")) {
			this.modeloBicicleta = copia.modeloBicicleta;
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

	@Column(name = "matricula", length = 200)
	public String getMatricula()
	{
		return this.matricula;
	}

	public void setMatricula(String matricula)
	{
		this.matricula = matricula;
	}

	@Column(name = "estado_bicicleta", length = 200)
	public String getEstadoBicicleta()
	{
		return estadoBicicleta;
	}
	
	public void setEstadoBicicleta(String estadoBicicleta)
	{
		this.estadoBicicleta = estadoBicicleta;
	}
	
	@Column(name = "modelo_bicicleta", length = 200)
	public String getModeloBicicleta()
	{
		return modeloBicicleta;
	}

	public void setModeloBicicleta(String modeloBicicleta)
	{
		this.modeloBicicleta = modeloBicicleta;
	}
	

	

	@Override
	public String toString() {
		return "BicicletaPublicaBicicleta [id=" + id + ", matricula=" + matricula
				+ ", estadoBicicleta=" + estadoBicicleta + ", modeloBicicleta=" + modeloBicicleta + "]";
	}





	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.ESBICI, Context.ESBICI_URI);	
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaBicicleta) copia, listado);
	}
	
	public BicicletaPublicaBicicleta cloneClass(BicicletaPublicaBicicleta copia, List<String> attributesToSet) {

		BicicletaPublicaBicicleta obj = new BicicletaPublicaBicicleta(copia,attributesToSet);		

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
								
//		if (!Util.validValue(this.getTitle())) {
//			result.add("Title is not valid [Title:"+this.getTitle()+"]");
//		}
		
		return result;
	}
	


	
	




}
