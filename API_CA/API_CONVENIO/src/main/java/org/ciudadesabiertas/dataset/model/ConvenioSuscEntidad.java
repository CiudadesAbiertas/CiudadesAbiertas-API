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
import com.opencsv.bean.CsvDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */


@Entity
@ApiModel
@Table(name = "convenio_susc_entidad")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCONV, propiedad = "SuscripcionDeConvenioPorEntidad")
@PathId(value="/convenio/suscripcion-por-entidad")
public class ConvenioSuscEntidad implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -803233533106372866L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la Entidad. Ejemplo: CONVSUSENT001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/convenio/convenio/", sujetoFinURI="convenioId", propiedadURI=Context.ESCONV_URI+"suscritoPor", objetoInicioURI="/convenio/suscripcion-por-entidad/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Nombre de la Entidad. Ejemplo: Localidata")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="entidadSuscriptora", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "entidadSuscriptora")
	//IRI: http://vocab.ciudadesabiertas.es/def/sector-publico/convenio#entidadSuscriptora
	private String entidadSuscriptora;
	
	@ApiModelProperty(value = "Obligación económica (Euros). Ejemplo: 200000.00")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "obliEconomicaEntidad")
	@Rdf(contexto = Context.ESCONV, propiedad = "obliEconomicaEntidad", typeURI=Context.XSD_URI+"float")
	private BigDecimal obliEconomicaEntidad;
	
	@ApiModelProperty(value = "Organizacion (id) asociada a la Entidad. Ejemplo: ORGLOC0010")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="organizationId")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESCONV, propiedad = "obligadoPrestacionesEntidad")
	@RdfExternalURI(inicioURI="/convenio/organization/",finURI="organizationId", urifyLevel = 1)
	private String organizationId;
		
	@ApiModelProperty(value = "Convenio (id) asociada a la Entidad. Ejemplo: CONV001")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="convenioId", format=Constants.STRING_FORMAT)
	private String convenioId;

	public ConvenioSuscEntidad() {
	}

	

	public ConvenioSuscEntidad(String ikey, String id, String entidadSuscriptora,
			BigDecimal obliEconomicaEntidad, String firmanteEntidad, String obligadoPresEntidadId,
			String organizationId) {
		this.ikey = ikey;		
		this.id = id;
		this.entidadSuscriptora = entidadSuscriptora;
		this.obliEconomicaEntidad = obliEconomicaEntidad;		
		this.organizationId = organizationId;
	
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	

	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "entidad_suscriptora", nullable = false, length = 200)
	public String getEntidadSuscriptora() {
		return this.entidadSuscriptora;
	}

	public void setEntidadSuscriptora(String entidadSuscriptora) {
		this.entidadSuscriptora = entidadSuscriptora;
	}

	@Column(name = "obli_economica_entidad", precision = 12)
	public BigDecimal getObliEconomicaEntidad() {
		return this.obliEconomicaEntidad;
	}

	public void setObliEconomicaEntidad(BigDecimal obliEconomicaEntidad) {
		this.obliEconomicaEntidad = obliEconomicaEntidad;
	}

	@Column(name = "organization_id", length = 50)
	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	
	
	
	@Column(name = "convenio_id", length = 50)
	public String getConvenioId() {
		return convenioId;
	}



	public void setConvenioId(String convenioId) {
		this.convenioId = convenioId;
	}



	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (ConvenioSuscEntidad) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public ConvenioSuscEntidad cloneClass(ConvenioSuscEntidad copia, List<String> attributesToSet) {
		
		ConvenioSuscEntidad obj = new ConvenioSuscEntidad(copia,attributesToSet);
		
		return obj;

	}
	
	public ConvenioSuscEntidad(ConvenioSuscEntidad copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
				
		if (attributesToSet.contains("entidadSuscriptora")) {
			this.entidadSuscriptora = copia.entidadSuscriptora;
		}
		
		if (attributesToSet.contains("obliEconomicaEntidad")) {
			this.obliEconomicaEntidad = copia.obliEconomicaEntidad;
		}
		
		if (attributesToSet.contains("organizationId")) {
			this.organizationId = copia.organizationId;
		}		
		
		if (attributesToSet.contains("convenioId")) {
			this.convenioId = copia.convenioId;
		}
		
		
	}
	
	
	public ConvenioSuscEntidad(ConvenioSuscEntidad obj) {

		this.ikey = obj.ikey;		
		this.id = obj.id;
		this.entidadSuscriptora = obj.entidadSuscriptora;
		this.obliEconomicaEntidad = obj.obliEconomicaEntidad;
		this.organizationId = obj.organizationId;		
		this.convenioId = obj.convenioId;
			
	}
	

	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}		

		if (!Util.validValue(this.getConvenioId())) {
			result.add("ConvenioId is not valid [ConvenioId:" + this.getConvenioId() + "]");
		}
		
		return result;
	}
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();	
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESCONV, Context.ESCONV_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		return prefixes;
	}



	@Override
	public String toString() {
		return "ConvenioSuscEntidad [id=" + id + ", entidadSuscriptora=" + entidadSuscriptora
				+ ", obliEconomicaEntidad=" + obliEconomicaEntidad + ", organizationId=" + organizationId
				+ ", convenioId=" + convenioId + "]";
	}





	

	
}
