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

package org.ciudadesabiertas.localcomercial;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.LocalComercialController;
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
public class LocalComercialDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;


	private String listURL=LocalComercialController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_RSQL_latidud_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>440636 and xETRS89<440637";
		

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 11);

	}
	
	@Test
	public void test_Busqueda_RSQL_longitud_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4478906 and yETRS89<4478907";
		
		
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 8);

	}


	
			
	
	@Test
	public void test_Busqueda_description_comodin() throws Exception
	{
		
		String value = "description=='*cafe*'";
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 123);
	}
	
	
	@Test
	public void test_Busqueda_postalCode_streetAddress() throws Exception
	{

		String value = "postalCode=='28039' and streetAddress=in=('Calle Artistas Num 13','Calle Torrijos Num 17')";
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 4);
	}
	
	
	@Test
	public void test_Busqueda_municipioId_municipioTitle_barrio() throws Exception
	{
		
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and barrio=='Valdeacederas'";		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1749);
	}
	
	@Test
	public void test_Busqueda_barrio_or() throws Exception
	{

		String value = "barrio=='Valdeacederas' or barrio=='Castillejos'";
	
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2839);
	}
	
	@Test
	public void test_Busqueda_Url() throws Exception
	{

		String value = "url=='http://api.ciudadesabiertas.org/id=60000122'";
	
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_telephone() throws Exception
	{

		String value = "telephone=='9199999968'";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 118);
	}
	
	@Test
	public void test_Busqueda_aforo() throws Exception
	{

		String value = "aforo==85";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 114);
	}
	
	@Test
	public void test_Busqueda_agrupacionComercial() throws Exception
	{

		String value = "agrupacionComercial=='99000218'";
		
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 36);
	}
	
	
	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{

		String value = "distrito=='Tetuan' and barrio=='Cuatro Caminos'";
		

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2319);
	}
	
	@Test
	public void test_Busqueda_id() throws Exception
	{
		String value = "id=='60000170'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_Busqueda_nombreComercial() throws Exception
	{
		String value = "nombreComercial=='Nombre Comercial PESCADERIA'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 7);
	}
	@Test
	public void test_Busqueda_referenciaCatastral() throws Exception
	{
		String value = "referenciaCatastral=='9872023 VH5797S 0001 WX' and barrio=='Castillejos'";
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1090);
	}
	@Test
	public void test_Busqueda_rotulo() throws Exception
	{
		String value = "rotulo=='Rotulo ADOLFO DOMINGUEZ'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_Busqueda_tieneLicenciaApertura() throws Exception
	{
		String value = "tieneLicenciaApertura=='60000068/106-1993-02762'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_Busqueda_tieneTerraza() throws Exception
	{
		String value = "tieneTerraza=='4769'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_Busqueda_tipoAcceso() throws Exception
	{
		String value = "tipoAcceso=='puerta de calle'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 8725);
	}
	
	@Test
	public void test_Busqueda_title() throws Exception
	{
		String value = "title=='Tiger'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1);
	}
	@Test
	public void test_Busqueda_tipoActividadEconomica() throws Exception
	{
		String value = "tipoActividadEconomica==56";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 1016);
	}
	@Test
	public void test_Busqueda_tipoSituacion() throws Exception
	{
		String value = "tipoSituacion=='activo'";		
		String paramField = "q";
		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		assertTrue(total == 6039);
	}


	
	
	

}
