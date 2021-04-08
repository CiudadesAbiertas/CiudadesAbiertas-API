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

package org.ciudadesabiertas.subvencion;

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.SubvencionConvocatoriaController;
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
public class SubvencionConvocatoriaTestFK {
	
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
    	  String jsonItem = "{\r\n"
    	  		+ "      \"id\": \"TESTSUB\",\r\n"
    	  		+ "      \"title\": \"CONVOCATORIA PREMIOS ARGANZUELA XXXII EDICION PINTURA Y X EDICIÓN FOTOGRAFÍA\",\r\n"
    	  		+ "      \"basesReguladoras\": \"https://www.bocm.es/boletin/CM_Orden_BOCM/2013/11/22/BOCM-20131122-34.PDF\",\r\n"
    	  		+ "      \"tipoInstrumento\": \"PRÉSTAMOS\",\r\n"
    	  		+ "      \"nominativa\": false,\r\n"
    	  		+ "      \"tipoProcedimiento\": \"subvencion-directa\",\r\n"
    	  		+ "      \"name\": \"Nombre del proyecto al que está asociada la subvención SUB1\",\r\n"
    	  		+ "      \"objeto\": \"Finalidad de la subvención SUB1\",\r\n"
    	  		+ "      \"importeTotalConcedido\": 2000,\r\n"
    	  		+ "      \"fechaAcuerdo\": \"2017-10-26T00:00:00\",\r\n"
    	  		+ "      \"clasificacionPrograma\": [\"926\"],\r\n"
    	  		+ "      \"clasificacionEconomicaGasto\": [\"13002\"],\r\n"
    	  		+ "      \"instrumentaId\": \"CONV001\",\r\n"
    	  		+ "      \"instrumentaTitle\": \"CONVENIO PRUEBAS 1\",\r\n"
    	  		+ "      \"tieneTematica\": \"deporte\",\r\n"
    	  		+ "      \"gestionadoPorOrganization\": false,\r\n"
    	  		+ "      \"organizationId\": \"A15003355\",\r\n"
    	  		+ "      \"gestionadoPorDistrito\": true,\r\n"
    	  		+ "      \"distritoId\": \"2800307\",\r\n"
    	  		+ "      \"distritoTitle\": \"Distrito 7\",\r\n"
    	  		+ "      \"areaId\": \"A05003315\",\r\n"
    	  		+ "      \"servicioId\": \"A05003255\",\r\n"
    	  		+ "      \"entidadFinanciadoraId\": \"A05004355\"\r\n"
    	  		+ "    }";  	    	    	
    	  jsonItem = new String (jsonItem.getBytes(),"UTF-8");
	     	 
	         this.mockMvc.perform(MockMvcRequestBuilders.post(SubvencionConvocatoriaController.ADD)
	         		.contentType(MediaType.APPLICATION_JSON)
	     	        .content(jsonItem)).andExpect(MockMvcResultMatchers.status().isConflict());
    	}
    	    	    	
    	
    }
    
    @Test
    public void test_02_update() throws Exception {
    	
    	if (activeFK==false)
    	{
    		assertFalse(activeFK);    		
    	}
    	else
    	{    	
    	  String jsonItem = " {\r\n"
    	  	+ "      \"id\": \"SUB1\",\r\n"
    	  	+ "      \"title\": \"CONVOCATORIA PREMIOS ARGANZUELA XXXII EDICION PINTURA Y X EDICIÓN FOTOGRAFÍA\",\r\n"
    	  	+ "      \"basesReguladoras\": \"https://www.bocm.es/boletin/CM_Orden_BOCM/2013/11/22/BOCM-20131122-34.PDF\",\r\n"
    	  	+ "      \"tipoInstrumento\": \"PRÉSTAMOS\",\r\n"
    	  	+ "      \"nominativa\": false,\r\n"
    	  	+ "      \"tipoProcedimiento\": \"subvencion-directa\",\r\n"
    	  	+ "      \"name\": \"Nombre del proyecto al que está asociada la subvención SUB1\",\r\n"
    	  	+ "      \"objeto\": \"Finalidad de la subvención SUB1\",\r\n"
    	  	+ "      \"importeTotalConcedido\": 2000,\r\n"
    	  	+ "      \"fechaAcuerdo\": \"2017-10-26T00:00:00\",\r\n"
    	  	+ "      \"clasificacionPrograma\": [\"926\"],\r\n"
    	  	+ "      \"clasificacionEconomicaGasto\": [\"13002\"],\r\n"
    	  	+ "      \"instrumentaId\": \"CONV001\",\r\n"
    	  	+ "      \"instrumentaTitle\": \"CONVENIO PRUEBAS 1\",\r\n"
    	  	+ "      \"tieneTematica\": \"deporte\",\r\n"
    	  	+ "      \"gestionadoPorOrganization\": false,\r\n"
    	  	+ "      \"organizationId\": \"A05023355\",\r\n"
    	  	+ "      \"gestionadoPorDistrito\": true,\r\n"
    	  	+ "      \"distritoId\": \"2800307\",\r\n"
    	  	+ "      \"distritoTitle\": \"Distrito 7\",\r\n"
    	  	+ "      \"areaId\": \"A05003351\",\r\n"
    	  	+ "      \"servicioId\": \"A05002351\",\r\n"
    	  	+ "      \"entidadFinanciadoraId\": \"A05003155\"\r\n"
    	  	+ "    }";  	    	    	  	    	    	
      	  jsonItem = new String (jsonItem.getBytes(),"UTF-8");	    	
	   
	     	 
	     	 this.mockMvc.perform(MockMvcRequestBuilders.put(SubvencionConvocatoriaController.ADD+"/"+"SUB1")
	         		.contentType(MediaType.APPLICATION_JSON)
	     	        .content(jsonItem)).andExpect(MockMvcResultMatchers.status().isConflict());
    	}
    	    	    	
    	
    }
    
    
     
    
    
    
   
    
}
