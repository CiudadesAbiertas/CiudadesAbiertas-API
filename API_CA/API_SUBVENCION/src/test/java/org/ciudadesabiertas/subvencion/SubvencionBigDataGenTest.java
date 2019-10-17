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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.SubvencionController;
import org.ciudadesabiertas.dataset.model.Subvencion;
import org.ciudadesabiertas.dataset.utils.SubvencionConstants;
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
/*
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martinez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 * */
public class SubvencionBigDataGenTest {
	
	@Autowired
	private WebApplicationContext wac;

    @Autowired
    protected DatasetService<Subvencion> service;
    
    private static final Logger log = LoggerFactory.getLogger(SubvencionBigDataGenTest.class);

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

	@Test
    public void test() {
    	assertTrue(true);
    }
    
      /*
    public void test99_Add() throws Exception {
    	
    	int limit10=10;
    	int limit1K=1000;
    	int limit1M=1000000;
    	String mask="TestEstress201906_";
    	int limit=limit1M;
    	for (int i=1;i<=limit;i++)
    	{
    		log.info(i+" of "+limit);
    		
    		String subvencionADD = "{"    			
    		    +"\"id\": \""+(mask+i) +"\","
    		    +"\"title\": \"subvención nominativa al ateneo 2016\","
    		    +"\"areaId\": \"A05003340\","
    		    +"\"areaTitle\": \"área de gobierno de cultura y deportes\","
    		    +"\"municipioId\": 28079,"
    		    +"\"municipioTitle\": \"madrid\","
    		    +"\"adjudicatarioId\": \"g28679801 \","
    		    +"\"adjudicatarioTitle\": \"ateneo cientifico literario y artistico de madrid. .\","
    		    +"\"entidadFinanciadoraId\": \"A05003340\","
    		    +"\"entidadFinanciadoraTitle\": \"área de gobierno de cultura y deportes\","
    		    +"\"importe\": 750000,"
    		    +"\"fechaAdjudicacion\": \"2016-09-22T00:00:00\","
    		    +"\"lineaFinanciacion\": \"LINEA 1\","
    		    +"\"basesReguladoras\": \"https://www.bocm.es/boletin/cm_orden_bocm/2015/12/30/bocm-20151230-29.pdf\","
    		    +"\"tipoInstrumento\": \"subvención y entrega dineraria sin contraprestación\","
    		    +"\"aplicacionPresupuestaria\": \"2016-G/33401/48901\""
    	
    			+"}";    	
    	 
    		this.mockMvc.perform(MockMvcRequestBuilders.post(SubvencionController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionADD));
        	        
    	}
    	
    	assertTrue(true);
        
    }
    */
    
}
