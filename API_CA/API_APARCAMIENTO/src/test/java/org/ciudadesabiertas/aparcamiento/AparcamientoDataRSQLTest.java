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

package org.ciudadesabiertas.aparcamiento;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AparcamientoController;
import org.ciudadesabiertas.dataset.utils.AparcamientoConstants;
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
public class AparcamientoDataRSQLTest
{

	@Autowired
	private WebApplicationContext wac;

	String listURL = AparcamientoController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_X() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89==441270.00046";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_Y() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89==4475197.11781";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_X_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>441270 and xETRS89<441271";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_RSQL_y_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4475197 and yETRS89<4475198";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_title_tipoEquipamiento() throws Exception
	{

		String value = "title=='Aparcamiento público. Auditorio Nacional de Música (Príncipe de Vergara)' and tipoEquipamiento=='" + AparcamientoConstants.TIPO_EQUIPAMIENTO + "'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_description_comodin() throws Exception
	{

		String value = "description=='*312 públicas y 174*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_postalCode_streetAddress() throws Exception
	{

		String value = "postalCode=='28010' and streetAddress=in=('CALLE FUENCARRAL V 111')";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_municipioId_municipioTitle_barrio() throws Exception
	{
		// Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and barrioId=='280796062'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 56);
	}

	@Test
	public void test_Busqueda_barrio_or() throws Exception
	{

		String value = "(barrioId=='280796062')";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 56);
	}

	@Test
	public void test_Busqueda_Url() throws Exception
	{
		String url = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=db8bc2c6e051c010VgnVCM2000000c205a0aRCRD";
		String value = "url=='" + Util.encodeURL(url) + "'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_email_or_telephone() throws Exception
	{

		String value = "email=='clientes@empark.es' or telephone=='91 401 73 74'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}

	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{

		String value = "distritoId=='28079606'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 56);
	}

	@Test
	public void test_Busqueda_openingHours() throws Exception
	{

		String value = "openingHours=='Sin Horario'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
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
