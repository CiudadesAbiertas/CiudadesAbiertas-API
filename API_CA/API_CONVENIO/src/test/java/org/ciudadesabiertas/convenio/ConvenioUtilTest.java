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

package org.ciudadesabiertas.convenio;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;


import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ciudadesabiertas.dataset.model.Convenio;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ConvenioUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","subvencionIdIsolated"};
	private static final String testJSON = " {\n" + 			
				   "   \"id\": \"CONV002\",\n" +
				   "   \"title\": \"CONVENIO PRUEBAS 1\",\n" +
				   "   \"description\": \"DESC CONVENIO PRUEBAS 1\",\n" +
				   "   \"objeto\": \"TEXTO DESCRIPTIVO DEL OBJETIVO DEL CONVENIO\",\n" +
				   "   \"fechaInicio\": \"2020-01-01T00:00:00\",\n" +
				   "   \"fechaFinalizacion\": \"2021-01-01T00:00:00\",\n" +
				   "   \"fechaSuscripcion\": \"2020-02-01T00:00:00\",\n" +
				   "   \"fechaResolucionFin\": \"2020-06-01T00:00:00\",\n" +
				   "   \"fechaIncorporacion\": \"2020-03-01T00:00:00\",\n" +
				   "   \"obligacionEconomicaAyto\": 1200000.8,\n" +
				   "   \"tipoConvenio\": \"tipo-convenio-1\",\n" +
				   "   \"tipoVariacion\": \"tipo-variacion-1\",\n" +
				   "   \"modalidad\": \"modalidad-convenio-1\",\n" +
				   "   \"materia\": \"tipo-materia-1\",\n" +
				   "   \"fechaAdjudicacionSub\": \"2020-02-11T00:00:00\",\n" +
				   "   \"importeSubv\": 320000.8,\n" +
				   "   \"adjudicatarioTitleSub\": \"Pedro Ruiz Gomez\",\n" +
				   "   \"subvencionId\": \"SUBV001\",\n" +
				   "   \"organizationId\": \"ORG0001\",\n" +
				   "   \"orgIdObligadoPrestacion\": \"ORG0002\",\n" +
				   "   \"gestionadoPorOrganization\": true,\n" +
				   "   \"gestionadoPorDistrito\": false,\n" +
				   "   \"municipioId\": \"28006\",\n" +
				   "   \"municipioTitle\": \"Alcobendas\",\n" +
				   "   \"distritoId\": \"2800601\",\n" +
				   "   \"distritoTitle\": \"Unico\",\n" +
				   "   \"esVariacionId\": \"CONV001\" \n" +
			   "    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Convenio mappedObj = mapper.readValue(testJSON, Convenio.class);
		Convenio organization = new Convenio(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(organization, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Convenio mappedObj = mapper.readValue(testJSON, Convenio.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Convenio CallejeroTramo = new Convenio(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
