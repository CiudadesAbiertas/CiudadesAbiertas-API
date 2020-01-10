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

package org.ciudadesabiertas.aparcamiento;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Equipamiento;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class AparcamientoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"EQAP0001\",\r\n" + 
			"    \"title\" : \"Aparcamiento mixto. Arquitecto Ribera\",\r\n" + 
			"    \"tipoEquipamiento\" : \"aparcamiento publico\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"provincia\" : \"Madrid\",\r\n" + 
			"    \"autonomia\" : \"Comunidad de Madrid\",\r\n" + 
			"    \"pais\" : \"España\",\r\n" + 
			"    \"streetAddress\" : \"CALLE BARCELO V 2\",\r\n" + 
			"    \"postalCode\" : \"28004\",\r\n" + 
			"    \"barrio\" : \"JUSTICIA\",\r\n" + 
			"    \"distrito\" : \"CENTRO\",\r\n" +
			"    \"email\" : \"ciudadesAbiertas@ciudadesAbiertas.com\",\r\n" +
			"    \"telephone\" : \"555 67 67\",\r\n" +
			"    \"url\" : \"http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=a50e15cbed51c010VgnVCM2000000c205a0aRCRD\",\r\n" + 
			"    \"titularidad\" : \"AYTO MADRID\",\r\n" + 
			"    \"openingHours\" : \"Sin Horario\",\r\n" + 
			"    \"description\" : \"Plazas: 318 públicas y 298 para residentes Abierto 24 horas Admite Tarjeta BonoRed Titularidad: Ayuntamiento de Madrid\",\r\n" + 
			"    \"xETRS89\" : 440654.00047,\r\n" + 
			"    \"yETRS89\" : 4475352.11778\r\n" + 
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Equipamiento mappedObj = mapper.readValue(testJSON, Equipamiento.class);
		Equipamiento aparcamiento = new Equipamiento(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(aparcamiento, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Equipamiento mappedObj = mapper.readValue(testJSON, Equipamiento.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Equipamiento aparcamiento = new Equipamiento(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(aparcamiento, fieldsToIngore);
		assertTrue(allFields);
	}

}