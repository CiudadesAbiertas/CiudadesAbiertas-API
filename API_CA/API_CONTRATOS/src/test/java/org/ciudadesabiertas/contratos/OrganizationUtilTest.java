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
import org.ciudadesabiertas.dataset.model.Organization;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class OrganizationUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","portalIdIsolated"};
	private static final String testJSON = " {\n" + 
			"      \"id\": \"300-2018-00524\",\n" +
			"      \"title\": \"Redondo y García S.A.\",\n" + 
 			"      \"url\": \"https://datos.madrid.es/FwFront/portal_egob/new/img/portal_logo_h.png\",\n" +
 		    "      \"municipioId\": \"28006\",\n" +
 		    "  	   \"municipioTitle\": \"Alcobendas\",\n" +
 		    "      \"streetAddress\": \"CL BLAS DE OTERO 4\",\n" +
 		    "   	\"postalCode\": \"28100\",\n" +
 		    "   	\"portalId\": \"PORTAL000101\",\n" +
 		    "   	\"contactPointFaxNumber\": \"1123333333\",\n" +
 		    "   	\"contactPointEmail\": \"pepe@botello.com\",\n" +
 		    "   	\"contactPointTelephone\": \"El fax no funciona llamar a 1123333335\",\n" +
 		    "  	 	\"contactPointTitle\": \"Redondo y García S.A.\" \n" +  
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Organization mappedObj = mapper.readValue(testJSON, Organization.class);
		Organization CallejeroTramo = new Organization(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Organization mappedObj = mapper.readValue(testJSON, Organization.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Organization CallejeroTramo = new Organization(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
