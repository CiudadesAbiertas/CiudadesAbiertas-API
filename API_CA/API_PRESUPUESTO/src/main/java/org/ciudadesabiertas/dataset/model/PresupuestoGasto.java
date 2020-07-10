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
 *
 */
@Entity
@ApiModel
@Table(name = "presupuesto_gasto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESPRESUP, propiedad = "PresupuestoGasto")
@PathId(value="/presupuesto/presupuesto-gasto")
public class PresupuestoGasto implements java.io.Serializable, RDFModel {

	@JsonIgnore	
	private static final long serialVersionUID = -4625165889696822268L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador del Presupuesto Gasto")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/presupuesto/presupuesto/", sujetoFinURI="presupuesto", propiedadURI=Context.ESPRESUP_URI+"presupuestoGasto", objetoInicioURI="/presupuesto/presupuestoGasto/", objetoFinURI="id")
	private String id;

	@ApiModelProperty(value = "Crédito aprobado inicialmente para determinada aplicación presupuestaria.")
	@CsvBindByPosition(position=2)
	@CsvBindByName (column = "creditoPresupuestarioInicial")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "creditoPresupuestarioInicial", typeURI=Context.XSD_URI+"double")
	private BigDecimal creditoPresupuestarioInicial;
	
	@ApiModelProperty(value = "La clasificación orgánica agrupa por secciones y servicios los créditos asignados a los distintos centros gestores de gasto.")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "clasificacionOrganica")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionOrganica")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/organica/madrid/", finURI="clasificacionOrganica", urifyLevel=2)
	private String clasificacionOrganica;
	
	@ApiModelProperty(value = "La clasificación por programa de gasto puede referirse a la taxonomía propia del ayuntamiento, es decir a sus propios grupos de programas o programas (representada como un esquema SKOS) o a la taxonomía general establecida por la Orden EHA/3565/2008, de 3 de diciembre, por la que se aprueba la estructura de los presupuestos de las entidades locales, representada también como un esquema SKOS: https://www.boe.es/buscar/act.php?id=BOE-A-2008-19916.")
	@CsvBindByPosition(position=4)
	@CsvBindByName (column = "clasificacionPrograma")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionPrograma")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/programas-gasto/madrid/", finURI="clasificacionPrograma", urifyLevel=2)
	private String clasificacionPrograma;
	
	@ApiModelProperty(value = "La clasificación económica del gasto agrupará los créditos por capítulos separando las operaciones corrientes, las de capital y las financieras.")
	@CsvBindByPosition(position=5)
	@CsvBindByName (column = "clasificacionEconomicaGasto")	
	@Rdf(contexto = Context.ESPRESUP, propiedad = "clasificacionEconomicaGasto")
	@RdfExternalURI(inicioURI= "http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-gasto/madrid/", finURI="clasificacionEconomicaGasto", urifyLevel=2)
	private String clasificacionEconomicaGasto;
	
	@ApiModelProperty(value = "Presupuesto con el que se relaciona este Presupuesto Gasto")
	@CsvBindByPosition(position=6)
	@CsvBindByName (column = "presupuesto")	
	private String presupuesto;

	public PresupuestoGasto() {
	}

	public PresupuestoGasto(PresupuestoGasto copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		
		this.creditoPresupuestarioInicial = copia.creditoPresupuestarioInicial;
		this.clasificacionOrganica = copia.clasificacionOrganica;
		this.clasificacionPrograma = copia.clasificacionPrograma;
		this.clasificacionEconomicaGasto=copia.clasificacionEconomicaGasto;
		this.presupuesto=copia.presupuesto;
	}

	
	public PresupuestoGasto(PresupuestoGasto copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}		
		
		if (attributesToSet.contains("creditoPresupuestarioInicial")) {
			this.creditoPresupuestarioInicial = copia.creditoPresupuestarioInicial;
		}
		if (attributesToSet.contains("clasificacionOrganica")) {
			this.clasificacionOrganica = copia.clasificacionOrganica;
		}
		if (attributesToSet.contains("clasificacionPrograma")) {
			this.clasificacionPrograma = copia.clasificacionPrograma;
		}
		if (attributesToSet.contains("clasificacionEconomicaGasto")) {
			this.clasificacionEconomicaGasto = copia.clasificacionEconomicaGasto;
		}
		
		if (attributesToSet.contains("presupuesto")) {
			this.presupuesto = copia.presupuesto;
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

	@Column(name = "credito_presupuestario_inicial", nullable = false, precision = 12)
	public BigDecimal getCreditoPresupuestarioInicial() {
		return this.creditoPresupuestarioInicial;
	}

	public void setCreditoPresupuestarioInicial(BigDecimal creditoPresupuestarioInicial) {
		this.creditoPresupuestarioInicial = creditoPresupuestarioInicial;
	}

	@Column(name = "clasificacion_organica", nullable = false, length = 50)
	public String getClasificacionOrganica() {
		return this.clasificacionOrganica;
	}

	public void setClasificacionOrganica(String clasificacionOrganica) {
		this.clasificacionOrganica = clasificacionOrganica;
	}

	@Column(name = "clasificacion_programa", nullable = false, length = 50)
	public String getClasificacionPrograma() {
		return this.clasificacionPrograma;
	}

	public void setClasificacionPrograma(String clasificacionPrograma) {
		this.clasificacionPrograma = clasificacionPrograma;
	}

	@Column(name = "clasificacion_economica_gasto", nullable = false, length = 50)
	public String getClasificacionEconomicaGasto() {
		return this.clasificacionEconomicaGasto;
	}

	public void setClasificacionEconomicaGasto(String clasificacionEconomicaGasto) {
		this.clasificacionEconomicaGasto = clasificacionEconomicaGasto;
	}

	
	@Column(name = "presupuesto", length = 50)
	public String getPresupuesto() {
		return this.presupuesto;
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
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		
		return prefixes;
	}

	
	@Override
	public String toString() {
		return "PresupuestoGasto [ikey=" + ikey + ", id=" + id + ", creditoPresupuestarioInicial="
				+ creditoPresupuestarioInicial + ", clasificacionOrganica=" + clasificacionOrganica
				+ ", clasificacionPrograma=" + clasificacionPrograma + ", clasificacionEconomicaGasto="
				+ clasificacionEconomicaGasto + ", presupuesto=" + presupuesto + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {		
		return  (T) cloneClass( (PresupuestoGasto) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public PresupuestoGasto cloneClass(PresupuestoGasto copia, List<String> attributesToSet) {		
		PresupuestoGasto obj = new PresupuestoGasto(copia,attributesToSet);	
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
