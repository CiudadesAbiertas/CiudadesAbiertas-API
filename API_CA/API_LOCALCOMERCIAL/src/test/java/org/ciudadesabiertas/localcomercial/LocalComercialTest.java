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

package org.ciudadesabiertas.localcomercial;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.LicenciaController;
import org.ciudadesabiertas.dataset.controller.LocalComercialController;
import org.ciudadesabiertas.dataset.model.LocalComercial;
import org.ciudadesabiertas.dataset.utils.LocalComercialConstants;
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
public class LocalComercialTest {
	
	private static final Logger log = LoggerFactory.getLogger(LocalComercialTest.class);
	
	@Autowired
	private WebApplicationContext wac;

    @Autowired
    private DatasetService<LocalComercial> service;    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<LocalComercial> items = service.basicQuery(LocalComercialConstants.KEY,LocalComercial.class);
        
        assertTrue( items.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("localComercialController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String itemAdd = "{"    			
    			+"\"id\" : \"TEST01_LC0001\","
    			+"\"title\" : \"LATAGLIATELLA2\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"tieneTerraza\" : \"4769\","    
    			+"\"tieneLicenciaApertura\" : \"280030153/106-2010-10782\","
    			+"\"agrupacionComercial\" : \"99000213\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\""
        	  +"}";
    	
    	itemAdd = new String (itemAdd.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String itemAdd = "{"     	
    			+"}";
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String itemAdd = "{"    			
    			+"\"id\" : \"TEST01_LC0001\","
    			+"\"title\" : \"LATAGLIATELLA2\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneLicenciaApertura\" : \"500/2017/00190\","
    			+"\"tieneTerraza\" : \"4769\","
    			+"\"agrupacionComercial\" : \"99000213\""    			 
        	  +"}";
    	
    	itemAdd = new String (itemAdd.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="TEST01_LC0001";    	
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
    			+"\"title\" : \"LATAGLIATELLA3\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneTerraza\" : \"4769\","    
    			+"\"tieneLicenciaApertura\" : \"280030153/106-2010-10782\","
    			+"\"agrupacionComercial\" : \"99000213\""    			 
        	  +"}";
    	
    	String LocalComercialUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String LocalComercialUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_LC0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
    			+"\"title\" : \"LATAGLIATELLA3\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneLicenciaApertura\" : \"500/2017/00190\","
    			+"\"tieneTerraza\" : \"4769\","
    			+"\"agrupacionComercial\" : \"99000213\""    			 
        	  +"}";
    	
    	String LocalComercialUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="TEST01_LC0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="TEST01_LC0001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(LocalComercialController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_LC0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="TEST01_LC0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_LC0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(LocalComercialController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(LocalComercialController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(LocalComercialController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="TEST01_TRASFORM_LC0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
    			+"\"title\" : \"LATAGLIATELLA3\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneLicenciaApertura\" : \"500/2017/00190\","
    			+"\"tieneTerraza\" : \"4769\","
    			+"\"agrupacionComercial\" : \"99000213\""    			 
        	  +"}";
    	
    	String LocalComercialTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	String id ="TEST01_TRASFORM_LC0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","    		
    			 /*+"\"title\" : \"Ambiental\","*/
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"latitud\" : 0,"
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneLicenciaApertura\" : \"500/2017/00190\","
    			+"\"tieneTerraza\" : \"4769\","
    			+"\"agrupacionComercial\" : \"99000213\""    			 
        	  +"}";
    	
    	String LocalComercialTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (LocalComercial.class).getDeclaredFields();
    	
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
    public void test21_Add_Checkin_Wrong_Agrupacion_409() throws Exception {
    	String itemAdd = "{"    			
    			+"\"id\" : \"TEST01_LC0001\","
    			+"\"title\" : \"LATAGLIATELLA2\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"agrupacionComercial\" : \"11111\""    			 
        	  +"}";
    	
    	itemAdd = new String (itemAdd.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test21_Add_Checkin_Wrong_Terraza_409() throws Exception {
    	String itemAdd = "{"    			
    			+"\"id\" : \"TEST01_LC0001\","
    			+"\"title\" : \"LATAGLIATELLA2\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","    			
    			+"\"tieneTerraza\" : \"1111\""    			    			 
        	  +"}";
    	
    	itemAdd = new String (itemAdd.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test21_Add_Checkin_Wrong_Licencia_409() throws Exception {
    	String itemAdd = "{"    			
    			+"\"id\" : \"TEST01_LC0001\","
    			+"\"title\" : \"LATAGLIATELLA2\","
    			+"\"description\" : \"Descripcion LATAGLIATELLA : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \","
    			+"\"municipioId\" : \"28079\","
    			+"\"municipioTitle\" : \"Madrid\","
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"postalCode\" : \"28039\","
    			+"\"barrio\" : \"Cuatro Caminos\","
    			+"\"distrito\" : \"Tetuan\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"telephone\" : \"9199999913\","
    			+"\"url\" : \"http://api.ciudadesabiertas.org/id=280030153\","
    			+"\"tipoActividadEconomica\" : \"56\","
    			+"\"nombreComercial\" : \"Nombre Comercial LATAGLIATELLA\","
    			+"\"rotulo\" : \"Rotulo LATAGLIATELLA\","
    			+"\"aforo\" : 50,"
    			+"\"tipoSituacion\" : \"Abierto\","
    			+"\"tipoAcceso\" : \"Agrupado\","
    			+"\"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\","
    			+"\"tieneLicenciaApertura\" : \"500-2017F00190\""    					    			 
        	  +"}";
    	
    	itemAdd = new String (itemAdd.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(LocalComercialController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemAdd))	
        	
        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test22_Update_WRONG_Licencia_409() throws Exception {
    	String id ="270017603";    	
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","    			
    			+"\"title\" : \"RESTAURANTE ASADOR EL TURIA\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"   
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"tieneLicenciaApertura\" : \"106-0100F03177\"" 			 
        	  +"}";
    	
    	String LocalComercialUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test23_Update_WRONG_Agrupacion_409() throws Exception {
    	String id ="270017603";    	
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","    			
    			+"\"title\" : \"RESTAURANTE ASADOR EL TURIA\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999," 
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"agrupacionComercial\" : \"P000001\"" 			 
        	  +"}";
    	
    	String LocalComercialUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test24_Update_WRONG_Terraza_409() throws Exception {
    	String id ="270017603";    	
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","    			
    			+"\"title\" : \"RESTAURANTE ASADOR EL TURIA\","
    			+"\"xETRS89\" : 441176.61,"
    			+"\"yETRS89\" : 4480303.53999,"
    			+"\"streetAddress\" : \"Avenida General Peron Num 40\","
    			+"\"tieneTerraza\" : \"TFALSA\"" 			 
        	  +"}";
    	
    	String LocalComercialUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(LocalComercialUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test25_List_Sort_200() throws Exception {
    	String sort="?sort=-id,title";
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test26_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test27_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
   	public void test28_Busqueda_geo() throws Exception
   	{	
   		String sort="?xETRS89=440929.42&yETRS89=4477579.19&meters=50";
           this.mockMvc.perform(MockMvcRequestBuilders.get(LocalComercialController.GEO_LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
   	}
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	
    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(LocalComercialController.LIST, mockMvc);
     	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,LocalComercialController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
}
