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
@Table(name = "subvencion")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESSUBV, propiedad = "Subvencion")
@PathId(value="/subvencion/subvencion")
public class Subvencion implements java.io.Serializable, RDFModel
{
	@JsonIgnore	
	private static final long serialVersionUID = -975372110060557710L;

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
	 @CsvBindByName (column = "areaId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 //@RdfExternalURI(tipo=Context.ORG_URI+"Organization",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/area/", finURI="areaId", propiedad=Context.ESSUBV_URI+"area")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"area", nodoId="area")
	 private String areaId;
	 
	 @CsvBindByPosition(position=4)
	 @CsvBindByName (column = "areaTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 //@RdfExternalURI(tipo=Context.ORG_URI+"Organization",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/area/", finURI="areaId", propiedad=Context.ESSUBV_URI+"area")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"area", nodoId="area")
 	 private String areaTitle;
	 
	 @CsvBindByPosition(position=5)
	 @CsvBindByName (column = "municipioId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 //@RdfExternalURI(tipo=Context.ESADM_URI+"Municipio",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/municipio/", finURI="municipioId", propiedad=Context.ESADM_URI+"municipio")
	 @RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")
	 private String municipioId;
	 
	 @CsvBindByPosition(position=6)
	 @CsvBindByName (column = "municipioTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 //@RdfExternalURI(tipo=Context.ESADM_URI+"Municipio",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/municipio/", finURI="municipioId", propiedad=Context.ESADM_URI+"municipio")
	 @RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")
	 private String municipioTitle;
	 
	 @CsvBindByPosition(position=7)
	 @CsvBindByName (column = "adjudicatarioId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 //@RdfExternalURI(tipo=Context.OWL_URI+"Thing",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/adjudicatario/", finURI="adjudicatarioId", propiedad=Context.ESSUBV_URI+"adjudicatario")
	 @RdfBlankNode(tipo=Context.OWL_URI+"Thing", propiedad=Context.ESSUBV_URI+"adjudicatario", nodoId="adjudicatario")
	 private String adjudicatarioId;
	 
	 @CsvBindByPosition(position=8)
	 @CsvBindByName (column = "adjudicatarioTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 //@RdfExternalURI(tipo=Context.OWL_URI+"Thing",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/adjudicatario/", finURI="adjudicatarioId", propiedad=Context.ESSUBV_URI+"adjudicatario")
	 @RdfBlankNode(tipo=Context.OWL_URI+"Thing", propiedad=Context.ESSUBV_URI+"adjudicatario", nodoId="adjudicatario")
	private String adjudicatarioTitle;
	 
	 @CsvBindByPosition(position=9)
	 @CsvBindByName (column = "entidadFinanciadoraId", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	 //@RdfExternalURI(tipo=Context.ORG_URI+"Organization",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/entidadFinanciadora/", finURI="entidadFinanciadoraId", propiedad=Context.ESSUBV_URI+"entidadFinanciadora")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"entidadFinanciadora", nodoId="entidadFinanciadora")
	private String entidadFinanciadoraId;
	 
	 @CsvBindByPosition(position=10)
	 @CsvBindByName (column = "entidadFinanciadoraTitle", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.DCT, propiedad = "title")
	 //@RdfExternalURI(tipo=Context.ORG_URI+"Organization",inicioURI=Metavariables.uriBase+Metavariables.contexto+"/entidadFinanciadora/", finURI="entidadFinanciadoraId", propiedad=Context.ESSUBV_URI+"entidadFinanciadora")
	 @RdfBlankNode(tipo=Context.ORG_URI+"Organization", propiedad=Context.ESSUBV_URI+"entidadFinanciadora", nodoId="entidadFinanciadora")
	private String entidadFinanciadoraTitle;
	 
	 @CsvBindByPosition(position=11)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "importe", typeURI=Context.XSD_URI+"double")
	 @CsvBindByName (column = "importe")
	private BigDecimal importe;
	 
	 @CsvBindByPosition(position=12)
	 @CsvBindByName (column = "fechaAdjudicacion")	
	 @CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESSUBV, propiedad = "fechaAdjudicacion", typeURI=Context.XSD_URI+"date")
	private Date fechaAdjudicacion;	 
	
	 @CsvBindByPosition(position=13)
	 @CsvBindByName (column = "lineaFinanciacion", format=Constants.STRING_FORMAT)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "lineaFinanciacion", typeURI=Context.XSD_URI+"string")
	private String lineaFinanciacion;
	 
	 @CsvBindByPosition(position=14)
	 @Rdf(contexto = Context.ESSUBV, propiedad = "basesReguladoras")
	 @IsUri()
	 @CsvBindByName (column = "basesReguladoras", format=Constants.STRING_FORMAT)
	private String basesReguladoras;
	 
	 @CsvBindByPosition(position=15)
	@CsvBindByName (column = "tipoInstrumento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESSUBV, propiedad = "tipoInstrumento")
	@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/subvencion/tipo-instrumento/", finURI="tipoInstrumento", urifyLevel=2)
	private String tipoInstrumento;
	
	 @CsvBindByPosition(position=16)
	 @CsvBindByName (column = "aplicacionPresupuestaria", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESPRESUP, propiedad = "aplicacionPresupuestaria")
	private String aplicacionPresupuestaria;

	public Subvencion()
	{
	}

	public Subvencion(String ikey, String tipoInstrumento, String aplicacionPresupuestaria)
	{
		this.ikey = ikey;
		this.tipoInstrumento = tipoInstrumento;
		this.aplicacionPresupuestaria = aplicacionPresupuestaria;
	}

	
	
	//Contructor Copia
	public Subvencion(Subvencion copia)
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
	}
	
	//Constructor copia con lista de attributos a settear
	public Subvencion(Subvencion copia, List<String> attributesToSet)
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
		return "Subvencion [id=" + id + ", title=" + title + ", areaId=" + areaId
				+ ", areaTitle=" + areaTitle + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", adjudicatarioId=" + adjudicatarioId + ", adjudicatarioTitle=" + adjudicatarioTitle
				+ ", entidadFinanciadoraId=" + entidadFinanciadoraId + ", entidadFinanciadoraTitle="
				+ entidadFinanciadoraTitle + ", importe=" + importe + ", fechaAdjudicacion=" + fechaAdjudicacion
				+ ", lineaFinanciacion=" + lineaFinanciacion + ", basesReguladoras=" + basesReguladoras + ", tipoInstrumento=" + tipoInstrumento
				+ ", aplicacionPresupuestaria=" + aplicacionPresupuestaria + "]";
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return  (T) cloneClass( (Subvencion) copia,listado) ;
	}
	
	
	// Constructor copia con lista de attributos a settear
	public Subvencion cloneClass(Subvencion copia, List<String> attributesToSet) {
		
		Subvencion obj = new Subvencion(copia,attributesToSet);		
		
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