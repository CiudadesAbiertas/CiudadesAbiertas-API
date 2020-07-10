/**
 * 
 */
package org.ciudadesabiertas.dataset.model;

import java.math.BigDecimal;
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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
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
@Table(name = "presupuesto_ingreso")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "PresupuestoIngreso")
@PathId(value="/presupuesto/presupuesto-ingreso")
public class PresupuestoIngreso implements java.io.Serializable, RDFModel {
	
	@JsonIgnore	
	private static final long serialVersionUID = -4625165889696822268L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	
	@ApiModelProperty(value = "Identificador del Presupuesto Ingreso")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/presupuesto/presupuesto/", sujetoFinURI="presupuesto", propiedadURI=Context.ESPRESUP_URI+"presupuestoIngreso", objetoInicioURI="/presupuesto/presupuestoIngreso/", objetoFinURI="id")
	private String id;
	
	@ApiModelProperty(value = "Es el importe previsto inicialmente, en términos de recaudación (caja) o derechos reconocidos")
	@CsvBindByPosition(position=2) 
	@CsvBindByName (column = "creditoPresupuestarioInicial")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "previsionInicial", typeURI=Context.XSD_URI+"double")
	private BigDecimal previsionInicial;
	
	@ApiModelProperty(value = "La clasificación orgánica agrupa por secciones y servicios los créditos asignados a los distintos centros gestores de gasto.")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "clasificacionOrganica")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionOrganica")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/organica/madrid/", finURI="clasificacionOrganica", urifyLevel=2)
	private String clasificacionOrganica;
	
	@ApiModelProperty(value = "La clasificación económica puede referirse a la taxonomía propia del ayuntamiento, es decir a sus propios conceptos o subconceptos.")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "clasificacionEconomicaIngreso")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionEconomicaIngreso")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-ingreso", finURI="clasificacionEconomicaIngreso", urifyLevel=2)
	private String clasificacionEconomicaIngreso;
	
	@ApiModelProperty(value = "Presupuesto con el que se relaciona este Presupuesto Ingreso")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "presupuesto")	
	private String presupuesto;
	
	/**
	 * 
	 */
	public PresupuestoIngreso() {
		super();
	}

	/**
	 * @param ikey
	 * @param id
	 * @param previsionInicial
	 * @param clasificacionOrganica
	 * @param clasificacionEconomicaIngreso
	 * @param presupuesto
	 */
	public PresupuestoIngreso(PresupuestoIngreso copia) 
	{
		super();
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.previsionInicial = copia.previsionInicial;
		this.clasificacionOrganica = copia.clasificacionOrganica;
		this.clasificacionEconomicaIngreso = copia.clasificacionEconomicaIngreso;
		this.presupuesto = copia.presupuesto;
	}

	public PresupuestoIngreso(PresupuestoIngreso copia, List<String> attributesToSet) 
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}	
		
		if (attributesToSet.contains("previsionInicial")) {
			this.previsionInicial = copia.previsionInicial;
		}
		if (attributesToSet.contains("clasificacionOrganica")) {
			this.clasificacionOrganica = copia.clasificacionOrganica;
		}
		if (attributesToSet.contains("clasificacionEconomicaIngreso")) {
			this.clasificacionEconomicaIngreso = copia.clasificacionEconomicaIngreso;
		}
		
		if (attributesToSet.contains("presupuesto")) {
			this.presupuesto = copia.presupuesto;
		}
	}
	
	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}
	
	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "prevision_inicial", nullable = false, precision = 12)
	public BigDecimal getPrevisionInicial() {
		return previsionInicial;
	}

	public void setPrevisionInicial(BigDecimal previsionInicial) {
		this.previsionInicial = previsionInicial;
	}

	@Column(name = "clasificacion_organica", nullable = false, length = 50)
	public String getClasificacionOrganica() {
		return clasificacionOrganica;
	}

	public void setClasificacionOrganica(String clasificacionOrganica) {
		this.clasificacionOrganica = clasificacionOrganica;
	}

	@Column(name = "clasif_economica_ingreso", nullable = false, length = 50)
	public String getClasificacionEconomicaIngreso() {
		return clasificacionEconomicaIngreso;
	}

	public void setClasificacionEconomicaIngreso(String clasificacionEconomicaIngreso) {
		this.clasificacionEconomicaIngreso = clasificacionEconomicaIngreso;
	}

	@Column(name = "presupuesto", length = 50)
	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.RDF, Context.RDF_URI);		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.OWL, Context.OWL_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		

		prefixes.put(Context.ESPRESUP, Context.ESPRESUP_URI);		
//		prefixes.put(Context.ORG, Context.ORG_URI);
		
		
		return prefixes;
	}

	@Override
	public String toString() {
		return "PresupuestoIngreso [ikey=" + ikey + ", id=" + id + ", previsionInicial=" + previsionInicial
				+ ", clasificacionOrganica=" + clasificacionOrganica + ", clasificacionEconomicaIngreso="
				+ clasificacionEconomicaIngreso + ", presupuesto=" + presupuesto + "]";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {		
		return  (T) cloneClass( (PresupuestoIngreso) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public PresupuestoIngreso cloneClass(PresupuestoIngreso copia, List<String> attributesToSet) {		
		PresupuestoIngreso obj = new PresupuestoIngreso(copia,attributesToSet);	
		return obj;		
	}
	
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getPresupuesto())) {
			result.add("Presupuesto is not valid [Id:" + this.getPresupuesto() + "]");
		}

		return result;
	}
}
