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

package org.ciudadesabiertas.core;

import javax.servlet.ServletContext;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.controller.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
public class CoreUserTest {
		
        
	
	@Autowired
	private WebApplicationContext wac;

        
    private MockMvc mockMvc;
    
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }
        
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("ciudadController"));
    }
    
    @Test    
    public void test01_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(UserController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test03_Add() throws Exception {
    	String id ="usernamePruebas";
    	String obj = "{"    			
    		    +"\"username\" : \""+id+"\","
    		    +"\"password\" : \""+id+"\","
    		    +"\"enabled\" : true,"
    		    +"\"userRole\" : [ "
    		    +	" {"
	    		+ 		"\"userRoleId\": 1,"
	            + 		"\"role\": \"ROLE_USER\""
				+	" },"
				+	" {"
				+ 		"\"userRoleId\": 2,"
	            + 		"\"role\": \"ROLE_ADMIN\""
				+	" }"
				+" ]"
        	  +"}";
    	    	    	
    	obj = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(UserController.ADD+".json")
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(obj))	        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test04_Update() throws Exception {
    	String id ="usernamePruebas";
    	String obj = "{"    			
    		    +"\"username\" : \""+id+"\","
    		    +"\"password\" : \""+id+"\","
    		    +"\"enabled\" : true,"
    		    +"\"userRole\" : [ "
    		    +	" {"
	    		+ 		"\"userRoleId\": 1,"
	            + 		"\"role\": \"ROLE_USER\""
				+	" },"
				+	" {"
				+ 		"\"userRoleId\": 2,"
	            + 		"\"role\": \"ROLE_ADMIN\""
				+	" }"
				+" ]"
        	  +"}";
    	    	    	
    	obj = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(UserController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(obj))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test05_changePassword() throws Exception {
    	String id ="usernamePruebas";
    	String password ="usernamePruebas";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(UserController.ADD+"/"+id+"/"+password)
        		.contentType(MediaType.APPLICATION_JSON))  	        	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test06_delete() throws Exception {
    	String id ="usernamePruebas";
    	String password ="usernamePruebas";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(UserController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))  	        	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
   
    
}