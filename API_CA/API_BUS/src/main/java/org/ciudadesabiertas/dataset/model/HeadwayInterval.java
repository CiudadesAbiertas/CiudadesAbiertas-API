/**
 * 
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

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
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

@Entity
@ApiModel
@Table(name = "bus_headwayinterval")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "HeadwayInterval")
@PathId(value="/autobus/headwayinterval")
public class HeadwayInterval implements java.io.Serializable, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269125191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador del intervalo de cabecera. Ejemplo: EMT")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Mínimo intervalo/frecuencia fijada desde cabecera. Ejemplo: P7M")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="minimumHeadwayInterval", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "minimumHeadwayInterval")
	private String minimumHeadwayInterval;
	
	@ApiModelProperty(value = "Máximo intervalo/frecuencia fijada desde cabecera. Ejemplo: P20M")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="maximumHeadwayInterval", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "maximumHeadwayInterval")
	private String maximumHeadwayInterval;
	
	@ApiModelProperty(value = "Intervalo/frecuencia programada desde cabecera. Ejemplo: Cada 7 - 20 min.")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="scheduledHeadwayInterval", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.TMJOURNEY, propiedad = "scheduledHeadwayInterval")
	private String scheduledHeadwayInterval;

	public HeadwayInterval() {
		
	}
	
	public HeadwayInterval(HeadwayInterval copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.minimumHeadwayInterval = copia.minimumHeadwayInterval;
		this.maximumHeadwayInterval = copia.maximumHeadwayInterval;
		this.scheduledHeadwayInterval = copia.scheduledHeadwayInterval;
	}

	public HeadwayInterval(HeadwayInterval copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("minimumHeadwayInterval")) {
			this.minimumHeadwayInterval = copia.minimumHeadwayInterval;
		}
		if (attributesToSet.contains("maximumHeadwayInterval")) {
			this.maximumHeadwayInterval = copia.maximumHeadwayInterval;
		}
		if (attributesToSet.contains("scheduledHeadwayInterval")) {
			this.scheduledHeadwayInterval = copia.scheduledHeadwayInterval;
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
	
	@Column(name = "minimum_headway_interval", length = 50)
	public String getMinimumHeadwayInterval() {
	  return this.minimumHeadwayInterval;
	}

	public void setMinimumHeadwayInterval(String minimumHeadwayInterval) {
	  this.minimumHeadwayInterval = minimumHeadwayInterval;
	}

	@Column(name = "maximum_headway_interval", length = 50)
	public String getMaximumHeadwayInterval() {
	  return this.maximumHeadwayInterval;
	}

	public void setMaximumHeadwayInterval(String maximumHeadwayInterval) {
	  this.maximumHeadwayInterval = maximumHeadwayInterval;
	}

	@Column(name = "scheduled_headway_interval", length = 200)
	public String getScheduledHeadwayInterval() {
	  return this.scheduledHeadwayInterval;
	}

	public void setScheduledHeadwayInterval(String scheduledHeadwayInterval) {
	  this.scheduledHeadwayInterval = scheduledHeadwayInterval;
	}

	@Override
	public String toString() {
		return "HeadwayInterval [ikey=" + ikey + ", id=" + id + ", minimumHeadwayInterval=" + minimumHeadwayInterval
				+ ", maximumHeadwayInterval=" + maximumHeadwayInterval + ", scheduledHeadwayInterval="
				+ scheduledHeadwayInterval + "]";
	}
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
		
		return prefixes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((HeadwayInterval) copia, listado);
	}
	
	public HeadwayInterval cloneClass(HeadwayInterval copia, List<String> attributesToSet) {

		HeadwayInterval obj = new HeadwayInterval(copia,attributesToSet);		

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
