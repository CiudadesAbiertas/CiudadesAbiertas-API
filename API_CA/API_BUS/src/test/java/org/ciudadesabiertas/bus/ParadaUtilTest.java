/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Parada;
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
public class ParadaUtilTest {
	
	private static final String[] fieldsToIngore = { "ikey","portalIdIsolated","x","y","latitud","longitud","distance" };
	
	private static final String testJSON = " {\r\n" + 	
			"	 \"id\" : \"BUSTEST001\",\r\n" +
			"    \"title\" : \"Cuatro Caminos\",\r\n" +
			"    \"description\" : \"Cuatro Caminos\",\r\n" +
			"    \"url\" : \"https://emtmadrid.es/paradas/\",\r\n" +
			"    \"wifi\" : 0,\r\n" +
			"    \"panelElectronico\" : 1,\r\n" +
			"    \"zona\" : \"A\",\r\n" +
			"    \"portalId\" : \"PORTAL000010\",\r\n" +
			"    \"streetAddress\" : \"Calle Cerro de la Plata, 4\",\r\n" +
			"    \"postalCode\" : \"28007\",\r\n" +
			"    \"xETRS89\" : 440242.56000,\r\n" + 
			"    \"yETRS89\" : 4476174.20000,\r\n" + 
			"    \"latitud\" : 40.43406413,\r\n" + 
			"    \"longitud\" : -3.70454469,\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" +
			"    \"municipioTitle\" : \"Madrid\"\r,\n" +
			"      \"barrioId\": \"28006011\",\r\n" +
			"      \"barrioTitle\": \"Norte\",\r\n" +
			"      \"distritoId\": \"2800601\",\r\n" +
			"      \"distritoTitle\": \"Unico\"\r\n" +
			"    }";
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Parada mappedObj = mapper.readValue(testJSON, Parada.class);
		Parada item = new Parada(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Parada mappedObj = mapper.readValue(testJSON, Parada.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Parada item = new Parada(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
}
