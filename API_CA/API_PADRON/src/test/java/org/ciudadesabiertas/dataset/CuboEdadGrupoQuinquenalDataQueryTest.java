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

package org.ciudadesabiertas.dataset;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.CuboEdadGrupoQuinquenalController;
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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CuboEdadGrupoQuinquenalDataQueryTest
{	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();
	
	private String queryURL=CuboEdadGrupoQuinquenalController.QUERY;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Querie_01() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure"};
		String [] value = {"refPeriod","SUM","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
		
	}
	
	@Test
	public void test_Querie_02() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"refPeriod","SUM","numeroPersonas","refPeriod=2016"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_03() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure"};
		String [] value = {"edadGruposQuinquenales","SUM","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 5);

	}
	
	@Test
	public void test_Querie_04() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"edadGruposQuinquenales","SUM","numeroPersonas","edadGruposQuinquenales='00-a-04'"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_05() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure"};
		String [] value = {"sex","SUM","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);

	}
	
	@Test
	public void test_Querie_06() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"sex","SUM","numeroPersonas","sex='sex-F'"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_07() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure"};
		String [] value = {"distritoTitle","SUM","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_08() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"distritoTitle","SUM","numeroPersonas","distritoTitle='Distrito 1'"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_09() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure"};
		String [] value = {"seccionCensalTitle","SUM","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 25);

	}
	
	@Test
	public void test_Querie_10() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"seccionCensalTitle","SUM","numeroPersonas","seccionCensalTitle='Sección Censal 20'"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Querie_11() throws Exception
	{
		
		String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"seccionCensalTitle","SUM","numeroPersonas","seccionCensalTitle='Sección Censal 20' or seccionCensalTitle='Sección Censal 22'"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);

	}
}
