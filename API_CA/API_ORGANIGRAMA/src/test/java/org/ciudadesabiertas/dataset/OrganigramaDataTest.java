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
public class OrganigramaDataTest
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
		String paramField="id";		
		String value = "1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Id_KO() throws Exception
	{
		String paramField="id";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}

	@Test
	public void test_Busqueda_Title() throws Exception
	{
		String paramField="title";
		String value = "AREA DE ALCALDIA*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Title_KO() throws Exception
	{
		String paramField="title";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_nivelJerarquico() throws Exception
	{
		String paramField="nivelJerarquico";
		String value = "2";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 9);
	}
	
	@Test
	public void test_Busqueda_nivelJerarquico_KO() throws Exception
	{
		String paramField="nivelJerarquico";
		String value = "99";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
		
	@Test
	public void test_Busqueda_unitOf() throws Exception
	{
		String paramField= "unitOf";
		String  value = "1";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 8);		
	}
	
	@Test
	public void test_Busqueda_unitOf_KO() throws Exception
	{
		String paramField="unitOf";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_unidadRaiz() throws Exception
	{

		String [] paramField= {"unidadRaiz","id"};
		String [] value = {"1","23344"};
		
		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_unidadRaiz_KO() throws Exception
	{
		String paramField="unidadRaiz";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_headOfName() throws Exception
	{
		String paramField= "headOfName";
		String  value = "SANTISTEVE ROCHE, PEDRO";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_headOfName_KO() throws Exception
	{
		String paramField="headOfName";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_foundingDate() throws Exception
	{
		String paramField= "foundingDate";
		String  value = "2018-03-21T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_foundingDate_KO() throws Exception
	{
		String paramField="foundingDate";
		String value = "3999-03-21T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_dissolutionDate() throws Exception
	{
		String paramField= "dissolutionDate";
		String  value = "2014-08-20T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_dissolutionDate_KO() throws Exception
	{
		String paramField="dissolutionDate";
		String value = "3999-03-21T00:00:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_estadoEntidadId() throws Exception
	{
		String paramField= "estadoEntidadId";
		String  value = "A";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 1);		
	}
	
	@Test
	public void test_Busqueda_estadoEntidadId_KO() throws Exception
	{
		String paramField="estadoEntidadId";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_estadoEntidadTitle() throws Exception
	{
		String paramField= "estadoEntidadTitle";
		String  value = "Anulado";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 1);	
	}
	
	@Test
	public void test_Busqueda_estadoEntidadTitle_KO() throws Exception
	{
		String paramField="estadoEntidadTitle";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionId() throws Exception
	{
		String [] paramField= {"nivelAdministracionId","tipoEntidadId"};
		String [] value = {"3","EE"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionId_KO() throws Exception
	{
		String paramField="nivelAdministracionId";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionTitle() throws Exception
	{
		String [] paramField= {"nivelAdministracionTitle","tipoEntidadTitle"};
		String [] value = {"Administración local","Entidad Pública Empresarial"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);	
	}
	
	@Test
	public void test_Busqueda_nivelAdministracionTitle_KO() throws Exception
	{
		String paramField="nivelAdministracionTitle";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_tipoEntidadId() throws Exception
	{
		String paramField= "tipoEntidadId";
		String  value = "EE";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 3);		
	}
	
	@Test
	public void test_Busqueda_tipoEntidadId_KO() throws Exception
	{
		String paramField="tipoEntidadId";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_tipoEntidadTitle() throws Exception
	{
		String paramField= "tipoEntidadTitle";
		String  value = "Entidad Pública Empresarial";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 3);	
	}
	
	@Test
	public void test_Busqueda_tipoEntidadTitle_KO() throws Exception
	{
		String paramField="tipoEntidadTitle";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_municipio_x() throws Exception
	{
		String value = "675950.94205";
		String paramField="xETRS89";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_x_KO() throws Exception
	{
		String paramField="xETRS89";
		String value = "676840.38123";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_municipio_y() throws Exception
	{
		String value = "4611562.71288";
		String paramField="yETRS89";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 4);
	}
	
	@Test
	public void test_Busqueda_y_KO() throws Exception
	{
		String paramField="yETRS89";
		String value = "461396";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_faxNumber() throws Exception
	{
		String paramField= "faxNumber";
		String  value = "976201985";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 7);	
	}
	
	@Test
	public void test_Busqueda_faxNumber_KO() throws Exception
	{
		String paramField="faxNumber";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_email() throws Exception
	{
		String paramField= "email";
		String  value = "ingresos-urbanisticos@zaragoza.es";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 3);	
	}
	
	@Test
	public void test_Busqueda_email_KO() throws Exception
	{
		String paramField="email";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{
		String paramField= "streetAddress";
		String  value = "VIA HISPANIDAD 20";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 100);	
	}
	
	@Test
	public void test_Busqueda_streetAddress_KO() throws Exception
	{
		String paramField="streetAddress";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{
		String paramField= "postalCode";
		String  value = "50071";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 118);	
	}
	
	@Test
	public void test_Busqueda_postalCode_KO() throws Exception
	{
		String paramField="postalCode";
		String value = "99999*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};
		String [] value = {"50297","23344"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
			
	}
	
	@Test
	public void test_Busqueda_municipioId_KO() throws Exception
	{
		String paramField="municipioId";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};
		String [] value = {"Zaragoza","23344"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_municipioTitle_KO() throws Exception
	{
		String paramField="municipioTitle";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{		
		
		String paramField= "url";
		String value = "http://www.zaragoza.es/sede/portal/organizacion/corporacion/luisa-broto";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 990);
	}
	
	@Test
	public void test_Busqueda_url_KO() throws Exception
	{
		String paramField="url";
		String value = "https://www.localidata.com";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_image() throws Exception
	{		
		
		String paramField= "image";
		String value = "https://transparencia.castillalamancha.es/sites/transparencia2.castillalamancha.es/files/cargos/jose_luis_martinez_guijarro.jpg";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 990);
	}
	
	@Test
	public void test_Busqueda_image_KO() throws Exception
	{
		String paramField="image";
		String value = "https://www.localidata.com";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_purpose() throws Exception
	{		
		
		String paramField= "purpose";
		String  value = "Le corresponde la organización de la actividad municipal*";
		JSONArray records = TestUtils.extractRecords(listURL,paramField, value.toUpperCase(),mockMvc);
		assertTrue(records.size() == 2);	
	}
	
	@Test
	public void test_Busqueda_purpose_KO() throws Exception
	{
		String paramField="purpose";
		String value = "xxxxxxxxxxxxxxxx*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_geo() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters"};
		String [] value = {"677755","4611562","5"};
		long total = TestUtils.extractTotal(OrganigramaController.GEO_LIST,paramField, value, mockMvc);
		assertTrue(total == 4);	
	}
	
	@Test
	public void test_Busqueda_geo_sort() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","sort"};
		String [] value = {"677755","4611562","5","id"};
		long total = TestUtils.extractTotal(OrganigramaController.GEO_LIST,paramField, value, mockMvc);
		assertTrue(total == 4);	
	}
	
	@Test
	public void test_Busqueda_geo_fields() throws Exception
	{
		String [] paramField= {"xETRS89","yETRS89","meters","fields"};
		String [] value = {"677755","4611562","5","id,title,distance"};
		long total = TestUtils.extractTotal(OrganigramaController.GEO_LIST,paramField, value, mockMvc);
		assertTrue(total == 4);	
	}
	
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "headOfName";

		long total = TestUtils.extractTotalDistinct(OrganigramaController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 76);
	}
	

	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		String paramField="portalId";
		
		String value = "PORTAL000098";

		long total = TestUtils.extractTotalDistinct(listURL, paramField, value, mockMvc);

		assertTrue(total > 10);
	}
	
	

}
