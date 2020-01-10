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

package org.ciudadesabiertas.territorio;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.MunicipioController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MunicipioDataTest
{

	@Autowired
	private WebApplicationContext wac;	

	private MockMvc mockMvc;
	
	private String listURL=MunicipioController.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "28006";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Nombre() throws Exception
	{
		String paramField="title";

		String value = "Alcobendas";

		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Provincia() throws Exception
	{
		String paramField="provincia";
		
		String value = "Madrid";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_Autonomia() throws Exception
	{
		String paramField="autonomia";
		
		String value = "Comunidad-Madrid";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_pais() throws Exception
	{
		String paramField="pais";
		
		String value = "España";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	


}
