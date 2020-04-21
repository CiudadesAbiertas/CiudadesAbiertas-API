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
import org.ciudadesabiertas.dataset.controller.AvisoQuejaSugController;
import org.ciudadesabiertas.utils.TestUtils;
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
public class AvisoQuejaSugDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;


	private String  listURL = AvisoQuejaSugController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_id() throws Exception
	{

		String paramField = "q";

		String value = "id=='AQSA0007'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_identifier() throws Exception
	{

		String paramField = "q";

		String value = "identifier=='0-33'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 154);

	}
	
	

	@Test
	public void test_Busqueda_title() throws Exception
	{

		String value = "title=='*AVISO-R RECOGIDA ANIMALES MUERTOS*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 27);
	}
	
	@Test
	public void test_Busqueda_stat_postal_code() throws Exception
	{

		String value = "stat=='abierto' and postalCode=='28019'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 22);
	}	
			
	
	@Test
	public void test_Busqueda_details_comodin() throws Exception
	{
		
		String value = "details=='*ESPACIOS PUBLICOS/MOBILIARIO*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 154);
	}	
	
	
	@Test
	public void test_Busqueda_municipioId_postalCode() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and postalCode=='28024'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 11);
	}	
	
	@Test
	public void test_Busqueda_openDate() throws Exception
	{

		
		String value = "openDate==2018-01-01T00:59:30";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_closeDate() throws Exception
	{

		
		String value = "closeDate==2018-12-27T12:07:39";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_Rango_Fechas() throws Exception
	{
		
		String value = "openDate>=2014-03-17T00:00:00 and closeDate<2018-12-13T23:59:59";


		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 768);
	}
	
	
	@Test
	public void test_Busqueda_source() throws Exception
	{

		String value = "source=='*MOVIL*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 170);
	}
		
	
	@Test
	public void test_Busqueda_Rango_movil_address() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "source=='MOVIL' and streetAddress=='CALLE EDUARDO MARQUINA 37*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_Rango_longitud() throws Exception
	{
		String paramField = "q";

		String value = "yETRS89>4468052 and yETRS89<4468053";
	

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_lat() throws Exception
	{
		String paramField = "q";

		String value = "xETRS89==449737.37";
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 3);
	}
	

	
	
	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{

		String value = "distritoId=='28079606' and barrioId=='280796062'";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2000);
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
