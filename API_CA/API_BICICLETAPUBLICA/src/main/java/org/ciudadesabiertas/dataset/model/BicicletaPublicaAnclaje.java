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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bici_anclaje")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "Anclaje")
@PathId(value="/bicicleta-publica/anclaje")
public class BicicletaPublicaAnclaje  implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/bicicleta-publica/estacion/", sujetoFinURI="estacionBicicletaId", propiedadURI=Context.ESBICI_URI+"tieneAsociadoAnclaje", objetoInicioURI="/bicicleta-publica/anclaje/", objetoFinURI="id")
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="estadoAnclaje", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "estadoAnclaje")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/transporte/bicicleta-publica/tipo-estado-anclaje/", finURI="estadoAnclaje", urifyLevel=2)
	private String estadoAnclaje;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="estacionBicicletaId", format=Constants.STRING_FORMAT)
//	@Rdf(contexto = Context.ESBICI, propiedad = "tieneAsociadoAnclaje")	
	private String estacionBicicletaId;
	
				

	public BicicletaPublicaAnclaje()
	{
	}

	public BicicletaPublicaAnclaje(BicicletaPublicaAnclaje copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.estadoAnclaje = copia.estadoAnclaje;
		this.estacionBicicletaId = copia.estacionBicicletaId;
	}

	
	public BicicletaPublicaAnclaje(BicicletaPublicaAnclaje copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("estadoAnclaje")) {
			this.estadoAnclaje = copia.estadoAnclaje;		
		}
		if (attributesToSet.contains("estacionBicicletaId")) {
			this.estacionBicicletaId = copia.estacionBicicletaId;
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

	@Column(name = "estado_anclaje", length = 200)
	public String getEstadoAnclaje() {
		return this.estadoAnclaje;
	}

	public void setEstadoAnclaje(String estadoAnclaje) {
		this.estadoAnclaje = estadoAnclaje;
	}
	
	@Column(name = "estacion_bicicleta_id", length = 50)
	public String getEstacionBicicletaId() {
		return this.estacionBicicletaId;
	}

	public void setEstacionBicicletaId(String estacionBicicletaId) {
		this.estacionBicicletaId = estacionBicicletaId;
	}

	@Override
	public String toString() {
		return "BicicletaPublicaAnclaje [ikey=" + ikey + ", id=" + id + ", estadoAnclaje=" + estadoAnclaje
				+ ", estacionBicicletaId=" + estacionBicicletaId + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESBICI, Context.ESBICI_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaAnclaje) copia, listado);
	}
	
	public BicicletaPublicaAnclaje cloneClass(BicicletaPublicaAnclaje copia, List<String> attributesToSet) {

		BicicletaPublicaAnclaje obj = new BicicletaPublicaAnclaje(copia,attributesToSet);		

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
