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

package org.ciudadesabiertas.interesturistico;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.PuntoInteresTuristicoController;
import org.ciudadesabiertas.dataset.utils.PuntoInteresTuristicoConstants;
import org.ciudadesabiertas.utils.TestUtils;
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
public class PuntoInteresTuristicoDataRSQLTest {

	@Autowired
	private WebApplicationContext wac;

	private String listURL = PuntoInteresTuristicoController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception {

		String paramField = "q";

		String value = "id==PIT0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	// Genera un error muy extraño
	@Test
	public void test_Busqueda_title() throws Exception {
		String paramField = "q";

		String value = "title=='Histohuellas'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_category_title() throws Exception {
		String paramField = "q";

		String value = "category=='" + PuntoInteresTuristicoConstants.CATEGORY + "' and title=='Histohuellas*'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_description() throws Exception {
		String paramField = "q";

		String value = "description=='*grupo de licenciados en Historia e Historia del Arte*'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_municipioId_municipioNombre_tipo_accesibilidad() throws Exception {
		// Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and tipoAccesibilidad=='Variada'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}

	@Test
	public void test_Busqueda_accesible() throws Exception {

		String value = "accesible==true";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}

	@Test
	public void test_Busqueda_address_barrio_distrito() throws Exception {
		String paramField = "q";
		String value = "streetAddress=='*Mayor*' and barrioId=='280796062' and distritoId=='28079606'";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_postal_code() throws Exception {

		String paramField = "q";

		String value = "postalCode=='28012'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}

	@Test
	public void test_Busqueda_XETRS89() throws Exception {

		String paramField = "q";

		String value = "xETRS89==440291.26773";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_YETRS89() throws Exception {

		String paramField = "q";

		String value = "yETRS89==4474254.64478";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_RSQL_xETRS89_intervalo() throws Exception {

		String paramField = "q";

		String value = "xETRS89>440200 and xETRS89<440300";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);

	}

	@Test
	public void test_Busqueda_RSQL_yETRS89_intervalo() throws Exception {

		String paramField = "q";

		String value = "yETRS89>4470700 and yETRS89<4470900";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);

	}

	@Test
	public void test_Busqueda_id_getXETRS89_getYETRS89() throws Exception {

		String[] paramField = { "srId", "id" };

		String[] value = { "EPSG:23030", "PIT0004" };

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		JSONObject obj = (JSONObject) records.get(0);

		boolean checkXY = ((obj.get("xETRS89") != null) && (obj.get("yETRS89") != null));

		assertTrue(checkXY == true);
	}

	@Test
	public void test_Busqueda_id_getLatitud_getLongitud() throws Exception {

		String[] paramField = { "srId", "id" };

		String[] value = { "EPSG:23030", "PIT0004" };

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		JSONObject obj = (JSONObject) records.get(0);

		boolean checkXY = ((obj.get("latitud") != null) && (obj.get("longitud") != null));

		assertTrue(checkXY == true);
	}

	@Test
	public void test_Busqueda_email() throws Exception {

		String paramField = "q";

		String value = "email=='info@histohuellas.es'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_url() throws Exception {

		String value = "url=='http://www.esmadrid.com/informacion-turistica/histohuellas'";

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}

	@Test
	public void test_Busqueda_siglo_estiloArtistico() throws Exception {

		String paramField = "q";

		String value = "siglo=='XII' and estiloArtistico=='Romanico'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_costeMantenimiento_ingresosObtenidos() throws Exception {

		String paramField = "q";

		String value = "costeMantenimiento==19800.98 and ingresosObtenidos==126980.02";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_fechaDeclaracionBien() throws Exception {

		String value = "fechaDeclaracionBien==2019-03-25T00:00:00";

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_Rango_Fechas() throws Exception {

		String value = "fechaDeclaracionBien>=2019-01-12T00:00:00 and fechaDeclaracionBien<2019-03-26T23:59:59";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 14);
	}

	@Test
	public void test_Busqueda_modoAcceso_medioTransporte() throws Exception {

		String value = "modoAcceso=='Ascensores y Escaleras Mecánicas' and medioTransporte=='Autobuses, cernanias y Metro'";

		String paramField = "q";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}

	@Test
	public void test_Busqueda_portalId() throws Exception {

		String paramField = "q";

		String value = "portalId=='PORTAL000098'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total > 10);
	}

}
