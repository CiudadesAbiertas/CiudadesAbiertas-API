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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class RegularExpressions
{
	private static final String DEFAULT_DATE = "'2020-01-01T";
	//fecha en formato 'AAAA-MM-DD'
	private static final String SIMPLE_DATE_REGEX = "'\\d{4}-\\d{2}-\\d{2}'";
	//fecha en formato 'AAAA-MM-DD'T'HH:mm:ss' o 'AAAA-MM-DD HH:mm:ss'	
	private static final String COMPLEX_DATE_REGEX = "'\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}'|'\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}'";
	//Cualquier número dentro de una cadena que  acabe en 'BD'
	private static final String NUMBER_INSIDE_STRING_THATS_ENDS_WITH_BD = "\\p{Digit}+BD";
	
	private static final String TIME_REGEX_RSQL = "'\\d{2}:\\d{2}:\\d{2}'";
	
	private static final String TIME_REGEX_API = "\\d{2}:\\d{2}:\\d{2}";
	
	private static Pattern patternFullDate=Pattern.compile(COMPLEX_DATE_REGEX);
	
	private static Pattern patternDate=Pattern.compile(SIMPLE_DATE_REGEX);

	private static Pattern numberEndsBD=Pattern.compile(NUMBER_INSIDE_STRING_THATS_ENDS_WITH_BD);
	
	
	private static Pattern patternTimeRSQL=Pattern.compile(TIME_REGEX_RSQL);
	
	private static Pattern patternTimeAPI=Pattern.compile(TIME_REGEX_API);
		
	
	public static ArrayList<Pair<String, String>> extractChangesFullDate(String text)
	{
		
		ArrayList<Pair<String, String>> changes=new ArrayList<>();
		
		Matcher matcherA = patternFullDate.matcher(text);			
		while (matcherA.find())
		{
			String original=matcherA.group();				
			String originalTratado=original.toUpperCase().replace("T", " ");
			String cambio="to_date("+originalTratado+",'YYYY-MM-DD HH24:MI:SS')";						
			Pair<String, String> pair = new MutablePair<>(original, cambio);
			if (changes.contains(pair)==false)
				changes.add(pair);
		}
		return changes;
	}
	
	public static ArrayList<Pair<String, String>> extractChangesDate(String text)
	{
		
		ArrayList<Pair<String, String>> changes=new ArrayList<>();
		
		Matcher matcherA = patternDate.matcher(text);			
		while (matcherA.find())
		{
			String original=matcherA.group();				
			String originalTratado=original.toUpperCase().replace("T", " ");
			String cambio="to_date("+originalTratado+",'YYYY-MM-DD')";						
			Pair<String, String> pair = new MutablePair<>(original, cambio);
			if (changes.contains(pair)==false)
				changes.add(pair);
		}
		return changes;
	}

	
	
	public static ArrayList<Pair<String, String>> removeSuffixBDInNumbers(String text)
	{
		
		ArrayList<Pair<String, String>> changes=new ArrayList<>();
		
		Matcher matcher=numberEndsBD.matcher(text);	
		while (matcher.find())
		{
			String subcadenaEncontrada=matcher.group();		
			Pair<String, String> pair = new MutablePair<>(subcadenaEncontrada, subcadenaEncontrada.replace("BD", ""));
			if (changes.contains(pair)==false)
				changes.add(pair);
		}
		return changes;
	}
	
	
	
	public static ArrayList<Pair<String, String>> getTimesWithDatesForRSQL(String text)
	{
		
		ArrayList<Pair<String, String>> changes=new ArrayList<>();
		
		Matcher matcher=patternTimeRSQL.matcher(text);	
		while (matcher.find())
		{
			String original=matcher.group();			
			String originalSinComillas=original.substring(1);
			originalSinComillas=StringUtils.chop(originalSinComillas);
			//LocalTime time = LocalTime.parse(originalSinComillas);
			String originalTratado=DEFAULT_DATE+originalSinComillas+"'";								
			Pair<String, String> pair = new MutablePair<>(original, originalTratado);
			if (changes.contains(pair)==false)
				changes.add(pair);
		}
		return changes;
	}
	
	public static boolean isFormatTime(String text)
	{
		boolean result = false;
		if (text!=null && !"".equals(text) && text.length()==8) {
			Matcher matcher=patternTimeAPI.matcher(text);	
			if (matcher.find()) {
				result=true;
			}
		}
		return result;
	}
	
	
	
	public static void main(String[] args) {
		
	  
	  
	  
	  String text="casasasfaf'23:23:00'";
	  
	  ArrayList<Pair<String, String>> addDateToTimesRSQL = getTimesWithDatesForRSQL(text);
	  
	  for (Pair<String, String> pair : addDateToTimesRSQL) {
		System.out.println(pair);
	  }
	  
	  
	  System.out.println("end");
		
	}

	

}
