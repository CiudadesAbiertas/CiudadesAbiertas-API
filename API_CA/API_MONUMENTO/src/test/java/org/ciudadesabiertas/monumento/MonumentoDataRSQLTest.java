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

package org.ciudadesabiertas.monumento;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.MonumentoController;
import org.ciudadesabiertas.dataset.utils.MonumentoConstants;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class MonumentoDataRSQLTest
{

	@Autowired
	private WebApplicationContext wac;

	private String listURL= MonumentoController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField = "q";

		String value = "id==PITM0001";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	// Genera un error muy extraño
	@Test
	public void test_Busqueda_title() throws Exception
	{
		String paramField = "q";

		String value = "title=='Banco de España'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_category_title() throws Exception
	{
		String paramField = "q";

		String value = "category=='" + MonumentoConstants.CATEGORY + "' and title=='Banco de España*'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_description() throws Exception
	{
		String paramField = "q";

		String value = "description=='*Fundada por Felipe V en 1712 como Biblioteca Pública de Palacio*'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_municipioId_municipioNombre_accesible() throws Exception
	{
		// Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and accesible==true";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 18);
	}

	@Test
	public void test_Busqueda_address_barrio_distrito() throws Exception
	{
		String paramField = "q";

		String value = "streetAddress=='*CALLE ALCALA V 48*' and barrioId=='280796062' and distritoId=='28079606'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_postal_code() throws Exception
	{

		String paramField = "q";

		String value = "postalCode=='28001'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}

	@Test
	public void test_Busqueda_XETRS89() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89==441395.00080";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_YETRS89() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89==4474937.11793";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_RSQL_XETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>440054 and xETRS89<442000";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 48);

	}

	@Test
	public void test_Busqueda_RSQL_YETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4473000 and yETRS89<4473500";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 7);

	}

	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception
	{

		String[] paramField = { "srId", "id" };

		String[] value = { "EPSG:25830", "PITM0004" };

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		JSONObject obj = (JSONObject) records.get(0);

		boolean checkXY = ((obj.get("xETRS89") != null) && (obj.get("yETRS89") != null));

		assertTrue(checkXY == true);
	}

	@Test
	public void test_Busqueda_email() throws Exception
	{

		String paramField = "q";

		String value = "email=='infobolsamadrid@grupobme.es'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_url() throws Exception
	{

		String url = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=0a9d81eb5f51c010VgnVCM2000000c205a0aRCRD";
		String encodeValue = Util.encodeURL(url);
		String value = "url=='" + encodeValue + "'";

		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		
		String paramField = "q";

		String value = "portalId=='PORTAL000098'";	
		// Cargamos los tipos asociados a category
		value += " and " + loadCategory();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total > 10);
	}
	
	
	private String loadCategory()
	{

		// Cargamos los tipos asociados a category
		String value = "";

		String aux = "category=in=(";
		int contador = 1;
		for (String obj : MonumentoConstants.CATEGORY_TEST)
		{
			if (contador < MonumentoConstants.CATEGORY_TEST.length)
			{
				aux += "'" + obj + "',";
			} else
			{
				aux += "'" + obj + "'";
			}
			contador++;
		}
		aux += ")";

		value += aux;
		return value;
	}

}
