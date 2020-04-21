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
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "bici_usuario")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "Usuario")
@PathId(value="/bicicleta-publica/usuario")
public class BicicletaPublicaUsuario  implements java.io.Serializable, RDFModel {
	
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
	@CsvBindByName(column="anioNacimiento")
	@Rdf(contexto = Context.ESBICI, propiedad = "anioNacimiento", typeURI=Context.XSD_URI+"int")
	private String anioNacimiento;	
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="sex")
	@Rdf(contexto = Context.SDMXTDIMENSION, propiedad = "sex")
	@RdfExternalURI(inicioURI="http://purl.org/linked-data/sdmx/2009/code#sex-", finURI="sex", urifyLevel=1)
	private String sex;	
				

	public BicicletaPublicaUsuario()
	{
	}

	public BicicletaPublicaUsuario(BicicletaPublicaUsuario copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.anioNacimiento = copia.anioNacimiento;
		this.sex = copia.sex;

	}
	
	public BicicletaPublicaUsuario(BicicletaPublicaUsuario copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("anioNacimiento")) {
			this.anioNacimiento = copia.anioNacimiento;		
		}
		if (attributesToSet.contains("sex")) {
			this.sex = copia.sex;
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
	
	@JsonProperty("anioNacimiento")
	@Column(name = "anio_nacimiento", length = 200)
	public String getAnioNacimiento()
	{
		return this.anioNacimiento;
	}

	public void setAnioNacimiento(String anioNacimiento)
	{
		this.anioNacimiento = anioNacimiento;
	}

	@Column(name = "sex", length = 200)
	public String getSex()
	{
		return sex;
	}
	
	public void setSex(String sex)
	{
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "BicicletaPublicaUsuario [ikey=" + ikey + ", id=" + id + ", anioNacimiento=" + anioNacimiento + ", sex="
				+ sex + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);			
		prefixes.put(Context.ESBICI, Context.ESBICI_URI);
		prefixes.put(Context.SDMXTDIMENSION, Context.SDMXDIMENSION_URI);
		
		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaUsuario) copia, listado);
	}
	
	public BicicletaPublicaUsuario cloneClass(BicicletaPublicaUsuario copia, List<String> attributesToSet) {

		BicicletaPublicaUsuario obj = new BicicletaPublicaUsuario(copia,attributesToSet);		

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
