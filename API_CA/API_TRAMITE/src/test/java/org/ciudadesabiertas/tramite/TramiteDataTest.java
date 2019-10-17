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

package org.ciudadesabiertas.tramite;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.TramiteController;
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
public class TramiteDataTest
{

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String listURL=TramiteController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "TR0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String [] paramField= {"description"};

		
		String [] value = {"*grupos*"};	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	
	@Test
	public void test_Busqueda_permiteTramitacionEnLinea() throws Exception
	{
		
		String [] paramField= {"permiteTramitacionEnLinea"};

		
		String [] value = {"true"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 25);
	}
	
	
	@Test
	public void test_Busqueda_permiteTramitacionPresencial() throws Exception
	{
		
		String [] paramField= {"permiteTramitacionPresencial"};

		
		String [] value = {"true"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 7);
	}

	@Test
	public void test_Busqueda_permiteTramitacionTelefono() throws Exception
	{
		
		String [] paramField= {"permiteTramitacionTelefono"};

		
		String [] value = {"true"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 16);
	}

	
	@Test
	public void test_Busqueda_permiteTramitationCorreoPostal() throws Exception
	{
		
		String [] paramField= {"permiteTramitationCorreoPostal"};

		
		String [] value = {"true"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 10);
	}
	
	@Test
	public void test_Busqueda_title() throws Exception
	{
		
		String [] paramField= {"title"};

		
		String [] value = {"Museos*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}


	@Test
	public void test_Busqueda_url() throws Exception
	{
		
		String [] paramField= {"url"};

		
		String [] value = {"http://www.zaragoza.es*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	
	@Test
	public void test_Busqueda_impreso() throws Exception
	{
		
		String [] paramField= {"impreso"};

		
		String [] value = {"https://www.zaragoza.es/sedeservicio/tramite/impreso-instancia/36*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_detalleTramitacionTelefono() throws Exception
	{
		
		String [] paramField= {"detalleTramitacionTelefono"};

		
		String [] value = {"*datos*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	
	
	@Test
	public void test_Busqueda_detalleTramitacionPresencial() throws Exception
	{
		
		String [] paramField= {"detalleTramitacionPresencial"};

		
		String [] value = {"*documentos*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 8);
	}
	

	@Test
	public void test_Busqueda_detalleTramitacionEnLinea() throws Exception
	{
		
		String [] paramField= {"detalleTramitacionEnLinea"};

		
		String [] value = {"*descarga*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_detalleTramitacionCorreoPostal() throws Exception
	{
		
		String [] paramField= {"detalleTramitacionCorreoPostal"};

		
		String [] value = {"*Escrito*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}	
	
	
	@Test
	public void test_Busqueda_normativa() throws Exception
	{
		
		String [] paramField= {"normativa"};

		
		String [] value = {"*4703*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 16);
	}		
	
	@Test
	public void test_Busqueda_organo() throws Exception
	{
		
		String [] paramField= {"organo"};

		
		String [] value = {"*superior*"};						

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 20);
	}
		
	
	@Test
	public void test_Busqueda_pago() throws Exception
	{		
		String [] paramField= {"pago"};		
		String [] value = {"*Tasa*"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 8);
	}
	
	@Test
	public void test_Busqueda_requisitos() throws Exception
	{		
		String [] paramField= {"requisitos"};		
		String [] value = {"*antecedentes*"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_sameAs() throws Exception
	{		
		String [] paramField= {"sameAs"};		
		String [] value = {"*www.zaragoza.es*"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 50);
	}
	
	
	@Test
	public void test_Busqueda_materia() throws Exception
	{		
		String [] paramField= {"materia"};		
		String [] value = {"Salud"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_fechaPlazoInicio() throws Exception
	{		
		String [] paramField= {"fechaPlazoInicio"};		
		String [] value = {"2019-01-08 23:59:59"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 50);
	}
	
	@Test
	public void test_Busqueda_fechaPlazoFin() throws Exception
	{		
		String [] paramField= {"fechaPlazoFin"};		
		String [] value = {"2019-12-08 23:59:59"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 50);
	}
	
	@Test
	public void test_Busqueda_fechaRespuesta() throws Exception
	{		
		String [] paramField= {"fechaRespuesta"};		
		String [] value = {"2019-06-11 09:00:00"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_fechaPresentacion() throws Exception
	{		
		String [] paramField= {"fechaPresentacion"};		
		String [] value = {"2019-05-04 11:05:00"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_fechaRespuestaTexto() throws Exception
	{		
		String [] paramField= {"fechaRespuestaTexto"};		
		String [] value = {"*mayo*"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_fechaPresentacionTexto() throws Exception
	{		
		String [] paramField= {"fechaPresentacionTexto"};		
		String [] value = {"*abril*"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_efectoSilencioAdministrativo() throws Exception
	{		
		String [] paramField= {"efectoSilencioAdministrativo"};		
		String [] value = {"NEGATIVO"};						
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 4);
	}
		
	
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String paramField="id";
		
		String value = "TRA0002";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);

		assertTrue(md5_content.equals(md5_head));
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "permiteTramitacionPresencial";

		long total = TestUtils.extractTotalDistinct(TramiteController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	
}
