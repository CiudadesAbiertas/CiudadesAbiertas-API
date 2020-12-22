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

package org.ciudadesabiertas.dataset.model.v1;


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
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
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
@Table(name = "subvencion_v1")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSUBV, propiedad = "Subvencion")
@PathId(value="/v1/subvencion/subvencion")
public class SubvencionV1 implements java.io.Serializable, RDFModel
{
	@JsonIgnore	
	private static final long serialVersionUID = -975372110060557710L;

	@ApiModelProperty(hidden = true)
	@JsonIgnore	
	private String ikey;
	 
	@ApiModelProperty(value = "Identificador de la subvención. Ejemplo: S0001")
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@ApiModelProperty(value = "Nombre de la subvención. Ejemplo: SUBVENCIÓN NOMINATIVA UCCI ACTIVIDADES GENERALES")
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;	 
	
	@ApiModelProperty(value = "Identificador de la propiedad que identifica al adjudicatario de una subvención. Ejemplo: G81628679")
	@CsvBindByPosition(position=3)
	@CsvBindByName (column = "adjudicatarioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	//@RdfExternalURI(tipo=Context.OWL_URI+"Thing",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/adjudicatario/", finURI="adjudicatarioId", propiedad=Context.ESSUBV_URI+"adjudicatario")
	@RdfBlankNode(tipo=Context.OWL_URI+"Thing", propiedad=Context.ESSUBV_URI+"adjudicatario", nodoId="adjudicatario")
	private String adjudicatarioId;
	 
	@ApiModelProperty(value = "Nombre de la propiedad que identifica al adjudicatario de una subvención. Ejemplo: FUNDACION REAL FABRICA DE TAPICES.")
	 @CsvBindByPosition(position=4)
	 @CsvBindByName (column = "adjudicatarioTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 //@RdfExternalURI(tipo=Context.OWL_URI+"Thing",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/adjudicatario/", finURI="adjudicatarioId", propiedad=Context.ESSUBV_URI+"adjudicatario")
	 @RdfBlankNode(tipo=Context.OWL_URI+"Thing", propiedad=Context.ESSUBV_URI+"adjudicatario", nodoId="adjudicatario")
	 private String adjudicatarioTitle;
	
	@ApiModelProperty(value = "Importe de la subvención concedida. Ejemplo: 150000.00")
	 @CsvBindByPosition(position=5)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "importe", typeURI=Context.XSD_URI+"double")
	 @CsvBindByName (column = "importe")
	 private BigDecimal importe;
	 
	@ApiModelProperty(value = "Fecha en la que se ha adjudicado la subvención. Ejemplo: 2016-04-25T00:00:00")
	 @CsvBindByPosition(position=6)
	 @CsvBindByName (column = "fechaAdjudicacion")	
	 @CsvDate(Constants.DATE_FORMAT)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "fechaAdjudicacion", typeURI=Context.XSD_URI+"date")
	 private Date fechaAdjudicacion;
	 
	@ApiModelProperty(value = "Identificador del Área del ayuntamiento que concede la subvención. Ejemplo: A05003369")
	 @CsvBindByPosition(position=7)
	 @CsvBindByName (column = "areaId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 @RdfExternalURI(inicioURI="/organigrama/organizacion/", finURI="areaId", propiedad=Context.ESSUBV_URI+"area")
	 private String areaId;
	 
	@ApiModelProperty(value = "Nombre del Área del ayuntamiento que concede la subvención. Ejemplo: ÁREA DE GOBIERNO DE CULTURA Y DEPORTE")
	 @CsvBindByPosition(position=8)
	 @CsvBindByName (column = "areaTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"area", nodoId="area")
 	 private String areaTitle;
	 
	 @Transient
	 @ApiModelProperty(hidden = true)
	 @Rdf(contexto = Context.DCT, propiedad = "identifier")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"area", nodoId="area")
	 private String areaIdIsolated;
	 
	@ApiModelProperty(value = "Identificador de la entidad que financia la subvención. Ejemplo: A05003369")
	 @CsvBindByPosition(position=9)
	 @CsvBindByName (column = "entidadFinanciadoraId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 @RdfExternalURI(inicioURI="/organigrama/organizacion/", finURI="entidadFinanciadoraId", propiedad=Context.ESSUBV_URI+"entidadFinanciadora")
	 private String entidadFinanciadoraId;
	 
	@ApiModelProperty(value = "Nombre de la entidad que financia la subvención. Ejemplo: ÁREA DE GOBIERNO DE CULTURA Y DEPORTES")
	 @CsvBindByPosition(position=10)
	 @CsvBindByName (column = "entidadFinanciadoraTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")	 
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"entidadFinanciadora", nodoId="entidadFinanciadora")	 
	 private String entidadFinanciadoraTitle;
	 
	 @Transient
	 @ApiModelProperty(hidden = true)
	 @Rdf(contexto = Context.DCT, propiedad = "identifier")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"entidadFinanciadora", nodoId="entidadFinanciadora")
	 private String entidadFinanciadoraIdIsolated;
	
	@ApiModelProperty(value = "Línea de financiación de la subvención. Ejemplo: Medio ambiente y sostenibilidad")
	 @CsvBindByPosition(position=11)
	 @CsvBindByName (column = "lineaFinanciacion", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "lineaFinanciacion", typeURI=Context.XSD_URI+"string")
	 private String lineaFinanciacion;
	 
	@ApiModelProperty(value = "Bases reguladoras de la subvención. Ejemplo: https://www.bocm.es/boletin/CM_Orden_BOCM/2015/12/30/BOCM-20151230-29.PDF")
	 @CsvBindByPosition(position=12)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "basesReguladoras")
	 @IsUri()
	 @CsvBindByName (column = "basesReguladoras", format=Constants.STRING_FORMAT)
	 private String basesReguladoras;
	
	@ApiModelProperty(value = "Tipo de instrumento utilizado por la subvención. Ejemplo: GARANTÍA")
	 @CsvBindByPosition(position=13)
	 @CsvBindByName (column = "tipoInstrumento", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "tipoInstrumento")
	 @RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-instrumento/", finURI="tipoInstrumento", urifyLevel=2)
	 private String tipoInstrumento;
	
	@ApiModelProperty(value = "Aplicacion presupuestaria de la subvención. Ejemplo: 2016-G/33601/48099")
	 @CsvBindByPosition(position=14)
	 @CsvBindByName (column = "aplicacionPresupuestaria", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESPRESUP, propiedad = "aplicacionPresupuestaria")
	 private String aplicacionPresupuestaria;
	 
	@ApiModelProperty(value = "Identificador del municipio de la subvencion. Ejemplo: 28079")
	 @CsvBindByPosition(position=15)
	 @CsvBindByName (column = "municipioId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESADM, propiedad = "municipio")
	 @RdfExternalURI(inicioURI="/territorio/municipio/",finURI="municipioId", urifyLevel = 1)
	 private String municipioId;
	 
	@ApiModelProperty(value = "Nombre del municipio de la subvencion. Ejemplo: MADRID")
	 @CsvBindByPosition(position=16)
	 @CsvBindByName (column = "municipioTitle", format=Constants.STRING_FORMAT)
	 private String municipioTitle;
	
	@ApiModelProperty(value = "Identifica si una subvención es nominativa o no. Ejemplo: true")
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="nominativa", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSUBV, propiedad = "nominativa", typeURI=Context.XSD_URI+"boolean")
	private Boolean nominativa;
	
	@ApiModelProperty(value = "Tipo de procedimiento utilizado por la subvención. Ejemplo: subvencion-nominativa")
	 @CsvBindByPosition(position=18)
	 @CsvBindByName (column = "tipoProcedimiento", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "tipoProcedimiento")
	 @RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-procedimiento/", finURI="tipoProcedimiento", urifyLevel=2)
	 private String tipoProcedimiento;
	 
	public SubvencionV1()
	{
	}

	
	
	//Contructor Copia
	public SubvencionV1(SubvencionV1 copia)
	{	
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.title = copia.title;
		this.areaId = copia.areaId;
		this.areaTitle = copia.areaTitle;
		this.municipioId = copia.municipioId;
		this.municipioTitle = copia.municipioTitle;
		this.adjudicatarioId = copia.adjudicatarioId;
		this.adjudicatarioTitle = copia.adjudicatarioTitle;
		this.entidadFinanciadoraId = copia.entidadFinanciadoraId;
		this.entidadFinanciadoraTitle = copia.entidadFinanciadoraTitle;
		this.importe = copia.importe;
		this.fechaAdjudicacion = copia.fechaAdjudicacion;
		this.lineaFinanciacion = copia.lineaFinanciacion;
		this.basesReguladoras = copia.basesReguladoras;
		this.tipoInstrumento = copia.tipoInstrumento;
		this.aplicacionPresupuestaria = copia.aplicacionPresupuestaria;
		this.nominativa = copia.nominativa;
		this.tipoProcedimiento = copia.tipoProcedimiento;
	}
	
	//Constructor copia con lista de attributos a settear
	public SubvencionV1(SubvencionV1 copia, List<String> attributesToSet)
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
		if (attributesToSet.contains("areaId")) {
			this.areaId = copia.areaId;
		}
		if (attributesToSet.contains("areaTitle")) {
			this.areaTitle = copia.areaTitle;
		}
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}
		if (attributesToSet.contains("adjudicatarioId")) {
			this.adjudicatarioId = copia.adjudicatarioId;
		}
		if (attributesToSet.contains("adjudicatarioTitle")) {
			this.adjudicatarioTitle = copia.adjudicatarioTitle;
		}
		if (attributesToSet.contains("entidadFinanciadoraId")) {
			this.entidadFinanciadoraId = copia.entidadFinanciadoraId;
		}
		if (attributesToSet.contains("entidadFinanciadoraTitle")) {
			this.entidadFinanciadoraTitle = copia.entidadFinanciadoraTitle;
		}
		if (attributesToSet.contains("importe")) {
			this.importe = copia.importe;
		}
		if (attributesToSet.contains("fechaAdjudicacion")) {
			this.fechaAdjudicacion = copia.fechaAdjudicacion;
		}
		if (attributesToSet.contains("lineaFinanciacion")) {
			this.lineaFinanciacion = copia.lineaFinanciacion;
		}
		if (attributesToSet.contains("basesReguladoras")) {
			this.basesReguladoras = copia.basesReguladoras;
		}
		if (attributesToSet.contains("tipoInstrumento")) {
			this.tipoInstrumento = copia.tipoInstrumento;
		}
		if (attributesToSet.contains("aplicacionPresupuestaria")) {
			this.aplicacionPresupuestaria = copia.aplicacionPresupuestaria;
		}
		if (attributesToSet.contains("nominativa")) {
			this.nominativa = copia.nominativa;
		}
		if (attributesToSet.contains("tipoProcedimiento")) {
			this.tipoProcedimiento = copia.tipoProcedimiento;
		}
		
	}
	

	@Id

	@Column(name = Constants.IKEY, unique = true, nullable = false)
	public String getIkey()
	{
		return this.ikey;
	}

	public void setIkey(String ikey)
	{
		this.ikey = ikey;
	}

	@Column(name = Constants.IDENTIFICADOR, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "title", length = 400)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "area_id", length = 50)
	public String getAreaId()
	{
		return this.areaId;
	}

	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}

	@Column(name = "area_title", length = 200)
	public String getAreaTitle()
	{
		return this.areaTitle;
	}

	public void setAreaTitle(String areaTitle)
	{
		this.areaTitle = areaTitle;
	}

	@Column(name = "municipio_id", length = 10)
	public String getMunicipioId()
	{
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId)
	{
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle()
	{
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle)
	{
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "adjudicatario_id", length = 50)
	public String getAdjudicatarioId()
	{
		return this.adjudicatarioId;
	}

	public void setAdjudicatarioId(String adjudicatarioId)
	{
		this.adjudicatarioId = adjudicatarioId;
	}

	@Column(name = "adjudicatario_title", length = 200)
	public String getAdjudicatarioTitle()
	{
		return this.adjudicatarioTitle;
	}

	public void setAdjudicatarioTitle(String adjudicatarioTitle)
	{
		this.adjudicatarioTitle = adjudicatarioTitle;
	}

	@Column(name = "entidad_financiadora_id", length = 50)
	public String getEntidadFinanciadoraId()
	{
		return this.entidadFinanciadoraId;
	}

	public void setEntidadFinanciadoraId(String entidadFinanciadoraId)
	{
		this.entidadFinanciadoraId = entidadFinanciadoraId;
	}

	@Column(name = "entidad_financiadora_title", length = 200)
	public String getEntidadFinanciadoraTitle()
	{
		return this.entidadFinanciadoraTitle;
	}

	public void setEntidadFinanciadoraTitle(String entidadFinanciadoraTitle)
	{
		this.entidadFinanciadoraTitle = entidadFinanciadoraTitle;
	}

	@Column(name = "importe", precision = 12)
	public BigDecimal getImporte()
	{
		return this.importe;
	}

	public void setImporte(BigDecimal importe)
	{
		this.importe = importe;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_adjudicacion", length = 19)
	public Date getFechaAdjudicacion()
	{
		return this.fechaAdjudicacion;
	}

	public void setFechaAdjudicacion(Date fechaAdjudicacion)
	{
		this.fechaAdjudicacion = fechaAdjudicacion;
	}

	@Column(name = "linea_financiacion", length = 200)
	public String getLineaFinanciacion()
	{
		return this.lineaFinanciacion;
	}

	public void setLineaFinanciacion(String lineaFinanciacion)
	{
		this.lineaFinanciacion = lineaFinanciacion;
	}

	@Column(name = "bases_reguladoras", length = 400)
	public String getBasesReguladoras()
	{
		return this.basesReguladoras;
	}

	public void setBasesReguladoras(String basesReguladoras)
	{
		this.basesReguladoras = basesReguladoras;
	}

	@Column(name = "tipo_instrumento", nullable = false, length = 100)
	public String getTipoInstrumento()
	{
		return this.tipoInstrumento;
	}

	public void setTipoInstrumento(String tipoInstrumento)
	{
		this.tipoInstrumento = tipoInstrumento;
	}

	@Column(name = "aplicacion_presupuestaria", nullable = false, length = 100)
	public String getAplicacionPresupuestaria()
	{
		return this.aplicacionPresupuestaria;
	}

	public void setAplicacionPresupuestaria(String aplicacionPresupuestaria)
	{
		this.aplicacionPresupuestaria = aplicacionPresupuestaria;
	}
	
	@Column(name = "nominativa")
	public Boolean getNominativa() {
		return this.nominativa;
	}

	public void setNominativa(Boolean nominativa) {
		this.nominativa = nominativa;
	}
	
	@Column(name = "tipo_procedimiento", nullable = false, length = 100)
	public String getTipoProcedimiento()
	{
		return this.tipoProcedimiento;
	}

	public void setTipoProcedimiento(String tipoProcedimiento)
	{
		this.tipoProcedimiento = tipoProcedimiento;
	}
	

	@Transient
	public String getAreaIdIsolated() {
		return areaIdIsolated;
	}

	@Transient
	public void setAreaIdIsolated(String areaIdIsolated) {
		this.areaIdIsolated = areaIdIsolated;
	}
	
	
	
	@Transient
	public String getEntidadFinanciadoraIdIsolated() {
		return entidadFinanciadoraIdIsolated;
	}

	@Transient
	public void setEntidadFinanciadoraIdIsolated(String entidadFinanciadoraIdIsolated) {
		this.entidadFinanciadoraIdIsolated = entidadFinanciadoraIdIsolated;
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.RDF, Context.RDF_URI);		
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.OWL, Context.OWL_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);
		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.ESSUBV, Context.ESSUBV_URI);
		prefixes.put(Context.ESPRESUP, Context.ESPRESUP_URI);		
		prefixes.put(Context.ORG, Context.ORG_URI);
		
		
		return prefixes;
	}

		
	
	
	@Override
	public String toString() {
		return "Subvencion [id=" + id + ", title=" + title + ", adjudicatarioId=" + adjudicatarioId
				+ ", adjudicatarioTitle=" + adjudicatarioTitle + ", importe=" + importe + ", fechaAdjudicacion="
				+ fechaAdjudicacion + ", areaId=" + areaId + ", areaTitle=" + areaTitle + ", areaIdIsolated="
				+ areaIdIsolated + ", entidadFinanciadoraId=" + entidadFinanciadoraId + ", entidadFinanciadoraTitle="
				+ entidadFinanciadoraTitle + ", entidadFinanciadoraIdIsolated=" + entidadFinanciadoraIdIsolated
				+ ", lineaFinanciacion=" + lineaFinanciacion + ", basesReguladoras=" + basesReguladoras
				+ ", tipoInstrumento=" + tipoInstrumento + ", aplicacionPresupuestaria=" + aplicacionPresupuestaria
				+ ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle + ", nominativa=" + nominativa
				+ ", tipoProcedimiento=" + tipoProcedimiento + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (SubvencionV1) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public SubvencionV1 cloneClass(SubvencionV1 copia, List<String> attributesToSet) {
		
		SubvencionV1 obj = new SubvencionV1(copia,attributesToSet);		
		
		return obj;
		
	}
	
	//Subvencion
	// TODO valiaciones de subvenciones ver si es posible realizar mediante
	// anotaciones en model el saber los campos obligatorios
	public  List<String> validate() {
		List<String> result = new ArrayList<String>();

		// Validamos campos Obligatorios ver si es posible obtener esta información
		// mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:" + this.getId() + "]");
		}

		if (!Util.validValue(this.getTitle())) {
			result.add("Nombre is not valid [Nombre:" + this.getTitle() + "]");
		}

		if (!Util.validValue(this.getAdjudicatarioId())) {
			result.add("AdjudicatarioId is not valid [AdjudicatarioId:" + this.getAdjudicatarioId() + "]");
		}

		if (!Util.validValue(this.getAreaId())) {
			result.add("AreaId is not valid [AreaId:" + this.getAreaId() + "]");
		}

		if (!Util.validValue(this.getEntidadFinanciadoraId())) {
			result.add("EntidadFinanciadoraId is not valid [EntidadFinanciadoraId:" + this.getEntidadFinanciadoraId()
					+ "]");
		}

		if (!Util.validValue(this.getImporte())) {
			result.add("Importe is not valid [Importe:" + this.getImporte() + "]");
		}

		if (!Util.validValue(this.getFechaAdjudicacion())) {
			result.add("FechaAdjudicacion is not valid [FechaAdjudicacion:" + this.getFechaAdjudicacion() + "]");
		}

		return result;
	}
	
	
}
