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

package org.ciudadesabiertas.agenda;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Agenda;
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
public class AgendaUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","equipamientoIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"AG0001\",\r\n" + 
			"    \"title\" : \"Programación cultural Revista El Público. Octubre 2018-Enero2019\",\r\n" + 
			"    \"municipioId\" : \"28006\",\r\n" + 
			"    \"municipioTitle\" : \"Alcobendas\",\r\n" + 
			"    \"description\" : \"Datos de interés Descripción Puede consultar el folleto con todos los espectáculos programados para el Primer período de la temporada. Puede informarse sobre abonos, horarios, precios, adquisición de localidades, etc. Documentos Programación espectáculos El Público (Octubre2018-Enero2019)\",\r\n" + 
			"    \"fechaInicio\" : \"2018-10-06T00:00:00\",\r\n" + 
			"    \"fechaFin\" : \"2019-01-23T23:59:59\",\r\n" + 
			"    \"tipoEvento\" : \"Teatro, Música y Danza,Teatro y Danza,Música,El Público,Abono General,Abono Alternativo,Abono Familia\",\r\n" + 
			"    \"tipoPublico\" : \"Infancia y Adolescencia,Público general\",\r\n" + 
			"    \"ageRange\" : \"Menores de 18\",\r\n" + 
			"    \"maximoParticipantes\" : 30,\r\n" + 
			"    \"equipamientoTitle\" : \"Teatro Auditorio Ciudad de Alcobendas\",\r\n" + 
			"    \"equipamientoId\" : \"EQ0002\",\r\n" + 
			"    \"lugarInscripcionTitle\" : \"Ayuntamiento\",\r\n" + 
			"    \"medioTransporteTitle\" : \"Bus 5\",\r\n" + 
			"    \"servicioMunicipalTitle\" : \"Cultura\",\r\n" + 
			"    \"accesible\" : true,\r\n" + 
			"    \"image\" : \"http://demoapi.localidata.com/image/23.jpg\",\r\n" + 
			"    \"tipoAccesibilidad\" : \"Total\"\r\n" + 
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Agenda mappedObj = mapper.readValue(testJSON, Agenda.class);
		Agenda agenda = new Agenda(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(agenda, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Agenda mappedObj = mapper.readValue(testJSON, Agenda.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		Agenda agenda = new Agenda(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(agenda, fieldsToIngore);
		assertTrue(allFields);
	}

}
