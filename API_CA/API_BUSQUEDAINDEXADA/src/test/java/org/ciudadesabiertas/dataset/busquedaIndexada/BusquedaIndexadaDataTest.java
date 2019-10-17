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

package org.ciudadesabiertas.dataset.busquedaIndexada;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.busquedaIndexada.controller.BusquedaIndexadaController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
public class BusquedaIndexadaDataTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;


	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	 

    @Test
    public void test00() throws Exception  {
    	assertTrue(true);
    }
	
	//@Test
	public void test_Busqueda() throws Exception
	{

		String [] paramField= {};

		String [] value = {};

		JSONArray records = extractRecords(paramField, value);

		assertTrue(records.size() == 64);

	}

	//@Test
	public void test_Busqueda_dataset() throws Exception
	{
		String [] paramField= {"dataset"};

		String [] value = {"agendaTests"};

		JSONArray records = extractRecords(paramField, value);

		assertTrue(records.size() == 64);
	}
	
	
	//@Test
	public void test_Busqueda_query() throws Exception
	{
		String [] paramField= {"query"};

		String [] value = {"pintura"};

		JSONArray records = extractRecords(paramField, value);

		assertTrue(records.size() == 10);
	}
	
	
	//@Test
	public void test_Busqueda_query_dataset() throws Exception
	{
		String [] paramField= {"query","dataset"};

		String [] value = {"cine","agendaTests"};

		JSONArray records = extractRecords(paramField, value);

		assertTrue(records.size() == 2);
	}
	
	
	
	
		
	
	private JSONArray extractRecords(String [] paramField, String [] value) throws Exception, UnsupportedEncodingException, ParseException
	{
		if (paramField.length==value.length) {
			String cadena = new String();
			for (int i=0; i<value.length; i++) {
				if (i==0) {
					cadena+= "?"+paramField[i]+"=" + value[i];
				}else {
					cadena+= "&"+paramField[i]+"=" + value[i];
				}
			}
			
			MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(BusquedaIndexadaController.SOLR_LIST+".json" + cadena)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			String content = result.getResponse().getContentAsString();

			JSONObject resultObj = (JSONObject) parser.parse(content);

			JSONArray records = (JSONArray) resultObj.get("records");
			return records;
		}else {
			return null;
		}
	}
	

}
