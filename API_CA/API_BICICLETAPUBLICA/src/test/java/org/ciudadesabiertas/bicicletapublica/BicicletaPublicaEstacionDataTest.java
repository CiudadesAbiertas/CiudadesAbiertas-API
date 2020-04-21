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
import org.ciudadesabiertas.dataset.controller.BicicletaPublicaEstacionController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class BicicletaPublicaEstacionDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=BicicletaPublicaEstacionController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "EST01";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_title() throws Exception
	{
		String paramField="title";

		String value = "Estación calle atocha";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_tipoEquipamiento() throws Exception
	{
		String paramField="tipoEquipamiento";

		String value = "estacion-de-bicicletas";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{
		String paramField="streetAddress";

		String value = "calle atocha";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		String paramField="portalId";

		String value = "PORTAL000101"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
		
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{
		String paramField="postalCode";

		String value = "28003"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_estadoEstacion() throws Exception
	{
		String paramField="estadoEstacion";

		String value = "operativa"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaAlta() throws Exception
	{
		String paramField="fechaAlta";

		String value = "2020-01-01"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaBaja() throws Exception
	{
		String paramField="fechaBaja";

		String value = ""; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_observesId() throws Exception
	{
		String paramField="observesId";

		String value = "anclajesLibres"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_observesTitle() throws Exception
	{
		String paramField="observesTitle";

		String value = "Anclajes libres"; 

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_municipio_Lat() throws Exception
	{

		String value = "440124.33000";
		
		String paramField="xETRS89";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_municipio_Lon() throws Exception
	{

		String value = "4474637.17000";
		
		String paramField="yETRS89";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_id_getX_getY() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:25830","EST01"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "Estación gran vía";
		
		String paramField="title";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "estadoEstacion";

		long total = TestUtils.extractTotalDistinct(BicicletaPublicaEstacionController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440124.33000","4474637.17000","100"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaEstacionController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440124.33000","4474637.17000","100","id"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaEstacionController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
	}

	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440124.33000","4474637.17000","100","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(BicicletaPublicaEstacionController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
	}
	
	
	

}
