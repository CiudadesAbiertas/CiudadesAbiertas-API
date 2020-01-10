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

package org.ciudadesabiertas.monumento;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.MonumentoController;
import org.ciudadesabiertas.dataset.utils.MonumentoConstants;
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
public class MonumentoDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private String listURL= MonumentoController.LIST;

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
		
		String value = "PITM0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_title() throws Exception
	{
		
		String [] paramField= {"title",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Banco de España",MonumentoConstants.CATEGORY};		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String [] paramField= {"description",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*Fundada por Felipe V en 1712 como Biblioteca Pública de Palacio*",MonumentoConstants.CATEGORY};	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_nota_historica() throws Exception
	{	
	
		String [] paramField= {"notaHistorica",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*Fundada por Felipe V en 1712 como Biblioteca Pública de Palacio*",MonumentoConstants.CATEGORY};	


		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_accesible() throws Exception
	{
		
		String [] paramField= {"accesible",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"true",MonumentoConstants.CATEGORY};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 18);
	}
	
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*PITM001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_address() throws Exception
	{
		
		
		String [] paramField= {"streetAddress",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"PASEO RECOLETOS V 20",MonumentoConstants.CATEGORY};		
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
			
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{
			
		String [] paramField= {"barrioId",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"280796062",MonumentoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 86);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{			
		
		String [] paramField= {"distritoId",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"28079606",MonumentoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 86);
	}
	
	
	@Test
	public void test_Busqueda_postal_code() throws Exception
	{			
		
		String [] paramField= {"postalCode",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"28001",MonumentoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==2);
	}
	
	@Test
	public void test_Busqueda_XETRS89() throws Exception
	{		
		
		String [] paramField= {"xETRS89",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"441054.00088",MonumentoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_YETRS89() throws Exception
	{			
		
		String [] paramField= {"yETRS89",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"4474477.11792",MonumentoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","PITM0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","PITM0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("latitud")!=null)&&(obj.get("longitud")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_email() throws Exception
	{
		
		String  paramField= "email";

		String  value = "infobolsamadrid@grupobme.es";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{
		
		String  paramField= "telephone";

		String  value = "*917 095 000 / 915 891 184*";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		
		String [] paramField= {"url",MonumentoConstants.CATEGORY_FIELD};
		
		String url = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=0a9d81eb5f51c010VgnVCM2000000c205a0aRCRD";
		String encodeValue = Util.encodeURL(url);
		//CATEGORY
		String [] value = {encodeValue,MonumentoConstants.CATEGORY};
		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{
		
		String [] paramField= {"image",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"https://www.madridiario.es/fotos/1/145352_unnamed_1_.jpg",MonumentoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
		
	
	@Test
	public void test_Busqueda_medioTransporte() throws Exception
	{
		
		String [] paramField= {"medioTransporte",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*Metro: Banco de España (línea 2)*",MonumentoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 11);
	}
	
	@Test
	public void test_Busqueda_openingHours() throws Exception
	{		
		
		String [] paramField= {"openingHours",MonumentoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*De lunes a viernes: 8.30 a 14.00 horas.*",MonumentoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"441054","4474477","1000"};
		JSONArray records = TestUtils.extractRecords(MonumentoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 42);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"441054","4474477","1000","id"};
		JSONArray records = TestUtils.extractRecords(MonumentoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 42);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"441054","4474477","1000","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(MonumentoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 42);	
	}
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String paramField="id";
		
		String value = "PITM0002";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "telephone";

		long total = TestUtils.extractTotalDistinct(MonumentoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 68);
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
