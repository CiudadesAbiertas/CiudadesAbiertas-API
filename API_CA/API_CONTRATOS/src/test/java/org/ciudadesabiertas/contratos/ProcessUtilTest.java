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

package org.ciudadesabiertas.contratos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;


import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ciudadesabiertas.dataset.model.Process;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ProcessUtilTest
{


	private static final String[] fieldsToIngore = { "ikey"};
	private static final String testJSON = " {\n" + 
			"      \"id\": \"300-2018-00524\",\n" +
			"      \"identifier\": \"300/2018/00524\",\n" + 
			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
			"      \"isBuyerFor\": \"LA0007386\",\n" + 
			"      \"hasTender\": \"TN1\",\n" + 
			"      \"url\": \"https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D\",\n" + 
			"      \"description\": \"Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ\"\n" + 
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Process mappedObj = mapper.readValue(testJSON, Process.class);
		Process CallejeroTramo = new Process(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Process mappedObj = mapper.readValue(testJSON, Process.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Process CallejeroTramo = new Process(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
