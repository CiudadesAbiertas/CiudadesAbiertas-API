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
import org.ciudadesabiertas.dataset.controller.OrganizationController;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
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
public class OrganizacionDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=OrganizationController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "25429480J";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";

		String value = "SANIVIDA";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"50071","L015029730baf190acd12a74aee60aa542413527f"};

	
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle"};

		String [] value = {"Zaragoza"};

		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(records == 14);
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
	public void test_Busqueda_url() throws Exception
	{		
		
		String value = "http://www.zaragozaturismo.es";
		
		value = Util.encodeURL(value);
		
		String paramField="url";		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
		
	
	@Test
	public void test_Busqueda_contactPoint_email() throws Exception
	{

		String value = "serviciocontratacion@zaragoza.es"; 
		
		String paramField = "contactPointEmail";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 12);
	}
	
//	@Test
//	public void test_Busqueda_contactPoint_faxNumber() throws Exception
//	{
//
//		String value = ""; 
//		
//		String paramField = "contactPointFaxNumber";
//
//		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
//
//		assertTrue(records == 167);
//	}
	
//	@Test
//	public void test_Busqueda_contactPoint_telephone() throws Exception
//	{
//
//		String value = ""; 
//		
//		String paramField = "contactPointTelephone";
//
//		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
//
//		assertTrue(records == 167);
//	}
	
	@Test
	public void test_Busqueda_contactPoint_title() throws Exception
	{

		String value = "Consejería de Presidencia, Hacienda e Interior del Ayuntamiento de Zaragoza"; 
		
		String paramField = "contactPointTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_street_address() throws Exception
	{

		String value = "*Hispanidad*"; 
		
		String paramField = "streetAddress";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 12);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String value = "50071"; 
		
		String paramField = "postalCode";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 12);
	}
	
	@Test
	public void test_Busqueda_lugar_portal_id() throws Exception
	{

		String value = "PORTAL000101"; 
		
		String paramField = "portalId";

		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(records == 43);
	}
	
	
	
	
	

	@Test
	public void test_Busqueda_super_portal_id() throws Exception
	{			
		
		String [] paramField= {"portalId"};

		
		String [] value = {"PORTAL000101"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 43);
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
		
		String value = "portalId";

		long total = TestUtils.extractTotalDistinct(OrganizationController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	

	

	
	
	
	

}
