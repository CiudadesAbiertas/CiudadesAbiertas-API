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
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "deuda_f_rel_prestamo_entidad")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value="/deuda-publica-financiera/rel-prestamo-entidad")
public class DeudaFinancieraRelPrestamoEntidad implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 7129085031552024191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la entidad de relacción entre las entidades y los prestamos. Ejemplo: RELPRES0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Prestamo  (id) asociada a la relaccion. Ejemplo: ES1234567893")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="prestamoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "prestamo")
	@RdfTripleExtenal(sujetoInicioURI="/deuda-publica-financiera/prestamo/", sujetoFinURI="prestamoId", propiedadURI=Context.ESDEUFINA_URI+"entidadPrestamista", objetoInicioURI="/deuda-publica/organization/", objetoFinURI="entidadPrestamista")
	private String prestamoId;
	
	@ApiModelProperty(value = "Entidad Prestamista (id) asociada al Prestamo. Ejemplo: Q2876002C")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="entidadPrestamista", format=Constants.STRING_FORMAT)	
	private String entidadPrestamista;


	public DeudaFinancieraRelPrestamoEntidad() {
	}

	

	public DeudaFinancieraRelPrestamoEntidad(String id, String prestamoId, String entidadPrestamista) {
		super();
		this.id = id;
		this.prestamoId = prestamoId;
		this.entidadPrestamista = entidadPrestamista;
	}
	
	public DeudaFinancieraRelPrestamoEntidad(DeudaFinancieraRelPrestamoEntidad copia) {
		super();
		this.id = copia.id;
		this.prestamoId = copia.prestamoId;
		this.entidadPrestamista = copia.entidadPrestamista;
	}

	public DeudaFinancieraRelPrestamoEntidad(DeudaFinancieraRelPrestamoEntidad copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("prestamoId")) {
			this.prestamoId = copia.prestamoId;
		}
		
		if (attributesToSet.contains("entidadPrestamista")) {
			this.entidadPrestamista = copia.entidadPrestamista;
		}
	}


	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}


	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name = "prestamo_id", nullable = false, length = 50)
	public String getPrestamoId() {
		return prestamoId;
	}


	public void setPrestamoId(String prestamoId) {
		this.prestamoId = prestamoId;
	}


	@Column(name = "entidad_prestamista", nullable = false, length = 50)
	public String getEntidadPrestamista() {
		return entidadPrestamista;
	}


	public void setEntidadPrestamista(String entidadPrestamista) {
		this.entidadPrestamista = entidadPrestamista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaFinancieraRelPrestamoEntidad) copia, listado);
	}
	
	public DeudaFinancieraRelPrestamoEntidad cloneClass(DeudaFinancieraRelPrestamoEntidad copia, List<String> attributesToSet) {

		DeudaFinancieraRelPrestamoEntidad obj = new DeudaFinancieraRelPrestamoEntidad(copia,attributesToSet);		

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
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getPrestamoId())) {
			result.add("PrestamoId is not valid [PrestamoId:"+this.getPrestamoId()+"]");
		}
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getEntidadPrestamista())) {
			result.add("EntidadPrestamista is not valid [EntidadPrestamista:"+this.getEntidadPrestamista()+"]");
		}
		
		
		return result;
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.TIME, Context.TIME_URI);
		prefixes.put(Context.ESDEUFINA, Context.ESDEUFINA_URI);
		
		
		return prefixes;
	}



	@Override
	public String toString() {
		return "DeudaFinancieraRelPrestamoEntidad [id=" + id + ", prestamoId=" + prestamoId + ", entidadPrestamista="
				+ entidadPrestamista + "]";
	}
	
	

}
