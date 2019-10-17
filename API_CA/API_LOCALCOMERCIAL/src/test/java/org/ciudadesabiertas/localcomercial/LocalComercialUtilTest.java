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

package org.ciudadesabiertas.localcomercial;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.LocalComercial;
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
public class LocalComercialUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"270002391\",\r\n" + 
			"    \"title\" : \"OGAME\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"streetAddress\" : \"Calle Bravo Murillo Num 360\",\r\n" + 
			"    \"postalCode\" : \"28039\",\r\n" + 
			"    \"barrio\" : \"Castillejos\",\r\n" + 
			"    \"distrito\" : \"Tetuan\",\r\n" + 
			"    \"latitud\" : 40.46489928,\r\n" + 
			"    \"longitud\" : -3.69355444,\r\n" + 
			"    \"telephone\" : \"919999991\",\r\n" + 
			"    \"url\" : \"http://api.ciudadesabiertas.org/id=270002391\",\r\n" + 
			"    \"tipoActividadEconomica\" : \"90\",\r\n" + 
			"    \"nombreComercial\" : \"Nombre Comercial OGAME\",\r\n" + 
			"    \"rotulo\" : \"Rotulo OGAME\",\r\n" + 
			"    \"aforo\" : 3,\r\n" +
			"    \"tieneTerraza\" : \"test\",\r\n" +
			"    \"agrupacionComercial\" : \"test\",\r\n" +
			"    \"tipoSituacion\" : \"activo\",\r\n" + 
			"    \"tipoAcceso\" : \"puerta de calle\",\r\n" + 
			"    \"referenciaCatastral\" : \"9872023 VH5797S 0001 WX\",\r\n" + 
			"    \"tieneLicenciaApertura\" : \"270002391/106-2003-01539\",\r\n" + 
			"    \"description\" : \"Descripcion OGAME : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book\",\r\n" + 
			"    \"xETRS89\" : 441201.61000,\r\n" + 
			"    \"yETRS89\" : 4479589.52999\r\n" + 
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		LocalComercial mappedObj = mapper.readValue(testJSON, LocalComercial.class);
		LocalComercial CallejeroTramo = new LocalComercial(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		LocalComercial mappedObj = mapper.readValue(testJSON, LocalComercial.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		LocalComercial CallejeroTramo = new LocalComercial(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
