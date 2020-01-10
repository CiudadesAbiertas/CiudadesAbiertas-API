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

package org.ciudadesabiertas.puntowifi;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.PuntoWifiController;
import org.ciudadesabiertas.dataset.utils.PuntoWifiConstants;
import org.ciudadesabiertas.utils.TestUtils;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PuntoWifiDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL= PuntoWifiController.LIST;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_XETRS89() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89==437829.00050";
		
		
		
		value+=" and " +loadTiposDeEquipamientos();

		long total = extractTotal(paramField, value);
		
		assertTrue(total == 3);

	}
	
	@Test
	public void test_Busqueda_RSQL_YETRS89() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89==4473131.11756";
		
		//Cargamos los tipos asociados a equipamiento primitivo
		value+=" and " +loadTiposDeEquipamientos();

		long total = extractTotal(paramField, value);
		
		assertTrue(total == 3);

	}
	
	@Test
	public void test_Busqueda_RSQL_XETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "xETRS89>437829 and xETRS89<437830";
		
		//Cargamos los tipos asociados a equipamiento tipo aparcamiento
		value+=" and " +loadTiposDeEquipamientos();

		long total = extractTotal(paramField, value);
		
		assertTrue(total == 3);

	}
	
	@Test
	public void test_Busqueda_RSQL_yETRS89_intervalo() throws Exception
	{

		String paramField = "q";

		String value = "yETRS89>4473131 and yETRS89<4473132";
		
		//Cargamos los tipos asociados a equipamiento tipo aparcamiento
		value+=" and " +loadTiposDeEquipamientos();

		long total = extractTotal(paramField, value);
		
		assertTrue(total == 3);

	}

	@Test
	public void test_Busqueda_title_tipoEquipamiento() throws Exception
	{

		String value = "title=='Biblioteca Pública Municipal Gerardo Diego (Villa de Vallecas)' and tipoEquipamiento=='"+PuntoWifiConstants.TIPO_EQUIPAMIENTO+"'";

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 1);
	}
	
	
		
	
	@Test
	public void test_Busqueda_description_comodin() throws Exception
	{
		
		String value = "description=='*La Biblioteca Pública Municipal*'";
		
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 12);
	}
	
	
	@Test
	public void test_Busqueda_postalCode_streetAddress() throws Exception
	{

		String value = "postalCode=='28041' and streetAddress=in=('PLAZA PUEBLO NUM 2','PLAZA ASOCIACION V 1')";
		
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 2);
	}
	
	
	
	@Test
	public void test_Busqueda_municipioId_municipioTitle_barrio() throws Exception
	{
		//Incluimos control para que no de como resultado el total de campos
		String value = "municipioId=='28079' and municipioTitle=='Madrid' and barrioId=='280796062'";

		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();
				
		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 203);
	}
	
	@Test
	public void test_Busqueda_barrio_or() throws Exception
	{

		String value = "barrioId=='280796062'";
		value+=" and postalCode=='28043'";
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();
		
		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 7);
	}
	
	
	
	@Test
	public void test_Busqueda_Url() throws Exception
	{
		String url = "http://www.madrid.es/sites/v/index.jsp?vgnextchannel=bfa48ab43d6bb410VgnVCM100000171f5a0aRCRD&vgnextoid=f7c6f4061d497210VgnVCM2000000c205a0aRCRD";
		String value = "url=='"+Util.encodeURL(url)+"'";
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_email_or_telephone() throws Exception
	{

		String value = "email=='cclosrosales@madrid.es' or telephone=='917 109 273'";
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();
		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 1);
	}
	
	@Test
	public void test_Busqueda_distrito_and_barrio() throws Exception
	{

		String value = "distritoId=='28079606' and barrioId=='280796062'";
		//Cargamos los tipos asociados a equipamiento tipo wifi
		value+=" and " +loadTiposDeEquipamientos();

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 203);
	}
	
	
	
	@Test
	public void test_Busqueda_openingHours() throws Exception
	{

		String value = "openingHours=='*Horario de verano: *'";
		//Cargamos los tipos asociados a equipamiento primitivo
		value+=" and " +loadTiposDeEquipamientos();

		String paramField = "q";

		long total = extractTotal(paramField, value);

		assertTrue(total == 2);
	}
		
	

	private long extractTotal(String paramField, String value) throws Exception, UnsupportedEncodingException, ParseException
	{
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(PuntoWifiController.LIST+".json" + "?" + paramField + "=" + value)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		JSONObject resultObj = (JSONObject) parser.parse(content);

		long total = (long) resultObj.get("totalRecords");
		return total;
	}
	
	@Test
	public void test_Busqueda_portalId() throws Exception
	{
		
		
		String paramField = "q";

		String value = "portalId=='PORTAL000098'";	
		value+=" and " +loadTiposDeEquipamientos();

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total > 10);
	}
	
	private String loadTiposDeEquipamientos() {
		
		//Cargamos los tipos asociados a equipamiento priitivo
		String value ="";
				
		String tipo_equipamiento = "tipoEquipamiento=in=(";
		int contador=1;
		for (String obj:PuntoWifiConstants.TIPO_EQUIPAMIENTO_TEST) {
			if (contador<PuntoWifiConstants.TIPO_EQUIPAMIENTO_TEST.length) {
				tipo_equipamiento+="'"+obj+"',";
			}else {
				tipo_equipamiento+="'"+obj+"'";
			}
			contador++;
		}
		tipo_equipamiento+=")";
		
		value+=tipo_equipamiento;
		return value;
	}

}
