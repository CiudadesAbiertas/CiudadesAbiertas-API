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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.DeudaComercialProperIntervalController;
import org.ciudadesabiertas.dataset.model.DeudaComercialProperInterval;
import org.ciudadesabiertas.dataset.utils.DeudaConstants;
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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeudaComercialProperIntervalTest {
	
	private static final Logger log = LoggerFactory.getLogger(DeudaComercialProperIntervalTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<DeudaComercialProperInterval> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<DeudaComercialProperInterval> items = dsService.basicQuery(DeudaConstants.KEY,DeudaComercialProperInterval.class);
        
        assertTrue( items.size()>0 );


    }
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("deudaComercialProperIntervalController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	String traficoADD = "{"   			
    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2020-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2020-03-31T23:01:00\"\r\n"
    			+ "}";
    	    	    	
    	traficoADD = new String (traficoADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialProperIntervalController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String traficoADD = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialProperIntervalController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	String traficoADD = "{"   			
    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2020-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2020-03-31T23:01:00\"\r\n"
    			+ "}";
    	
    	traficoADD = new String (traficoADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialProperIntervalController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	  			
    	String obj = "{"   			
    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2021-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2021-03-31T23:01:00\"\r\n"
    			+ "}";

    	String traficoUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialProperIntervalController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String traficoUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialProperIntervalController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_EQ0001";
    	String obj = "{"   			
    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2021-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2021-03-31T23:01:00\"\r\n"
    			+ "}";
    	
    	String traficoUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialProperIntervalController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialProperIntervalController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="TEST01_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialProperIntervalController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialProperIntervalController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialProperIntervalController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialProperIntervalController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialProperIntervalController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="TEST01_TRASFORM_TRAPROINT01";
    	
    	String obj = "{"   			
    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2020-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2020-03-31T23:01:00\"\r\n"
    			+ "}";
    	
    	String traficoTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialProperIntervalController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
//    	String id ="TEST01_TRASFORM_BICI0001";
    	
    	String obj = "{"   			
//    			+ "	  \"id\": \""+id+"\",\r\n" 
    			+ "   \"hasBeginning\": \"2020-03-31T23:00:00\",\r\n"
    			+ "   \"hasEnd\": \"2020-03-31T23:01:00\"\r\n"
    			+ "}";
    	
    	String traficoTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialProperIntervalController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(traficoTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (DeudaComercialProperInterval.class).getDeclaredFields();
    	String[] fieldsToIngore = { "hasBeginning","hasEnd" };
    	ArrayList<String> ignoreList = new ArrayList<String>(Arrays.asList(fieldsToIngore));
    	
    	boolean checkFields=true;
    	
    	for (Field f:declaredFields)
    	{
    		if(!ignoreList.contains(f.getName())) {
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
    	}
    	
    	assertTrue(checkFields);
    	
    }
    
    @Test    
    public void test21_List_Sort_200() throws Exception {
    	String sort="?sort=-id,hasBeginning";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialProperIntervalController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialProperIntervalController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,DeudaComercialProperIntervalController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }

    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialProperIntervalController.LIST+"/"+"2020-06", mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    
}
