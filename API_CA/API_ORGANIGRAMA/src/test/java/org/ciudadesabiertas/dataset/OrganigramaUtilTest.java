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

import org.ciudadesabiertas.dataset.model.Organigrama;
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
public class OrganigramaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"1\",\r\n" + 
			"    \"title\" : \"AYUNTAMIENTO DE ZARAGOZA\",\r\n" + 
			"    \"unidadRaiz\" : \"1\",\r\n" + 
			"    \"nivelJerarquico\" : 1,\r\n" + 
			"    \"foundingDate\" : \"1950-01-01T00:00:00\",\r\n" + 
			"    \"estadoEntidadId\" : \"V\",\r\n" + 
			"    \"estadoEntidadTitle\" : \"Vigente\",\r\n" + 
			"    \"nivelAdministracionId\" : \"3\",\r\n" + 
			"    \"nivelAdministracionTitle\" : \"Administración local\",\r\n" + 
			"    \"tipoEntidadId\" : \"AY\",\r\n" + 
			"    \"tipoEntidadTitle\" : \"Ayuntamiento\",\r\n" +
			"    \"postalCode\" : \"50297\",\r\n" +
			"    \"telephone\" : \"976721100\",\r\n" +
			"    \"faxNumber\" : \"976721100\",\r\n" +
			"    \"email\" : \"gm-cha@zaragoza.es\",\r\n" +
			"    \"purpose\" : \"La organización, coordinación y dirección\",\r\n" +
			"    \"municipioId\" : \"50297\",\r\n" + 
			"    \"municipioTitle\" : \"Zaragoza\",\r\n" +
			"    \"unitOf\" : \"Zaragoza\",\r\n" +
			"    \"headOfName\" : \"Zaragoza\",\r\n" +
			"    \"dissolutionDate\" : \"1950-01-01T00:00:00\",\r\n" +  
			"    \"streetAddress\" : \"Calle real 1\",\r\n" +
			"    \"url\" : \"http://www.zaragoza.es/sede/portal/organizacion/corporacion/luisa-broto\",\r\n" + 
			"    \"image\" : \"https://transparencia.castillalamancha.es/sites/transparencia2.castillalamancha.es/files/cargos/vicepte_2.jpg\",\r\n" +
			"	 \"xETRS89\" : 676840.38000"+",\r\n" +
		    " 	 \"yETRS89\" : 4613965.90000"+",\r\n" +
		    "    \"portalId\" : \"PORTAL00008\"\r\n" +
			"     }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Organigrama mappedObj = mapper.readValue(testJSON, Organigrama.class);
		Organigrama CallejeroTramo = new Organigrama(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Organigrama mappedObj = mapper.readValue(testJSON, Organigrama.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Organigrama CallejeroTramo = new Organigrama(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
