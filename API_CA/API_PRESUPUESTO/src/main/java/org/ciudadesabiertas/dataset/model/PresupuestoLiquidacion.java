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
@Table(name = "presupuesto_liquidacion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "Liquidacion")
@PathId(value="/presupuesto/liquidacion")
public class PresupuestoLiquidacion implements java.io.Serializable, RDFModel {

	@JsonIgnore	
	private static final long serialVersionUID = 3627620500424591559L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador de la Liquidación. Ejemplo: LIQUID0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "El resultado presupuestario ajustado forma parte del estado de liquidación del presupuesto y es la diferencia entre los derechos de cobro que se han liquidado en el ejercicio y las obligaciones de pago reconocidas en el mismo periodo. Por tanto, es una magnitud que refleja el superávit o déficit del ejercicio del Ayuntamiento.")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "resultadoPresupuestario", typeURI=Context.XSD_URI+"double")
	private BigDecimal resultadoPresupuestario;
	
	@ApiModelProperty(value = "Los ajustes corresponden a los créditos financiados con el remanente de tesorería y las desviaciones positivas y negativas del ejercicio.")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="ajustes")
	@Rdf(contexto = Context.ESPRESUP, propiedad = "ajustes", typeURI=Context.XSD_URI+"double")
	private BigDecimal ajustes;
	
	@ApiModelProperty(value = "El resultado presupuestario deberá ajustarse, en su caso, en función de las obligaciones financiadas con el remanente de tesorería para gastos generales y las desviaciones de financiación del ejercicio derivadas de los gastos con financiación afectada.")
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="resultadoAjustado")
	@Rdf(contexto = Context.ESPRESUP, propiedad = "resultadoAjustado", typeURI=Context.XSD_URI+"double")
	private BigDecimal resultadoAjustado;
	
	@ApiModelProperty(value = "Fecha de aprobación del estado de liquidación.")
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="fechaAprobacionLiquid")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "fechaAprobacionLiquid", typeURI=Context.XSD_URI+"date")
	private Date fechaAprobacionLiquid;	
	

	public PresupuestoLiquidacion() {
	}

	public PresupuestoLiquidacion(PresupuestoLiquidacion copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		
		this.resultadoPresupuestario = copia.resultadoPresupuestario;
		this.ajustes = copia.ajustes;
		this.resultadoAjustado = copia.resultadoAjustado;
		this.fechaAprobacionLiquid=copia.fechaAprobacionLiquid;
	}

	
	public PresupuestoLiquidacion(PresupuestoLiquidacion copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("resultadoPresupuestario")) {
			this.resultadoPresupuestario = copia.resultadoPresupuestario;
		}
		if (attributesToSet.contains("ajustes")) {
			this.ajustes = copia.ajustes;
		}
		if (attributesToSet.contains("resultadoAjustado")) {
			this.resultadoAjustado = copia.resultadoAjustado;
		}
		if (attributesToSet.contains("fechaAprobacionLiquid")) {
			this.fechaAprobacionLiquid = copia.fechaAprobacionLiquid;
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

	@Column(name = "resultado_presupuestario", precision = 12)
	public BigDecimal getResultadoPresupuestario() {
		return this.resultadoPresupuestario;
	}

	public void setResultadoPresupuestario(BigDecimal resultadoPresupuestario) {
		this.resultadoPresupuestario = resultadoPresupuestario;
	}

	@Column(name = "ajustes", precision = 12)
	public BigDecimal getAjustes() {
		return this.ajustes;
	}

	public void setAjustes(BigDecimal ajustes) {
		this.ajustes = ajustes;
	}

	@Column(name = "resultado_ajustado", precision = 12)
	public BigDecimal getResultadoAjustado() {
		return this.resultadoAjustado;
	}

	public void setResultadoAjustado(BigDecimal resultadoAjustado) {
		this.resultadoAjustado = resultadoAjustado;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_aprobacion_liquid", length = 19)
	public Date getFechaAprobacionLiquid() {
		return this.fechaAprobacionLiquid;
	}

	public void setFechaAprobacionLiquid(Date fechaAprobacionLiquid) {
		this.fechaAprobacionLiquid = fechaAprobacionLiquid;
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
		return "PresupuestoLiquidacion [ikey=" + ikey + ", id=" + id + ", resultadoPresupuestario="
				+ resultadoPresupuestario + ", ajustes=" + ajustes + ", resultadoAjustado=" + resultadoAjustado
				+ ", fechaAprobacionLiquid=" + fechaAprobacionLiquid + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (PresupuestoLiquidacion) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public PresupuestoLiquidacion cloneClass(PresupuestoLiquidacion copia, List<String> attributesToSet) {
		
		PresupuestoLiquidacion obj = new PresupuestoLiquidacion(copia,attributesToSet);		
		
		return obj;
		
	}
		
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getResultadoPresupuestario())) {
			result.add("Resultado presupuestario is not valid [Id:" + this.getResultadoPresupuestario() + "]");
		}

		return result;
	}


}
