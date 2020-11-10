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
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncidenciaDataRSQLTest {
	
	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL = IncidenciaController.LIST;
	
	private final String paramField = "q";
	
	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_Id() throws Exception
	{
		String value = "id=='BUSINC01'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_description() throws Exception
	{
		String value = "description=='*alcalá*'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_x() throws Exception
	{
		String value = "x=='440124.33000'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_tipoIncidencia() throws Exception
	{
		String value = "tipoIncidencia=='obras'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_datePosted() throws Exception
	{
		String value = "datePosted=='2020-02-28T08:00:00'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numSentidos() throws Exception
	{
		String value = "numSentidos=='4'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_numCarriles() throws Exception
	{
		String value = "numCarriles=='8'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 2);
	}
	
	@Test
	public void test_Busqueda_esRecurrente() throws Exception
	{
		String value = "esRecurrente=='0'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_fechaFinPrevista() throws Exception
	{
		String value = "fechaFinPrevista=='2020-06-03T23:59:00'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_incidenciaEnTramo() throws Exception
	{
		String value = "incidenciaEnTramo=='TRAFTRAM01'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_incidenciaTramoDescription() throws Exception
	{
		String value = "incidenciaTramoDescription=='*Gran Vía*'";		
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_barrioId() throws Exception
	{
		
		String value = "barrioId=='28006011'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_barrioTitle() throws Exception
	{
				
		String value = "barrioTitle=='Norte'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 3);
	}
	
	@Test
	public void test_Busqueda_distritoId() throws Exception
	{			
		
		String value = "distritoId=='2800601'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_distritoTitle() throws Exception
	{			

		String value = "distritoTitle=='Unico'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 3);
	}
	
	@Test
	public void test_Busqueda_municipioId() throws Exception
	{
		
		String value ="municipioId=='28006' and id=='BUSINC01'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
			
	}
	
	
	@Test
	public void test_Busqueda_municipioNombre() throws Exception
	{		
		
		String value ="municipioTitle=='Alcobendas' and id=='BUSINC01'";

		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);

		assertTrue(records.size() == 1);
	}
	
}
