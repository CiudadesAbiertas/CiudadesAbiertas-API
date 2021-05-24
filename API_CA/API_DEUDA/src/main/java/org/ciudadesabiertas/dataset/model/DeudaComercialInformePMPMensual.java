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
@Table(name = "deuda_c_inf_pmp_mes")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUCOM, propiedad = "InformePMPMensual")
@PathId(value="/deuda-publica-comercial/informe-pmp-mensual")
public class DeudaComercialInformePMPMensual  implements java.io.Serializable, RDFModel {
	
	
	@JsonIgnore
	private static final long serialVersionUID = -3116602231512772956L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: 2020-06-zaragoza")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Período medio de pago a proveedores mensual expresado en días. Ejemplo: 27.04")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="periodoMedioPagoMensual")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodoMedioPagoMensual" ,typeURI=Context.XSD_URI+"double")
	private BigDecimal periodoMedioPagoMensual;
	
	@ApiModelProperty(value = "Indicador del número de días promedio que se ha tardado en realizar los pagos en el período. Ejemplo: 18.51")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="ratioOperacionesPagadas")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "ratioOperacionesPagadas" ,typeURI=Context.XSD_URI+"double")
	private BigDecimal ratioOperacionesPagadas;
		
	@ApiModelProperty(value = "indicador del número de días promedio de antigüedad de las operaciones pendientes de pago al final del período. Ejemplo: 18.51")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="ratioOperacionesPendientesPago")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "ratioOperacionesPendientesPago" ,typeURI=Context.XSD_URI+"double")
	private BigDecimal ratioOperacionesPendientesPago;
	

	
	@ApiModelProperty(value = "Entidad que se está reportando. Ejemplo: 12-28-079-AP-001")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="entidad")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "entidad")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/organizacion/", finURI="entidad", urifyLevel=2)
	private String entidad;
	
	@ApiModelProperty(value = "Período que se está reportando. Ejemplo: 2020-06")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="periodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodo")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/proper-interval/", finURI="periodo")
	private String periodo;

	public DeudaComercialInformePMPMensual()
	{
	}
	
	
	public DeudaComercialInformePMPMensual(String id, BigDecimal periodoMedioPagoMensual,
			BigDecimal ratioOperacionesPagadas, BigDecimal importeOperacionesPagada,
			BigDecimal ratioOperacionesPendientesPago, BigDecimal importeOperacionesPendientesPago, String entidad,
			String periodo) {
		super();
		this.id = id;
		this.periodoMedioPagoMensual = periodoMedioPagoMensual;
		this.ratioOperacionesPagadas = ratioOperacionesPagadas;
		this.ratioOperacionesPendientesPago = ratioOperacionesPendientesPago;
		this.entidad = entidad;
		this.periodo = periodo;
	}


	public DeudaComercialInformePMPMensual(DeudaComercialInformePMPMensual copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.periodoMedioPagoMensual = copia.periodoMedioPagoMensual;
		this.ratioOperacionesPagadas = copia.ratioOperacionesPagadas;
		this.ratioOperacionesPendientesPago = copia.ratioOperacionesPendientesPago;
		this.entidad = copia.entidad;
		this.periodo = copia.periodo;
	}


	public DeudaComercialInformePMPMensual(DeudaComercialInformePMPMensual copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("periodoMedioPagoMensual")) {
			this.periodoMedioPagoMensual = copia.periodoMedioPagoMensual;		
		}
		
		if (attributesToSet.contains("ratioOperacionesPagadas")) {
			this.ratioOperacionesPagadas = copia.ratioOperacionesPagadas;		
		}
				
		if (attributesToSet.contains("ratioOperacionesPendientesPago")) {
			this.ratioOperacionesPendientesPago = copia.ratioOperacionesPendientesPago;		
		}
		
		if (attributesToSet.contains("entidad")) {
			this.entidad = copia.entidad;		
		}
		
		if (attributesToSet.contains("periodo")) {
			this.periodo = copia.periodo;		
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
		
	
	
	
	@Column(name = "pmp_mensual", precision = 12)
	public BigDecimal getPeriodoMedioPagoMensual() {
		return periodoMedioPagoMensual;
	}


	public void setPeriodoMedioPagoMensual(BigDecimal periodoMedioPagoMensual) {
		this.periodoMedioPagoMensual = periodoMedioPagoMensual;
	}

	@Column(name = "ratio_opereraciones_pagadas", precision = 12)
	public BigDecimal getRatioOperacionesPagadas() {
		return ratioOperacionesPagadas;
	}


	public void setRatioOperacionesPagadas(BigDecimal ratioOperacionesPagadas) {
		this.ratioOperacionesPagadas = ratioOperacionesPagadas;
	}



	@Column(name = "ratio_operaciones_pdte_pago", precision = 12)
	public BigDecimal getRatioOperacionesPendientesPago() {
		return ratioOperacionesPendientesPago;
	}

	public void setRatioOperacionesPendientesPago(BigDecimal ratioOperacionesPendientesPago) {
		this.ratioOperacionesPendientesPago = ratioOperacionesPendientesPago;
	}


	@Column(name = "entidad", nullable = false, length = 50)
	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}


	@Column(name = "periodo", nullable = true, length = 50)
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}



	
	@Override
	public String toString() {
		return "DeudaComercialInformePMPMensual [id=" + id + ", periodoMedioPagoMensual=" + periodoMedioPagoMensual
				+ ", ratioOperacionesPagadas=" + ratioOperacionesPagadas  + ", ratioOperacionesPendientesPago=" + ratioOperacionesPendientesPago
				+ ", entidad=" + entidad
				+ ", periodo=" + periodo + "]";
	}


	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();	
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.TIME, Context.TIME_URI);
		prefixes.put(Context.ESDEUCOM, Context.ESDEUCOM_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaComercialInformePMPMensual) copia, listado);
	}
	
	public DeudaComercialInformePMPMensual cloneClass(DeudaComercialInformePMPMensual copia, List<String> attributesToSet) {

		DeudaComercialInformePMPMensual obj = new DeudaComercialInformePMPMensual(copia,attributesToSet);		

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
		
		if (!Util.validValue(this.getEntidad())) {
			result.add("Entidad is not valid [Id:"+this.getEntidad()+"]");
		}	
		
		return result;
	}
	


	
	




}

