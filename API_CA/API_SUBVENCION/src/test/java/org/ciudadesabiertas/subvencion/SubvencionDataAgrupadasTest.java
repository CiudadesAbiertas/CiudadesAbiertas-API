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

package org.ciudadesabiertas.subvencion;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.SubvencionController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
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
public class SubvencionDataAgrupadasTest
{	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();
	
	private String listURL=SubvencionController.SEARCHGROUP;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Agrupada_01() throws Exception
	{
		
		String params="fields=areaTitle,sum(importe)&group=areaTitle&sort=-sum(importe)";
		

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 31);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_02() throws Exception
	{
		
		String params="fields=EXTRACT(YEAR FROM fechaAdjudicacion),EXTRACT(MONTH FROM fechaAdjudicacion),sum(importe)&group=EXTRACT(YEAR FROM fechaAdjudicacion),EXTRACT(MONTH FROM fechaAdjudicacion)";
		

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 29);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_03() throws Exception
	{
		
		String params="fields=count(id)&group=tipoInstrumento";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	@Test
	public void test_Busqueda_Agrupada_04() throws Exception
	{
		
		String params="fields=count(distinct adjudicatario_id)&group=tipoInstrumento";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	@Test
	public void test_Busqueda_Agrupada_05() throws Exception
	{
		
		String params="fields=count(distinct aplicacionPresupuestaria)&group=tipoInstrumento";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_06() throws Exception
	{
		
		String params="fields=sum(importe)&group=tipoInstrumento";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_07() throws Exception
	{
		
		String params="fields=EXTRACT(YEAR FROM fechaAdjudicacion), EXTRACT(MONTH FROM fechaAdjudicacion), sum(importe)&where=fechaAdjudicacion>='2017-08-01T00:00:00' and fechaAdjudicacion<'2018-09-01T00:00:00'&group=EXTRACT(YEAR FROM fechaAdjudicacion), EXTRACT(MONTH FROM fechaAdjudicacion)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 13);

	}
	
	@Test
	public void test_Busqueda_Agrupada_08() throws Exception
	{
		
		String params="fields=EXTRACT(YEAR FROM fechaAdjudicacion),EXTRACT(MONTH FROM fechaAdjudicacion),sum(importe)&where=areaTitle like 'Área gobierno de equidad, derechos sociales y empleo'&group=EXTRACT(YEAR FROM fechaAdjudicacion),EXTRACT(MONTH FROM fechaAdjudicacion)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 17);

	}
	
	@Test
	public void test_Busqueda_Agrupada_09() throws Exception
	{
		
		String params="fields=areaTitle,sum(importe)&where=areaTitle like 'Área gobierno de equidad, derechos sociales y empleo'&group=areaTitle&sort=-sum(importe)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_10() throws Exception
	{
		
		String params="fields=adjudicatarioId,adjudicatarioTitle&sort=-sum(importe)&group=adjudicatarioId,adjudicatarioTitle&where=adjudicatarioId like 'U*'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 2);

	}
	
	@Test
	public void test_Busqueda_Agrupada_11() throws Exception
	{
		
		String params="fields=adjudicatarioId,adjudicatarioTitle&group=adjudicatarioId,adjudicatarioTitle&sort=-sum(importe)&where=adjudicatarioId like 'G*'&pageSize=10&page=1";

		long total = TestUtils.extractTotalRecords(listURL, params, mockMvc);
		
		assertTrue(total == 935);

	}
	
	@Test
	public void test_Busqueda_Agrupada_12_incidencia_left_by_substring() throws Exception
	{
		//fields=sum(importe),substring(adjudicatarioId,1,1)&group=substring(adjudicatarioId,1,1)
		String params="fields=sum(importe),substring(adjudicatarioId,1,1)&group=substring(adjudicatarioId,1,1)";

		long total = TestUtils.extractTotalRecords(listURL, params, mockMvc);
		
		assertTrue(total == 23);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_13() throws Exception
	{
		
		String params="fields=areaTitle,sum(importe),count(title)&group=areaTitle&sort=-sum(importe)&where=title like '*pintura*'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 9);
	}
	
	@Test
	public void test_Busqueda_Agrupada_14() throws Exception
	{
		
		String params="fields=areaTitle,sum(importe),count(title)&group=areaTitle&sort=-sum(importe)&where=title like '*RAPIDA*DALI*'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Agrupada_15() throws Exception
	{
		
		String params="fields=sum(importe),EXTRACT(YEAR FROM fechaAdjudicacion)&group=EXTRACT(YEAR FROM fechaAdjudicacion)&sort=-EXTRACT(YEAR FROM fechaAdjudicacion)&where=adjudicatarioId like 'U86902889'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_Agrupada_16() throws Exception
	{
		
		String params="fields=count(distinct adjudicatarioId)&group=tipoInstrumento";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	@Test
	public void test_Busqueda_Agrupada_17() throws Exception
	{
		
		String params="fields=areaTitle, sum(importe)&group=areaTitle&sort=-sum(importe)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 31);

	}
	
	@Test
	public void test_Busqueda_Agrupada_18() throws Exception
	{
		
		String params="fields=substring(adjudicatarioId,1,1),sum(importe)&group=substring(adjudicatarioId,1,1)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 23);

	}
	
	@Test
	public void test_Busqueda_Agrupada_19() throws Exception
	{
		
		String params="fields=areaTitle&group=areaTitle";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 31);

	}
	
	@Test
	public void test_Busqueda_Agrupada_20() throws Exception
	{
		
		String params="fields=EXTRACT(YEAR FROM fechaAdjudicacion)&group=EXTRACT(YEAR FROM fechaAdjudicacion)";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 3);

	}
	
	@Test
	public void test_Busqueda_Agrupada_21() throws Exception
	{
		
		String params="fields=lineaFinanciacion,sum(importe)&group=lineaFinanciacion";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 5);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_22() throws Exception
	{
		
		String params="fields=title,areaTitle&group=title,areaTitle&sort=-sum(importe)";

		long total = TestUtils.extractTotal(listURL, params, mockMvc);
		
		assertTrue(total == 443);

	}
	
	@Test
	public void test_Busqueda_Agrupada_23() throws Exception
	{
		
		String params="fields=count(importe)&group=title,areaTitle&where=title like 'CONVENIO SUBV. CON UCCI 2017' and areaTitle like 'COORDINACIÓN GENERAL DE LA ALCALDÍA'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);

	}
	
	
	@Test
	public void test_Busqueda_Agrupada_24() throws Exception
	{
		
		String params="fields=sum(importe)&group=title,areaTitle&where=title like 'CONVENIO SUBV. CON UCCI 2017' and areaTitle like 'COORDINACIÓN GENERAL DE LA ALCALDÍA'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Busqueda_Agrupada_25() throws Exception
	{
		
		String params="fields=title,areaTitle,sum(importe),count(title)&group=title,areaTitle&sort=-sum(importe)";
		
		long total = TestUtils.extractTotal(listURL, params, mockMvc);
		
		assertTrue(total == 443);

	}
	
	@Test
	public void test_Busqueda_Agrupada_26() throws Exception
	{
		
		String params="fields=sum(importe),adjudicatarioId,adjudicatarioTitle&group=adjudicatarioId,adjudicatarioTitle&sort=-sum(importe)";
		
		long total = TestUtils.extractTotal(listURL, params, mockMvc);
		
		assertTrue(total == 6589);

	}
	
	@Test
	public void test_Busqueda_Agrupada_27() throws Exception
	{
		
		String params="fields=count(title),adjudicatarioId,adjudicatarioTitle&group=adjudicatarioId,adjudicatarioTitle&sort=-count(title)";
		
		long total = TestUtils.extractTotal(listURL, params, mockMvc);
		
		assertTrue(total == 6589);

	}
	
	@Test
	public void test_Busqueda_Agrupada_28() throws Exception
	{
		
		String params="fields=sum(importe)&group=adjudicatarioId,adjudicatarioTitle&where=adjudicatarioId like 'U86902889'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Busqueda_Agrupada_29() throws Exception
	{
		
		String params="fields=count(title)&group=adjudicatarioId,adjudicatarioTitle&where=adjudicatarioId like 'U86902889'";

		JSONArray records = TestUtils.extractRecords(listURL, params, mockMvc);
		
		assertTrue(records.size() == 1);

	}
	
}
