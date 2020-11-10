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
public class ParadaDataRSQLTest {
	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL = ParadaController.LIST;
	
	private final String paramField = "q";
	
	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception
	{

		String value = "id=='1918'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Busqueda_title() throws Exception
	{

		String value = "title=='Cuatro Caminos'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{

		String value = "description=='Cuatro Caminos'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);

	}
	
	@Test
	public void test_Busqueda_url() throws Exception
	{

		String value = "url=='https://emtmadrid.es/paradas*'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_wifi() throws Exception
	{

		String value = "wifi==false";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_panelElectronico() throws Exception
	{

		String value = "panelElectronico==true";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_zona() throws Exception
	{

		String value = "zona=='A'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{

		String value = "portalId=='PORTAL000010'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_streetAddress() throws Exception
	{

		String value = "streetAddress=='Calle Cerro de la Plata, 4'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_postalCode() throws Exception
	{

		String value = "postalCode=='28007'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 5);

	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{

		String value = "municipioId=='28079'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_municipioTitle() throws Exception
	{

		String value = "municipioTitle=='Madrid'";

		

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);

	}
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
			
		String value = "barrioId=='28079011'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
			
		String value = "barrioTitle=='Norte'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 6);
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{			
		
		String value = "distritoId=='2807901'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{			
		
		String value = "distritoTitle=='Unico'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 6);
	}
}
