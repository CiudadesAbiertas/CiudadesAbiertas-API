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

package org.ciudadesabiertas.contaminacionacustica;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.ContAcusticaObservacion;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContAcusticaObservacionUtilTest
{
	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\r\n" + 			
			"	 \"id\" : \"TEST01_CONTACUSTOBVD001\",\r\n" +
			"    \"observedProperty\" : \"nivelRuido\",\r\n" +
			"    \"madeBySensor\" : \"CONTACUSTESTMED001\",\r\n" +
			"    \"resultTime\" : \"2020-04-15T08:00:00\",\r\n" +
			"    \"hasSimpleResult\" : 135.01,\r\n" +
			"    \"validada\" : false,\r\n" +
			"    \"tipoMedicion\" : \"LAS50\",\r\n" +
			"    \"tipoEmisorPredominante\" : \"ferrocarriles\",\r\n" +
			"    \"tipoIntervaloReferencia\" : \"D\"\r\n" +
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		ContAcusticaObservacion mappedObj = mapper.readValue(testJSON, ContAcusticaObservacion.class);
		ContAcusticaObservacion item = new ContAcusticaObservacion(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		ContAcusticaObservacion mappedObj = mapper.readValue(testJSON, ContAcusticaObservacion.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		ContAcusticaObservacion item = new ContAcusticaObservacion(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
