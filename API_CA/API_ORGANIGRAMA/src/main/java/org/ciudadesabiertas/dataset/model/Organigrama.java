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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.GeoModel;
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
@Table(name = "organigrama")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ORG, propiedad = "Organization")
@PathId(value = "/organigrama/organizacion")
public class Organigrama implements java.io.Serializable, GeoModel, RDFModel
{
	@JsonIgnore
	private static final long serialVersionUID = 3254116450000749613L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "title", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;

	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "unidadRaiz", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ORGES, propiedad = "unidadRaiz")
	@RdfExternalURI(inicioURI = "/organigrama/organizacion/", finURI = "unidadRaiz")
	private String unidadRaiz;

	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "unitOf", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ORG, propiedad = "unitOf")
	@RdfExternalURI(inicioURI = "/organigrama/organizacion/", finURI = "unitOf")
	private String unitOf;

	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "nivelJerarquico")
	@Rdf(contexto = Context.ORGES, propiedad = "nivelJerarquico", typeURI = Context.XSD_URI + "int")
	private Integer nivelJerarquico;

	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "headOfName", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.FOAF, propiedad = "name")
	@RdfBlankNode(tipo = Context.FOAF_URI + "Agent", propiedad = Context.ORG_URI + "headOf", nodoId = "agentNode")
	private String headOfName;

	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "foundingDate", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "foundingDate", typeURI = Context.XSD_URI + "date")
	private Date foundingDate;

	@CsvBindByPosition(position = 8)
	@CsvBindByName(column = "dissolutionDate", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "dissolutionDate", typeURI = Context.XSD_URI + "date")
	private Date dissolutionDate;

	@CsvBindByPosition(position = 9)
	@CsvBindByName(column = "estadoEntidadId")
	@Rdf(contexto = Context.ORGES, propiedad = "estadoEntidad")
	@RdfExternalURI(inicioURI = "http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/organizacion/estado-entidad/", finURI = "estadoEntidadId")
	private String estadoEntidadId;

	@CsvBindByPosition(position = 10)
	@CsvBindByName(column = "estadoEntidadTitle")
	private String estadoEntidadTitle;

	@CsvBindByPosition(position = 11)
	@CsvBindByName(column = "nivelAdministracionId")
	@Rdf(contexto = Context.ORGES, propiedad = "nivelAdministracion")
	@RdfExternalURI(inicioURI = "http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/organizacion/nivel-administracion/", finURI = "nivelAdministracionId")
	private String nivelAdministracionId;

	@CsvBindByPosition(position = 12)
	@CsvBindByName(column = "nivelAdministracionTitle")
	private String nivelAdministracionTitle;

	@CsvBindByPosition(position = 13)
	@CsvBindByName(column = "tipoEntidadId")
	@Rdf(contexto = Context.ORGES, propiedad = "tipoEntidad")
	@RdfExternalURI(inicioURI = "http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/organizacion/tipo-entidad/", finURI = "tipoEntidadId")
	private String tipoEntidadId;

	@CsvBindByPosition(position = 14)
	@CsvBindByName(column = "tipoEntidadTitle")
	private String tipoEntidadTitle;

	@CsvBindByPosition(position = 15)
	@CsvBindByName(column = "streetAddress", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI + "address", nodoId = "address")
	private String streetAddress;

	@CsvBindByPosition(position = 16)
	@CsvBindByName(column = "postalCode", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI + "address", nodoId = "address")
	private String postalCode;

	@CsvBindByPosition(position = 17)
	@CsvBindByName(column = "municipioId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo = Context.ESADM_URI + "Municipio", propiedad = Context.ESADM_URI + "municipio", nodoId = "municipio")
	private String municipioId;

	@CsvBindByPosition(position = 18)
	@CsvBindByName(column = "municipioTitle", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo = Context.ESADM_URI + "Municipio", propiedad = Context.ESADM_URI + "municipio", nodoId = "municipio")
	private String municipioTitle;

	@CsvBindByPosition(position=19)
	@CsvBindByName(column="xETRS89")	
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	


	@CsvBindByPosition(position = 23)
	@CsvBindByName(column = "telephone", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;

	@CsvBindByPosition(position = 24)
	@CsvBindByName(column = "faxNumber", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "faxNumber")
	private String faxNumber;

	@CsvBindByPosition(position = 25)
	@CsvBindByName(column = "email", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	private String email;

	@CsvBindByPosition(position = 26)
	@CsvBindByName(column = "url", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@CsvBindByPosition(position=27)
	@CsvBindByName(column="image", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "image")
	@IsUri
	private String image;
	
	@CsvBindByPosition(position = 28)
	@CsvBindByName(column = "purpose", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ORG, propiedad = "purpose")
	private String purpose;
	
	private Double distance;

	public Organigrama()
	{
	}

	public Organigrama(Organigrama copia)
	{
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.unidadRaiz = copia.unidadRaiz;
		this.unitOf = copia.unitOf;
		this.nivelJerarquico = copia.nivelJerarquico;
		this.headOfName = copia.headOfName;
		this.foundingDate = copia.foundingDate;
		this.dissolutionDate = copia.dissolutionDate;
		this.estadoEntidadId = copia.estadoEntidadId;
		this.estadoEntidadTitle = copia.estadoEntidadTitle;
		this.nivelAdministracionId = copia.nivelAdministracionId;
		this.nivelAdministracionTitle = copia.nivelAdministracionTitle;
		this.tipoEntidadId = copia.tipoEntidadId;
		this.tipoEntidadTitle = copia.tipoEntidadTitle;
		this.streetAddress = copia.streetAddress;
		this.postalCode = copia.postalCode;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.latitud = copia.latitud;
		this.longitud = copia.longitud;		
		this.telephone = copia.telephone;
		this.faxNumber = copia.faxNumber;
		this.email = copia.email;
		this.purpose = copia.purpose;
		this.x=copia.x;
		this.y=copia.y;
		this.url=copia.url;
		this.image=copia.image;
	}

	public Organigrama(Organigrama copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY))
		{
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR))
		{
			this.id = copia.id;
		}
		if (attributesToSet.contains("title"))
		{
			this.title = copia.title;
		}
		if (attributesToSet.contains("unidadRaiz"))
		{
			this.unidadRaiz = copia.unidadRaiz;
		}

		if (attributesToSet.contains("unitOf"))
		{
			this.unitOf = copia.unitOf;
		}

		if (attributesToSet.contains("headOfName"))
		{
			this.headOfName = copia.headOfName;
		}

		if (attributesToSet.contains("foundingDate"))
		{
			this.foundingDate = copia.foundingDate;
		}

		if (attributesToSet.contains("dissolutionDate"))
		{
			this.dissolutionDate = copia.dissolutionDate;
		}

		if (attributesToSet.contains("estadoEntidadId"))
		{
			this.estadoEntidadId = copia.estadoEntidadId;
		}

		if (attributesToSet.contains("estadoEntidadTitle"))
		{
			this.estadoEntidadTitle = copia.estadoEntidadTitle;
		}

		if (attributesToSet.contains("nivelAdministracionId"))
		{
			this.nivelAdministracionId = copia.nivelAdministracionId;
		}

		if (attributesToSet.contains("nivelAdministracionTitle"))
		{
			this.nivelAdministracionTitle = copia.nivelAdministracionTitle;
		}

		if (attributesToSet.contains("tipoEntidadId"))
		{
			this.tipoEntidadId = copia.tipoEntidadId;
		}

		if (attributesToSet.contains("tipoEntidadTitle"))
		{
			this.tipoEntidadTitle = copia.tipoEntidadTitle;
		}

		if (attributesToSet.contains("streetAddress"))
		{
			this.streetAddress = copia.streetAddress;
		}

		if (attributesToSet.contains("postalCode"))
		{
			this.postalCode = copia.postalCode;
		}

		if (attributesToSet.contains("municipioId"))
		{
			this.municipioId = copia.municipioId;
		}

		if (attributesToSet.contains("municipioTitle"))
		{
			this.municipioTitle = copia.municipioTitle;
		}

		if (attributesToSet.contains("latitud"))
		{
			this.latitud = copia.latitud;
		}

		if (attributesToSet.contains("longitud"))
		{
			this.longitud = copia.longitud;
		}

		if (attributesToSet.contains("telephone"))
		{
			this.telephone = copia.telephone;
		}

		if (attributesToSet.contains("faxNumber"))
		{
			this.faxNumber = copia.faxNumber;
		}

		if (attributesToSet.contains("email"))
		{
			this.email = copia.email;
		}

		if (attributesToSet.contains("purpose"))
		{
			this.purpose = copia.purpose;
		}
		
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
		}
		
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
		}
		
		if (attributesToSet.contains("url"))
		{
			this.url = copia.url;
		}
		
		if (attributesToSet.contains("image"))
		{
			this.image = copia.image;
		}
		
		if (attributesToSet.contains("nivelJerarquico"))
		{
			this.nivelJerarquico = copia.nivelJerarquico;
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

	@Column(name = "unidad_raiz", unique = false, nullable = false, length = 50)
	public String getUnidadRaiz()
	{
		return this.unidadRaiz;
	}

	public void setUnidadRaiz(String unidadRaiz)
	{
		this.unidadRaiz = unidadRaiz;
	}

	@Column(name = "unit_of", unique = false, nullable = false, length = 50)
	public String getUnitOf()
	{
		return this.unitOf;
	}

	public void setUnitOf(String unitOf)
	{
		this.unitOf = unitOf;
	}

	@Column(name = "id", unique = true, nullable = false, length = 50)
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

	@Column(name = "nivel_jerarquico")
	public Integer getNivelJerarquico()
	{
		return this.nivelJerarquico;
	}

	public void setNivelJerarquico(Integer nivelJerarquico)
	{
		this.nivelJerarquico = nivelJerarquico;
	}

	@Column(name = "head_of_name", length = 200)
	public String getHeadOfName()
	{
		return this.headOfName;
	}

	public void setHeadOfName(String headOfName)
	{
		this.headOfName = headOfName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "founding_date", length = 19)
	public Date getFoundingDate()
	{
		return this.foundingDate;
	}

	public void setFoundingDate(Date foundingDate)
	{
		this.foundingDate = foundingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dissolution_date", length = 19)
	public Date getDissolutionDate()
	{
		return this.dissolutionDate;
	}

	public void setDissolutionDate(Date dissolutionDate)
	{
		this.dissolutionDate = dissolutionDate;
	}

	@Column(name = "estado_entidad_id", nullable = false, length = 50)
	public String getEstadoEntidadId()
	{
		return this.estadoEntidadId;
	}

	public void setEstadoEntidadId(String estadoEntidadId)
	{
		this.estadoEntidadId = estadoEntidadId;
	}

	@Column(name = "estado_entidad_title", nullable = false, length = 200)
	public String getEstadoEntidadTitle()
	{
		return this.estadoEntidadTitle;
	}

	public void setEstadoEntidadTitle(String estadoEntidadTitle)
	{
		this.estadoEntidadTitle = estadoEntidadTitle;
	}

	@Column(name = "nivel_administracion_id", nullable = false, length = 50)
	public String getNivelAdministracionId()
	{
		return this.nivelAdministracionId;
	}

	public void setNivelAdministracionId(String nivelAdministracionId)
	{
		this.nivelAdministracionId = nivelAdministracionId;
	}

	@Column(name = "nivel_administracion_title", nullable = false, length = 200)
	public String getNivelAdministracionTitle()
	{
		return this.nivelAdministracionTitle;
	}

	public void setNivelAdministracionTitle(String nivelAdministracionTitle)
	{
		this.nivelAdministracionTitle = nivelAdministracionTitle;
	}

	@Column(name = "tipo_entidad_id", nullable = false, length = 50)
	public String getTipoEntidadId()
	{
		return this.tipoEntidadId;
	}

	public void setTipoEntidadId(String tipoEntidadId)
	{
		this.tipoEntidadId = tipoEntidadId;
	}

	@Column(name = "tipo_entidad_title", nullable = false, length = 200)
	public String getTipoEntidadTitle()
	{
		return this.tipoEntidadTitle;
	}

	public void setTipoEntidadTitle(String tipoEntidadTitle)
	{
		this.tipoEntidadTitle = tipoEntidadTitle;
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


	@Column(name = "telephone", length = 200)
	public String getTelephone()
	{
		return this.telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	@Column(name = "fax_number", length = 200)
	public String getFaxNumber()
	{
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;
	}

	@Column(name = "street_address", length = 200)
	public String getStreetAddress()
	{
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode()
	{
		return this.postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	@Column(name = "municipio_id", nullable = false, length = 10)
	public String getMunicipioId()
	{
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId)
	{
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", nullable = false, length = 200)
	public String getMunicipioTitle()
	{
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle)
	{
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "purpose", length = 4000)
	public String getPurpose()
	{
		return this.purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}

	@Column(name = "email", length = 200)
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	@Column(name = "url", length = 400)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	@Column(name = "image", length = 200)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}

	public Map<String, String> prefixes()
	{
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.GEO, Context.GEO_URI);
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.ORG, Context.ORG_URI);
		prefixes.put(Context.ORGES, Context.ORGES_URI);
		prefixes.put(Context.FOAF, Context.FOAF_URI);
		return prefixes;
	}

	@Override
	public String toString() {
		return "Organigrama [ikey=" + ikey + ", id=" + id + ", title=" + title + ", unidadRaiz=" + unidadRaiz
				+ ", unitOf=" + unitOf + ", nivelJerarquico=" + nivelJerarquico + ", headOfName=" + headOfName
				+ ", foundingDate=" + foundingDate + ", dissolutionDate=" + dissolutionDate + ", estadoEntidadId="
				+ estadoEntidadId + ", estadoEntidadTitle=" + estadoEntidadTitle + ", nivelAdministracionId="
				+ nivelAdministracionId + ", nivelAdministracionTitle=" + nivelAdministracionTitle + ", tipoEntidadId="
				+ tipoEntidadId + ", tipoEntidadTitle=" + tipoEntidadTitle + ", streetAddress=" + streetAddress
				+ ", postalCode=" + postalCode + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", x=" + x + ", y=" + y + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", telephone=" + telephone + ", faxNumber=" + faxNumber + ", email=" + email + ", url=" + url
				+ ", image=" + image + ", purpose=" + purpose + "]";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Organigrama) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public Organigrama cloneClass(Organigrama copia, List<String> attributesToSet) {
		
		Organigrama obj = new Organigrama(copia,attributesToSet);		
		
		return obj;
		
	}

	//Organigrama
	public  List<String> validate() {
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		
		if (!Util.validValue(this.getNivelJerarquico())) {
			result.add("NivelJerarquico is not valid [Title:"+this.getNivelJerarquico()+"]");
		}
		
		if (!Util.validValue(this.getUnidadRaiz())) {
			result.add("UnidadRaiz is not valid [Title:"+this.getUnidadRaiz()+"]");
		}
		
		if (!Util.validValue(this.getUnitOf())) {
			result.add("UnitOf is not valid [Title:"+this.getUnitOf()+"]");
		}

		
		return result;
	}
	
}
