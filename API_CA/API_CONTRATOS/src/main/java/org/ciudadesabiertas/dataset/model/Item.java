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
@Table(name = "contratos_item")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.OCDS, propiedad = "Item")
@PathId(value = "/contract/item")
public class Item implements java.io.Serializable,  RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -1355958084137350781L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = Constants.IDENTIFICADOR, format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;

	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "description", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "hasClassification", format = Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "hasClassification")
	private String hasClassification;

	public Item() {
	}

	public Item(Item copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.description = copia.description;
		this.hasClassification = copia.hasClassification;
	}

	public Item(Item copia, List<String> attributesToSet) {
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}

		if (attributesToSet.contains("description")) {
			this.description = copia.description;
		}
		if (attributesToSet.contains("hasClassification")) {
			this.hasClassification = copia.hasClassification;
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

	@Column(name = "description", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "has_classification", length = 400)
	public String getHasClassification() {
		return hasClassification;
	}

	public void setHasClassification(String hasClassification) {
		this.hasClassification = hasClassification;
	}

	public Map<String, String> prefixes() {
		Map<String, String> prefixes = new HashMap<String, String>();
		prefixes.put(Context.DCT, Context.DCT_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.OCDS, Context.OCDS_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);

		return prefixes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		return (T) cloneClass((Process) copia, listado);
	}

	public Process cloneClass(Process copia, List<String> attributesToSet) {

		Process obj = new Process(copia, attributesToSet);

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

		if (!Util.validValue(this.getDescription())) {
			result.add("Description is not valid [Title:" + this.getDescription() + "]");
		}

		return result;
	}

	@Override
	public String toString() {
		return "Item [ikey=" + ikey + ", id=" + id + ", description=" + description + ", hasClassification="
				+ hasClassification + "]";
	}
	
	

}
