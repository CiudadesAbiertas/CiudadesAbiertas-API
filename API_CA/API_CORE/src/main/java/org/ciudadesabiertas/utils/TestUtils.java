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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
public class TestUtils
{
	private static final Logger log = LoggerFactory.getLogger(TestUtils.class);
	
	public static JSONParser parser = new JSONParser();	

	public static String extractHeadMD5(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.head(URL+".json" + "?"+paramField+"=" + value)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getHeader("Content-MD5");
				
		return content;
	}
	
	public static String extractHeadMD5(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		String cadena = new String();
		if (paramField.length==value.length) {			
			for (int i=0; i<value.length; i++) {
				if (i==0) {
					cadena+= "?"+paramField[i]+"=" + value[i];
				}else {
					cadena+= "&"+paramField[i]+"=" + value[i];
				}
			}
		}
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL+".json" + cadena)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getHeader("Content-MD5");
				
		return content;
	}
	
	
	public static JSONArray extractRecords(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{

		JSONObject resultObj = invokeService(URL, "?"+paramField+"=" + value, mockMvc);

		JSONArray records = (JSONArray) resultObj.get("records");
		return records;
	}
	
	
	
	
	public static long extractTotal(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{	

		JSONObject resultObj = invokeService(URL, "?"+paramField+"=" + value, mockMvc);

		long total = (long) resultObj.get("totalRecords");
		return total;
	}
	
	public static long extractTotal(String URL, String paramField, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{		

		JSONObject resultObj = invokeService(URL, "?"+paramField, mockMvc);

		long total = (long) resultObj.get("totalRecords");
		return total;
	}
	
	public static String extractContentMD5(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{		

		JSONObject resultObj = invokeService(URL, "?"+paramField+"=" + value, mockMvc);

		String md5 = (String) resultObj.get("contentMD5");
		return md5;
	}
	
	
	public static String extractContentMD5(String URL, String[] paramField, String[] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{		

		JSONObject resultObj = invokeService(URL, paramField, value, mockMvc);

		String md5 = (String) resultObj.get("contentMD5");
		return md5;
	}

	
	
	public static long extractTotalDistinct(String URL, String paramField, String value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{		
		
		JSONObject resultObj = invokeService(URL, "?"+paramField+"=" + value, mockMvc);

		long total = (long) resultObj.get("totalRecords");
		return total;
	}
	
	
	/* Funciones test datos Agrupadas*/
	
	public static JSONArray extractRecords(String URL, String params,  MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		
		JSONObject resultObj = invokeService(URL, "?"+params, mockMvc);

		JSONArray records = (JSONArray) resultObj.get("records");
		return records;
	}
	
	public static long extractTotalRecords(String URL, String params,  MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{		

		JSONObject resultObj = invokeService(URL, "?"+params, mockMvc);

		long total = (long) resultObj.get("totalRecords");
		return total;
	}
	
	
	public static JSONArray extractRecords(String URL, String [] paramField, String [] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		
			JSONObject resultObj = invokeService(URL,paramField,value,mockMvc);
			if (resultObj!=null) {
				JSONArray records = (JSONArray) resultObj.get("records");
				return records;
			}else {
				return null;
			}
	}
	
	public static long extractTotal(String URL, String [] paramField, String [] value, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		
		JSONObject resultObj = invokeService(URL,paramField,value,mockMvc);
		if (resultObj!=null) {				
			long total = (long) resultObj.get("totalRecords");
			return total;
		}else {
			return 0;
		}
	}
	
	
	
	public static boolean checkFormatURIs(String URL, MockMvc mockMvc) throws Exception 
	{   	
		String[] formatos= {"json","xml","csv","rdf","ttl","jsonld","n3"};
	    ArrayList<Integer> responseStatus=new ArrayList<Integer>();
	    	    	
    	for (String format:formatos)
    	{
    		String formatURL=URL+"."+format;
    	    int status = mockMvc.perform(MockMvcRequestBuilders.get(formatURL)).andReturn().getResponse().getStatus();    		
    		responseStatus.add(status);
    	}
    	
    	boolean checkAll=true;
    	for (Integer i:responseStatus)
    	{    		
    		if (i.intValue()!=200)
    		{
    			checkAll=false;
    			break;
    		}    			
    	}
	        
	    return checkAll;    	    	
	 }
	
	
	
	public static boolean checkAllAttributes( Object objToCheck, String[] fieldstoingore) throws IllegalAccessException
	{
		ArrayList<String> ignoreList = new ArrayList<String>(Arrays.asList(fieldstoingore));
		boolean allFields=true;
		for (Field field : objToCheck.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String name = field.getName();
		    Object value = field.get(objToCheck);
			if ((ignoreList.contains(name)==false)&&(value==null))
			{
				log.error("field without value "+name);
				allFields=false;
				break;
			}
		}
		return allFields;
	}
	
	public static  <T> List<String> extractFields(T mappedObj)
	{
		List<String> listaCampos = new ArrayList<String>();
		for (Field field : mappedObj.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			String name = field.getName();
			if ((name.equals("ikey") == false))
			{
				listaCampos.add(name);
			}
			if (name.equals("x"))
			{
				listaCampos.add(Constants.XETRS89);
			}
			if (name.equals("y"))
			{
				listaCampos.add(Constants.YETRS89);
			}
		}
		return listaCampos;
	}
	
	/**
	 * ***********************************************************
	 * 					METODOS PRIVADOS 
	 * ***********************************************************
	 *  ||												||
	 *  \/												\/
	 */
	
	private static JSONObject invokeService(String URL, String [] paramField, String [] value, MockMvc mockMvc) throws Exception {
			if (paramField.length==value.length) {
				String cadena = new String();
				for (int i=0; i<value.length; i++) {
					if (i==0) {
						cadena+= "?"+paramField[i]+"=" + value[i];
					}else {
						cadena+= "&"+paramField[i]+"=" + value[i];
					}
				}
						
				return invokeService(URL, cadena, mockMvc);
		}else {
			return null;
		}
	}
	
	private static JSONObject invokeService(String URL, String cadena, MockMvc mockMvc) throws Exception, UnsupportedEncodingException, ParseException
	{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL+".json" + cadena)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		return (JSONObject) parser.parse(content);
	}

	
	public static String checkRDFURI(MockMvc mockMvc,String listURI) throws Exception, UnsupportedEncodingException
	{
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(listURI+".ttl")).andReturn().getResponse();    		
    	String ttlResponse=response.getContentAsString();
    	String[] lines = ttlResponse.split("\\r?\\n");
    	List<String> urisToCheck=new ArrayList<String>();
        for (String line : lines) {             
             if (line.contains(listURI))
             {
            	 urisToCheck.add(line.trim().replace("<", "").replace(">", ""));
             }
        }
        
        String theURI="";
        if (urisToCheck.size()>0)
        {
        	theURI=urisToCheck.get(0);
        	theURI=theURI.substring(theURI.indexOf(listURI));
        	theURI=theURI+".json";
        }
		return theURI;
	}

	
	/**Metodo para chequear las URLs que estan compuestas por varios ids*/
	public static String checkRDFURI(MockMvc mockMvc, String listURI, String patron)  throws Exception, UnsupportedEncodingException
	{
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(listURI+".ttl")).andReturn().getResponse();    		
    	String ttlResponse=response.getContentAsString();
    	String[] lines = ttlResponse.split("\\r?\\n");
    	List<String> urisToCheck=new ArrayList<String>();
        for (String line : lines) {             
             if (line.contains(patron))
             {
            	 urisToCheck.add(line.trim().replace("<", "").replace(">", ""));
             }
        }
        
        String theURI="";
        if (urisToCheck.size()>0)
        {
        	theURI=urisToCheck.get(0);
        	theURI=theURI.substring(theURI.indexOf(patron));
        	theURI=theURI+".json";
        }
		return theURI;
	}
	
}
