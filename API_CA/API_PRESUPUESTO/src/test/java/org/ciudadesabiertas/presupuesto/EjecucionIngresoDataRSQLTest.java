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

package org.ciudadesabiertas.presupuesto;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.EjecucionIngresoController;
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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EjecucionIngresoDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL = EjecucionIngresoController.LIST;

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

		String value = "id=='EJEINGRESO01'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_excesoDefectoPrevision() throws Exception
	{

		String paramField = "q";

		String value = "excesoDefectoPrevision==67543";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	
	@Test
	public void test_Busqueda_RSQL_previsionModificaciones() throws Exception
	{

		String paramField = "q";

		String value = "previsionModificaciones==0";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total ==2 );

	}

	
	
	
	@Test
	public void test_Busqueda_RSQL_previsionDefinitiva() throws Exception
	{

		String value = "previsionDefinitiva==57543";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_RSQL_derechosReconocidos() throws Exception
	{		
		String value = "derechosReconocidos==65439";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_RSQL_derechosAnulados() throws Exception
	{		
		String value = "derechosAnulados==75439";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}

	@Test
	public void test_Busqueda_RSQL_derechosCancelados() throws Exception
	{		
		String value = "derechosCancelados==68";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_RSQL_derechosReconocidosNetos() throws Exception
	{		
		String value = "derechosReconocidosNetos==65371";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_RSQL_recaudacionNeta() throws Exception
	{		
		String value = "recaudacionNeta==65371";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_RSQL_derechosPendientesCobro31Dic() throws Exception
	{		
		String value = "derechosPendientesCobro31Dic==0";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Busqueda_RSQL_periodoEjecucionBeginning() throws Exception
	{		
		String value = "periodoEjecucionBeginning=='2019-01'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_RSQL_periodoEjecucionEnd() throws Exception
	{		
		String value = "periodoEjecucionEnd=='2019-11'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_RSQL_presupuestoIngreso() throws Exception
	{		
		String value = "presupuestoIngreso=='PREINGRESO01'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
}
