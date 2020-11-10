/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.ServiceCalendar;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ServiceCalendarUtilTest {
	
	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\r\n" + 	
			"	 \"id\" : \"2020\",\r\n" +
			"    \"title\" : \"Calendario de servicio de la EMT Madrid para 2020\",\r\n" +
			"    \"description\" : \"Calendario de servicio de la EMT Madrid para 2020\",\r\n" +
			"    \"shortName\" : \"Calendario de servicio de la EMT Madrid para 2020\",\r\n" +
			"    \"from\" : \"2020-01-01T23:59:59\",\r\n" +
			"    \"to\" : \"2020-12-31T23:59:59\"\r\n" +
			"    }";
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		ServiceCalendar mappedObj = mapper.readValue(testJSON, ServiceCalendar.class);
		ServiceCalendar item = new ServiceCalendar(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		ServiceCalendar mappedObj = mapper.readValue(testJSON, ServiceCalendar.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		ServiceCalendar item = new ServiceCalendar(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
}
