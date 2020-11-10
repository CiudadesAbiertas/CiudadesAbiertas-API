package org.ciudadesabiertas.dataset.model;

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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "presupuesto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "Presupuesto")
@PathId(value="/presupuesto/presupuesto")
public class Presupuesto  implements java.io.Serializable, RDFModel  {

	@JsonIgnore	
	private static final long serialVersionUID = 295428473255697784L;
	

	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador del Presupuesto. Ejemplo: PRESUP0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Fecha en la que se ha aprobado el pleno. Ejemplo: 2016-04-25")
	@CsvBindByPosition(position=2)
	@CsvBindByName (column = "fechaAprobacionPleno")	
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "fechaAprobacionPleno", typeURI=Context.XSD_URI+"date")
	private Date fechaAprobacionPleno;	
	
	@ApiModelProperty(value = "Si el presupuesto de la entidad local a 31 de diciembre no está aprobado, se considerarán automáticamente prorrogados los presupuestos iniciales del ejercicio anterior hasta la aprobación y publicación del nuevo")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="estadoProrroga", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "estadoProrroga", typeURI=Context.XSD_URI+"boolean")
	private Boolean estadoProrroga;
	
	
	@ApiModelProperty(value = "Ejercicio fiscal. Período de 12 meses entre el 1 de enero y 31 de diciembre.")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="anioFiscal", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "anioFiscal", typeURI=Context.XSD_URI+"gYear")
	private String anioFiscal;
	
	
	@ApiModelProperty(value = "Identificador de la liquidación con la que se corresponde este presupuesto.")
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="liquidacion", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "liquidacion")
	@RdfExternalURI(inicioURI="/presupuesto/liquidacion/",finURI="liquidacion", urifyLevel = 1)
	private String liquidacion;
	
	public Presupuesto() {
	}
	
	
	public Presupuesto(Presupuesto copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		
		this.fechaAprobacionPleno = copia.fechaAprobacionPleno;
		this.estadoProrroga = copia.estadoProrroga;
		this.anioFiscal = copia.anioFiscal;
		this.liquidacion=copia.liquidacion;
	}

	
	public Presupuesto(Presupuesto copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("fechaAprobacionPleno")) {
			this.fechaAprobacionPleno = copia.fechaAprobacionPleno;
		}
		if (attributesToSet.contains("estadoProrroga")) {
			this.estadoProrroga = copia.estadoProrroga;
		}
		if (attributesToSet.contains("anioFiscal")) {
			this.anioFiscal = copia.anioFiscal;
		}
		if (attributesToSet.contains("liquidacion")) {
			this.liquidacion = copia.liquidacion;
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
	@Column(name = "fecha_aprobacion_pleno", length = 19)
	public Date getFechaAprobacionPleno() {
		return this.fechaAprobacionPleno;
	}

	public void setFechaAprobacionPleno(Date fechaAprobacionPleno) {
		this.fechaAprobacionPleno = fechaAprobacionPleno;
	}

	@Column(name = "estado_prorroga")
	public Boolean getEstadoProrroga() {
		return this.estadoProrroga;
	}

	public void setEstadoProrroga(Boolean estadoProrroga) {
		this.estadoProrroga = estadoProrroga;
	}

	@Column(name = "anio_fiscal", nullable = false, length = 4)
	public String getAnioFiscal() {
		return this.anioFiscal;
	}

	public void setAnioFiscal(String anioFiscal) {
		this.anioFiscal = anioFiscal;
	}
	
	@Column(name = "liquidacion", nullable = true)
	public String getLiquidacion() {
		return liquidacion;
	}


	public void setLiquidacion(String liquidacion) {
		this.liquidacion = liquidacion;
	}


	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.RDF, Context.RDF_URI);		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.OWL, Context.OWL_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		
	
		prefixes.put(Context.ESPRESUP, Context.ESPRESUP_URI);		
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		
		return prefixes;
	}

	
	


	@Override
	public String toString() {
		return "Presupuesto [ikey=" + ikey + ", id=" + id + ", fechaAprobacionPleno=" + fechaAprobacionPleno
				+ ", estadoProrroga=" + estadoProrroga + ", anioFiscal=" + anioFiscal + ", liquidacion=" + liquidacion
				+ "]";
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Presupuesto) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public Presupuesto cloneClass(Presupuesto copia, List<String> attributesToSet) {
		
		Presupuesto obj = new Presupuesto(copia,attributesToSet);		
		
		return obj;
		
	}
	
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getAnioFiscal())) {
			result.add("Año fiscal is not valid [Id:" + this.getAnioFiscal() + "]");
		}

		return result;
	}

}
