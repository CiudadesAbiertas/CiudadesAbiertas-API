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

package org.ciudadesabiertas.contratos;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.TenderController;
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
public class TenderDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=TenderController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";		
		String value = "ZT03005-2020-TN1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_hasSupplier() throws Exception
	{
		String paramField="hasSupplier";		
		String value = "0085526-19-AW1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";
		String value = "*Contratación*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 2);
	}
	

	
	@Test
	public void test_Busqueda_mainProcurementCategory() throws Exception
	{
		String paramField="mainProcurementCategory";
		String value = "2";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 14);
	}
	
	@Test
	public void test_Busqueda_additionalProcurementCategory() throws Exception
	{
		String paramField="additionalProcurementCategory";
		String value = "";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 0);
	}
	
	@Test
	public void test_Busqueda_numberOfTenderers() throws Exception
	{
		String paramField="numberOfTenderers";
		String value = "3";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_procurementMethod() throws Exception
	{
		String paramField="procurementMethod";
		String value = "1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 23);
	}
	
	@Test
	public void test_Busqueda_procurementMethodDetails() throws Exception
	{
		String paramField="procurementMethodDetails";
		String value = "1";
		long records = TestUtils.extractTotal(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records == 38);
	}
	
	@Test
	public void test_Busqueda_tenderStatus() throws Exception
	{
		String paramField="tenderStatus";
		String value = "RES";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 17);
	}
	
	@Test
	public void test_Busqueda_periodDurationInDays() throws Exception
	{
		String paramField="periodDurationInDays";
		String value = "730";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 8);
	}
	
	@Test
	public void test_Busqueda_periodEndDate() throws Exception
	{
		String paramField="periodEndDate";
		String value = "2020-12-17";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_periodStartDate() throws Exception
	{
		String paramField="periodStartDate";
		String value = "2020-12-17";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_valueAmount() throws Exception
	{
		String paramField="valueAmount";
		String value = "39792.00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{
		String value = "ZT03005-2020-TN1";		
		String paramField="id";
		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);
		assertTrue(md5_content.equals(md5_head));
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{		
		String paramField="field";		
		String value = "title";
		long total = TestUtils.extractTotalDistinct(TenderController.SEARCH_DISTINCT, paramField, value, mockMvc);
		assertTrue(total == 40);
	}
	
	
}
