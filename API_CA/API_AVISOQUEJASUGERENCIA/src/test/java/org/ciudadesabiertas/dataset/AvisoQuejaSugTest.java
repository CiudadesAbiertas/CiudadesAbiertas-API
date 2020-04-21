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

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AvisoQuejaSugController;
import org.ciudadesabiertas.dataset.model.AvisoQuejaSug;
import org.ciudadesabiertas.dataset.utils.AvisoQuejaSugConstants;
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
public class AvisoQuejaSugTest {
	
	private static final Logger log = LoggerFactory.getLogger(AvisoQuejaSugTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<AvisoQuejaSug> dsService;
        
    private MockMvc mockMvc;
    
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<AvisoQuejaSug> users = dsService.basicQuery(AvisoQuejaSugConstants.KEY,AvisoQuejaSug.class);
        
        assertTrue( users.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("avisoQuejaSugController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="TEST01_AQS0001";
    	String aqsADD = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E\","
    		    +"\"identifier\" : \"0-52\","
    		    +"\"stat\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    	
    	aqsADD = new String (aqsADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AvisoQuejaSugController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String aqsADD = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AvisoQuejaSugController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="TEST01_AQS0001";
    	String aqsADD = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E\","
    		    +"\"identifier\" : \"0-52\","
    		    +"\"stat\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    	
    	aqsADD = new String (aqsADD.getBytes(),"UTF-8");		
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AvisoQuejaSugController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="TEST01_AQS0001";
    	String aqsUpdate = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"XXXXX AVISO-R RECOGIDA ANIMALES MUERTOS E XXXXX\","
    		    +"\"identifier\" : \"0-52\","
    		    +"\"stat\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"XXXX RECOGIDA DE ANIMALES MUERTOS XXXX\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    	
    	aqsUpdate = new String (aqsUpdate.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AvisoQuejaSugController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String aqsUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AvisoQuejaSugController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {    	
    	String id ="TEST01_NOOK_AQS0001";
    	String aqsUpdate = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"XXXXX AVISO-R RECOGIDA ANIMALES MUERTOS E XXXXX\","
    		    +"\"identifier\" : \"0-52\","
    		    +"\"stat\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"XXXX RECOGIDA DE ANIMALES MUERTOS XXXX\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    	
    	aqsUpdate = new String (aqsUpdate.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AvisoQuejaSugController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="TEST01_AQS0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="TEST01_AQS0001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(AvisoQuejaSugController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_AQS0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="TEST01_AQS0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(AvisoQuejaSugController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_AGENDA0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(AvisoQuejaSugController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AvisoQuejaSugController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AvisoQuejaSugController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AvisoQuejaSugController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="TEST01_TRASFORM_AGENDA0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E\","
    		    +"\"identifier\" : \"0-52\","
    		    +"\"stat\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    
    	
    	String aqsTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(AvisoQuejaSugController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	String id ="TEST01_TRASFORM_AGENDA0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
    		    +"\"cityName\" : \"Madrid\","
    		    +"\"title\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E\","
    		    //+"\"identifier\" : \"0-52\","
    		    +"\"status\" : \"abierto\","
    		    +"\"statusNotes\" : \"abierto\","
    		    +"\"openDate\" : \"2018-01-01T00:59:30\","
    		    +"\"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"source\" : \"PRESENCIAL/TELEFONICOES\","
    		    +"\"address\" : \"CALLE MAQUEDA 1 --\","
    		    +"\"postalCode\" : \"28024\","
    		    +"\"barrio\" : \"Barrio test\","
        	    +"\"distrito\" : \"Unico\","
    		    +"\"xETRS89\" : 435189.14,"
    		    +"\"yETRS89\" : 4472097.38,"
    		    +"\"typeName\" : \"RECOGIDA DE ANIMALES MUERTOS\","
    		    +"\"typeCode\" : \"3547\""
        	  +"}";
    	    	    
    	
    	String aqsTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(AvisoQuejaSugController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(aqsTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (AvisoQuejaSug.class).getDeclaredFields();
    	
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
    	String sort="?sort=-id,identifier";
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test
   	public void test23_Busqueda_geo() throws Exception
   	{	
   		String sort="?xETRS89=439908&yETRS89=4466975&meters=50";
           this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.GEO_LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
   	}
   
    
    @Test    
    public void test24_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(AvisoQuejaSugController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(AvisoQuejaSugController.LIST, true, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,AvisoQuejaSugController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(AvisoQuejaSugController.LIST+"/"+"AQSA0001", true, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
}
