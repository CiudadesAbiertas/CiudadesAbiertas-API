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
public class BicicletaPublicaEstacionRSQLTest
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
		
		String value = "id=='EST01'";

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_title() throws Exception
	{
	
		
		String value = "title=='Estación calle atocha'";

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}	
	
	
	@Test
	public void test_Busqueda_tipoEquipamiento() throws Exception
	{		
		
		String value = "tipoEquipamiento=='estacion-de-bicicletas'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{		
		
		String value = "streetAddress=='calle atocha'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_portalId() throws Exception
	{		
		
		String value = "portalId=='PORTAL000101'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{		
		
		String value = "postalCode=='28003'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_estadoEstacion() throws Exception
	{		
		
		String value = "estadoEstacion=='operativa'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaAlta() throws Exception
	{		
		
		String value = "fechaAlta=='2020-01-01'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaBaja() throws Exception
	{		
		
		String value = "fechaBaja==null";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_observesId() throws Exception
	{		
		
		String value = "observesId=='anclajesLibres'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_observesTitle() throws Exception
	{		
		
		String value = "observesTitle=='Anclajes libres'";

		String paramField = "q";
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_RSQL_latitud() throws Exception
	{
		String paramField = "q";
		String value = "xETRS89==440124.33000";		
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);		
		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_RSQL_longitud() throws Exception
	{
		String paramField = "q";
		String value = "yETRS89==4474637.17000";	
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);		
		assertTrue(total == 1);
	}
}
