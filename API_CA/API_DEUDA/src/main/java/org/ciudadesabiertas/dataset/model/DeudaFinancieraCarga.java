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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
import com.opencsv.bean.CsvDate;

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
@Table(name = "deuda_f_carga_financiera")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUFINA, propiedad = "CargaFinanciera")
@PathId(value="/deuda-publica-financiera/carga-financiera")
public class DeudaFinancieraCarga implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -1104645131562024191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: 2017-06-21-ES1234567893")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Entidad que se está reportando. Ejemplo: ES1234567893")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="instrumentoFinanciacion")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "instrumentoFinanciacionCargaFinanciera")
	@RdfExternalURI(inicioURI= "/deuda-publica-financiera/intrumento-financiacion/", finURI="instrumentoFinanciacion", urifyLevel=2)
	private String instrumentoFinanciacion;
	
	@ApiModelProperty(value = "Fecha de la Carga financiera. Ejemplo: 2017-06-21 00:00:00")
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="date")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "date", typeURI=Context.XSD_URI+"date")
	private Date date;
	
	@ApiModelProperty(value = "Importe la Carga financiera. Ejemplo: 692332.00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="importe")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "importe", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importe;
	
	@ApiModelProperty(value = "Año Fiscal de la Carga Finaciera. Ejemplo: 2017")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="anio")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "anio" )
	private String anio;
	
	@ApiModelProperty(value = "Gasto o Pasio de la Carga Finaciera. Ejemplo: gasto financiero")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="gastoOPasivoFinanciero")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "gastoOPasivoFinanciero" )
	private String gastoOPasivoFinanciero;
	
	@ApiModelProperty(value = "Descripción de la Carga Finaciera. Ejemplo: AMORT.VTO.21/06/17 PTM L/P MDEC-I.C.O.(20,77MM)'06 (2017)")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="description")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "description" )
	private String description;

	public DeudaFinancieraCarga() {
	}

	
	
	public DeudaFinancieraCarga(String id, String instrumentoFinanciacion, Date date, BigDecimal importe,
			String anio, String gastoOPasivoFinanciero, String description) {
		super();
		this.id = id;
		this.instrumentoFinanciacion = instrumentoFinanciacion;
		this.date = date;
		this.importe = importe;
		this.anio = anio;
		this.gastoOPasivoFinanciero = gastoOPasivoFinanciero;
		this.description = description;
	}

	public DeudaFinancieraCarga(DeudaFinancieraCarga copia) {
		super();
		this.ikey = copia.ikey;
		this.instrumentoFinanciacion = copia.instrumentoFinanciacion;
		this.id = copia.id;
		this.date = copia.date;
		this.importe = copia.importe;
		this.anio = copia.anio;
		this.gastoOPasivoFinanciero = copia.gastoOPasivoFinanciero;
		this.description = copia.description;
	}


	public DeudaFinancieraCarga(DeudaFinancieraCarga copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("instrumentoFinanciacion")) {
			this.instrumentoFinanciacion = copia.instrumentoFinanciacion;
		}
		
		if (attributesToSet.contains("date")) {
			this.date = copia.date;
		}
		
		if (attributesToSet.contains("importe")) {
			this.importe = copia.importe;
		}
		
		if (attributesToSet.contains("anio")) {
			this.anio = copia.anio;
		}
		
		if (attributesToSet.contains("gastoOPasivoFinanciero")) {
			this.gastoOPasivoFinanciero = copia.gastoOPasivoFinanciero;
		}
		
		if (attributesToSet.contains("description")) {
			this.description = copia.description;
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

	@Column(name = "instrumenta_financiacion", nullable = false, length = 50)
	public String getInstrumentoFinanciacion() {
		return this.instrumentoFinanciacion;
	}

	public void setInstrumentoFinanciacion(String instrumentoFinanciacion) {
		this.instrumentoFinanciacion = instrumentoFinanciacion;
	}

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_carga", length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "importe", precision = 12)
	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
	@Column(name = "anio", length = 4)
	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	@Column(name = "gasto_o_pasivo", length = 100)
	public String getGastoOPasivoFinanciero() {
		return this.gastoOPasivoFinanciero;
	}

	public void setGastoOPasivoFinanciero(String gastoOPasivoFinanciero) {
		this.gastoOPasivoFinanciero = gastoOPasivoFinanciero;
	}

	@Column(name = "description", length = 400)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaFinancieraCarga) copia, listado);
	}
	
	public DeudaFinancieraCarga cloneClass(DeudaFinancieraCarga copia, List<String> attributesToSet) {

		DeudaFinancieraCarga obj = new DeudaFinancieraCarga(copia,attributesToSet);		

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
								
		if (!Util.validValue(this.getImporte())) {
			result.add("Importe is not valid [Title:"+this.getImporte()+"]");
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
		return "DeudaFinancieraCarga [id=" + id + ", instrumentoFinanciacion=" + instrumentoFinanciacion + ", date="
				+ date + ", importe=" + importe + ", anio=" + anio + ", gastoOPasivoFinanciero="
				+ gastoOPasivoFinanciero + ", description=" + description + "]";
	}



	

}
