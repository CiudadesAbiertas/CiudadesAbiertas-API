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

package org.ciudadesabiertas.empleo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.OfertaEmpleoPublico;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OfertaEmpleoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\n" + 
			  "    \"id\": \"oferta001\",\n" + 
			  "    \"title\": \"Oferta Informatica\",\n" +
			  "    \"description\": \"Oferta Informatica para el Ayuntamiento\", \n" +
			  "    \"dateModified\": \"2020-11-17T12:00:00\", \n" +
			  "    \"datePublished\": \"2020-11-24T11:00:00\", \n" +
			  "    \"fechaAprobacion\": \"2020-11-24T12:00:00\", \n" +
			  "    \"municipioId\": \"28079\", \n" +
			  "    \"municipioTitle\": \"Madrid\", \n" +
			  "    \"provinciaId\": \"28\", \n" +
			  "    \"provinciaTitle\": \"Madrid\", \n" +
			  "    \"autonomiaId\": \"13\", \n" +
			  "    \"autonomiaTitle\": \"Comunidad de Madrid\", \n" +
			  "    \"boletinOficialId\": \"boletin001\" " +
		"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		OfertaEmpleoPublico mappedObj = mapper.readValue(testJSON, OfertaEmpleoPublico.class);
		OfertaEmpleoPublico item = new OfertaEmpleoPublico(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		OfertaEmpleoPublico mappedObj = mapper.readValue(testJSON, OfertaEmpleoPublico.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		OfertaEmpleoPublico item = new OfertaEmpleoPublico(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
