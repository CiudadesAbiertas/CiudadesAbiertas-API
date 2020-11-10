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

package org.ciudadesabiertas.dataset;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.OrganigramaController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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
public class OrganigramaDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL=OrganigramaController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	
	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="q";		
		String value = "id=='1'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField = "q";
		String value = "title=='AREA DE ALCALDIA'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_nivelJerarquico() throws Exception
	{
		String paramField = "q";
		String value = "nivelJerarquico==2";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 9);
	}
	
	@Test
	public void test_Busqueda_unitOf() throws Exception
	{
		String paramField = "q";
		String value = "unitOf=='1'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 8);
	}
	
	@Test
	public void test_Busqueda_unidadRaiz() throws Exception
	{
		String paramField = "q";
		String value = "unidadRaiz=='1' and id=='23344'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_headOfName() throws Exception
	{
		String paramField = "q";
		String value = "headOfName=='SANTISTEVE ROCHE, PEDRO'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_foundingDate() throws Exception
	{
		String paramField = "q";
		String value = "foundingDate==2018-03-21T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_dissolutionDate() throws Exception
	{
		String paramField = "q";
		String value = "dissolutionDate==2014-08-20T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_estadoEntidadId() throws Exception
	{
		String paramField = "q";
		String value = "estadoEntidadId=='A'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_estadoEntidadTitle() throws Exception
	{
		String paramField = "q";
		String value = "estadoEntidadTitle=='Anulado'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionId() throws Exception
	{
		String paramField = "q";
		String value = "nivelAdministracionId=='3'  and tipoEntidadId=='EE'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionTitle() throws Exception
	{
		String paramField = "q";
		String value = "nivelAdministracionTitle=='Administración local' and tipoEntidadTitle=='Entidad Pública Empresarial'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_tipoEntidadId() throws Exception
	{
		String paramField = "q";
		String value = "tipoEntidadId=='EE'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_tipoEntidadTitle() throws Exception
	{
		String paramField = "q";
		String value = "tipoEntidadTitle=='Entidad Pública Empresarial'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_RSQL_x() throws Exception
	{
		String paramField = "q";
		String value = "xETRS89==675950.94205";		
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);		
		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_RSQL_y() throws Exception
	{
		String paramField = "q";
		String value = "yETRS89==4611562.71288";		
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);		
		assertTrue(total == 4);
	}

	@Test
	public void test_Busqueda_faxNumber() throws Exception
	{
		String paramField = "q";
		String value = "faxNumber=='976201985'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_email() throws Exception
	{
		String paramField = "q";
		String value = "email=='ingresos-urbanisticos@zaragoza.es'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{
		String paramField = "q";
		String value = "streetAddress=='VIA HISPANIDAD 20'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 100);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{
		String paramField = "q";
		String value = "postalCode=='50071'";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 118);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String paramField = "q";
		String value = "municipioId=='50297' and id=='23344'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{
		String paramField = "q";
		String value = "municipioTitle=='Zaragoza' and id=='23344'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		String paramField = "q";
		String value = "url=='http://www.zaragoza.es/sede/portal/organizacion/corporacion/luisa-broto'";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 990);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{
		String paramField = "q";
		String value = "image=='https://transparencia.castillalamancha.es/sites/transparencia2.castillalamancha.es/files/cargos/jose_luis_martinez_guijarro.jpg'";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 990);
	}
	
	@Test
	public void test_Busqueda_purpose() throws Exception
	{
		String paramField = "q";
		String value = "purpose=='Le corresponde la organización de la actividad municipal*'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}
	
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		
		String paramField = "q";

		String value = "portalId=='PORTAL000098'";			

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total > 10);
	}
	
	
	

}
