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
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "deuda_f_inst_financiacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUFINA, propiedad = "InstrumentoFinanciacion")
@PathId(value="/deuda-publica-financiera/instrumento-financiacion")
public class DeudaFinancieraInstrumentoFinanciacion implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 6103605131552024191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: ES0201001148")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Importe para Instrumento Financiación. Ejemplo: 102000000.00")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="importe")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "importe", typeURI=Context.XSD_URI+"float" )
	private BigDecimal importe;
	
	@ApiModelProperty(value = "Tipo Interes para Instrumento Financiación. Ejemplo: variable")
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="tipoInteres", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "tipoInteres")
	private String tipoInteres;
	
	@ApiModelProperty(value = "Tasa Fija para Instrumento Financiación. Ejemplo: 3000.00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="tasaFija")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "tasaFija", typeURI=Context.XSD_URI+"float" )
	private BigDecimal tasaFija;
	
	@ApiModelProperty(value = "Referencia para Instrumento Financiación. Ejemplo: Euribor 1/3/6 meses")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="referencia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "referencia")
	private String referencia;
	
	@ApiModelProperty(value = "Margen para Instrumento Financiación. Ejemplo: 0.015")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="margen")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "margen", typeURI=Context.XSD_URI+"float" )
	private BigDecimal margen;
	
	@ApiModelProperty(value = "Tipo Instrumento para Instrumento Financiación. Ejemplo: prestamo")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="tipoInstrumento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "tipoInstrumento")
	private String tipoInstrumento;
	
	@ApiModelProperty(value = "Prestamo para Instrumento Financiación. Ejemplo: ES1234567893")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="prestamo")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "esPrestamo")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/prestamo/", finURI="prestamo")
	private String prestamo;
	
	@ApiModelProperty(value = "Emision para Instrumento Financiación. Ejemplo: ES0201001148")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="emision")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "esEmision")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/emision/", finURI="emision")
	private String emision;
	
	@ApiModelProperty(value = "Deuda Anual para Instrumento Financiación. Ejemplo: 2019")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="deudaAnual")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "deudaAnualInstrumentoFinanciacion")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/deuda-anual/", finURI="deudaAnual")
	private String deudaAnual;
	
	@ApiModelProperty(value = "Descripcion para Instrumento Financiación. Ejemplo: Texto *")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "description")
	private String description;
	

	public DeudaFinancieraInstrumentoFinanciacion() {
	}

	public DeudaFinancieraInstrumentoFinanciacion(String id, BigDecimal importe, String tipoInteres,
			BigDecimal tasaFija, String referencia, BigDecimal margen, String tipoInstrumento, String prestamo,
			String emision, String deudaAnual, String description) {
		super();
		this.id = id;
		this.importe = importe;
		this.tipoInteres = tipoInteres;
		this.tasaFija = tasaFija;
		this.referencia = referencia;
		this.margen = margen;
		this.tipoInstrumento = tipoInstrumento;
		this.prestamo = prestamo;
		this.emision = emision;
		this.deudaAnual = deudaAnual;
		this.description = description;
	}
	
	public DeudaFinancieraInstrumentoFinanciacion(DeudaFinancieraInstrumentoFinanciacion copia) {
		super();
		this.id = copia.id;
		this.importe = copia.importe;
		this.tipoInteres = copia.tipoInteres;
		this.tasaFija = copia.tasaFija;
		this.referencia = copia.referencia;
		this.margen = copia.margen;
		this.tipoInstrumento = copia.tipoInstrumento;
		this.prestamo = copia.prestamo;
		this.emision = copia.emision;
		this.deudaAnual = copia.deudaAnual;
		this.description = copia.description;
	}
	
	public DeudaFinancieraInstrumentoFinanciacion(DeudaFinancieraInstrumentoFinanciacion copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("importe")) {
			this.importe = copia.importe;
		}
		
		if (attributesToSet.contains("tipoInteres")) {
			this.tipoInteres = copia.tipoInteres;
		}
		
		if (attributesToSet.contains("tasaFija")) {
			this.tasaFija = copia.tasaFija;
		}
		
		if (attributesToSet.contains("referencia")) {
			this.referencia = copia.referencia;
		}
		
		if (attributesToSet.contains("margen")) {
			this.margen = copia.margen;
		}
		
		if (attributesToSet.contains("tipoInstrumento")) {
			this.tipoInstrumento = copia.tipoInstrumento;
		}
		
		if (attributesToSet.contains("prestamo")) {
			this.prestamo = copia.prestamo;
		}
		
		if (attributesToSet.contains("emision")) {
			this.emision = copia.emision;
		}
		
		if (attributesToSet.contains("deudaAnual")) {
			this.deudaAnual = copia.deudaAnual;
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

	
	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "importe", precision = 12)
	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	@Column(name = "tipo_interes", length = 100)
	public String getTipoInteres() {
		return this.tipoInteres;
	}

	public void setTipoInteres(String tipoInteres) {
		this.tipoInteres = tipoInteres;
	}

	@Column(name = "tasa_fija", precision = 12)
	public BigDecimal getTasaFija() {
		return this.tasaFija;
	}

	public void setTasaFija(BigDecimal tasaFija) {
		this.tasaFija = tasaFija;
	}

	@Column(name = "referencia", length = 100)
	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Column(name = "margen", precision = 5, scale = 3)
	public BigDecimal getMargen() {
		return this.margen;
	}

	public void setMargen(BigDecimal margen) {
		this.margen = margen;
	}

	@Column(name = "tipo_instrumento", nullable = false, length = 100)
	public String getTipoInstrumento() {
		return this.tipoInstrumento;
	}

	public void setTipoInstrumento(String tipoInstrumento) {
		this.tipoInstrumento = tipoInstrumento;
	}


	@Column(name = "prestamo",  length = 50)
	public String getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(String prestamo) {
		this.prestamo = prestamo;
	}

	@Column(name = "emision",  length = 50)
	public String getEmision() {
		return emision;
	}


	public void setEmision(String emision) {
		this.emision = emision;
	}


	@Column(name = "deuda_anual", nullable = false, length = 50)
	public String getDeudaAnual() {
		return deudaAnual;
	}


	public void setDeudaAnual(String deudaAnual) {
		this.deudaAnual = deudaAnual;
	}

	@Column(name = "description",  length = 2000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaFinancieraInstrumentoFinanciacion) copia, listado);
	}
	
	public DeudaFinancieraInstrumentoFinanciacion cloneClass(DeudaFinancieraInstrumentoFinanciacion copia, List<String> attributesToSet) {

		DeudaFinancieraInstrumentoFinanciacion obj = new DeudaFinancieraInstrumentoFinanciacion(copia,attributesToSet);		

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
		if (!Util.validValue(this.getDeudaAnual())) {
			result.add("DeudaAnual is not valid [DeudaAnual:"+this.getDeudaAnual()+"]");
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
		return "DeudaFinancieraInstrumentoFinanciacion [id=" + id + ", importe=" + importe + ", tipoInteres="
				+ tipoInteres + ", tasaFija=" + tasaFija + ", referencia=" + referencia + ", margen=" + margen
				+ ", tipoInstrumento=" + tipoInstrumento + ", prestamo=" + prestamo + ", emision=" + emision
				+ ", deudaAnual=" + deudaAnual + ", description=" + description + "]";
	}
	
	

}
