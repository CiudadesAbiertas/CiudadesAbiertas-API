/**
 * 
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

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
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
@Table(name = "cont_acus_observacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@RdfMultiple({@Rdf(contexto = Context.NOISE, propiedad = "Observacion"),@Rdf(contexto = Context.SOSA, propiedad = "Observacion")})
@PathId(value="/contaminacion-acustica/observacion")
public class ContAcusticaObservacion  implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la Estación de Medida. Ejemplo: CONTACUSTOBVD002")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Relación que enlaza la propiedad que se observa. Ejemplo: nivelRuido")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="observedProperty", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "observedProperty")
	@RdfExternalURI(inicioURI="/contaminacion-acustica/propiedad/",finURI="observedProperty", urifyLevel = 1)
	private String observedProperty;
	
	@ApiModelProperty(value = "Relación entre la observación y la estación. Ejemplo: CONTACUSTESTMED001")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="madeBySensor", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "madeBySensor")
	@RdfExternalURI(inicioURI="/contaminacion-acustica/estacion/",finURI="madeBySensor", urifyLevel = 1)
	private String madeBySensor; 
	
	@ApiModelProperty(value = "Esta propiedad establece la fecha/hora de la observación. Ejemplo: 2020-05-15 08:00:00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="resultTime")
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@Rdf(contexto = Context.SOSA, propiedad = "resultTime" ,typeURI=Context.XSD_URI+"dateTime")
	private Date resultTime;	
	
	@ApiModelProperty(value = "Esta propiedad muestra el resultado de la observación. Ejemplo: 105.01")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="hasSimpleResult")
	@Rdf(contexto = Context.SOSA, propiedad = "hasSimpleResult", typeURI=Context.XSD_URI+"float")
	private BigDecimal hasSimpleResult;
	
	@ApiModelProperty(value = "Esta propiedad permite conocer si se ha producido una validación de la observación o no. Ejemplo: 0")
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="validada")
	@Rdf(contexto = Context.NOISE, propiedad = "validada", typeURI=Context.XSD_URI+"boolean")
	private Boolean validada;
	
	@ApiModelProperty(value = "Tipo de medición de la observación. Ejemplo: las50")
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="tipoMedicion")
	@Rdf(contexto = Context.NOISE, propiedad = "tipoMedicion")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/medio-ambiente/contaminacion-acustica/tipo-medicion/", finURI="tipoMedicion", urifyLevel=2)
	private String tipoMedicion;
	
	@ApiModelProperty(value = "Tipo de emisor predominante de la observación. Ejemplo: coches")
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="tipoEmisorPredominante")
	@Rdf(contexto = Context.NOISE, propiedad = "tipoEmisorPredominante")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/medio-ambiente/contaminacion-acustica/tipo-emisor-predominante/", finURI="tipoEmisorPredominante", urifyLevel=2)
	private String tipoEmisorPredominante;
	
	@ApiModelProperty(value = "Tipo de intervalo de referencia de la observación. Ejemplo: d")
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="tipoIntervaloReferencia")
	@Rdf(contexto = Context.NOISE, propiedad = "tipoIntervaloReferencia")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/medio-ambiente/contaminacion-acustica/tipo-intervalo-referencia/", finURI="tipoIntervaloReferencia", urifyLevel=2)
	private String tipoIntervaloReferencia;

	public ContAcusticaObservacion() {
		
	}
	
	public ContAcusticaObservacion(ContAcusticaObservacion copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.observedProperty = copia.observedProperty;
		this.madeBySensor = copia.madeBySensor;
		this.resultTime = copia.resultTime;
		this.hasSimpleResult = copia.hasSimpleResult;
		this.validada = copia.validada;
		this.tipoMedicion = copia.tipoMedicion;
		this.tipoEmisorPredominante = copia.tipoEmisorPredominante;
		this.tipoIntervaloReferencia = copia.tipoIntervaloReferencia;
	}
	
	public ContAcusticaObservacion(ContAcusticaObservacion copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("observedProperty")) {
			this.observedProperty = copia.observedProperty;		
		}
		if (attributesToSet.contains("madeBySensor")) {
			this.madeBySensor = copia.madeBySensor;		
		}
		if (attributesToSet.contains("resultTime")) {
			this.resultTime = copia.resultTime;
		}
		if (attributesToSet.contains("hasSimpleResult")) {
			this.hasSimpleResult = copia.hasSimpleResult;
		}
		if (attributesToSet.contains("validada")) {
			this.validada = copia.validada;
		}
		if (attributesToSet.contains("tipoMedicion")) {
			this.tipoMedicion = copia.tipoMedicion;
		}
		if (attributesToSet.contains("tipoEmisorPredominante")) {
			this.tipoEmisorPredominante = copia.tipoEmisorPredominante;
		}
		if (attributesToSet.contains("tipoIntervaloReferencia")) {
			this.tipoIntervaloReferencia = copia.tipoIntervaloReferencia;
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

	@Column(name = "observed_property", nullable = false, length = 100)
	public String getObservedProperty() {
		return this.observedProperty;
	}

	public void setObservedProperty(String observedProperty) {
		this.observedProperty = observedProperty;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "result_time", nullable = false, length = 19)
	public Date getResultTime() {
		return this.resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	@Column(name = "has_simple_result", nullable = false, precision = 12)
	public BigDecimal getHasSimpleResult() {
		return this.hasSimpleResult;
	}

	public void setHasSimpleResult(BigDecimal hasSimpleResult) {
		this.hasSimpleResult = hasSimpleResult;
	}

	@Column(name = "validada")
	public Boolean getValidada() {
		return this.validada;
	}

	public void setValidada(Boolean validada) {
		this.validada = validada;
	}

	@Column(name = "tipo_medicion", length = 200)
	public String getTipoMedicion() {
		return this.tipoMedicion ;
	}

	public void setTipoMedicion(String tipoMedicion) {
		this.tipoMedicion  = tipoMedicion;
	}
	
	@Column(name = "tipo_emisor_predominante", length = 200)
	public String getTipoEmisorPredominante() {
		return this.tipoEmisorPredominante ;
	}

	public void setTipoEmisorPredominante(String tipoEmisorPredominante) {
		this.tipoEmisorPredominante  = tipoEmisorPredominante;
	}
	
	@Column(name = "tipo_intervalo_referencia", length = 200)
	public String getTipoIntervaloReferencia() {
		return this.tipoIntervaloReferencia ;
	}

	public void setTipoIntervaloReferencia(String tipoIntervaloReferencia) {
		this.tipoIntervaloReferencia  = tipoIntervaloReferencia;
	}
	
	@Column(name = "made_by_sensor", length = 50)
	public String getMadeBySensor() {
		return madeBySensor;
	}

	public void setMadeBySensor(String madeBySensor) {
		this.madeBySensor = madeBySensor;
	}

	@Override
	public String toString() {
		return "ContAcusticaObservacion [ikey=" + ikey + ", id=" + id + ", observedProperty=" + observedProperty
				+ ", madeBySensor=" + madeBySensor + ", resultTime=" + resultTime + ", hasSimpleResult="
				+ hasSimpleResult + ", validada=" + validada + ", tipoMedicion=" + tipoMedicion
				+ ", tipoEmisorPredominante=" + tipoEmisorPredominante + ", tipoIntervaloReferencia="
				+ tipoIntervaloReferencia + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.NOISE, Context.NOISE_URI);
		prefixes.put(Context.ESCJR, Context.ESCJR_URI);
		prefixes.put(Context.SOSA, Context.SOSA_URI);
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((ContAcusticaObservacion) copia, listado);
	}
	
	public ContAcusticaObservacion cloneClass(ContAcusticaObservacion copia, List<String> attributesToSet) {

		ContAcusticaObservacion obj = new ContAcusticaObservacion(copia,attributesToSet);		

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
		
		return result;
	}
}
