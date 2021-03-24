package org.ciudadesabiertas.dataset.model;
// Generated 30 nov. 2020 11:06:05 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
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
@Table(name = "empleo_rel_boletin_convoca", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value = "/empleo/relacion-boletin-convocatoria")
public class RelBoletinConvoca implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -107294817250088761L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@ApiModelProperty(value = "Identificador de la relación entre boletin y convenio. Ejemplo: rel01")
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI = "/empleo/boletin-convocatoria/", sujetoFinURI = "convocatoriaId", propiedadURI = Context.ESEMPLEO_URI
	+ "sePublicaEn", objetoInicioURI = "/empleo/boletin-oficial/", objetoFinURI = "boletinId")
	private String id;

	@ApiModelProperty(value = "Boletin (id) asociada a la convocatoria. Ejemplo: boletin001")
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "boletinId")
	private String boletinId;

	@ApiModelProperty(value = "Convocatoria (id) asociada al Boletin. Ejemplo: convocatoria001")
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "convocatoriaId")
	private String convocatoriaId;

	public RelBoletinConvoca() {
	}

	public RelBoletinConvoca(String ikey, String id, String boletinId, String convocatoriaId) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.boletinId = boletinId;
		this.convocatoriaId = convocatoriaId;
	}

	public RelBoletinConvoca(RelBoletinConvoca relObj) {
		this.ikey = relObj.ikey;
		this.id = relObj.id;
		this.boletinId = relObj.boletinId;
		this.convocatoriaId = relObj.convocatoriaId;
	}

	public RelBoletinConvoca(RelBoletinConvoca copia, List<String> attributesToSet) {

		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}

		if (attributesToSet.contains("boletinId")) {
			this.boletinId = copia.boletinId;
		}

		if (attributesToSet.contains("convocatoriaId")) {
			this.convocatoriaId = copia.convocatoriaId;
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

	@Column(name = "boletin_id", unique = true, length = 50)
	public String getBoletinId() {
		return this.boletinId;
	}

	public void setBoletinId(String boletinId) {
		this.boletinId = boletinId;
	}

	@Column(name = "convocatoria_id", unique = true, length = 50)
	public String getConvocatoriaId() {
		return this.convocatoriaId;
	}

	public void setConvocatoriaId(String convocatoriaId) {
		this.convocatoriaId = convocatoriaId;
	}

	@Column(name = "id", unique = true, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (RelBoletinConvoca) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public RelBoletinConvoca cloneClass(RelBoletinConvoca copia, List<String> attributesToSet) {
		
		RelBoletinConvoca obj = new RelBoletinConvoca(copia,attributesToSet);
		
		return obj;

	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESEMPLEO, Context.ESEMPLEO_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "RelBoletinConvoca [id=" + id + ", boletinId=" + boletinId + ", convocatoriaId=" + convocatoriaId + "]";
	}
	
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}
		
		
		if (!Util.validValue(this.getConvocatoriaId())) {
			result.add("ConvocatoriaId is not valid [ConvocatoriaId:" + this.getConvocatoriaId() + "]");
		}
		
		if (!Util.validValue(this.getBoletinId())) {
			result.add("BoletinId is not valid [BoletinId:" + this.getBoletinId() + "]");
		}


		return result;
	}

}
