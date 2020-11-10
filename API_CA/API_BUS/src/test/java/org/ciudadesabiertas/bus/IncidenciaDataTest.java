/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.IncidenciaController;
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
public class IncidenciaDataTest {
	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;
	
	private String listURL = IncidenciaController.LIST;
	
	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String paramField="id";		
		String value = "BUSINC01";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		String paramField="description";		
		String value = "*alcalá*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_x() throws Exception
	{
		String paramField="x";		
		String value = "440124.33000";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_y() throws Exception
	{
		String paramField="y";		
		String value = "4474637.17000";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_tipoIncidencia() throws Exception
	{
		String paramField="tipoIncidencia";		
		String value = "obras";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_datePosted() throws Exception
	{
		String paramField="datePosted";		
		String value = "2020-02-28 08:00:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numSentidos() throws Exception
	{
		String paramField="numSentidos";		
		String value = "4";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numCarriles() throws Exception
	{
		String paramField="numCarriles";		
		String value = "8";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_esRecurrente() throws Exception
	{
		String paramField="esRecurrente";		
		String value = "0";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_fechaFinPrevista() throws Exception
	{
		String paramField="fechaFinPrevista";		
		String value = "2020-06-03 23:59:00";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_incidenciaEnTramo() throws Exception
	{
		String paramField="incidenciaEnTramo";		
		String value = "TRAFTRAM01";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_incidenciaTramoDescription() throws Exception
	{
		String paramField="incidenciaTramoDescription";		
		String value = "*Gran Vía*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
			
		String [] paramField= {"barrioId"};

		
		String [] value = {"28006011"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
			
		String [] paramField= {"barrioTitle"};

		
		String [] value = {"Norte"};


		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{			
		
		String [] paramField= {"distritoId"};

		
		String [] value = {"2800601"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{			
		
		String [] paramField= {"distritoTitle"};

		
		String [] value = {"Unico"};

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		String [] paramField= {"municipioId","id"};

		String [] value = {"28006","BUSINC01"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String [] paramField= {"municipioTitle","id"};

		String [] value = {"Alcobendas","BUSINC01"};

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
}
