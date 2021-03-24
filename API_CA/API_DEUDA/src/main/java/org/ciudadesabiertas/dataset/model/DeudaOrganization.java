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
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.ICallejero;
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
@Table(name = "deuda_organization")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ORG, propiedad = "Organization")
@PathId(value = "/deuda-publica/organization")
public class DeudaOrganization implements java.io.Serializable, RDFModel, ICallejero {

	@JsonIgnore
	private static final long serialVersionUID = -1514640833269124192L;	

	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;

	@ApiModelProperty(value = "Identificador de la organización. Ejemplo: ORG0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@ApiModelProperty(value = "Nombre de la organización. Ejemplo: Alcaldia Ayto XXX")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Web de la organización. Ejemplo: https://datos.madrid.es/FwFront/portal_egob/new/img/portal_logo_h.png")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@ApiModelProperty(value = "Identificador del municipio de la organización. Ejemplo: 28006")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId")
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la organización. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito de la organización. Ejemplo: 2800601")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "distritoId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito de la organización. Ejemplo: Unico")
	@CsvBindByPosition(position=7)
    @CsvBindByName (column = "distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@ApiModelProperty(value = "Calle de la organización. Ejemplo: CALLE BUSTAMANTE V 16")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;
	
	@ApiModelProperty(value = "Código postal de la organización. Ejemplo: 28045")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@ApiModelProperty(value = "Identificador de la calle de la organización. Ejemplo: PORTAL000112")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(value = "Clasificación de la organización. Ejemplo: tipo-clasificación2")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="classification", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ORG, propiedad = "classification")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/convenios/tipo-entidad/", finURI="classification", urifyLevel=2)	
	private String classification;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	
	@ApiModelProperty(value = "Fax de la organización. Ejemplo: 1123333333")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="contactPointFaxNumber", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "faxNumber")
	@RdfBlankNode(tipo=Context.OCDS_URI+"ContactPoint", propiedad=Context.SCHEMA_URI+"contactPoint", nodoId="contactPoint")
	private String contactPointFaxNumber;
	
	@ApiModelProperty(value = "Correo electrónico de la organización. Ejemplo: info@aytoxxx.com")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="contactPointEmail", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	@RdfBlankNode(tipo=Context.OCDS_URI+"ContactPoint", propiedad=Context.SCHEMA_URI+"contactPoint", nodoId="contactPoint")
	private String contactPointEmail;
	
	@ApiModelProperty(value = "Teléfono de la organización. Ejemplo: 1123333335")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="contactPointTelephone", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	@RdfBlankNode(tipo=Context.OCDS_URI+"ContactPoint", propiedad=Context.SCHEMA_URI+"contactPoint", nodoId="contactPoint")
	private String contactPointTelephone;
	
	@ApiModelProperty(value = "Nombre del contacto de la organización. Ejemplo: Centro Cultural Ayto XXX")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="contactPointTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "title")
	@RdfBlankNode(tipo=Context.OCDS_URI+"ContactPoint", propiedad=Context.SCHEMA_URI+"contactPoint", nodoId="contactPoint")
	private String contactPointTitle;
	
		

	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	
	@Column(name = "title", nullable = false, length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "URL", nullable = false, length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

	@Column(name = "municipio_id", nullable = false, length = 50)
	public String getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", nullable = false, length = 200)
	public String getMunicipioTitle() {
		return municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
	}
	
	@Column(name = "distrito_id", nullable = false, length = 50)
	public String getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	@Column(name = "distrito_title", nullable = false, length = 200)
	public String getDistritoTitle() {
		return distritoTitle;
	}

	public void setDistritoTitle(String distritoTitle) {
		this.distritoTitle = distritoTitle;
	}


	@Column(name = "street_address", nullable = false, length = 200)
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", nullable = false, length = 10)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "portal_id", nullable = false, length = 10)
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

	@Transient
	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated = portalIdIsolated;
	}

	@Column(name = "contactPoint_faxNumber", nullable = false, length = 200)
	public String getContactPointFaxNumber() {
		return contactPointFaxNumber;
	}

	public void setContactPointFaxNumber(String contactPointFaxNumber) {
		this.contactPointFaxNumber = contactPointFaxNumber;
	}

	@Column(name = "contactPoint_email", nullable = false, length = 200)
	public String getContactPointEmail() {
		return contactPointEmail;
	}

	public void setContactPointEmail(String contactPointEmail) {
		this.contactPointEmail = contactPointEmail;
	}

	@Column(name = "contactPoint_telephone", nullable = false, length = 200)
	public String getContactPointTelephone() {
		return contactPointTelephone;
	}

	public void setContactPointTelephone(String contactPointTelephone) {
		this.contactPointTelephone = contactPointTelephone;
	}

	@Column(name = "contactPoint_title", nullable = false, length = 200)
	public String getContactPointTitle() {
		return contactPointTitle;
	}

	public void setContactPointTitle(String contactPointTitle) {
		this.contactPointTitle = contactPointTitle;
	}
	
	@Column(name = "classification", length = 200)
	public String getClassification() {
		return this.classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	
	
	public DeudaOrganization() {
		
	}

	

	public DeudaOrganization(DeudaOrganization copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;		
		this.title = copia.title;		
		this.url = copia.url;
		this.portalId = copia.portalId;
		this.portalIdIsolated= copia.portalIdIsolated;
		this.contactPointEmail= copia.contactPointEmail;
		this.contactPointFaxNumber= copia.contactPointFaxNumber;
		this.contactPointTelephone= copia.contactPointTelephone;
		this.contactPointTitle= copia.contactPointTitle;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.distritoId = copia.distritoId;
		this.distritoTitle = copia.distritoTitle;
		this.postalCode = copia.postalCode;
		this.streetAddress = copia.streetAddress;								
		this.classification = copia.classification;			
	}
	
	

	public DeudaOrganization(DeudaOrganization copia, List<String> attributesToSet)
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
		
		if (attributesToSet.contains("url")) {
			this.url = copia.url;		
		}	
		
		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;		
		}
		
		if (attributesToSet.contains("portalIdIsolated")) {
			this.portalIdIsolated = copia.portalIdIsolated;		
		}
		
		if (attributesToSet.contains("contactPointEmail")) {
			this.contactPointEmail = copia.contactPointEmail;		
		}
		
		if (attributesToSet.contains("contactPointFaxNumber")) {
			this.contactPointFaxNumber = copia.contactPointFaxNumber;		
		}
		
		if (attributesToSet.contains("contactPointTelephone")) {
			this.contactPointTelephone = copia.contactPointTelephone;		
		}
		
		if (attributesToSet.contains("contactPointTitle")) {
			this.contactPointTitle = copia.contactPointTitle;		
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
		
		if (attributesToSet.contains("distritoTitle")) {
			this.distritoTitle = copia.distritoTitle;		
		}
		
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;		
		}
		
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;		
		}	
		
		if (attributesToSet.contains("classification")) {
			this.classification = copia.classification;		
		}
			
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((DeudaOrganization) copia, listado);
	}
	
	public DeudaOrganization cloneClass(DeudaOrganization copia, List<String> attributesToSet) {

	  DeudaOrganization obj = new DeudaOrganization(copia,attributesToSet);		

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
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.ORG, Context.ORG_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.OCDS, Context.OCDS_URI);
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "DeudaOrganization [ikey=" + ikey + ", id=" + id + ", title=" + title + ", url=" + url
				+ ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle + ", distritoId=" + distritoId
				+ ", distritoTitle=" + distritoTitle + ", streetAddress=" + streetAddress + ", postalCode=" + postalCode
				+ ", portalId=" + portalId + ", classification=" + classification + ", portalIdIsolated="
				+ portalIdIsolated + ", contactPointFaxNumber=" + contactPointFaxNumber + ", contactPointEmail="
				+ contactPointEmail + ", contactPointTelephone=" + contactPointTelephone + ", contactPointTitle="
				+ contactPointTitle + "]";
	}

	
	
	
}
