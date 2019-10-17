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

package org.ciudadesabiertas.instalaciondep;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.InstalacionDepController;
import org.ciudadesabiertas.dataset.utils.InstalacionDepConstants;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
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
public class InstalacionDepDataRSQLTest
{

	@Autowired
	private WebApplicationContext wac;

	private String listURL = InstalacionDepController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_XETRS89() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89==439000.00048";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_YETRS89() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89==4472322.11762";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_XETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>439000 and xETRS89<439001";

		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_yETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4472322 and yETRS89<4472323";

		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_title_tipoEquipamiento() throws Exception
	{

		String value = "title=='Instalación Deportiva Municipal Básica Campo de Fútbol Tierra' and tipoEquipamiento=='" + InstalacionDepConstants.TIPO_EQUIPAMIENTO + "'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_description_comodin() throws Exception
	{

		String value = "description=='*1 Campo de fútbol*'";

		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 85);
	}

	@Test
	public void test_Busqueda_postalCode_streetAddress() throws Exception
	{

		String value = "postalCode=='28030' and streetAddress=in=('AVENIDA DOCTOR GARCIA TAPIA V 51')";

		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_municipioId_municipioTitle_barrio() throws Exception
	{
		// Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and barrio=='MARROQUINA'";

		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 10);
	}

	@Test
	public void test_Busqueda_barrio_or() throws Exception
	{

		String value = "barrio=='PIOVERA' or barrio=='DELICIAS'";
		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 7);
	}

	@Test
	public void test_Busqueda_Url() throws Exception
	{
		String url = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=fa670286ac984310VgnVCM1000000b205a0aRCRD";
		String value = "url=='" + Util.encodeURL(url) + "'";
		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_email_or_telephone() throws Exception
	{

		String value = "email=='test@pruebas.es' or telephone=='91 588 97 00'";
		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}

	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{

		String value = "distrito=='VILLAVERDE' and barrio=='BUTARQUE'";
		// Cargamos los tipos asociados a equipamiento tipo aparcamiento

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}

	@Test
	public void test_Busqueda_openingHours() throws Exception
	{

		String value = "openingHours=='Sin Horario'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}


}
