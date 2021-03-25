package org.ciudadesabiertas.dataset.model;
// Generated 13 dic. 2019 13:00:26 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.CustomId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
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
@Table(name = "contratos_lot_rel_item")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@PathId(value = "/contract/item")
public class LotRelItem implements java.io.Serializable,  RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = -1797030034216023141L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la relación Clasificación - Lote. Ejemplo: 00000000000001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)	
	@CustomId(id = "item")	
	private String id;
	
	@ApiModelProperty(value = "Identificador de la Clasificación. Ejemplo: IT1")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="item", format=Constants.STRING_FORMAT)
	private String item;
	
	@ApiModelProperty(value = "Identificador del Lote. Ejemplo: LT1")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="lot", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "isRelatedToLot")	
	@RdfExternalURI(inicioURI="/contract/lot/",finURI="lot", urifyLevel = 1)
	private String lot;

	public LotRelItem() {
	}
	

	public LotRelItem(LotRelItem copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.item = copia.item;
		this.lot = copia.lot;
	}


	public LotRelItem(LotRelItem copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("item")) {
			this.item = copia.item;
		}
		if (attributesToSet.contains("lot")) {
			this.lot = copia.lot;		
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

	@Column(name = "item_id", nullable = false, length = 50)
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "lot_id", nullable = false, length = 50)
	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
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

		if (!Util.validValue(this.getItem())) {
			result.add("Item is not valid [Title:" + this.getItem() + "]");
		}
		
		if (!Util.validValue(this.getLot())) {
			result.add("Lot is not valid [Title:" + this.getLot() + "]");
		}

		return result;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "LotRelItem [ikey=" + ikey + ", id=" + id + ", item=" + item + ", lot=" + lot + "]";
	}

	
	

}
