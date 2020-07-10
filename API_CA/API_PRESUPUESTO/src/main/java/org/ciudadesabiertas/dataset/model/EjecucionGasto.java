/**
 * 
 */
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

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfTripleExtenal;
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
 * @author Hugo Lafuente (Localidata)
 */
@Entity
@ApiModel
@Table(name = "presupuesto_ejecucion_gasto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "EjecucionGasto")
@PathId(value="/presupuesto/ejecucion-gasto")
public class EjecucionGasto  implements java.io.Serializable, RDFModel 
{
	@JsonIgnore	
	private static final long serialVersionUID = -4625165889696822268L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador de la ejecucion Gasto")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/presupuesto/presupuestoGasto/", sujetoFinURI="presupuestoGasto", propiedadURI=Context.ESPRESUP_URI+"ejecucionGasto", objetoInicioURI="/presupuesto/ejecucionGasto/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Un crédito presupuestario inicial puede sufrir modificaciones con lo cual se tiene un crédito definitivo vigente.")
	@CsvBindByPosition(position=2)
	@CsvBindByName (column = "creditoModificaciones")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoModificaciones", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoModificaciones;
	
	@ApiModelProperty(value = "El crédito definitivo vigente en cada momento vendrá determinado por el crédito inicial aumentado o disminuido como consecuencia de modificaciones presupuestarias.")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "creditoDefinitivoVigente")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoDefinitivoVigente", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoDefinitivoVigente;
	
	@ApiModelProperty(value = "El crédito autorizado recoge los importes autorizados por el órgano competente para la realización de un gasto determinado por una cuantía cierta o aproximada reservando a tal fi")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "creditoAutorizado")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoAutorizado", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoAutorizado;
	
	@ApiModelProperty(value = "El compromiso es el acto mediante el cual se acuerda, tras el cumplimiento de los trámites legalmente establecidos, la realización de gastos previamente aprobados, por un importe determinado o determinable.")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "totalGastoComprometido")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "totalGastoComprometido", typeURI=Context.XSD_URI+"double")
	private BigDecimal totalGastoComprometido;
	
	@ApiModelProperty(value = "El crédito disponible es el crédito no gastado.")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "creditoDisponible")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoDisponible", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoDisponible;
	
	@ApiModelProperty(value = "El crédito retenido es el importe reservado para realizar una actuación administrativa posterior (administración, adjudicación).")
	@CsvBindByPosition(position=7)
	@CsvBindByName (column = "creditoRetenido")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoRetenido", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoRetenido;
	
	@ApiModelProperty(value = "Importe de las obligaciones de pago que han tenido su origen en el ejercicio.")
	@CsvBindByPosition(position=8)
	@CsvBindByName (column = "obligacionesReconocidasNetas")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "obligacionesReconocidasNetas", typeURI=Context.XSD_URI+"double")
	private BigDecimal obligacionesReconocidasNetas;
	
	@ApiModelProperty(value = "Importe de las obligaciones reconocidas netas que se han pagado.")
	@CsvBindByPosition(position=9)
	@CsvBindByName (column = "pagos")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "pagos", typeURI=Context.XSD_URI+"double")
	private BigDecimal pagos;
	
	@ApiModelProperty(value = "Importe de las obligaciones reconocidas en el ejercicio que se encuentran pendientes de pagar a 31 de diciembre de ese año. Su importe será el resultado de deducir de las obligaciones reconocidas netas los pagos realizados.")
	@CsvBindByPosition(position=10)
	@CsvBindByName (column = "obligacionesPendientes31Dic")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "obligacionesPendientes31Dic", typeURI=Context.XSD_URI+"double")
	private BigDecimal obligacionesPendientes31Dic;
	
	//TODO Hay que repasar el periodo ya que deberia de tener 2 nodos en blanco pero demomento no lo hemos hecho.
	@ApiModelProperty(value = "Corrresponde al período de actividad reportada en la ejecución, en general a partir del 1 de enero del año fiscal hasta la fecha de la ejecución. Comienzo del valor de la entidad temporal.")
	@CsvBindByPosition(position=11)
	@CsvBindByName (column = "periodoEjecucionBeginning")
	@Rdf(contexto = Context.TIME, propiedad = "hasBeginning")
	@RdfBlankNode(tipo=Context.TIME_URI+"properInterval", propiedad=Context.ESPRESUP_URI+"periodoEjecucion", nodoId="periodoEjecucionBeginning")
	private String periodoEjecucionBeginning;
	
	//TODO Hay que repasar el periodo ya que deberia de tener 2 nodos en blanco pero demomento no lo hemos hecho. 
	@ApiModelProperty(value = "Corrresponde al período de actividad reportada en la ejecución, en general a partir del 1 de enero del año fiscal hasta la fecha de la ejecución. Final del valor de la entidad temporal.")
	@CsvBindByPosition(position=12)
	@CsvBindByName (column = "periodoEjecucionEnd")
	@Rdf(contexto = Context.TIME, propiedad = "hasEnd")
	@RdfBlankNode(tipo=Context.TIME_URI+"properInterval", propiedad=Context.ESPRESUP_URI+"periodoEjecucion", nodoId="periodoEjecucionEnd" )
	private String periodoEjecucionEnd;
	
	@ApiModelProperty(value = "Presupuesto Gasto con el que se relaciona esta Ejecución Gasto")
	@CsvBindByPosition(position=13)
	@CsvBindByName (column = "presupuestoGasto")	
	private String presupuestoGasto;
	
	@ApiModelProperty(value = "Importe de los créditos definitivos que no se han consumido en el año fiscal. Su importe será el que resulte de deducir de los créditos definitivos las obligaciones reconocidas netas.")
	@CsvBindByPosition(position=14)
	@CsvBindByName (column = "remanenteCredito")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "remanenteCredito", typeURI=Context.XSD_URI+"double")
	private BigDecimal remanenteCredito;
	
	public EjecucionGasto() 
	{
	}


	/**
	 * @param ikey
	 * @param id
	 * @param remanenteCredito
	 * @param creditoModificaciones
	 * @param creditoDefinitivoVigente
	 * @param creditoAutorizado
	 * @param totalGastoComprometido
	 * @param creditoDisponible
	 * @param creditoRetenido
	 * @param obligacionesReconocidasNetas
	 * @param pagos
	 * @param obligacionesPendientes31Dic
	 * @param periodoEjecucionBeginning
	 * @param periodoEjecucionEnd
	 */
	public EjecucionGasto(EjecucionGasto copia) 
	{
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.remanenteCredito = copia.remanenteCredito;
		this.creditoModificaciones = copia.creditoModificaciones;
		this.creditoDefinitivoVigente = copia.creditoDefinitivoVigente;
		this.creditoAutorizado = copia.creditoAutorizado;
		this.totalGastoComprometido = copia.totalGastoComprometido;
		this.creditoDisponible = copia.creditoDisponible;
		this.creditoRetenido = copia.creditoRetenido;
		this.obligacionesReconocidasNetas = copia.obligacionesReconocidasNetas;
		this.pagos = copia.pagos;
		this.obligacionesPendientes31Dic = copia.obligacionesPendientes31Dic;
		this.periodoEjecucionBeginning = copia.periodoEjecucionBeginning;
		this.periodoEjecucionEnd = copia.periodoEjecucionEnd;
		this.presupuestoGasto = copia.presupuestoGasto;
	}
	
	public EjecucionGasto(EjecucionGasto copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("remanenteCredito")) {
			this.remanenteCredito = copia.remanenteCredito;
		}
		if (attributesToSet.contains("creditoModificaciones")) {
			this.creditoModificaciones = copia.creditoModificaciones;
		}
		if (attributesToSet.contains("creditoDefinitivoVigente")) {
			this.creditoDefinitivoVigente = copia.creditoDefinitivoVigente;
		}
		if (attributesToSet.contains("creditoAutorizado")) {
			this.creditoAutorizado = copia.creditoAutorizado;
		}
		if (attributesToSet.contains("totalGastoComprometido")) {
			this.totalGastoComprometido = copia.totalGastoComprometido;
		}
		if (attributesToSet.contains("creditoDisponible")) {
			this.creditoDisponible = copia.creditoDisponible;
		}
		if (attributesToSet.contains("creditoRetenido")) {
			this.creditoRetenido = copia.creditoRetenido;
		}
		if (attributesToSet.contains("obligacionesReconocidasNetas")) {
			this.obligacionesReconocidasNetas = copia.obligacionesReconocidasNetas;
		}
		if (attributesToSet.contains("pagos")) {
			this.pagos = copia.pagos;
		}
		if (attributesToSet.contains("obligacionesPendientes31Dic")) {
			this.obligacionesPendientes31Dic = copia.obligacionesPendientes31Dic;
		}
		if (attributesToSet.contains("periodoEjecucionBeginning")) {
			this.periodoEjecucionBeginning = copia.periodoEjecucionBeginning;
		}
		if (attributesToSet.contains("periodoEjecucionEnd")) {
			this.periodoEjecucionEnd = copia.periodoEjecucionEnd;
		}
		if (attributesToSet.contains("presupuestoGasto")) {
			this.presupuestoGasto = copia.presupuestoGasto;
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

	@Column(name = "remanente_credito", nullable = false, precision = 12)
	public BigDecimal getRemanenteCredito() {
		return remanenteCredito;
	}


	public void setRemanenteCredito(BigDecimal remanenteCredito) {
		this.remanenteCredito = remanenteCredito;
	}

	@Column(name = "credito_modificaciones", nullable = false, precision = 12)
	public BigDecimal getCreditoModificaciones() {
		return creditoModificaciones;
	}


	public void setCreditoModificaciones(BigDecimal creditoModificaciones) {
		this.creditoModificaciones = creditoModificaciones;
	}

	@Column(name = "credito_definitivo_vigente", nullable = false, precision = 12)
	public BigDecimal getCreditoDefinitivoVigente() {
		return creditoDefinitivoVigente;
	}


	public void setCreditoDefinitivoVigente(BigDecimal creditoDefinitivoVigente) {
		this.creditoDefinitivoVigente = creditoDefinitivoVigente;
	}

	@Column(name = "credito_autorizado", nullable = false, precision = 12)
	public BigDecimal getCreditoAutorizado() {
		return creditoAutorizado;
	}


	public void setCreditoAutorizado(BigDecimal creditoAutorizado) {
		this.creditoAutorizado = creditoAutorizado;
	}

	@Column(name = "total_gasto_comprometido", nullable = false, precision = 12)
	public BigDecimal getTotalGastoComprometido() {
		return totalGastoComprometido;
	}


	public void setTotalGastoComprometido(BigDecimal totalGastoComprometido) {
		this.totalGastoComprometido = totalGastoComprometido;
	}

	@Column(name = "credito_disponible", nullable = false, precision = 12)
	public BigDecimal getCreditoDisponible() {
		return creditoDisponible;
	}


	public void setCreditoDisponible(BigDecimal creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}

	@Column(name = "credito_retenido", nullable = false, precision = 12)
	public BigDecimal getCreditoRetenido() {
		return creditoRetenido;
	}


	public void setCreditoRetenido(BigDecimal creditoRetenido) {
		this.creditoRetenido = creditoRetenido;
	}

	@Column(name = "obligaciones_reconocidas_netas", nullable = false, precision = 12)
	public BigDecimal getObligacionesReconocidasNetas() {
		return obligacionesReconocidasNetas;
	}


	public void setObligacionesReconocidasNetas(BigDecimal obligacionesReconocidasNetas) {
		this.obligacionesReconocidasNetas = obligacionesReconocidasNetas;
	}

	@Column(name = "pagos", nullable = false, precision = 12)
	public BigDecimal getPagos() {
		return pagos;
	}


	public void setPagos(BigDecimal pagos) {
		this.pagos = pagos;
	}

	@Column(name = "obligaciones_pend_31_dic", nullable = false, precision = 12)
	public BigDecimal getObligacionesPendientes31Dic() {
		return obligacionesPendientes31Dic;
	}


	public void setObligacionesPendientes31Dic(BigDecimal obligacionesPendientes31Dic) {
		this.obligacionesPendientes31Dic = obligacionesPendientes31Dic;
	}

	@Column(name = "periodo_ejecucion_beginning", nullable = false, precision = 12)
	public String getPeriodoEjecucionBeginning() {
		return periodoEjecucionBeginning;
	}


	public void setPeriodoEjecucionBeginning(String periodoEjecucionBeginning) {
		this.periodoEjecucionBeginning = periodoEjecucionBeginning;
	}

	@Column(name = "periodo_ejecucion_end", nullable = false, precision = 12)
	public String getPeriodoEjecucionEnd() {
		return periodoEjecucionEnd;
	}


	public void setPeriodoEjecucionEnd(String periodoEjecucionEnd) {
		this.periodoEjecucionEnd = periodoEjecucionEnd;
	}

	@Column(name = "presupuesto_gasto", nullable = false, precision = 12)
	public String getPresupuestoGasto() {
		return presupuestoGasto;
	}


	public void setPresupuestoGasto(String presupuestoGasto) {
		this.presupuestoGasto = presupuestoGasto;
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
		prefixes.put(Context.TIME, Context.TIME_URI);
		
		return prefixes;
	}


	@Override
	public String toString() {
		return "EjecucionGasto [ikey=" + ikey + ", id=" + id + ", remanenteCredito=" + remanenteCredito
				+ ", creditoModificaciones=" + creditoModificaciones + ", creditoDefinitivoVigente="
				+ creditoDefinitivoVigente + ", creditoAutorizado=" + creditoAutorizado + ", totalGastoComprometido="
				+ totalGastoComprometido + ", creditoDisponible=" + creditoDisponible + ", creditoRetenido="
				+ creditoRetenido + ", obligacionesReconocidasNetas=" + obligacionesReconocidasNetas + ", pagos="
				+ pagos + ", obligacionesPendientes31Dic=" + obligacionesPendientes31Dic
				+ ", periodoEjecucionBeginning=" + periodoEjecucionBeginning + ", periodoEjecucionEnd="
				+ periodoEjecucionEnd + ", presupuestoGasto=" + presupuestoGasto + "]";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {		
		return  (T) cloneClass( (EjecucionGasto) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public EjecucionGasto cloneClass(EjecucionGasto copia, List<String> attributesToSet) {		
		EjecucionGasto obj = new EjecucionGasto(copia,attributesToSet);	
		return obj;		
	}
	
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getPresupuestoGasto())) {
			result.add("Presupuesto is not valid [Id:" + this.getPresupuestoGasto() + "]");
		}

		return result;
	}
}
