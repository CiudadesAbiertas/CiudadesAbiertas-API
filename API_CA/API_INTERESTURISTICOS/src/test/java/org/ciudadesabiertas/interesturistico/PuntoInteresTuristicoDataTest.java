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

package org.ciudadesabiertas.interesturistico;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.PuntoInteresTuristicoController;
import org.ciudadesabiertas.dataset.utils.PuntoInteresTuristicoConstants;
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
public class PuntoInteresTuristicoDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private String listURL= PuntoInteresTuristicoController.LIST;

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
		
		String value = "PIT0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_title() throws Exception
	{
		
		String [] paramField= {"title",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Histohuellas",PuntoInteresTuristicoConstants.CATEGORY};		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String [] paramField= {"description",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*grupo de licenciados en Historia e Historia del Arte*",PuntoInteresTuristicoConstants.CATEGORY};	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_nota_historica() throws Exception
	{	
	
		String [] paramField= {"notaHistorica",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*grupo de licenciados en Historia e Historia del Arte*",PuntoInteresTuristicoConstants.CATEGORY};	


		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_accesible() throws Exception
	{
		
		String [] paramField= {"accesible",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"true",PuntoInteresTuristicoConstants.CATEGORY};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}
	
	@Test
	public void test_Busqueda_tipo_accesibilidad() throws Exception
	{
	
		
		String [] paramField= {"tipoAccesibilidad",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Variada",PuntoInteresTuristicoConstants.CATEGORY};			

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28079","*PIT001*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_address() throws Exception
	{
		
		
		String [] paramField= {"streetAddress",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*Mayor*",PuntoInteresTuristicoConstants.CATEGORY};		
				
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 2);
			
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{
			
		String [] paramField= {"barrio",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"CUATRO CAMINOS",PuntoInteresTuristicoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_distrito() throws Exception
	{			
		
		String [] paramField= {"distrito",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"TETUAN",PuntoInteresTuristicoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	
	@Test
	public void test_Busqueda_postal_code() throws Exception
	{			
		
		String [] paramField= {"postalCode",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"28012",PuntoInteresTuristicoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total==3);
	}
	
	@Test
	public void test_Busqueda_XETRS89() throws Exception
	{		
		
		String [] paramField= {"xETRS89",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"440291.26773",PuntoInteresTuristicoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_YETRS89() throws Exception
	{			
		
		String [] paramField= {"yETRS89",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"4474254.64478",PuntoInteresTuristicoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);		

		assertTrue(records.size() == 2);
	}
	
	
	
	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","PIT0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("latitud")!=null)&&(obj.get("longitud")!=null));
		
		assertTrue(checkXY == true);
	}
	
	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception
	{

		String [] paramField= {"srId","id"};

		String [] value = {"EPSG:23030","PIT0004"};
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		
		JSONObject obj=(JSONObject) records.get(0);

		boolean checkXY=((obj.get("xETRS89")!=null)&&(obj.get("yETRS89")!=null));
		
		assertTrue(checkXY == true);
	}
	
	
	@Test
	public void test_Busqueda_email() throws Exception
	{
		
		String  paramField= "email";

		String  value = "info@histohuellas.es";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{
		
		String  paramField= "telephone";

		String  value = "(+34) 665 63 17 74";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		
		String [] paramField= {"url",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"http://www.esmadrid.com/informacion-turistica/histohuellas",PuntoInteresTuristicoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{
		
		String [] paramField= {"image",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"http://www.esmadrid.com/sites/default/files/recursosturisticos/infoturistica/histohuellas_5.jpg",PuntoInteresTuristicoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_siglo() throws Exception
	{

		String [] paramField= {"siglo",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"XII",PuntoInteresTuristicoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_estiloArtistico() throws Exception
	{
				
		String [] paramField= {"estiloArtistico",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Romanico",PuntoInteresTuristicoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_costeMantenimiento() throws Exception
	{		
		
		String [] paramField= {"costeMantenimiento",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"19800.98",PuntoInteresTuristicoConstants.CATEGORY};


		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_ingresosObtenidos() throws Exception
	{
				
		String [] paramField= {"ingresosObtenidos",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"126980.02",PuntoInteresTuristicoConstants.CATEGORY};


		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_afluenciaPublico() throws Exception
	{			
		
		String [] paramField= {"afluenciaPublico",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Jovenes y Adultos",PuntoInteresTuristicoConstants.CATEGORY};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5);
	}
	
	@Test
	public void test_Busqueda_fechaDeclaracionBien() throws Exception
	{
	
		
		String [] paramField= {"fechaDeclaracionBien",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"2019-03-25T00:00:00",PuntoInteresTuristicoConstants.CATEGORY};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_modoAcceso() throws Exception
	{
				
		String [] paramField= {"modoAcceso",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Ascensores y Escaleras Mecánicas",PuntoInteresTuristicoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_medioTransporte() throws Exception
	{
		
		String [] paramField= {"medioTransporte",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"Autobuses, cernanias y Metro",PuntoInteresTuristicoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_openingHours() throws Exception
	{		
		
		String [] paramField= {"openingHours",PuntoInteresTuristicoConstants.CATEGORY_FIELD};

		//CATEGORY
		String [] value = {"*Domingo: 19:15*",PuntoInteresTuristicoConstants.CATEGORY};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"440291","4474254","50"};
		JSONArray records = TestUtils.extractRecords(PuntoInteresTuristicoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"440291","4474254","50","id"};
		JSONArray records = TestUtils.extractRecords(PuntoInteresTuristicoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"440291","4474254","50","id,title,distance"};
		JSONArray records = TestUtils.extractRecords(PuntoInteresTuristicoController.GEO_LIST, paramField, value, mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String paramField="id";
		
		String value = "PIT0004";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "telephone";

		long total = TestUtils.extractTotal(PuntoInteresTuristicoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 25);
	}	
	
	
	
	

}
