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
@Table(name = "conv_rel_firmante_entidad")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value="/convenio/firmante-entidad")
public class ConvRelFirmanteEntidad implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 5034029821564061777L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del firmante de la entidad. Ejemplo: CONVRELFIRMENTIDAD001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/convenio/suscripcion-por-entidad/", sujetoFinURI="entidadId", propiedadURI=Context.ESCONV_URI+"firmanteEntidad", objetoInicioURI="/convenio/firmante-entidad/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Rol del firmante entidad. Ejemplo: Presidente Localidata")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="firmanteRole", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ORG, propiedad = "role")	
	private String firmanteRole;
	
	@ApiModelProperty(value = "Organizacion (id) asociada al firmante. Ejemplo: ORGLOC0010")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="organizationId")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ORG, propiedad = "organizationId")
	@RdfExternalURI(inicioURI="/convenio/organization/",finURI="organizationId", urifyLevel = 1)
	private String organizationId;
	
	@ApiModelProperty(value = "Entidad (id) asociada al firmante. Ejemplo: CONVSUSENT001")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="entidadId", format=Constants.STRING_FORMAT)
	private String entidadId;

	public ConvRelFirmanteEntidad() {
	}

	public ConvRelFirmanteEntidad(String ikey, String id) {
		this.ikey = ikey;
		this.id = id;
	}

	public ConvRelFirmanteEntidad(String ikey, String id, String firmanteRole, String organizationId,
			 String entidadId) {
		this.ikey = ikey;
		this.entidadId = entidadId;
		this.id = id;
		this.firmanteRole = firmanteRole;
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

	@Column(name = "entidad_id", length = 50)
	public String getEntidadId() {
		return this.entidadId;
	}

	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "firmante_role", length = 200)
	public String getFirmanteRole() {
		return this.firmanteRole;
	}

	public void setFirmanteRole(String firmanteRole) {
		this.firmanteRole = firmanteRole;
	}

	@Column(name = "organization_id", length = 50)
	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

		
	public ConvRelFirmanteEntidad(ConvRelFirmanteEntidad obj) {

		this.ikey = obj.ikey;
		this.entidadId = obj.entidadId;
		this.id = obj.id;
		this.firmanteRole = obj.firmanteRole;
		this.organizationId = obj.organizationId;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (ConvRelFirmanteEntidad) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public ConvRelFirmanteEntidad cloneClass(ConvRelFirmanteEntidad copia, List<String> attributesToSet) {
		
		ConvRelFirmanteEntidad obj = new ConvRelFirmanteEntidad(copia,attributesToSet);
		
		return obj;

	}
	
	public ConvRelFirmanteEntidad(ConvRelFirmanteEntidad copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("firmanteRole")) {
			this.firmanteRole = copia.firmanteRole;
		}		
		
		if (attributesToSet.contains("organizationId")) {
			this.organizationId = copia.organizationId;
		}		
		
		if (attributesToSet.contains("entidadId")) {
			this.entidadId = copia.entidadId;
		}
						
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESCONV, Context.ESCONV_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		prefixes.put(Context.ESCONV, Context.ESCONV_URI);	
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "ConvRelFirmanteEntidad [id=" + id + ", firmanteRole=" + firmanteRole + ", organizationId=" + organizationId
				+ ", entidadId=" + entidadId + "]";
	}
	
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}
		
		
		if (!Util.validValue(this.getEntidadId())) {
			result.add("EntidadId is not valid [EntidadId:" + this.getEntidadId() + "]");
		}


		return result;
	}

}
