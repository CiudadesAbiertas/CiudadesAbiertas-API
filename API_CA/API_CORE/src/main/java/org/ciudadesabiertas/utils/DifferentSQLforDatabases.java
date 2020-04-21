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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class DifferentSQLforDatabases {
	
	public static final String TRANSLATE_ORACLE = "TRANSLATE(UPPER(";
	public static final String TRANSLATE_SQLSERVER = StartVariables.db_schema+".translateCA(UPPER(";	
	public static final String TRANSLATE_END = "),'ÂÁÀÄÃÊÉÈËÎÍÌÏÔÓÒÖÕÛÚÙÜÑ', 'AAAAAEEEEIIIIOOOOOUUUUN')";	


	private static final Logger log = LoggerFactory.getLogger(DifferentSQLforDatabases.class);
		

	public static RegularExpressions regularExpressions=new RegularExpressions();
	
	public static String transForm (String where, String driver) {
		
		
		
		String result = "";
		if (Util.getDatabaseTypeFromDriver(driver).equals(Constants.ORACLE)) {

			
			ArrayList<Pair<String, String>> changesFullDate = regularExpressions.extractChangesFullDate(where);
			
			String whereWithoutPreSubstitution=where;
			
			//Esto es para no repetir extracciones, lo que ya se ha sustituido previametne se cambia por --SubString--
			for (Pair<String,String> pair:changesFullDate)
			{
				whereWithoutPreSubstitution=whereWithoutPreSubstitution.replaceAll(pair.getKey(), "--SubString--");
			}
					
			
			ArrayList<Pair<String, String>> changesDate = regularExpressions.extractChangesDate(whereWithoutPreSubstitution);
						
			
			if (changesFullDate.size()>0 || (changesDate.size()>0))
			{
				log.info("before where: "+where);
			
				for (Pair<String,String> pair:changesFullDate)
				{
					where=where.replaceAll(pair.getKey(), pair.getValue());
				}
				
				for (Pair<String,String> pair:changesDate)
				{
					where=where.replaceAll(pair.getKey(), pair.getValue());
				}
			
				log.info("after where: "+where);
			}
			
			result = where;
			
		}else if (Util.getDatabaseTypeFromDriver(driver).equals(Constants.MYSQL)) {			
			result = where;
			
		}else if (Util.getDatabaseTypeFromDriver(driver).equals(Constants.SQLSERVER)) {			
			result = where;
		}else {			
			result = where;
		}
		
		return result;
		
	}
	
	
	public static String controlLikeConditions(String conditions, String driver)
	{
		if ((driver.contains(Constants.ORACLE))||(driver.contains(Constants.SQLSERVER)))
		{
			ArrayList<String> firstLoop = likesControl(conditions);
			String finalHaving = genWhereLike(firstLoop, driver);
			return finalHaving;	
		}
		else
		{
			return conditions;
		}
	}
	
	/*Funcion que añade la funcion para eliminar acentos y no distinguir entra mayusculas y minusculas en un array con likes*/
	private static String genWhereLike(ArrayList<String> firstLoop, String driver)
	{
		for (int i=1;i<firstLoop.size()-1;i++)
		{							
			if (firstLoop.get(i).equals("like"))
			{
				if (driver.contains(Constants.ORACLE))
				{
					//Tocamos el elemento anterior
					firstLoop.set(i-1,TRANSLATE_ORACLE+ firstLoop.get(i-1) +TRANSLATE_END);
					//Tocamos el elemento posterior
					firstLoop.set(i+1,TRANSLATE_ORACLE+ firstLoop.get(i+1) +TRANSLATE_END);
				}
				else if (driver.contains(Constants.SQLSERVER))
				{
					//Tocamos el elemento anterior
					firstLoop.set(i-1,TRANSLATE_SQLSERVER+ firstLoop.get(i-1) +TRANSLATE_END);
					//Tocamos el elemento posterior
					firstLoop.set(i+1,TRANSLATE_SQLSERVER+ firstLoop.get(i+1) +TRANSLATE_END);
				}
			}
		}
		String finalWhere="";
		for (String st:firstLoop)
		{
			finalWhere+=st+" ";
		}
		finalWhere=StringUtils.chop(finalWhere);
		return finalWhere;
	}

	
	/*Funcion que recibe un conjunto de condiciones (where, having) y devuelve una array agrupando los likes
	 * Ejemplo:  name like 'solid snake' and age>23 -> [name, like, solid snake, and, age, >, 23]*/
	private static ArrayList<String> likesControl(String where)
	{
		String[] splitted=where.split(" ");
		ArrayList<String> firstLoop=new ArrayList<String>();
		String aux="";
		for (String split:splitted)
		{
			if (split.contains("'"))
			{
				if ((split.startsWith("'"))&&(split.endsWith("'")))
				{
					firstLoop.add(split);	
				}
				else if ((split.startsWith("'"))&&(split.endsWith("'")==false))
				{
					aux=split;
				}
				else if (aux.equals("")==false) 
				{
					if (split.endsWith("'"))
					{
						aux+=" "+split;
						firstLoop.add(aux);	
						aux="";
					}else {
						aux+=" "+split;
					}
				}
			}
			else
			{
				if (aux.equals(""))
				{
					firstLoop.add(split);
				}else {
					aux+=" "+split;
				}
			}
		}
		return firstLoop;
	}
		
	/* Metodo que sustituye los campos de la select que esten dentro de la funcion lower por
	 * la funcion translate (para borrar acentos y ñ) de cada bbdd y además pasa a mayusculas
	 * */
	public static String rsqlTransFormLower(String query, String driver)
	{
		String queryT=query;
		
		String[] result = StringUtils.substringsBetween(query, "lower(", ")");
		
		List<String> fieldsTranslated=new ArrayList<String>();
		boolean changed=false;
		for (String field:result)
		{
			String fieldT="";
			if (driver.contains(Constants.ORACLE))
			{
				fieldT=TRANSLATE_ORACLE+field+TRANSLATE_END;
				fieldsTranslated.add(fieldT);
				changed=true;
			}
			else if (driver.contains(Constants.SQLSERVER))
			{
				fieldT=TRANSLATE_SQLSERVER+field+TRANSLATE_END;
				fieldsTranslated.add(fieldT);
				changed=true;
			}
			
			
			
		}
		
		for (int i=0;i<fieldsTranslated.size();i++)
		{
			queryT=queryT.replace(result[i],fieldsTranslated.get(i));
		}
		
		if (changed)
		{
			queryT=queryT.replace("lower", "");
		}
		
		return queryT;
	}
	
	
	/* Metodo que sustituye los campos de la select que trata el in por
	 * la funcion translate (para borrar acentos y ñ) de cada bbdd y además pasa a mayusculas
	 * 
	 * inOrOut puede ser ' in ' o 'not in'
	 * 
	 * */
	public static String rsqlTransFormInOut(String query, String driver, String inOrOut) throws Exception
	{
		String queryT=query;
		
		if (!inOrOut.equals(" in ") && (!inOrOut.equals(" not in ")))
		{
			throw new Exception ("wrong parameter value, must be ' in ' or ' not in '");
		}
				
		String[] split = query.split("generatedAlias0.");
		
		ArrayList<Pair<String, String>> changes=new ArrayList<>();
		
		for (String s:split)
		{
			if (s.contains(inOrOut))
			{
				String tratamiento="generatedAlias0."+s;
				
				String[] result = StringUtils.substringsBetween(tratamiento, "generatedAlias0.", inOrOut);
				
				List<String> fieldsTranslated=new ArrayList<String>();
				
				for (String field:result)
				{	
					String fieldT="";
					if (driver.contains(Constants.ORACLE))
					{
						fieldT=TRANSLATE_ORACLE+"generatedAlias0."+field+TRANSLATE_END;
						fieldsTranslated.add(fieldT);
					}
					else if (driver.contains(Constants.SQLSERVER))
					{
						fieldT=TRANSLATE_SQLSERVER+"generatedAlias0."+field+TRANSLATE_END;
						fieldsTranslated.add(fieldT);
					}
				}
				
				for (int i=0;i<fieldsTranslated.size();i++)
				{	
					Pair<String, String> pair = new MutablePair<>("generatedAlias0."+result[i], fieldsTranslated.get(i));
					if (changes.contains(pair)==false)
					{
						changes.add(pair);
					}
				}
			}
		}
		
			
		for (Pair<String,String> pair:changes)
		{
			queryT=queryT.replaceAll(pair.getKey(), pair.getValue());
		}
		
		return queryT;
	}
	
	
	
	
	public static String rsqlRemoveAccentsForParams(String param)
	{
		String paramT=param;
		
		paramT=paramT.replace("À", "A");
		paramT=paramT.replace("Á", "A");
		paramT=paramT.replace("É", "E");
		paramT=paramT.replace("È", "E");
		paramT=paramT.replace("Í", "I");
		paramT=paramT.replace("Ì", "I");
		paramT=paramT.replace("Ó", "O");
		paramT=paramT.replace("Ú", "U");
		paramT=paramT.replace("Ù", "U");
		paramT=paramT.replace("Ü", "U");
		paramT=paramT.replace("Ñ", "N");

		return paramT;
		
			
	}
}
