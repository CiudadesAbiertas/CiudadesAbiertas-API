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
@Table(name = "cube_dsd_measure")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.QB, propiedad = "MeasureProperty")
@PathId(value="/data-cube/data-structure-definition/measure")
public class CubeDsdMeasure implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -3858805416184882206L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;		
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;	
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	

	public CubeDsdMeasure() {
	}

	public CubeDsdMeasure(String ikey, String id, String title) {
		this.ikey = ikey;	
		this.id = id;
		this.title = title;
	}
	
	public CubeDsdMeasure(CubeDsdMeasure copia) {
		this.ikey = copia.ikey;		
		this.id = copia.id;
		this.title = copia.title;
	}
	
	public CubeDsdMeasure(CubeDsdMeasure copia, List<String> attributesToSet) {
		
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
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


	@Column(name = "id", nullable = false, length = 50)
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
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.QB, Context.QB_URI);
		prefixes.put(Context.SKOS, Context.SKOS_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);				
		prefixes.put(Context.DUL, Context.DUL_URI);
		
		
		return prefixes;
	}
	
	


	@Override
	public String toString() {
		return "CubeDsdMeasure [ikey=" + ikey + ", id=" + id + ", title=" + title + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((CubeDsdMeasure) copia, listado);
	}
	
	public CubeDsdMeasure cloneClass(CubeDsdMeasure copia, List<String> attributesToSet) {
		CubeDsdMeasure obj = new CubeDsdMeasure(copia,attributesToSet);		
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
