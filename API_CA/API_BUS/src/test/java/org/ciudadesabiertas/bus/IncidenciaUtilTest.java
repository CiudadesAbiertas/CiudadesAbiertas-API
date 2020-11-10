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

import org.ciudadesabiertas.dataset.model.Incidencia;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidenciaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","incidenciaEnTramoIdIsolated", "portalIdIsolated" };
	
	private static final String testJSON = "{\n" + 
		"      \"id\": \"18059944-382A-49AA-A068-B55BF2FAC51F\",\n" + 
		"      \"description\": \"Corte de calles entre el cruce de Alcalá y  la Plaza de Sol\",\n" + 
		"      \"x\": 440124.33,\n" + 
		"      \"y\": 4474637.17,\n" + 
		"      \"latitud\": 40.42020937,\n" + 
		"      \"longitud\": -3.70579377,\n" + 
		"      \"tipoIncidencia\": \"obras\",\n" + 
		"      \"datePosted\": \"2020-02-28T08:00:00\",\n" + 
		"      \"numSentidos\": 2,\n" + 
		"      \"numCarriles\": 8,\n" + 
		"      \"esRecurrente\": false,\n" + 
		"      \"fechaFinPrevista\": \"2020-07-03T23:59:00\",\n" + 
		"      \"incidenciaEnTramo\": \"TRAFTRAM01\",\n" + 
		"      \"incidenciaTramoDescription\": \"Calles entre el cruce de Alcalá con Gran Vía y la Plaza de la Independencia\",\n" + 
		"      \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
		"      \"postalCode\" : \"28100\",\r\n" +
		"      \"portalId\" : \"PORTAL000119\",\r\n" +
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
		Incidencia mappedObj = mapper.readValue(testJSON, Incidencia.class);
		Incidencia item = new Incidencia(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Incidencia mappedObj = mapper.readValue(testJSON, Incidencia.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Incidencia item = new Incidencia(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
