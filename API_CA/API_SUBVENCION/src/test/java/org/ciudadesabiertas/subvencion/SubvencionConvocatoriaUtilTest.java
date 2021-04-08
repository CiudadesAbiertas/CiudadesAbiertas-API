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

package org.ciudadesabiertas.subvencion;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.SubvencionConvocatoria;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubvencionConvocatoriaUtilTest
{


	private static final String[] fieldsToIngore = { "ikey", "instrumentaIdIsolated", "clasificacionProgramaSimple", "clasificacionEconomicaGastoSimple" };
	
	private static final String testJSON = "{\r\n"
			+ "      \"id\": \"SUB1\",\r\n"
			+ "      \"title\": \"CONVOCATORIA PREMIOS ARGANZUELA XXXII EDICION PINTURA Y X EDICIÓN FOTOGRAFÍA\",\r\n"
			+ "      \"basesReguladoras\": \"https://www.bocm.es/boletin/CM_Orden_BOCM/2013/11/22/BOCM-20131122-34.PDF\",\r\n"
			+ "      \"tipoInstrumento\": \"PRÉSTAMOS\",\r\n"
			+ "      \"nominativa\": false,\r\n"
			+ "      \"tipoProcedimiento\": \"subvencion-directa\",\r\n"
			+ "      \"objeto\": \"Finalidad de la subvención SUB1\",\r\n"
			+ "      \"importePresupuestado\": 6500,\r\n"
			+ "      \"importeTotalConcedido\": 6500,\r\n"
			+ "      \"fechaAcuerdo\": \"2017-09-26T00:00:00\",\r\n"
			+ "      \"anioFiscal\": \"2017\",\r\n"
			+ "      \"clasificacionPrograma\": [\"334\"],\r\n"
			+ "      \"clasificacionEconomicaGasto\": [\"48\"],\r\n"
			+ "      \"tieneTematica\": \"deporte\",\r\n"
			+ "      \"gestionadoPorOrganization\": false,\r\n"
			+ "      \"gestionadoPorDistrito\": true,\r\n"
			+ "      \"distritoId\": \"28079602\",\r\n"
			+ "      \"distritoTitle\": \"Arganzuela\",\r\n"
			+ "      \"areaId\": \"A05003355\",\r\n"
			+ "      \"servicioId\": \"A05003341\",\r\n"
			+ "      \"entidadFinanciadoraId\": \"A05003367\",\r\n"
			+ "      \"programaAsText\": \"926\",\r\n"
			+ "      \"gastoAsText\": \"13002\",\r\n"
			+ "      \"instrumentaId\": \"CONV003\",\r\n"
			+ "      \"instrumentaTitle\": \"CONVENIO DE SUBVENCIÓN ENTRE EL AYUNTA\",\r\n"
			+ "      \"organizationId\": \"A05003369\"\r\n"
			+ "    }";
	

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		SubvencionConvocatoria mappedObj = mapper.readValue(testJSON, SubvencionConvocatoria.class);
		SubvencionConvocatoria subvencion = new SubvencionConvocatoria(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(subvencion, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		SubvencionConvocatoria mappedObj = mapper.readValue(testJSON, SubvencionConvocatoria.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		SubvencionConvocatoria concesion = new SubvencionConvocatoria(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(concesion, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
