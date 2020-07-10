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

package org.ciudadesabiertas.presupuesto;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.EjecucionGasto;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EjecucionGastoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","periodoEjecucionBeginning","periodoEjecucionEnd" };
	
	private static final String testJSON = " {\r\n" + 
			"      \"id\": \"EJEGASTO01\",\r\n" + 
			"      \"remanenteCredito\": 67543,\r\n" + 
			"      \"creditoModificaciones\": 0,\r\n" + 
			"      \"creditoDefinitivoVigente\":  57543,\r\n"+
			"      \"creditoAutorizado\":  65439,\r\n"+
			"      \"totalGastoComprometido\":  75439,\r\n"+
			"      \"creditoDisponible\":  68,\r\n"+
			"      \"creditoRetenido\":  0,\r\n"+
			"      \"obligacionesReconocidasNetas\":  65371,\r\n"+
			"      \"pagos\":  0,\r\n"+
			"      \"obligacionesPendientes31Dic\":  0,\r\n"+
			"      \"periodoEjecucionBeginning\":  \"2019-01\",\r\n"+
			"      \"periodoEjecucionEnd\":  \"2019-11\",\r\n"+
			"      \"presupuestoGasto\":  \"PREGASTO01\"\r\n"+
			"    }";
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		EjecucionGasto mappedObj = mapper.readValue(testJSON, EjecucionGasto.class);
		EjecucionGasto item = new EjecucionGasto(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		EjecucionGasto mappedObj = mapper.readValue(testJSON, EjecucionGasto.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		EjecucionGasto item = new EjecucionGasto(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
