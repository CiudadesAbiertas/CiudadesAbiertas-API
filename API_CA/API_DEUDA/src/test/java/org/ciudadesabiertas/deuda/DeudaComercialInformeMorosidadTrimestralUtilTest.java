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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.DeudaComercialInformeMorosidadTrimestral;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeudaComercialInformeMorosidadTrimestralUtilTest
{

	private static final String[] fieldsToIngore = { "ikey" };
	
	private static final String testJSON = "{\n"
			+ "	  	 \"id\": \"2019-cuarto-12-28-079-AP-001\",\n"
			+ "      \"periodoMedioPagoTrimestral\": 55.5,\n"
			+ "      \"numPagosDentroPeriodo\": 6472,\n"
			+ "      \"importePagosDentroPeriodo\": 51831754.13,\n"
			+ "      \"numPagosFueraPeriodo\": 1124,\n"
			+ "      \"importePagosFueraPeriodo\": 5579630.33,\n"
			+ "      \"numPagosInteresesDemora\": 0,\n"
			+ "      \"importePagosInteresesDemora\": 0,\n"
			+ "      \"numFacturasPendientesDentroPeriodo\": 3020,\n"
			+ "      \"importeFacturasPendientesDentroPeriodo\": 34441734.41,\n"
			+ "      \"numFacturasPendientesFueraPeriodo\": 25,\n"
			+ "      \"importeFacturasPendientesFueraPeriodo\": 489769.85,\n"
			+ "      \"periodoMedioPagoPendiente\": 33.87,\n"
			+ "      \"tipoContabilidad\": \"empresarial\",\n"
			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
			+ "      \"periodo\": \"2019-cuarto-trimestre\"\n "
			+ "    }";


	
	
	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{
		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		DeudaComercialInformeMorosidadTrimestral mappedObj = mapper.readValue(testJSON, DeudaComercialInformeMorosidadTrimestral.class);
		DeudaComercialInformeMorosidadTrimestral item = new DeudaComercialInformeMorosidadTrimestral(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		DeudaComercialInformeMorosidadTrimestral mappedObj = mapper.readValue(testJSON, DeudaComercialInformeMorosidadTrimestral.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		DeudaComercialInformeMorosidadTrimestral item = new DeudaComercialInformeMorosidadTrimestral(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
