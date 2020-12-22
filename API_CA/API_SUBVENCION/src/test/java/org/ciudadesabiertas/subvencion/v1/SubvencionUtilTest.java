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

package org.ciudadesabiertas.subvencion.v1;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.v1.SubvencionV1;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubvencionUtilTest
{


	private static final String[] fieldsToIngore = { "ikey","areaIdIsolated","entidadFinanciadoraIdIsolated" };
	
	private static final String testJSON = "{"  
		    +"\"id\": \"2Test01186\","
		    +"\"title\": \"subvención nominativa al ateneo 2016\","
		    +"\"areaId\": \"A05003340\","
		    +"\"areaTitle\": \"área de gobierno de cultura y deportes\","
		    +"\"municipioId\": 28079,"
		    +"\"municipioTitle\": \"madrid\","
		    +"\"adjudicatarioId\": \"g28679801 \","
		    +"\"adjudicatarioTitle\": \"ateneo cientifico literario y artistico de madrid. .\","
		    +"\"entidadFinanciadoraId\": \"A05003340\","
		    +"\"entidadFinanciadoraTitle\": \"área de gobierno de cultura y deportes\","
		    +"\"importe\": 750000,"
		    +"\"fechaAdjudicacion\": \"2016-09-22T00:00:00\","
		    +"\"lineaFinanciacion\": \"LINEA 1\","
		    +"\"basesReguladoras\": \"https://www.bocm.es/boletin/cm_orden_bocm/2015/12/30/bocm-20151230-29.pdf\","
		    +"\"tipoInstrumento\": \"subvención y entrega dineraria sin contraprestación\","
		    +"\"aplicacionPresupuestaria\": \"2016-G/33401/48901\","
		    +"\"nominativa\" : true," 
		    +"\"tipoProcedimiento\": \"subvencion-nominativa\""
			+"  }";
	
	

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		SubvencionV1 mappedObj = mapper.readValue(testJSON, SubvencionV1.class);
		SubvencionV1 CallejeroTramo = new SubvencionV1(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		SubvencionV1 mappedObj = mapper.readValue(testJSON, SubvencionV1.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		SubvencionV1 CallejeroTramo = new SubvencionV1(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
