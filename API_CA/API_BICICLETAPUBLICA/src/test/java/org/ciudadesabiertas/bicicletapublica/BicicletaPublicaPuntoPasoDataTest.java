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

package org.ciudadesabiertas.bicicletapublica;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.BicicletaPublicaPuntoPasoController;
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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BicicletaPublicaPuntoPasoDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=BicicletaPublicaPuntoPasoController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "PPASO01";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_fechaPaso() throws Exception
	{
		String paramField="fechaPaso";

		String value = "2020-01-09T07:00:00";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_trayectoId() throws Exception
	{
		String paramField="trayectoId";

		String value = "TRA04";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_orden() throws Exception
	{
		String paramField="orden";

		String value = "1";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_xETRS89() throws Exception
	{
		String paramField="xETRS89";

		String value = "440124.33000"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_yETRS89() throws Exception
	{
		String paramField="yETRS89";

		String value = "4474637.17000"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "PPASO01";
		
		String paramField="id";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "trayectoId";

		long total = TestUtils.extractTotalDistinct(BicicletaPublicaPuntoPasoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440124.33000","4474637.17000","100"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaPuntoPasoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440124.33000","4474637.17000","100","id"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaPuntoPasoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}

	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440124.33000","4474637.17000","100","id,fechaPaso,distance"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaPuntoPasoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}
	
	
	

}
