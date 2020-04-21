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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bici_trayecto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "trayecto")
@PathId(value="/bicicleta-publica/trayecto")
public class BicicletaPublicaTrayecto  implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/bicicleta-publica/usuario/", sujetoFinURI="usuarioId", propiedadURI=Context.ESBICI_URI+"realizaTrayecto", objetoInicioURI="/bicicleta-publica/trayecto/", objetoFinURI="id")
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="startDate")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "startDate")
	private Date startDate;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="endDate")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "endDate")	
	private Date endDate;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="usuarioId", format=Constants.STRING_FORMAT)	
	private String usuarioId;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="bicicletaId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "tieneAsociadaBicicleta")
	@RdfExternalURI(inicioURI="/bicicleta-publica/bicicleta/",finURI="bicicletaId", urifyLevel = 1)
	private String bicicletaId;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="estacionBicicletaOrigenId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "tieneEstacionOrigen")
	@RdfExternalURI(inicioURI="/bicicleta-publica/estacion/",finURI="estacionBicicletaOrigenId", urifyLevel = 1)
	private String estacionBicicletaOrigenId;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="estacionBicicletaDestinoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "tieneEstacionDestino")
	@RdfExternalURI(inicioURI="/bicicleta-publica/estacion/",finURI="estacionBicicletaDestinoId", urifyLevel = 1)
	private String estacionBicicletaDestinoId;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="anclajeOrigenId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "tieneAnclajeOrigen")
	@RdfExternalURI(inicioURI="/bicicleta-publica/anclaje/",finURI="anclajeOrigenId", urifyLevel = 1)
	private String anclajeOrigenId;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="anclajeDestinoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "tieneAnclajeDestino")
	@RdfExternalURI(inicioURI="/bicicleta-publica/anclaje/",finURI="anclajeDestinoId", urifyLevel = 1)
	private String anclajeDestinoId;
	

	public BicicletaPublicaTrayecto()
	{
	}

	public BicicletaPublicaTrayecto(BicicletaPublicaTrayecto copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.startDate = copia.startDate;
		this.endDate = copia.endDate;
		this.usuarioId = copia.usuarioId;
		this.bicicletaId = copia.bicicletaId;
		this.estacionBicicletaOrigenId = copia.estacionBicicletaOrigenId;
		this.estacionBicicletaDestinoId = copia.estacionBicicletaDestinoId;
		this.anclajeOrigenId = copia.anclajeOrigenId;
		this.anclajeDestinoId = copia.anclajeDestinoId;
	}

	
	public BicicletaPublicaTrayecto(BicicletaPublicaTrayecto copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("startDate")) {
			this.startDate = copia.startDate;		
		}
		if (attributesToSet.contains("endDate")) {
			this.endDate = copia.endDate;
		}
		if (attributesToSet.contains("usuarioId")) {
			this.usuarioId = copia.usuarioId;
		}
		if (attributesToSet.contains("bicicletaId")) {
			this.bicicletaId = copia.bicicletaId;
		}
		if (attributesToSet.contains("estacionBicicletaOrigenId")) {
			this.estacionBicicletaOrigenId = copia.estacionBicicletaOrigenId;
		}
		if (attributesToSet.contains("estacionBicicletaDestinoId")) {
			this.estacionBicicletaDestinoId = copia.estacionBicicletaDestinoId;
		}
		if (attributesToSet.contains("anclajeOrigenId")) {
			this.anclajeOrigenId = copia.anclajeOrigenId;
		}
		if (attributesToSet.contains("anclajeDestinoId")) {
			this.anclajeDestinoId = copia.anclajeDestinoId;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "usuario_id", length = 50)
	public String getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	@Column(name = "bicicleta_id", length = 50)
	public String getBicicletaId() {
		return bicicletaId;
	}

	public void setBicicletaId(String bicicletaId) {
		this.bicicletaId = bicicletaId;
	}
	
	@Column(name = "estacion_bicicleta_origen_id", length = 50)
	public String getEstacionBicicletaOrigenId() {
		return estacionBicicletaOrigenId;
	}


	public void setEstacionBicicletaOrigenId(String estacionBicicletaOrigenId) {
		this.estacionBicicletaOrigenId = estacionBicicletaOrigenId;
	}
	
	@Column(name = "estacion_bicicleta_destino_id", length = 50)
	public String getEstacionBicicletaDestinoId() {
		return estacionBicicletaDestinoId;
	}


	public void setEstacionBicicletaDestinoId(String estacionBicicletaDestinoId) {
		this.estacionBicicletaDestinoId = estacionBicicletaDestinoId;
	}
	
	@Column(name = "anclaje_origen_id", length = 50)
	public String getAnclajeOrigenId() {
		return anclajeOrigenId;
	}


	public void setAnclajeOrigenId(String anclajeOrigenId) {
		this.anclajeOrigenId = anclajeOrigenId;
	}
	
	@Column(name = "anclaje_destino_id", length = 50)
	public String getAnclajeDestinoId() {
		return anclajeDestinoId;
	}


	public void setAnclajeDestinoId(String anclajeDestinoId) {
		this.anclajeDestinoId = anclajeDestinoId;
	}

	@Override
	public String toString() {
		return "BicicletaPublicaTrayecto [ikey=" + ikey + ", id=" + id + ", startDate=" + startDate + ", endDate="
				+ endDate + ", usuarioId=" + usuarioId + ", bicicletaId=" + bicicletaId + ", estacionBicicletaOrigenId="
				+ estacionBicicletaOrigenId + ", estacionBicicletaDestinoId=" + estacionBicicletaDestinoId
				+ ", anclajeOrigenId=" + anclajeOrigenId + ", anclajeDestinoId=" + anclajeDestinoId + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.ESBICI, Context.ESBICI_URI);		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaTrayecto) copia, listado);
	}
	
	public BicicletaPublicaTrayecto cloneClass(BicicletaPublicaTrayecto copia, List<String> attributesToSet) {

		BicicletaPublicaTrayecto obj = new BicicletaPublicaTrayecto(copia,attributesToSet);		

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
							
		if (!Util.validValue(this.getStartDate())) {
			result.add("StartDate is not valid [StartDate:" + this.getStartDate() + "]");
		}
		
		return result;
	}
	


	
	




}
