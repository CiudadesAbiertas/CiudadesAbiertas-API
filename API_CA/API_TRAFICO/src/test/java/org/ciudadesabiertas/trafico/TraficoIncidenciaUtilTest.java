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

package org.ciudadesabiertas.trafico;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.TraficoIncidencia;
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

public class TraficoIncidenciaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey", "latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
		"      \"id\": \"TRAFINCI01\",\r\n" + 
		"      \"description\": \"Corte de calle\",\r\n" + 
		"      \"tipoIncidencia\": \"obra\",\r\n" + 
		"      \"datePosted\": \"2020-03-31T08:00:00\",\r\n" + 
		"      \"numSentidos\": 2,\r\n" + 
		"      \"numCarriles\": 8,\r\n" + 
		"      \"esRecurrente\": false,\r\n" + 
		"      \"fechaFinPrevista\": \"2020-05-03T23:59:00\",\r\n" + 
		"      \"recurrencia\": \"Sin recurrencia\",\r\n" + 
		"      \"incidenciaEnTramo\": \"TRAFTRAM01\",\r\n" + 
		"      \"latitud\": 40.42020937,\r\n" + 
		"      \"longitud\": -3.70579377,\r\n" + 
		"      \"portalId\": \"PORTAL000010\",\n" + 
		"      \"streetAddress\": \"Bravo Murillo 111\",\r\n" + 
		"      \"postalCode\": \"28039\",\r\n" + 
		"      \"municipioId\": \"madrid\",\r\n" + 
		"      \"municipioTitle\": \"Madrid\",\r\n" + 
		"      \"barrioId\": \"bellas-vistas\",\r\n" + 
		"      \"barrioTitle\": \"Bellas Vistas\",\r\n" + 
		"      \"distritoId\": \"tetuan\",\r\n" + 
		"      \"distritoTitle\": \"Tetuán\",\r\n" + 
		"      \"xETRS89\": 440124.33,\r\n" + 
		"      \"yETRS89\": 4474637.17\r\n" + 
		"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		TraficoIncidencia mappedObj = mapper.readValue(testJSON, TraficoIncidencia.class);
		TraficoIncidencia item = new TraficoIncidencia(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		TraficoIncidencia mappedObj = mapper.readValue(testJSON, TraficoIncidencia.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		TraficoIncidencia item = new TraficoIncidencia(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
