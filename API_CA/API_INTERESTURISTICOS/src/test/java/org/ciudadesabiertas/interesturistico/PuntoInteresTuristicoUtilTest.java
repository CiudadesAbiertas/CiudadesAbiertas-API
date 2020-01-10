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

package org.ciudadesabiertas.interesturistico;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.PuntoInteresTuristico;
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
public class PuntoInteresTuristicoUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance","portalIdIsolated" };
	
	private static final String testJSON = "{\r\n" + 
			"    \"id\" : \"PIT0001\",\r\n" + 
			"    \"title\" : \"Histohuellas\",\r\n" + 
			"    \"category\" : \"lugar interes turistico\",\r\n" + 
			"    \"accesible\" : true,\r\n" + 
			"    \"tipoAccesibilidad\" : \"Variada\",\r\n" + 
			"    \"municipioId\" : \"28079\",\r\n" + 
			"    \"municipioTitle\" : \"Madrid\",\r\n" + 
			"    \"streetAddress\" : \"Mayor\",\r\n" + 
			"    \"postalCode\" : \"28012\",\r\n" + 
			"    \"barrioId\" : \"CUATRO CAMINOS\",\r\n" + 
			"    \"distritoId\" : \"TETUAN\",\r\n" + 
			"    \"latitud\" : 40.41677540,\r\n" + 
			"    \"longitud\" : -3.70379020,\r\n" + 
			"    \"email\" : \"info@histohuellas.es\",\r\n" + 
			"    \"telephone\" : \"(+34) 665 63 17 74\",\r\n" + 
			"    \"url\" : \"http://www.esmadrid.com/informacion-turistica/histohuellas\",\r\n" + 
			"    \"siglo\" : \"XII\",\r\n" + 
			"    \"estiloArtistico\" : \"Romanico\",\r\n" + 
			"    \"costeMantenimiento\" : 19800.98,\r\n" + 
			"    \"ingresosObtenidos\" : 126980.02,\r\n" + 
			"    \"afluenciaPublico\" : \"Jovenes y Adultos\",\r\n" + 
			"    \"fechaDeclaracionBien\" : \"2019-03-25T00:00:00\",\r\n" + 
			"    \"modoAcceso\" : \"Ascensores y Escaleras Mecánicas\",\r\n" + 
			"    \"medioTransporte\" : \"Autobuses, cernanias y Metro\",\r\n" + 
			"    \"notaHistorica\" : \"<p><strong>Compuesta por un grupo de licenciados en Historia e Historia del Arte, especializados en docencia y gestión del patrimonio cultural y con experiencia en el sector turístico, esta empresa ofrece a los ciudadanos y turistas numerosas actividades histórico&nbsp; - culturales con las que dar a conocer el importante patrimonio artístico y cultural de Madrid.</strong></p><p>Entre sus propuestas se encuentran excursiones para colegios, compuestas de visitas, gymkanas y entradas a museos; visitas privadas exteriores o en museos y gymkanas culturales; y actividades culturales para empresas. Además, todos los fines de semana organizan visitas guiadas en grupos abiertos, para las que es preciso reservar entrada en su página web.</p>\",\r\n" + 
			"    \"openingHours\" : \"<p>Lun &ndash; vier: 8:00 &ndash; 20:00 h</p><p>Sábado: 10:00 &ndash; 20:00 h</p><p>Domingo: 10:00 &ndash; 15:00 h</p>\",\r\n" + 
			"    \"image\" : \"http://www.esmadrid.com/sites/default/files/recursosturisticos/infoturistica/histohuellas_5.jpg\",\r\n" + 
			"    \"description\" : \"<p><strong>Compuesta por un grupo de licenciados en Historia e Historia del Arte, especializados en docencia y gestión del patrimonio cultural y con experiencia en el sector turístico, esta empresa ofrece a los ciudadanos y turistas numerosas actividades histórico&nbsp; - culturales con las que dar a conocer el importante patrimonio artístico y cultural de Madrid.</strong></p><p>Entre sus propuestas se encuentran excursiones para colegios, compuestas de visitas, gymkanas y entradas a museos; visitas privadas exteriores o en museos y gymkanas culturales; y actividades culturales para empresas. Además, todos los fines de semana organizan visitas guiadas en grupos abiertos, para las que es preciso reservar entrada en su página web.</p>\",\r\n" + 
			"    \"xETRS89\" : 440291.26773,\r\n" + 
			"    \"yETRS89\" : 4474254.64478,\r\n" + 
			"    \"portalId\" : \"PORTAL00008\"\r\n" +
			"  }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		PuntoInteresTuristico mappedObj = mapper.readValue(testJSON, PuntoInteresTuristico.class);
		PuntoInteresTuristico CallejeroTramo = new PuntoInteresTuristico(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		PuntoInteresTuristico mappedObj = mapper.readValue(testJSON, PuntoInteresTuristico.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		PuntoInteresTuristico CallejeroTramo = new PuntoInteresTuristico(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
