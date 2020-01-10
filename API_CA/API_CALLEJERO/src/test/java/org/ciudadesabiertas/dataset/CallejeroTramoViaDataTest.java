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
import org.ciudadesabiertas.dataset.controller.CallejeroTramoViaController;
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
public class CallejeroTramoViaDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL= CallejeroTramoViaController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";		
		String value = "TRA000014";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";
		String value = "Tramo comprendido entre el número 27 hasta el 27 de la/del Calle Maudes";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_Title_comodin() throws Exception
	{
		String paramField="title";
		String value = "*Calle Maudes*";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 8);
	}	
		
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};
		String [] value = {"28079","TRA000014"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{				
		String [] paramField= {"municipioTitle","id"};
		String [] value = {"Madrid","TRA000014"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	
	
	
	@Test
	public void test_Busqueda_estado() throws Exception
	{				
		String [] paramField= {"estado","id"};
		String [] value = {"vigente","TRA000014"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_via() throws Exception
	{				
		String paramField= "via";
		String value = "496400";
		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(records == 8);
	}	
	
	@Test
	public void test_Busqueda_junta() throws Exception
	{				
		String [] paramField= {"juntaAdministrativa","id"};
		String [] value = {"Chamberí","TRA000014"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numeroFinTramo() throws Exception
	{				
		String paramField= "numeroFinTramo";
		String value = "27";
		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(records == 1);
	}
	
	@Test
	public void test_Busqueda_numeroInicioTramo() throws Exception
	{				
		String paramField= "numeroInicioTramo";
		String value = "27";
		long records = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(records == 1);
	}
	 
	

	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "TRA000014";
		
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

		long total = TestUtils.extractTotalDistinct(CallejeroTramoViaController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 22);
	}	
	
	
		
	
	
}