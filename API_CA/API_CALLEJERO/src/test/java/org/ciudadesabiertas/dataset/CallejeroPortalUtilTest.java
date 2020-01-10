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

package org.ciudadesabiertas.dataset;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.CallejeroPortal;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class CallejeroPortalUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"PORTAL000001\",\r\n" + 
			"    \"via\" : \"114600\",\r\n" + 
			"    \"streetAddress\" : \"CALLE DE BRAVO MURILLO NUMERO 1\",\r\n" + 
			"    \"postalCode\" : \"28015\",\r\n" + 
			"    \"latitud\" : 40.43406413,\r\n" + 
			"    \"longitud\" : -3.70454469,\r\n" + 
			"    \"barrioId\" : \"Rios Rosas\",\r\n" + 
			"    \"distritoId\" : \"Chamberí\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"provinciaId\" : \"Madrid\",\r\n" + 
			"    \"autonomiaId\" : \"Comunidad de Madrid\",\r\n" + 
			"    \"xETRS89\" : 440242.56000,\r\n" + 
			"    \"yETRS89\" : 4476174.20000\r\n" + 
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		CallejeroPortal mappedObj = mapper.readValue(testJSON, CallejeroPortal.class);
		CallejeroPortal CallejeroPortal = new CallejeroPortal(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroPortal, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		CallejeroPortal mappedObj = mapper.readValue(testJSON, CallejeroPortal.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		CallejeroPortal CallejeroPortal = new CallejeroPortal(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroPortal, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
