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
import org.ciudadesabiertas.dataset.model.Tender;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class TenderUtilTest
{


	private static final String[] fieldsToIngore = { "ikey"};
	private static final String testJSON = " {\n" +
			"      \"ikey\": \"TN1\",\n" + 
			"      \"id\": \"TN1\",\n" + 
			"      \"title\": \"Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.\",\n" + 
			"      \"hasSupplier\": \"S\",\n" +
			"      \"mainProcurementCategory\": \"Goods\",\n" + 
			"      \"additionalProcurementCategory\": \"2233\",\n" + 
			"      \"numberOfTenderers\": 12,\n" + 
			"      \"procurementMethod\": \"Simplified open\",\n" + 
			"      \"procurementMethodDetails\": \"Ordinary\",\n" + 
			"      \"tenderStatus\": \"complete\",\n" + 
			"      \"periodDurationInDays\": 200,\n" + 
			"      \"periodEndDate\": \"2019-12-02T00:00:00\",\n" + 
			"      \"periodStartDate\": \"2019-06-03T00:00:00\",\n" + 
			"      \"valueAmount\": 280000.5\n" + 
			"    },";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		Tender mappedObj = mapper.readValue(testJSON, Tender.class);
		Tender CallejeroTramo = new Tender(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		Tender mappedObj = mapper.readValue(testJSON, Tender.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		boolean allFields = true;
	

		Tender CallejeroTramo = new Tender(mappedObj, listaCampos);
		allFields = TestUtils.checkAllAttributes(CallejeroTramo, fieldsToIngore);
		assertTrue(allFields);
	}

	
}
