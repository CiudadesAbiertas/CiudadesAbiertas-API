/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ParadaController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
public class ParadaDataTest {
	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;
	
	private String listURL = ParadaController.LIST;
	
	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";
		
		String value = "1918";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_title() throws Exception
	{
		String paramField="title";
		
		String value = "Cuatro Caminos";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		String paramField="description";
		
		String value = "Cuatro Caminos";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{
		String paramField="url";
		
		String value = "https://emtmadrid.es/paradas*";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_wifi() throws Exception
	{
		String paramField="wifi";
		
		String value = "0";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_panel_electronico() throws Exception
	{
		String paramField="panelElectronico";
		
		String value = "1";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_zona() throws Exception
	{
		String paramField="zona";
		
		String value = "A";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_portal_id() throws Exception
	{
		String paramField="portalId";
		
		String value = "PORTAL000010";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_street_address() throws Exception
	{
		String paramField="streetAddress";
		
		String value = "Calle Cerro de la Plata, 4";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_postal_code() throws Exception
	{
		String paramField="postalCode";
		
		String value = "28007";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);
	}
	
	@Test
	public void test_Busqueda_municipio_id() throws Exception
	{
		String paramField="municipioId";
		
		String value = "28079";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_municipio_title() throws Exception
	{
		String paramField="municipioTitle";
		
		String value = "Madrid";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
			
		String [] paramField= {"barrioId"};

		
		String [] value = {"28079011"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
			
		String [] paramField= {"barrioTitle"};

		
		String [] value = {"Norte"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{			
		
		String [] paramField= {"distritoId"};

		
		String [] value = {"2807901"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{			
		
		String [] paramField= {"distritoTitle"};

		
		String [] value = {"Unico"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}
}
