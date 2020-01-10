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
import org.ciudadesabiertas.dataset.controller.SeccionCensalController;
import org.ciudadesabiertas.utils.TestUtils;
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
public class SeccionCensalDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL=SeccionCensalController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_id() throws Exception
	{

		String paramField = "q";

		String value = "id==2800601001";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_title() throws Exception
	{

		String paramField = "q";

		String value = "title=='Sección Censal 1'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_pais() throws Exception
	{

		//String value = "paisObject.identifier==España";
		String value = "pais==España";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 62);
	}
	
	@Test
	public void test_Busqueda_RSQL_autonomia() throws Exception
	{

		//String value = "paisObject.identifier==España";
		String value = "autonomia=='Comunidad-Madrid'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 62);
	}
	
	@Test
	public void test_Busqueda_RSQL_provincia() throws Exception
	{

		//String value = "paisObject.identifier==España";
		String value = "provincia=='Madrid'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 62);
	}
	
	@Test
	public void test_Busqueda_RSQL_municipio() throws Exception
	{

		//String value = "paisObject.identifier==España";
		String value = "municipioId=='28006'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 62);
	}
	
	@Test
	public void test_Busqueda_RSQL_distrito() throws Exception
	{

		//String value = "paisObject.identifier==España";
		String value = "distritoId=='2800601'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 62);
	}
	
	
	
	
	

	

}
