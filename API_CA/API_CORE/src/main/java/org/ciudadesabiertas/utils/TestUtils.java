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

package org.ciudadesabiertas.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class TestUtils {
private static final Logger log = LoggerFactory.getLogger(TestUtils.class);

public static JSONParser parser = new JSONParser();

public static String extractHeadMD5(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {
  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.head(URL + ".json" + "?" + paramField + "=" + value)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

  String content = result.getResponse().getHeader("Content-MD5");

  return content;
}

public static String extractHeadMD5(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {
  String cadena = new String();
  if (paramField.length == value.length) {
	for (int i = 0; i < value.length; i++) {
	  if (i == 0) {
		cadena += "?" + paramField[i] + "=" + value[i];
	  } else {
		cadena += "&" + paramField[i] + "=" + value[i];
	  }
	}
  }

  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + ".json" + cadena)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

  String content = result.getResponse().getHeader("Content-MD5");

  return content;
}

public static JSONArray extractRecords(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + paramField + "=" + value, mockMvc);

  JSONArray records = (JSONArray) resultObj.get("records");

  if (records == null) {
	return new JSONArray();
  }

  return records;
}

public static long extractTotal(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + paramField + "=" + value, mockMvc);

  long total = (long) resultObj.get("totalRecords");
  return total;
}

public static long extractTotal(String URL, String paramField, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + paramField, mockMvc);

  long total = (long) resultObj.get("totalRecords");
  return total;
}

public static String extractContentMD5(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + paramField + "=" + value, mockMvc);

  String md5 = (String) resultObj.get("contentMD5");
  return md5;
}

public static String extractContentMD5(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, paramField, value, mockMvc);

  String md5 = (String) resultObj.get("contentMD5");
  return md5;
}

public static long extractTotalDistinct(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + paramField + "=" + value, mockMvc);

  long total = (long) resultObj.get("totalRecords");
  return total;
}

/* Funciones test datos Agrupadas */

public static JSONArray extractRecords(String URL, String params, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + params, mockMvc);

  JSONArray records = (JSONArray) resultObj.get("records");
  return records;
}

public static long extractTotalRecords(String URL, String params, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, "?" + params, mockMvc);

  long total = (long) resultObj.get("totalRecords");
  return total;
}

public static JSONArray extractRecords(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, paramField, value, mockMvc);
  if (resultObj != null) {
	JSONArray records = (JSONArray) resultObj.get("records");
	return records;
  } else {
	return null;
  }
}

public static long extractTotal(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {

  JSONObject resultObj = invokeService(URL, paramField, value, mockMvc);
  if (resultObj != null) {
	long total = (long) resultObj.get("totalRecords");
	return total;
  } else {
	return 0;
  }
}

public static boolean checkFormatURIs(String URL, MockMvc mockMvc) {
  return checkFormatURIs(URL, false, mockMvc);
}

public static boolean checkFormatURIs(String URL, boolean geoFormats, MockMvc mockMvc) {
  List<String> formatos = TestUtils.formatos(geoFormats);
  ArrayList<Integer> responseStatus = new ArrayList<Integer>();

  for (String format : formatos) {
	log.info("checking " + format + " format");
	String formatURL = URL + "." + format;
	int status = 0;
	try {
	  status = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andReturn().getResponse().getStatus();
	} catch (Exception e) {
	  log.error("Error in URL: " + formatURL);
	}
	responseStatus.add(status);

	if (format.equals("geojson") || format.equals("json") || format.equals("jsonld")) {
	  JSONObject jsonObj = null;
	  String jsonResponse = "";
	  try {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		jsonResponse = result.getResponse().getContentAsString();
		jsonObj = (JSONObject) parser.parse(jsonResponse);

		if (format.equals("geojson")) {
		  JSONArray records = (JSONArray) jsonObj.get("features");
		  JSONObject obj = (JSONObject) records.get(0);
		  JSONObject geometry = (JSONObject) obj.get("geometry");
		  JSONArray coordinates = (JSONArray) geometry.get("coordinates");
		  boolean validCoordinates = false;
		  if ((coordinates != null)) {
			validCoordinates = true;
		  }
		  if (!validCoordinates) {
			assertTrue(validCoordinates);
		  }
		}
	  } catch (Exception e) {
		log.error("Format: " + format);
		log.error("Error in with content in URI: " + formatURL);
		log.error(e.toString());
		log.error(jsonResponse);
	  }
	  if (jsonObj == null) {
		// Si no es un objeto valido, lanzo este assert para provocar error
		assertNotNull(jsonObj);
	  }
	} else if ((format.equals("georss")) || (format.equals("xml")) || (format.equals("rdf")) || (format.equals("odata"))) {
	  String xmlResponse = "";
	  boolean valid = false;
	  try {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		xmlResponse = result.getResponse().getContentAsString();
		valid = checkXML(xmlResponse);
	  } catch (Exception e) {
		log.error(e.toString());
		valid = false;
	  }
	  if (valid == false) {
		log.error("Format: " + format);
		log.error("Error in with content in URI: " + formatURL);
		log.error(xmlResponse);
		assertTrue(valid);
	  }
	} else if (format.equals("csv")) {
	  String response = "";
	  boolean valid = false;
	  try {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		response = result.getResponse().getContentAsString();
		valid=checkResponse(response);
		if (valid) {
		  if (response.contains(Constants.DATE_FORMAT)) {
			valid = false;
			log.error("DATE not visible in CSV");
		  }
		  if (RegularExpressions.wrongFormatDate(response)) {
			valid = false;
			log.error("Wrong DATE format in CSV");
		  }
		  String[] split = response.split(System.getProperty("line.separator"));
		  if (split[0].contains(",,"))
		  {
			valid = false;
			log.error("Wrong headers in CSV");
		  }
		}
	  } catch (Exception e) {
		log.error(e.toString());
		valid = false;
	  }
	  assertTrue(valid);

	} else {
	  String response = "";
	  boolean valid = false;
	  try {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		response = result.getResponse().getContentAsString();
		valid = checkResponse(response);
	  } catch (Exception e) {
		log.error(e.toString());
		valid = false;
	  }
	  if (valid == false) {
		log.error("Format: " + format);
		log.error("Error in with content in URI: " + formatURL);
		log.error(response);
		assertTrue(valid);
	  }
	}
  }

  boolean checkAll = true;
  for (Integer i : responseStatus) {
	if (i.intValue() != 200) {
	  checkAll = false;
	  break;
	}
  }

  return checkAll;
}

public static boolean checkAllAttributes(Object objToCheck, String[] fieldstoingore) throws IllegalAccessException {
  ArrayList<String> ignoreList = new ArrayList<String>(Arrays.asList(fieldstoingore));
  boolean allFields = true;
  for (Field field : objToCheck.getClass().getDeclaredFields()) {
	field.setAccessible(true);
	String name = field.getName();
	Object value = field.get(objToCheck);

	if ((ignoreList.contains(name) == false) && (value == null)) {
	  log.error("field without value " + name);
	  allFields = false;
	  break;
	}
  }
  return allFields;
}

public static <T> List<String> extractFields(T mappedObj) {
  List<String> listaCampos = new ArrayList<String>();
  for (Field field : mappedObj.getClass().getDeclaredFields()) {
	field.setAccessible(true);
	String name = field.getName();
	if ((name.equals("ikey") == false)) {
	  listaCampos.add(name);
	}
	if (name.equals("x")) {
	  listaCampos.add(Constants.XETRS89);
	}
	if (name.equals("y")) {
	  listaCampos.add(Constants.YETRS89);
	}
	if (name.equals("finX")) {
	  listaCampos.add(Constants.XETRS89Fin);
	}
	if (name.equals("finY")) {
	  listaCampos.add(Constants.YETRS89Fin);
	}
  }
  return listaCampos;
}

/**
 * *********************************************************** METODOS PRIVADOS
 * *********************************************************** || || \/ \/
 */

private static JSONObject invokeService(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception {
  if (paramField.length == value.length) {
	String cadena = new String();
	for (int i = 0; i < value.length; i++) {
	  if (i == 0) {
		cadena += "?" + paramField[i] + "=" + value[i];
	  } else {
		cadena += "&" + paramField[i] + "=" + value[i];
	  }
	}

	return invokeService(URL, cadena, mockMvc);
  } else {
	return null;
  }
}

private static JSONObject invokeService(String URL, String cadena, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException {
  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + ".json" + cadena)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

  String content = result.getResponse().getContentAsString();

  return (JSONObject) parser.parse(content);
}

public static String checkRDFURI(MockMvc mockMvc, String listURI) throws Exception, UnsupportedEncodingException {
  MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(listURI + ".ttl")).andReturn().getResponse();
  // Convertimos a byte[] para que la ñ aparezca correctamente
  byte[] contentAsByteArray = response.getContentAsByteArray();
  String ttlResponse = new String(contentAsByteArray);
  String[] lines = ttlResponse.split("\\r?\\n");
  List<String> urisToCheck = new ArrayList<String>();

  for (String line : lines) {
	if (line.contains(listURI) && (line.startsWith("<htt"))) {
	  if ((line.contains("/geometry") == false)) {
		String lineM = line.trim().replace("<", "").replace(">", "");
		if (lineM.trim().endsWith(".")) {
		  lineM = lineM.trim();
		  lineM = StringUtils.chop(lineM).trim();
		}

		int position = lineM.indexOf(listURI) + 1 + listURI.length();
		String part1 = lineM.substring(0, position);
		String part2 = lineM.substring(position, lineM.length());
		log.debug(part2);
		part2 = Util.encodeURL(part2);
		urisToCheck.add(part1 + part2);

		break;

		// Encodeo la ñ
		// lineM=lineM.replace("Ã±", "%C3%B1");
		// urisToCheck.add(lineM);
	  }
	}
  }

  String theURI = "";
  if (urisToCheck.size() > 0) {
	theURI = urisToCheck.get(0);
	theURI = theURI.substring(theURI.indexOf(listURI));
	theURI = theURI + ".json";
  }
  return theURI;
}

/** Metodo para chequear las URLs que estan compuestas por varios ids */
public static String checkRDFURI(MockMvc mockMvc, String listURI, String patron) throws Exception, UnsupportedEncodingException {
  MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(listURI + ".ttl")).andReturn().getResponse();
  String ttlResponse = response.getContentAsString();
  String[] lines = ttlResponse.split("\\r?\\n");
  List<String> urisToCheck = new ArrayList<String>();
  for (String line : lines) {
	if (line.contains(patron)) {
	  urisToCheck.add(line.trim().replace("<", "").replace(">", ""));
	}
  }

  String theURI = "";
  if (urisToCheck.size() > 0) {
	theURI = urisToCheck.get(0);
	theURI = theURI.substring(theURI.indexOf(patron));
	theURI = theURI + ".json";
  }
  return theURI;
}

public static List<String> formatos(boolean geoFormats) {
  List<String> formatos = new ArrayList<String>();
  formatos.add("json");
  formatos.add("xml");
  formatos.add("csv");
  formatos.add("rdf");
  formatos.add("ttl");
  formatos.add("jsonld");
  formatos.add("n3");
  formatos.add("odata");

  if (geoFormats) {
	formatos.add("geojson");
	formatos.add("georss");
  }

  return formatos;
}

public static boolean checkXML(String content) {
  try {
	InputStream stream = new ByteArrayInputStream(content.getBytes("UTF-8"));
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	dBuilder.parse(stream);
	return true;
  } catch (Exception e) {
	log.error("Error validanting xml", e);
  }
  return false;
}

private static boolean checkResponse(String response) {

  if (response == null) {
	return false;
  }

  if (response.length() < 1) {
	return false;
  }

  if (response.toLowerCase().contains(" error ")) {
	return false;
  }

  return true;
}

}
