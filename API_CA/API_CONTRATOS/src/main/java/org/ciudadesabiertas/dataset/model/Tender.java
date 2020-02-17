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
@Table(name = "contratos_tender")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.OCDS, propiedad = "Tender")
@PathId(value = "/contract/tender")
public class Tender implements java.io.Serializable,  RDFModel {

	@JsonIgnore
	private static final long serialVersionUID = 1719587425857691093L;
	
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
	@CsvBindByName(column="hasSupplier", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "hasSupplier")
	@RdfExternalURI(inicioURI="/contratos/award/", finURI="hasSupplier", urifyLevel=2)
	private String hasSupplier;	
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="mainProcurementCategory", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "mainProcurementCategory")	
	private String mainProcurementCategory;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="additionalProcurementCategory", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "additionalProcurementCategory")
	private String additionalProcurementCategory;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="numberOfTenderers", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "numberOfTenderers")
	private Integer numberOfTenderers;
		
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="procurementMethod", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "procurementMethod")
	private String procurementMethod;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="procurementMethodDetails", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "procurementMethodDetails")
	private String procurementMethodDetails;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="tenderStatus", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "tenderStatus")
	private String tenderStatus;
	
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="periodDurationInDays", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "periodDurationInDays", typeURI=Context.XSD_URI+"integer")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Period", propiedad=Context.OCDS_URI+"hasTenderPeriod", nodoId="tenderPeriod")
	private Integer periodDurationInDays;
	
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="periodEndDate", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "periodEndDate",typeURI=Context.XSD_URI+"date")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Period", propiedad=Context.OCDS_URI+"hasTenderPeriod", nodoId="tenderPeriod")
	private Date periodEndDate;
	
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="periodStartDate", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "periodStartDate",typeURI=Context.XSD_URI+"date")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Period", propiedad=Context.OCDS_URI+"hasTenderPeriod", nodoId="tenderPeriod")
	private Date periodStartDate;
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="valueAmount", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "valueAmount",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Value", propiedad=Context.OCDS_URI+"hasMaxEstimatedValue", nodoId="amount")
	private BigDecimal valueAmount;
	
	

	
	public Tender() {
	}

	
	
	public Tender(Tender copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.hasSupplier = copia.hasSupplier;
		this.mainProcurementCategory = copia.mainProcurementCategory;
		this.additionalProcurementCategory = copia.additionalProcurementCategory;
		this.numberOfTenderers = copia.numberOfTenderers;
		this.procurementMethod = copia.procurementMethod;
		this.procurementMethodDetails = copia.procurementMethodDetails;
		this.tenderStatus = copia.tenderStatus;
		this.periodDurationInDays = copia.periodDurationInDays;
		this.periodEndDate = copia.periodEndDate;
		this.periodStartDate = copia.periodStartDate;
		this.valueAmount = copia.valueAmount;
	}



	public Tender(Tender copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("mainProcurementCategory")) {
			this.mainProcurementCategory = copia.mainProcurementCategory;		
		}
		if (attributesToSet.contains("additionalProcurementCategory")) {
			this.additionalProcurementCategory = copia.additionalProcurementCategory;		
		}
		if (attributesToSet.contains("numberOfTenderers")) {
			this.numberOfTenderers = copia.numberOfTenderers;		
		}
		if (attributesToSet.contains("procurementMethod")) {
			this.procurementMethod = copia.procurementMethod;		
		}
		if (attributesToSet.contains("procurementMethodDetails")) {
			this.procurementMethodDetails = copia.procurementMethodDetails;		
		}
		if (attributesToSet.contains("tenderStatus")) {
			this.tenderStatus = copia.tenderStatus;		
		}
		if (attributesToSet.contains("periodDurationInDays")) {
			this.periodDurationInDays = copia.periodDurationInDays;		
		}
		if (attributesToSet.contains("periodEndDate")) {
			this.periodEndDate = copia.periodEndDate;		
		}
		if (attributesToSet.contains("periodStartDate")) {
			this.periodStartDate = copia.periodStartDate;		
		}
		if (attributesToSet.contains("valueAmount")) {
			this.valueAmount = copia.valueAmount;		
		}
		if (attributesToSet.contains("hasSupplier")) {
			this.hasSupplier = copia.hasSupplier;		
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

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "main_proc_category", length = 50)
	public String getMainProcurementCategory() {
		return mainProcurementCategory;
	}

	public void setMainProcurementCategory(String mainProcurementCategory) {
		this.mainProcurementCategory = mainProcurementCategory;
	}
	

	@Column(name = "additional_proc_category", length = 50)
	public String getAdditionalProcurementCategory() {
		return additionalProcurementCategory;
	}

	public void setAdditionalProcurementCategory(String additionalProcurementCategory) {
		this.additionalProcurementCategory = additionalProcurementCategory;
	}
	
	
	@Column(name = "number_of_tenderers")
	public Integer getNumberOfTenderers() {
		return this.numberOfTenderers;
	}



	public void setNumberOfTenderers(Integer numberOfTenderers) {
		this.numberOfTenderers = numberOfTenderers;
	}

	@Column(name = "proc_method", length = 200)
	public String getProcurementMethod() {
		return procurementMethod;
	}



	public void setProcurementMethod(String procurementMethod) {
		this.procurementMethod = procurementMethod;
	}
	
	
	

	@Column(name = "proc_method_details", length = 200)
	public String getProcurementMethodDetails() {
		return procurementMethodDetails;
	}



	public void setProcurementMethodDetails(String procurementMethodDetails) {
		this.procurementMethodDetails = procurementMethodDetails;
	}
	

	@Column(name = "tender_status", length = 200)
	public String getTenderStatus() {
		return this.tenderStatus;
	}

	



	public void setTenderStatus(String tenderStatus) {
		this.tenderStatus = tenderStatus;
	}

	@Column(name = "period_duration_in_days")
	public Integer getPeriodDurationInDays() {
		return this.periodDurationInDays;
	}

	public void setPeriodDurationInDays(Integer periodDurationInDays) {
		this.periodDurationInDays = periodDurationInDays;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "period_end_date", length = 19)
	public Date getPeriodEndDate() {
		return this.periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "period_start_date", length = 19)
	public Date getPeriodStartDate() {
		return this.periodStartDate;
	}

	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	@Column(name = "value_amount", precision = 12)
	public BigDecimal getValueAmount() {
		return this.valueAmount;
	}

	public void setValueAmount(BigDecimal valueAmount) {
		this.valueAmount = valueAmount;
	}

	@Column(name = "has_supplier", length = 50)
	public String getHasSupplier() {
		return hasSupplier;
	}

	public void setHasSupplier(String hasSupplier) {
		this.hasSupplier = hasSupplier;
	}

	@Override
	public String toString() {
		return "Tender [ikey=" + ikey + ", id=" + id + ", title=" + title + ", hasSupplier=" + hasSupplier
				+ ", mainProcurementCategory=" + mainProcurementCategory + ", additionalProcurementCategory="
				+ additionalProcurementCategory + ", numberOfTenderers=" + numberOfTenderers + ", procurementMethod="
				+ procurementMethod + ", procurementMethodDetails=" + procurementMethodDetails + ", tenderStatus="
				+ tenderStatus + ", periodDurationInDays=" + periodDurationInDays + ", periodEndDate=" + periodEndDate
				+ ", periodStartDate=" + periodStartDate + ", valueAmount=" + valueAmount + "]";
	}



	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.OCDS, Context.OCDS_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);
		
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
				
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		
		
		
		return result;
	}

}
