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

package org.ciudadesabiertas.agendamunicipal;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AgendaMEventoController;
import org.ciudadesabiertas.dataset.model.AgendaMEvento;
import org.ciudadesabiertas.dataset.utils.AgendaMunicipalConstants;
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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AgendaMEventoTest {
	
	private static final Logger log = LoggerFactory.getLogger(AgendaMEventoTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<AgendaMEvento> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<AgendaMEvento> items = dsService.basicQuery(AgendaMunicipalConstants.KEY,AgendaMEvento.class);
        
        assertTrue( items.size()>0 );


    }
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("agendaMEventoController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	String agendaADD = "{"    +			
    			"	  \"id\": \""+id+"\",\r\n" +
    			"      \"title\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n" +
    			"      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n" +
    			"      \"startDate\": \"2019-12-23T10:59:59\",\r\n" +
    			"      \"endDate\": \"2019-12-23T13:59:59\",\r\n" +
    			"      \"locationTitle\": \"Auditorio del Comité Olímpico Español\",\r\n" +
    			"      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" +
    			"      \"postalCode\": \"28100\",\r\n" +
    			"      \"portalId\": \"PORTAL000119\",\r\n" +
    			"      \"municipioId\": \"28006\",\r\n" +
    			"      \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"      \"barrioId\": \"28006011\",\r\n" +
    			"      \"barrioTitle\": \"Norte\",\r\n" +
    			"      \"distritoId\": \"2800601\",\r\n" +
    			"      \"distritoTitle\": \"Unico\",\r\n" +
    			"      \"equipamientoTitle\": \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"      \"equipamientoId\": \"EQ0044\",\r\n" +
    			"      \"superEventId\": \"AGM0001\",\r\n" +
    			"      \"reunionLobby\": false, \r\n" +
    			"      \"tipoEvento\": \"evento-con-medio-de-comunicacion\",\r\n" +
    			"      \"tipoAcceso\": \"privado\",\r\n" +
    			"      \"tipoSesion\": \"extraordinaria\",\r\n" +
    			"      \"canal\": \"presencial\",\r\n" +
    			"      \"description\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n" +
        	    "}";
    	    	    	
    	agendaADD = new String (agendaADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AgendaMEventoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String agendaADD = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AgendaMEventoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	String agendaADD = "{"    +			
    			"	  \"id\": \""+id+"\",\r\n" +
    			"      \"title\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n" +
    			"      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n" +
    			"      \"startDate\": \"2019-12-08T10:00:00\",\r\n" +
    			"      \"endDate\": \"2019-12-08T20:00:00\",\r\n" +
    			"      \"locationTitle\": \"Auditorio del Comité Olímpico Español\",\r\n" +
    			"      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" +
    			"      \"postalCode\": \"28100\",\r\n" +
    			"      \"portalId\": \"PORTAL000119\",\r\n" +
    			"      \"municipioId\": \"28006\",\r\n" +
    			"      \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"      \"barrioId\": \"28006011\",\r\n" +
    			"      \"barrioTitle\": \"Norte\",\r\n" +
    			"      \"distritoId\": \"2800601\",\r\n" +
    			"      \"distritoTitle\": \"Unico\",\r\n" +
    			"      \"equipamientoTitle\": \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"      \"equipamientoId\": \"EQ0044\",\r\n" +
    			"      \"superEventId\": \"AGM0001\",\r\n" +
    			"      \"reunionLobby\": false, \r\n" +
    			"      \"tipoEvento\": \"evento-con-medio-de-comunicacion\",\r\n" +
    			"      \"tipoAcceso\": \"privado\",\r\n" +
    			"      \"tipoSesion\": \"extraordinaria\",\r\n" +
    			"      \"canal\": \"presencial\",\r\n" +
    			"      \"description\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n" +
        	    "}";
    	
    	agendaADD = new String (agendaADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(AgendaMEventoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	  			
		String obj = "{" 
				+ "	     \"id\": \"" + id + "\",\r\n"
				+ "      \"title\": \"XXXActo de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n"
				+ "      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n"
				+ "      \"startDate\": \"2019-11-08T10:00:00\",\r\n"
				+ "      \"endDate\": \"2019-11-08T20:00:00\",\r\n"
				+ "      \"locationTitle\": \"XXAuditorio del Comité Olímpico Español\",\r\n"
				+ "      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" 
				+ "      \"postalCode\": \"28100\",\r\n"
				+ "      \"portalId\": \"PORTAL000110\",\r\n" 
				+ "      \"municipioId\": \"28006\",\r\n"
				+ "      \"municipioTitle\": \"Alcobendasx\",\r\n" 
				+ "      \"barrioId\": \"28006011\",\r\n"
				+ "      \"barrioTitle\": \"Nortexx\",\r\n" 
				+ "      \"distritoId\": \"2800601\",\r\n"
				+ "      \"distritoTitle\": \"Unicoxx\",\r\n"
				+ "      \"equipamientoTitle\": \"xxxTeatro Auditorio Ciudad de Alcobendas\",\r\n"
				+ "      \"equipamientoId\": \"EQ0041\",\r\n" 
				+ "      \"superEventId\": \"AGM0001\",\r\n"
				+ "      \"reunionLobby\": false, \r\n"
				+ "      \"tipoEvento\": \"xxevento-con-medio-de-comunicacion\",\r\n"
				+ "      \"tipoAcceso\": \"xxprivado\",\r\n" 
				+ "      \"tipoSesion\": \"extraordinaria\",\r\n"
				+ "      \"canal\": \"presencial\",\r\n"
				+ "      \"description\": \"xxxxxxActo de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n"
				+ "}";

    	String agendaUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String agendaUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_EQ0001";
    	String obj = "{" 
				+ "	     \"id\": \"" + id + "\",\r\n"
				+ "      \"title\": \"XXXActo de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n"
				+ "      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n"
				+ "      \"startDate\": \"2019-11-08T10:00:00\",\r\n"
				+ "      \"endDate\": \"2019-11-08T20:00:00\",\r\n"
				+ "      \"locationTitle\": \"XXAuditorio del Comité Olímpico Español\",\r\n"
				+ "      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" 
				+ "      \"postalCode\": \"28100\",\r\n"
				+ "      \"portalId\": \"PORTAL000110\",\r\n" 
				+ "      \"municipioId\": \"28006\",\r\n"
				+ "      \"municipioTitle\": \"Alcobendasx\",\r\n" 
				+ "      \"barrioId\": \"28006011\",\r\n"
				+ "      \"barrioTitle\": \"Nortexx\",\r\n" 
				+ "      \"distritoId\": \"2800601\",\r\n"
				+ "      \"distritoTitle\": \"Unicoxx\",\r\n"
				+ "      \"equipamientoTitle\": \"xxxTeatro Auditorio Ciudad de Alcobendas\",\r\n"
				+ "      \"equipamientoId\": \"EQ0041\",\r\n" 
				+ "      \"superEventId\": \"AGM0001\",\r\n"
				+ "      \"reunionLobby\": false, \r\n"
				+ "      \"tipoEvento\": \"xxevento-con-medio-de-comunicacion\",\r\n"
				+ "      \"tipoAcceso\": \"xxprivado\",\r\n" 
				+ "      \"tipoSesion\": \"extraordinaria\",\r\n"
				+ "      \"canal\": \"presencial\",\r\n"
				+ "      \"description\": \"xxxxxxActo de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n"
				+ "}";
    	
    	String agendaUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(AgendaMEventoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_AGENDA0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="TEST01_AGENDA0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_AGENDA0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AgendaMEventoController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AgendaMEventoController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(AgendaMEventoController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="TEST01_TRASFORM_AGENDA0001";
    	String obj = "{" 
				+ "	     \"id\": \"" + id + "\",\r\n"
				+ "      \"title\": \"XXXActo de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n"
				+ "      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n"
				+ "      \"startDate\": \"2019-11-08T10:00:00\",\r\n"
				+ "      \"endDate\": \"2019-11-08T20:00:00\",\r\n"
				+ "      \"locationTitle\": \"XXAuditorio del Comité Olímpico Español\",\r\n"
				+ "      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" 
				+ "      \"postalCode\": \"28100\",\r\n"
				+ "      \"portalId\": \"PORTAL000110\",\r\n" 
				+ "      \"municipioId\": \"28006\",\r\n"
				+ "      \"municipioTitle\": \"Alcobendasx\",\r\n" 
				+ "      \"barrioId\": \"28006011\",\r\n"
				+ "      \"barrioTitle\": \"Nortexx\",\r\n" 
				+ "      \"distritoId\": \"2800601\",\r\n"
				+ "      \"distritoTitle\": \"Unicoxx\",\r\n"
				+ "      \"equipamientoTitle\": \"xxxTeatro Auditorio Ciudad de Alcobendas\",\r\n"
				+ "      \"equipamientoId\": \"EQ0041\",\r\n" 
				+ "      \"superEventId\": \"AGM0001\",\r\n"
				+ "      \"reunionLobby\": false, \r\n"
				+ "      \"tipoEvento\": \"xxevento-con-medio-de-comunicacion\",\r\n"
				+ "      \"tipoAcceso\": \"xxprivado\",\r\n" 
				+ "      \"tipoSesion\": \"extraordinaria\",\r\n"
				+ "      \"canal\": \"presencial\",\r\n"
				+ "      \"description\": \"xxxxxxActo de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n"
				+ "}";
    	
    	String agendaTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(AgendaMEventoController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	String id ="TEST01_TRASFORM_AGENDA0001";
    	String obj = "{" 
				+ "	     \"id\": \"" + id + "\",\r\n"
				//+ "      \"title\": \"XXXActo de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n"
				+ "      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n"
				+ "      \"startDate\": \"2019-11-08T10:00:00\",\r\n"
				+ "      \"endDate\": \"2019-11-08T20:00:00\",\r\n"
				+ "      \"locationTitle\": \"XXAuditorio del Comité Olímpico Español\",\r\n"
				+ "      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" 
				+ "      \"postalCode\": \"28100\",\r\n"
				+ "      \"portalId\": \"PORTAL000110\",\r\n" 
				+ "      \"municipioId\": \"28006\",\r\n"
				+ "      \"municipioTitle\": \"Alcobendasx\",\r\n" 
				+ "      \"barrioId\": \"28006011\",\r\n"
				+ "      \"barrioTitle\": \"Nortexx\",\r\n" 
				+ "      \"distritoId\": \"2800601\",\r\n"
				+ "      \"distritoTitle\": \"Unicoxx\",\r\n"
				+ "      \"equipamientoTitle\": \"xxxTeatro Auditorio Ciudad de Alcobendas\",\r\n"
				+ "      \"equipamientoId\": \"EQ0041\",\r\n" 
				+ "      \"superEventId\": \"AGM0001\",\r\n"
				+ "      \"reunionLobby\": false, \r\n"
				+ "      \"tipoEvento\": \"xxevento-con-medio-de-comunicacion\",\r\n"
				+ "      \"tipoAcceso\": \"xxprivado\",\r\n" 
				+ "      \"tipoSesion\": \"extraordinaria\",\r\n"
				+ "      \"canal\": \"presencial\",\r\n"
				+ "      \"description\": \"xxxxxxActo de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n"
				+ "}";
    	
    	String agendaTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(AgendaMEventoController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(agendaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (AgendaMEvento.class).getDeclaredFields();
    	
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(AgendaMEventoController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(AgendaMEventoController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,AgendaMEventoController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }

    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(AgendaMEventoController.LIST+"/"+"AGM0001", mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test    
    public void test27_Delete_Conflict_FK() throws Exception {
    	String id ="AGM0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(AgendaMEventoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))
            
	        .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    
}
