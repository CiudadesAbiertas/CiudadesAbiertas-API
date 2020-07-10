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

import org.ciudadesabiertas.dataset.model.TraficoDispositivoMedicion;
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

public class TraficoDispositivoMedicionUtilTest
{

	private static final String[] fieldsToIngore = { "ikey", "latitud","longitud","distance" };
	
	private static final String testJSON = " {\r\n" 
			+ "	  \"id\": \"TEST01_TRAFDISMED0001\",\r\n" 
			+ "   \"description\": \"Dispositivo que detecta los cambios que se producen en un campo electromagnético cuando circula un vehículo (masa metálica) sobre un punto determinado de la calzada. Registra el número total de vehículos que pasan y pueden clasificarlos por su longitud, número de ejes y masas.\",\r\n"
			+ "   \"numSentidos\": 2,\r\n"
			+ "   \"numCarriles\": 2,\r\n"
			+ "   \"urbano\": \"true\",\r\n"
			+ "   \"tipoEquipoTrafico\": \"lazo-magnetico\",\r\n"
			+ "   \"monitorea\": \"TRAFTRAM01\",\r\n"
			+ "   \"enServicio\": \"false\",\r\n"
			+ "   \"frecuenciaMedicion\": \"P1m\",\r\n"
			+ "   \"observes\": \"carga\",\r\n"
			+ "   \"xETRS89\": 440124.33000,\r\n"
			+ "   \"yETRS89\": 4474637.17000\r\n"
			+ "    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		TraficoDispositivoMedicion mappedObj = mapper.readValue(testJSON, TraficoDispositivoMedicion.class);
		TraficoDispositivoMedicion item = new TraficoDispositivoMedicion(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		TraficoDispositivoMedicion mappedObj = mapper.readValue(testJSON, TraficoDispositivoMedicion.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		TraficoDispositivoMedicion item = new TraficoDispositivoMedicion(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
