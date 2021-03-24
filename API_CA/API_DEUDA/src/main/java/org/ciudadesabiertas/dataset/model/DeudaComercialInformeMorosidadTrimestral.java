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
@Table(name = "deuda_c_inf_morosidad_tri") 
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUCOM, propiedad = "InformeMorosidadTrimestral")
@PathId(value="/deuda-publica-comercial/informe-morosidad-trimestral")
public class DeudaComercialInformeMorosidadTrimestral  implements java.io.Serializable, RDFModel {
	
	
	@JsonIgnore
	private static final long serialVersionUID = -3056661531552872156L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Referencia inequívoca al recurso dentro de un contexto dado. Ejemplo: DEUDAC0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Período medio de pago a proveedores trimestral expresado en días. Ejemplo: 55.50")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="periodoMedioPagoTrimestral")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodoMedioPagoTrimestral" ,typeURI=Context.XSD_URI+"double")
	private BigDecimal periodoMedioPagoTrimestral;
	
	@ApiModelProperty(value = "Número total de facturas pendientes de pago al final del período de pago dentro del período legal. Ejemplo: 1124")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="numPagosDentroPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "numPagosDentroPeriodo", typeURI=Context.XSD_URI+"integer")
	private Integer numPagosDentroPeriodo;
	
	@ApiModelProperty(value = "Importe total de pagos realizados dentro del período. Ejemplo: 5579630.33")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="importePagosDentroPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "importePagosDentroPeriodo", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importePagosDentroPeriodo;
	
	@ApiModelProperty(value = "Número de pagos fuera del período legal de pago. Ejemplo: 1124")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="numPagosFueraPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "numPagosFueraPeriodo", typeURI=Context.XSD_URI+"integer")
	private Integer numPagosFueraPeriodo;
	
	@ApiModelProperty(value = "Importe total de pagos realizados fuera del período legal de pago. Ejemplo: 5579630.33")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="importePagosFueraPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "importePagosFueraPeriodo", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importePagosFueraPeriodo;
	
	
	@ApiModelProperty(value = "Número de pagos con intereses de demora dentro del período. Ejemplo: 1124")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="numPagosInteresesDemora")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "numPagosInteresesDemora", typeURI=Context.XSD_URI+"integer")
	private Integer numPagosInteresesDemora;
	
	@ApiModelProperty(value = "Importe total de intereses de demora pagados en el período. Ejemplo: 5579630.33")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="importePagosInteresesDemora")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "importePagosInteresesDemora", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importePagosInteresesDemora;
	
	@ApiModelProperty(value = "Número total de facturas pendientes de pago al final del período de pago dentro del período legal. Ejemplo: 1124")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="numFacturasPendientesDentroPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "numFacturasPendientesDentroPeriodo", typeURI=Context.XSD_URI+"integer")
	private Integer numFacturasPendientesDentroPeriodo;
	
	@ApiModelProperty(value = "Importe total de facturas pendientes de pago al final del período de pago dentro del período legal. Ejemplo: 5579630.33")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="importeFacturasPendientesDentroPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "importeFacturasPendientesDentroPeriodo", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importeFacturasPendientesDentroPeriodo;
	
	@ApiModelProperty(value = "Número de pagos fuera del período legal de pago. Ejemplo: 1124")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="numFacturasPendientesFueraPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "numFacturasPendientesFueraPeriodo", typeURI=Context.XSD_URI+"integer")
	private Integer numFacturasPendientesFueraPeriodo;
	
	@ApiModelProperty(value = "Importe total de facturas pendientes de pago al final del período de pago fuera del período legal. Ejemplo: 5579630.33")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="importeFacturasPendientesFueraPeriodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "importeFacturasPendientesFueraPeriodo", typeURI=Context.XSD_URI+"double" )
	private BigDecimal importeFacturasPendientesFueraPeriodo;
	
	@ApiModelProperty(value = "Período medio de pago pendiente del período expresado en días. Ejemplo: 33.87")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="periodoMedioPagoPendiente")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodoMedioPagoPendiente")
	private BigDecimal periodoMedioPagoPendiente;
	
	
	@ApiModelProperty(value = "Tipo de la Contabilidad a la que pertenece la Deuda. Ejemplo: empresarial")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="tipoContabilidad")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "tipoContabilidad")
	private String tipoContabilidad;
	
	@ApiModelProperty(value = "Entidad que se está reportando. Ejemplo: 12-28-079-AP-001")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="entidad")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "entidad")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/organizacion/", finURI="entidad", urifyLevel=2)
	private String entidad;
	
	@ApiModelProperty(value = "Período que se está reportando. Ejemplo: 2020-06")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="periodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodo")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/proper-interval/", finURI="periodo")
	private String periodo;

	public DeudaComercialInformeMorosidadTrimestral()
	{
	}



	public DeudaComercialInformeMorosidadTrimestral(String id, BigDecimal periodoMedioPagoTrimestral,
			Integer numPagosDentroPeriodo, BigDecimal importeDentroPeriodo, Integer numPagosFueraPeriodo,
			BigDecimal importePagosFueraPeriodo, Integer numPagosInteresesDemora, BigDecimal importePagosInteresesDemora,
			Integer numFacturasPendientesDentroPeriodo, BigDecimal importeFacturasPendientesDentroPeriodo,
			Integer numFacturasPendientesFueraPeriodo, BigDecimal importeFacturasPendientesFueraPeriodo,
			BigDecimal periodoMedioPagoPendiente, String tipoContabilidad, String entidad, String periodo) {
		super();
		this.id = id;
		this.periodoMedioPagoTrimestral = periodoMedioPagoTrimestral;
		this.numPagosDentroPeriodo = numPagosDentroPeriodo;
		this.importePagosDentroPeriodo = importeDentroPeriodo;
		this.numPagosFueraPeriodo = numPagosFueraPeriodo;
		this.importePagosFueraPeriodo = importePagosFueraPeriodo;
		this.numPagosInteresesDemora = numPagosInteresesDemora;
		this.importePagosInteresesDemora = importePagosInteresesDemora;
		this.numFacturasPendientesDentroPeriodo = numFacturasPendientesDentroPeriodo;
		this.importeFacturasPendientesDentroPeriodo = importeFacturasPendientesDentroPeriodo;
		this.numFacturasPendientesFueraPeriodo = numFacturasPendientesFueraPeriodo;
		this.importeFacturasPendientesFueraPeriodo = importeFacturasPendientesFueraPeriodo;
		this.periodoMedioPagoPendiente = periodoMedioPagoPendiente;
		this.tipoContabilidad = tipoContabilidad;
		this.entidad = entidad;
		this.periodo = periodo;
	}
	
	public DeudaComercialInformeMorosidadTrimestral(DeudaComercialInformeMorosidadTrimestral copia) {
		super();
		this.id = copia.id;
		this.periodoMedioPagoTrimestral = copia.periodoMedioPagoTrimestral;
		this.numPagosDentroPeriodo = copia.numPagosDentroPeriodo;
		this.importePagosDentroPeriodo = copia.importePagosDentroPeriodo;
		this.numPagosFueraPeriodo = copia.numPagosFueraPeriodo;
		this.importePagosFueraPeriodo = copia.importePagosFueraPeriodo;
		this.numPagosInteresesDemora = copia.numPagosInteresesDemora;
		this.importePagosInteresesDemora = copia.importePagosInteresesDemora;
		this.numFacturasPendientesDentroPeriodo = copia.numFacturasPendientesDentroPeriodo;
		this.importeFacturasPendientesDentroPeriodo = copia.importeFacturasPendientesDentroPeriodo;
		this.numFacturasPendientesFueraPeriodo = copia.numFacturasPendientesFueraPeriodo;
		this.importeFacturasPendientesFueraPeriodo = copia.importeFacturasPendientesFueraPeriodo;
		this.periodoMedioPagoPendiente = copia.periodoMedioPagoPendiente;
		this.tipoContabilidad = copia.tipoContabilidad;
		this.entidad = copia.entidad;
		this.periodo = copia.periodo;
	}
	

	public DeudaComercialInformeMorosidadTrimestral(DeudaComercialInformeMorosidadTrimestral copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("periodoMedioPagoTrimestral")) {
			this.periodoMedioPagoTrimestral = copia.periodoMedioPagoTrimestral;		
		}
		
		if (attributesToSet.contains("numPagosDentroPeriodo")) {
			this.numPagosDentroPeriodo = copia.numPagosDentroPeriodo;		
		}
		
		if (attributesToSet.contains("importePagosDentroPeriodo")) {
			this.importePagosDentroPeriodo = copia.importePagosDentroPeriodo;		
		}
		
		if (attributesToSet.contains("numPagosFueraPeriodo")) {
			this.numPagosFueraPeriodo = copia.numPagosFueraPeriodo;		
		}
		
		if (attributesToSet.contains("importePagosFueraPeriodo")) {
			this.importePagosFueraPeriodo = copia.importePagosFueraPeriodo;		
		}
		
		if (attributesToSet.contains("numPagosInteresesDemora")) {
			this.numPagosInteresesDemora = copia.numPagosInteresesDemora;		
		}
		
		if (attributesToSet.contains("importePagosInteresesDemora")) {
			this.importePagosInteresesDemora = copia.importePagosInteresesDemora;		
		}
		
		if (attributesToSet.contains("numFacturasPendientesDentroPeriodo")) {
			this.numFacturasPendientesDentroPeriodo = copia.numFacturasPendientesDentroPeriodo;		
		}
		
		if (attributesToSet.contains("importeFacturasPendientesDentroPeriodo")) {
			this.importeFacturasPendientesDentroPeriodo = copia.importeFacturasPendientesDentroPeriodo;		
		}
		
		if (attributesToSet.contains("numFacturasPendientesFueraPeriodo")) {
			this.numFacturasPendientesFueraPeriodo = copia.numFacturasPendientesFueraPeriodo;		
		}
		
		if (attributesToSet.contains("importeFacturasPendientesFueraPeriodo")) {
			this.importeFacturasPendientesFueraPeriodo = copia.importeFacturasPendientesFueraPeriodo;		
		}
		
		if (attributesToSet.contains("periodoMedioPagoPendiente")) {
			this.periodoMedioPagoPendiente = copia.periodoMedioPagoPendiente;		
		}
		
		if (attributesToSet.contains("tipoContabilidad")) {
			this.tipoContabilidad = copia.tipoContabilidad;		
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
		
	
	

	@Column(name = "periodo", nullable = false, length = 50)
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	

	@Column(name = "pmp_trimestral", precision = 12)
	public BigDecimal getPeriodoMedioPagoTrimestral() {
		return periodoMedioPagoTrimestral;
	}


	
	public void setPeriodoMedioPagoTrimestral(BigDecimal periodoMedioPagoTrimestral) {
		this.periodoMedioPagoTrimestral = periodoMedioPagoTrimestral;
	}


	@Column(name = "num_pagos_dentro_periodo")
	public Integer getNumPagosDentroPeriodo() {
		return numPagosDentroPeriodo;
	}



	public void setNumPagosDentroPeriodo(Integer numPagosDentroPeriodo) {
		this.numPagosDentroPeriodo = numPagosDentroPeriodo;
	}


	@Column(name = "imp_pagos_dentro_periodo", precision = 12)
	public BigDecimal getImportePagosDentroPeriodo() {
		return importePagosDentroPeriodo;
	}



	public void setImportePagosDentroPeriodo(BigDecimal importePagosDentroPeriodo) {
		this.importePagosDentroPeriodo = importePagosDentroPeriodo;
	}


	@Column(name = "num_pagos_fuera_periodo")
	public Integer getNumPagosFueraPeriodo() {
		return numPagosFueraPeriodo;
	}



	public void setNumPagosFueraPeriodo(Integer numPagosFueraPeriodo) {
		this.numPagosFueraPeriodo = numPagosFueraPeriodo;
	}


	@Column(name = "imp_pagos_fuera_periodo", precision = 12)
	public BigDecimal getImportePagosFueraPeriodo() {
		return importePagosFueraPeriodo;
	}



	public void setImportePagosFueraPeriodo(BigDecimal importePagosFueraPeriodo) {
		this.importePagosFueraPeriodo = importePagosFueraPeriodo;
	}


	@Column(name = "num_pagos_intereses_demora")
	public Integer getNumPagosInteresesDemora() {
		return numPagosInteresesDemora;
	}



	public void setNumPagosInteresesDemora(Integer numPagosInteresesDemora) {
		this.numPagosInteresesDemora = numPagosInteresesDemora;
	}


	@Column(name = "imp_pagos_intereses_demora", precision = 12)
	public BigDecimal getImportePagosInteresesDemora() {
		return importePagosInteresesDemora;
	}



	public void setImportePagosInteresesDemora(BigDecimal importePagosInteresesDemora) {
		this.importePagosInteresesDemora = importePagosInteresesDemora;
	}


	@Column(name = "n_facturas_pte_dtro_periodo")
	public Integer getNumFacturasPendientesDentroPeriodo() {
		return numFacturasPendientesDentroPeriodo;
	}



	public void setNumFacturasPendientesDentroPeriodo(Integer numFacturasPendientesDentroPeriodo) {
		this.numFacturasPendientesDentroPeriodo = numFacturasPendientesDentroPeriodo;
	}


	@Column(name = "imp_facturas_pte_dtro_periodo", precision = 12)
	public BigDecimal getImporteFacturasPendientesDentroPeriodo() {
		return importeFacturasPendientesDentroPeriodo;
	}



	public void setImporteFacturasPendientesDentroPeriodo(BigDecimal importeFacturasPendientesDentroPeriodo) {
		this.importeFacturasPendientesDentroPeriodo = importeFacturasPendientesDentroPeriodo;
	}


	
	@Column(name = "n_facturas_pte_fuera_periodo")
	public Integer getNumFacturasPendientesFueraPeriodo() {
		return numFacturasPendientesFueraPeriodo;
	}



	public void setNumFacturasPendientesFueraPeriodo(Integer numFacturasPendientesFueraPeriodo) {
		this.numFacturasPendientesFueraPeriodo = numFacturasPendientesFueraPeriodo;
	}


	@Column(name = "imp_facturas_pte_fuera_periodo", precision = 12)
	public BigDecimal getImporteFacturasPendientesFueraPeriodo() {
		return importeFacturasPendientesFueraPeriodo;
	}



	public void setImporteFacturasPendientesFueraPeriodo(BigDecimal importeFacturasPendientesFueraPeriodo) {
		this.importeFacturasPendientesFueraPeriodo = importeFacturasPendientesFueraPeriodo;
	}


	@Column(name = "periodo_medio_pago_pte", precision = 12)
	public BigDecimal getPeriodoMedioPagoPendiente() {
		return periodoMedioPagoPendiente;
	}



	public void setPeriodoMedioPagoPendiente(BigDecimal periodoMedioPagoPendiente) {
		this.periodoMedioPagoPendiente = periodoMedioPagoPendiente;
	}


	@Column(name = "tipo_contabilidad", nullable = true, length = 100)
	public String getTipoContabilidad() {
		return tipoContabilidad;
	}



	public void setTipoContabilidad(String tipoContabilidad) {
		this.tipoContabilidad = tipoContabilidad;
	}


	@Column(name = "entidad", nullable = false, length = 50)
	public String getEntidad() {
		return entidad;
	}



	public void setEntidad(String entidad) {
		this.entidad = entidad;
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
		return (T) cloneClass((DeudaComercialInformeMorosidadTrimestral) copia, listado);
	}
	
	public DeudaComercialInformeMorosidadTrimestral cloneClass(DeudaComercialInformeMorosidadTrimestral copia, List<String> attributesToSet) {

		DeudaComercialInformeMorosidadTrimestral obj = new DeudaComercialInformeMorosidadTrimestral(copia,attributesToSet);		

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
		
		if (!Util.validValue(this.getPeriodo())) {
			result.add("Periodo is not valid [Id:"+this.getPeriodo()+"]");
		}	
		
		if (!Util.validValue(this.getTipoContabilidad())) {
			result.add("TipoContabilidad is not valid [Id:"+this.getTipoContabilidad()+"]");
		}	
		
		if (!Util.validValue(this.getEntidad())) {
			result.add("Entidad is not valid [Id:"+this.getEntidad()+"]");
		}
		
		return result;
	}
	


	
	




}

