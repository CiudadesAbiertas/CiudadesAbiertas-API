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

package org.ciudadesabiertas.bicicletapublica;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.BicicletaPublicaPuntoPaso;
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

public class BicicletaPublicaPuntoPasoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey", "latitud","longitud","distance", "portalIdIsolated" };
	
	private static final String testJSON = " {\r\n" + 
			"      \"id\": \"TEST01_PPASO0001\",\r\n" 
			+ "   \"fechaPaso\": \"2020-01-09T07:00:00\",\r\n"
			+ "   \"trayectoId\": \"TRA04\",\r\n"
			+ "   \"orden\": \"1\",\r\n"
			+ "   \"xETRS89\": 440124.33000,\r\n"
			+ "   \"yETRS89\": 4474637.17000,\r\n"
			+ "   \"portalId\": \"PORTAL000101\",\r\n"
			+ "   \"streetAddress\": \"CALLE DE RAIMUNDO FERNÁNDEZ VILLAVERDE NUMERO 43\",\r\n"
			+ "   \"postalCode\": \"28003\",\r\n"
			+ "   \"barrioId\": \"bellas-vistas\",\r\n"
			+ "   \"barrioTitle\": \"Bellas Vistas\",\r\n"
			+ "   \"distritoId\": \"tetuan\",\r\n"
			+ "   \"distritoTitle\": \"Tetuán\",\r\n"
			+ "   \"municipioId\": \"madrid\",\r\n"
			+ "   \"municipioTitle\": \"Madrid\"\r\n"
			+ "    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		BicicletaPublicaPuntoPaso mappedObj = mapper.readValue(testJSON, BicicletaPublicaPuntoPaso.class);
		BicicletaPublicaPuntoPaso item = new BicicletaPublicaPuntoPaso(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		BicicletaPublicaPuntoPaso mappedObj = mapper.readValue(testJSON, BicicletaPublicaPuntoPaso.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		BicicletaPublicaPuntoPaso item = new BicicletaPublicaPuntoPaso(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
