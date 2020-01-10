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
import org.ciudadesabiertas.dataset.controller.CallejeroPortalController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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
public class CallejeroPortalDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	private String listURL= CallejeroPortalController.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";		
		String value = "PORTAL000105";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{
		String paramField="streetAddress";		
		String value = "CALLE DE RAIMUNDO FERNÁNDEZ VILLAVERDE NUMERO 49";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_streetAddress_comodin() throws Exception
	{
		String paramField="streetAddress";		
		String value = "*RAIMUNDO*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 61);
	}

	@Test
	public void test_Busqueda_postalCode() throws Exception
	{	
		String [] paramField= {"postalCode","id"};
		String [] value = {"28003","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	@Test
	public void test_Busqueda_x() throws Exception
	{
		String paramField="xETRS89";		
		String value = "440929.42";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}

	
	@Test
	public void test_Busqueda_y() throws Exception
	{
		String paramField="yETRS89";		
		String value = "4477579.19";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_barrio() throws Exception
	{	
		String [] paramField= {"barrioId","id"};
		String [] value = {"280796062","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	public void test_Busqueda_distrito() throws Exception
	{	
		String [] paramField= {"distritoId","id"};
		String [] value = {"28079606","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};
		String [] value = {"28079","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{				
		String [] paramField= {"municipioTitle","id"};
		String [] value = {"Madrid","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_provincia() throws Exception
	{				
		String [] paramField= {"provinciaId","id"};
		String [] value = {"Madrid","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	
	@Test
	public void test_Busqueda_autonomia() throws Exception
	{				
		String [] paramField= {"autonomiaId","id"};
		String [] value = {"Comunidad-Madrid*","PORTAL000105"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440929.42","4477579.19","50"};
		JSONArray records = TestUtils.extractRecords(CallejeroPortalController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 4);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440929.42","4477579.19","50","id"};
		JSONArray records = TestUtils.extractRecords(CallejeroPortalController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 4);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440929.42","4477579.19","50","id,via,distance"};
		JSONArray records = TestUtils.extractRecords(CallejeroPortalController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 4);	
	}
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "PORTAL000105";
		
		String paramField="id";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "xETRS89";

		long total = TestUtils.extractTotalDistinct(CallejeroPortalController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 120);
	}	
	
		
	
	
}
