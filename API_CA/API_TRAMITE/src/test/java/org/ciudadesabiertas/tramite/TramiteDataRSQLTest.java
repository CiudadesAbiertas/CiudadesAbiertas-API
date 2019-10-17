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
public class TramiteDataRSQLTest
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
		String paramField="q";		
		String value = "id==TR0001";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		String paramField="q";		
		String value = "description==*grupos*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_permiteTramitacionEnLinea() throws Exception
	{
		String paramField="q";		
		String value = "permiteTramitacionEnLinea==true";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 25);
	}
	
	@Test
	public void test_Busqueda_permiteTramitacionPresencial() throws Exception
	{
		String paramField="q";		
		String value = "permiteTramitacionPresencial==true";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_permiteTramitacionTelefono() throws Exception
	{
		String paramField="q";		
		String value = "permiteTramitacionTelefono==true";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 16);
	}
	
	@Test
	public void test_Busqueda_permiteTramitationCorreoPostal() throws Exception
	{
		String paramField="q";		
		String value = "permiteTramitationCorreoPostal==true";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 10);
	}
	
	@Test
	public void test_Busqueda_title() throws Exception
	{
		String paramField="q";		
		String value = "title==Museos*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		String paramField="q";		
		String value = "url==http://www.zaragoza.es*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_impreso() throws Exception
	{
		String paramField="q";		
		String value = "impreso==https://www.zaragoza.es/sedeservicio/tramite/impreso-instancia/36*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_detalleTramitacionTelefono() throws Exception
	{
		String paramField="q";		
		String value = "detalleTramitacionTelefono==*datos*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_detalleTramitacionPresencial() throws Exception
	{
		String paramField="q";		
		String value = "detalleTramitacionPresencial==*documentos*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 8);
	}
	
	
	@Test
	public void test_Busqueda_detalleTramitacionEnLinea() throws Exception
	{
		String paramField="q";		
		String value = "detalleTramitacionEnLinea==*descarga*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_detalleTramitacionCorreoPostal() throws Exception
	{
		String paramField="q";		
		String value = "detalleTramitacionCorreoPostal==*Escrito*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}	
	

	@Test
	public void test_Busqueda_normativa() throws Exception
	{
		String paramField="q";		
		String value = "normativa==*4703*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 16);
	}	
	
	@Test
	public void test_Busqueda_organo() throws Exception
	{
		String paramField="q";		
		String value = "organo==*superior*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 20);
	}	
	
	@Test
	public void test_Busqueda_pago() throws Exception
	{
		String paramField="q";		
		String value = "pago==*Tasa*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 8);
	}
	
	@Test
	public void test_Busqueda_requisitos() throws Exception
	{
		String paramField="q";		
		String value = "requisitos==*antecedentes*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	@Test
	public void test_Busqueda_sameAs() throws Exception
	{
		String paramField="q";		
		String value = "sameAs==*www.zaragoza.es*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 50);
	}
	
	@Test
	public void test_Busqueda_materia() throws Exception
	{
		String paramField="q";		
		String value = "materia==Salud*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 20);
	}
	
	@Test
	public void test_Busqueda_fechaPlazoInicio() throws Exception
	{
		String paramField="q";		
		String value = "fechaPlazoInicio==2019-01-08T23:59:59";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 50);
	}	
	
	
	@Test
	public void test_Busqueda_fechaPlazoFin() throws Exception
	{
		String paramField="q";		
		String value = "fechaPlazoFin==2019-12-08T23:59:59";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 50);
	}	
	

	@Test
	public void test_Busqueda_fechaRespuesta() throws Exception
	{	
		String paramField="q";	
		String value = "fechaRespuesta==2019-06-11T09:00:00";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
	}
	
	@Test
	public void test_Busqueda_fechaPresentacion() throws Exception
	{	
		String paramField="q";	
		String value = "fechaPresentacion==2019-05-04T11:05:00";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
		
	}
	
	@Test
	public void test_Busqueda_fechaRespuestaTexto() throws Exception
	{
		String paramField="q";	
		String value = "fechaRespuestaTexto==*mayo*";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
		
	}
	
	@Test
	public void test_Busqueda_fechaPresentacionTexto() throws Exception
	{	
		String paramField="q";	
		String value = "fechaPresentacionTexto==*abril*";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 20);
		
	}
	
	@Test
	public void test_Busqueda_efectoSilencioAdministrativo() throws Exception
	{		
		String paramField="q";	
		String value = "efectoSilencioAdministrativo==NEGATIVO";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 4);

	}
	
	
}
