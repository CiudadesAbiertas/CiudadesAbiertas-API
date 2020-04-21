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

package org.ciudadesabiertas.bicicletapublica;

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.BicicletaPublicaTrayectoController;
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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BicicletaPublicaTrayectoTestFK {
	
	@Autowired
	private WebApplicationContext wac;
        
    private MockMvc mockMvc;
    
    private static boolean activeFK = StartVariables.activeFK;
     
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();        
    }
    
       
    @Test    
    public void test01_Add() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {
    	
	    	String id ="TEST01_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    	    	    	
		}
    }
    

    @Test    
    public void test02_Add_ERROR_FK1() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {
    	
	    	String id ="TEST02_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02_KO\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
		}
    	
    }
    
    @Test    
    public void test02_Add_ERROR_FK2() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST03_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06_KO\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
    	    	    	
		}
    }
    
    @Test    
    public void test03_Add_ERROR_FK3() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST04_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02_KO\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
    	    	    	
		}
    }
    
    @Test    
    public void test04_Add_ERROR_FK4() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST05_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01_KO\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
    	    	    	
		}
    }
    
    @Test    
    public void test05_Add_ERROR_FK5() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST06_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06_KO\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
    	    	    	
		}
    }
    
    @Test    
    public void test06_Add_ERROR_FK6() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST07_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01_KO\"\r\n"
	    			+ "}";
	
			item = new String(item.getBytes(), "UTF-8");
	
			this.mockMvc.perform(
					MockMvcRequestBuilders.post(BicicletaPublicaTrayectoController.ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(item))
						.andExpect(MockMvcResultMatchers.status().isConflict());
    	    	    	
		}
    }
    
    @Test    
    public void test07_Update_ERROR_FK1() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {
    	
	    	String id ="TEST01_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02_KO\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";   	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
        
		}
    }
    
    @Test    
    public void test08_Update_ERROR_FK2() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST02_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06_KO\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
	     	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
		}
    }
    
    @Test    
    public void test09_Update_ERROR_FK3() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST03_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02_KO\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
	     	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
		}
    }
    
    @Test    
    public void test10_Update_ERROR_FK4() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST04_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01_KO\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
	     	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
		}
    }
    
    @Test    
    public void test11_Update_ERROR_FK5() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST05_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06_KO\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
	    			+ "}";
	
	     	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
		}
    }
    
    @Test    
    public void test12_Update_ERROR_FK6() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TEST06_TRA0001";    	
	    	String item = " {\n"
	    			+ "    \"id\": \""+id+"\",\n" 
	    			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
	    			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
	    			+ "   \"usuarioId\": \"USU02\",\r\n"
	    			+ "   \"bicicletaId\": \"BIC06\",\r\n"
	    			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
	    			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
	    			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
	    			+ "   \"anclajeDestinoId\": \"ANC01_KO\"\r\n"
	    			+ "}";
	
	     	
	    	
	    	String itemUPDATE = new String (item.getBytes(),"UTF-8");	
	    	
	    	id=Util.encodeURL(id);
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.put(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(itemUPDATE))	
	            
		        .andExpect(MockMvcResultMatchers.status().isConflict());
		}
    }
    
    @Test    
    public void test13_Delete_ERROR_FK1() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	
	    	String id ="TRA04";	    	
	    	id=Util.encodeURL(id);	    	
	        this.mockMvc.perform(MockMvcRequestBuilders.delete(BicicletaPublicaTrayectoController.ADD+"/"+id)
	        		.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(MockMvcResultMatchers.status().isConflict());    	
		}
    	
    }
    
//    @Test    
//    public void test05_Delete_ERROR_FK1() throws Exception {
//    	
//    	if (activeFK == false) {
//			assertFalse(activeFK);
//		} else {    	
//	    	String id ="LT1";	    	
//	    	id=Util.encodeURL(id);	    	
//	        this.mockMvc.perform(MockMvcRequestBuilders.delete(BicicletaPublicaTrayectoController.ADD+"/"+id)
//	        		.contentType(MediaType.APPLICATION_JSON))
//		        .andExpect(MockMvcResultMatchers.status().isConflict());    	
//		}
//    	
//    }
    
    
    
   
    
    @Test    
    public void test99_Delete() throws Exception {
    	
    	if (activeFK == false) {
			assertFalse(activeFK);
		} else {
			String id ="TEST01_TRA0001";    	
			id=Util.encodeURL(id);    	
			this.mockMvc.perform(MockMvcRequestBuilders.delete(BicicletaPublicaTrayectoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());    	
		}
    	
    }
    
    
  
    
}
