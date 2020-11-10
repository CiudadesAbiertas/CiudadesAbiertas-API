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
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.ICallejero;
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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@Table(name = "aviso_queja_sug")
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.OPEN311, propiedad = "ServiceRequest")
@PathId(value="/aviso-queja-sugerencia/aviso-queja-sugerencia")
public class AvisoQuejaSug implements java.io.Serializable, GeoModel, RDFModel, ICallejero {

	@JsonIgnore
	private static final long serialVersionUID = -5896570841942655495L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador de la solicitudes de servicio. Ejemplo: AQSA0002")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la solicitudes de servicio enviadas. Ejemplo: AVISO-ALUMBRADO PUBLICO EXTERIOR")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Identidicador de la solicitudes de servicio. Ejemplo: 0-2")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = Constants.IDENTIFIER)
	private String identifier;
	
	@ApiModelProperty(value = "Texto, que representa el estado de la solicitud de servicio. Ejemplo: abierto")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="stat", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "status")	
	private String stat;
	
	@ApiModelProperty(value = "Texto, explicando por qué se cambió el estado al estado actual o detalles adicionales sobre el estado actual. Ejemplo: abierto")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="statusNotes", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "statusNotes")
	private String statusNotes;
	
	@ApiModelProperty(value = "Fecha, que representa la fecha de apertura de las solicitudes enviadas. Ejemplo: 2018-01-01T01:30:45")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "openDate")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "hasOpenDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date openDate;
	
	@ApiModelProperty(value = "Fecha, que representa la fecha de cierre de las solicitudes enviadas. Ejemplo: 2020-03-11T10:57:02")
	@CsvBindByPosition(position=7)
	@CsvBindByName (column = "closeDate")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "hasCloseDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date closeDate;
	
	@ApiModelProperty(value = "Fecha, que representa la fecha de cierre de las solicitudes enviadas. Ejemplo: 2020-03-11T10:57:02")
	@CsvBindByPosition(position=8)
	@CsvBindByName (column = "updateDate")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.TIME, propiedad = "hasUpdateDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date updateDate;
	
	@ApiModelProperty(value = "Esta propiedad de datos proporciona más información sobre la solicitud de servicio. Ejemplo: AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="details", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "details")
	private String details;
	
	@ApiModelProperty(value = "Representa cómo se realizó la solicitud de servicio. Ejemplo: PRESENCIAL/TELEFONICOES")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="source", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "source")	
	private String source;
	
	@ApiModelProperty(value = "texto, que muestra el tipo de solicitud de servicio. Ejemplo: ALUMBRADO - 02. CALLE APAGADA (>1 FAROLA)")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="typeName", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "311TypeName")
	@RdfBlankNode(tipo=Context.OPEN311_URI+"Type", propiedad=Context.OPEN311_URI+"has311Type", nodoId="Type")	
	private String typeName;
	
	@ApiModelProperty(value = "Representa el código único de cada tipo de solicitud de servicio. Ejemplo: 3745")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="typeCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OPEN311, propiedad = "311TypeCode")
	@RdfBlankNode(tipo=Context.OPEN311_URI+"Type", propiedad=Context.OPEN311_URI+"has311Type", nodoId="Type")	
	private String typeCode; 
	
	@ApiModelProperty(value = "Calle de la solicitud de servicio. Ejemplo: CALLE ISLA DE AROSA 1 --")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;
	
	@ApiModelProperty(value = "Código postal de la solicitud de servicio. Ejemplo: 28035")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@ApiModelProperty(value = "Identificador de la solicitud de servicio. Ejemplo: PORTAL000098")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(value = "Coordenada X de la solicitud de servicio. Ejemplo: 439399.64")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="xETRS89")	
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	@ApiModelProperty(value = "Coordenada Y de la solicitud de servicio. Ejemplo: 4481455.07")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Identificador del municipio de la solicitud de servicio. Ejemplo: 28079")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la solicitud de servicio")
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del barrio de la solicitud de servicio. Ejemplo: 280796062")
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="barrioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId", urifyLevel = 1)		
	private String barrioId;
	
	@ApiModelProperty(value = "Identificador del distrito de la solicitud de servicio. Ejemplo: 28079606")
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;

	@ApiModelProperty(hidden = true)	
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	
	private Double distance;

	public AvisoQuejaSug() {
	}

	public AvisoQuejaSug(String ikey) {
		this.ikey = ikey;
	}

	

	public AvisoQuejaSug(AvisoQuejaSug copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.identifier = copia.identifier;
		this.stat = copia.stat;
		this.statusNotes = copia.statusNotes;
		this.openDate = copia.openDate;
		this.closeDate = copia.closeDate;
		this.updateDate = copia.updateDate;
		this.details = copia.details;
		this.source = copia.source;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.barrioId = copia.barrioId;
		this.distritoId = copia.distritoId;
		this.streetAddress = copia.streetAddress;
		this.postalCode = copia.postalCode;
		this.latitud = copia.latitud;
		this.longitud = copia.longitud;
		this.typeName = copia.typeName;
		this.typeCode = copia.typeCode;
		this.x = copia.x;
		this.y = copia.y;
		this.portalId = copia.portalId;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@Column(name = "id", length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "identifier", length = 50)
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Column(name = "status", length = 50)
	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	@Column(name = "status_notes", length = 2000)
	public String getStatusNotes() {
		return this.statusNotes;
	}

	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "open_date", length = 19)
	public Date getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date", length = 19)
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "details", length = 2000)
	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Column(name = "source", length = 200)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "streetAddress", length = 200)
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String address) {
		this.streetAddress = address;
	}

	@Column(name = "postal_code", length = 200)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Transient
	public BigDecimal getLatitud()
	{
		return this.latitud;
	}

	public void setLatitud(BigDecimal latitud)
	{
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud()
	{
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud)
	{
		this.longitud = longitud;
	}

	@Column(name = "type_name", length = 200)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type_code", length = 50)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "municipio_id", length = 10)
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
	
	@Column(name = "barrio_id", length = 50)
	public String getBarrioId() {
		return this.barrioId;
	}

	public void setBarrioId(String barrioId) {
		this.barrioId = barrioId;
	}

	@Column(name = "distrito_id", length = 50)
	public String getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
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
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}
	

	@Column(name = "portal_id", length = 20)
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
	}


	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated = portalIdIsolated;
	}

	@Override
	public String toString() {
		return "AvisoQuejaSug [ikey=" + ikey + ", id=" + id + ", title=" + title
				+ ", identifier=" + identifier + ", stat=" + stat + ", statusNotes=" + statusNotes
				+ ", openDate=" + openDate + ", closeDate=" + closeDate + ", updateDate=" + updateDate + ", details="
				+ details + ", source=" + source + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", barrioId=" + barrioId + ", distritoId=" + distritoId + ", address=" + streetAddress + ", postalCode="
				+ postalCode + ", x=" + x + ", y=" + y + ", typeName=" + typeName
				+ ", typeCode=" + typeCode + ", portalId="+portalId+"]";
	}

	
	
	public AvisoQuejaSug (AvisoQuejaSug copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
						
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
		}
		
		if (attributesToSet.contains("identifier")) {
			this.identifier = copia.identifier;		
		}
		
		if (attributesToSet.contains("stat")) {
			this.stat = copia.stat;		
		}
		
		if (attributesToSet.contains("statusNotes")) {
			this.statusNotes = copia.statusNotes;		
		}
		
		if (attributesToSet.contains("openDate")) {
			this.openDate = copia.openDate;		
		}
		
		if (attributesToSet.contains("closeDate")) {
			this.closeDate = copia.closeDate;		
		}
		
		if (attributesToSet.contains("updateDate")) {
			this.updateDate = copia.updateDate;		
		}
				
		if (attributesToSet.contains("details")) {
			this.details = copia.details;		
		}
		
		if (attributesToSet.contains("source")) {
			this.source = copia.source;		
		}
		
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;		
		}
		
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;		
		}
		
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;		
		}
								
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;		
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;		
		}
		
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;		
		}
		
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;		
		}
		
		if (attributesToSet.contains("typeName")) {
			this.typeName = copia.typeName;		
		}
			
		if (attributesToSet.contains("typeCode")) {
			this.typeCode = copia.typeCode;		
		}
		
		if (attributesToSet.contains("barrioId")) {
			this.barrioId = copia.barrioId;		
		}
		
		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;		
		}

	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.OPEN311, Context.OPEN311_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);	
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.TIME, Context.TIME_URI);	
		prefixes.put(Context.ESADM, Context.ESADM_URI);	
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (AvisoQuejaSug) copia,listado) ;
	}

	public AvisoQuejaSug cloneClass (AvisoQuejaSug copia, List<String> attributesToSet) {
		
		AvisoQuejaSug obj = new AvisoQuejaSug(copia,attributesToSet);
		
		return obj;
	}
		
	// TODO valiaciones de equipamientos ver si es posible realizar mediante
	// anotaciones en model el saber los campos obligatorios
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getIdentifier())) {
			result.add("Identifier is not valid [Identifier:" + this.getIdentifier() + "]");
		}

		if (!Util.validValue(this.getStat())) {
			result.add("Status is not valid [Stat:" + this.getStat() + "]");
		}

		if (!Util.validValue(this.getOpenDate())) {
			result.add("OpenDate is not valid [openDate:" + this.getOpenDate() + "]");
		}

		if (!Util.validValue(this.getSource())) {
			result.add("Source is not valid [source:" + this.getSource() + "]");
		}

		return result;
	}
}
