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

package org.ciudadesabiertas.alojamiento;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AlojamientoController;
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
public class AlojamientoDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String listURL=AlojamientoController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "ALJ0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_title() throws Exception
	{
		
		String [] paramField= {"title"};

		
		String [] value = {"*Hotel Madrid Centro Puerta del Sol*"};		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String [] paramField= {"description"};

		
		String [] value = {"*Próximo a la céntrica Puerta del Sol se encuentra este hotel*"};	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_accesible() throws Exception
	{
		
		String [] paramField= {"accesible"};

		
		String [] value = {"true"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 10);
	}
	
	
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*ALJ001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_address() throws Exception
	{
		
		
		String [] paramField= {"streetAddress"};

		
		String [] value = {"Esparteros, 6, 2º"};		
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
			
	}
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
			
		String [] paramField= {"barrioId"};

		
		String [] value = {"280796062"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{			
		
		String [] paramField= {"distritoId"};

		
		String [] value = {"28079606"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 10);
	}
	
	
	@Test
	public void test_Busqueda_postal_code() throws Exception
	{			
		
		String [] paramField= {"postalCode"};

		
		String [] value = {"28001"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==23);
	}
	
	@Test
	public void test_Busqueda_XETRS89() throws Exception
	{		
		
		String [] paramField= {"xETRS89"};

		
		String [] value = {"440396.51166"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_yETRS89() throws Exception
	{			
		
		String [] paramField= {"yETRS89"};

		
		String [] value = {"4474341.38044"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","ALJ0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","ALJ0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("latitud")!=null)&&(obj.get("longitud")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_email() throws Exception
	{
		
		String  paramField= "email";

		String  value = "hotel.puertadelsol@hotelbb.com";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{
		
		String  paramField= "telephone";

		String  value = "*+34) 914 890 591*";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		
		String [] paramField= {"url"};
		
		String url = "http://www.esmadrid.com/alojamientos/bb-hotel-madrid-centro-puerta-sol";
		String encodeValue = Util.encodeURL(url);
		
		String [] value = {encodeValue};
		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{
		
		String [] paramField= {"image"};

		
		String [] value = {"http://www.esmadrid.com/sites/default/files/recursosturisticos/alojamientos/bb_hotel_madrid_centro_puerta_del_sol.jpg"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numCamas() throws Exception
	{		
		
		String [] paramField= {"numCamas"};

		
		String [] value = {"190"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_numHabitaciones() throws Exception
	{		
		
		String [] paramField= {"numHabitaciones"};

		
		String [] value = {"100"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_categoria() throws Exception
	{		
		
		String [] paramField= {"categoria"};

		
		String [] value = {"*Hoteles 3 estrellas*"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 75);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440396.51166","4474341.38044","100"};
		JSONArray records = TestUtils.extractRecords(AlojamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440396.51166","4474341.38044","100","id"};
		JSONArray records = TestUtils.extractRecords(AlojamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440396.51166","4474341.38044","100","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(AlojamientoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 5);	
	}
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String paramField="id";
		
		String value = "ALJ0002";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "streetAddress";

		long total = TestUtils.extractTotalDistinct(AlojamientoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 626);
	}
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		String paramField="portalId";
		
		String value = "PORTAL000098";

		long total = TestUtils.extractTotalDistinct(AlojamientoController.LIST, paramField, value, mockMvc);

		assertTrue(total > 10);
	}
	
	
}
