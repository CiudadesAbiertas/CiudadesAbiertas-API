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

package org.ciudadesabiertas.agendamunicipal;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AgendaMEventoController;
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
public class AgendaMEventoDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	private String listURL=AgendaMEventoController.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	
	
	

	@Test
	public void test_Busqueda_title() throws Exception
	{

		String value = "title=='Acto de entrega*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_por_Tipos() throws Exception
	{

		String value = "tipoEvento=='evento-con-medio-de-comunicacion' and tipoSesion=='extraordinaria' and tipoAcceso=='privado'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
		
	
	@Test
	public void test_Busqueda_descripcion_comodin() throws Exception
	{
		
		String value = "description=='*Acto de entrega*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
			
	
	
	@Test
	public void test_Busqueda_municipioId_municipioNombre_distrito() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28006' and municipioTitle=='Alcobendas' and distritoId=='2800601'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
	
	
	@Test
	public void test_Busqueda_fechaInicio() throws Exception
	{

		String value = "startDate==2019-12-08T10:00:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_fechaFin() throws Exception
	{

		
		String value = "endDate==2019-12-08T20:00:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_Rango_Fechas() throws Exception
	{
		
		String value = "startDate>=2019-01-12T00:00:00 and endDate<2019-12-30T23:59:59";


		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
			
	
	
	
	@Test
	public void test_Busqueda_locationTitle_reunionLobby() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "reunionLobby=='true' and locationTitle=='IFEMA'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_equipamiento_id() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "equipamientoId=='EQ0008' ";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_super_event_id() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "superEventId=='AGM0001' ";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	


}
