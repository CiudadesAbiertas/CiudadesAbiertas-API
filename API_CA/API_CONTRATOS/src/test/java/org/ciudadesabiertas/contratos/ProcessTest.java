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
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;
import org.ciudadesabiertas.dataset.model.Process;
import org.ciudadesabiertas.dataset.utils.ContratosConstants;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ProcessController;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.StartVariables;
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
public class ProcessTest {
	
	private static final Logger log = LoggerFactory.getLogger(ProcessTest.class);
	
	private static boolean activeFK = StartVariables.activeFK;
	
	@Autowired
	private WebApplicationContext wac;

    @Autowired
    private DatasetService<Process> service;
        
    private MockMvc mockMvc;
     
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();        
    }
    
    @Test
    public void test01_Service() throws DAOException {
	
        final List<Process> list = service.basicQuery(ContratosConstants.KEY,Process.class);
        
        assertTrue( list.size()>0 );
    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("processController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	
    	String id ="300-2018-000000524";
    	String identifier ="300/2018/000000524";
     	String item = " {\n" + 
     			"      \"id\":\"" +id+"\",\n" +
     			"      \"identifier\":\"" +identifier+"\",\n" + 
     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132\",\n" + 
     			"      \"hasTender\": \"0013496-19-TN1\",\n" + 
     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
     			"    }";   	
     	
     	
     	    	    	
     	item = new String (item.getBytes(),"UTF-8");	
     	
     	 
         this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.ADD)
         		.contentType(MediaType.APPLICATION_JSON)
     	        .content(item))	
         
         .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    	
    	
    	
    	    	    	
    	
    }
    
    
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String item = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(item))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
   
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="300-2018-000000524";
    	String identifier ="300/2018/000000524";
     	String item = " {\n" + 
     			"      \"id\":\"" +id+"\",\n" +
     			"      \"identifier\":\"" +identifier+"\",\n" + 
     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Policia del Ayuntamiento de Madrid.\",\n" + 
     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132\",\n" + 
     			"      \"hasTender\": \"0013496-19-TN1\",\n" + 
     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
     			"    }";   	
    	
    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
    	
    	id=Util.encodeURL(id);
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
   
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String agendaUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="300/2018/000000523";
     	String item = " {\n" + 
     			"      \"id\":\"" +id+"\",\n" + 
     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132\",\n" + 
     			"      \"hasTender\": \"0013496-19-TN1\",\n" + 
     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
     			"    }";   
    	
    	String agendaUPDATE = new String (item.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    
      
   
    
    
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="300-2018-000000524";

    	id=Util.encodeURL(id);
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="300-2018-000000524";
    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(ProcessController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="300-2018-000000524_ko";
    	    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	
    	
    	String id ="300-2018-000000524";
    	
    	id=Util.encodeURL(id);
    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    	
    	
    	
    }
    
    /*
    @Test    
    public void test13_Delete_409_fk() throws Exception {
    	String id ="300-2018-000000524";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isConflict());
    } 
 	*/
    
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="300/2018/000000523";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ProcessController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ProcessController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ProcessController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ProcessController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="300-2018-000000524";
    	String identifier ="300/2018/000000524";
     	String item = " {\n" + 
     			"      \"id\":\"" +id+"\",\n" +
     			"      \"identifier\":\"" +identifier+"\",\n" + 
     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132\",\n" + 
     			"      \"hasTender\": \"0013496-19-TN1\",\n" + 
     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
     			"    }";   
    	
    	String agendaTransform = new String (item.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	
     	String item = " {\n" +
     			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132\",\n" + 
     			"      \"hasTender\": \"0013496-19-TN1\",\n" + 
     			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
     			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
     			"    }";   
    	
    	String agendaTransform = new String (item.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ProcessController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (Process.class).getDeclaredFields();
    	
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
    	String sort="?sort=-id,title";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ProcessController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ProcessController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,ProcessController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
    
    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	
    	String id ="0013496-19";
    	id=Util.encodeURL(id);
    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ProcessController.LIST+"/"+id, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    @Test    
    public void test28_Add_FK_NO_OK_409() throws Exception {
    	
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
	     			"      \"isBuyerFor\": \"L015029736cfa4d7559501497497a0db85667a132_KO\",\n" + 
	     			"      \"hasTender\": \"0013496-19-TN1_KO\",\n" + 
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
    
    
   
    
    
}
