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

package org.ciudadesabiertas.aparcamiento;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AparcamientoController;
import org.ciudadesabiertas.dataset.utils.AparcamientoConstants;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class AparcamientoDataTest
{

	@Autowired
	private WebApplicationContext wac;


	String  listURL = AparcamientoController.LIST;

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
		
		String value = "EQAP0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception	
	{
		String [] paramField= {"title","tipoEquipamiento"};

		//TIPO_EQUIPAMIENTO = TIPO_WIFI
		String [] value = {"Aparcamiento mixto. Arquitecto Ribera",AparcamientoConstants.TIPO_EQUIPAMIENTO_TEST[0]};
				
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		
		String [] paramField= {"description","tipoEquipamiento"};

		//TIPO_EQUIPAMIENTO = TIPO_WIFI
		String [] value = {"*residentes Abierto 24*",AparcamientoConstants.TIPO_EQUIPAMIENTO_TEST[0]};
				

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 19);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*EQAP002*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioTitle() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Madrid","*EQAP002*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	
	@Test
	public void test_Busqueda_municipioId_KO() throws Exception
	{
		String paramField="municipioId";

		String value = "29078";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertNull(records);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioTitle_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertNull(records);
	}
	
	@Test
	public void test_Busqueda_provincia() throws Exception	
	{
		
		String [] paramField= {"provincia","id"};

		String [] value = {"Madrid","*EQAP002*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_autonomia() throws Exception
	{

		String [] paramField= {"autonomia","id"};

		String [] value = {"Comunidad de Madrid","*EQAP0005*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_pais() throws Exception
	{	
		
		String [] paramField= {"pais","id"};

		String [] value = {"España","*EQAP001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_address() throws Exception
	{

		String value = "CALLE BARCELO V 2";
		
		String paramField="streetAddress";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String value = "28004";
		
		String paramField="postalCode";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{

		String value = "JUSTICIA";
		
		String paramField="barrio";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{
		
		String  paramField= "distrito";

		String  value = "CENTRO";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 16);
	}
	
	@Test
	public void test_Busqueda_X() throws Exception
	{

		
		String [] paramField= {"xETRS89",AparcamientoConstants.TIPO_EQUIPAMIENTO_FIELD};

		//TIPO_EQUIPAMIENTO
		String [] value = {"441270.00046",AparcamientoConstants.TIPO_EQUIPAMIENTO};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Y() throws Exception
	{

		
		
		String [] paramField= {"yETRS89",AparcamientoConstants.TIPO_EQUIPAMIENTO_FIELD};

		//TIPO_EQUIPAMIENTO
		String [] value = {"4475197.11781",AparcamientoConstants.TIPO_EQUIPAMIENTO};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_email() throws Exception
	{

		String value = "parkingcoronaboreal@hotmail.com";
		
		String paramField="email";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{

		String value = "913 573 714";
		
		String paramField="telephone";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{

		String value = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=db8bc2c6e051c010VgnVCM2000000c205a0aRCRD";
		String encodeValue = Util.encodeURL(value);
		String paramField="url";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, encodeValue, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_titularidad() throws Exception
	{

		
		String [] paramField= {"titularidad","id"};

		String [] value = {"AYTO MADRID","*EQAP004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_title_comodines_sin_acentos() throws Exception
	{

		String  paramField= "title";
		
		String  value = "*Aparcamiento mixto. Corona Boreal*";
		
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_id_getX_getY() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","EQAP0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","EQAP0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("latitud")!=null)&&(obj.get("longitud")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"441270","4475197","50"};
		JSONArray records = TestUtils.extractRecords(AparcamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
	}
	
	@Test
	public void test_Busqueda_geo_sort_id() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"441270","4475197","50","id"};
		JSONArray records = TestUtils.extractRecords(AparcamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"441270","4475197","50","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(AparcamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 1);	
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
		
		String value = "barrio";

		long total = TestUtils.extractTotalDistinct(AparcamientoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 29);
	}
	
	
	@Test
	public void test_Busqueda_openingHours() throws Exception
	{
		

		String [] paramField= {"openingHours","tipoEquipamiento"};

		//TIPO_EQUIPAMIENTO = Equipamiento Cultural
		String [] value = {"Sin Horario",AparcamientoConstants.TIPO_EQUIPAMIENTO_TEST[0]};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
		
}
