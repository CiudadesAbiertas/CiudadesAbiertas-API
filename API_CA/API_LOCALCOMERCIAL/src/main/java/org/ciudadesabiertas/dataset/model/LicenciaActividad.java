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
 *
 */
@Entity
@Table(name = "local_comercial_licencia")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESCOM, propiedad = "Licencia")
@PathId(value="/localComercial/licenciaActividad")
public class LicenciaActividad implements java.io.Serializable, RDFModel
{	
	private static final long serialVersionUID = -6739471497337390438L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
		
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@CsvBindByPosition(position=2)
	@CsvBindByName(column="referencia", format=Constants.STRING_FORMAT)	
	private String referencia;	
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="asociadaA", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "asociadaA")
	@RdfExternalURI(inicioURI="/localComercial/localComercial/", finURI="asociadaA")
	private String asociadaA;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="autorizaActividadEconomica", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "autorizaActividadEconomica")
	private String autorizaActividadEconomica;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="estadoTramitacion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "estadoTramitacion")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/comercio/tipo-estado-tramite-licencia/", finURI="estadoTramitacion", urifyLevel=1)
	private String estadoTramitacion;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="fechaAlta", format=Constants.STRING_FORMAT)
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "fechaAlta", typeURI=Context.XSD_URI+"date")
	private Date fechaAlta;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="fechaCese", format=Constants.STRING_FORMAT)
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "fechaCese", typeURI=Context.XSD_URI+"date")
	private Date fechaCese;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="fechaSolicitud", format=Constants.STRING_FORMAT)
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "fechaSolicitud", typeURI=Context.XSD_URI+"date")
	private Date fechaSolicitud;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="seOtorgaA", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCOM, propiedad = "seOtorgaA")	
	private String seOtorgaA;

	public LicenciaActividad()
	{
	}

	public LicenciaActividad(String ikey, String id, Date fechaSolicitud)
	{
		this.ikey = ikey;
		this.id = id;
		this.fechaSolicitud = fechaSolicitud;
	}

	public LicenciaActividad(LicenciaActividad copia)
	{
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.asociadaA = copia.asociadaA;
		this.autorizaActividadEconomica = copia.autorizaActividadEconomica;
		this.estadoTramitacion = copia.estadoTramitacion;
		this.fechaAlta = copia.fechaAlta;
		this.fechaCese = copia.fechaCese;
		this.fechaSolicitud = copia.fechaSolicitud;
		this.seOtorgaA = copia.seOtorgaA;
		this.referencia = copia.referencia;
	}
	
	public LicenciaActividad(LicenciaActividad obj, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = obj.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = obj.id;
		}
		if (attributesToSet.contains("asociadaA")) {
			this.asociadaA = obj.asociadaA;
		}
		if (attributesToSet.contains("autorizaActividadEconomica")) {
			this.autorizaActividadEconomica = obj.autorizaActividadEconomica;
		}
		if (attributesToSet.contains("estadoTramitacion")) {
			this.estadoTramitacion = obj.estadoTramitacion;
		}
		if (attributesToSet.contains("fechaAlta")) {
			this.fechaAlta = obj.fechaAlta;
		}
		if (attributesToSet.contains("fechaCese")) {
			this.fechaCese = obj.fechaCese;
		}
		if (attributesToSet.contains("fechaSolicitud")) {
			this.fechaSolicitud = obj.fechaSolicitud;
		}
		if (attributesToSet.contains("seOtorgaA")) {
			this.seOtorgaA = obj.seOtorgaA;
		}
		if (attributesToSet.contains("referencia")) {
			this.referencia = obj.referencia;
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

	@Column(name = "asociada_a", length = 200)
	public String getAsociadaA()
	{
		return this.asociadaA;
	}

	public void setAsociadaA(String asociadaA)
	{
		this.asociadaA = asociadaA;
	}

	@Column(name = "autoriza_actividad_economica", length = 400)
	public String getAutorizaActividadEconomica()
	{
		return this.autorizaActividadEconomica;
	}

	public void setAutorizaActividadEconomica(String autorizaActividadEconomica)
	{
		this.autorizaActividadEconomica = autorizaActividadEconomica;
	}

	@Column(name = "estado_tramitacion", length = 200)
	public String getEstadoTramitacion()
	{
		return this.estadoTramitacion;
	}

	public void setEstadoTramitacion(String estadoTramitacion)
	{
		this.estadoTramitacion = estadoTramitacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", length = 19)
	public Date getFechaAlta()
	{
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_cese", length = 19)
	public Date getFechaCese()
	{
		return this.fechaCese;
	}

	public void setFechaCese(Date fechaCese)
	{
		this.fechaCese = fechaCese;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_solicitud", nullable = false, length = 19)
	public Date getFechaSolicitud()
	{
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud)
	{
		this.fechaSolicitud = fechaSolicitud;
	}

	@Column(name = "se_otorga_a", length = 200)
	public String getSeOtorgaA()
	{
		return this.seOtorgaA;
	}

	public void setSeOtorgaA(String seOtorgaA)
	{
		this.seOtorgaA = seOtorgaA;
	}
	
	@Column(name = "referencia", length = 20)
	public String getReferencia()
	{
		return referencia;
	}

	public void setReferencia(String referencia)
	{
		this.referencia = referencia;
	}

	@Override
	public Map<String, String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.ESCOM, Context.ESCOM_URI);
		
		return prefixes;
	}

	@Override
	public String toString()
	{
		return "LicenciaActividad [ikey=" + ikey + ", id=" + id + ", referencia=" + referencia + ", asociadaA=" + asociadaA + ", autorizaActividadEconomica=" + autorizaActividadEconomica + ", estadoTramitacion=" + estadoTramitacion + ", fechaAlta=" + fechaAlta + ", fechaCese=" + fechaCese + ", fechaSolicitud="
				+ fechaSolicitud + ", seOtorgaA=" + seOtorgaA + "]";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (LicenciaActividad) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public LicenciaActividad cloneClass(LicenciaActividad copia, List<String> attributesToSet) {
		
		LicenciaActividad obj = new LicenciaActividad(copia,attributesToSet);		
		
		return obj;
	}
		
		
	
	//LicenciaActividad
	// TODO valiaciones  ver si es posible realizar mediante
	// anotaciones en model el saber los campos obligatorios
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getEstadoTramitacion())) {
			result.add("estadoTramitacion is not valid [estadoTramitacion:" + this.getEstadoTramitacion() + "]");
		}

		if (!Util.validValue(this.getFechaAlta())) {
			result.add("fechaAlta is not valid [fechaAlta:" + this.getFechaAlta() + "]");
		}

		if (!Util.validValue(this.getAutorizaActividadEconomica())) {
			result.add("autorizaActividadEconomica is not valid [autorizaActividadEconomica:"
					+ this.getAutorizaActividadEconomica() + "]");
		}

		if (!Util.validValue(this.getSeOtorgaA())) {
			result.add("seOtorgaA is not valid [seOtorgaA:" + this.getSeOtorgaA() + "]");
		}

		return result;
	}
}
