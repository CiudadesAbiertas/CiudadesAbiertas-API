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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
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





@Entity
@ApiModel
@Table(name = "contratos_award")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.OCDS, propiedad = "Award")
@PathId(value = "/contract/award")
public class Award implements java.io.Serializable,  RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 1719587425857691093L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;

	@ApiModelProperty(value = "Identificador de la adjudicación. Ejemplo: AW1")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Una adjudicación es proveedora para una organización. Ejemplo: B83234799")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="isSupplierFor", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "isSupplierFor")
	@RdfExternalURI(inicioURI="/contratos/organization/", finURI="isSupplierFor", urifyLevel=2)
	private String isSupplierFor;	
	
	@ApiModelProperty(value = "Fecha de adjudicación. Ejemplo: 2018-12-26 00:00:00.0")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "awardDate")	
	@CsvDate(Constants.DATE_TIME_FORMAT)
	@RdfMultiple(@Rdf(contexto = Context.OCDS, propiedad = "awardDate",typeURI=Context.XSD_URI+"dateTime" ))
	private Date awardDate;
	
	@ApiModelProperty(value = "Valor de la adjudicación. Ejemplo: 5508.00")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="valueAmount", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "valueAmount",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Value", propiedad=Context.OCDS_URI+"Value", nodoId="amount")
	private BigDecimal valueAmount;
	
	@ApiModelProperty(value = "Descripción de la adjudicación. Ejemplo: Ser el único licitador presentado y ser el precio el único criterio de adjudicación.")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;

	
	public Award() {
	}

	
	
	public Award(Award copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.awardDate = copia.awardDate;
		this.description = copia.description;
		this.valueAmount = copia.valueAmount;
		this.isSupplierFor = copia.isSupplierFor;
	}


	public Award(Award copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		
		if (attributesToSet.contains("awardDate")) {
			this.awardDate = copia.awardDate;		
		}
		
		if (attributesToSet.contains("valueAmount")) {
			this.valueAmount = copia.valueAmount;		
		}
		
		if (attributesToSet.contains("description")) {
			this.description = copia.description;		
		}
		
		if (attributesToSet.contains("isSupplierFor")) {
			this.isSupplierFor = copia.isSupplierFor;		
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


	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "award_date", length = 19)
	public Date getAwardDate() {
		return awardDate;
	}


	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}


	@Column(name = "value_amount", precision = 12)
	public BigDecimal getValueAmount() {
		return valueAmount;
	}



	public void setValueAmount(BigDecimal valueAmount) {
		this.valueAmount = valueAmount;
	}



	@Column(name = "description", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "is_supplier_for", length = 50)
	public String getIsSupplierFor() {
		return isSupplierFor;
	}

	public void setIsSupplierFor(String isSupplierFor) {
		this.isSupplierFor = isSupplierFor;
	}



	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.OCDS, Context.OCDS_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.TIME, Context.TIME_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((Process) copia, listado);
	}
	
	public Process cloneClass(Process copia, List<String> attributesToSet) {

		Process obj = new Process(copia,attributesToSet);		

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
		
		//En Liquibase la descripción no debe ser nula 
		if (!Util.validValue(this.getDescription())) {
			result.add("Description is not valid [Description:"+this.getDescription()+"]");
		}
		
		
		
		return result;
	}



	@Override
	public String toString() {
		return "Award [ikey=" + ikey + ", id=" + id + ", isSupplierFor=" + isSupplierFor + ", awardDate=" + awardDate
				+ ", valueAmount=" + valueAmount + ", description=" + description + "]";
	}

	
}
