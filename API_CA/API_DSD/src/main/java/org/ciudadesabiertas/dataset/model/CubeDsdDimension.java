package org.ciudadesabiertas.dataset.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RangeSKOS;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfList;
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
@Table(name = "cube_dsd_dimension")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.QB, propiedad = "DimensionProperty")
@PathId(value="/data-cube/data-structure-definition/dimension")
public class CubeDsdDimension implements java.io.Serializable, RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 7494740093503532371L;
	
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
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="notation", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SKOS, propiedad = "notation")
	private String notation;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="conceptScheme", format=Constants.STRING_FORMAT)		
	@RangeSKOS()
	private String conceptScheme;	
	
	
	@Rdf(contexto = Context.OWL, propiedad = "sameAs")
	@IsUri
	private String sameAs;	
	
	@JsonIgnore
	private String multipleField;	
	
	
	@Transient
	@RdfList(propiedad=Context.SKOS_URI+"hasTopConcept")
	private List<String> hasTopConcept;
	
	public CubeDsdDimension() {
	}

	public CubeDsdDimension(String ikey, String id, String title, String notation, String sameAs, String conceptScheme, List<String> hasTopConcept, String multipleField) {
		this.ikey = ikey;
		this.id = id;
		this.title = title;
		this.notation=notation;
		this.conceptScheme=conceptScheme;
		this.hasTopConcept=new ArrayList<String>();
		this.hasTopConcept.addAll(hasTopConcept);
		this.multipleField=multipleField;
	}
	
	public CubeDsdDimension(CubeDsdDimension copia) {
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.notation=copia.notation;
		this.conceptScheme=copia.conceptScheme;
		this.hasTopConcept=new ArrayList<String>();
		this.hasTopConcept.addAll(copia.hasTopConcept);
		this.sameAs=copia.sameAs;
		this.multipleField=copia.multipleField;
	}
	
	public CubeDsdDimension(CubeDsdDimension copia, List<String> attributesToSet)
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
		
		if (attributesToSet.contains("notation")) {
			this.notation = copia.notation;		
		}
		
		if (attributesToSet.contains("conceptScheme")) {
			this.conceptScheme = copia.conceptScheme;		
		}
		
		if (attributesToSet.contains("hasTopConcept")) {
			this.hasTopConcept=new ArrayList<String>();
			this.hasTopConcept.addAll(copia.hasTopConcept);
		}
		
		if (attributesToSet.contains("sameAs")) {
			this.sameAs=copia.sameAs;		
		}
		
		if (attributesToSet.contains("multipleField")) {
			this.multipleField=copia.multipleField;		
		}
		
		
		
		
	}

	public CubeDsdDimension(String ikey, String id, String title, Set<CubeDsdRelDimension> cubeDsdRelDimensions,
			Set<CubeDsdDimensionValue> cubeDsdDimensionValues,  String conceptScheme, List<String> hasTopConcept, String multipleField) {
		this.ikey = ikey;
		this.id = id;
		this.title = title;
		this.conceptScheme=conceptScheme;
		this.hasTopConcept=new ArrayList<String>();		
		this.hasTopConcept.addAll(hasTopConcept);
		this.multipleField=multipleField;
		
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
	
	@Column(name = "multiple_field", nullable = true, length = 400)
	public String getMutipleField() {
		return this.multipleField;
	}

	public void setMutipleField(String multipleField) {
		this.multipleField = multipleField;
	}

	@Column(name = "notation", nullable = false, length = 50)
	public String getNotation()
	{
		return this.notation;
	}

	public void setNotation(String notation)
	{
		this.notation = notation;
	}

	
	@Column(name = "concept_scheme", nullable = false, length = 400)
	public String getConceptScheme() {
		return this.conceptScheme;
	}

	public void setConceptScheme(String conceptScheme) {
		this.conceptScheme = conceptScheme;
	}
	
	@Column(name = "same_as", length = 400)
	public String getSameAs() {
		return this.sameAs;
	}

	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}
	
	@Transient
	public List<String> getHasTopConcept() {
		return hasTopConcept;
	}
	
	@Transient
	public void setHasTopConcept(List<String> hasTopConcept) {
		 this.hasTopConcept=new ArrayList<String>();
		 this.hasTopConcept.addAll(hasTopConcept);
	}


	@Override
	public String toString() {
		return "CubeDsdDimension [ikey=" + ikey + ", id=" + id + ", title=" + title + ", notation=" + notation
				+ ", conceptScheme=" + conceptScheme + ", sameAs=" + sameAs + ", hasTopConcept=" + hasTopConcept + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.QB, Context.QB_URI);
		prefixes.put(Context.SKOS, Context.SKOS_URI);		
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.RDFS, Context.RDFS_URI);
		prefixes.put(Context.OWL, Context.OWL_URI);
		prefixes.put(Context.IAESTDIMENSION, Context.IAESTDIMENSION_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((CubeDsdDimension) copia, listado);
	}
	
	public CubeDsdDimension cloneClass(CubeDsdDimension copia, List<String> attributesToSet) {
		CubeDsdDimension obj = new CubeDsdDimension(copia,attributesToSet);		
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
		
		if (!Util.validValue(this.getNotation())) {
			result.add("Notation is not valid [Notation:"+this.getNotation()+"]");
		}
		
		return result;
	}
	
}
