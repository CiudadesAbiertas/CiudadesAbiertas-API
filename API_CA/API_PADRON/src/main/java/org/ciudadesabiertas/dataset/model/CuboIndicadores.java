/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.dataset.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.DataCubeModel;
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
@Table(name = "padron_indicadores")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.QB, propiedad = "Observation")
@PathId(value="/padron/datacube/indicadores/observation")
public class CuboIndicadores implements java.io.Serializable, RDFModel, DataCubeModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@ApiModelProperty(value = "Identificador de la observación. Ejemplo: obs1")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;	
	
	@ApiModelProperty(value = "Conjunto de datos de la observación. Ejemplo: poblacionPorIndicadores")
	@Transient
	@JsonIgnore
	@Rdf(contexto = Context.SKOS, propiedad = "notation")
	@RdfExternalURI(tipo=Context.QB_URI+"DataSet",inicioURI="/padron/datacube/", finURI="dataset", propiedad=Context.QB_URI+"dataset")
	private String dataset;
	
	@ApiModelProperty(value = "DSD de la observación. Ejemplo: poblacionPorIndicadores")
	@Transient
	@JsonIgnore
	@Rdf(contexto = Context.QB, propiedad = "structure")
	@RdfExternalURI(tipo=Context.QB_URI+"DataSet",inicioURI="/data-cube/data-structure-definition/", finURI="dsd", propiedad=Context.QB_URI+"structure")
	private String dsd;
	
	@ApiModelProperty(value = "El período de tiempo o punto en el tiempo al que se refiere la observación. Ejemplo: 2016")
	@CsvBindByPosition(position=2)		
	@CsvBindByName(column="refPeriod", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SDMXTDIMENSION , propiedad = "refPeriod")
	@RdfExternalURI(inicioURI="http://reference.data.gov.uk/id/year/", finURI="refPeriod", urifyLevel=1)
	private String refPeriod;
	
	@ApiModelProperty(value = "Identificador del municipio de la observación. Ejemplo: 28006")
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)	
	private String municipioId;
	
	@ApiModelProperty(value = "Nombre del municipio de la observación. Ejemplo: Alcobendas")
	@CsvBindByPosition(position=4)	
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)	
	private String municipioTitle;
	
	@ApiModelProperty(value = "Identificador del distrito de la observación. Ejemplo: 2800601")
	@CsvBindByPosition(position=5)	
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)	
	private String distritoId;
	
	@ApiModelProperty(value = "Nombre del distrito de la observación. Ejemplo: Distrito 1")
	@CsvBindByPosition(position=6)	
	@CsvBindByName(column="distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@ApiModelProperty(value = "Identificador del barrio de la observación. Ejemplo: 28006011")
	@CsvBindByPosition(position=7)	
	@CsvBindByName(column="barrioId", format=Constants.STRING_FORMAT)
	private String barrioId;
	
	@ApiModelProperty(value = "Nombre del barrio de la observación. Ejemplo: Barrio 1")
	@CsvBindByPosition(position=8)	
	@CsvBindByName(column="barrioTitle", format=Constants.STRING_FORMAT)
	private String barrioTitle;
	
	@ApiModelProperty(value = "Identificador de la sección censal de la observación. Ejemplo: 2800601020")
	@CsvBindByPosition(position=9)	
	@CsvBindByName(column="seccionCensalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SDMXTDIMENSION, propiedad = "refArea")
	@RdfExternalURI(inicioURI="/territorio/seccion-censal/", finURI="seccionCensalId", urifyLevel=1)
	private String seccionCensalId;
	
	@ApiModelProperty(value = "Nombre de la sección censal de la observación. Ejemplo: Sección Censal 20")
	@CsvBindByPosition(position=10)	
	@CsvBindByName(column="seccionCensalTitle", format=Constants.STRING_FORMAT)	
	private String seccionCensalTitle;	
	
	@ApiModelProperty(value = "Numero de personas de la observación. Ejemplo: 20623")
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="numeroPersonas")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="numero-personas", typeURI=Context.XSD_URI+"int")
	private int numeroPersonas;	
	
	@ApiModelProperty(value = "Indice de dependencia de la observación. Ejemplo: 80")
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="indiceDependencia")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-dependencia", typeURI=Context.XSD_URI+"int")
	private int indiceDependencia;
	
	@ApiModelProperty(value = "Indice de feminidad de la observación. Ejemplo: 119")
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="indiceFeminidad")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-feminidad", typeURI=Context.XSD_URI+"int")	
	private int indiceFeminidad;
	
	@ApiModelProperty(value = "Indice de infancia de la observación. Ejemplo: 35")
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="indiceInfancia")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-infancia", typeURI=Context.XSD_URI+"int")
	private int indiceInfancia;
	
	@ApiModelProperty(value = "Indice de juventud de la observación. Ejemplo: 97")
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="indiceJuventud")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-juventud", typeURI=Context.XSD_URI+"int")
	private int indiceJuventud;
	
	@ApiModelProperty(value = "Indice de maternidad de la observación. Ejemplo: 9")
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="indiceMaternidad")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-maternidad", typeURI=Context.XSD_URI+"int")
	private int indiceMaternidad;
	
	@ApiModelProperty(value = "Indice de población activa de la observación. Ejemplo: 91")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="indicePoblacionActiva")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-poblacion-activa", typeURI=Context.XSD_URI+"int")
	private int indicePoblacionActiva;
	
	@ApiModelProperty(value = "Indice de reemplazo de la observación. Ejemplo: 13")
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="indiceReemplazo")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-reemplazo", typeURI=Context.XSD_URI+"int")
	private int indiceReemplazo;
	
	@ApiModelProperty(value = "Indice de sobreemvejecimiento de la observación. Ejemplo: 20")
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="indiceSobreenvejecimiento")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-sobreenvejecimiento", typeURI=Context.XSD_URI+"int")	
	private int indiceSobreenvejecimiento;
	
	@ApiModelProperty(value = "Indice de tendencia de la observación. Ejemplo: 97")
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="indiceTendencia")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="indice-de-tendencia", typeURI=Context.XSD_URI+"int")	
	private int indiceTendencia;
	
	@ApiModelProperty(value = "Indice de mortalidad de la observación. Ejemplo: 991")
	@CsvBindByPosition(position=21)
	@CsvBindByName(column="tasaMortalidad")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="tasa-de-mortalidad", typeURI=Context.XSD_URI+"int")	
	private int tasaMortalidad;
	
	@ApiModelProperty(value = "Indice de natalidad de la observación. Ejemplo: 378")
	@CsvBindByPosition(position=22)
	@CsvBindByName(column="tasaNatalidad")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="tasa-de-natalidad", typeURI=Context.XSD_URI+"int")
	private int tasaNatalidad;
	
	@ApiModelProperty(value = "Edad media de la población de la observación. Ejemplo: 75")
	@CsvBindByPosition(position=23)
	@CsvBindByName(column="edadMediaPoblacion")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="edad-media-de-la-poblacion", typeURI=Context.XSD_URI+"int")
	private int edadMediaPoblacion;
	
	@ApiModelProperty(value = "Porcentaje de población joven de la observación. Ejemplo: 17")
	@CsvBindByPosition(position=24)
	@CsvBindByName(column="porcentajePoblacionJoven")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="porcentaje-de-poblacion-joven", typeURI=Context.XSD_URI+"int")	
	private int porcentajePoblacionJoven;
	
	@ApiModelProperty(value = "Porcentaje de población adulta de la observación. Ejemplo: 17")
	@CsvBindByPosition(position=25)
	@CsvBindByName(column="porcentajePoblacionAdulta")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="porcentaje-de-poblacion-adulta", typeURI=Context.XSD_URI+"int")	
	private int porcentajePoblacionAdulta;
	
	@ApiModelProperty(value = "Porcentaje de población envejecida de la observación. Ejemplo: 8")
	@CsvBindByPosition(position=26)
	@CsvBindByName(column="porcentajePoblacionEnvejecida")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="porcentaje-de-poblacion-envejecida", typeURI=Context.XSD_URI+"int")
	private int porcentajePoblacionEnvejecida;
	
	@ApiModelProperty(value = "Porcentaje de población extranjera infantil de la observación. Ejemplo: 10")
	@CsvBindByPosition(position=27)
	@CsvBindByName(column="porcentajePoblacionExtranjeraInfantil")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="porcentaje-de-poblacion-infantil-extranjera", typeURI=Context.XSD_URI+"int")	
	private int porcentajePoblacionExtranjeraInfantil;
	
	
	
	public void asignaCubo()
	{
		dataset="poblacionPorIndicadores";
	}
	
	public void asignaDSD()
	{
		dsd="poblacionPorIndicadores";
	}
	
	public CuboIndicadores()
	{
		numeroPersonas=Constants.defaultNumberValue;
		indiceDependencia=Constants.defaultNumberValue;
		indiceFeminidad=Constants.defaultNumberValue;
		indiceInfancia=Constants.defaultNumberValue;
		indiceJuventud=Constants.defaultNumberValue;
		indiceMaternidad=Constants.defaultNumberValue;
		indicePoblacionActiva=Constants.defaultNumberValue;
		indiceReemplazo=Constants.defaultNumberValue;
		indiceSobreenvejecimiento=Constants.defaultNumberValue;
		indiceTendencia=Constants.defaultNumberValue;
		tasaMortalidad=Constants.defaultNumberValue;
		tasaNatalidad=Constants.defaultNumberValue;
		edadMediaPoblacion=Constants.defaultNumberValue;
		porcentajePoblacionJoven=Constants.defaultNumberValue;
		porcentajePoblacionAdulta=Constants.defaultNumberValue;
		porcentajePoblacionEnvejecida=Constants.defaultNumberValue;
		porcentajePoblacionExtranjeraInfantil=Constants.defaultNumberValue;
	}

	public CuboIndicadores(CuboIndicadores copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;		
		this.municipioId = copia.municipioId;
		this.municipioTitle= copia.municipioTitle;
		this.distritoId= copia.distritoId;
		this.distritoTitle= copia.distritoTitle;
		this.barrioId= copia.barrioId;
		this.barrioTitle= copia.barrioTitle;
		this.seccionCensalId= copia.seccionCensalId;
		this.seccionCensalTitle= copia.seccionCensalTitle;
		this.refPeriod= copia.refPeriod;
	
		
		this.numeroPersonas= copia.numeroPersonas;
		this.indiceDependencia= copia.indiceDependencia;
		this.indiceFeminidad= copia.indiceFeminidad;
		this.indiceInfancia= copia.indiceInfancia;
		this.indiceJuventud= copia.indiceJuventud;
		this.indiceMaternidad= copia.indiceMaternidad;
		this.indicePoblacionActiva= copia.indicePoblacionActiva;
		this.indiceReemplazo= copia.indiceReemplazo;
		this.indiceSobreenvejecimiento= copia.indiceSobreenvejecimiento;
		this.indiceTendencia= copia.indiceTendencia;
		this.tasaMortalidad= copia.tasaMortalidad;
		this.tasaNatalidad= copia.tasaNatalidad;
		this.edadMediaPoblacion= copia.edadMediaPoblacion;
		this.porcentajePoblacionJoven= copia.porcentajePoblacionJoven;
		this.porcentajePoblacionAdulta= copia.porcentajePoblacionAdulta;
		this.porcentajePoblacionEnvejecida= copia.porcentajePoblacionEnvejecida;
		this.porcentajePoblacionExtranjeraInfantil= copia.porcentajePoblacionExtranjeraInfantil;

	}

	
	public CuboIndicadores(CuboIndicadores copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}	
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		
		if (attributesToSet.contains("distritoId")) {
			this.distritoId = copia.distritoId;
		}
		if (attributesToSet.contains("distritoTitle")) {
			this.distritoTitle = copia.distritoTitle;
		}
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}	
		if (attributesToSet.contains("barrioId")) {
			this.barrioId = copia.barrioId;
		}
		if (attributesToSet.contains("barrioTitle")) {
			this.barrioTitle = copia.barrioTitle;
		}	
		if (attributesToSet.contains("seccionCensalId")) {
			this.seccionCensalId = copia.seccionCensalId;
		}
		if (attributesToSet.contains("seccionCensalTitle")) {
			this.seccionCensalTitle = copia.seccionCensalTitle;
		}	
		if (attributesToSet.contains("refPeriod")) {
			this.refPeriod = copia.refPeriod;
		}
		
		if (attributesToSet.contains("indiceDependencia")) {
			this.indiceDependencia= copia.indiceDependencia;
		}
		
		if (attributesToSet.contains("indiceFeminidad")) {
			this.indiceFeminidad= copia.indiceFeminidad;
		}		
		if (attributesToSet.contains("indiceInfancia")) {
			this.indiceInfancia= copia.indiceInfancia;
		}		
		if (attributesToSet.contains("refPeriod")) {
			this.indiceJuventud= copia.indiceJuventud;
		}	
		if (attributesToSet.contains("indiceMaternidad")) {
			this.indiceMaternidad= copia.indiceMaternidad;
		}		
		if (attributesToSet.contains("indicePoblacionActiva")) {
			this.indicePoblacionActiva= copia.indicePoblacionActiva;
		}
		if (attributesToSet.contains("indiceReemplazo")) {
			this.indiceReemplazo= copia.indiceReemplazo;
		}			
		if (attributesToSet.contains("indiceSobreenvejecimiento")) {
			this.indiceSobreenvejecimiento= copia.indiceSobreenvejecimiento;
		}				
		if (attributesToSet.contains("indiceTendencia")) {
			this.indiceTendencia= copia.indiceTendencia;
		}
		if (attributesToSet.contains("tasaMortalidad")) {
			this.tasaMortalidad= copia.tasaMortalidad;	
		}		
		if (attributesToSet.contains("edadMediaPoblacion")) {
			this.edadMediaPoblacion= copia.edadMediaPoblacion;
		}
		if (attributesToSet.contains("porcentajePoblacionJoven")) {
			this.porcentajePoblacionJoven= copia.porcentajePoblacionJoven;
		}
		if (attributesToSet.contains("porcentajePoblacionAdulta")) {
			this.porcentajePoblacionAdulta= copia.porcentajePoblacionAdulta;
		}
		if (attributesToSet.contains("porcentajePoblacionEnvejecida")) {
			this.porcentajePoblacionEnvejecida= copia.porcentajePoblacionEnvejecida;
		}
		if (attributesToSet.contains("porcentajePoblacionExtranjeraInfantil")) {
			this.porcentajePoblacionExtranjeraInfantil= copia.porcentajePoblacionExtranjeraInfantil;
		}	
		if (attributesToSet.contains("numeroPersonas")) {
			this.numeroPersonas = copia.numeroPersonas;
		}
		
	}




	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey()
	{
		return this.ikey;
	}

	public void setIkey(String ikey)
	{
		this.ikey = ikey;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	@Column(name = "municipio_id", nullable = false, length = 50)
	public String getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", nullable = false, length = 400)
	public String getMunicipioTitle() {
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
	}

	

	@Column(name = "distrito_id", nullable = false, length = 50)
	public String getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	
	@Column(name = "distrito_title", nullable = false, length = 400)
	public String getDistritoTitle() {
		return this.distritoTitle;
	}

	public void setDistritoTitle(String distritoTitle) {
		this.distritoTitle = distritoTitle;
	}
	
	@Column(name = "barrio_id", nullable = false, length = 50)
	public String getBarrioId() {
		return this.barrioId;
	}

	public void setBarrioId(String barrioId) {
		this.barrioId = barrioId;
	}

	@Column(name = "barrio_title", nullable = false, length = 400)
	public String getBarrioTitle() {
		return this.barrioTitle;
	}

	public void setBarrioTitle(String barrioTitle) {
		this.barrioTitle = barrioTitle;
	}

	@Column(name = "ref_period", nullable = false, length = 50)
	public String getRefPeriod() {
		return this.refPeriod;
	}

	public void setRefPeriod(String refPeriod) {
		this.refPeriod = refPeriod;
	}
	
	@Column(name = "numero_personas", nullable = false)
	public int getNumeroPersonas() {
		return this.numeroPersonas;
	}

	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	
	
	
	@Column(name = "seccion_censal_id", nullable = false, length = 50)
	public String getSeccionCensalId() {
		return this.seccionCensalId;
	}

	public void setSeccionCensalId(String seccionCensalId) {
		this.seccionCensalId = seccionCensalId;
	}

	@Column(name = "seccion_censal_title", nullable = false, length = 400)
	public String getSeccionCensalTitle() {
		return this.seccionCensalTitle;
	}

	public void setSeccionCensalTitle(String seccionCensalTitle) {
		this.seccionCensalTitle = seccionCensalTitle;
	}
	
	
	@Column(name = "indice_dependencia", nullable = false)
	public int getIndiceDependencia() {
		return this.indiceDependencia;
	}

	public void setIndiceDependencia(int indiceDependencia) {
		this.indiceDependencia = indiceDependencia;
	}

	@Column(name = "indice_feminidad", nullable = false)
	public int getIndiceFeminidad() {
		return this.indiceFeminidad;
	}

	public void setIndiceFeminidad(int indiceFeminidad) {
		this.indiceFeminidad = indiceFeminidad;
	}

	@Column(name = "indice_infancia", nullable = false)
	public int getIndiceInfancia() {
		return this.indiceInfancia;
	}

	public void setIndiceInfancia(int indiceInfancia) {
		this.indiceInfancia = indiceInfancia;
	}

	@Column(name = "indice_juventud", nullable = false)
	public int getIndiceJuventud() {
		return this.indiceJuventud;
	}

	public void setIndiceJuventud(int indiceJuventud) {
		this.indiceJuventud = indiceJuventud;
	}

	@Column(name = "indice_maternidad", nullable = false)
	public int getIndiceMaternidad() {
		return this.indiceMaternidad;
	}

	public void setIndiceMaternidad(int indiceMaternidad) {
		this.indiceMaternidad = indiceMaternidad;
	}

	@Column(name = "indice_poblacion_activa", nullable = false)
	public int getIndicePoblacionActiva() {
		return this.indicePoblacionActiva;
	}

	public void setIndicePoblacionActiva(int indicePoblacionActiva) {
		this.indicePoblacionActiva = indicePoblacionActiva;
	}

	@Column(name = "indice_reemplazo", nullable = false)
	public int getIndiceReemplazo() {
		return this.indiceReemplazo;
	}

	public void setIndiceReemplazo(int indiceReemplazo) {
		this.indiceReemplazo = indiceReemplazo;
	}

	@Column(name = "indice_sobreenvejecimiento", nullable = false)
	public int getIndiceSobreenvejecimiento() {
		return this.indiceSobreenvejecimiento;
	}

	public void setIndiceSobreenvejecimiento(int indiceSobreenvejecimiento) {
		this.indiceSobreenvejecimiento = indiceSobreenvejecimiento;
	}

	@Column(name = "indice_tendencia", nullable = false)
	public int getIndiceTendencia() {
		return this.indiceTendencia;
	}

	public void setIndiceTendencia(int indiceTendencia) {
		this.indiceTendencia = indiceTendencia;
	}

	@Column(name = "tasa_mortalidad", nullable = false)
	public int getTasaMortalidad() {
		return this.tasaMortalidad;
	}

	public void setTasaMortalidad(int tasaMortalidad) {
		this.tasaMortalidad = tasaMortalidad;
	}

	@Column(name = "tasa_natalidad", nullable = false)
	public int getTasaNatalidad() {
		return this.tasaNatalidad;
	}

	public void setTasaNatalidad(int tasaNatalidad) {
		this.tasaNatalidad = tasaNatalidad;
	}

	@Column(name = "edad_media_poblacion", nullable = false)
	public int getEdadMediaPoblacion() {
		return this.edadMediaPoblacion;
	}

	public void setEdadMediaPoblacion(int edadMediaPoblacion) {
		this.edadMediaPoblacion = edadMediaPoblacion;
	}

	@Column(name = "porcentaje_poblacion_joven", nullable = false)
	public int getPorcentajePoblacionJoven() {
		return this.porcentajePoblacionJoven;
	}

	public void setPorcentajePoblacionJoven(int porcentajePoblacionJoven) {
		this.porcentajePoblacionJoven = porcentajePoblacionJoven;
	}

	@Column(name = "porcentaje_poblacion_adulta", nullable = false)
	public int getPorcentajePoblacionAdulta() {
		return this.porcentajePoblacionAdulta;
	}

	public void setPorcentajePoblacionAdulta(int porcentajePoblacionAdulta) {
		this.porcentajePoblacionAdulta = porcentajePoblacionAdulta;
	}

	@Column(name = "porcentaje_poblacion_envejec", nullable = false)
	public int getPorcentajePoblacionEnvejecida() {
		return this.porcentajePoblacionEnvejecida;
	}

	public void setPorcentajePoblacionEnvejecida(int porcentajePoblacionEnvejecida) {
		this.porcentajePoblacionEnvejecida = porcentajePoblacionEnvejecida;
	}

	@Column(name = "porcentaje_poblacion_ext_inf", nullable = false)
	public int getPorcentajePoblacionExtranjeraInfantil() {
		return this.porcentajePoblacionExtranjeraInfantil;
	}

	public void setPorcentajePoblacionExtranjeraInfantil(int porcentajePoblacionExtranjeraInfantil) {
		this.porcentajePoblacionExtranjeraInfantil = porcentajePoblacionExtranjeraInfantil;
	}


	@Transient
	public String getDataset() {
		return dataset;
	}
	
	@Transient
	public void setDataset(String dataset) {
		 this.dataset=dataset;
	}
	
	
	@Transient
	public String getDsd() {
		return dsd;
	}
	
	@Transient
	public void setDsd(String dsd) {
		 this.dsd=dsd;
	}
	
	

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		//prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.QB, Context.QB_URI);		
		prefixes.put(Context.SKOS, Context.SKOS_URI);
		prefixes.put(Context.ESPAD, Context.ESPAD_URI);
		prefixes.put(Context.ESPADMEDIDA, Context.ESPADMEDIDA_URI);
		prefixes.put(Context.IAESTDIMENSION, Context.IAESTDIMENSION_URI);
		prefixes.put(Context.SDMXTDIMENSION, Context.SDMXDIMENSION_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((CuboIndicadores) copia, listado);
	}
	
	public CuboIndicadores cloneClass(CuboIndicadores copia, List<String> attributesToSet) {

		CuboIndicadores obj = new CuboIndicadores(copia,attributesToSet);		

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
		
		
		if (!Util.validValue(this.getNumeroPersonas())) {
			result.add("NumeroPersonas is not valid [Id:"+this.getNumeroPersonas()+"]");
		}
		
		return result;
	}
	


	
	




}
