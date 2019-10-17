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
public class SubvencionTest {
	
	@Autowired
	private WebApplicationContext wac;

    @Autowired
    protected DatasetService<Subvencion> service;
    
    private static final Logger log = LoggerFactory.getLogger(SubvencionTest.class);

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<Subvencion> users = service.basicQuery(SubvencionConstants.KEY,Subvencion.class);
        
        assertTrue( users.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("subvencionController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String subvencionADD = "{"    			
    		    +"\"id\": \"2Test01186\","
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
    	        .content(subvencionADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String subvencionADD = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(SubvencionController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String subvencionADD = "{"    			
    		    +"\"id\": \"2Test01186\","
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
    	        .content(subvencionADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="2Test01186";
    	String subvencionUPDATE = "{"    			
    		    +"\"id\": \""+id+"\","
    		    +"\"title\": \"subvención nominativa al ateneo 2016 XXXXX\","
    		    +"\"areaId\": \"A05003340 XXXXX\","
    		    +"\"areaTitle\": \"área de gobierno de cultura y deportes XXXX\","
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
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(SubvencionController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String subvencionUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(SubvencionController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="2TestNOOK01186";
    	String subvencionUPDATE = "{"    			
    		    +"\"id\": \""+id+"\","
    		    +"\"title\": \"subvención nominativa al ateneo 2016 XXXXX\","
    		    +"\"areaId\": \"A05003340 XXXXX\","
    		    +"\"areaTitle\": \"área de gobierno de cultura y deportes XXXX\","
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
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(SubvencionController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="2Test01186";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="2Test01186";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(SubvencionController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="2TestNOOK01186";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="2Test01186";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(SubvencionController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="2TestNOOK01186";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(SubvencionController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(SubvencionController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(SubvencionController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(SubvencionController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test18_Post_transform() throws Exception {
    	String id ="2Test_Transform_01186";
    	String obj = "{"    			
    		    +"\"id\": \""+id+"\","
    		    +"\"title\": \"subvención nominativa al ateneo 2016 XXXXX\","
    		    +"\"areaId\": \"A05003340 XXXXX\","
    		    +"\"areaTitle\": \"área de gobierno de cultura y deportes XXXX\","
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
    	
    	String subvencionTransform = new String (obj.getBytes(),"UTF-8");
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(SubvencionController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionTransform))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_transform_NO_OK() throws Exception {
    	String id ="2Test_Transform_01186";
    	String obj = "{"    			
    		    +"\"id\": \""+id+"\","
    		    /*+"\"title\": \"subvención nominativa al ateneo 2016 XXXXX\","*/
    		    +"\"areaId\": \"A05003340 XXXXX\","
    		    +"\"areaTitle\": \"área de gobierno de cultura y deportes XXXX\","
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
    	
    	String subvencionTransform = new String (obj.getBytes(),"UTF-8");
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(SubvencionController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(subvencionTransform))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (Subvencion.class).getDeclaredFields();
    	
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(SubvencionController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	
    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(SubvencionController.LIST, mockMvc);
     	assertTrue(checkAllFormats);    	    	
    }

    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,SubvencionController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
}
