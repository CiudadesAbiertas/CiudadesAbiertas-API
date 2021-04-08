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

package org.ciudadesabiertas.subvencion;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.SubvencionConcesion;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubvencionConcesionUtilTest
{
	private static final String[] fieldsToIngore = { "ikey","instrumentaIdIsolated","clasificacionProgramaSimple", "clasificacionEconomicaGastoSimple" };
	
	private static final String testJSON = "{\r\n"
			+ "      \"id\": \"SUBBEN1\",\r\n"
			+ "      \"importeSolicitado\": 2000,\r\n"
			+ "      \"importeConcedido\": 1600,\r\n"
			+ "      \"fechaSolicitud\": \"2017-10-26T00:00:00\",\r\n"
			+ "      \"fechaConcesion\": \"2018-10-26T00:00:00\",\r\n"
			+ "      \"convocatoria\": \"SUB1\",\r\n"
			+ "      \"beneficiario\": \"03401397L\",\r\n"
			+ "      \"clasificacionPrograma\": [\"334\"],\r\n"
			+ "      \"clasificacionEconomicaGasto\": [\"48\"],\r\n"
			+ "      \"programaAsText\": \"926\",\r\n"
			+ "      \"title\": \"just a title\",\r\n"
			+ "      \"gastoAsText\": \"13002\"\r\n"
			+ "    }";
	
	

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		SubvencionConcesion mappedObj = mapper.readValue(testJSON, SubvencionConcesion.class);
		SubvencionConcesion concesion = new SubvencionConcesion(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(concesion, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		SubvencionConcesion mappedObj = mapper.readValue(testJSON, SubvencionConcesion.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		SubvencionConcesion CallejeroTramo = new SubvencionConcesion(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
