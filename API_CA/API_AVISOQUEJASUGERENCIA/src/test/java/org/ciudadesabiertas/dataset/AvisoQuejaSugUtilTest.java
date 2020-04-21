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

import org.ciudadesabiertas.dataset.model.AvisoQuejaSug;
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
public class AvisoQuejaSugUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"AQSA0001\",\r\n" + 
			"    \"title\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E\",\r\n" + 
			"    \"identifier\" : \"0-52\",\r\n" + 
			"    \"stat\" : \"abierto\",\r\n" + 
			"    \"statusNotes\" : \"abierto\",\r\n" + 
			"    \"openDate\" : \"2018-01-01T00:59:30\",\r\n" +
			"    \"updateDate\" : \"2018-01-01T01:59:30\",\r\n" + 
			"    \"closeDate\" : \"2018-01-01T02:59:30\",\r\n" + 
			"    \"details\" : \"AVISO-R RECOGIDA ANIMALES MUERTOS E-RECOGIDA DE ANIMALES MUERTOS\",\r\n" + 
			"    \"source\" : \"PRESENCIAL/TELEFONICOES\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"barrioId\" : \"280796062\",\r\n" + 
			"    \"distritoId\" : \"2807960\",\r\n" + 
			"    \"streetAddress\" : \"CALLE MAQUEDA 1 --\",\r\n" + 
			"    \"postalCode\" : \"28024\",\r\n" + 
			"    \"latitud\" : 40.39696028,\r\n" + 
			"    \"longitud\" : -3.76370471,\r\n" + 
			"    \"typeName\" : \"RECOGIDA DE ANIMALES MUERTOS\",\r\n" + 
			"    \"typeCode\" : \"3547\",\r\n" + 
			"    \"xETRS89\" : 435189.14000,\r\n" + 
			"    \"yETRS89\" : 4472097.38000,\r\n" + 
			"    \"portalId\" : \"PORTAL00008\"\r\n" +
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		AvisoQuejaSug mappedObj = mapper.readValue(testJSON, AvisoQuejaSug.class);
		AvisoQuejaSug AvisoQuejaSug = new AvisoQuejaSug(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(AvisoQuejaSug, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		AvisoQuejaSug mappedObj = mapper.readValue(testJSON, AvisoQuejaSug.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		AvisoQuejaSug AvisoQuejaSug = new AvisoQuejaSug(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(AvisoQuejaSug, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
