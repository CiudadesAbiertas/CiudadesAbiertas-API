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
@Table(name = "deuda_c_inf_pmp_mes_global")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESDEUCOM, propiedad = "InformePMPMensualGlobal")
@PathId(value="/deuda-publica-comercial/informe-pmp-mensual-global")
public class DeudaComercialInformePMPMensualGlobal  implements java.io.Serializable, RDFModel {
	
	
	@JsonIgnore
	private static final long serialVersionUID = -3016601231552772956L;

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
	@CsvBindByName(column="periodoMedioPagoMensualGlobal")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodoMedioPagoMensualGlobal" ,typeURI=Context.XSD_URI+"double")
	private BigDecimal periodoMedioPagoMensualGlobal;
	
	@ApiModelProperty(value = "Período que se está reportando. Ejemplo: 2020-06")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="periodo")
	@Rdf(contexto = Context.ESDEUCOM, propiedad = "periodo")
	@RdfExternalURI(inicioURI= "/deuda-publica-comercial/proper-interval/", finURI="periodo")
	private String periodo;

	public DeudaComercialInformePMPMensualGlobal()
	{
	}

	public DeudaComercialInformePMPMensualGlobal(DeudaComercialInformePMPMensualGlobal copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.periodoMedioPagoMensualGlobal = copia.periodoMedioPagoMensualGlobal;
		this.periodo = copia.periodo;
	}


	public DeudaComercialInformePMPMensualGlobal(DeudaComercialInformePMPMensualGlobal copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("periodoMedioPagoMensualGlobal")) {
			this.periodoMedioPagoMensualGlobal = copia.periodoMedioPagoMensualGlobal;		
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
		
	
	@Column(name = "pmp_mensual_global", nullable = false, length = 50)
	public BigDecimal getPeriodoMedioPagoMensualGlobal() {
		return periodoMedioPagoMensualGlobal;
	}

	public void setPeriodoMedioPagoMensualGlobal(BigDecimal periodoMedioPagoMensualGlobal) {
		this.periodoMedioPagoMensualGlobal = periodoMedioPagoMensualGlobal;
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
		return "DeudaComercialInformePMPMensualGlobal [ikey=" + ikey + ", id=" + id + ", periodoMedioPagoMensualGlobal="
				+ periodoMedioPagoMensualGlobal + ", periodo=" + periodo + "]";
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
		return (T) cloneClass((DeudaComercialInformePMPMensualGlobal) copia, listado);
	}
	
	public DeudaComercialInformePMPMensualGlobal cloneClass(DeudaComercialInformePMPMensualGlobal copia, List<String> attributesToSet) {

		DeudaComercialInformePMPMensualGlobal obj = new DeudaComercialInformePMPMensualGlobal(copia,attributesToSet);		

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
		
		if (!Util.validValue(this.getPeriodoMedioPagoMensualGlobal())) {
			result.add("PeriodoMedioPagoMensualGlobal is not valid [Id:"+this.getPeriodoMedioPagoMensualGlobal()+"]");
		}	
		
		return result;
	}
	


	
	




}

