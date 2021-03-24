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
@Table(name = "deuda_f_prestamo")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUFINA, propiedad = "Prestamo")
@PathId(value="/deuda-publica-financiera/prestamo")
public class DeudaFinancieraPrestamo implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 1123005031552024191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: ES0201001148")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Moneda en la cual se realiza un préstamo. Ejemplo: euro")
	@CsvBindByPosition(position=2)	
	@CsvBindByName(column="moneda", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "moneda" )
	private String moneda;
	
	@ApiModelProperty(value = "Fecha en la cual se suscribe un préstamo. Ejemplo: 2006-12-14 00:00:00")
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="fechaFormalizacion")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "fechaFormalizacion", typeURI=Context.XSD_URI+"date")
	private Date fechaFormalizacion;
	
	@ApiModelProperty(value = "Lapso de pago de un préstamo. Ejemplo: P16Y")
	@CsvBindByPosition(position=4)	
	@CsvBindByName(column="plazo", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "plazo", typeURI=Context.XSD_URI+"duration" )
	private String plazo;
	
	@ApiModelProperty(value = "Fecha en que comienzan a devengarse las obligaciones contraídas en el préstamo. Ejemplo: 2007-01-01 00:00:00")
	@CsvBindByPosition(position=5)	
	@CsvBindByName(column="fechaInicio")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "fechaInicio", typeURI=Context.XSD_URI+"date")
	private Date fechaInicio;
	
	@ApiModelProperty(value = "Fecha pactada para devolver el capital prestado. Ejemplo: 2022-12-14 00:00:00")
	@CsvBindByPosition(position=6)	
	@CsvBindByName(column="fechaVencimiento")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "fechaVencimiento", typeURI=Context.XSD_URI+"date")
	private Date fechaVencimiento;
	
	@ApiModelProperty(value = "Préstamo con disposiciones diferidas o parciales de capital. Ejemplo: false")
	@CsvBindByPosition(position=7)	
	@CsvBindByName(column="diferidasOParcialesCap", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "diferidasOParcialesCapital", typeURI=Context.XSD_URI+"boolean")
	private Boolean diferidasOParcialesCap;
	
	@ApiModelProperty(value = "Clasificación del tipo de prestamo. Ejemplo: bilateral")
	@CsvBindByPosition(position=8)	
	@CsvBindByName(column="tipoPrestamo", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESDEUFINA, propiedad = "tipo-prestamo")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/economia/deuda-publica-financiera/tipo-prestamo/", finURI="tipoPrestamo", urifyLevel=2)	
	private String tipoPrestamo;
	

	public DeudaFinancieraPrestamo() {
	}

	

	public DeudaFinancieraPrestamo(String id, String moneda, Date fechaFormalizacion, String plazo, Date fechaInicio,
			Date fechaVencimiento, Boolean diferidasOParcialesCap, String tipoPrestamo) {
		super();
		this.id = id;
		this.moneda = moneda;
		this.fechaFormalizacion = fechaFormalizacion;
		this.plazo = plazo;
		this.fechaInicio = fechaInicio;
		this.fechaVencimiento = fechaVencimiento;
		this.diferidasOParcialesCap = diferidasOParcialesCap;
		this.tipoPrestamo = tipoPrestamo;
	}
	
	public DeudaFinancieraPrestamo(DeudaFinancieraPrestamo copia) {
		super();
		this.id = copia.id;
		this.moneda = copia.moneda;
		this.fechaFormalizacion = copia.fechaFormalizacion;
		this.plazo = copia.plazo;
		this.fechaInicio = copia.fechaInicio;
		this.fechaVencimiento = copia.fechaVencimiento;
		this.diferidasOParcialesCap = copia.diferidasOParcialesCap;
		this.tipoPrestamo = copia.tipoPrestamo;
	}

	public DeudaFinancieraPrestamo(DeudaFinancieraPrestamo copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("moneda")) {
			this.moneda = copia.moneda;
		}
		
		if (attributesToSet.contains("fechaFormalizacion")) {
			this.fechaFormalizacion = copia.fechaFormalizacion;
		}
		
		if (attributesToSet.contains("plazo")) {
			this.plazo = copia.plazo;
		}
		
		if (attributesToSet.contains("fechaInicio")) {
			this.fechaInicio = copia.fechaInicio;
		}
		
		if (attributesToSet.contains("fechaVencimiento")) {
			this.fechaVencimiento = copia.fechaInicio;
		}
		
		if (attributesToSet.contains("diferidasOParcialesCap")) {
			this.diferidasOParcialesCap = copia.diferidasOParcialesCap;
		}
		
		if (attributesToSet.contains("tipoPrestamo")) {
			this.tipoPrestamo = copia.tipoPrestamo;
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

	@Column(name = "moneda", length = 100)
	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_formalizacion", length = 19)
	public Date getFechaFormalizacion() {
		return this.fechaFormalizacion;
	}

	public void setFechaFormalizacion(Date fechaFormalizacion) {
		this.fechaFormalizacion = fechaFormalizacion;
	}

	@Column(name = "plazo", length = 100)
	public String getPlazo() {
		return this.plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio", length = 19)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_vencimiento", length = 19)
	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	@Column(name = "diferidas_o_parciales_cap")
	public Boolean getDiferidasOParcialesCap() {
		return this.diferidasOParcialesCap;
	}

	public void setDiferidasOParcialesCap(Boolean diferidasOParcialesCap) {
		this.diferidasOParcialesCap = diferidasOParcialesCap;
	}

	@Column(name = "tipo_prestamo", nullable = false, length = 100)
	public String getTipoPrestamo() {
		return this.tipoPrestamo;
	}

	public void setTipoPrestamo(String tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaFinancieraPrestamo) copia, listado);
	}
	
	public DeudaFinancieraPrestamo cloneClass(DeudaFinancieraPrestamo copia, List<String> attributesToSet) {

		DeudaFinancieraPrestamo obj = new DeudaFinancieraPrestamo(copia,attributesToSet);		

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
		if (!Util.validValue(this.getTipoPrestamo())) {
			result.add("TipoPrestamo is not valid [TipoPrestamo:"+this.getTipoPrestamo()+"]");
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
		return "DeudaFinancieraPrestamo [id=" + id + ", moneda=" + moneda + ", fechaFormalizacion=" + fechaFormalizacion
				+ ", plazo=" + plazo + ", fechaInicio=" + fechaInicio + ", fechaVencimiento=" + fechaVencimiento
				+ ", diferidasOParcialesCap=" + diferidasOParcialesCap + ", tipoPrestamo=" + tipoPrestamo + "]";
	}
	
	

}
