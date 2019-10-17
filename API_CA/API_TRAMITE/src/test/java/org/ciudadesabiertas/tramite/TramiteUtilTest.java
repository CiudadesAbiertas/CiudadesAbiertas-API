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

package org.ciudadesabiertas.tramite;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Tramite;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TramiteUtilTest
{

	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = "{\r\n" + 
			"      \"id\": \"TR0001\",\r\n" + 
			"      \"title\": \"Apoyo a la Formación en las Asociaciones juveniles\",\r\n" + 
			"      \"description\": \"<p>Se pretende ayudar a grupos promotores de asociaciones o a entidades ya constituidas a mejorar su funcionamiento y sus resultados.</p>\",\r\n" + 
			"      \"permiteTramitacionEnLinea\": true,\r\n" + 
			"      \"permiteTramitacionPresencial\": true,\r\n" + 
			"      \"permiteTramitacionTelefono\": true,\r\n" + 
			"      \"permiteTramitationCorreoPostal\": true,\r\n" +			
			"	   \"url\": \"http://www.zaragoza.es/api/recurso/sector-publico/procedimiento-tramite/5503\",\r\n" +
			"	   \"impreso\": \"http://www.zaragoza.es/api/recurso/sector-publico/procedimiento-tramite/5503\",\r\n" +
			"      \"sameAs\": \"http://www.zaragoza.es/api/recurso/sector-publico/procedimiento-tramite/5503\",\r\n" +
			"      \"detalleTramitacionTelefono\": \"documentación teléfono\",\r\n" + 
			"      \"detalleTramitacionPresencial\": \"documentación presencial\",\r\n" +
			"      \"detalleTramitacionEnLinea\": \"documentación en linea\",\r\n" +
			"      \"detalleTramitacionCorreoPostal\": \"documentación correo postal\",\r\n" +			
			"      \"normativa\": \"normativa\",\r\n" +
			"      \"pago\": \"pago\",\r\n" +
			"      \"requisitos\": \"requisitos\",\r\n" +
			"      \"organo\": \"organo principal\",\r\n" +
			"      \"fechaPlazoInicio\": \"2019-03-05\",\r\n" +
			"      \"fechaPlazoFin\": \"2019-08-05\",\r\n" +
			"      \"materia\": \"Salud\",\r\n" +
			"      \"fechaRespuesta\" : \"2019-06-11T09:00:00\",\r\n" +			
			"      \"fechaRespuestaTexto\" : \"Once de mayo de dos mil diecinueve a las nueve en punto\",\r\n" +			
			"      \"fechaPresentacion\" : \"2019-05-04T11:05:00\",\r\n" +
			"      \"fechaPresentacionTexto\" : \"Cuatro de abril de dos mill diecinueve a las once y cinco\",\r\n"+
			"      \"efectoSilencioAdministrativo\" : \"NEGATIVO\"\r\n" +			
			"    }";
	
	
	
	

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Tramite mappedObj = mapper.readValue(testJSON, Tramite.class);
		Tramite CallejeroTramo = new Tramite(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Tramite mappedObj = mapper.readValue(testJSON, Tramite.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Tramite CallejeroTramo = new Tramite(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
