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
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
import org.ciudadesabiertas.model.IGeoModelXY;
import org.ciudadesabiertas.model.ICallejero;
import org.ciudadesabiertas.model.IEquipamiento;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "cont_acus_estacion_medida")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.NOISE, propiedad = "EstacionMedida")
@PathId(value="/contaminacion-acustica/estacion")
public class ContAcusticaEstacionMedida  implements java.io.Serializable, IGeoModelXY, RDFModel, ICallejero, IEquipamiento {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la Estación de Medida. Ejemplo: CONTACUSTESTMED001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la Estación de Medida. Ejemplo: Dispositivo que detecta los ruidos I")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;	
	
	@ApiModelProperty(value = "Fecha de Alta de la Estación de Medida. Ejemplo: 2020-03-31 08:00:00")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "fechaAlta")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.ESEQUIP, propiedad = "fechaAlta",typeURI=Context.XSD_URI+"dateTime" ))
	private Date fechaAlta;
	
	@ApiModelProperty(value = "Fecha de Baja de la Estación de Medida. Ejemplo: 2020-07-30 09:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "fechaBaja")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.ESEQUIP, propiedad = "fechaBaja",typeURI=Context.XSD_URI+"dateTime" ))
	private Date fechaBaja;
	
	@ApiModelProperty(value = "Nombre del equipamiento del evento. Ejemplo: Teatro Auditorio Ciudad de Alcobendas")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="equipamientoTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoTitle;
	
	@ApiModelProperty(value = "Identificador del equipamiento de la Estación de Medida. Ejemplo: EQ0044")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="equipamientoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "equipamiento")
	@RdfExternalURI(inicioURI="/equipamiento/equipamiento/",finURI="equipamientoId", urifyLevel = 1)
	private String equipamientoId;
	
	@ApiModelProperty(value = "Tipo de equipamiento. Ejemplo: Aparato de medición A.C.M.E")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="tipoEquipamiento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "tipoEquipamiento")	
	private String tipoEquipamiento;
	
	@ApiModelProperty(value = "Observaciones. Ejemplo: estacion-medida-contaminacion-acustica")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="observes", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "observes")	
	private String observes;
	
	@ApiModelProperty(value = "Calle del evento. Ejemplo: CL BLAS DE OTERO 4")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")		
	private String streetAddress;
	
	@ApiModelProperty(value = "Código postal del evento. Ejemplo: 28100")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@ApiModelProperty(value = "Identificador de la calle del evento. Ejemplo: PORTAL000119")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(value = "Coordenada X del equipamiento. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal x;	
	
	@ApiModelProperty(value = "Coordenada Y del equipamiento. Ejemplo: 4474637.17000")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
		
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Identificador del municipio de la estación. Ejemplo: 28006")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId")	
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la estación. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")	
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId", urifyLevel = 1)
	private String barrioId;
	
	@ApiModelProperty(value = "Nombre del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="barrioTitle", format=Constants.STRING_FORMAT)
	private String barrioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito de la estación. Ejemplo: 2800601")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")	
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito de la estación. Ejemplo: Unico")
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfBlankNode(tipo=Context.ESEQUIP_URI+"Equipamiento", propiedad=Context.ESEQUIP_URI+"equipamiento", nodoId="equipamiento")
	private String equipamientoIdIsolated;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	
	private Double distance;
	

	public ContAcusticaEstacionMedida()
	{
	}
	
	

	

	public ContAcusticaEstacionMedida(ContAcusticaEstacionMedida copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.fechaAlta = copia.fechaAlta;
		this.fechaBaja = copia.fechaBaja;
		this.postalCode = copia.postalCode;
		this.portalId = copia.portalId;
		this.streetAddress = copia.streetAddress;
		this.equipamientoId = copia.equipamientoId;
		this.equipamientoTitle = copia.equipamientoTitle;
		this.tipoEquipamiento = copia.tipoEquipamiento;
		this.observes = copia.observes;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.barrioId = copia.barrioId;
		this.barrioTitle = copia.barrioTitle;
		this.distritoId = copia.distritoId;
		this.distritoTitle = copia.distritoTitle;
		this.x = copia.x;
		this.y = copia.y;	
		
	}

	
	public ContAcusticaEstacionMedida(ContAcusticaEstacionMedida copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
		}
		
		if (attributesToSet.contains("fechaAlta")) {
			this.fechaAlta = copia.fechaAlta;	
		}
		
		if (attributesToSet.contains("fechaBaja")) {
			this.fechaBaja = copia.fechaBaja;	
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;	
		}
		
		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;	
		}
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;	
		}
		
		if (attributesToSet.contains("equipamientoId")) {
			this.equipamientoId = copia.equipamientoId;	
		}

		if (attributesToSet.contains("equipamientoTitle")) {
			this.equipamientoTitle = copia.equipamientoTitle;	
		}
		
		if (attributesToSet.contains("tipoEquipamiento")) {
			this.tipoEquipamiento = copia.tipoEquipamiento;	
		}
		
		if (attributesToSet.contains("observes")) {
			this.observes = copia.observes;	
		}		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;	
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;	
		}
		if (attributesToSet.contains("barrioId")) {
			this.barrioId = copia.barrioId;	
		}
		if (attributesToSet.contains("barrioTitle")) {
			this.barrioTitle = copia.barrioTitle;	
		}
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;	
		}
		if (attributesToSet.contains("distritoTitle")) {
			this.distritoTitle = copia.distritoTitle;	
		}
		
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
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

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89", precision = 13, scale = 5)
	public BigDecimal getX()
	{
		return x;
	}

	
	public void setX(BigDecimal x)
	{
		this.x = x;
	}

	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89", precision = 13, scale = 5)
	public BigDecimal getY()
	{
		return y;
	}

	
	public void setY(BigDecimal y)
	{
		this.y = y;
	}
	
	@Transient
	public BigDecimal getLatitud() {
		return this.latitud;
	}

	@Transient
	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud() {
		return this.longitud;
	}

	@Transient
	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	
	
	@Transient
	public Double getDistance() {
		return distance;
	}


	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", length = 19)
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_baja", length = 19)
	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "portal_id", length = 50)
	public String getPortalId() {
		return this.portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	@Column(name = "street_address", length = 200)
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "equipamiento_id", length = 50)
	public String getEquipamientoId() {
		return this.equipamientoId;
	}

	public void setEquipamientoId(String equipamientoId) {
		this.equipamientoId = equipamientoId;
	}

	@Column(name = "equipamiento_title", length = 400)
	public String getEquipamientoTitle() {
		return this.equipamientoTitle;
	}

	public void setEquipamientoTitle(String equipamientoTitle) {
		this.equipamientoTitle = equipamientoTitle;
	}

	@Column(name = "tipo_equipamiento", length = 200)
	public String getTipoEquipamiento() {
		return this.tipoEquipamiento;
	}

	public void setTipoEquipamiento(String tipoEquipamiento) {
		this.tipoEquipamiento = tipoEquipamiento;
	}
	
	@Column(name = "municipio_id", length = 50)
	public String getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle() {
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "distrito_id", length = 50)
	public String getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	@Column(name = "distrito_title", length = 200)
	public String getDistritoTitle() {
		return this.distritoTitle;
	}

	public void setDistritoTitle(String distritoTitle) {
		this.distritoTitle = distritoTitle;
	}
	
	@Column(name = "barrio_title", length = 400)
	public String getBarrioTitle() {
		return this.barrioTitle;
	}

	public void setBarrioTitle(String barrioTitle) {
		this.barrioTitle = barrioTitle;
	}
	
	@Column(name = "barrio_id", length = 50)
	public String getBarrioId() {
		return this.barrioId;
	}

	public void setBarrioId(String barrioId) {
		this.barrioId = barrioId;
	}

	@Column(name = "observes", length = 200)
	public String getObserves() {
		return this.observes;
	}

	public void setObserves(String observes) {
		this.observes = observes;
	}




	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
	}





	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated = portalIdIsolated;
	}




	@Transient
	public String getEquipamientoIdIsolated() {
		return equipamientoIdIsolated;
	}





	public void setEquipamientoIdIsolated(String equipamientoIdIsolated) {
		this.equipamientoIdIsolated = equipamientoIdIsolated;
	}





	@Override
	public String toString() {
		return "EstacionMedida [id=" + id + ", title=" + title + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja
				+ ", streetAddress=" + streetAddress + ", postalCode=" + postalCode + ", portalId=" + portalId
				+ ", portalIdIsolated=" + portalIdIsolated + ", equipamientoTitle=" + equipamientoTitle
				+ ", equipamientoId=" + equipamientoId + ", equipamientoIdIsolated=" + equipamientoIdIsolated
				+ ", tipoEquipamiento=" + tipoEquipamiento + ", observes=" + observes + ", x=" + x + ", y=" + y
				+ ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}





	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);		
		prefixes.put(Context.NOISE, Context.NOISE_URI);
		prefixes.put(Context.ESEQUIP, Context.ESEQUIP_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.SOSA, Context.SOSA_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((ContAcusticaEstacionMedida) copia, listado);
	}
	
	public ContAcusticaEstacionMedida cloneClass(ContAcusticaEstacionMedida copia, List<String> attributesToSet) {

		ContAcusticaEstacionMedida obj = new ContAcusticaEstacionMedida(copia,attributesToSet);		

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
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		return result;
	}
	


	
	




}
