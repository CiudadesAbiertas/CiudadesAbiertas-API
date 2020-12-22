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

package org.ciudadesabiertas.empleo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.ConvocatoriaEmpleoPublico;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvocatoriaPublicaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey","latitud","longitud","distance" };
	
	private static final String testJSON = "{\n" + 
		"      \"id\": \"convocatoria001\",\n" + 
		"      \"title\": \"Convocatoria Informática Noviembre 2020\",\n" + 
		"      \"description\": \"Convocatoria para cubrir 5 plazas para el despartamento de Informática\",\n" + 
		"      \"datePublished\": \"2020-11-02T14:00:00\",\n" + 
		"      \"fechaAprobacion\": \"2020-10-28T10:00:00\",\n" + 
		"      \"fechaResolucion\": \"2020-10-20T14:00:00\",\n" + 
		"      \"estadoPlazo\": true,\n" + 
		"      \"plazos\": \"200\",\n" + 
		"      \"numeroPlazasConvocadas\": 5,\n" + 
		"      \"listaEsperaEx\": false,\n" + 
		"      \"observaciones\": \"observaciones\",\n" + 
		"      \"disposiciones\": \"disposiciones\",\n" + 
		"      \"requisitos\": \"Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática\",\n" + 
		"      \"bases\": \"Las bases cuentan....\",\n" + 
		"      \"basesUrl\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020\",\n" + 
		"      \"formularioInscripcion\": \"http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario\",\n" + 
		"      \"pruebas\": \"Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse\",\n" + 
		"      \"grupoProfesional\": \"A1\",\n" + 
		"      \"empleadoPublico\": \"funcionario\",\n" + 
		"      \"cuerpo\": \"administracion-general\",\n" + 
		"      \"modalidad\": \"oposicion\"\n" + 
		"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		ConvocatoriaEmpleoPublico mappedObj = mapper.readValue(testJSON, ConvocatoriaEmpleoPublico.class);
		ConvocatoriaEmpleoPublico item = new ConvocatoriaEmpleoPublico(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		ConvocatoriaEmpleoPublico mappedObj = mapper.readValue(testJSON, ConvocatoriaEmpleoPublico.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		ConvocatoriaEmpleoPublico item = new ConvocatoriaEmpleoPublico(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
