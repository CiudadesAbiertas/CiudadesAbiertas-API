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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
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
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 */
@Entity
@ApiModel
@Table(name = "bus_authority")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMORG, propiedad = "Authority")
@PathId(value = "/autobus/authority")
public class Authority implements java.io.Serializable, RDFModel, ICallejero {

	@JsonIgnore
	private static final long serialVersionUID = 6875904357069558046L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@ApiModelProperty(value = "Identificador de la autoridad. Ejemplo: crtm")
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@ApiModelProperty(value = "El nombre legal de la autoridad. Ejemplo: Empresa Municipal de Transportes")
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "legalName", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "legalName")
	private String legalName;

	@ApiModelProperty(value = "El nombre alternativo por el que se conoce a la autoridad. Ejemplo: EMT")
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "alternateName", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "alternateName")
	private String alternateName;

	@ApiModelProperty(value = "Telefono de la autoridad. Ejemplo: 34916618096")
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "telephone", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;

	@ApiModelProperty(value = "Email de la autoridad. Ejemplo: crtm@comunidad.org")
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "email", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	private String email;

	@ApiModelProperty(value = "Web de la autoridad. Ejemplo: http://www.crtm.org")
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "url", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	@IsUri
	private String url;

	@ApiModelProperty(value = "Identificador de la calle de la autoridad. Ejemplo: PORTAL000098")
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "portalId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI = "/callejero/portal/", finURI = "portalId", urifyLevel = 1)
	private String portalId;

	@ApiModelProperty(value = "Calle de la autoridad. Ejemplo: CL ORENSE 7")
	@CsvBindByPosition(position = 8)
	@CsvBindByName(column = "streetAddress", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI
			+ "address", nodoId = "address")
	private String streetAddress;

	@ApiModelProperty(value = "Código postal de la autoridad. Ejemplo: 28100")
	@CsvBindByPosition(position = 9)
	@CsvBindByName(column = "postalCode", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")
	@RdfBlankNode(tipo = Context.SCHEMA_URI + "PostalAddress", propiedad = Context.SCHEMA_URI
			+ "address", nodoId = "address")
	private String postalCode;

	@ApiModelProperty(value = "Identificador del municipio de la estación. Ejemplo: 28006")
	@CsvBindByPosition(position = 10)
	@CsvBindByName(column = "municipioId", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI = "/territorio/municipio/", finURI = "municipioId")
	private String municipioId;

	@ApiModelProperty(value = "Nombre del municipio de la estación. Ejemplo: Alcobendas")
	@CsvBindByPosition(position = 11)
	@CsvBindByName(column = "municipioTitle", format = Constants.STRING_FORMAT)
	private String municipioTitle;

	@ApiModelProperty(value = "Identificador del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position = 12)
	@CsvBindByName(column = "barrio", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")
	@RdfExternalURI(inicioURI = "/territorio/barrio/", finURI = "barrioId",  urifyLevel = 1)
	private String barrioId;

	@ApiModelProperty(value = "Nombre del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position = 13)
	@CsvBindByName(column = "barrioTitle", format = Constants.STRING_FORMAT)
	private String barrioTitle;

	@ApiModelProperty(value = "Identificador del distrito de la estación. Ejemplo: 2800601")
	@CsvBindByPosition(position = 14)
	@CsvBindByName(column = "distrito", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	@RdfExternalURI(inicioURI = "/territorio/distrito/", finURI = "distritoId",  urifyLevel = 1)
	private String distritoId;

	@ApiModelProperty(value = "Nombre del distrito de la estación. Ejemplo: Unico")
	@CsvBindByPosition(position = 15)
	@CsvBindByName(column = "distritoTitle", format = Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;

	public Authority() {
	}

	public Authority(Authority copia) {
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.telephone = copia.telephone;
		this.email = copia.email;
		this.url = copia.url;
		this.portalId = copia.portalId;
		this.streetAddress = copia.streetAddress;
		this.postalCode = copia.postalCode;
		this.legalName = copia.legalName;
		this.alternateName = copia.alternateName;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.barrioId = copia.barrioId;
		this.barrioTitle = copia.barrioTitle;
		this.distritoId = copia.distritoId;
		this.distritoTitle = copia.distritoTitle;
	}

//Constructor copia con lista de attributos a settear
	public Authority(Authority copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("telephone")) {
			this.telephone = copia.telephone;
		}

		if (attributesToSet.contains("email")) {
			this.email = copia.email;
		}
		if (attributesToSet.contains("url")) {
			this.url = copia.url;
		}

		if (attributesToSet.contains("portalId")) {
			this.portalId = copia.portalId;
		}

		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;
		}

		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;
		}

		if (attributesToSet.contains("legalName")) {
			this.legalName = copia.legalName;
		}

		if (attributesToSet.contains("alternateName")) {
			this.alternateName = copia.alternateName;
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

	}

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

	@Column(name = "telephone", length = 200)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Column(name = "postal_code", length = 10)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "legal_name", length = 200)
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	@Column(name = "alternate_name", length = 200)
	public String getAlternateName() {
		return this.alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
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
	
	@Transient
	public String getPortalIdIsolated() {
		return portalIdIsolated;
	}


	public void setPortalIdIsolated(String portalIdIsolated) {
		this.portalIdIsolated = portalIdIsolated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {

		return (T) cloneClass((Authority) copia, listado);
	}

// Constructor copia con lista de attributos a settear
	public Authority cloneClass(Authority copia, List<String> attributesToSet) {

		Authority obj = new Authority(copia, attributesToSet);

		return obj;

	}

	@Override
	public List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getLegalName())) {
			result.add("LegalName is not valid [LegalName:" + this.getLegalName() + "]");
		}

		return result;
	}

	@Override
	public Map<String, String> prefixes() {
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.TMORG, Context.TMORG_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);

		return prefixes;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", legalName=" + legalName + ", alternateName=" + alternateName + ", telephone="
				+ telephone + ", email=" + email + ", url=" + url + ", portalId=" + portalId + ", streetAddress="
				+ streetAddress + ", postalCode=" + postalCode + ", municipioId=" + municipioId + ", municipioTitle="
				+ municipioTitle + ", barrioId=" + barrioId + ", barrioTitle=" + barrioTitle + ", distritoId="
				+ distritoId + ", distritoTitle=" + distritoTitle + "]";
	}


}
