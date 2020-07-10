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
import org.ciudadesabiertas.dataset.controller.EjecucionGastoController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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
public class EjecucionGastoDataTest
{

	@Autowired
	private WebApplicationContext wac;	

	private MockMvc mockMvc;
	
	private String listURL=EjecucionGastoController.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "EJEGASTO01";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_remanenteCredito() throws Exception
	{
		String paramField="remanenteCredito";

		String value = "67543";	

		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_creditoModificaciones() throws Exception
	{
		String paramField="creditoModificaciones";
		
		String value = "0";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_creditoDefinitivoVigente() throws Exception
	{
		String paramField="creditoDefinitivoVigente";

		String value = "57543";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_creditoAutorizado() throws Exception
	{
		String paramField="creditoAutorizado";

		String value = "65439";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_totalGastoComprometido() throws Exception
	{
		String paramField="totalGastoComprometido";

		String value = "75439";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_creditoDisponible() throws Exception
	{
		String paramField="creditoDisponible";

		String value = "68";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_creditoRetenido() throws Exception
	{
		String paramField="creditoRetenido";

		String value = "0";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_obligacionesReconocidasNetas() throws Exception
	{
		String paramField="obligacionesReconocidasNetas";

		String value = "65371";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_pagos() throws Exception
	{
		String paramField="pagos";

		String value = "0";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_obligacionesPendientes31Dic() throws Exception
	{
		String paramField="obligacionesPendientes31Dic";

		String value = "0";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_periodoEjecucionBeginning() throws Exception
	{
		String paramField="periodoEjecucionBeginning";

		String value = "2019-01";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_periodoEjecucionEnd() throws Exception
	{
		String paramField="periodoEjecucionEnd";

		String value = "2019-11";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_presupuestoGasto() throws Exception
	{
		String paramField="presupuestoGasto";

		String value = "PREGASTO01";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "presupuestoGasto";

		long total = TestUtils.extractTotalDistinct(EjecucionGastoController.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "EJEGASTO01";
		
		String paramField="id";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);
		
		assertTrue(md5_content.equals(md5_head));
	}
	
	


}
