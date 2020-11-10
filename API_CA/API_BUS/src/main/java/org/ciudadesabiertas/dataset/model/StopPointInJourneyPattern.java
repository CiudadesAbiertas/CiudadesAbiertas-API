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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;




@Entity
@ApiModel
@Table(name = "bus_stoppointinjourneypattern")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "StopPointInJourneyPattern")
@PathId(value="/autobus/stoppointinjourneypattern")
public class StopPointInJourneyPattern implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = -250448232147610407L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador del punto de parada. Ejemplo: 6a2-1918")
@CsvBindByPosition(position = 1)
@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;


@ApiModelProperty(value = "Orden del punto de parada. Ejemplo: 3")
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "order")
@Rdf(contexto = Context.TMJOURNEY, propiedad = "order")
private Integer orderStop;


@ApiModelProperty(value = "Permite la descripción del tipo de uso de una parada: acceso, intercambio, de paso, etc...")
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "stopUse")
@Rdf(contexto = Context.TMJOURNEY, propiedad = "stopUse")
@RdfExternalURI(inicioURI=Context.TMKOSJOURNEY_URI, finURI="stopUse")
private String stopUse;



@ApiModelProperty(value = "Esta propiedad conecta el punto de ruta con el patron de viaje. Ejemplo: 138a1")
@CsvBindByPosition(position=4)
@CsvBindByName(column="in", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "in")
private String in;


@ApiModelProperty(value = "Esta propiedad conecta el punto de ruta con el patron de viaje. Ejemplo: 138a1")
@CsvBindByPosition(position=5)
@CsvBindByName(column="viewedAs", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.TMJOURNEY, propiedad = "viewedAs")
@RdfExternalURI(inicioURI="/autobus/scheduledstoppoint/", finURI="viewedAs")
private String viewedAs;




public StopPointInJourneyPattern() {
}

public StopPointInJourneyPattern(StopPointInJourneyPattern copia) {

  this.ikey = copia.ikey;
  this.id = copia.id;
  this.orderStop = copia.orderStop;
  this.stopUse = copia.stopUse;
  this.in = copia.in;
  this.viewedAs = copia.viewedAs;
}

public StopPointInJourneyPattern(StopPointInJourneyPattern copia, List<String> attributesToSet) {
  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("orderStop")) {
	this.orderStop = copia.orderStop;
  }
  if (attributesToSet.contains("stopUse")) {
	this.stopUse = copia.stopUse;
  }
  if (attributesToSet.contains("in")) {
	this.in = copia.in;
  }
  if (attributesToSet.contains("viewedAs")) {
	this.viewedAs = copia.viewedAs;
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

@Column(name = "order_stop")
public Integer getOrderStop() {
  return this.orderStop;
}

public void setOrderStop(Integer orderStop) {
  this.orderStop = orderStop;
}

@Column(name = "stop_use", length = 200)
public String getStopUse() {
  return this.stopUse;
}

public void setStopUse(String stopUse) {
  this.stopUse = stopUse;
}


@Column(name = "in_id", length = 50)
public String getIn() {
  return in;
}

public void setIn(String in) {
  this.in = in;
}


@Column(name = "viewed_as", length = 50)
public String getViewedAs() {
  return viewedAs;
}

public void setViewedAs(String viewedAs) {
  this.viewedAs = viewedAs;
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((StopPointInJourneyPattern) copia, listado);
}

//Constructor copia con lista de attributos a settear
public StopPointInJourneyPattern cloneClass(StopPointInJourneyPattern copia, List<String> attributesToSet) {

  StopPointInJourneyPattern obj = new StopPointInJourneyPattern(copia, attributesToSet);

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



@Override
public Map<String, String> prefixes() {
  Map<String, String> prefixes = new HashMap<String, String>();
  prefixes.put(Context.XSD, Context.XSD_URI);
  prefixes.put(Context.DCT, Context.DCT_URI);
  prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
  prefixes.put(Context.ESCJR, Context.ESCJR_URI);
  prefixes.put(Context.TMORG, Context.TMORG_URI);
  prefixes.put(Context.TMJOURNEY, Context.TMJOURNEY_URI);
  prefixes.put(Context.TMKOSJOURNEY, Context.TMKOSJOURNEY_URI);
  prefixes.put(Context.TMCOMMONS, Context.TMCOMMONS_URI);
  prefixes.put(Context.ESAUTOB, Context.ESAUTOB_URI);
  prefixes.put(Context.ESTRAF, Context.ESTRAF_URI);
  prefixes.put(Context.GEO, Context.GEO_URI);
  prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);	

  return prefixes;
}

public StopPointInJourneyPattern(String ikey, String id, Integer orderStop, String stopUse, String in, String viewedAs) {
  super();
  this.ikey = ikey;
  this.id = id;
  this.orderStop = orderStop;
  this.stopUse = stopUse;
  this.in = in;
  this.viewedAs = viewedAs;
}







}
