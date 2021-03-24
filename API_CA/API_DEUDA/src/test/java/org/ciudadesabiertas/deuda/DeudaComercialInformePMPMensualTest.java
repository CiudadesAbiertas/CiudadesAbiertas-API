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
import org.ciudadesabiertas.dataset.controller.DeudaComercialInformePMPMensualController;
import org.ciudadesabiertas.dataset.model.DeudaComercialInformePMPMensual;
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
public class DeudaComercialInformePMPMensualTest {
	
	private static final Logger log = LoggerFactory.getLogger(DeudaComercialInformePMPMensualTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<DeudaComercialInformePMPMensual> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<DeudaComercialInformePMPMensual> items = dsService.basicQuery(DeudaConstants.KEY,DeudaComercialInformePMPMensual.class);
        
        assertTrue( items.size()>0 );


    }
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("deudaComercialInformePMPMensualController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	String dcInformePMPMensual = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
    			+ "      \"importeOperacionesPagada\": 0,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";
    	    	    	
    	dcInformePMPMensual = new String (dcInformePMPMensual.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensual))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String dcInformePMPMensual = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensual))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	String dcInformePMPMensual = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
    			+ "      \"importeOperacionesPagada\": 0,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";
    	
    	dcInformePMPMensual = new String (dcInformePMPMensual.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensual))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 11.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 11.51,\n"
    			+ "      \"importeOperacionesPagada\": 1,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 21.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 1,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";

    	String dcInformePMPMensualUpdate = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformePMPMensualController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensualUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String dcInformePMPMensualUpdate = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformePMPMensualController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensualUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="XXXX-XXXX-PMP-KO";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 11.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 11.51,\n"
    			+ "      \"importeOperacionesPagada\": 1,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 21.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 1,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";
    	
    	String dcInformePMPMensualUpdate = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformePMPMensualController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dcInformePMPMensualUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformePMPMensualController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="XXXX-XXXX-PMP-OK";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialInformePMPMensualController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialInformePMPMensualController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformePMPMensualController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformePMPMensualController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformePMPMensualController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	
    	String id ="XXXX-XXXX-PMP-TRANSFOR";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 11.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 11.51,\n"
    			+ "      \"importeOperacionesPagada\": 1,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 21.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 1,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";
    	
    	String transform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(transform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	//String id ="02-50-297-AO-005-TRANSFOR";
    	String obj = "{\r\n"
    			//+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoMensual\": 11.72,\n"
    			+ "      \"ratioOperacionesPagadas\": 11.51,\n"
    			+ "      \"importeOperacionesPagada\": 1,\n"
    			+ "      \"ratioOperacionesPendientesPago\": 21.23,\n"
    			+ "      \"importeOperacionesPendientesPago\": 1,\n"
    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
    			+ "      \"periodo\": \"2020-06\" \n"
    			+ "    }";
    	
    	String transform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(transform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (DeudaComercialInformePMPMensual.class).getDeclaredFields();
    	String[] fieldsToIngore = { "" };
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
    	String sort="?sort=-id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformePMPMensualController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialInformePMPMensualController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    


    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialInformePMPMensualController.LIST+"/"+"02-50-297-AO-005", mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    
}
