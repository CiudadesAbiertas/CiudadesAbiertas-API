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

package org.ciudadesabiertas.convenio;

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ConvenioController;
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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvenioTestFK {
	
	private static boolean activeFK = StartVariables.activeFK;
	
	@Autowired
	private WebApplicationContext wac;
        
    private MockMvc mockMvc;
    
    @Before
    public void setup() throws Exception {    	
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    	
    }
      
    @Test
	public void test01_Add() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV_TEST01";
			String item = " {\n" +
	     			   "   \"id\":\"" +id+"\",\n" +
		     		   "   \"title\": \"CONVENIO PRUEBAS 1\",\n" +
					   "   \"description\": \"DESC CONVENIO PRUEBAS 1\",\n" +
					   
					   "   \"objeto\": \"TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO\",\n" +
					   "   \"fechaInicio\": \"2020-01-01T00:00:00\",\n" +
					   "   \"fechaFinalizacion\": \"2021-01-01T00:00:00\",\n" +
					   "   \"fechaSuscripcion\": \"2020-02-01T00:00:00\",\n" +
					   "   \"fechaResolucionFin\": \"2020-06-01T00:00:00\",\n" +
					   "   \"fechaIncorporacion\": \"2020-03-01T00:00:00\",\n" +
					   "   \"obligacionEconomicaAyto\": 1200000.8,\n" +
					   "   \"tipoConvenio\": \"tipo-convenio-1\",\n" +
					   "   \"tipoVariacion\": \"tipo-variacion-XX\",\n" +
					   "   \"modalidad\": \"modalidad-convenio-1\",\n" +
					   "   \"materia\": \"tipo-materia-1\",\n" +
					   "   \"fechaAdjudicacionSub\": \"2020-02-11T00:00:00\",\n" +
					   "   \"importeSubv\": 320000.8,\n" +
					   "   \"adjudicatarioTitleSub\": \"Pedro Ruiz Gomez\",\n" +
					   "   \"subvencionId\": \"SUBV001\",\n" +
					   "   \"organizationId\": \"ORG0001\",\n" +
					   "   \"orgIdObligadoPrestacion\": \"ORG0002\",\n" +
					   "   \"gestionadoPorOrganization\": true,\n" +
					   "   \"gestionadoPorDistrito\": false,\n" +
					   "   \"municipioId\": \"28006\",\n" +
					   "   \"municipioTitle\": \"Alcobendas\",\n" +
					   "   \"distritoId\": \"2800601\",\n" +
					   "   \"distritoTitle\": \"Unico\", \n" +
					   "   \"esVariacionId\": \"CONV001\" \n" +  
	    			"    }"; 

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(ConvenioController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		}
	}

	@Test
	public void test01_Add_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV_TEST02";
			String item = " {\n" + 
					   "    \"id\": \"" + id + "\",\n" + 
					   "   \"title\": \"CONVENIO PRUEBAS X\",\n" +
					   "   \"description\": \"DESC CONVENIO PRUEBAS X\",\n" +
					  
					   "   \"objeto\": \"TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO\",\n" +
					   "   \"fechaInicio\": \"2020-01-01T00:00:00\",\n" +
					   "   \"fechaFinalizacion\": \"2021-01-01T00:00:00\",\n" +
					   "   \"fechaSuscripcion\": \"2020-02-01T00:00:00\",\n" +
					   "   \"fechaResolucionFin\": \"2020-06-01T00:00:00\",\n" +
					   "   \"fechaIncorporacion\": \"2020-03-01T00:00:00\",\n" +
					   "   \"obligacionEconomicaAyto\": 1200000.8,\n" +
					   "   \"tipoConvenio\": \"tipo-convenio-1\",\n" +
					   "   \"tipoVariacion\": \"tipo-variacion-XX\",\n" +
					   "   \"modalidad\": \"modalidad-convenio-1\",\n" +
					   "   \"materia\": \"tipo-materia-1\",\n" +
					   "   \"fechaAdjudicacionSub\": \"2020-02-11T00:00:00\",\n" +
					   "   \"importeSubv\": 320000.8,\n" +
					   "   \"adjudicatarioTitleSub\": \"Pedro Ruiz Gomez\",\n" +
					   "   \"subvencionId\": \"SUBV001\",\n" +
					   "   \"organizationId\": \"ORGMAL0001\",\n" +
					   "   \"orgIdObligadoPrestacion\": \"ORGMAL0002\",\n" +
					   "   \"gestionadoPorOrganization\": true,\n" +
					   "   \"gestionadoPorDistrito\": false,\n" +
					   "   \"municipioId\": \"28006\",\n" +
					   "   \"municipioTitle\": \"Alcobendas\",\n" +
					   "   \"distritoId\": \"2800601\",\n" +
					   "   \"distritoTitle\": \"Unico\", \n" +
					   "   \"esVariacionId\": \"CONVMAL001\" \n" +  
					"}";

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(ConvenioController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test07_Update() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV_TEST01";
			String item = " {\n" +
	     			   "   \"id\":\"" +id+"\",\n" +
		     		   "   \"title\": \"CONVENIO PRUEBAS 1 XX\",\n" +
					   "   \"description\": \"DESC CONVENIO PRUEBAS 1XX\",\n" +
					   
					   "   \"objeto\": \"TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO\",\n" +
					   "   \"fechaInicio\": \"2020-01-01T00:00:00\",\n" +
					   "   \"fechaFinalizacion\": \"2021-01-01T00:00:00\",\n" +
					   "   \"fechaSuscripcion\": \"2020-02-01T00:00:00\",\n" +
					   "   \"fechaResolucionFin\": \"2020-06-01T00:00:00\",\n" +
					   "   \"fechaIncorporacion\": \"2020-03-01T00:00:00\",\n" +
					   "   \"obligacionEconomicaAyto\": 1200000.8,\n" +
					   "   \"tipoConvenio\": \"tipo-convenio-XX\",\n" +
					   "   \"tipoVariacion\": \"tipo-variacion-XX\",\n" +
					   "   \"modalidad\": \"modalidad-convenio-XX\",\n" +
					   "   \"materia\": \"tipo-materia-XX\",\n" +
					   "   \"fechaAdjudicacionSub\": \"2020-02-11T00:00:00\",\n" +
					   "   \"importeSubv\": 320000.8,\n" +
					   "   \"adjudicatarioTitleSub\": \"Pedro Ruiz Gomez\",\n" +
					   "   \"subvencionId\": \"SUBV001\",\n" +
					   "   \"organizationId\": \"ORG0001\",\n" +
					   "   \"orgIdObligadoPrestacion\": \"ORG0002\",\n" +
					   "   \"gestionadoPorOrganization\": true,\n" +
					   "   \"gestionadoPorDistrito\": false,\n" +
					   "   \"municipioId\": \"28006\",\n" +
					   "   \"municipioTitle\": \"Alcobendas\",\n" +
					   "   \"distritoId\": \"2800601\",\n" +
					   "   \"distritoTitle\": \"Unico\", \n" +
					   "   \"esVariacionId\": \"CONV001\" \n" +  
	    			"    }"; 

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(ConvenioController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}
	}

	@Test
	public void test07_Update_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV_TEST01";
			String item = " {\n" + 
					   "   \"id\":\""+id+"\",\n" +
		     		   "   \"title\": \"CONVENIO PRUEBAS XXX\",\n" +
					   "   \"description\": \"DESC CONVENIO PRUEBAS XXX\",\n" +
					  
					   "   \"objeto\": \"TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO XX\",\n" +
					   "   \"fechaInicio\": \"2020-02-01T00:00:00\",\n" +
					   "   \"fechaFinalizacion\": \"2022-01-01T00:00:00\",\n" +
					   "   \"fechaSuscripcion\": \"2020-03-01T00:00:00\",\n" +
					   "   \"fechaResolucionFin\": \"2020-03-01T00:00:00\",\n" +
					   "   \"fechaIncorporacion\": \"2020-02-01T00:00:00\",\n" +
					   "   \"obligacionEconomicaAyto\": 1290000.8,\n" +
					   "   \"tipoConvenio\": \"tipo-convenio-X\",\n" +
					   "   \"tipoVariacion\": \"tipo-variacion-XX\",\n" +
					   "   \"modalidad\": \"modalidad-convenio-X\",\n" +
					   "   \"materia\": \"tipo-materia-X\",\n" +
					   "   \"fechaAdjudicacionSub\": \"2020-03-11T00:00:00\",\n" +
					   "   \"importeSubv\": 320000.8,\n" +
					   "   \"adjudicatarioTitleSub\": \"Pedro Ruiz Gomez XX\",\n" +
					   "   \"subvencionId\": \"SUBV001\",\n" +
					   "   \"organizationId\": \"ORGMAL0001\",\n" +
					   "   \"orgIdObligadoPrestacion\": \"ORGMAL0001\",\n" +
					   "   \"gestionadoPorOrganization\": false,\n" +
					   "   \"gestionadoPorDistrito\": true,\n" +
					   "   \"municipioId\": \"28006\",\n" +
					   "   \"municipioTitle\": \"Alcobendas\",\n" +
					   "   \"distritoId\": \"2800601\",\n" +
					   "   \"distritoTitle\": \"Unico\", \n" +
					   "   \"esVariacionId\": \"CONVMAL001\" \n" +
				"}";

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(ConvenioController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test08_Delete_FK_ERROR() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV001";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(ConvenioController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}

	}

	

	@Test
	public void test99_Delete() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "CONV_TEST01";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(ConvenioController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}

	}
           
    
}
