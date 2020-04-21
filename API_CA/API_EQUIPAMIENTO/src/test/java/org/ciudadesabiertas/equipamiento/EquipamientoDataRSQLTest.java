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

package org.ciudadesabiertas.equipamiento;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.EquipamientoController;
import org.ciudadesabiertas.dataset.utils.EquipamientoConstants;
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
public class EquipamientoDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL= EquipamientoController.LIST;

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

		String value = "xETRS89==444333.78925";
		
		
		
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_YETRS89() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89==4489188.88773";
		
		
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_XETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>444333 and xETRS89<444334";
		
		//Cargamos los tipos asociados a equipamiento tipo aparcamiento
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_yETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4489188 and yETRS89<4489189";
		
		//Cargamos los tipos asociados a equipamiento tipo aparcamiento
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}

	@Test
	public void test_Busqueda_title_tipoEquipamiento() throws Exception
	{

		String value = "title=='Ayuntamiento de Alcobendas' and tipoEquipamiento=='"+EquipamientoConstants.TIPO_EQUIPAMIENTO+"'";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
		
	
	@Test
	public void test_Busqueda_description_comodin() throws Exception
	{
		
		String value = "description=='*Datos de interés*'";
		
		
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 44);
	}
	
	
	@Test
	public void test_Busqueda_postalCode_streetAddress() throws Exception
	{

		String value = "postalCode=='28100' and streetAddress=in=('CL MALAGA 50','PZ MAYOR 1')";
		
		
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	
	
	@Test
	public void test_Busqueda_municipioId_municipioTitle_barrio() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28006' and municipioTitle=='Alcobendas' and barrioId=='28006011'";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 45);
	}
	
	@Test
	public void test_Busqueda_barrio() throws Exception
	{

		String value = "barrioId=='28006011'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 45);
	}
	
	
	
	@Test
	public void test_Busqueda_Url() throws Exception
	{

		String value = "url=='http://www.teatroalcobendas.org'";
		
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_email_or_telephone() throws Exception
	{

		String value = "email=='teatro@aytoalcobendas.org' or telephone=='34916590957'";
		
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{
		String value = "distritoId=='2800601' and barrioId=='28006011'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 45);
	}
	
	@Test
	public void test_Busqueda_tipoEquipamiento() throws Exception
	{

		String value = "tipoEquipamiento=='"+EquipamientoConstants.TIPO_EQUIPAMIENTO+"'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 45);
	}
	
	
	@Test
	public void test_Busqueda_titularidadPublica() throws Exception
	{

		String value = "titularidadPublica==true";
		
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 45);
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
