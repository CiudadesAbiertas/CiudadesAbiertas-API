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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.DeudaComercialInformeMorosidadTrimestralController;
import org.ciudadesabiertas.dataset.model.DeudaComercialInformeMorosidadTrimestral;
import org.ciudadesabiertas.dataset.utils.DeudaConstants;
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
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeudaComercialInformeMorosidadTrimestralTest {
	
	private static final Logger log = LoggerFactory.getLogger(DeudaComercialInformeMorosidadTrimestralTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<DeudaComercialInformeMorosidadTrimestral> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<DeudaComercialInformeMorosidadTrimestral> items = dsService.basicQuery(DeudaConstants.KEY,DeudaComercialInformeMorosidadTrimestral.class);
        
        assertTrue( items.size()>0 );


    }
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("deudaComercialInformeMorosidadTrimestralController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	String dciMorosidadTri = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6472,\n"
    			+ "      \"importePagosDentroPeriodo\": 51831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 5579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 0,\n"
    			+ "      \"importePagosInteresesDemora\": 0,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 34441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 25,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 489769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 33.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";
    	    	    	
    	dciMorosidadTri = new String (dciMorosidadTri.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformeMorosidadTrimestralController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTri))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String dciMorosidadTri = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformeMorosidadTrimestralController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTri))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	String dciMorosidadTri = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6472,\n"
    			+ "      \"importePagosDentroPeriodo\": 51831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 5579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 0,\n"
    			+ "      \"importePagosInteresesDemora\": 0,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 34441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 25,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 489769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 33.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";
    	
    	dciMorosidadTri = new String (dciMorosidadTri.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformeMorosidadTrimestralController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTri))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6572,\n"
    			+ "      \"importePagosDentroPeriodo\": 81831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 8579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 5,\n"
    			+ "      \"importePagosInteresesDemora\": 5,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 84441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 215,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 459769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 35.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";

    	String dciMorosidadTriUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTriUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String dciMorosidadTriUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTriUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01-NOK";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6572,\n"
    			+ "      \"importePagosDentroPeriodo\": 81831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 8579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 5,\n"
    			+ "      \"importePagosInteresesDemora\": 5,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 84441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 215,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 459769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 35.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";

    	String dciMorosidadTriUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTriUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="XXX-XXX-MOR-TRI-01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_TRAPROINT01";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DeudaComercialInformeMorosidadTrimestralController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformeMorosidadTrimestralController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformeMorosidadTrimestralController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DeudaComercialInformeMorosidadTrimestralController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	
    	String id ="XXX-XXX-MOR-TRI-01-TRANSFORM";
    	String obj = "{\r\n"
    			+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6572,\n"
    			+ "      \"importePagosDentroPeriodo\": 81831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 8579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 5,\n"
    			+ "      \"importePagosInteresesDemora\": 5,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 84441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 215,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 459769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 35.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";

    	String dciMorosidadTriUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformeMorosidadTrimestralController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTriUPDATE))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	//String id ="XXX-XXX-MOR-TRI-01-TRANSFORM";
    	String obj = "{\r\n"
    			//+ "      \"id\": \""+id+"\",\r\n"
    			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
    			+ "      \"numPagosDentroPeriodo\": 6572,\n"
    			+ "      \"importePagosDentroPeriodo\": 81831754.13,\n"
    			+ "      \"numPagosFueraPeriodo\": 1124,\n"
    			+ "      \"importePagosFueraPeriodo\": 8579630.33,\n"
    			+ "      \"numPagosInteresesDemora\": 5,\n"
    			+ "      \"importePagosInteresesDemora\": 5,\n"
    			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
    			+ "      \"importeFacturasPendientesDentroPeriodo\": 84441734.41,\n"
    			+ "      \"numFacturasPendientesFueraPeriodo\": 215,\n"
    			+ "      \"importeFacturasPendientesFueraPeriodo\": 459769.85,\n"
    			+ "      \"periodoMedioPagoPendiente\": 35.87,\n"
    			+ "      \"tipoContabilidad\": \"empresarial\",\n"
    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
    			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
    			+ "    }";

    	String dciMorosidadTriUPDATE = new String (obj.getBytes(),"UTF-8");	    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformeMorosidadTrimestralController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(dciMorosidadTriUPDATE))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (DeudaComercialInformeMorosidadTrimestral.class).getDeclaredFields();
    	String[] fieldsToIngore = { "hasBeginning","hasEnd" };
    	ArrayList<String> ignoreList = new ArrayList<String>(Arrays.asList(fieldsToIngore));
    	
    	boolean checkFields=true;
    	
    	for (Field f:declaredFields)
    	{
    		if(!ignoreList.contains(f.getName())) {
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
    	}
    	
    	assertTrue(checkFields);
    	
    }
    
    @Test    
    public void test21_List_Sort_200() throws Exception {
    	String sort="?sort=-id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DeudaComercialInformeMorosidadTrimestralController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialInformeMorosidadTrimestralController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    


    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DeudaComercialInformeMorosidadTrimestralController.LIST+"/"+"2019-cuarto-12-28-079-AP-001", mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    
}
