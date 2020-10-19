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

import org.ciudadesabiertas.dataset.model.ContAcusticaEstacionMedida;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContAcusticaEstacionMedidaUtilTest
{
	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated","equipamientoIdIsolated" };
	
	private static final String testJSON = " {\r\n" + 			
			"	 \"id\" : \"CONTACUSTESTMED001\",\r\n" +
			"    \"title\" : \"Dispositivo que detecta los ruidos I.\",\r\n" +
			"    \"fechaAlta\" : \"2020-03-31T08:00:00\",\r\n" +
			"    \"fechaBaja\" : \"2020-07-30T09:00:00\",\r\n" +
			"    \"streetAddress\" : \"CL BLAS DE OTERO 4\",\r\n" +
			"    \"postalCode\" : \"28100\",\r\n" +
			"    \"portalId\" : \"PORTAL000119\",\r\n" +
			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
			"    \"equipamientoId\" : \"EQ0044\",\r\n" +
			"    \"tipoEquipamiento\" : \"Aparato de medición A.C.M.E\",\r\n" +
			"    \"observes\" : \"nivelRuido\",\r\n" +
			"    \"municipioId\": \"28006\",\r\n" +
			"    \"municipioTitle\": \"Alcobendas\",\r\n" +
			"    \"barrioId\": \"28006011\",\r\n" +
			"    \"barrioTitle\": \"Norte\",\r\n" +
			"    \"distritoId\": \"2800601\",\r\n" +
			"    \"distritoTitle\": \"Unico\",\r\n" +
			"    \"latitud\" : 40.42020937,\r\n" +
			"    \"longitud\" : -3.70579377,\r\n" +
			"    \"xETRS89\" : 440124.33000,\r\n" +
			"    \"yETRS89\" : 4474637.17000\r\n" + 
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		ContAcusticaEstacionMedida mappedObj = mapper.readValue(testJSON, ContAcusticaEstacionMedida.class);
		ContAcusticaEstacionMedida item = new ContAcusticaEstacionMedida(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		ContAcusticaEstacionMedida mappedObj = mapper.readValue(testJSON, ContAcusticaEstacionMedida.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		ContAcusticaEstacionMedida item = new ContAcusticaEstacionMedida(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
