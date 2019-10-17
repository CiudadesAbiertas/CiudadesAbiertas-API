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


import java.math.BigDecimal;
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
@Table(name = "local_comercial_terraza")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCOM, propiedad = "Terraza")
@PathId(value="/localComercial/terraza")
public class Terraza implements java.io.Serializable, RDFModel
{
	@JsonIgnore
	private static final long serialVersionUID = -5961893412810181397L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="numeroMesasAutorizadas")
	@Rdf(contexto = Context.ESCOM, propiedad = "numeroMesasAutorizadas", typeURI=Context.XSD_URI+"int")
	private Integer numeroMesasAutorizadas;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="numeroSillasAutorizadas")
	@Rdf(contexto = Context.ESCOM, propiedad = "numeroSillasAutorizadas", typeURI=Context.XSD_URI+"int")
	private Integer numeroSillasAutorizadas;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "periodoFuncionamiento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "periodoFuncionamiento")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/periodo-funcionamiento/", finURI="periodoFuncionamiento", urifyLevel=2)
	private String periodoFuncionamiento;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "superficie")
	@Rdf(contexto = Context.ESCOM, propiedad = "superficie", typeURI=Context.XSD_URI+"double")
	private BigDecimal superficie;
	
	@CsvBindByPosition(position=6)	
	@CsvBindByName(column="openingHours", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "openingHours")
	private String openingHours;
	
	@CsvBindByPosition(position=7)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	public Terraza()
	{
	}

	public Terraza(String ikey, String id)
	{
		this.ikey = ikey;
		this.id = id;
	}

	public Terraza(String ikey, String id, Integer numeroMesasAutorizadas, Integer numeroSillasAutorizadas, String periodoFuncionamiento, BigDecimal superficie, String horario, String description)
	{
		this.ikey = ikey;
		this.id = id;
		this.numeroMesasAutorizadas = numeroMesasAutorizadas;
		this.numeroSillasAutorizadas = numeroSillasAutorizadas;
		this.periodoFuncionamiento = periodoFuncionamiento;
		this.superficie = superficie;
		this.openingHours = horario;
		this.description = description;
	}

	public Terraza(Terraza lct)
	{
		this.ikey = lct.ikey;
		this.id = lct.id;
		this.numeroMesasAutorizadas = lct.numeroMesasAutorizadas;
		this.numeroSillasAutorizadas = lct.numeroSillasAutorizadas;
		this.periodoFuncionamiento = lct.periodoFuncionamiento;
		this.superficie = lct.superficie;
		this.openingHours = lct.openingHours;
		this.description = lct.description;
	}
	public Terraza(Terraza lct, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = lct.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = lct.id;
		}
		if (attributesToSet.contains("numeroMesasAutorizadas")) {
			this.numeroMesasAutorizadas = lct.numeroMesasAutorizadas;
		}
		if (attributesToSet.contains("numeroSillasAutorizadas")) {
			this.numeroSillasAutorizadas = lct.numeroSillasAutorizadas;
		}
		if (attributesToSet.contains("periodoFuncionamiento")) {
			this.periodoFuncionamiento = lct.periodoFuncionamiento;
		}
		if (attributesToSet.contains("superficie")) {
			this.superficie = lct.superficie;
		}
		if (attributesToSet.contains("horario")) {
			this.openingHours = lct.openingHours;
		}
		if (attributesToSet.contains("description")) {
			this.description = lct.description;
		}
		if (attributesToSet.contains("openingHours")) {
			this.openingHours = lct.openingHours;
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

	@Column(name = "numero_mesas_autorizadas")
	public Integer getNumeroMesasAutorizadas()
	{
		return this.numeroMesasAutorizadas;
	}

	public void setNumeroMesasAutorizadas(Integer numeroMesasAutorizadas)
	{
		this.numeroMesasAutorizadas = numeroMesasAutorizadas;
	}

	@Column(name = "numero_sillas_autorizadas")
	public Integer getNumeroSillasAutorizadas()
	{
		return this.numeroSillasAutorizadas;
	}

	public void setNumeroSillasAutorizadas(Integer numeroSillasAutorizadas)
	{
		this.numeroSillasAutorizadas = numeroSillasAutorizadas;
	}

	@Column(name = "periodo_funcionamiento", length = 400)
	public String getPeriodoFuncionamiento()
	{
		return this.periodoFuncionamiento;
	}

	public void setPeriodoFuncionamiento(String periodoFuncionamiento)
	{
		this.periodoFuncionamiento = periodoFuncionamiento;
	}

	@Column(name = "superficie", precision = 12)
	public BigDecimal getSuperficie()
	{
		return this.superficie;
	}

	public void setSuperficie(BigDecimal superficie)
	{
		this.superficie = superficie;
	}

	@Column(name = "opening_hours", length = 400)
	public String getOpeningHours()
	{
		return this.openingHours;
	}

	public void setOpeningHours(String openingHours)
	{
		this.openingHours = openingHours;
	}

	@Column(name = "description", length = 400)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	public Map<String, String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		
		prefixes.put(Context.ESCOM, Context.ESCOM_URI);		
		
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "Terraza [ikey=" + ikey + ", id=" + id + ", numeroMesasAutorizadas=" + numeroMesasAutorizadas
				+ ", numeroSillasAutorizadas=" + numeroSillasAutorizadas + ", periodoFuncionamiento="
				+ periodoFuncionamiento + ", superficie=" + superficie + ", openingHours=" + openingHours
				+ ", description=" + description + "]";
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {

		return (T) cloneClass((Terraza) copia, listado);
	}

// Constructor copia con lista de attributos a settear
	public Terraza cloneClass(Terraza lct, List<String> attributesToSet) {

		Terraza obj = new Terraza(lct,attributesToSet);	

		return obj;

	}
	
	//Terraza
	// TODO valiaciones de equipamientos ver si es posible realizar mediante
	// anotaciones en model el saber los campos obligatorios
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getOpeningHours())) {
			result.add("OpeningHours is not valid [Id:" + this.getOpeningHours() + "]");
		}

		return result;
	}

}
