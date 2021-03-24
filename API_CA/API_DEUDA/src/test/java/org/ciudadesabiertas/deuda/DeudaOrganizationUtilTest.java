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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.DeudaOrganization;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeudaOrganizationUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","portalIdIsolated" };
	
	private static final String testJSON = " {\n"
		+ "      \"id\": \"02-50-297-AO-005\",\n"
		+ "      \"title\": \"IMAFE. Instituto para la Formación y Empleo\",\n"
		+ "      \"url\": \"https://www.imafe.org\",\n"
		+ "      \"municipioId\": \"28003\",\n"
		+ "      \"municipioTitle\": \"Madrid\",\n"
		+ "      \"distritoId\": \"12313\",\n"
		+ "      \"distritoTitle\": \"Cuatro Caminos\",\n"
		+ "      \"streetAddress\": \"CALLE  SAN GERMAN,  11  PLANTA  entreplanta  derecha\",\n"
		+ "      \"postalCode\": \"28020  \",\n"
		+ "      \"portalId\": \"28020  \",\n"
		+ "      \"classification\": \"28020  \",\n"
		+ "      \"contactPointEmail\": \"infoformacion@imafe.org \",\n"
		+ "      \"contactPointTitle\": \"Plaza de prueba\",\n"
		+ "      \"contactPointFaxNumber\": \"917 269 807\",\n"
		+ "      \"contactPointTelephone\": \"917 269 807\"\n"
		+ "    }";


	
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		DeudaOrganization mappedObj = mapper.readValue(testJSON, DeudaOrganization.class);
		DeudaOrganization item = new DeudaOrganization(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		DeudaOrganization mappedObj = mapper.readValue(testJSON, DeudaOrganization.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		DeudaOrganization item = new DeudaOrganization(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
