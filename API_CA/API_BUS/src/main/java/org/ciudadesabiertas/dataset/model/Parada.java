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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bus_parada")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESAUTOB, propiedad = "Parada")
@PathId(value = "/autobus/parada")
public class Parada implements java.io.Serializable, RDFModel, ICallejero {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador de la parada. Ejemplo: 138")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la parada. Ejemplo: Línea 138, comienzo en Cristo Rey y final en San Ignacio de Loyola")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "Una descripción del recurso dentro de un contexto dado. Ejemplo: Línea 138, comienzo en Cristo Rey y final en San Ignacio de Loyola")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "description")
	private String description;
	
	@ApiModelProperty(value = "Dirección URL pública donde puedes obtener información de la entidad. Ejemplo: https://www.emtmadrid.es/Bloques-EMT/EMT-BUS/Mi-linea-(1).aspx?linea=138&lang=es-ES")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	private String url;
	
	@ApiModelProperty(value = "La parada dispone de conexión Wifi pública. Ejemplo: true")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="wifi", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAUTOB, propiedad = "wifi", typeURI=Context.XSD_URI+"boolean" )
	private Boolean wifi;
	
	@ApiModelProperty(value = "La parada dispone de conexión Wifi pública. Ejemplo: true")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="wifi", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAUTOB, propiedad = "panelElectronico", typeURI=Context.XSD_URI+"boolean")
	private Boolean panelElectronico;
	
	@ApiModelProperty(value = "Zona a la que pertenece la Parada. Ejemplo: A")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="zona", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESAUTOB, propiedad = "zona")
	private String zona;
	
	@ApiModelProperty(value = "Identificador de la calle de la parada. Ejemplo: PORTAL000119")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="portalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESCJR, propiedad = "portal")
	@RdfExternalURI(inicioURI="/callejero/portal/",finURI="portalId", urifyLevel = 1)
	private String portalId;
	
	@ApiModelProperty(hidden = true)	
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String portalIdIsolated;
	
	@ApiModelProperty(value = "Calle de la parada. Ejemplo: CL BLAS DE OTERO 4")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String streetAddress;
	
	@ApiModelProperty(value = "Código postal de la parada. Ejemplo: 28100")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String postalCode;
	
	
	
	@ApiModelProperty(value = "Coordenada X de la parada. Ejemplo: 440124.33000")
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	@ApiModelProperty(value = "Coordenada Y de la parada. Ejemplo: 4474637.17000")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@ApiModelProperty(value = "Identificador del municipio de la parada. Ejemplo: 28079")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "municipio")
	@RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la parada. Ejemplo: Madrid")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	private String municipioTitle;
	
	
	@ApiModelProperty(value = "Identificador del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")	
	@RdfExternalURI(inicioURI="/territorio/barrio/",finURI="barrioId", urifyLevel = 1)
	private String barrioId;
	
	@ApiModelProperty(value = "Nombre del barrio de la estación. Ejemplo: 28006011")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="barrioTitle", format=Constants.STRING_FORMAT)
	private String barrioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito de la estación. Ejemplo: 2800601")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")	
	@RdfExternalURI(inicioURI="/territorio/distrito/",finURI="distritoId", urifyLevel = 1)
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito de la estación. Ejemplo: Unico")
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	private Double distance;

	public Parada() {
	}

	public Parada(Parada copia) {
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.title = copia.title;
		this.url = copia.url;
		this.wifi = copia.wifi;
		this.panelElectronico = copia.panelElectronico;
		this.zona = copia.zona;
		this.x = copia.x;
		this.y = copia.y;
		this.portalId = copia.portalId;
		this.streetAddress = copia.streetAddress;
		this.postalCode = copia.postalCode;
		this.longitud = copia.longitud;
		this.latitud = copia.latitud;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.barrioId = copia.barrioId;
		this.barrioTitle = copia.barrioTitle;
		this.distritoId = copia.distritoId;
		this.distritoTitle = copia.distritoTitle;
	}

	public Parada(Parada copia, List<String> attributesToSet) {

		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;
		}
		if (attributesToSet.contains("title")) {
			this.title = copia.title;
		}
		if (attributesToSet.contains("url")) {
			this.url = copia.url;
		}
		if (attributesToSet.contains("wifi")) {
			this.wifi = copia.wifi;
		}
		if (attributesToSet.contains("panelElectronico")) {
			this.panelElectronico = copia.panelElectronico;
		}
		if (attributesToSet.contains("zona")) {
			this.zona = copia.zona;
		}
		if (attributesToSet.contains("XEtrs89")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("YEtrs89")) {
			this.y = copia.y;
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
		if (attributesToSet.contains("longitud")) {
			this.longitud = copia.longitud;
		}
		if (attributesToSet.contains("latitud")) {
			this.latitud = copia.latitud;
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

	@Column(name = "description", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "title", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "wifi")
	public Boolean getWifi() {
		return this.wifi;
	}

	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}

	@Column(name = "panel_electronico")
	public Boolean getPanelElectronico() {
		return this.panelElectronico;
	}

	public void setPanelElectronico(Boolean panelElectronico) {
		this.panelElectronico = panelElectronico;
	}

	@Column(name = "zona", length = 10)
	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}
	
	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89", precision = 13, scale = 5)
	public BigDecimal getX() {
		return this.x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}
	
	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89", precision = 13, scale = 5)
	public BigDecimal getY() {
		return this.y;
	}
	
	
	public void setY(BigDecimal y) {
		this.y = y;
	}
	
	@Transient
	public BigDecimal getLatitud() {
		return latitud;
	}
	
	@Transient
	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud() {
		return longitud;
	}
	
	@Transient
	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	@Column(name = "portal_id", length = 50)
	public String getPortalId() {
		return this.portalId;
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

	@Column(name = "municipio_id", length = 50)
	public String getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}
	
	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle() {
		return municipioTitle;
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
	public Double getDistance() {
		return distance;
	}
	
	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Map<String, String> prefixes() {
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.ESAUTOB, Context.ESAUTOB_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		
		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((Parada) copia, listado);
	}
	
	public Parada cloneClass(Parada copia, List<String> attributesToSet) {

		Parada obj = new Parada(copia,attributesToSet);		

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

	@Override
	public String toString() {
		return "Parada [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url + ", wifi="
				+ wifi + ", panelElectronico=" + panelElectronico + ", zona=" + zona + ", portalId=" + portalId
				+ ", streetAddress=" + streetAddress + ", postalCode=" + postalCode + ", x=" + x + ", y=" + y
				+ ", latitud=" + latitud + ", longitud=" + longitud + ", municipioId=" + municipioId
				+ ", municipioTitle=" + municipioTitle + ", barrioId=" + barrioId + ", barrioTitle=" + barrioTitle
				+ ", distritoId=" + distritoId + ", distritoTitle=" + distritoTitle + ", distance=" + distance + "]";
	}

	

}
