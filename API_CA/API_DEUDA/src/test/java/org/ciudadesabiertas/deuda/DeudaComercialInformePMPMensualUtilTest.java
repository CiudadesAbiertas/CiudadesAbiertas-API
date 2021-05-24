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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.DeudaComercialInformePMPMensual;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeudaComercialInformePMPMensualUtilTest
{

	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = "{\n"
			+ "      \"id\": \"02-50-297-AO-005\",\n"
			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
			+ "      \"periodo\": \"2020-06\" \n"
			+ "    }";


	
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{
		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		DeudaComercialInformePMPMensual mappedObj = mapper.readValue(testJSON, DeudaComercialInformePMPMensual.class);
		DeudaComercialInformePMPMensual item = new DeudaComercialInformePMPMensual(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		DeudaComercialInformePMPMensual mappedObj = mapper.readValue(testJSON, DeudaComercialInformePMPMensual.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		DeudaComercialInformePMPMensual item = new DeudaComercialInformePMPMensual(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
