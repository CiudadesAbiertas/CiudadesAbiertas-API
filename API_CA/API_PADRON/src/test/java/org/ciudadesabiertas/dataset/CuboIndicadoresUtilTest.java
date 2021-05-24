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

import org.ciudadesabiertas.dataset.model.CuboIndicadores;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CuboIndicadoresUtilTest
{

	private static final String[] fieldsToIngore = { "ikey", "dataset","dsd" };
	
	private static final String testJSON = "  {\n" + 
			"      \"id\": \"obs1\",\n" + 
			"      \"paisId\": \"123\",\n" + 
			"      \"paisTitle\": \"España\",\n" + 
			"      \"autonomiaId\": \"343\",\n" + 
			"      \"autonomiaTitle\": \"Comunidad de Madrid\",\n" + 
			"      \"municipioId\": \"28006\",\n" + 
			"      \"municipioTitle\": \"Alcobendas\",\n" + 
			"      \"distritoId\": \"2800601\",\n" + 
			"      \"distritoTitle\": \"Distrito 1\",\n" + 
			"      \"barrioId\": \"28006011\",\n" + 
			"      \"barrioTitle\": \"Barrio 1\",\n" + 
			"      \"seccionCensalId\": \"2800601020\",\n" + 
			"      \"seccionCensalTitle\": \"Sección Censal 20\",\n" + 
			"      \"refPeriod\": \"2016\",\n" + 
			"      \"numeroPersonas\": 82665,\n" + 
			"      \"indiceDependencia\": 25,\n" + 
			"      \"indiceFeminidad\": 116,\n" + 
			"      \"indiceInfancia\": 97,\n" + 
			"      \"indiceJuventud\": 29,\n" + 
			"      \"indiceMaternidad\": 29,\n" + 
			"      \"indicePoblacionActiva\": 36,\n" + 
			"      \"indiceReemplazo\": 26,\n" + 
			"      \"indiceSobreenvejecimiento\": 9,\n" + 
			"      \"indiceTendencia\": 24,\n" + 
			"      \"tasaMortalidad\": 369,\n" + 
			"      \"tasaNatalidad\": 225,\n" + 
			"      \"edadMediaPoblacion\": 82,\n" + 
			"      \"porcentajePoblacionJoven\": 21,\n" + 
			"      \"porcentajePoblacionAdulta\": 40,\n" + 
			"      \"porcentajePoblacionEnvejecida\": 73,\n" + 
			"      \"porcentajePoblacionExtranjeraInfantil\": 63,\n" +
			"      \"porcentajePoblacionExtranjera\": 74,\n" +
			"      \"porcentajePoblacionNacidaExtranjero\": 85\n" +
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		CuboIndicadores c=new CuboIndicadores();
		System.out.println(c.getPaisId());
		
		// JSON file to Java object
		CuboIndicadores mappedObj = mapper.readValue(testJSON, CuboIndicadores.class);
		CuboIndicadores item = new CuboIndicadores(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		CuboIndicadores mappedObj = mapper.readValue(testJSON, CuboIndicadores.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		CuboIndicadores item = new CuboIndicadores(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
