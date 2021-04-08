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
import org.ciudadesabiertas.dataset.controller.CuboIndicadoresController;
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
public class CuboIndicadoresDataTest
{

	@Autowired
	private WebApplicationContext wac;	

	private MockMvc mockMvc;
	
	private String listURL=CuboIndicadoresController.LIST;
	private String queryURL=CuboIndicadoresController.QUERY;
	
	
	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";		
		String value = "obs1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}	
	
	
	@Test
	public void test_Busqueda_MunicipioId() throws Exception
	{		
		String [] paramField= {"municipioId","indiceJuventud"};
		String [] value = {"28006","72.44"};		
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_MunicipioTitle() throws Exception
	{
		String [] paramField= {"municipioTitle","indiceJuventud"};
		String [] value = {"Alcobendas","72.44"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
		
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{
		String [] paramField= {"distritoId","indiceJuventud"};
		String [] value = {"2800601","72.44"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{
		String [] paramField= {"distritoTitle","indiceJuventud"};
		String [] value = {"Distrito 1","72.44"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
		String [] paramField= {"barrioId","indiceJuventud"};
		String [] value = {"28006011","315.96"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
		String [] paramField= {"barrioTitle","indiceJuventud"};
		String [] value = {"Valdelasfuentes","315.96"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
//	@Test
//	public void test_Busqueda_seccionCensalId() throws Exception
//	{
//		String [] paramField= {"seccionCensalId","indiceJuventud"};
//		String [] value = {null,"72.44"};
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
//		assertTrue(records.size() == 3);
//	}
	
//	@Test
//	public void test_Busqueda_seccionCensalTitle() throws Exception
//	{
//		String [] paramField= {"seccionCensalTitle","numeroPersonas"};
//		String [] value = {"Sección Censal 20","82665"};
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	@Test
	public void test_Busqueda_refPeriod() throws Exception
	{
		String [] paramField= {"refPeriod","indiceJuventud"};
		String [] value = {"2011","72.44"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	
//	@Test
//	public void test_Busqueda_numeroPersonas() throws Exception
//	{
//		String paramField="numeroPersonas";
//		String value = "82665";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	@Test
	public void test_Busqueda_indiceDependencia() throws Exception
	{
		String paramField="indiceDependencia";
		String value = "48";
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}

//	@Test
//	public void test_Busqueda_indiceFeminidad() throws Exception
//	{
//		String paramField="indiceFeminidad";
//		String value = "100";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	
//	@Test
//	public void test_Busqueda_indiceInfancia() throws Exception
//	{
//		String paramField="indiceInfancia";
//		String value = "7";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 3);
//	}
	
	
	@Test
	public void test_Busqueda_indiceJuventud() throws Exception
	{
		String paramField="indiceJuventud";
		String value = "72.44";
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}
	
//	@Test
//	public void test_Busqueda_indiceMaternidad() throws Exception
//	{
//		String paramField="indiceMaternidad";
//		String value = "4";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_indicePoblacionActiva() throws Exception
//	{
//		String paramField="indicePoblacionActiva";
//		String value = "99";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_indiceReemplazo() throws Exception
//	{
//		String paramField="indiceReemplazo";
//		String value = "10";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() ==1);
//	}
	
	
	@Test
	public void test_Busqueda_indiceSobreenvejecimiento() throws Exception
	{
		String paramField="indiceSobreenvejecimiento";
		String value = "127.30";
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}

//	@Test
//	public void test_Busqueda_indiceTendencia() throws Exception
//	{
//		String paramField="indiceTendencia";
//		String value = "3";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_tasaMortalidad() throws Exception
//	{
//		String paramField="tasaMortalidad";
//		String value = "35";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_tasaNatalidad() throws Exception
//	{
//		String paramField="tasaNatalidad";
//		String value = "45";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_edadMediaPoblacion() throws Exception
//	{
//		String paramField="edadMediaPoblacion";
//		String value = "4";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
//	@Test
//	public void test_Busqueda_porcentajePoblacionJoven() throws Exception
//	{
//		String paramField="porcentajePoblacionJoven";
//		String value = "7";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 2);
//	}
	
	
//	@Test
//	public void test_Busqueda_porcentajePoblacionAdulta() throws Exception
//	{
//		String paramField="porcentajePoblacionAdulta";
//		String value = "11";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	
//	@Test
//	public void test_Busqueda_porcentajePoblacionEnvejecida() throws Exception
//	{
//		String paramField="porcentajePoblacionEnvejecida";
//		String value = "1";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	
//	@Test
//	public void test_Busqueda_porcentajePoblacionExtranjeraInfantil() throws Exception
//	{
//		String paramField="porcentajePoblacionExtranjeraInfantil";
//		String value = "3";
//		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
//		assertTrue(records.size() == 1);
//	}
	
	
	
	
	 @Test    
    public void test_Query1Dimension() throws Exception 
	{   
    	String [] paramField= {"dimension","group","measure"};
		String [] value = {"refPeriod","AVG","indiceJuventud"};           
        JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 10);               
    }
	    
	    
    @Test    
    public void test_Query2Dimensions() throws Exception 
    {	
    	String [] paramField= {"dimension","group","measure"};
		String [] value = {"refPeriod,distritoId","AVG","indiceJuventud"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 10);
    }
	    
	    
    @Test    
    public void test_Query2DimensionsWhere() throws Exception
    {	
    	String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"refPeriod,distritoId","AVG","indiceJuventud","refPeriod=2011"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
    }
    
	

}
