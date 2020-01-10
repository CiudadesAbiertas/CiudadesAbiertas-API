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

package org.ciudadesabiertas.DataCube;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CubeDsdUtilTest
{

	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\n" + 
			"      \"id\": \"distritoEdad\",\n" + 
			"      \"title\": \"Cubo de Población Edad y Distrito\",\n" + 
			"      \"notation\": \"distritoEdad\",\n" + 
			"      \"dimension\": [\n" + 
			"        \"distrito\",\n" + 
			"        \"edad\"\n" + 
			"      ],\n" + 
			"      \"measure\": [\n" + 
			"        \"habitantes\"\n" + 
			"      ]\n" + 
			"    }";	
	    
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		CubeDsd mappedObj = mapper.readValue(testJSON, CubeDsd.class);
		CubeDsd item = new CubeDsd(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		CubeDsd mappedObj = mapper.readValue(testJSON, CubeDsd.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		CubeDsd item = new CubeDsd(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
