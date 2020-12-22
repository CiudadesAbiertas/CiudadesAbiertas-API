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

package org.ciudadesabiertas.empleo;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ConvocatoriaEmpleoPublicoController;
import org.ciudadesabiertas.dataset.model.ConvocatoriaEmpleoPublico;
import org.ciudadesabiertas.dataset.utils.EmpleoConstants;
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
public class ConvocatoriaPublicaTest {
	
	private static final Logger log = LoggerFactory.getLogger(ConvocatoriaPublicaTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<ConvocatoriaEmpleoPublico> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<ConvocatoriaEmpleoPublico> items = dsService.basicQuery(EmpleoConstants.KEY,ConvocatoriaEmpleoPublico.class);
        
        assertTrue( items.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("convocatoriaEmpleoPublicoController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String jsonItem =  "{\n" + 
    		"      \"id\": \"convocatoriaTest001\",\n" + 
    		"      \"title\": \"Convocatoria Informática Noviembre 2020\",\n" + 
    		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
    		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
    		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
    		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
    		"      \"estadoPlazo\": true,\n" + 
    		"      \"plazos\": \"200\",\n" + 
    		"      \"numeroPlazasConvocadas\": 5,\n" + 
    		"      \"listaEsperaEx\": false,\n" + 
    		"      \"observaciones\": \"observaciones\",\n" + 
    		"      \"disposiciones\": \"disposiciones\",\n" + 
    		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
    		"      \"bases\": \"Las bases cuentan....\",\n" + 
    		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
    		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
    		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
    		"      \"grupoProfesional\": \"A1\",\n" + 
    		"      \"empleadoPublico\": \"funcionario\",\n" + 
    		"      \"cuerpo\": \"administracion-general\",\n" + 
    		"      \"modalidad\": \"oposicion\"\n" + 
    		"    }";

    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ConvocatoriaEmpleoPublicoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String jsonItem = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ConvocatoriaEmpleoPublicoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
  		String jsonItem =  "{\n" + 
		"      \"id\": \"convocatoriaTest001\",\n" + 
		"      \"title\": \"Convocatoria Informática Noviembre 2020\",\n" + 
		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
		"      \"estadoPlazo\": true,\n" + 
		"      \"plazos\": \"200\",\n" + 
		"      \"numeroPlazasConvocadas\": 5,\n" + 
		"      \"listaEsperaEx\": false,\n" + 
		"      \"observaciones\": \"observaciones\",\n" + 
		"      \"disposiciones\": \"disposiciones\",\n" + 
		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
		"      \"bases\": \"Las bases cuentan....\",\n" + 
		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
		"      \"grupoProfesional\": \"A1\",\n" + 
		"      \"empleadoPublico\": \"funcionario\",\n" + 
		"      \"cuerpo\": \"administracion-general\",\n" + 
		"      \"modalidad\": \"oposicion\"\n" + 
		"    }";
    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(ConvocatoriaEmpleoPublicoController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="convocatoriaTest001";
    	
    	String jsonItem =  "{\n" + 
    		"      \"id\": \""+id+"\",\n" + 
    		"      \"title\": \"Convocatoria Informática Noviembre 2021\",\n" + 
    		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
    		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
    		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
    		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
    		"      \"estadoPlazo\": true,\n" + 
    		"      \"plazos\": \"200\",\n" + 
    		"      \"numeroPlazasConvocadas\": 5,\n" + 
    		"      \"listaEsperaEx\": false,\n" + 
    		"      \"observaciones\": \"observaciones\",\n" + 
    		"      \"disposiciones\": \"disposiciones\",\n" + 
    		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
    		"      \"bases\": \"Las bases cuentan....\",\n" + 
    		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
    		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
    		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
    		"      \"grupoProfesional\": \"A1\",\n" + 
    		"      \"empleadoPublico\": \"funcionario\",\n" + 
    		"      \"cuerpo\": \"administracion-general\",\n" + 
    		"      \"modalidad\": \"oposicion\"\n" + 
    		"    }";
    	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ConvocatoriaEmpleoPublicoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="convocatoriaTest001";
    	String itemUpdate = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ConvocatoriaEmpleoPublicoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="convocatoriaTest001ko";
    	String jsonItem =  "{\n" + 
    		"      \"id\": \""+id+"\",\n" + 
    		"      \"title\": \"Convocatoria Informática Noviembre 2021\",\n" + 
    		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
    		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
    		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
    		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
    		"      \"estadoPlazo\": true,\n" + 
    		"      \"plazos\": \"200\",\n" + 
    		"      \"numeroPlazasConvocadas\": 5,\n" + 
    		"      \"listaEsperaEx\": false,\n" + 
    		"      \"observaciones\": \"observaciones\",\n" + 
    		"      \"disposiciones\": \"disposiciones\",\n" + 
    		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
    		"      \"bases\": \"Las bases cuentan....\",\n" + 
    		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
    		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
    		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
    		"      \"grupoProfesional\": \"A1\",\n" + 
    		"      \"empleadoPublico\": \"funcionario\",\n" + 
    		"      \"cuerpo\": \"administracion-general\",\n" + 
    		"      \"modalidad\": \"oposicion\"\n" + 
    		"    }";
    	
       	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(ConvocatoriaEmpleoPublicoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="convocatoriaTest001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="convocatoriaTest001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(ConvocatoriaEmpleoPublicoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="P_TEST_0KO001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="convocatoriaTest001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ConvocatoriaEmpleoPublicoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="P_TEST_00KO0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(ConvocatoriaEmpleoPublicoController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ConvocatoriaEmpleoPublicoController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ConvocatoriaEmpleoPublicoController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(ConvocatoriaEmpleoPublicoController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
  	String id ="convocatoriaTest001ko";
  	String jsonItem =  "{\n" + 
  		"      \"id\": \""+id+"\",\n" + 
  		"      \"title\": \"Convocatoria Informática Noviembre 2021\",\n" + 
  		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
  		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
  		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
  		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
  		"      \"estadoPlazo\": true,\n" + 
  		"      \"plazos\": \"200\",\n" + 
  		"      \"numeroPlazasConvocadas\": 5,\n" + 
  		"      \"listaEsperaEx\": false,\n" + 
  		"      \"observaciones\": \"observaciones\",\n" + 
  		"      \"disposiciones\": \"disposiciones\",\n" + 
  		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
  		"      \"bases\": \"Las bases cuentan....\",\n" + 
  		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
  		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
  		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
  		"      \"grupoProfesional\": \"A1\",\n" + 
  		"      \"empleadoPublico\": \"funcionario\",\n" + 
  		"      \"cuerpo\": \"administracion-general\",\n" + 
  		"      \"modalidad\": \"oposicion\"\n" + 
  		"    }";
    	
    	String ConvocatoriaPublicaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ConvocatoriaEmpleoPublicoController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(ConvocatoriaPublicaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
  	String id ="convocatoriaTest001";
  	String jsonItem =  "{\n" + 
  		"      \"id\": \""+id+"\",\n" + 
  	//	"      \"title\": \"Convocatoria Informática Noviembre 2021\",\n" + 
  		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
  		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
  		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
  		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
  		"      \"estadoPlazo\": true,\n" + 
  		"      \"plazos\": \"200\",\n" + 
  		"      \"numeroPlazasConvocadas\": 5,\n" + 
  		"      \"listaEsperaEx\": false,\n" + 
  		"      \"observaciones\": \"observaciones\",\n" + 
  		"      \"disposiciones\": \"disposiciones\",\n" + 
  		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
  		"      \"bases\": \"Las bases cuentan....\",\n" + 
  		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
  		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
  		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
  		"      \"grupoProfesional\": \"A1\",\n" + 
  		"      \"empleadoPublico\": \"funcionario\",\n" + 
  		"      \"cuerpo\": \"administracion-general\",\n" + 
  		"      \"modalidad\": \"oposicion\"\n" + 
  		"    }";
    	
    	String ConvocatoriaPublicaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(ConvocatoriaEmpleoPublicoController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(ConvocatoriaPublicaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (ConvocatoriaEmpleoPublico.class).getDeclaredFields();
    	
    	boolean checkFields=true;
    	
    	for (Field f:declaredFields)
    	{
    		Rdf annotation=f.getAnnotation(Rdf.class);
    		if (annotation!=null)
    		{   
    			if (!Util.validatorFieldRDF(f.getName(), annotation.propiedad()))
    			{
    			  	if (f.getName().equals("name")==false)
    			  	{
    			  	  log.info(f.getName()+" vs. "+annotation.propiedad() );
    			  	  checkFields=false;
    			  	}
    			}
    		}
    	}
    	
    	assertTrue(checkFields);
    	
    }
    
    @Test    
    public void test21_List_Sort_200() throws Exception {
    	String sort="?sort=-id,title";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    
      	
    
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(ConvocatoriaEmpleoPublicoController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(ConvocatoriaEmpleoPublicoController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,ConvocatoriaEmpleoPublicoController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
}
