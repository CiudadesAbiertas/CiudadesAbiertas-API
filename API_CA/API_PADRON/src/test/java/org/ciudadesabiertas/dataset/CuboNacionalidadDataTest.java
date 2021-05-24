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
import org.ciudadesabiertas.dataset.controller.CuboNacionalidadController;
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
public class CuboNacionalidadDataTest
{

	@Autowired
	private WebApplicationContext wac;	

	private MockMvc mockMvc;
	
	private String listURL=CuboNacionalidadController.LIST;
	private String queryURL=CuboNacionalidadController.QUERY;
	
	
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
	public void test_Busqueda_Sex() throws Exception
	{

		String [] paramField= {"sex","numeroPersonas"};
		String [] value = {"sex-F","75321"};		
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_MunicipioId() throws Exception
	{		
		String [] paramField= {"municipioId","numeroPersonas"};
		String [] value = {"28006","75321"};		
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_MunicipioTitle() throws Exception
	{
		String [] paramField= {"municipioTitle","numeroPersonas"};
		String [] value = {"Alcobendas","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
		
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{
		String [] paramField= {"distritoId","numeroPersonas"};
		String [] value = {"2800601","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{
		String [] paramField= {"distritoTitle","numeroPersonas"};
		String [] value = {"Distrito 1","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
		String [] paramField= {"barrioId","numeroPersonas"};
		String [] value = {"28006011","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
		String [] paramField= {"barrioTitle","numeroPersonas"};
		String [] value = {"Barrio 1","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_seccionCensalId() throws Exception
	{
		String [] paramField= {"seccionCensalId","numeroPersonas"};
		String [] value = {"2800601020","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_seccionCensalTitle() throws Exception
	{
		String [] paramField= {"seccionCensalTitle","numeroPersonas"};
		String [] value = {"Sección Censal 20","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_refPeriod() throws Exception
	{
		String [] paramField= {"refPeriod","numeroPersonas"};
		String [] value = {"2016","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_edadGruposQuinquenales() throws Exception
	{
		String [] paramField= {"edadGruposQuinquenales","numeroPersonas"};
		String [] value = {"00-a-04","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numeroPersonas() throws Exception
	{
		String paramField="numeroPersonas";
		String value = "75321";
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value.toUpperCase(), mockMvc);
		assertTrue(records.size() == 1);
	}

	
	@Test
	public void test_Busqueda_nacionalidad() throws Exception
	{
	    String [] paramField= {"nacionalidad","numeroPersonas"};
		String [] value = {"espanoles","75321"};
		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	 @Test    
    public void test_Query1Dimension() throws Exception 
	{   
    	String [] paramField= {"dimension","group","measure"};
		String [] value = {"nacionalidad","AVG","numeroPersonas"};           
        JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);               
    }
	    
	    
    @Test    
    public void test_Query2Dimensions() throws Exception 
    {	
    	String [] paramField= {"dimension","group","measure"};
		String [] value = {"nacionalidad, refPeriod","AVG","numeroPersonas"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);
    }
	    
	    
    @Test    
    public void test_Query2DimensionsWhere() throws Exception
    {	
    	String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"nacionalidad,refPeriod","AVG","numeroPersonas","refPeriod=2016"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);
    }
    
    @Test
   	public void test_Busqueda_AutonomiaPaisId() throws Exception
   	{		
   		String [] paramField= {"autonomiaId","paisId","numeroPersonas"};
   		String [] value = {"13","724","1232"};		
   		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
   		assertTrue(records.size() == 1);
   		
   	
   	}
   	
   	@Test
   	public void test_Busqueda_AutonomiaPaisTitle() throws Exception
   	{
   		String [] paramField= {"autonomiaTitle","paisTitle","numeroPersonas"};
   		String [] value = {"Comunidad de Madrid","España","1232"};
   		JSONArray records = TestUtils.extractRecords(listURL, paramField,  value, mockMvc);
   		assertTrue(records.size() == 1);
   	}
   	
   	
   	@Test    
    public void test_Query3DimensionsPais() throws Exception 
    {	
    	String [] paramField= {"dimension","group","measure","where"};
		String [] value = {"nacionalidad,refPeriod,paisTitle","AVG","numeroPersonas","refPeriod=2016"};   
		JSONArray records = TestUtils.extractRecords(queryURL, paramField,  value, mockMvc);
		assertTrue(records.size() == 2);
    }
    
	

}
