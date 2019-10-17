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
import org.ciudadesabiertas.dataset.controller.AvisoQuejaSugController;
import org.ciudadesabiertas.utils.TestUtils;
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
public class AvisoQuejaSugDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String  listURL = AvisoQuejaSugController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "AQSA0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_serviceRequestName() throws Exception
	{
		String paramField="serviceRequestName";

		String value = "AVISO-R RECOGIDA ANIMALES MUERTOS*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(),mockMvc);

		assertTrue(records.size() == 27);
	}
	
	
	@Test
	public void test_Busqueda_serviceRequestId() throws Exception
	{
		String paramField="serviceRequestId";

		String value = "0-52";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 27);
	}
	
	@Test
	public void test_Busqueda_status() throws Exception
	{
		String paramField="stat";

		String value = "abierto";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1000);
	}
	
	@Test
	public void test_Busqueda_statusNotes() throws Exception
	{
		String [] paramField= {"statusNotes","id"};

		String [] value = {"abierto","*AQSA001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_openDate() throws Exception
	{		
		String paramField="openDate";

		String value = "2018-01-01T00:59:30";
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_closeDate() throws Exception
	{		
		String paramField="closeDate";

		String value = "2018-12-27T12:07:39";
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*AQSA001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	@Test
	public void test_Busqueda_municipioTitle() throws Exception
	{
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Madrid","*AQSA001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	@Test
	public void test_Busqueda_details() throws Exception
	{
		String paramField="details";

		String value = "*AVISO-R CUBO/CONT (TODO TIPO) VACIADO E-VACIADO CUBO/CONTENEDOR NARANJA ORG*";
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 24);
			
	}
	
	@Test
	public void test_Busqueda_source() throws Exception
	{
			
		String paramField="source";
		
		String value = "MOVIL";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==170);
	}
	
	
	@Test
	public void test_Busqueda_address() throws Exception
	{
		String paramField="address";

		String value = "*CALLE ILLESCAS 82*";
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_postal_code() throws Exception
	{
			
		String paramField="postalCode";
		
		String value = "28019";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==144);
	}
	
	@Test
	public void test_Busqueda_x() throws Exception
	{
			
		String paramField="xETRS89";
		
		String value = "439908.40000";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_y() throws Exception
	{
			
		String paramField="yETRS89";
		
		String value = "4466975.57";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_typeCode() throws Exception
	{
			
		String paramField="typeCode";
		
		String value = "3438";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==127);
	}
	
	@Test
	public void test_Busqueda_typeName() throws Exception
	{
			
		String paramField="typeName";
		
		String value = "*LIMPIEZA VIAS PUBLICAS Y ZONAS VERDES*";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==128);
	}
	
	@Test
	public void test_Busqueda_id_getX_getY() throws Exception
	{

		String [] paramField= {"id"};

		String [] value = {"AQSA0001"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"439908","4466975","50"};
		JSONArray records = TestUtils.extractRecords(AvisoQuejaSugController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"439908","4466975","50","id"};
		JSONArray records = TestUtils.extractRecords(AvisoQuejaSugController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"439908","4466975","50","id,serviceRequestName,source,distance"};
		JSONArray records = TestUtils.extractRecords(AvisoQuejaSugController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String paramField="id";
		
		String value = "AQSA0001";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "address";

		long total = TestUtils.extractTotalDistinct(AvisoQuejaSugController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 1755);
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{

		String value = "JUSTICIA";
		
		String paramField="barrio";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 18);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{
		
		String  paramField= "distrito";

		String  value = "CENTRO";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 100);
	}
	
	
}
