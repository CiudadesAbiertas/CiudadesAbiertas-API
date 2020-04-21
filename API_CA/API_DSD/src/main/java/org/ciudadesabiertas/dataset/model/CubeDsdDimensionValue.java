package org.ciudadesabiertas.dataset.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ciudadesAbiertas.rdfGeneratorZ.PathIdComplex;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.dataset.controller.CubeDsdDimensionValueController;
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
@Table(name = "cube_dsd_dimension_value")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})	
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value=CubeDsdDimensionValueController.RECORD)
public class CubeDsdDimensionValue implements java.io.Serializable, RDFModel, PathIdComplex {

	@JsonIgnore
	private static final long serialVersionUID = 8857877996980515206L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del valor de la dimensión. Ejemplo: 05-a-09")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre del valor de la dimensión. Ejemplo: De 5 a 9 años")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	@ApiModelProperty(value = "SKOS al que pertenece el valor de la dimensión. Ejemplo: https://opendata.aragon.es/kos/iaest/edad-grupos-quinquenales")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="topConceptOf", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SKOS, propiedad = "topConceptOf")
	@IsUri
	private String topConceptOf;
	
	@JsonIgnore
	private String externalPathId;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private CubeDsdDimension cubeDsdDimension;
	

	public CubeDsdDimensionValue() {
	}

	public CubeDsdDimensionValue(String ikey, CubeDsdDimension cubeDsdDimension, String id, String title, String topConceptOf, String externalPathId) {
		this.ikey = ikey;
		this.cubeDsdDimension = cubeDsdDimension;
		this.id = id;
		this.title = title;
		this.topConceptOf= topConceptOf;
		this.externalPathId = externalPathId;
	}
	
	public CubeDsdDimensionValue(CubeDsdDimensionValue copia) {
		this.ikey = copia.ikey;
		this.cubeDsdDimension = copia.cubeDsdDimension;
		this.id = copia.id;
		this.title = copia.title;
		this.topConceptOf= copia.topConceptOf;
		this.externalPathId = copia.externalPathId;
	}
	
	public CubeDsdDimensionValue(CubeDsdDimensionValue copia, List<String> attributesToSet)
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
		
		if (attributesToSet.contains("topConceptOf")) {
			this.topConceptOf = copia.topConceptOf;		
		}
		
		if (attributesToSet.contains("externalPathId")) {
			this.externalPathId = copia.externalPathId;		
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dimension_key", nullable = false)
	public CubeDsdDimension getCubeDsdDimension() {
		return this.cubeDsdDimension;
	}

	public void setCubeDsdDimension(CubeDsdDimension cubeDsdDimension) {
		this.cubeDsdDimension = cubeDsdDimension;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "top_concept_of", nullable = false, length = 400)
	public String getTopConceptOf() {
		return this.topConceptOf;
	}

	public void setTopConceptOf(String topConceptOf) {
		this.topConceptOf = topConceptOf;
	}
	
	@Column(name = "external_path_id", nullable = true, length = 400)
	public String getExternalPathId() {
		return this.externalPathId;
	}

	public void setExternalPathId(String externalPathId) {
		this.externalPathId = externalPathId;
	}
	
	@Column(name = "title", nullable = false, length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	

	@Override
	public String toString() {
		return "CubeDsdDimensionValue [ikey=" + ikey + ", id=" + id + ", title=" + title + ", topConceptOf="
				+ topConceptOf + ", externalPathId=" + externalPathId + ", cubeDsdDimension=" + cubeDsdDimension + "]";
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
		prefixes.put(Context.OWL, Context.OWL_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((CubeDsdDimensionValue) copia, listado);
	}
	
	public CubeDsdDimensionValue cloneClass(CubeDsdDimensionValue copia, List<String> attributesToSet) {
		CubeDsdDimensionValue obj = new CubeDsdDimensionValue(copia,attributesToSet);		
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
	
	
	public String obtainURLPath()
	{
		String path=CubeDsdDimensionValueController.RECORD;
		if (topConceptOf==null)
		{				
			String dimensionId=this.getCubeDsdDimension().getId();		
		
			path=path.replace("{dimensionId}", dimensionId);
			path=path.replace("{id}", this.id);
		
		} else {
			path=externalPathId;
		}
		
		return path;
	}
	
}
