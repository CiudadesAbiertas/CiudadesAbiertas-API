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

package org.ciudadesabiertas.agendamunicipal;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.AgendaMEvento;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgendaMEventoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","equipamientoIdIsolated","portalIdIsolated" };
	
	private static final String testJSON = " {\r\n" + 
			"	  \"id\": \"AGM0002\",\r\n" +
			"      \"title\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL. Profesionales\",\r\n" +
			"      \"url\": \"https://sede.madrid.es/portal/site/tramites/menuitem.5dd4485239c96e10f7a72106a8a409a0/?vgnextoid=e83703a8a6e82410vgnvcm2000000c205a0arcrd&vgnextchannel=e81965dd72ede410vgnvcm1000000b205a0arcrd&vgnextfmt=default\",\r\n" +
			"      \"startDate\": \"2019-12-08T10:00:00\",\r\n" +
			"      \"endDate\": \"2019-12-08T20:00:00\",\r\n" +
			"      \"locationTitle\": \"Auditorio del Comité Olímpico Español\",\r\n" +
			"      \"streetAddress\": \"CL BLAS DE OTERO 4\",\r\n" +
			"      \"postalCode\": \"28100\",\r\n" +
			"      \"portalId\": \"PORTAL000119\",\r\n" +
			"      \"municipioId\": \"28006\",\r\n" +
			"      \"municipioTitle\": \"Alcobendas\",\r\n" +
			"      \"barrioId\": \"28006011\",\r\n" +
			"      \"barrioTitle\": \"Norte\",\r\n" +
			"      \"distritoId\": \"2800601\",\r\n" +
			"      \"distritoTitle\": \"Unico\",\r\n" +
			"      \"equipamientoTitle\": \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" +
			"      \"equipamientoId\": \"EQ0044\",\r\n" +			
			"      \"superEventId\": \"AGM0001\",\r\n" +
			"      \"reunionLobby\": false, \r\n" +
			"      \"tipoEvento\": \"evento-con-medio-de-comunicacion\",\r\n" +
			"      \"tipoAcceso\": \"privado\",\r\n" +
			"      \"tipoSesion\": \"extraordinaria\",\r\n" +
			"      \"canal\": \"presencial\",\r\n" +
			"      \"description\": \"Acto de entrega de los Premios Los Leones de EL ESPAÑOL solo para profesionales\" \r\n" +
			"    }";
	
	
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		AgendaMEvento mappedObj = mapper.readValue(testJSON, AgendaMEvento.class);
		AgendaMEvento agenda = new AgendaMEvento(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(agenda, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		AgendaMEvento mappedObj = mapper.readValue(testJSON, AgendaMEvento.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		AgendaMEvento agenda = new AgendaMEvento(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(agenda, fieldsToIngore);
		assertTrue(allFields);
	}


}
