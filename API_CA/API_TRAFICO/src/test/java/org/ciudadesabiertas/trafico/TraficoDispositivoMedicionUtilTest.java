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

	private static final String[] fieldsToIngore = { "ikey", "latitud","longitud","distance", "portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
		"      \"id\": \"TRAFDISMED02\",\r\n" + 
		"      \"description\": \"C. GRAN VIA;San Bernardo-Garcia Molinas;San Bernardo\",\r\n" + 
		"      \"numSentidos\": 2,\r\n" + 
		"      \"numCarriles\": 8,\r\n" + 
		"      \"urbano\": true,\r\n" + 
		"      \"tipoEquipoTrafico\": \"detector-bluetooth\",\r\n" + 
		"      \"monitorea\": \"TRAFTRAM02\",\r\n" + 
		"      \"enServicio\": true,\r\n" + 
		"      \"frecuenciaMedicion\": \"3 minutos\",\r\n" + 
		"      \"observes\": \"carga\",\r\n" + 
		"      \"latitud\": 40.43051427,\r\n" + 
		"      \"longitud\": -3.71624419,\r\n" + 
		"      \"portalId\": \"PORTAL000010\",\n" + 
		"      \"streetAddress\": \"Bravo Murillo 267\",\r\n" + 
		"      \"postalCode\": \"28039\",\r\n" + 
		"      \"municipioId\": \"madrid\",\r\n" + 
		"      \"municipioTitle\": \"Madrid\",\r\n" + 
		"      \"barrioId\": \"bellas-vistas\",\r\n" + 
		"      \"barrioTitle\": \"Bellas Vistas\",\r\n" + 
		"      \"distritoId\": \"tetuan\",\r\n" + 
		"      \"distritoTitle\": \"Tetuán\",\r\n" + 
		"      \"xETRS89\": 439247.039,\r\n" + 
		"      \"yETRS89\": 4475788.146\r\n" + 
		"    }";

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
