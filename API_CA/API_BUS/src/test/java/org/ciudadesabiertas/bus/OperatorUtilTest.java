/**
 * 
 */
package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Operator;
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
public class OperatorUtilTest {
	
	private static final String[] fieldsToIngore = { "ikey", "portalIdIsolated" };
	
	private static final String testJSON = " {\r\n" + 	
			"	 \"id\" : \"BUSTEST001\",\r\n" +
			"    \"servingPtFor\" : \"crtm\",\r\n" +
			"    \"telephone\" : \"+34 91 406 88 10\",\r\n" +
			"    \"email\" : \"info@emt.es\",\r\n" +
			"    \"url\" : \"https://www.emtmadrid.es/AtencionAlCliente/Agradecimientos\",\r\n" +
			"    \"portalId\" : \"PORTAL000010\",\r\n" +
			"    \"streetAddress\" : \"Calle Cerro de la Plata, 4\",\r\n" +
			"    \"postalCode\" : \"28007\",\r\n" +
			"    \"legalName\" : \"Empresa Municipal de Transportes\",\r\n" +
			"    \"alternateName\" : \"EMT\"\r,\n" +
			"      \"municipioId\": \"28006\",\r\n" +
			"      \"municipioTitle\": \"Alcobendas\",\r\n" +
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
		Operator mappedObj = mapper.readValue(testJSON, Operator.class);
		Operator item = new Operator(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Operator mappedObj = mapper.readValue(testJSON, Operator.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Operator item = new Operator(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
		
	}
	
}
