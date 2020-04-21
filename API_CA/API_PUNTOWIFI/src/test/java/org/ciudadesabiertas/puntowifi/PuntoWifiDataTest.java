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

package org.ciudadesabiertas.puntowifi;


import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.PuntoWifiController;
import org.ciudadesabiertas.dataset.utils.PuntoWifiConstants;
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
public class PuntoWifiDataTest
{

	@Autowired
	private WebApplicationContext wac;


	private String listURL= PuntoWifiController.LIST;

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
		
		String value = "EQPW0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception	
	{
		String [] paramField= {"title","tipoEquipamiento"};

		//TIPO_EQUIPAMIENTO = TIPO_WIFI
		String [] value = {"*Sala de estudio y lectura*",PuntoWifiConstants.TIPO_EQUIPAMIENTO_TEST[0]};
				
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		
		String [] paramField= {"description","tipoEquipamiento"};

		//TIPO_EQUIPAMIENTO = TIPO_WIFI
		String [] value = {"*Puestos de lectura:*",PuntoWifiConstants.TIPO_EQUIPAMIENTO_TEST[0]};
				

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 39);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*EQPW02*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioTitle() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Madrid","*EQPW02*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
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
	public void test_Busqueda_provincia() throws Exception	
	{
		
		String [] paramField= {"provinciaId","id"};

		String [] value = {"Madrid","*EQPW003*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_autonomia() throws Exception
	{

		String [] paramField= {"autonomiaId","id"};

		String [] value = {"Comunidad-Madrid","*EQPW004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_pais() throws Exception
	{	
		
		String [] paramField= {"paisId","id"};

		String [] value = {"España","*EQPW004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_address() throws Exception
	{

		String value = "CALLE BUSTAMANTE V 16";
		
		String paramField="streetAddress";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String value = "28009";
		
		String paramField="postalCode";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{
		String [] paramField= {"barrioId","postalCode"};
		String [] value = {"280796062","28043"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{		
				
		String [] paramField= {"distritoId","postalCode"};
		String [] value = {"28079606","28043"};

		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_XETRS89() throws Exception
	{

		
		String [] paramField= {"xETRS89",PuntoWifiConstants.TIPO_EQUIPAMIENTO_FIELD};

		//TIPO_EQUIPAMIENTO
		String [] value = {"437829.00050",PuntoWifiConstants.TIPO_EQUIPAMIENTO};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_YETRS89() throws Exception
	{

		
		
		String [] paramField= {"yETRS89",PuntoWifiConstants.TIPO_EQUIPAMIENTO_FIELD};

		//TIPO_EQUIPAMIENTO
		String [] value = {"4473131.11756",PuntoWifiConstants.TIPO_EQUIPAMIENTO};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_email() throws Exception
	{

		String value = "objetosperdidos@madrid.es";
		
		String paramField="email";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{

		String value = "915 279 590";
		
		String paramField="telephone";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{

		String value = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=f7c6f4061d497210VgnVCM2000000c205a0aRCRD";
		String encodedValue = Util.encodeURL(value);
		String paramField="url";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, encodedValue, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_titularidadPublica() throws Exception
	{

		
		String [] paramField= {"titularidadPublica","id"};

		String [] value = {"true","*EQPW004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_title_comodines_sin_acentos() throws Exception
	{

		String  paramField= "title";
		
		String  value = "*Formacion de Ancora*";
		
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_con_SRID() throws Exception
	{		
		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","EQPW0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject eq = (org.json.simple.JSONObject) records.get(0);
		
		double coordinateX=(double) eq.get("xETRS89");
		
		double coordinateY=(double) eq.get("yETRS89");
			
	
		assertTrue(Double.toString(coordinateX).contains("441752.4924")&&Double.toString(coordinateY).contains("4472638.7964"));
	}
	
	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","EQPW0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("latitud")!=null)&&(obj.get("longitud")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","EQPW0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"437829","4473131","1000"};
		JSONArray records = TestUtils.extractRecords(PuntoWifiController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 11);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"437829","4473131","1000","id"};
		JSONArray records = TestUtils.extractRecords(PuntoWifiController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 11);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"437829","4473131","1000","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(PuntoWifiController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 11);	
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
	public void test_Busqueda_openingHours() throws Exception
	{
		

		String [] paramField= {"openingHours","tipoEquipamiento"};
		
		String [] value = {"*Horario de verano: *",PuntoWifiConstants.TIPO_EQUIPAMIENTO_TEST[0]};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		String paramField="field";
		
		String value = "telephone";

		long total = TestUtils.extractTotalDistinct(PuntoWifiController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 168);
	}	
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		String paramField="portalId";
		
		String value = "PORTAL000098";

		long total = TestUtils.extractTotalDistinct(listURL, paramField, value, mockMvc);

		assertTrue(total > 10);
	}
	
	}
