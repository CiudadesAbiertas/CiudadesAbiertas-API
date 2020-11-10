/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.bus;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Authority;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthorityUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance", "portalIdIsolated" };
	
	private static final String testJSON = " {\r\n" + 
			"      \"id\": \"crtm\",\r\n" + 
			"      \"legalName\": \"Consorcio de transportes de Madrid\",\r\n" + 
			"      \"alternateName\": \"CRTM\",\n" + 
			"      \"telephone\": \"+34 91 406 88 10\",\n" + 
			"      \"email\": \"info@crtm.es\",\n" + 
			"      \"url\": \"info@crtm.es\",\n" + 
			"      \"portalId\": \"PORTAL000010\",\n" + 
			"      \"streetAddress\": \"Calle Cerro de la Plata, 4\",\n" + 
			"      \"postalCode\": \"28007\",\n" +
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
		Authority mappedObj = mapper.readValue(testJSON, Authority.class);
		Authority item = new Authority(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Authority mappedObj = mapper.readValue(testJSON, Authority.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Authority item = new Authority(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
