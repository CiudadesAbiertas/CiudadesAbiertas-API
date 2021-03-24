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

package org.ciudadesabiertas.contratos;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;
import org.ciudadesabiertas.dataset.model.Item;
import org.ciudadesabiertas.dataset.utils.ContratosConstants;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ItemController;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
public class ItemTest {
	
	private static final Logger log = LoggerFactory.getLogger(ItemTest.class);
	
	@Autowired
	private WebApplicationContext wac;

    @Autowired
    private DatasetService<Item> service;
        
    private MockMvc mockMvc;
     
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();        
    }
    
    @Test
    public void test01_Service() throws DAOException {
	
        final List<Item> list = service.basicQuery(ContratosConstants.KEY,Item.class);
        
        assertTrue( list.size()>0 );
    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("lotController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	
    	String id ="IT_TEST01";    	
    	String item = "{\n" + 
    			"      \"id\":\""+id+"\",\n" + 
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C1\"\n" + 
    			"    }";

		item = new String(item.getBytes(), "UTF-8");

		this.mockMvc.perform(
				MockMvcRequestBuilders.post(ItemController.ADD)
					.contentType(MediaType.APPLICATION_JSON)
					.content(item))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    	    	    	
    	
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String item = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ItemController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(item))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="IT_TEST01";    	
    	String item = "{\n" + 
    			"      \"id\":\""+id+"\",\n" + 
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C1\"\n" + 
    			"    }";

    	
    	item = new String (item.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ItemController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(item))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="IT_TEST01";    	
    	String item = "{\n" + 
    			"      \"id\":\""+id+"\",\n" + 
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C222222\"\n" + 
    			"    }";

     	
    	
    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
    	
    	id=Util.encodeURL(id);
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ItemController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
   
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="IT_TEST01"; 
    	String agendaUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ItemController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="IT_TEST01_KO";    	
    	String item = "{\n" + 
    			"      \"id\":\""+id+"\",\n" + 
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C1\"\n" + 
    			"    }";

    	
    	String agendaUPDATE = new String (item.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ItemController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    
      
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="IT_TEST01";

    	id=Util.encodeURL(id);
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="IT_TEST01";
    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(ItemController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TNTEST0002";
    	    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	
    	
    	String id ="IT_TEST01";
    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ItemController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    	
    	
    	
    }
    
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TNTEST0002";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ItemController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ItemController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ItemController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ItemController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="IT_TEST01";    	
    	String item = "{\n" + 
    			"      \"id\":\""+id+"\",\n" + 
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C222222\"\n" + 
    			"    }";
    	   
    	
    	String agendaTransform = new String (item.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ItemController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	
    	String item = "{\n" +
    			"      \"description\": \"Artículos de ferretería\",\n" + 
    			"      \"hasClassification\": \"C222222\"\n" + 
    			"    }";

    	  
    	
    	String agendaTransform = new String (item.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ItemController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (Item.class).getDeclaredFields();
    	
    	boolean checkFields=true;
    	
    	for (Field f:declaredFields)
    	{
    		Rdf annotation=f.getAnnotation(Rdf.class);
    		if (annotation!=null)
    		{        		        		
    			if (!Util.validatorFieldRDF(f.getName(), annotation.propiedad()))
    			{
    				log.info(f.getName()+" vs. "+annotation.propiedad() );
    				checkFields=false;
    			}
    		}
    	}
    	
    	assertTrue(checkFields);
    	
    }
    
    @Test    
    public void test21_List_Sort_200() throws Exception {
    	String sort="?sort=-id,description";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ItemController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ItemController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,ItemController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
    
    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	
    	String id ="01-2020-IT1";
    	id=Util.encodeURL(id);
    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ItemController.LIST+"/"+id, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
}
