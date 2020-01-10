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
import org.ciudadesabiertas.dataset.controller.LicenciaController;
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
public class LicenciaDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;


	private String listURL=LicenciaController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}	
	@Test
	public void test_Busqueda_id() throws Exception
	{
		String value = "id=='60000068-106-1993-02762'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_identifier() throws Exception
	{
		String value = "identifier=='60000068/106-1993-02762'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_tipo_asociadaA() throws Exception
	{
		String value = "asociadaA=='60000068'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_autorizaActividadEconomica() throws Exception
	{
		String value = "autorizaActividadEconomica==52";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 5);
	}
	@Test
	public void test_estadoTramitacion() throws Exception
	{
		String value = "estadoTramitacion=='concedida'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 603);
	}
	@Test
	public void test_fechaAlta() throws Exception
	{
		String value = "fechaAlta==1900-01-01T00:00:00 and estadoTramitacion=='concedida'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 57);
	}
	@Test
	public void test_fechaCese() throws Exception
	{
		String value = "fechaCese==2040-02-16T00:00:00";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 613);
	}
	@Test
	public void test_fechaSolicitud() throws Exception
	{
		String value = "fechaSolicitud==2010-02-16T00:00:00 and estadoTramitacion=='en tramite'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 10);
	}
	@Test
	public void test_seOtorgaA() throws Exception
	{
		String value = "seOtorgaA=='Mario Gomez Gomez' and estadoTramitacion=='en tramite'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 10);
	}
	


	
	
	
	
	

}
