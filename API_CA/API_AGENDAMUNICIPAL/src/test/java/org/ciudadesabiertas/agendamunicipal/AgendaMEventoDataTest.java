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

package org.ciudadesabiertas.agendamunicipal;


import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AgendaMEventoController;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
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
public class AgendaMEventoDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=AgendaMEventoController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "AGM0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";

		String value = "Acto de entrega*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
	@Test
	public void test_Busqueda_descripcion() throws Exception
	{
		String paramField="description";

		String value = "*Acto de entrega*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28006","AGM0001"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Alcobendas","AGM0001"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
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
	public void test_Busqueda_municipioNombre_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{		
		
		String value = "https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default";
		
		value = Util.encodeURL(value);
		
		String paramField="url";		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaInicio() throws Exception
	{

		String value = "2019-12-08T10:00:00"; 
		
		String paramField="startDate";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_fechaFin() throws Exception
	{

		String value = "2019-12-08T20:00:00"; 
		
		String paramField="endDate";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_tipoEvento() throws Exception
	{

		String value = "evento-con-medio-de-comunicacion"; 
		
		String paramField = "tipoEvento";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_tipo_acceso() throws Exception
	{

		String value = "privado"; 
		
		String paramField = "tipoAcceso";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_tipo_sesion() throws Exception
	{

		String value = "extraordinaria"; 
		
		String paramField = "tipoSesion";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_location_title() throws Exception
	{

		String value = "Auditorio del Comité Olímpico Español"; 
		
		String paramField = "locationTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_street_address() throws Exception
	{

		String value = "CL BLAS DE OTERO 4"; 
		
		String paramField = "streetAddress";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String value = "28100"; 
		
		String paramField = "postalCode";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_lugar_portal_id() throws Exception
	{

		String value = "PORTAL000119"; 
		
		String paramField = "portalId";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
			
		String [] paramField= {"barrioId"};

		
		String [] value = {"28006011"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
			
		String [] paramField= {"barrioTitle"};

		
		String [] value = {"Norte"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{			
		
		String [] paramField= {"distritoId"};

		
		String [] value = {"2800601"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
	
	@Test
	public void test_Busqueda_distritoTiTle() throws Exception
	{			
		
		String [] paramField= {"distritoId"};

		
		String [] value = {"2800601"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
	
	@Test
	public void test_Busqueda_equipamientoId() throws Exception
	{			
		
		String [] paramField= {"equipamientoId"};

		
		String [] value = {"EQ0044"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_equipamientoTitle() throws Exception
	{			
		
		String [] paramField= {"equipamientoTitle"};

		
		String [] value = {"Teatro Auditorio Ciudad de Alcobendas"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_super_event_id() throws Exception
	{			
		
		String [] paramField= {"superEventId"};

		
		String [] value = {"AGM0001"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	@Test
	public void test_Busqueda_reunionLobby() throws Exception
	{

		String value = "false"; 
		
		String paramField = "reunionLobby";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	
	@Test
	public void test_Busqueda_canal() throws Exception
	{

		String value = "presencial"; 
		
		String paramField = "canal";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 4);
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
		
		String value = "tipoEvento";

		long total = TestUtils.extractTotalDistinct(AgendaMEventoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	

	

	
	
	
	

}
