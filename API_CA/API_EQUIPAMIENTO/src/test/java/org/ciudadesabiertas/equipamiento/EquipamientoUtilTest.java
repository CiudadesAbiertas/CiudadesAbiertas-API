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

package org.ciudadesabiertas.equipamiento;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Equipamiento;
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
public class EquipamientoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"EQ0001\",\r\n" + 
			"    \"title\" : \"Aula de Educación Ambiental\",\r\n" + 
			"    \"tipoEquipamiento\" : \"equipamiento municipal\",\r\n" + 
			"    \"municipioId\" : \"28006\",\r\n" + 
			"    \"municipioTitle\" : \"Alcobendas\",\r\n" + 
			"    \"provinciaId\" : \"Madrid\",\r\n" + 
			"    \"autonomiaId\" : \"Comunidad de Madrid\",\r\n" + 
			"    \"paisId\" : \"España\",\r\n" + 
			"    \"streetAddress\" : \"AV OLIMPICA s/n\",\r\n" + 
			"    \"postalCode\" : \"28108\",\r\n" + 
			"    \"barrioId\" : \"Urbanizaciones\",\r\n" + 
			"    \"distritoId\" : \"Unico\",\r\n" + 
			"    \"latitud\" : 40.53538651,\r\n" + 
			"    \"longitud\" : -3.63554906,\r\n" + 
			"    \"email\" : \"aulaambiental2@aytoalcobendas.org\",\r\n" + 
			"    \"telephone\" : \"34916618096\",\r\n" +
			"    \"url\" : \"http://www.ciudadesabiertas.es\",\r\n" + 
			"    \"titularidad\" : \"Publico\",\r\n" + 
			"    \"openingHours\" : \"Sin horario\",\r\n" + 
			"    \"description\" : \"Datos de interés Servicios principales Equipamiento ambiental situado en el Jardín de la Vega donde se desarrollan actividades y programas relacionados con la Educación Ambiental. Proyecto Coches compartidos Accesos Coche Autobús:  151 / 152C  / 153  /154C / 158 / 166      Metro. Estación La Moraleja Más información sobre transportes  Horario Del 1/10 al 31/03: lunes a viernes de 16:30 a 18:30 h. Del 1/04 al 30/06: lunes a viernes de 17:00 a 20:00 h. Del 1/07 al 30/09: lunes a viernes de 10:00 a 13:00 h.\",\r\n" + 
			"    \"xETRS89\" : 446175.61012,\r\n" + 
			"    \"yETRS89\" : 4487376.65943,\r\n" + 
			"    \"portalId\" : \"PORTAL00008\"\r\n" +
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Equipamiento mappedObj = mapper.readValue(testJSON, Equipamiento.class);
		Equipamiento CallejeroTramo = new Equipamiento(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Equipamiento mappedObj = mapper.readValue(testJSON, Equipamiento.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Equipamiento CallejeroTramo = new Equipamiento(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
