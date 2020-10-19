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

package org.ciudadesabiertas.contaminacionacustica;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ContAcusticaEstacionMedidaController;
import org.ciudadesabiertas.dataset.model.ContAcusticaEstacionMedida;
import org.ciudadesabiertas.dataset.utils.ContaminacionAcusticaConstants;
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
public class ContAcusticaEstacionMedidaTest {
	
	private static final Logger log = LoggerFactory.getLogger(ContAcusticaEstacionMedidaTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<ContAcusticaEstacionMedida> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<ContAcusticaEstacionMedida> items = dsService.basicQuery(ContaminacionAcusticaConstants.KEY,ContAcusticaEstacionMedida.class);
        
        assertTrue( items.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("contAcusticaEstacionMedidaController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String jsonItem =  " {\r\n" + 
    			"	 \"id\" : \"CONTACUSTESTMEDTEST001\",\r\n" +
    			"    \"title\" : \"Dispositivo que detecta los ruidos I.\",\r\n" +
    			"    \"fechaAlta\" : \"2020-03-31T08:00:00\",\r\n" +
    			"    \"fechaBaja\" : \"2020-07-30T09:00:00\",\r\n" +
    			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
    			"    \"postalCode\" : \"28100\",\r\n" +
    			"    \"portalId\" : \"PORTAL000119\",\r\n" +
    			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
    			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
    			"    \"observes\" : \"nivelRuido\",\r\n" +
    			"    \"municipioId\": \"28006\",\r\n" +
    			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"    \"barrioId\": \"28006011\",\r\n" +
    			"    \"barrioTitle\": \"Norte\",\r\n" +
    			"    \"distritoId\": \"2800601\",\r\n" +
    			"    \"distritoTitle\": \"Unico\",\r\n" +
    			"    \"latitud\" : 40.42020937,\r\n" +
    			"    \"longitud\" : -3.70579377,\r\n" +
    			"    \"xETRS89\" : 440124.33000,\r\n" +
    			"    \"yETRS89\" : 4474637.17000\r\n" +
    			"    }";

    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ContAcusticaEstacionMedidaController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String jsonItem = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ContAcusticaEstacionMedidaController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String jsonItem =  " {\r\n" + 
    			"	 \"id\" : \"CONTACUSTESTMEDTEST001\",\r\n" +
    			"    \"title\" : \"Dispositivo que detecta los ruidos I.\",\r\n" +
    			"    \"fechaAlta\" : \"2020-03-31T08:00:00\",\r\n" +
    			"    \"fechaBaja\" : \"2020-07-30T09:00:00\",\r\n" +
    			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
    			"    \"postalCode\" : \"28100\",\r\n" +
    			"    \"portalId\" : \"PORTAL000119\",\r\n" +
    			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
    			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
    			"    \"observes\" : \"nivelRuido\",\r\n" +
    			"    \"municipioId\": \"28006\",\r\n" +
    			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"    \"barrioId\": \"28006011\",\r\n" +
    			"    \"barrioTitle\": \"Norte\",\r\n" +
    			"    \"distritoId\": \"2800601\",\r\n" +
    			"    \"distritoTitle\": \"Unico\",\r\n" +
    			"    \"latitud\" : 40.42020937,\r\n" +
    			"    \"longitud\" : -3.70579377,\r\n" +
    			"    \"xETRS89\" : 440124.33000,\r\n" +
    			"    \"yETRS89\" : 4474637.17000\r\n" +
    			"    }";
    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ContAcusticaEstacionMedidaController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="CONTACUSTESTMEDTEST001";
    	String jsonItem =  " {\r\n" + 
    			"    \"id\": \""+id+"\",\r\n" + 
    			"    \"title\" : \"Dispositivo XXX que detecta los ruidos I.\",\r\n" +
    			"    \"fechaAlta\" : \"2010-03-11T08:00:00\",\r\n" +
    			"    \"fechaBaja\" : \"2020-03-20T09:00:00\",\r\n" +
    			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
    			"    \"postalCode\" : \"28100\",\r\n" +
    			"    \"portalId\" : \"PORTAL000119\",\r\n" +
    			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
    			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
    			"    \"observes\" : \"nivelRuido\",\r\n" +
    			"    \"municipioId\": \"28006\",\r\n" +
    			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"    \"barrioId\": \"28006011\",\r\n" +
    			"    \"barrioTitle\": \"Norte\",\r\n" +
    			"    \"distritoId\": \"2800601\",\r\n" +
    			"    \"distritoTitle\": \"Unico\",\r\n" +
    			"    \"latitud\" : 40.42020937,\r\n" +
    			"    \"longitud\" : -3.70579377,\r\n" +
    			"    \"xETRS89\" : 440124.33000,\r\n" +
    			"    \"yETRS89\" : 4474637.17000\r\n" +
    			"    }";
    	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ContAcusticaEstacionMedidaController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="CONTACUSTESTMEDTEST001";
    	String itemUpdate = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ContAcusticaEstacionMedidaController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="CONTACUSTESTMEDTESTKO001";
    	String jsonItem =  " {\r\n" + 
    			"      \"id\": \""+id+"\",\r\n" + 
    			"    \"title\" : \"Dispositivo XXX que detecta los ruidos I.\",\r\n" +
    			"    \"fechaAlta\" : \"2010-03-11T08:00:00\",\r\n" +
    			"    \"fechaBaja\" : \"2020-03-20T09:00:00\",\r\n" +
    			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
    			"    \"postalCode\" : \"28100\",\r\n" +
    			"    \"portalId\" : \"PORTAL000119\",\r\n" +
    			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
    			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
    			"    \"observes\" : \"nivelRuido\",\r\n" +
    			"    \"municipioId\": \"28006\",\r\n" +
    			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"    \"barrioId\": \"28006011\",\r\n" +
    			"    \"barrioTitle\": \"Norte\",\r\n" +
    			"    \"distritoId\": \"2800601\",\r\n" +
    			"    \"distritoTitle\": \"Unico\",\r\n" +
    			"    \"latitud\" : 40.42020937,\r\n" +
    			"    \"longitud\" : -3.70579377,\r\n" +
    			"    \"xETRS89\" : 440124.33000,\r\n" +
    			"    \"yETRS89\" : 4474637.17000\r\n" +
    			"    }";
    	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ContAcusticaEstacionMedidaController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="CONTACUSTESTMEDTEST001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="CONTACUSTESTMEDTEST001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(ContAcusticaEstacionMedidaController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="CONTACUSTESTMEDTESTKO001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="CONTACUSTESTMEDTEST001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ContAcusticaEstacionMedidaController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="CONTACUSTESTMEDTESTKO0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ContAcusticaEstacionMedidaController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ContAcusticaEstacionMedidaController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ContAcusticaEstacionMedidaController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ContAcusticaEstacionMedidaController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="P_TEST_001";
    	String jsonItem =  " {\r\n" + 
    			"      \"id\": \""+id+"\",\r\n" + 
    			"    \"title\" : \"Dispositivo que detecta los ruidos I.\",\r\n" +
    			"    \"fechaAlta\" : \"2020-03-31T08:00:00\",\r\n" +
    			"    \"fechaBaja\" : \"2020-07-30T09:00:00\",\r\n" +
    			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
    			"    \"postalCode\" : \"28100\",\r\n" +
    			"    \"portalId\" : \"PORTAL000119\",\r\n" +
    			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
    			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
    			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
    			"    \"observes\" : \"nivelRuido\",\r\n" +
    			"    \"municipioId\": \"28006\",\r\n" +
    			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
    			"    \"barrioId\": \"28006011\",\r\n" +
    			"    \"barrioTitle\": \"Norte\",\r\n" +
    			"    \"distritoId\": \"2800601\",\r\n" +
    			"    \"distritoTitle\": \"Unico\",\r\n" +
    			"    \"latitud\" : 40.42020937,\r\n" +
    			"    \"longitud\" : -3.70579377,\r\n" +
    			"    \"xETRS89\" : 440124.33000,\r\n" +
    			"    \"yETRS89\" : 4474637.17000\r\n" +
    			"    }";
    	
    	String PlantillaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ContAcusticaEstacionMedidaController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(PlantillaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	String id ="P_TEST_001";
    	String jsonItem =  " {\r\n" + 
    			"      \"id\": \""+id+"\",\r\n" +     			
    			"      \"xETRS89\": 440407.15471,\r\n" + 
    			"      \"yETRS89\": 4474267.12122\r\n" + 
    			"    }";
    	
    	String PlantillaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ContAcusticaEstacionMedidaController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(PlantillaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (ContAcusticaEstacionMedida.class).getDeclaredFields();
    	
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    
    @Test
   	public void test23_Busqueda_geo() throws Exception
   	{	
   		String sort="?xETRS89=440686&yETRS89=4483490&meters=1";
           this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.GEO_LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
   	}
   	
    
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ContAcusticaEstacionMedidaController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ContAcusticaEstacionMedidaController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,ContAcusticaEstacionMedidaController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
}
