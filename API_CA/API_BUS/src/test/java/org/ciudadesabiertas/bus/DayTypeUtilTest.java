/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.ciudadesabiertas.dataset.model.DayType;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class DayTypeUtilTest {
	
	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\r\n" + 	
			"	 \"id\" : \"festivo\",\r\n" +
			"    \"title\" : \"Día festivo\",\r\n" +
			"    \"description\" : \"Horario general para el servicio de EMT en día festivo.\",\r\n" +
			"    \"shortName\" : \"Festivos\",\r\n" +
			"    \"earliestTime\" : \"23:59:59\"\r\n" +
			"    }";
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		
		//TIMER
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// Y con este formato
		mapper.setDateFormat(new SimpleDateFormat(Constants.TIME_FORMAT));
		
		// JSON file to Java object
		DayType mappedObj = mapper.readValue(testJSON, DayType.class);
		
		
		DayType item = new DayType(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		
		//TIMER
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// Y con este formato
		mapper.setDateFormat(new SimpleDateFormat(Constants.TIME_FORMAT));

		// JSON file to Java object
		DayType mappedObj = mapper.readValue(testJSON, DayType.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		DayType item = new DayType(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
}
