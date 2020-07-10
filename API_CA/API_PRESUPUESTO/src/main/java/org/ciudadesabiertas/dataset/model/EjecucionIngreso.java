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
@Table(name = "presupuesto_ejecucion_ingreso")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "EjecucionIngreso")
@PathId(value="/presupuesto/ejecucion-ingreso")
public class EjecucionIngreso  implements java.io.Serializable, RDFModel 
{
	@JsonIgnore	
	private static final long serialVersionUID = -4625165889696822268L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador de la ejecucion Ingreso")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/presupuesto/presupuestoIngreso/", sujetoFinURI="presupuestoIngreso", propiedadURI=Context.ESPRESUP_URI+"ejecucionIngreso", objetoInicioURI="/presupuesto/ejecucionIngreso/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Una previsión inicial de ingreso puede sufrir modificaciones lo cual conlleva a una previsión definitiva.")
	@CsvBindByPosition(position=2)
	@CsvBindByName (column = "previsionModificaciones")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "previsionModificaciones", typeURI=Context.XSD_URI+"double")
	private BigDecimal previsionModificaciones;
	
	@ApiModelProperty(value = "Es la suma de la previsión inicial y sus modificaciones")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "previsionDefinitiva")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "previsionDefinitiva", typeURI=Context.XSD_URI+"double")
	private BigDecimal previsionDefinitiva;
	
	@ApiModelProperty(value = "El reconocimiento de los derechos de cobro es un acto mediante el cual se cuantifica y se reconoce la existencia de un crédito o derecho de cobro a favor de la entidad.")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "derechosReconocidos")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "derechosReconocidos", typeURI=Context.XSD_URI+"double")
	private BigDecimal derechosReconocidos;
	
	@ApiModelProperty(value = "Importe de los derechos reconocidos en el ejercicio que han sido anulados, por motivos tales como aplazamientos y fraccionamientos, anulación de liquidaciones o devoluciones de ingresos que se han pagado en ese año.")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "derechosAnulados")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "derechosAnulados", typeURI=Context.XSD_URI+"double")
	private BigDecimal derechosAnulados;
	
	@ApiModelProperty(value = "Importe de los derechos reconocidos en el ejercicio que han sido cancelados porque se han cobrado en especie o bien porque el deudor ha resultado insolvente.")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "derechosCancelados")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "derechosCancelados", typeURI=Context.XSD_URI+"double")
	private BigDecimal derechosCancelados;
	
	@ApiModelProperty(value = "Importe de los derechos reconocidos minorados por los derechos anulados y los derechos cancelados.")
	@CsvBindByPosition(position=7)
	@CsvBindByName (column = "derechosReconocidosNetos")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "derechosReconocidosNetos", typeURI=Context.XSD_URI+"double")
	private BigDecimal derechosReconocidosNetos;
	
	@ApiModelProperty(value = "Importe de los cobros que se han realizado sobre los derechos reconocidos en el ejercicio, minorados por el importe de las devoluciones de ingresos pagadas en el ejercicio.")
	@CsvBindByPosition(position=8)
	@CsvBindByName (column = "recaudacionNeta")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "recaudacionNeta", typeURI=Context.XSD_URI+"double")
	private BigDecimal recaudacionNeta;
	
	@ApiModelProperty(value = "Importe de los derechos reconocidos en el ejercicio que no se han cobrado, es decir, es el resultado de restar a los derechos reconocidos netos la recaudación neta.")
	@CsvBindByPosition(position=9)
	@CsvBindByName (column = "derechosPendientesCobro31Dic")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "derechosPendientesCobro31Dic", typeURI=Context.XSD_URI+"double")
	private BigDecimal derechosPendientesCobro31Dic;
	
	//TODO Hay que repasar el periodo ya que deberia de tener 2 nodos en blanco pero demomento no lo hemos hecho.
	@ApiModelProperty(value = "Corrresponde al período de actividad reportada en la ejecución, en general a partir del 1 de enero del año fiscal hasta la fecha de la ejecución. Comienzo del valor de la entidad temporal.")
	@CsvBindByPosition(position=10)
	@CsvBindByName (column = "periodoEjecucionBeginning")
	@Rdf(contexto = Context.TIME, propiedad = "hasBeginning")
	@RdfBlankNode(tipo=Context.TIME_URI+"properInterval", propiedad=Context.ESPRESUP_URI+"periodoEjecucion", nodoId="periodoEjecucionBeginning")
	private String periodoEjecucionBeginning;
	
	//TODO Hay que repasar el periodo ya que deberia de tener 2 nodos en blanco pero demomento no lo hemos hecho. 
	@ApiModelProperty(value = "Corrresponde al período de actividad reportada en la ejecución, en general a partir del 1 de enero del año fiscal hasta la fecha de la ejecución. Final del valor de la entidad temporal.")
	@CsvBindByPosition(position=11)
	@CsvBindByName (column = "periodoEjecucionEnd")
	@Rdf(contexto = Context.TIME, propiedad = "hasEnd")
	@RdfBlankNode(tipo=Context.TIME_URI+"properInterval", propiedad=Context.ESPRESUP_URI+"periodoEjecucion", nodoId="periodoEjecucionEnd" )
	private String periodoEjecucionEnd;
	
	@ApiModelProperty(value = "Presupuesto Ingreso con el que se relaciona esta Ejecución Ingreso")
	@CsvBindByPosition(position=12)
	@CsvBindByName (column = "presupuestoIngreso")	
	private String presupuestoIngreso;
	
	@ApiModelProperty(value = "Informa sobre la desviación que ha existido en las previsiones de ingresos. Es el resultado de restar a las previsiones definitivas los derechos reconocidos netos.")
	@CsvBindByPosition(position=13)
	@CsvBindByName (column = "excesoDefectoPrevision")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "excesoDefectoPrevision", typeURI=Context.XSD_URI+"double")
	private BigDecimal excesoDefectoPrevision;
	
	public EjecucionIngreso() 
	{
	}

	
	/**
	 * @param ikey
	 * @param id
	 * @param excesoDefectoPrevision
	 * @param previsionModificaciones
	 * @param previsionDefinitiva
	 * @param derechosReconocidos
	 * @param derechosAnulados
	 * @param derechosCancelados
	 * @param derechosReconocidosNetos
	 * @param recaudacionNeta
	 * @param derechosPendientesCobro31Dic
	 * @param periodoEjecucionBeginning
	 * @param periodoEjecucionEnd
	 * @param presupuestoIngreso
	 */
	public EjecucionIngreso(EjecucionIngreso copia) {
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.excesoDefectoPrevision = copia.excesoDefectoPrevision;
		this.previsionModificaciones = copia.previsionModificaciones;
		this.previsionDefinitiva = copia.previsionDefinitiva;
		this.derechosReconocidos = copia.derechosReconocidos;
		this.derechosAnulados = copia.derechosAnulados;
		this.derechosCancelados = copia.derechosCancelados;
		this.derechosReconocidosNetos = copia.derechosReconocidosNetos;
		this.recaudacionNeta = copia.recaudacionNeta;
		this.derechosPendientesCobro31Dic = copia.derechosPendientesCobro31Dic;
		this.periodoEjecucionBeginning = copia.periodoEjecucionBeginning;
		this.periodoEjecucionEnd = copia.periodoEjecucionEnd;
		this.presupuestoIngreso = copia.presupuestoIngreso;
	}


	public EjecucionIngreso(EjecucionIngreso copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("excesoDefectoPrevision")) {
			this.excesoDefectoPrevision = copia.excesoDefectoPrevision;
		}
		if (attributesToSet.contains("previsionModificaciones")) {
			this.previsionModificaciones = copia.previsionModificaciones;
		}
		if (attributesToSet.contains("previsionDefinitiva")) {
			this.previsionDefinitiva = copia.previsionDefinitiva;
		}
		if (attributesToSet.contains("derechosReconocidos")) {
			this.derechosReconocidos = copia.derechosReconocidos;
		}
		if (attributesToSet.contains("derechosAnulados")) {
			this.derechosAnulados = copia.derechosAnulados;
		}
		if (attributesToSet.contains("derechosCancelados")) {
			this.derechosCancelados = copia.derechosCancelados;
		}
		if (attributesToSet.contains("derechosReconocidosNetos")) {
			this.derechosReconocidosNetos = copia.derechosReconocidosNetos;
		}
		if (attributesToSet.contains("recaudacionNeta")) {
			this.recaudacionNeta = copia.recaudacionNeta;
		}
		if (attributesToSet.contains("derechosPendientesCobro31Dic")) {
			this.derechosPendientesCobro31Dic = copia.derechosPendientesCobro31Dic;
		}
		if (attributesToSet.contains("periodoEjecucionBeginning")) {
			this.periodoEjecucionBeginning = copia.periodoEjecucionBeginning;
		}
		if (attributesToSet.contains("periodoEjecucionEnd")) {
			this.periodoEjecucionEnd = copia.periodoEjecucionEnd;
		}
		if (attributesToSet.contains("presupuestoIngreso")) {
			this.presupuestoIngreso = copia.presupuestoIngreso;
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
	
	@Column(name = "exceso_defecto_prevision", nullable = false, precision = 12)
	public BigDecimal getExcesoDefectoPrevision() {
		return excesoDefectoPrevision;
	}


	public void setExcesoDefectoPrevision(BigDecimal excesoDefectoPrevision) {
		this.excesoDefectoPrevision = excesoDefectoPrevision;
	}

	@Column(name = "prevision_modificaciones", nullable = false, precision = 12)
	public BigDecimal getPrevisionModificaciones() {
		return previsionModificaciones;
	}


	public void setPrevisionModificaciones(BigDecimal previsionModificaciones) {
		this.previsionModificaciones = previsionModificaciones;
	}

	@Column(name = "prevision_definitiva", nullable = false, precision = 12)
	public BigDecimal getPrevisionDefinitiva() {
		return previsionDefinitiva;
	}


	public void setPrevisionDefinitiva(BigDecimal previsionDefinitiva) {
		this.previsionDefinitiva = previsionDefinitiva;
	}

	@Column(name = "derechos_reconocidos", nullable = false, precision = 12)
	public BigDecimal getDerechosReconocidos() {
		return derechosReconocidos;
	}


	public void setDerechosReconocidos(BigDecimal derechosReconocidos) {
		this.derechosReconocidos = derechosReconocidos;
	}

	@Column(name = "derechos_anulados", nullable = false, precision = 12)
	public BigDecimal getDerechosAnulados() {
		return derechosAnulados;
	}


	public void setDerechosAnulados(BigDecimal derechosAnulados) {
		this.derechosAnulados = derechosAnulados;
	}

	@Column(name = "derechos_cancelados", nullable = false, precision = 12)
	public BigDecimal getDerechosCancelados() {
		return derechosCancelados;
	}


	public void setDerechosCancelados(BigDecimal derechosCancelados) {
		this.derechosCancelados = derechosCancelados;
	}

	@Column(name = "derechos_reconocidos_netos", nullable = false, precision = 12)
	public BigDecimal getDerechosReconocidosNetos() {
		return derechosReconocidosNetos;
	}


	public void setDerechosReconocidosNetos(BigDecimal derechosReconocidosNetos) {
		this.derechosReconocidosNetos = derechosReconocidosNetos;
	}

	@Column(name = "recaudacion_neta", nullable = false, precision = 12)
	public BigDecimal getRecaudacionNeta() {
		return recaudacionNeta;
	}


	public void setRecaudacionNeta(BigDecimal recaudacionNeta) {
		this.recaudacionNeta = recaudacionNeta;
	}

	@Column(name = "derechos_pend_cobro_31_dic", nullable = false, precision = 12)
	public BigDecimal getDerechosPendientesCobro31Dic() {
		return derechosPendientesCobro31Dic;
	}


	public void setDerechosPendientesCobro31Dic(BigDecimal derechosPendientesCobro31Dic) {
		this.derechosPendientesCobro31Dic = derechosPendientesCobro31Dic;
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

	@Column(name = "presupuesto_ingreso", nullable = false, precision = 12)
	public String getPresupuestoIngreso() {
		return presupuestoIngreso;
	}


	public void setPresupuestoIngreso(String presupuestoIngreso) {
		this.presupuestoIngreso = presupuestoIngreso;
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
		return "EjecucionIngreso [ikey=" + ikey + ", id=" + id + ", excesoDefectoPrevision=" + excesoDefectoPrevision
				+ ", previsionModificaciones=" + previsionModificaciones + ", previsionDefinitiva="
				+ previsionDefinitiva + ", derechosReconocidos=" + derechosReconocidos + ", derechosAnulados="
				+ derechosAnulados + ", derechosCancelados=" + derechosCancelados + ", derechosReconocidosNetos="
				+ derechosReconocidosNetos + ", recaudacionNeta=" + recaudacionNeta + ", derechosPendientesCobro31Dic="
				+ derechosPendientesCobro31Dic + ", periodoEjecucionBeginning=" + periodoEjecucionBeginning
				+ ", periodoEjecucionEnd=" + periodoEjecucionEnd + ", presupuestoIngreso=" + presupuestoIngreso + "]";
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {		
		return  (T) cloneClass( (EjecucionIngreso) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public EjecucionIngreso cloneClass(EjecucionIngreso copia, List<String> attributesToSet) {		
		EjecucionIngreso obj = new EjecucionIngreso(copia,attributesToSet);	
		return obj;		
	}
	
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getPresupuestoIngreso())) {
			result.add("Presupuesto is not valid [Id:" + this.getPresupuestoIngreso() + "]");
		}

		return result;
	}
}
