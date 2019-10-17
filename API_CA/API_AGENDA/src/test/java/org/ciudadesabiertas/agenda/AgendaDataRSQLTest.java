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

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AgendaController;
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
public class AgendaDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	private String listURL=AgendaController.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_maximoParticipantes() throws Exception
	{

		String paramField = "q";

		String value = "maximoParticipantes==30";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 18);

	}
	
	@Test
	public void test_Busqueda_RSQL_maximoParticipantes_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "maximoParticipantes>0 and maximoParticipantes<30";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 17);

	}
	
	

	@Test
	public void test_Busqueda_nombre_lugar() throws Exception
	{

		String value = "title=='25 años Colección Pública de Fotografía Alcobendas' and equipamientoTitle=='Bulevar Salvador Allende'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_tipoEvento_lugarInscripcionNombre() throws Exception
	{

		String value = "tipoEvento=='Fiestas, Ferias y otros eventos,Otros' and lugarInscripcionTitle=='Ayuntamiento'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	
		
	
	@Test
	public void test_Busqueda_descripcion_comodin() throws Exception
	{
		
		String value = "description=='*Mercadillo vecinal en el que particulares*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	
	
	
	@Test
	public void test_Busqueda_municipioId_municipioNombre_tipo_accesibilidad() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28006' and municipioTitle=='Alcobendas' and tipoAccesibilidad=='Solo auditivo'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 13);
	}
	
	
	@Test
	public void test_Busqueda_fechaInicio() throws Exception
	{

		
		String value = "fechaInicio==2018-01-01T10:30:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_fechaFin() throws Exception
	{

		
		String value = "fechaFin==2018-12-31T14:30:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_Rango_Fechas() throws Exception
	{
		
		String value = "fechaInicio>=2019-01-12T00:00:00 and fechaFin<2019-02-13T23:59:59";


		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 7);
	}
	
	
	@Test
	public void test_Busqueda_imagen() throws Exception
	{

		String value = "image=='*cm_payoff_1-sht_v6_lg_3.jpg*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	
	
	@Test
	public void test_Busqueda_accesible_tipo_accesibilidad_rango_edad() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "accesible=='true' and tipoAccesibilidad=='Total' and ageRange=='Todas las edades'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 21);
	}
	
	

	
	


}
