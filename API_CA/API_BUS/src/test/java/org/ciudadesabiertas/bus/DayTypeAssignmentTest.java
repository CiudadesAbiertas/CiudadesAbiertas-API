/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.DayTypeAssignmentController;
import org.ciudadesabiertas.dataset.model.DayTypeAssignment;
import org.ciudadesabiertas.dataset.utils.BusConstants;
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
public class DayTypeAssignmentTest {
	
private static final Logger log = LoggerFactory.getLogger(DayTypeAssignmentTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<DayTypeAssignment> dsService;	
	
	private MockMvc mockMvc;
	
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<DayTypeAssignment> items = dsService.basicQuery(BusConstants.KEY,DayTypeAssignment.class);
        
        assertTrue( items.size()>0 );

    }
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("dayTypeAssignmentController"));
    }
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test04_Add() throws Exception {
    	String id ="BUSTEST001";
    	String jsonItem =  "{\r\n" + 
    		"      \"id\": \""+id+"\",\r\n" + 
    		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
    		"      \"isAvailable\": \"true\",\r\n" + 
    		"      \"specifying\": \"laborable\",\r\n" +
    		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
    		"    }";

    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DayTypeAssignmentController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test    
    public void test05_Add_NO_OK_400() throws Exception {
    	String jsonItem = "{"     	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DayTypeAssignmentController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test06_Add_NO_OK_409() throws Exception {
      String id ="BUSTEST001";
  	String jsonItem =  "{\r\n" + 
  		"      \"id\": \""+id+"\",\r\n" + 
		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
		"      \"isAvailable\": \"true\",\r\n" + 
		"      \"specifying\": \"laborable\",\r\n" +
		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
  		"    }";
    	
    	jsonItem = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.post(DayTypeAssignmentController.ADD)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(jsonItem))	
        	
        	.andExpect(MockMvcResultMatchers.status().isConflict());
    }
    
    @Test    
    public void test07_Update() throws Exception {
      String id ="BUSTEST001";
  	String jsonItem =  "{\r\n" + 
  		"      \"id\": \""+id+"\",\r\n" + 
		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
		"      \"isAvailable\": \"true\",\r\n" + 
		"      \"specifying\": \"laborable\",\r\n" +
		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
  		"    }";
    	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DayTypeAssignmentController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test08_Update_NO_OK_400() throws Exception {
    	String id ="BUSTEST001";
    	String itemUpdate = "{"      	
    			+"}";
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DayTypeAssignmentController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test09_Update_NO_OK_404() throws Exception {
      String id ="BUSTEST001KO";
  	String jsonItem =  "{\r\n" + 
  		"      \"id\": \""+id+"\",\r\n" + 
		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
		"      \"isAvailable\": \"true\",\r\n" + 
		"      \"specifying\": \"laborable\",\r\n" +
		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
  		"    }";
    	
    	String itemUpdate = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
        this.mockMvc.perform(MockMvcRequestBuilders.put(DayTypeAssignmentController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(itemUpdate))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="BUSTEST001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="BUSTEST001";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(DayTypeAssignmentController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id="BUSTEST001KO";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.ADD+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test13_Delete() throws Exception {
    	String id ="BUSTEST001";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DayTypeAssignmentController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test14_Delete_NO_OK_404() throws Exception {
    	String id ="BUSTEST001KO";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.delete(DayTypeAssignmentController.ADD+"/"+id)
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DayTypeAssignmentController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DayTypeAssignmentController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(DayTypeAssignmentController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test18_Post_Transform() throws Exception {
      String id ="BUSTEST001";
    	String jsonItem =  "{\r\n" + 
    		"      \"id\": \""+id+"\",\r\n" + 
    		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
    		"      \"isAvailable\": \"true\",\r\n" + 
    		"      \"specifying\": \"laborable\",\r\n" +
    		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
    		"    }";
    	
    	String PlantillaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DayTypeAssignmentController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(PlantillaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test19_Post_Transform_NO_OK() throws Exception {
      String id ="BUSTEST001";
    	String jsonItem =  "{\r\n" + 
    		//"      \"id\": \""+id+"\",\r\n" + 
    		"      \"date\": \"2020-01-02T00:00:00\",\r\n" + 
    		"      \"isAvailable\": \"true\",\r\n" + 
    		"      \"specifying\": \"laborable\",\r\n" +
    		"      \"forTheDefinitionOf\": \"2020\"\r\n" +
    		"    }";
    	
    	String PlantillaTransform = new String (jsonItem.getBytes(),"UTF-8");	
    	
    	 
    	this.mockMvc.perform(MockMvcRequestBuilders.post(DayTypeAssignmentController.TRANSFORM)
        		.contentType(MediaType.APPLICATION_JSON)
    	        .content(PlantillaTransform))	
        	
        	.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test    
    public void test20_Database_and_vocabulary() throws Exception {
    	
    	Field[] declaredFields = (DayTypeAssignmentController.class).getDeclaredFields();
    	
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
    	String sort="?sort=-id,date";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    @Test    
    public void test23_List_Distinct_200() throws Exception {
    	String sort="?field=id";
        this.mockMvc.perform(MockMvcRequestBuilders.get(DayTypeAssignmentController.SEARCH_DISTINCT+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(DayTypeAssignmentController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,DayTypeAssignmentController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
    
    
}
