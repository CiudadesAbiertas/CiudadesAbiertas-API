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

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.LocalComercialController;
import org.ciudadesabiertas.utils.StartVariables;
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
public class LocalComercialTestFK {
	

	
	private static boolean activeFK = StartVariables.activeFK;
	
	@Autowired
	private WebApplicationContext wac;



    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();       
    }

   
   
    
       
    @Test    
    public void test21_Add_Checkin_Wrong_Agrupacion_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    }
    
    @Test    
    public void test21_Add_Checkin_Wrong_Terraza_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    }
    
    @Test    
    public void test21_Add_Checkin_Wrong_Licencia_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    }
    
    @Test    
    public void test22_Update_WRONG_Licencia_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    }
    
    @Test    
    public void test23_Update_WRONG_Agrupacion_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    	
    	 
			this.mockMvc
					.perform(MockMvcRequestBuilders.put(LocalComercialController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(LocalComercialUPDATE))
					.andExpect(MockMvcResultMatchers.status().isConflict());

    	}
        
    }
    
    @Test    
    public void test24_Update_WRONG_Terraza_409() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{  
    	
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
    
    }
}
