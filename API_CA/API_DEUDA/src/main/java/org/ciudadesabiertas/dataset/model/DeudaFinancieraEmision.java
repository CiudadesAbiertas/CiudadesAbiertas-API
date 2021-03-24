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
@Table(name = "deuda_f_emision")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUFINA, propiedad = "Emision")
@PathId(value="/deuda-publica-financiera/emision")
public class DeudaFinancieraEmision implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 2104625131552024191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: ES0201001148")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Código de la Emision. Ejemplo: ES0201001148")
	@CsvBindByPosition(position=2)	
	@CsvBindByName(column="codIsin", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "codISIN")
	private String codIsin;
	
	@ApiModelProperty(value = "Número de titulos de la Emisión. Ejemplo: 692332.00")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="numeroTitulos")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "numeroTitulos", typeURI=Context.XSD_URI+"integer" )
	private Integer numeroTitulos;
	
	@ApiModelProperty(value = "La cuantia asociada a los titulos de la Emisión. Ejemplo: 100000")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="cuantiaPorTitulo")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "cuantiaPorTitulo", typeURI=Context.XSD_URI+"float" )
	private BigDecimal cuantiaPorTitulo;
	
	@ApiModelProperty(value = "Importe anual de la Emisión. Ejemplo: 4550.00")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="importeAnual")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "importeAnual", typeURI=Context.XSD_URI+"float" )
	private BigDecimal importeAnual;

	@ApiModelProperty(value = "Mes dia de Pago de la Emision. Ejemplo: 06-16")
	@CsvBindByPosition(position=6)	
	@CsvBindByName(column="mesDiaPago", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "mesDiaPago", typeURI=Context.XSD_URI+"gMonthDay" )
	private String mesDiaPago;
	
	@ApiModelProperty(value = "Precio de la Emisión. Ejemplo: 99.81")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="precioEmision")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "precioEmision", typeURI=Context.XSD_URI+"float" )
	private BigDecimal precioEmision;
	
	@ApiModelProperty(value = "Precio del reembolso de la Emisión. Ejemplo: 100.00")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="precioReembolso")
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "precioReembolso", typeURI=Context.XSD_URI+"float" )
	private BigDecimal precioReembolso;
	
	@ApiModelProperty(value = "Duración de la Emision. Ejemplo: P30Y")
	@CsvBindByPosition(position=9)	
	@CsvBindByName(column="duracion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "duracion", typeURI=Context.XSD_URI+"duration" )
	private String duracion;
	
	@ApiModelProperty(value = "Fecha de la Emision. Ejemplo: 2006-06-16 00:00:00")
	@CsvBindByPosition(position=10)	
	@CsvBindByName(column="fechaEmision")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "fechaEmision", typeURI=Context.XSD_URI+"date")
	private Date fechaEmision;
	

	public DeudaFinancieraEmision() {
	}

		

	public DeudaFinancieraEmision(String id, String codIsin, Integer numeroTitulos, BigDecimal cuantiaPorTitulo,
			BigDecimal importeAnual, String mesDiaPago, BigDecimal precioEmision, BigDecimal precioReembolso,
			String duracion, Date fechaEmision) {
		super();
		this.id = id;
		this.codIsin = codIsin;
		this.numeroTitulos = numeroTitulos;
		this.cuantiaPorTitulo = cuantiaPorTitulo;
		this.importeAnual = importeAnual;
		this.mesDiaPago = mesDiaPago;
		this.precioEmision = precioEmision;
		this.precioReembolso = precioReembolso;
		this.duracion = duracion;
		this.fechaEmision = fechaEmision;
	}


	public DeudaFinancieraEmision(DeudaFinancieraEmision copia) {
		super();
		this.id = copia.id;
		this.codIsin = copia.codIsin;
		this.numeroTitulos = copia.numeroTitulos;
		this.cuantiaPorTitulo = copia.cuantiaPorTitulo;
		this.importeAnual = copia.importeAnual;
		this.mesDiaPago = copia.mesDiaPago;
		this.precioEmision = copia.precioEmision;
		this.precioReembolso = copia.precioReembolso;
		this.duracion = copia.duracion;
		this.fechaEmision = copia.fechaEmision;
		
	}
	
	public DeudaFinancieraEmision(DeudaFinancieraEmision copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("codIsin")) {
			this.codIsin = copia.codIsin;
		}
		
		if (attributesToSet.contains("numeroTitulos")) {
			this.numeroTitulos = copia.numeroTitulos;
		}
		
		if (attributesToSet.contains("cuantiaPorTitulo")) {
			this.cuantiaPorTitulo = copia.cuantiaPorTitulo;
		}
		
		if (attributesToSet.contains("importeAnual")) {
			this.importeAnual = copia.importeAnual;
		}
		
		if (attributesToSet.contains("mesDiaPago")) {
			this.mesDiaPago = copia.mesDiaPago;
		}
		
		if (attributesToSet.contains("precioEmision")) {
			this.precioEmision = copia.precioEmision;
		}
		
		if (attributesToSet.contains("precioReembolso")) {
			this.precioReembolso = copia.precioReembolso;
		}
		
		if (attributesToSet.contains("duracion")) {
			this.duracion = copia.duracion;
		}
		
		if (attributesToSet.contains("fechaEmision")) {
			this.fechaEmision = copia.fechaEmision;
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

	@Column(name = "codISIN", nullable = false, length = 50)
	public String getCodIsin() {
		return this.codIsin;
	}

	public void setCodIsin(String codIsin) {
		this.codIsin = codIsin;
	}

	@Column(name = "numero_titulos")
	public Integer getNumeroTitulos() {
		return this.numeroTitulos;
	}

	public void setNumeroTitulos(Integer numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	@Column(name = "cuantia_por_titulo", precision = 12)
	public BigDecimal getCuantiaPorTitulo() {
		return this.cuantiaPorTitulo;
	}

	public void setCuantiaPorTitulo(BigDecimal cuantiaPorTitulo) {
		this.cuantiaPorTitulo = cuantiaPorTitulo;
	}

	@Column(name = "importe_anual", precision = 12)
	public BigDecimal getImporteAnual() {
		return this.importeAnual;
	}

	public void setImporteAnual(BigDecimal importeAnual) {
		this.importeAnual = importeAnual;
	}

	@Column(name = "mes_dia_pago", length = 100)
	public String getMesDiaPago() {
		return this.mesDiaPago;
	}

	public void setMesDiaPago(String mesDiaPago) {
		this.mesDiaPago = mesDiaPago;
	}

	@Column(name = "precio_emision", precision = 12)
	public BigDecimal getPrecioEmision() {
		return this.precioEmision;
	}

	public void setPrecioEmision(BigDecimal precioEmision) {
		this.precioEmision = precioEmision;
	}

	@Column(name = "precio_reembolso", precision = 12)
	public BigDecimal getPrecioReembolso() {
		return this.precioReembolso;
	}

	public void setPrecioReembolso(BigDecimal precioReembolso) {
		this.precioReembolso = precioReembolso;
	}

	@Column(name = "duracion", length = 100)
	public String getDuracion() {
		return this.duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_emision", length = 19)
	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaFinancieraEmision) copia, listado);
	}
	
	public DeudaFinancieraEmision cloneClass(DeudaFinancieraEmision copia, List<String> attributesToSet) {

		DeudaFinancieraEmision obj = new DeudaFinancieraEmision(copia,attributesToSet);		

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
		if (!Util.validValue(this.getCodIsin())) {
			result.add("CodIsin is not valid [Id:"+this.getCodIsin()+"]");
		}
								
		if (!Util.validValue(this.getFechaEmision())) {
			result.add("FechaEmision is not valid [Title:"+this.getFechaEmision()+"]");
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
		return "DeudaFinancieraEmision [id=" + id + ", codIsin=" + codIsin + ", numeroTitulos=" + numeroTitulos
				+ ", cuantiaPorTitulo=" + cuantiaPorTitulo + ", importeAnual=" + importeAnual + ", mesDiaPago="
				+ mesDiaPago + ", precioEmision=" + precioEmision + ", precioReembolso=" + precioReembolso
				+ ", duracion=" + duracion + ", fechaEmision=" + fechaEmision + "]";
	}

	

}
