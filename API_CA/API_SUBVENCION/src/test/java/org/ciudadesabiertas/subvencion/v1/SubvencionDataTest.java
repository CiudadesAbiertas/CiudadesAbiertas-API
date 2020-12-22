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

package org.ciudadesabiertas.subvencion.v1;


import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.v1.SubvencionControllerV1;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubvencionDataTest
{

	@Autowired
	private WebApplicationContext wac;	

	private MockMvc mockMvc;
	
	private String listURL=SubvencionControllerV1.LIST;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String paramField="id";
		
		String value = "S0001";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}

	@Test
	public void test_Busqueda_Nombre() throws Exception
	{
		String paramField="title";

		String value = "*convocatoria de una beca*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);

		assertTrue(records.size() > 1);
	}
	
	@Test
	public void test_Busqueda_areaId() throws Exception
	{
		String paramField="areaId";
		
		String value = "A05003340";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == StartVariables.defaultPageSize);
	}
	
	@Test
	public void test_Busqueda_areaNombre() throws Exception
	{
		String paramField="areaTitle";

		String value = "ÁREA DE GOBIERNO DE CULTURA Y DEPORTES";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == StartVariables.defaultPageSize);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String paramField="municipioId";

		String value = "28079";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == StartVariables.defaultPageSize);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{

		String value = "madrid";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == StartVariables.defaultPageSize);
	}
	
	
	@Test
	public void test_Busqueda_municipioId_KO() throws Exception
	{
		String paramField="municipioId";

		String value = "28078";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre_KO() throws Exception
	{

		String value = "madrid2";
		
		String paramField="municipioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size()==0);
	}
	
	@Test
	public void test_Busqueda_adjudicatarioId() throws Exception
	{

		String value = "02287827V";
		
		String paramField="adjudicatarioId";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_adjudicatarioNombre() throws Exception
	{

		String value = "francisco javier sánchez platero";
		
		String paramField="adjudicatarioTitle";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_entidadFinanciadoraId() throws Exception
	{

		String value = "A05003362";
		
		String paramField="entidadFinanciadoraId";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 90);
	}
	
	
	@Test
	public void test_Busqueda_entidadFinanciadoraNombre() throws Exception
	{

		String value = "madrid salud";
		
		String paramField="entidadFinanciadoraTitle";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 90);
	}
	
	@Test
	public void test_Busqueda_importe() throws Exception
	{

		String value = "11700";
		
		String paramField="importe";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 2);
	}

	@Test
	public void test_Busqueda_fechaAdjudicacion() throws Exception
	{

		String value = "2016-03-29T00:00:00";
		
		String paramField="fechaAdjudicacion";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_lineaFinanciacion() throws Exception
	{

		String value = "Fomento económico y social";
		
		String paramField="lineaFinanciacion";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1715);
	}
	
	
	
	@Test
	public void test_Busqueda_basesReguladoras() throws Exception
	{

		String value = "http://www.bocm.es/boletin/cm_orden_bocm/2017/02/20/bocm-20170220-27.pdf";
		
		String paramField="basesReguladoras";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 114);
	}
	
	
	@Test
	public void test_Busqueda_tipoInstrumento() throws Exception
	{

		String value = "subvención y entrega dineraria sin contraprestación";
		
		String paramField="tipoInstrumento";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1701);
	}
	
	@Test
	public void test_Busqueda_aplicacionPresupuestaria() throws Exception
	{

		String value = "2018-G/33401/48901";
		
		String paramField="aplicacionPresupuestaria";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 56);
	}
	
	@Test
	public void test_Busqueda_tipoInstrumento_comodines_sin_acentos() throws Exception
	{

		String value = "*FINANCIACION*";
		
		String paramField="tipoInstrumento";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1613);
	}
	
	@Test
	public void test_Busqueda_distinct() throws Exception
	{
		
		String paramField="field";
		
		String value = "lineaFinanciacion";

		long total = TestUtils.extractTotalDistinct(SubvencionControllerV1.SEARCH_DISTINCT, paramField, value, mockMvc);

		assertTrue(total == 5);
	}
	
	@Test
	public void test_Busqueda_tipoProcedimiento() throws Exception
	{

		String value = "subvencion-directa";
		
		String paramField="tipoProcedimiento";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1659);
	}
	
	@Test
	public void test_Busqueda_Nominativa() throws Exception
	{

		String value = "true";
		
		String paramField="nominativa";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5205);
	}
	
	@Test
	public void test_Head_MD5() throws Exception
	{

		String value = "LINEA 1";
		
		String paramField="lineaFinanciacion";

		String md5_content = TestUtils.extractContentMD5(listURL, paramField, value, mockMvc);
		
		String md5_head = TestUtils.extractHeadMD5(listURL, paramField, value, mockMvc);
		
		assertTrue(md5_content.equals(md5_head));
	}
	
	


}
