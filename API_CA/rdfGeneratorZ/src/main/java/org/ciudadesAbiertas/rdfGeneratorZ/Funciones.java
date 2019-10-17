package org.ciudadesAbiertas.rdfGeneratorZ;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Funciones {
	private static final Logger logger = LoggerFactory.getLogger(Funciones.class);
	
	public static  SimpleDateFormat formateadorFechaHora= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static  SimpleDateFormat formateadorFecha= new SimpleDateFormat("yyyy-MM-dd");
	
	public static  SimpleDateFormat formateadorHora= new SimpleDateFormat("HH:mm:ss");
	
	public static String getGetterMethodName(String name) {
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static Object retrieveObjectValue(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Object ivalue = null;
				if (Map.class.isAssignableFrom(obj.getClass())) {
					Map map = (Map) obj;
					ivalue = map.get(props[0]);
				} else {
					Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
					ivalue = method.invoke(obj);
				}
				
				if (ivalue == null) {
					return null;
				}
				return retrieveObjectValue(ivalue, property.substring(props[0].length() + 1));
			} catch (Exception e) {
				// throw new
				// InvalidImplementationException("Failed to retrieve value for "+property,
				// e);
				return null;
			}
		} else {
			// let's get the object value directly
			try {
				if (Map.class.isAssignableFrom(obj.getClass())) {
					Map map = (Map) obj;
					return HbUtils.deproxy(map.get(property));
				} else {
					Method method = obj.getClass().getMethod(getGetterMethodName(property));
					return HbUtils.deproxy(method.invoke(obj));
				}
			} catch (Exception e) {
				// throw new
				// InvalidImplementationException("Failed to retrieve value for "+property,
				// e);
				return null;
			}
		}
	}


	public static String generarHash(String cadena) {
		return "" + cadena.hashCode();
	}
	
	public static String urlify(String chain, int level) {
		
		if (chain==null)
			return null;
		
		if (level<=0)
		{
			chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
			chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			return chain;
		}
		if (level==1)
		{	
			chain = chain.replace(" de ", " ");
			chain = chain.replace(" ", "-");			
			return chain;
		}
		else 
		{
			chain = chain.trim().toLowerCase();
			
			chain = chain.replace(" de ", " ");
			
			while (chain.indexOf("  ") >= 0)
				chain = chain.replace("  ", " ");
			chain = chain.replace(" ", "-");
			
			chain = chain.replaceAll("\\s+", "_");
			
	
			chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
			chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			
			
			try {
				chain = URLEncoder.encode(chain, "UTF-8");
			} catch (UnsupportedEncodingException e) {			
				logger.error("Unsuported encoding",e);
			}
			
	
			while (chain.indexOf("--") >= 0)
				chain = chain.replace("--", "-");
	
			return chain;
		}

	}
	
	
	public static void replaceAll(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); 
	        index = builder.indexOf(from, index);
	    }
	}
	
	
	public static void main(String[] args)
	{
		String[] urifyTest= {"España"};
		for (String t:urifyTest)
		{
			System.out.println(t+" -> "+urlify(t,0));
			
		}
	}
}

