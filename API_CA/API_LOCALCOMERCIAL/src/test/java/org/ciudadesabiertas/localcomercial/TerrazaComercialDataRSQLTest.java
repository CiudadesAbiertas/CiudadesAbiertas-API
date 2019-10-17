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

package org.ciudadesabiertas.localcomercial;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.TerrazaLocalComercialController;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.runners.MethodSorters;


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
public class TerrazaComercialDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;
	
	private String listURL=TerrazaLocalComercialController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}	
	@Test
	public void test_Busqueda_id() throws Exception
	{
		String value = "id=='4856'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		String value = "description=='Otros'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 21);
	}
	
	@Test
	public void test_Busqueda_opening_hours_comodin() throws Exception	
	{
		
		String  value = "openingHours=='*hasta 23:00:00*'";	
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 5);

	}
	
	@Test
	public void test_Busqueda_superficie() throws Exception	
	{
	
		String value= "superficie=='66.66'";
						
		String paramField = "q";
		
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_periodo_funcionamiento() throws Exception	
	{
		String value= "periodoFuncionamiento=='Estacional'";

		String paramField = "q";
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 83);
	}
	
	@Test
	public void test_Busqueda_numero_mesas_autorizadas() throws Exception	
	{
		String value= "numeroMesasAutorizadas==14";

		String paramField = "q";
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5);
		
	}
	
	@Test
	public void test_Busqueda_numero_sillas_autorizadas() throws Exception	
	{
		
		String value= "numeroSillasAutorizadas==81";

		String paramField = "q";
						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
		
	}
	
	
	

}
