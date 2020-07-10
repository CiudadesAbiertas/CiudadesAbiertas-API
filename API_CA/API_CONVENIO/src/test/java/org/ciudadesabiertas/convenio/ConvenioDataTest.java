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

package org.ciudadesabiertas.convenio;


import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ConvenioController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvenioDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=ConvenioController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "CONV002";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";

		String value = "CONVENIO PRUEBAS 2*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String [] paramField= {"description"};

		
		String [] value = {"DESC CONVENIO PRUEBAS 2*"};	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28006","CONV002"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{
		String [] paramField= {"distritoId","id"};

		String [] value = {"2800601","CONV002"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Alcobendas","CONV002"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_distritoNombre() throws Exception
	{		
		
		String [] paramField= {"distritoTitle","id"};

		String [] value = {"Unico","CONV002"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_municipioId_KO() throws Exception
	{
		String paramField="municipioId";

		String value = "29078";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
	}
		
		
	@Test
	public void test_Busqueda_Objeto() throws Exception
	{
		String paramField="objeto";

		String value = "*TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_TipoConvenio() throws Exception
	{
		String paramField="tipoConvenio";

		String value = "tipo-convenio-2";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_TipoVariacion() throws Exception
	{
		String paramField="tipoVariacion";

		String value = "prorroga";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Modalidad() throws Exception
	{
		String paramField="modalidad";

		String value = "modalidad-convenio-2";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_Materia() throws Exception
	{
		String paramField="materia";

		String value = "tipo-materia-2";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_Obligacion_economica_ayto() throws Exception
	{
		String paramField="obligacionEconomicaAyto";

		String value = "4200000.80";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_ImporteSubv() throws Exception
	{
		String paramField="importeSubv";

		String value = "400000.80";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_AdjudicatarioSub() throws Exception
	{
		String paramField="adjudicatarioTitleSub";

		String value = "Otro xx iz Gomez";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_SubvencionId() throws Exception
	{
		String paramField="subvencionId";

		String value = "SUBV002";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_OrgIdObligadoPrestacion() throws Exception
	{
		String paramField="orgIdObligadoPrestacion";

		String value = "ORG0002";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_OrganizationId() throws Exception
	{
		String paramField="organizationId";

		String value = "ORG0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_GestionadoPorOrg() throws Exception
	{
		String paramField="gestionadoPorOrganization";

		String value = "true";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_GestionadoPorDistrito() throws Exception
	{
		String paramField="gestionadoPorDistrito";

		String value = "true";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	//FECHAS
	@Test
	public void test_Busqueda_fechaInicio() throws Exception
	{

		String value = "2020-01-01T00:00:00"; 
		
		String paramField="fechaInicio";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_fechaFin() throws Exception
	{

		String value = "2021-01-01T00:00:00"; 
		
		String paramField="fechaFinalizacion";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_fechaSuscripcion() throws Exception
	{

		String value = "2020-02-01T00:00:00"; 
		
		String paramField="fechaSuscripcion";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_fecha_resolucion_fin() throws Exception
	{

		String value = "2020-06-01T00:00:00"; 
		
		String paramField="fechaResolucionFin";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_fecha_incorporacion() throws Exception
	{

		String value = "2020-03-01T00:00:00"; 
		
		String paramField="fechaIncorporacion";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fecha_adjudicacion_sub() throws Exception
	{

		String value = "2020-02-11T00:00:00"; 
		
		String paramField="fechaAdjudicacionSub";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_esVariacionId() throws Exception
	{
		String paramField="esVariacionId";

		String value = "CONV001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "28006";
		
		String paramField="municipioId";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "title";

		long total = TestUtils.extractTotalDistinct(ConvenioController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	

	

	
	
	
	

}
