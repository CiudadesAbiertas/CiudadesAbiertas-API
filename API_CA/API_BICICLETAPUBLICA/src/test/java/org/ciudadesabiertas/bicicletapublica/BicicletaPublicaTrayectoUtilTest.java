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

import org.ciudadesabiertas.dataset.model.BicicletaPublicaTrayecto;
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

public class BicicletaPublicaTrayectoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = " {\r\n" + 
			"      \"id\": \"TEST01_TRA0001\",\r\n"  
			+ "   \"startDate\": \"2020-01-09T08:00:00\",\r\n"
			+ "   \"endDate\": \"2020-01-09T07:30:00\",\r\n"
			+ "   \"usuarioId\": \"USU02\",\r\n"
			+ "   \"bicicletaId\": \"BIC06\",\r\n"
			+ "   \"estacionBicicletaOrigenId\": \"EST02\",\r\n"
			+ "   \"estacionBicicletaDestinoId\": \"EST01\",\r\n"
			+ "   \"anclajeOrigenId\": \"ANC06\",\r\n"
			+ "   \"anclajeDestinoId\": \"ANC01\"\r\n"
			+ "    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		BicicletaPublicaTrayecto mappedObj = mapper.readValue(testJSON, BicicletaPublicaTrayecto.class);
		BicicletaPublicaTrayecto item = new BicicletaPublicaTrayecto(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		BicicletaPublicaTrayecto mappedObj = mapper.readValue(testJSON, BicicletaPublicaTrayecto.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		BicicletaPublicaTrayecto item = new BicicletaPublicaTrayecto(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
