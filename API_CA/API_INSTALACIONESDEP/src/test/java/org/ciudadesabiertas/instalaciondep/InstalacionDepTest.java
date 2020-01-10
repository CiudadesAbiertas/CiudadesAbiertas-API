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

package org.ciudadesabiertas.instalaciondep;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.InstalacionDepController;
import org.ciudadesabiertas.dataset.model.Equipamiento;
import org.ciudadesabiertas.dataset.utils.InstalacionDepConstants;
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
public class InstalacionDepTest {
	
	private static final Logger log = LoggerFactory.getLogger(InstalacionDepTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<Equipamiento> service;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<Equipamiento> users = service.basicQuery(InstalacionDepConstants.KEY,Equipamiento.class);
        
        assertTrue( users.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("instalacionDepController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Add() throws Exception {
    	String ikey ="TEST01_EQID0001";
    	String equipamientoADD = "{"    			
    			+"\"id\" : \""+ikey+"\","    	
        	    +"\"title\" : \"Instalación Deportiva Básica Sala Municipal de PRUEBAS DE TEST\","
        	    +"\"description\" : \" XXXXX XXXXXX ALTA XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28006\","
        	    +"\"municipioTitle\" : \"Alcobendas\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"AV OLIMPICA s/n\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"aulaambiental2@aytoalcobendas.org\","
        	    +"\"telephone\" : \"34916618096\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\"" 
        	  +"}";
    	
    	equipamientoADD = new String (equipamientoADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(InstalacionDepController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String equipamientoADD = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(InstalacionDepController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
    	String ikey ="TEST01_EQID0001";
    	String equipamientoADD = "{"    			
    			+"\"id\" : \""+ikey+"\","
        	    +"\"title\" : \"Instalación Deportiva Básica Sala Municipal de PRUEBAS DE TEST\","
        	    +"\"description\" : \" XXXXX XXXXXX ALTA XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28006\","
        	    +"\"municipioTitle\" : \"Alcobendas\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"AV OLIMPICA s/n\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"aulaambiental2@aytoalcobendas.org\","
        	    +"\"telephone\" : \"34916618096\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\""   
        	  +"}";
    	
    	equipamientoADD = new String (equipamientoADD.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(InstalacionDepController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoADD))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
    	String id ="TEST01_EQID0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
        	    +"\"title\" : \" XXXModificación Instalación Deportiva Básica Sala Municipal de PRUEBAS DE TEST\","
        	    +"\"description\" : \" XXXXX XXXXXX MODIFICACION XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28006\","
        	    +"\"municipioTitle\" : \"Alcobendas\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"AV OLIMPICA s/n\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"modificacion@aytoalcobendas.org\","
        	    +"\"telephone\" : \"Sin Numero de tlfno.\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\""   
        	  +"}";
    	
    	String equipamientoUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(InstalacionDepController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="2Test01186";
    	String equipamientoUPDATE = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(InstalacionDepController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_EQ0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
        	    +"\"title\" : \" XXXMODIFICACION XXXX INSTALACION DEPORTIVA PRUBA TEST\","
        	    +"\"description\" : \" XXXXX XXXXXX MODIFICACION XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28006\","
        	    +"\"municipioTitle\" : \"Alcobendas\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"AV OLIMPICA s/n\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"modificacion@aytoalcobendas.org\","
        	    +"\"telephone\" : \"Sin Numero de tlfno.\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\""   
        	  +"}";
    	
    	String equipamientoUPDATE = new String (obj.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(InstalacionDepController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoUPDATE))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="TEST01_EQID0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="TEST01_EQID0001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(InstalacionDepController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_EQID0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="TEST01_EQID0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(InstalacionDepController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="TEST01_NOOK_EQID0001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(InstalacionDepController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(InstalacionDepController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(InstalacionDepController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(InstalacionDepController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    @Test    
    public void test18_Post_Transform() throws Exception {
    	String id ="TEST01_TRASFORM_EQI0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
        	    +"\"title\" : \" XXX Instalacion Dep Prueba Transformacion\","
        	    +"\"description\" : \" XXXXX XXXXXX MODIFICACION XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28079\","
        	    +"\"municipioTitle\" : \"Madrid\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"CALLE BARCELO V 2\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"modificacion@aytoalcobendas.org\","
        	    +"\"telephone\" : \"Sin Numero de tlfno.\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\""  
        	  +"}";
    	
    	String equipamientoTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(InstalacionDepController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
    	String id ="TEST01_TRASFORM_EQ0001";
    	String obj = "{"    			
    			+"\"id\" : \""+id+"\","
        	    /*+"\"title\" : \" XXX TRANSFORMACIÓN XXXX Aula de Educación Ambiental\","*/
        	    +"\"description\" : \" XXXXX XXXXXX MODIFICACION XXXX XXXXX PRUEBAS XXXXXX Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\","
        	    +"\"tipoEquipamiento\" : \""+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"\","
        	    +"\"municipioId\" : \"28006\","
        	    +"\"municipioTitle\" : \"Alcobendas\","
        	    +"\"provincia\" : \"Madrid\","
        	    +"\"region\" : \"Madrid\","
        	    +"\"pais\" : \"España\","
        	    +"\"streetAddress\" : \"AV OLIMPICA s/n\","
        	    +"\"postalCode\" : \"28108\","
        	    +"\"barrio\" : \"Urbanizaciones\","
        	    +"\"distrito\" : \"Unico\","
        	    +"\"xETRS89\" : 440598.07885,"
        	    +"\"yETRS89\" : 4472390.03112,"
        	    +"\"email\" : \"modificacion@aytoalcobendas.org\","
        	    +"\"telephone\" : \"Sin Numero de tlfno.\","
        	    +"\"titularidad\" : \"Publico\"," 
        	    +"\"openingHours\" : \"Horario de Pruebas\"" 
        	  +"}";
    	
    	String equipamientoTransform = new String (obj.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(InstalacionDepController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(equipamientoTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (Equipamiento.class).getDeclaredFields();
    	
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test
   	public void test23_Busqueda_geo() throws Exception
   	{	
   		String sort="?xETRS89=440929.42&yETRS89=4477579.19&meters=50";
           this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.GEO_LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
   	}
    
    public void test24_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(InstalacionDepController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(InstalacionDepController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,InstalacionDepController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }

}