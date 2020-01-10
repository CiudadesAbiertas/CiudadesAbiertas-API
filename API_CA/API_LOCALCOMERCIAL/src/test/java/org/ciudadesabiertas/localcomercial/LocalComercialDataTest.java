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

package org.ciudadesabiertas.localcomercial;


import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.LocalComercialController;
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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalComercialDataTest
{

	@Autowired
	private WebApplicationContext wac;
	
	private String listURL=LocalComercialController.LIST;


	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;


	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "290000397";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception	
	{
		String paramField= "title";

		String  value = "TIGER";
						
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_Title_Comodin() throws Exception	
	{
		String paramField= "title";

		String  value = "*COPA*";
						
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 7);
	}

	@Test
	public void test_Busqueda_description() throws Exception
	{
		String paramField="description";

		String value = "Descripcion BAR 7 COPAS*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	

	
	
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","barrioId"};

		String [] value = {"28079","280796062"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total== 9498);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioTitle() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","barrioId"};

		String [] value = {"Madrid","280796062"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total== 9498);
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
	public void test_Busqueda_municipioTitle_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
	}
	
	
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{

		String value = "Calle Bravo Murillo Num 241";
		
		String paramField="streetAddress";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String [] paramField= {"postalCode","barrioId"};

		String [] value = {"28039","280796062"};
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 9498);
	}
	
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{

		String value = "280796062";
		
		String paramField="barrioId";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 9498);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{
		
		String [] paramField= {"distritoId","barrioId"};

		String [] value = {"28079606","280796062"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 9498);
	}
	
	
	@Test
	public void test_Busqueda_x() throws Exception
	{

		String value = "440636.61";
		
		String paramField="xETRS89";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_y() throws Exception
	{

		String value = "4478906.53999";
		
		String paramField="yETRS89";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{

		String value = "9199999968";
		
		String paramField="telephone";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 118);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{

		String value = "http://api.ciudadesabiertas.org/id=285033785";
		
		String paramField="url";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_aforo() throws Exception
	{

		String value = "85";
		
		String paramField="aforo";

		long total= TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 114);
	}
	
	
	@Test
	public void test_Busqueda_agrupacionComercial() throws Exception
	{

		String value = "99000218";
		
		String paramField="agrupacionComercial";

		long total= TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 36);
	}
	
	
	@Test
	public void test_Busqueda_nombreComercial() throws Exception	
	{
		String paramField= "nombreComercial";

		String  value = "Nombre Comercial PESCADERIA";
						
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 7);
	}
	
	
	@Test
	public void test_Busqueda_referenciaCatastral() throws Exception	
	{

		
		String [] paramField= {"referenciaCatastral","barrioId"};

		String [] value = {"9872023 VH5797S 0001 WX","280796062"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 9498);
	}
	
	
	@Test
	public void test_Busqueda_rotulo() throws Exception	
	{

		
		String [] paramField= {"rotulo"};

		String [] value = {"Rotulo ADOLFO DOMINGUEZ"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_tipoSituacion() throws Exception	
	{

		
		String [] paramField= {"tipoSituacion"};

		String [] value = {"activo"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6039);
	}
	
	
	@Test
	public void test_Busqueda_tipoAcceso() throws Exception	
	{

		
		String [] paramField= {"tipoAcceso"};

		String [] value = {"puerta de calle"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 8725);
	}
	
	
	@Test
	public void test_Busqueda_tieneLicenciaApertura() throws Exception	
	{

		
		String [] paramField= {"tieneLicenciaApertura"};

		String [] value = {"60000068-106-1993-02762"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_tieneTerraza() throws Exception	
	{

		
		String [] paramField= {"tieneTerraza"};

		String [] value = {"4769"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_tipoActividadEconomica() throws Exception	
	{

		
		String [] paramField= {"tipoActividadEconomica"};

		String [] value = {"56"};
		
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1016);
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
	public void test_Busqueda_id_getX_getY() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:25830","290000397"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "tipoAcceso";

		long total = TestUtils.extractTotalDistinct(LocalComercialController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440929.42","4477579.19","50"};
		JSONArray records = TestUtils.extractRecords(LocalComercialController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 19);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440929.42","4477579.19","50","id"};
		JSONArray records = TestUtils.extractRecords(LocalComercialController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 19);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440929.42","4477579.19","50","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(LocalComercialController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 19);	
	}
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		
		String paramField = "q";

		String value = "portalId=='PORTAL000098'";			

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total > 10);
	}
}
