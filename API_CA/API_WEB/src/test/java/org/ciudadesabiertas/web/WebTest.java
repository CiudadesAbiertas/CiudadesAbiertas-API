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

package org.ciudadesabiertas.web;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.utils.StartVariables;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class WebTest {
	
	@Autowired
	private WebApplicationContext wac;
       
    private MockMvc mockMvc;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();       
    }


	
    private static final Logger log = LoggerFactory.getLogger(WebTest.class);
	
    
    //Constantes que se utilizan para los tests de datos
    private static final String DIMENSION_ID = "{dimensionId}";	
    
    @Test
    public void test_List_URIs_200() throws Exception {
    	
    	
    	ArrayList<Integer> responseStatus=new ArrayList<Integer>();
    	
    	if (StartVariables.listURIs.size()>0)
    	{
    	    
	    	for (String URL:StartVariables.listURIs)
	    	{
	    		URL=URL.replace(DIMENSION_ID,"");
	    		
	    	    int status = this.mockMvc.perform(MockMvcRequestBuilders.get(URL+".html")).andReturn().getResponse().getStatus();
	    	    if (status!=200)
	    	    {
	    	    	log.error(status+" "+URL);
	    	    }
	    		responseStatus.add(status);
	    	}
	    	
	    	boolean checkAll=true;
	    	for (Integer i:responseStatus)
	    	{	
	    		if (i.intValue()!=200)
	    		{
	    			checkAll=false;
	    			break;
	    		}	
	    	}
	    	
	    	assertTrue(checkAll);
    	
    	}else {
    		assertTrue(false);
    	}
        
    	
    	
    	
    }
    
    
   
}
