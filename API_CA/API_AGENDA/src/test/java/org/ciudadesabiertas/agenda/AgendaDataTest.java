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

package org.ciudadesabiertas.agenda;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AgendaController;
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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AgendaDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=AgendaController.LIST;
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "AG0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";

		String value = "Programación*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 3);
	}
	
	
	@Test
	public void test_Busqueda_descripcion() throws Exception
	{
		String paramField="description";

		String value = "*Puede consultar el folleto*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28006","*AG004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Alcobendas","*AG004*"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 10);
	}
	
	
	@Test
	public void test_Busqueda_municipioId_KO() throws Exception
	{
		String paramField="municipioId";

		String value = "29078";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertNull(records);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertNull(records);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{		
		
		String value = "*cm_payoff_1-sht_v6_lg_3.jpg*";
		
		String paramField="image";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_fechaInicio() throws Exception
	{

		String value = "2018-11-28T00:00:00"; 
		
		String paramField="fechaInicio";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_fechaFin() throws Exception
	{

		String value = "2019-05-31T23:59:59"; 
		
		String paramField="fechaFin";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	
	@Test
	public void test_Busqueda_tipoEvento() throws Exception
	{

		String value = "Pintura, Escultura y Fotografía,Otros"; 
		
		String paramField = "tipoEvento";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_tipo_publico() throws Exception
	{

		String value = "Infancia y Adolescencia,Público general"; 
		
		String paramField = "tipoPublico";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 15);
	}
	
	@Test
	public void test_Busqueda_rango_edad() throws Exception
	{

		String value = "Menores de 18"; 
		
		String paramField = "ageRange";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 30);
	}
	
	@Test
	public void test_Busqueda_maximo_asistentes() throws Exception
	{

		String value = "30"; 
		
		String paramField = "maximoParticipantes";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 18);
	}
	
	@Test
	public void test_Busqueda_lugar_celebracion_nombre() throws Exception
	{

		String value = "Centro Cultural Pablo Iglesias"; 
		
		String paramField = "equipamientoTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_lugar_inscripcion_nombre() throws Exception
	{

		String value = "Centro de Arte Alcobendas"; 
		
		String paramField = "lugarInscripcionTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_medios_transporte_nombre() throws Exception
	{

		String value = "Bus 5"; 
		
		String paramField = "medioTransporteTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 32);
	}
	
	@Test
	public void test_Busqueda_servicio_municipal_nombre() throws Exception
	{

		String value = "Servicios sociales"; 
		
		String paramField = "servicioMunicipalTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 13);
	}
	
	@Test
	public void test_Busqueda_accesible() throws Exception
	{

		String value = "false"; 
		
		String paramField = "accesible";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 13);
	}
	
	@Test
	public void test_Busqueda_tipo_accesibilidad() throws Exception
	{

		String value = "Solo auditivo"; 
		
		String paramField = "tipoAccesibilidad";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 13);
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
		
		String value = "tipoPublico";

		long total = TestUtils.extractTotalDistinct(AgendaController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 7);
	}
	

	

	
	
	
	

}
