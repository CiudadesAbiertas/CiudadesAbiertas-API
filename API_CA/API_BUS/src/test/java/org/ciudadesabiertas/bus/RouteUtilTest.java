/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Route;
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
public class RouteUtilTest {
	
	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\r\n" + 	
			"	 \"id\" : \"BUSTEST001\",\r\n" +
			"    \"description\" : \"Línea 6, comienzo en plaza de Jacinto Benavente y final en Orcasitas\",\r\n" +
			"    \"directionType\" : \"outbound\",\r\n" +
			"    \"onId\" : \"6\"\r\n" +
			"    }";
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Route mappedObj = mapper.readValue(testJSON, Route.class);
		Route item = new Route(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Route mappedObj = mapper.readValue(testJSON, Route.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Route item = new Route(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
}
