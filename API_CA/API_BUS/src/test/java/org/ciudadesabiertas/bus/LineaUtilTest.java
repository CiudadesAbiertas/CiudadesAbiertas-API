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

import org.ciudadesabiertas.dataset.model.Linea;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LineaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\n" + 
		"      \"id\": \"138\",\n" + 
		"      \"description\": \"La línea 138 de la EMT de Madrid une el Hospital Clínico San Carlos con la Colonia San Ignacio de Loyola, atravesando el eje Valmojado-Sepúlveda-Caramuel y el intercambiador de Aluche.\",\n" + 
		"      \"title\": \"Línea 138\",\n" + 
		"      \"url\": \"https://www.emtmadrid.es/Bloques-EMT/EMT-BUS/Mi-linea-(1).aspx?linea=138&lang=es-ES\",\n" + 
		"      \"shortName\": \"138\",\n" + 
		"      \"distance\": 58,\n" + 
		"      \"colour\": \"Azul\",\n" + 
		"      \"textColour\": \"Negro\",\n" + 
		"      \"operating\": \"emt\",\n" + 
		"      \"cabeceraLinea\": \"4608\",\n" + 
		"      \"finalLinea\": \"5481\"\n" + 
		"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object			
		Linea mappedObj = mapper.readValue(testJSON, Linea.class);
		Linea item = new Linea(mappedObj);	
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Linea mappedObj = mapper.readValue(testJSON, Linea.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Linea item = new Linea(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
