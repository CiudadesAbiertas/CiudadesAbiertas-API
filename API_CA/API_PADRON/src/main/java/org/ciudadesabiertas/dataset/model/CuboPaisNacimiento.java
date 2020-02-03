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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfDinamico;
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


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "padron_pais_nacimiento")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.QB, propiedad = "Observation")
@PathId(value="/padron/datacube/paisNacimiento/observation")
public class CuboPaisNacimiento implements java.io.Serializable, RDFModel, DataCubeModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;
	
	@JsonIgnore
	private String ikey;	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;	
	
	@Transient
	@JsonIgnore
	@Rdf(contexto = Context.SKOS, propiedad = "notation")
	@RdfExternalURI(tipo=Context.QB_URI+"DataSet",inicioURI="/padron/datacube/", finURI="dataset", propiedad=Context.QB_URI+"dataset")
	private String dataset;
	
	@Transient
	@JsonIgnore
	@Rdf(contexto = Context.QB, propiedad = "structure")
	@RdfExternalURI(tipo=Context.QB_URI+"DataSet",inicioURI="/data-cube/data-structure-definition/", finURI="dsd", propiedad=Context.QB_URI+"structure")
	private String dsd;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SDMXTDIMENSION , propiedad = "sexo")
	@RdfExternalURI(inicioURI="http://purl.org/linked-data/sdmx/2009/code#",finURI="sex")
	private String sex;
	
	@CsvBindByPosition(position=3)	
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)	
	private String municipioId;
	
	@CsvBindByPosition(position=4)	
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)	
	private String municipioTitle;

	
	@CsvBindByPosition(position=5)	
	@CsvBindByName(column="distritoId", format=Constants.STRING_FORMAT)	
	private String distritoId;
	
	@CsvBindByPosition(position=6)	
	@CsvBindByName(column="distritoTitle", format=Constants.STRING_FORMAT)
	private String distritoTitle;
	
	@CsvBindByPosition(position=7)	
	@CsvBindByName(column="barrioId", format=Constants.STRING_FORMAT)
	private String barrioId;
	
	@CsvBindByPosition(position=8)	
	@CsvBindByName(column="barrioTitle", format=Constants.STRING_FORMAT)
	private String barrioTitle;
	
	@CsvBindByPosition(position=9)	
	@CsvBindByName(column="seccionCensalId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SDMXTDIMENSION, propiedad = "refArea")
	@RdfExternalURI(inicioURI="/territorio/seccionCensal/", finURI="seccionCensalId", urifyLevel=1)
	private String seccionCensalId;
	
	@CsvBindByPosition(position=10)	
	@CsvBindByName(column="seccionCensalTitle", format=Constants.STRING_FORMAT)	
	private String seccionCensalTitle;	
	
	@CsvBindByPosition(position=11)		
	@CsvBindByName(column="refPeriod", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SDMXTDIMENSION , propiedad = "refPeriod")
	@RdfExternalURI(inicioURI="http://reference.data.gov.uk/id/year/", finURI="refPeriod", urifyLevel=1)
	private String refPeriod;		
	
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="edad", format=Constants.STRING_FORMAT)
	@RdfDinamico(inicioURI="https://opendata.aragon.es/kos/iaest/edad-grupos-quinquenales/", finURI="edadGruposQuinquenales", propiedad=Context.IAESTDIMENSION_URI+"edad-grupos-quinquenales")
	private String edadGruposQuinquenales;
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="paisNacimiento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESPAD , propiedad = "paisNacimiento")
	@RdfExternalURI(inicioURI="/territorio/pais/", finURI="paisNacimiento", urifyLevel=3)
	private String paisNacimiento;
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="numeroPersonas")
	@Rdf(contexto = Context.ESPADMEDIDA, propiedad="numero-personas", typeURI=Context.XSD_URI+"int")
	private int numeroPersonas;
	
	public void asignaCubo()
	{
		dataset="poblacionPorNacionalidad";
	}
	
	public void asignaDSD()
	{
		dsd="poblacionPorNacionalidad";
	}
	
	public CuboPaisNacimiento()
	{
		numeroPersonas=Constants.defaultNumberValue;
	}

	public CuboPaisNacimiento(CuboPaisNacimiento copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.sex = copia.sex;
		this.municipioId = copia.municipioId;
		this.municipioTitle= copia.municipioTitle;
		this.distritoId= copia.distritoId;
		this.distritoTitle= copia.distritoTitle;
		this.barrioId= copia.barrioId;
		this.barrioTitle= copia.barrioTitle;
		this.seccionCensalId= copia.seccionCensalId;
		this.seccionCensalTitle= copia.seccionCensalTitle;
		this.refPeriod= copia.refPeriod;
		this.edadGruposQuinquenales= copia.edadGruposQuinquenales;
		this.paisNacimiento=copia.paisNacimiento;
		this.numeroPersonas= copia.numeroPersonas;

	}

	
	public CuboPaisNacimiento(CuboPaisNacimiento copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}	
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("sex")) {
			this.sex = copia.sex;
		}
		if (attributesToSet.contains("edadGruposQuinquenales")) {
			this.edadGruposQuinquenales = copia.edadGruposQuinquenales;
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
		
		if (attributesToSet.contains("paisNacimiento")) {
			this.paisNacimiento = copia.paisNacimiento;
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

	@Column(name = "edad_grupos_quinquenales", nullable = false, length = 50)
	public String getEdadGruposQuinquenales() {
		return this.edadGruposQuinquenales;
	}

	public void setEdadGruposQuinquenales(String edadGruposQuinquenales) {
		this.edadGruposQuinquenales = edadGruposQuinquenales;
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

	@Column(name = "sex", nullable = false, length = 50)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	@Column(name = "pais_nacimiento", nullable = false, length = 50)
	public String getPaisNacimiento() {
		return this.paisNacimiento;
	}

	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
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
		prefixes.put(Context.ESPADMEDIDA, Context.ESPADMEDIDA_URI);
		prefixes.put(Context.ESPAD, Context.ESPAD_URI);
		prefixes.put(Context.IAESTDIMENSION, Context.IAESTDIMENSION_URI);
		prefixes.put(Context.SDMXTDIMENSION, Context.SDMXDIMENSION_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((CuboPaisNacimiento) copia, listado);
	}
	
	public CuboPaisNacimiento cloneClass(CuboPaisNacimiento copia, List<String> attributesToSet) {

		CuboPaisNacimiento obj = new CuboPaisNacimiento(copia,attributesToSet);		

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
