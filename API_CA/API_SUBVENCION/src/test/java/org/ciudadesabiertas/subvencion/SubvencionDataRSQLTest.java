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
public class SubvencionDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL=SubvencionController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_importe() throws Exception
	{

		String paramField = "q";

		String value = "importe==19406";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 2);

	}
	
	@Test
	public void test_Busqueda_RSQL_importe_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "importe>10000 and importe<20000";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 325);

	}

	@Test
	public void test_Busqueda_importe_adjudicatario() throws Exception
	{

		String value = "importe<10000 and adjudicatarioId=in=(08029713E,21406767T)";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5);
	}
	
	
	@Test
	public void test_Busqueda_fechaAdjudicacion() throws Exception
	{

		
		String value = "fechaAdjudicacion==2016-03-29T00:00:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	

	@Test
	public void test_Busqueda_fechaAdjudicacion_intervalo() throws Exception
	{

		
		String value = "fechaAdjudicacion>=2016-01-01T00:00:00 and fechaAdjudicacion<=2016-06-01T00:00:00";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 13);
	}
	
	
	@Test
	public void test_Busqueda_fechaAdjudicacion_title() throws Exception
	{

		
		String value = "title=='subvención nominativa ucci actividades generales'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_fechaAdjudicacion_title_comodin() throws Exception
	{
		
		String value = "title==*subvención*";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total > 1000);
	}
	
	
	
	
	@Test
	public void test_Busqueda_entidadFinanciadoraId() throws Exception
	{

		String value = "importe<10000 and adjudicatarioId=in=(08029713E,21406767T)";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5);
	}
	
	@Test
	public void test_Busqueda_areaId_areaTitle() throws Exception
	{

		String value = "areaId=='A05003366' or areaTitle=='madrid salud*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 211);
	}
	
	@Test
	public void test_Busqueda_municipioId_municipioTitle() throws Exception
	{

		String value = "municipioId=='28079' and municipioTitle=='madrid'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 8298);
	}
	
	@Test
	public void test_Busqueda_adjudicatarioId_adjudicatarioTitle() throws Exception
	{

		String value = "adjudicatarioId=='Q2866001G' or adjudicatarioTitle=='cruz roja*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 13);
	}
	
	@Test
	public void test_Busqueda_entidadFinanciadoraId_entidadFinanciadoraTitle() throws Exception
	{

		String value = "entidadFinanciadoraId=='A05003340' or entidadFinanciadoraTitle=='*ALCALDÍA*'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4142);
	}
	
	@Test
	public void test_Busqueda_lineaFinanciacion() throws Exception
	{

		String value = "lineaFinanciacion=in=('Medio ambiente y sostenibilidad','Fomento ecomómico y social')";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3353);
	}
	
	@Test
	public void test_Busqueda_basesReguladoras() throws Exception
	{

		String value = "basesReguladoras=='https://www.bocm.es/boletin/cm_orden_bocm/2015/12/30/bocm-20151230-29.pdf'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 95);
	}
	
	
	@Test
	public void test_Busqueda_tipoInstrumento() throws Exception
	{

		String value = "tipoInstrumento=='PRÉSTAMOS'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1677);
	}
	
	
	@Test
	public void test_Busqueda_aplicacionPresupuestaria() throws Exception
	{

		String value = "aplicacionPresupuestaria=='2016-G/92207/48901'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 5);
	}
	
	

	

}
