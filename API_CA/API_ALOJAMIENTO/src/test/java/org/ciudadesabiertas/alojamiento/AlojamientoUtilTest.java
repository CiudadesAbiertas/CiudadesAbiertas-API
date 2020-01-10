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

package org.ciudadesabiertas.alojamiento;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.Alojamiento;
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
public class AlojamientoUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"ALJ0001\",\r\n" + 
			"    \"title\" : \"B&amp;B Hotel Madrid Centro Puerta del Sol\",\r\n" + 
			"    \"categoria\" : \"Hoteles 3 estrellas\",\r\n" + 
			"    \"accesible\" : true,\r\n" + 
			"    \"tipoAccesibilidad\" : \"Variada\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"streetAddress\" : \"de la Montera, 10 - 12\",\r\n" + 
			"    \"postalCode\" : \"28013\",\r\n" + 
			"    \"barrioId\" : \"CUATRO CAMINOS\",\r\n" + 
			"    \"distritoId\" : \"TETUAN\",\r\n" +
			"    \"email\" : \"hotel.puertadelsol@hotelbb.com\",\r\n" + 
			"    \"telephone\" : \"(+34) 914 890 591\",\r\n" +
			"    \"faxNumber\" : \"(+34) 914 890 591\",\r\n" + 
			"    \"url\" : \"http://www.esmadrid.com/alojamientos/bb-hotel-madrid-centro-puerta-sol\",\r\n" + 
			"    \"numHabitaciones\" : 100,\r\n" + 
			"    \"numCamas\" : 190,\r\n" + 
			"    \"modified\" : \"2018-11-27T00:00:00\",\r\n" + 
			"    \"image\" : \"http://www.esmadrid.com/sites/default/files/recursosturisticos/alojamientos/bb_hotel_madrid_centro_puerta_del_sol.jpg\",\r\n" + 
			"    \"description\" : \"<p><strong>Próximo a la céntrica Puerta del Sol se encuentra este hotel de tres estrellas que dispone de 74 habitaciones con un diseño arquitectónico innovador. Cuenta con un moderno lobby lounge con café e infusiones las 24 horas y wifi gratis. En su azotea se encuentra el restaurante <a href=\\\"https://www.esmadrid.com/restaurantes/dona-luz\\\">Doña Luz</a>, en el que se puede disfrutar de recetas internacionales, copas y cócteles.</strong></p><p>Todas las habitaciones están compuestas de una exclusiva cama flotante Airbed&reg;, TV LCD de 49&rsquo;&rsquo;, escritorio, caja fuerte electronica, minibar, baño de diseño novedoso y con ducha de efecto lluvia, así como con secador de pelo y WiFi de alta velocidad. Se puede elegir entre habitaciones individuales premium, dobles matrimoniales deluxe, doble premium o doble premium con terraza con vistas a la Puerta del Sol.</p>\",\r\n" + 
			"    \"xETRS89\" : 440396.51166,\r\n" + 
			"    \"yETRS89\" : 4474341.38044,\r\n" + 
			"    \"portalId\" : \"PORTAL00008\"\r\n" +
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Alojamiento mappedObj = mapper.readValue(testJSON, Alojamiento.class);
		Alojamiento Alojamiento = new Alojamiento(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(Alojamiento, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Alojamiento mappedObj = mapper.readValue(testJSON, Alojamiento.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Alojamiento Alojamiento = new Alojamiento(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(Alojamiento, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
