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

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ProcessController;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class ProcessTestFK {
	
	private static boolean activeFK = StartVariables.activeFK;
	
	@Autowired
	private WebApplicationContext wac;
        
    private MockMvc mockMvc;
    
    @Before
    public void setup() throws Exception {    	
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    	
    }
        
    @Test
    public void test_01_add() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{    	
	    	String id ="300-2018-000000524";
	    	String identifier ="300/2018/000000524";
	     	String item = " {\n" + 
	     			"      \"id\":\"" +id+"\",\n" +
	     			"      \"identifier\":\"" +identifier+"\",\n" + 
	     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
	     			"      \"isBuyerFor\": \"LA0007386\",\n" + 
	     			"      \"hasTender\": \"TN1\",\n" + 
	     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
	     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
	     			"    }";	     	    	    	
	     	item = new String (item.getBytes(),"UTF-8");
	     	 
	         this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.ADD)
	         		.contentType(MediaType.APPLICATION_JSON)
	     	        .content(item)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    	}
    	    	    	
    	
    }
    
    @Test
    public void test_99_delete() throws Exception 
    {	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{
	    	String id ="300-2018-000000524";    	
	    	id=Util.encodeURL(id);    	
	        this.mockMvc.perform(MockMvcRequestBuilders.delete(ProcessController.ADD+"/"+id).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    	}
    }
    
    
    @Test    
    public void test_02_add_FK_ERROR() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{     
	    	String id ="300-2018-000000524";
	    	String identifier ="300/2018/000000524";
	     	String item = " {\n" + 
	     			"      \"id\":\"" +id+"\",\n" +
	     			"      \"identifier\":\"" +identifier+"\",\n" + 
	     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
	     			"      \"isBuyerFor\": \"LA0007386_KO\",\n" + 
	     			"      \"hasTender\": \"TN1_KO\",\n" + 
	     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
	     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
	     			"    }";   
	    	
	    	item = new String (item.getBytes(),"UTF-8");	
	    	
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.ADD)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(item))		        	
	        			.andExpect(MockMvcResultMatchers.status().isConflict());
    	}
    }
       
    
   
   
    
    @Test    
    public void test_03_update_FK_ERROR() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{ 
    	   	String id ="300-2018-000000524";
		   	String identifier ="300/2018/000000524";
		   	String item = " {\n" + 
		     			"      \"id\":\"" +id+"\",\n" + 
		     			"      \"identifier\":\"" +identifier+"\",\n" + 
		     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
		     			"      \"isBuyerFor\": \"LA0007386_KO\",\n" + 
		     			"      \"hasTender\": \"TN2_KO\",\n" + 
		     			"      \"url\": \"https://www.localidata.com\",\n" + 
		     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
		     			"    }";   
		    	
		   	String itemUPDATE = new String (item.getBytes(),"UTF-8");
		    id=Util.encodeURL(id);
		    	 
		    this.mockMvc.perform(MockMvcRequestBuilders.put(ProcessController.ADD+"/"+id)
		        		.contentType(MediaType.APPLICATION_JSON)
		    	        .content(itemUPDATE))
			        .andExpect(MockMvcResultMatchers.status().isConflict());
    	}
    
    
    
    
    }
   
    
    
    
   
    
}
