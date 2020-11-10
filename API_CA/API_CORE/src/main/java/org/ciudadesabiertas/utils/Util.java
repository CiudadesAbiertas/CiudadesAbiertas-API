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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.model.DataCubeModel;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.ITrafico;
import org.ciudadesabiertas.utils.converters.RDFConverter;
import org.jasypt.util.text.BasicTextEncryptor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class Util
{
	public static AliasToLinkedEntityMapTransformer  transformadorCamposSqlOrdenados= new AliasToLinkedEntityMapTransformer();

	private static final String ISOLATED = "isolated";

	private static final Logger log = LoggerFactory.getLogger(Util.class);
	
	public static  SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
	
	public static  SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public static  SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.TIME_FORMAT);
	
	public static  SimpleDateFormat dateTimeFormatterWithoutT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_B);	

	private static BasicTextEncryptor textEncryptor;
	
	public static Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	
	public static Pattern DATE_PATTERN_B = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
	
	
	//Parser utilizado solo para pretty print
	private static JsonParser prettyPrintParser = new JsonParser();
	//Gson utilizado solo para pretty print
	private static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

	private static JSONParser JSONParser=new JSONParser();
	
	
	public static String decimalFormatterCSV(Float value)
	{
	    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	    otherSymbols.setDecimalSeparator('.');		 
	    DecimalFormat df = new DecimalFormat("#.00", otherSymbols);
	    return df.format(value);
	}

	public static <T> ArrayList<String> extractPropertiesFromBean(Class<T> beanClass)
	{	
		ArrayList<String> availableFields = new ArrayList<String>();
		
		//TODO activar para para añadir el esquema a las entidades cuando es necesario
		//Table annotation = beanClass.getAnnotation(Table.class);		
		//changeAnnotationValue(annotation,"schema","esquemaAPI");
		
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				String propertyName = propertyDesc.getName();
				//Para que no solicite los campos *isolated
				if (propertyName.toLowerCase().endsWith(ISOLATED)==false)
				{
					availableFields.add(propertyName);					
				}
			}
			if (availableFields.contains(Constants.X.toLowerCase()))
			{
				//AÑADIMOS LOS CAMPOS NECESARIOS DE GEO
				availableFields.add(Constants.XETRS89);
				availableFields.add(Constants.YETRS89);
				availableFields.add(Constants.DISTANCE);
			}
			
			
		} catch (IntrospectionException e)
		{
			log.error("Error extracting fields from "+beanClass.getName(), e);
		}

		return availableFields;
	}
	
	public static <T> HashMap<String,String> extractPropertiesAndTypeBean(Class<T> beanClass)
	{
		HashMap<String,String> availableFields = new HashMap<String, String>();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				String propertyName = propertyDesc.getName();
				String typeName = propertyDesc.getPropertyType().getName();
				availableFields.put(propertyName,typeName);
			}
		} catch (IntrospectionException e)
		{
			log.error("Error extracting fields from "+beanClass.getName(), e);
		}

		return availableFields;
	}
	
	public static <T> ArrayList<String> extractPropertiesFromBeanTypeData(Class<T> beanClass, String typeData)
	{
		ArrayList<String> availableFields = new ArrayList<String>();

		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				if (propertyDesc.getPropertyType().getName().toLowerCase().contains(typeData)) {
					String propertyName = propertyDesc.getName();
					availableFields.add(propertyName);
				}
			}
		} catch (IntrospectionException e)
		{
			log.error("Error extracting fields from "+beanClass.getName(), e);
		}

		return availableFields;
	}
	
	public static  List<BeanUtil> obtenerBeanUtil(Object beanClass){
		List<BeanUtil> listado = new ArrayList<BeanUtil> ();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass.getClass());
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				log.debug(propertyDesc.getName());
				BeanUtil bean = new BeanUtil();
				bean.setFieldName( propertyDesc.getName() );
				bean.setTypeName( propertyDesc.getPropertyType().getName() );
				Object valor = null;
				try {
					valor = propertyDesc.getReadMethod().invoke(beanClass);
				} catch (IllegalAccessException e) {
					log.error("Error extracting fields from "+beanClass.getClass().getName(), e);
				} catch (IllegalArgumentException e) {
					log.error("Error extracting fields from "+beanClass.getClass().getName(), e);
				} catch (InvocationTargetException e) {
					log.error("Error extracting fields from "+beanClass.getClass().getName(), e);
				}
				bean.setValue(valor);
				listado.add(bean);
				
			}
		} catch (IntrospectionException e)
		{
			log.error("Error extracting fields from "+beanClass.getClass().getName(), e);
		}
		return listado;
	}
	
	public static  Object setIkey(Object beanClass,String ikey){
		
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass.getClass());
			boolean encontrado = false;
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				
				
				String field = propertyDesc.getName() ;
				if (field.contains(Constants.IKEY)) {
					
						try {
							propertyDesc.getWriteMethod().invoke(beanClass, ikey);
							encontrado = true;
						} catch (IllegalAccessException e) {
							log.error("Error setIKey fields from "+beanClass.getClass().getName(), e);
						} catch (InvocationTargetException e) {
							log.error("Error setIKey fields from "+beanClass.getClass().getName(), e);
						} catch (IllegalArgumentException e) {
							log.error("Error setIKey fields from "+beanClass.getClass().getName(), e);
						} 
					
					break;
				}
				
			}
			if (!encontrado) {
				log.error("ERROR [setIKey] NOT FIELD IN CLASS <"+beanClass+">");
			}
		} catch (IntrospectionException e)
		{
			log.error("Error setIkey  from "+beanClass.getClass().getName(), e);
		}
		return beanClass;
	}

	public static int genRandomNum(int min, int max)
	{
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

	public static String formatearFechas(Date fecha, String formato)
	{
		String result = "";
		String format = Constants.DATE_TIME_FORMAT;
		if (formato != null && !"".equals(formato))
		{
			format = formato;
		}
		SimpleDateFormat sd2 = new SimpleDateFormat(format);
		result = sd2.format(fecha);
		return result;
	}

	public static String formatearFechas(Date fecha)
	{
		return formatearFechas(fecha, "");
	}

	public static boolean validValue(String value)
	{
		if (value != null && !"".equals(value))
			return true;
		return false;
	}
	
	public static boolean validValue(Boolean value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	

	public static boolean validValue(BigDecimal value)
	{
		if (value != null)
			return true;
		return false;
	}

	public static boolean validValue(Date value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	public static boolean validValue(Integer value)
	{
		if (value != null )
			return true;
		return false;
	}
	
	public static boolean validValue(Object value)
	{
		if (value != null )
			return true;
		return false;
	}

	public static Date getFecha(String _str)
	{
			return getFecha (_str, null);
	}
	
	public static Date getFecha(String _str, String format)
	{
		Date fecha = null;
		if (format==null)
		{
			format=Constants.DATE_TIME_FORMAT;
		}
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			fecha = formatter.parse(_str);
		} catch (Exception ex)
		{
			log.error("Error getting date: [string:"+_str+"][format: "+format+"]"+ ex.getMessage());
		}
		return fecha;
	}

	public static BigDecimal getBigDecimal(String _str)
	{
		BigDecimal numero = null;
		try
		{

			numero = new BigDecimal(_str);
		} catch (Exception ex)
		{
			log.error("Error getting big decimal", ex);
		}
		return numero;
	}

	public static String getDataJson(JSONObject data, String label)
	{
		String resultado = "";
		if (data.get(label) != null)
		{
			resultado = data.get(label).toString();
		}
		return resultado;
	}
	
	
	
	 public static String getFullURL(HttpServletRequest request)
	 {
	        StringBuffer requestURL = request.getRequestURL();
	        if (request.getQueryString() != null)
	        {
	            requestURL.append("?").append(request.getQueryString());
	        }
	        String completeURL = requestURL.toString();	        
	        return completeURL;
	 }
	
	public static PageInfo getPageInfoFromURL(HttpServletRequest request, int pageSize, String uriBase, String context) {
	    
		PageInfo info=new PageInfo();
		if (pageSize==Constants.NO_PAGINATION)
		{
			pageSize=-1;
			info.setPage(-1);
			info.setPageSize(-1);
		}
		
		if (Util.validValue(pageSize))
		{
			info.setPageSize(pageSize);
		}
		String extraParams="";
		
		String selfRequest = generateSelfRequest(request);
		
		info.setRequestURL(selfRequest);
		
		 Map<String, String[]> parameterNames = request.getParameterMap(); 
		 
		 for (Entry<String, String[]> entry : parameterNames.entrySet())
		 {
			 String value=getValueFromStringArray(entry.getValue());		
			 if (entry.getKey().equals("pageSize"))
			 {
				 if (!value.equals(Constants.NO_PAGINATION+""))
				 {
					 int pageSizeRequest=Integer.parseInt(value);
					 if (pageSizeRequest>info.getPageSize()) {
						 log.error("[getPageInfoFromURL] pageSizeRequest["+pageSizeRequest+"] != pageSize["+info.getPageSize()+"] Se ha superado el maximo permitido");
					 }else {
						 info.setPageSize(pageSizeRequest);
					 }
					 
				 }
			 }
			 else if (entry.getKey().equals("page"))
			 {
				 info.setPage(Integer.parseInt(value));
			 }
			 else if (entry.getKey().equals("fields"))
			 {
				 info.setFields(value);
			 }
			 else if (entry.getKey().equals("q"))
			 {
				 info.setQ(value);
			 }
			 else if (entry.getKey().equals("sort"))
			 {
				 info.setSort(value);
			 }
			 else {
				 if (extraParams.equals("")==false)
				 {
					 extraParams+="&";
				 }		 
				 extraParams+=entry.getKey()+"="+value;
			 }
		 }
		 	        
	    info.setExtraParams(extraParams);
		 
	    return info;
	    
	    
	}

	public static String generateSelfRequest(HttpServletRequest request) {
		String selfRequest="";
		
		String serverData=request.getServerName()+":"+request.getServerPort();		
		String tomcatContex=request.getContextPath();
		
		//Si tomcatContex no es valido, es porque viene de un test
		if (Util.validValue(tomcatContex))
		{
			//Esta logica se hace por si hay un servidor frontal delante de nuestro contenedor
			//de servlets (normalmente tomcat, que es donde esta el war)
			String tomcatURI=request.getRequestURL().toString();
			
			tomcatURI=tomcatURI.replace(serverData, StartVariables.serverPort);
			tomcatURI=tomcatURI.replace(tomcatContex, StartVariables.context);
			
			int cutPosition=tomcatURI.indexOf("://")+3;		
			String prefix=StartVariables.schema;
			tomcatURI=prefix+tomcatURI.substring(cutPosition).replace("//", "/");
			
			selfRequest=tomcatURI;
		}
		else
		{
			selfRequest=request.getRequestURL().toString();
		}
		return selfRequest;
	}
	
	
	public static String getValueFromStringArray(String[] param)	
	{
		String SEPARATOR = ",";
		String value="";
		for (String v:param)
		{
			value+=v+SEPARATOR;
		}
		if (value.endsWith(SEPARATOR))
		{
			value=StringUtils.chop(value);
		}
		return value;
	}
	
	
	public static Map<String,String> pageMetadataCalculation(HttpServletRequest request, long total, int pageSize, String uriBase, String context )
	{
		PageInfo info=Util.getPageInfoFromURL(request,pageSize, uriBase, context);
		log.debug(info.toString());
		//Numero de paginas
		long numPages=total/pageSize;
		long resto=total%pageSize;
		if (resto!=0)
		{
			numPages++;
		}
		
		HashMap<String,String> pagesMetadata=new HashMap<String,String>();
		
		//Pagina actual
		pagesMetadata.put(Constants.SELF, info.toString());
		
		//Pagina inicial
		PageInfo aux=new PageInfo(info);
		aux.setPage(Constants.defaultPage);		
		pagesMetadata.put(Constants.FIRST, aux.toString());
		//Ultima página
		aux.setPage(numPages);		
		pagesMetadata.put(Constants.LAST, aux.toString());
		//Pagina siguiente
		if (info.getPage()<=Constants.defaultPage)
		{
			if (numPages>Constants.defaultPage)
			{
				aux=new PageInfo(info);
				aux.setPage(Constants.defaultPage+1);			
				pagesMetadata.put(Constants.NEXT, aux.toString());
			}
		}else {
			aux=new PageInfo(info);					
			if (aux.getPage()+1<=numPages)
			{
				aux.setPage(aux.getPage()+1);				
				pagesMetadata.put(Constants.NEXT, aux.toString());
			}
		}
		//Pagina anterior
		if (info.getPage()>Constants.defaultPage)
		{
			aux=new PageInfo(info);		
			//Si me meten un pagina mayor al numero de paginas
			if (aux.getPage()>numPages)
			{						
				aux.setPage(numPages);
			}else {
				aux.setPage(aux.getPage()-1);	
			}
			pagesMetadata.put(Constants.PREV, aux.toString());
		}		
		
		
		return pagesMetadata;
	}
	
	
	public static HttpHeaders extractHeaders(Result<?> result)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constants.SELF, result.getSelf());
		headers.add(Constants.FIRST, result.getFirst());
		headers.add(Constants.LAST, result.getLast());
		if (Util.validValue(result.getNext())) {
			headers.add(Constants.NEXT, result.getNext());
		}
		if (Util.validValue(result.getPrev())) {
			headers.add(Constants.PREV, result.getPrev());
		}		
		
		headers.add(Constants.PAGE, result.getPage()+"");
		headers.add(Constants.PAGESIZE, result.getPageSize()+"");
		headers.add(Constants.PAGERECORDS, result.getPageRecords()+"");
		headers.add(Constants.TOTALRECORDS, result.getTotalRecords()+"");		
		headers.add(Constants.STATUS, result.getStatus()+"");
		headers.add(Constants.RESPONSEDATE, dateTimeFormatter.format(result.getResponseDate()));
		headers.add(Constants.CONTENT_MD5, result.getContentMD5());
		
		if (Util.validValue(result.getProjectedCoordinates())) {
			headers.add(Constants.STR_PROJECTEDCOORDINATES, result.getProjectedCoordinates());
		}
		
		if (Util.validValue(result.getGeographicCoordinates())) {
			headers.add(Constants.STR_GEOGRAPHICCOORDINATES, result.getGeographicCoordinates());
		}
		
		return headers;
		
	}


	public static ArrayList<String> checkMultiValuedParamInList(String fields, List<String> availableFields)
	{
		ArrayList<String> errors=new ArrayList<String>();
		
		String[] splittedFields = fields.split(",");
		
		for (String field:splittedFields)
		{
			if (availableFields.contains(field)==false)
			{
				errors.add(field);
			}
		}
		return errors;
	}

	
	public static String getDatabaseTypeFromDriver(String driver)
    {
        String databaseType="";
        
        if (driver.toLowerCase().contains(Constants.ORACLE))
        {
            databaseType=Constants.ORACLE;
        }else if (driver.toLowerCase().contains(Constants.MYSQL))
        {
            databaseType=Constants.MYSQL;
        }else if  (driver.toLowerCase().contains(Constants.SQLSERVER)) 
        {
        	 databaseType=Constants.SQLSERVER;
        }else {
        	databaseType = "ERROR";
        }
        return databaseType;
    }
	
	public static boolean contains(String _strToFind, ArrayList<String> list) {
		boolean result=false;
		if (list!=null && !list.isEmpty()) {
			if (_strToFind!=null && !"".equals(_strToFind)) {
				for (String obj:list) {
					if (_strToFind.contains(obj)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	
	
	public static String stripAccents(String s) 
	{
		if (s!=null)
		{
			s = Normalizer.normalize(s, Normalizer.Form.NFD);
			s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		}
	    return s;
	}
	
	
	public static String urlify(String chain) {

		chain = chain.trim().toLowerCase();
		
		while (chain.indexOf("  ") >= 0)
			chain = chain.replace("  ", " ");
		chain = chain.replace(" ", "-");
		
		chain = chain.replaceAll("\\s+", "_");
		

		chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
		chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		

		try {
			chain = URLEncoder.encode(chain, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
			log.error("Unsuported encoding",e);
		}

		while (chain.indexOf("--") >= 0)
			chain = chain.replace("--", "-");

		return chain;

	}
	
	
	public static String checkAndSetEncodedProperty(String propertyValue){
		
		String decrypted="";
		if (propertyValue!=null) {
			if  (textEncryptor ==null)
				{
				textEncryptor=new BasicTextEncryptor();												
					textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);
				}
			
			if (propertyValue.startsWith(Constants.PREFIX_PROPERTY_ENCODED))
			{	    	
				String encryptedText=propertyValue;
				encryptedText=encryptedText.substring(Constants.PREFIX_PROPERTY_ENCODED.length(),encryptedText.length()-1);
				decrypted=textEncryptor.decrypt(encryptedText);
			}
			else
			{
				decrypted=propertyValue;
			}		 
		}
		return decrypted;
	}
	
	
	
	public static String generateHash(String original) {

        // log.info(original);

        StringBuffer sb = new StringBuffer();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("Error with hash algorith", e);
        }

        if (md != null) {
            md.update(original.getBytes());
            byte[] digest = md.digest();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
        }
        return sb.toString();
    }
	
	//Generación del HASH
	public static <T> String toString(List<T> records)
	{
		String result=" ";
		if (records!=null && !records.isEmpty())
		for (Object obj: records ) {
			if (obj != null)
			{			
				result+= obj.toString();
			}
		}
		return result;
	}
	
	public static String decodeURL(String theURL)	{
		
		try
		{
			String decodedURL=URLDecoder.decode(theURL, "UTF-8");
			return decodedURL;
		} catch (UnsupportedEncodingException e)
		{
			log.error("Error decoding URL",e);
			return theURL;
		}
		
	}
	
	public static String encodeURL(String theURL)	{
	
		try
		{
			String decodedURL=URLEncoder.encode(theURL, "UTF-8");
			return decodedURL;
		} catch (UnsupportedEncodingException e)
		{
			log.error("Error decoding URL",e);
			return theURL;
		}
		
	}
	
	public static String generateID(String id, String pattern) {
		
		return generateID(id, pattern,false);
	}
	
	public static String generateID(String id, String pattern, boolean withDate) {
		String result = new String("");
		
		if (withDate) {
			String fecha = formatearFechas(new Date(), "yyyyMMddHHmmssSSS");
			result+=fecha;
		}
		if (validValue(pattern)) {
			result+=pattern;
		}
		result+=id;
		return result;
	}
	
	
	public static ResponseEntity<Object> negotiationContent(HttpServletRequest request)
	{	
		ResponseEntity<Object> response=null;
		HttpHeaders headers = new HttpHeaders();
		String red=Util.getFullURL(request);
		
		URL aURL = null;
		try
		{
			aURL = new URL(red);
		} 
		catch (MalformedURLException e1)
		{
			log.error("Error generating URL from request",e1);
			return response;
		}
		
		String acceptHeader=request.getHeader("ACCEPT");		
		if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==false))
		{	
			headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".json"));
			response=new ResponseEntity<Object>(headers,HttpStatus.SEE_OTHER);			
		}
		else if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==true))
		{					
			if (acceptHeader.contains(Constants.mimeHTML))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".html"));
			}else if (acceptHeader.contains(Constants.mimeJSON))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".json"));
			}else if (acceptHeader.contains(Constants.mimeXML))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".xml"));
			} 
			else if (acceptHeader.contains(Constants.mimeCSV))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".csv"));
			}
			else if (acceptHeader.contains(RDFConverter.RDF_XML))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".rdf"));
			}
			else if (acceptHeader.contains(RDFConverter.TURTLE))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".ttl"));
			}
			else if (acceptHeader.contains(RDFConverter.N3))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".n3"));
			}
			else if (acceptHeader.contains(RDFConverter.JSONLD))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".jsonld"));
			}	
			//CMG NUEVOS FORMATOS
			else if (acceptHeader.contains(Constants.mimeGEOJSON))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".geojson"));
			}
			else if (acceptHeader.contains(Constants.mimeGEORSS))
			{
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".georss"));
			}
			else {
				//Sin formato definido o valido se devuelve por defecto JSON
				headers.add(Constants.HEADER_LOCATION, red.replace(aURL.getPath(), aURL.getPath()+".json"));
			}
		}
		if (headers.size()>0)
		{
			response=new ResponseEntity<Object>(headers,HttpStatus.SEE_OTHER);	
		}
		
		return response;
	}
	
	
	public static void printHeadersFromRequest(HttpServletRequest request)
	{
		 Enumeration<?> headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	        	log.info(key +":"+value);
	        }
	}
	

	public static int searchString (String [] lista,String cadena) {
		int indice = -1; //si no existe en el listado;
		for (int i=0; i < lista.length; i++) {
			String obj = lista[i];
			if (obj.equals(cadena)) {
				indice = i;
				break;
			}
		}
		
		return indice;
	}
	
	public static boolean exitString (String [] lista,String cadena) {
		return searchString(lista, cadena)!=-1;
	}
	
	public static int searchString (String [][] lista,String cadena) {
		int indice = -1; //si no existe en el listado;
		for (int i=0; i < lista.length; i++) {
			String listAux [] = lista[i];
			indice = searchString(listAux,cadena);
			if (indice >=0) {
				indice = i;
				break;
			}
		}
		
		return indice;
	}
	
	public static List<RequestType> getRequestType(Properties properties,String key, List<RequestType> listRequestType) {
		
		log.info("[getRequestType] start...");
		
		if (properties==null || listRequestType == null || listRequestType.size()==0) {
			log.info("[getRequestType] ["+key+"] "+LiteralConstants.TXT_CUSTOM_REQUEST_OFF);
		}else {
			
			String list_public_auth = properties.getProperty(Constants.STR_REQUEST_PUBLIC_AUTH);
			listRequestType = verifyRequestByParamAuth(list_public_auth, key, listRequestType, Constants.NO_AUTH);
			
			String list_basic_auth = properties.getProperty(Constants.STR_REQUEST_BASIC_AUTH);
			listRequestType = verifyRequestByParamAuth(list_basic_auth, key, listRequestType, Constants.BASIC_AUTH);
			
			
			String list_admin_auth = properties.getProperty(Constants.STR_REQUEST_ADMIN_AUTH);			
			listRequestType = verifyRequestByParamAuth(list_admin_auth, key, listRequestType, Constants.ADMIN_AUTH);
			
			log.info("[getRequestType] ["+key+"] "+LiteralConstants.TXT_CUSTOM_REQUEST_ON);											
		}
		
		return listRequestType;
		
	}
	
	
	public static void generaXYfromLATLONG(String srId, List listado)
	{
		 generaXYfromLATLONG( null,  srId, listado);
	}
	
	public static void generaXYfromLATLONG(String source, String srId, List listado)
	{
		CoordinateTransformer ct = new CoordinateTransformer(source,srId);
		
		for (int i=0;i<listado.size();i++)		
		{
			GeoModel geomodel=(GeoModel) listado.get(i);
			if ((validValue(geomodel.getLatitud()))&&(validValue(geomodel.getLongitud())))
			{
				BigDecimal latitud = geomodel.getLatitud();
				BigDecimal longitud = geomodel.getLongitud();												
				double[] transformCoordinates = ct.transformCoordinates(latitud.doubleValue(), longitud.doubleValue());						
				geomodel.setX(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				geomodel.setY(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
			}
		}
	}
	
	public static void generaLATLONGfromXY( List listado)
	{
		generaLATLONGfromXY( null, listado);
	}
	
	public static void generaLATLONGfromXY(String source, List listado)
	{
		CoordinateTransformer ct = new CoordinateTransformer(source,StartVariables.SRID_LAT_LON_APP);
		
		for (int i=0;i<listado.size();i++)		
		{
			GeoModel geomodel=(GeoModel) listado.get(i);
			if ((validValue(geomodel.getX()))&&(validValue(geomodel.getY())))
			{
				BigDecimal x = geomodel.getX();
				BigDecimal y = geomodel.getY();		
				//Alternamos posición X e Y
				double[] transformCoordinates = ct.transformCoordinates(y.doubleValue(), x.doubleValue());	
				//Alternamos la salida del vector para Lat y lon
				geomodel.setLatitud(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				geomodel.setLongitud(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
			}
		}
	}
	
	public static void generaLATLONGfromXY(String source, GeoModel geomodel)
	{	
		ArrayList<GeoModel> l=new ArrayList<GeoModel>();
		l.add(geomodel);
				
		generaLATLONGfromXY(source,l);
	}
	
	public static void generaXYfromLATLONG(String source,String srId, GeoModel geomodel)
	{	
		ArrayList<GeoModel> l=new ArrayList<GeoModel>();
		l.add(geomodel);
				
		generaXYfromLATLONG(source,srId,l);
	}
	
	public static void generaCoordenadasAll( String srId, GeoModel geomodel)
	{
		ArrayList<GeoModel> l=new ArrayList<GeoModel>();
		l.add(geomodel);
		generaCoordenadasAll(StartVariables.SRID_XY_APP, srId, l);
	}
	
	public static void generaCoordenadasAll( String srId, List listado)
	{
		Util.generaCoordenadasAll(StartVariables.SRID_XY_APP, srId, listado);
	}
	
	public static void generaCoordenadasAll(String source, String srId, List listado)
	{	
		//control para los objetos que o extienden de GeoModel
		boolean isTransformacionXY = true;
		if (isObjectGeoModel(listado)) {
			
			String target = StartVariables.SRID_LAT_LON_APP;
			//Comprobamos el srId si es para x e y o Lat y lon
			if (validValue(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				target = srId;
				isTransformacionXY = false;
			}
			//1 obtenemos lat y lon Siempre se genera esta transformación, activamos true, para que permita la transformacion
			CoordinateTransformer ct1 = new CoordinateTransformer(source,target);
						
			for (int i=0;i<listado.size();i++)		
			{
				GeoModel geomodel=(GeoModel) listado.get(i);
				if ((validValue(geomodel.getX()))&&(validValue(geomodel.getY())))
				{
					BigDecimal x = geomodel.getX();
					BigDecimal y = geomodel.getY();		
					//Alternamos posición X e Y
					double[] transformCoordinates = ct1.transformCoordinates(y.doubleValue(), x.doubleValue());	
					//Alternamos la salida del vector para Lat y lon
					geomodel.setLatitud(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					geomodel.setLongitud(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				}
			}
			

			
			//Es necesario comprobar si debe aplicar transformación a las X e Y ya que puede ser que no sea el caso
			if (validValue(srId) && isTransformacionXY) {
				// 2 transformamos x e y segun el parametro srId y las coordenadas lat / long
				// generadas anteriormente.
				CoordinateTransformer ct2 = new CoordinateTransformer(source, srId);

				for (int i = 0; i < listado.size(); i++) {
					GeoModel geomodel = (GeoModel) listado.get(i);
					if ((validValue(geomodel.getX()))&&(validValue(geomodel.getY())))
					{
						BigDecimal x = geomodel.getX();
						BigDecimal y = geomodel.getY();		
						
						double[] transformCoordinates = ct2.transformCoordinates(x.doubleValue(), y.doubleValue());	
						
						geomodel.setX(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
						geomodel.setY(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					}
				}
			}//fin if validValue(srId)
			
		}//fin if isGeomodel 
		
		if(isObjectITrafico(listado)) 
		{
			String target = StartVariables.SRID_LAT_LON_APP;
			//Comprobamos el srId si es para x e y o Lat y lon
			if (validValue(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				target = srId;
				isTransformacionXY = false;
			}
			//1 obtenemos lat y lon Siempre se genera esta transformación, activamos true, para que permita la transformacion
			CoordinateTransformer ct1 = new CoordinateTransformer(source,target);
						
			for (int i=0;i<listado.size();i++)		
			{
				ITrafico iTrafico = (ITrafico) listado.get(i);
				if ((validValue(iTrafico.getFinX()))&&(validValue(iTrafico.getFinY())))
				{
					BigDecimal x = iTrafico.getFinX();
					BigDecimal y = iTrafico.getFinY();		
					//Alternamos posición X e Y
					double[] transformCoordinates = ct1.transformCoordinates(y.doubleValue(), x.doubleValue());	
					//Alternamos la salida del vector para Lat y lon
					iTrafico.setFinLatitud(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					iTrafico.setFinLongitud(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
				}
			}
			

			
			//Es necesario comprobar si debe aplicar transformación a las X e Y ya que puede ser que no sea el caso
			if (validValue(srId) && isTransformacionXY) {
				// 2 transformamos x e y segun el parametro srId y las coordenadas lat / long
				// generadas anteriormente.
				CoordinateTransformer ct2 = new CoordinateTransformer(source, srId);

				for (int i = 0; i < listado.size(); i++) {
					ITrafico iTrafico = (ITrafico) listado.get(i);
					if ((validValue(iTrafico.getFinX()))&&(validValue(iTrafico.getFinY())))
					{
						BigDecimal x = iTrafico.getFinX();
						BigDecimal y = iTrafico.getFinY();		
						
						double[] transformCoordinates = ct2.transformCoordinates(x.doubleValue(), y.doubleValue());	
						
						iTrafico.setFinX(new BigDecimal(transformCoordinates[1]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
						iTrafico.setFinY(new BigDecimal(transformCoordinates[0]).setScale(Constants.NUM_DECIMALS_XY, BigDecimal.ROUND_HALF_UP));
					}
				}
			}//fin if validValue(srId)
		//fin if isObjectDobleGeoModel	
		}
		
	}
	
	public static void generaDataCubeInfo(List listado)
	{	
		//control para los objetos que o extienden de GeoModel		
		if (isObjectDataCubeModel(listado)) {
						
			for (int i=0;i<listado.size();i++)		
			{
				DataCubeModel dataCubeModel=(DataCubeModel) listado.get(i);
				dataCubeModel.asignaCubo();
				dataCubeModel.asignaDSD();
			}			
			
		}//fin if isGeomodel
		
	}
	
	
	/* Private Method Auxiliar */
	
	private static List<RequestType> verifyRequestByParamAuth(String listService, String key,
			List<RequestType> listRequestType, String typeAuth) {

		String SEPARATOR = ",";
		if (listService != null && !"".equals(listService)) {

			log.info("[getRequestType] [" + key + "] [Authentication:" + typeAuth + "] "
					+ LiteralConstants.TXT_CUSTOM_REQUEST_ON);

			StringTokenizer strTokenizer = new StringTokenizer(listService.trim(), SEPARATOR);
			while (strTokenizer.hasMoreTokens()) {
				String aux = strTokenizer.nextToken();
				for (RequestType obj : listRequestType) {
					if (aux.equals(obj.getId())) {
						obj.setAuthenticate(typeAuth);
					}
				}
			}
		} // Fin del AUTH

		return listRequestType;
	}

	
	public static <T> String extractKeyFromModelClass(Class<T> type)
	{
		String s="";
		if (type!=null)
		{
			String fullName=type.getName();
			fullName=fullName.toLowerCase();
			if (fullName.contains(".")) 
			{				
				s=fullName.substring(fullName.lastIndexOf(".")+1, fullName.length());
			}
			else 
			{
				s=fullName;
			}
		}
		return s;
	}
	
	public static <T> String extractNameFromModelClass(Class<T> type)
	{
		String s="";
		if (type!=null)
		{
			String fullName=type.getName();			
			if (fullName.contains(".")) 
			{				
				s=fullName.substring(fullName.lastIndexOf(".")+1, fullName.length());
			}
			else 
			{
				s=fullName;
			}
		}
		return s;
	}
	
	
	public static <T> String extractEntityFromModelClass(Class<T> type)
	{
		Table  table = type.getAnnotation(Table.class);
		String nameEntity = table.name();
		return nameEntity;
	}
	
	//Clase para validar los campos asociados al RDF
	public static boolean validatorFieldRDF (String field, String anotation) {
		boolean result = false;
		//TODO Excepción en la integración con callejero (Revisar si se toca el módulo en un futuro realizar el cambio correctamente).
		if ("portalId".equals(field)) {
			field="portal";
		}
		
		//1º Validacionn nombre de campo iguales
		if (field.toLowerCase().equals(anotation.toLowerCase())) {
			return true;
		}//2º Validacion de que aparezca id en campo y identifier en anotacion
		else if (field.toLowerCase().contains("id")) {
			if ("identifier".equals(anotation)) {
				return true;
			}
		}
		
		//Puede darse el caso de que la cadena id Aparezca en la palabra (Ejemplo: entIDad)
		//Por lo que debe generarse una validacion aparte no un else if
		
		//3º El Campo tiene title la anotacion deberia contener title
		if (field.toLowerCase().contains("title")) {
			if ("title".equals(anotation)) {
				return true;
			}else if (field.toLowerCase().contains(anotation.toLowerCase())){
				return true;
			}
		}//4º La anotación contiene parte de la estructura del campo
		else if (anotation.toLowerCase().contains(field.toLowerCase())){
			return true;
		}//5º El campo contiene parte de la anotación
		else if (field.toLowerCase().contains(anotation.toLowerCase())){
			return true;
		}//6º la anotacion esta costruida con guiones
		else if (anotation.contains("-")) {
			String aux = anotation.replaceAll("-", "");
			 if (field.toLowerCase().contains(aux.toLowerCase())){
				 return true;
			 }
		}
		else {
			result = false;
		}
		
		return result;
		
	}
	
	// Clase para validar los campos asociados al RDF implementando excepcions
	// {{"subvencionId","organizationId","orgIdObligadoPrestacion","distritoId","prorrogaId"},{"instrumenta","gestionadoPor",
	// "obligadoPrestacionesAyuntamiento","gestionadoPor","esProrrogaDe"}}
	public static boolean validatorFieldRDF(String field, String anotation, String[][] exceptionFileds) {

		if (exceptionFileds != null && exceptionFileds.length > 0) {
			if (Util.exitString(exceptionFileds[0], field)) {
				int indice = Util.searchString(exceptionFileds[0], field);
				String anotationAux = exceptionFileds[1][indice];
				if (anotation.equals(anotationAux)) {
					return true;
				}
			}
		}

		return validatorFieldRDF(field, anotation);

	}
	
	public static boolean isUrlParam(String valor) {
		boolean result = false;
		if (valor!=null && !"".equals(valor)) {
			result = valor.toLowerCase().startsWith("http");
		}
		
		return result;
	}
	

	/*Mejor no utilizar, solo para emergencias. Pasa de notacion java a campo de bbdd
	 * Ejemplo: estacionNombre -> estacion_nombre*/
	public static String javaNameToTableField(String javaName)
	{
		String fieldName = javaName.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
		fieldName=fieldName.toLowerCase().replace(" ", "_");
		return fieldName;
	}
	
	/**
	 * Metodo para Validar los campos de la Ordenación 
	 * recibidos en las peticiones (Sort)
	 * @param sort
	 * @param fields
	 * @return
	 */
	public static ResponseEntity<?> validateSort(String sort, List<String> availableFields) {
		List<String> errors = new ArrayList<String>();
		ResponseEntity<?> responseEntity = null;
		if (sort != null && !"".equals(sort)) {
			String[] listado = sort.split(Constants.SORT_SEPARATOR);

			for (String obj : listado) {
				
				String  sortField = "";
				if (obj.startsWith(Constants.SORT_DESC)) {
					  sortField = obj.substring(1, obj.length());
				} else {
					  sortField = obj;
				}
				
				if (availableFields.contains(sortField)==false)
				{
					errors.add(sortField);
				}
			}
			if (!errors.isEmpty()) {
				String filedError = "";
				for (String obj:errors) {
					filedError+=" "+obj;
				}
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong sort ["+filedError+"] in sort param"));
			}
		}
		
		

		return responseEntity;
	}
	
	/*Metodo para validar los parametros reibidos
	 * parameterMap: mapa con todos los parametros recibidos
	 * availableFields: campos propios del conjunto de datos a validar
	 * */
	public static ResponseEntity<?> validateParams(Map<String, String[]> parameterMap, List<String> availableFields) 
	{	
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.add(Constants.FIELDS);
		allowedParams.add(Constants.RSQL_Q);
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.SORT);
		allowedParams.add(Constants.AJAX_PARAM);
		allowedParams.add(Constants.SRID);
		allowedParams.add(Constants.XETRS89);
		allowedParams.add(Constants.YETRS89);
		allowedParams.add(Constants.METERS);
		
		return validateParams(parameterMap, availableFields,allowedParams);
	}
	
	/*Metodo para validar los parametros reibidos
	 * parameterMap: mapa con todos los parametros recibidos
	 * availableFields: campos propios del conjunto de datos a validar
	 * */
	public static ResponseEntity<?> validateParams(Map<String, String[]> parameterMap, List<String> availableFields,List<String> allowedParams) 
	{	

		allowedParams.addAll(availableFields);
		List<String> wrongParamList=new ArrayList<String>();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
		    if (allowedParams.contains(entry.getKey())==false)
		    {
		    	wrongParamList.add(entry.getKey());
		    }		    
		}
		
		if (wrongParamList.size()>0)
		{
			String listString = String.join(", ", wrongParamList);					
			BadRequestException e=new BadRequestException("Unknown parameters: "+listString);
			return ExceptionUtil.checkException(e);
		}
		
		return null;
	}
	
	/**
	 * Metodo para mostrar o no las referencias de las geolocalizacion
	 * @param obj Result
	 * @param srId String
	 */
	public static void showCoordinates(Result obj,String srId) {
		
		if (isObjectGeoModel(obj.getRecords())){
		
			//CMG Control para mostrar las coordenadas
			String srIdXY=StartVariables.SRID_XY_APP;
			String srIdLatLon=StartVariables.SRID_LAT_LON_APP;
			if (!"".equals(srId) && CoordinateTransformer.comprobarSrIdXY(srId)) {
				srIdXY = srId;
				((Result<?>) obj).setProjectedCcoordinates(srIdXY);
				((Result<?>) obj).setGeographicCoordinates(srIdLatLon);
			}else if (!"".equals(srId) && CoordinateTransformer.comprobarSrIdLatLon(srId)) {
				srIdLatLon=srId;
				((Result<?>) obj).setProjectedCcoordinates(srIdXY);
				((Result<?>) obj).setGeographicCoordinates(srIdLatLon);
			}
			else {
				((Result<?>) obj).setProjectedCcoordinates(srIdXY);
				((Result<?>) obj).setGeographicCoordinates(srIdLatLon);
			}
		}
	}
	
	
	
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de Geomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectGeoModel(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				GeoModel aux=(GeoModel) listado.get(0);
				result = true;
			}catch (Exception e) {
				log.info("[isObjectGeoModel] [listado] No is a object GeoModel");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	/**
	 * Metodo para ver si los objetos de un listado extienden de DobleGeomodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectITrafico(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				ITrafico aux=(ITrafico) listado.get(0);
				result = true;
			}catch (Exception e) {
				log.info("[isObjectITrafico] [listado] No is a object ITrafico");
				result = false;
			}//Fin control
		}
		return result;
	}
	
	

	/**
	 * Metodo para ver si los objetos de un listado extienden de DataCubemodel.
	 * @param listado
	 * @return
	 */
	public static boolean isObjectDataCubeModel(List listado) {
		boolean result=false;
		if (listado!=null && listado.size()>0) {
			try {
				DataCubeModel aux=(DataCubeModel) listado.get(0);
				result = true;
			}catch (Exception e) {
				log.info("[isObjectDataCubeModel] [listado] No is a object DataCubeModel");
				result = false;
			}//Fin control
		}
		return result;
	}
	

	

	
	public static String getExtensionUri(String uri) {
		//String uri = "http://www.google.com/support/enterprise/static/gsa/docs/admin/70/gsa_doc_set/integrating_apps/images/google_logo.png";
		String extension="";
		if(uri.contains("/")) {
		    String cortoURL = uri.substring(uri.lastIndexOf("/"));
		    if ("/".equals(cortoURL)) {
		    	extension="/";
		    }
		    else if (cortoURL.contains("?")) {
		    	String sinArgumentos = cortoURL.substring(0, cortoURL.indexOf('?'));
		    	if (sinArgumentos.contains(".")) {
		    		extension =  sinArgumentos.substring(sinArgumentos.lastIndexOf(".")+1);
		    	}
		    	
		    }else {
		    	if (cortoURL.contains(".")) {
		    		extension =  cortoURL.substring(cortoURL.lastIndexOf(".")+1);
		    	}
		    }
		}
		return extension.toLowerCase();
	}
		
	
	public static Optional<String> getFileExtension(final URL url) {

	    Objects.requireNonNull(url, "url is null");

	    final String file = url.getFile();

	    if (file.contains(".")) {

	        final String sub = file.substring(file.lastIndexOf('.') + 1);

	        if (sub.length() == 0) {
	            return Optional.empty();
	        }

	        if (sub.contains("?")) {
	            return Optional.of(sub.substring(0, sub.indexOf('?')));
	        }

	        return Optional.of(sub);
	    }

	    return Optional.empty();
	}
	
	public static boolean isValidExtensionOfUri(String extension) {
		
		if (validValue(extension)==false)
		{
			return true;
		}		
		
		if (!"/".equals(extension)) {
			for (String objExtension: Constants.FORMATOS_EXTENSIONES) {
				if (extension.equals(objExtension)) {
					return true;
				}
			}
			return false;
		}else {
			//PARA LA CARGA DE CONTEXTO servidor/API/
			return true;
		}
		
	}
	
	
	public static String jsonPrettyPrint(String ugglyJson)
	{	
		JsonObject obj = prettyPrintParser.parse(ugglyJson).getAsJsonObject();		
		String prettyJson = prettyGson.toJson(obj);
		return prettyJson;		
	}
	
	public static JSONObject stringToJSONObject(String json)
	{	
		JSONObject obj=new JSONObject();
		try
		{
			obj = (JSONObject) JSONParser.parse(json);
		} catch (ParseException e)
		{
			log.error("Error parsing JSONdata",e);			
		}
		return obj;
		
	}
	
	public static boolean isPathAbsolute(String path) {
		boolean result = false;
		
			try {
				File file = new File(path);
				if (file.isDirectory()) {
					result = true;
				}
			} catch (Exception e) {
				log.error("[path:" + path + "] is not path valid");
			}
		
		
		return result;
	}
	
	
	public static boolean validURL(HttpServletRequest request)	
	{
		//como mucho 1 punto por servlet path, que genera 2 cadenas al hacer split
		int limit=2;
        String requestURL = request.getServletPath();
        String[] split = requestURL.split("\\.");
        if (split.length>limit)
        {
        	return false;
        }
        return true;
	                
	}
	
	
	public static boolean validateURL(String URL) {
		try {
	        new URL(URL);	        
	    } catch (MalformedURLException malformedURLException) {
	        malformedURLException.printStackTrace();
	        return false;
	    }
		return true;
	}
	
	public static String prepareStringToCompare(String text)
	{
		text=text.toLowerCase();
		text=StringUtils.stripAccents(text);
		return text;
	}
	
	public static boolean isGeoLocationPetition(List<String> availableFields,HttpServletRequest request) {
		
		boolean result = false;
		String red=Util.getFullURL(request);
		
		URL aURL = null;
		try
		{
			aURL = new URL(red);
		} 
		catch (MalformedURLException e1)
		{
			log.error("Error generating URL from request",e1);
			return result;
		}
		
		String acceptHeader=request.getHeader("ACCEPT");		
		if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==false))
		{	
			return result;
		}
		else if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==true)&&(acceptHeader.equals("*/*")==false))
		{					
			
			if (acceptHeader.contains(Constants.MEDIA_TYPE_GEORSS))
			{
				result=true;
			}
			else if (acceptHeader.contains(Constants.MEDIA_TYPE_GEORSS))
			{
				result=true;
			}
								
		}
		else if (aURL.getPath().contains("."))
		{
			String ext=getExtensionUri(aURL.getPath());
			if (ext.contains("georss"))
			{
				result=true;
			}
			else if (ext.contains("geojson"))
			{
				result=true;
			}	
		}
		
		return result;
	}
	
	
	public static boolean isSemanticPetition(HttpServletRequest request)
	{	
		boolean semantic=false;
		String red=Util.getFullURL(request);
		
		URL aURL = null;
		try
		{
			aURL = new URL(red);
		} 
		catch (MalformedURLException e1)
		{
			log.error("Error generating URL from request",e1);
			return semantic;
		}
		
		String acceptHeader=request.getHeader("ACCEPT");		
		if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==false))
		{	
			return semantic;
		}
		else if ((aURL.getPath().contains(".")==false)&&(Util.validValue(acceptHeader)==true)&&(acceptHeader.equals("*/*")==false))
		{					
			
			if (acceptHeader.contains(RDFConverter.RDF_XML))
			{
				semantic=true;
			}
			else if (acceptHeader.contains(RDFConverter.TURTLE))
			{
				semantic=true;
			}
			else if (acceptHeader.contains(RDFConverter.N3))
			{
				semantic=true;
			}
			else if (acceptHeader.contains(RDFConverter.JSONLD))
			{
				semantic=true;
			}							
		}
		else if (aURL.getPath().contains("."))
		{
			String ext=getExtensionUri(aURL.getPath());
			if (ext.contains("rdf"))
			{
				semantic=true;
			}
			else if (ext.contains("ttl"))
			{
				semantic=true;
			}
			else if (ext.contains("n3"))
			{
				semantic=true;
			}
			else if (ext.contains("jsonld"))
			{
				semantic=true;
			}			
		}
		
		return semantic;
	}
	
	public static boolean isTraficoIntegration() {
		String nameControler="TraficoTramoController";
		return exitController(nameControler);
	}
	
	public static boolean isCallejeroIntegration() {
		String nameControlerTerritorio="CallejeroPortalController";
		return exitController(nameControlerTerritorio);
	}
	
	public static boolean isCallejeroViaIntegration() {
		String nameControlerTerritorio="CallejeroViaController";
		return exitController(nameControlerTerritorio);
	}
	
	public static boolean isOrganigramaIntegration() {
		String nameControler="OrganigramaController";
		return exitController(nameControler);
	}
	
	public static boolean isEquipamientoIntegration() {
		String nameControler="EquipamientoController";
		return exitController(nameControler);
	}
	
	public static boolean isSubvencionIntegration() {
		String nameControler="SubvencionController";
		return exitController(nameControler);
	}
	
	public static boolean exitController(String nameController) {
		boolean result=false;
		if (nameController!=null) {
			if (StartVariables.listControllers!= null && !StartVariables.listControllers.isEmpty()) {
				for (String nameC : StartVariables.listControllers) {
					if (nameC.contains(nameController)) {
						result= true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static String extractSrIdFromURL(String stringURL) {
		String petitionSrId="";
		if (stringURL.contains(Constants.SRID))
		{
			petitionSrId=stringURL.substring(stringURL.indexOf(Constants.SRID)+Constants.SRID.length()+1);
			if (petitionSrId.contains("&"))
			{
				petitionSrId=petitionSrId.substring(0,petitionSrId.indexOf("&"));					
			}
			petitionSrId=petitionSrId.replace("EPSG:", "");
			log.info(Constants.SRID+" extraido de la URL: "+petitionSrId);
		}
		return petitionSrId;
	}
	
	
	@SuppressWarnings("unchecked")
	/*
	 * Metodo que cambia el valor de una anotacion de manera dinámica
	 * */
	public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
	    Object handler = Proxy.getInvocationHandler(annotation);
	    Field f;
	    try {
	        f = handler.getClass().getDeclaredField("memberValues");
	    } catch (NoSuchFieldException | SecurityException e) {
	        throw new IllegalStateException(e);
	    }
	    f.setAccessible(true);
	    Map<String, Object> memberValues;
	    try {
	        memberValues = (Map<String, Object>) f.get(handler);
	    } catch (IllegalArgumentException | IllegalAccessException e) {
	        throw new IllegalStateException(e);
	    }
	    Object oldValue = memberValues.get(key);
	    if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
	        throw new IllegalArgumentException();
	    }
	    memberValues.put(key,newValue);
	    return oldValue;
	}
	
	//Metodo para reemplazar los Time en RSQL
	public static final String parserTimeByRSQL(String queryString) {
		String result = queryString;
		if (queryString!=null && !"".equals(queryString)) {
			ArrayList<Pair<String, String>> changesTime = RegularExpressions.getTimesWithDatesForRSQL(queryString);
		
			if (changesTime.size()>0)
			{
				log.info("before queryString: "+queryString);
			
				for (Pair<String,String> pair:changesTime)
				{
					result=result.replaceAll(pair.getKey(), pair.getValue());
				}	
			
				log.info("after where: "+result);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		
		ArrayList<String> listaURIs=new ArrayList<String>();
		listaURIs.add("http://localhost:8080/API/api.json");
		listaURIs.add("http://localhost:8080/API/api");
		listaURIs.add("http://localhost:8080/API/subvencion/subvencion.html");
		listaURIs.add("http://localhost:8080/API/subvencion/subvencion/groupBy.csv?fields=areaTitle,sum(importe),count(title)&group=areaTitle&sort=-sum(importe)&where=title%20like%20%27%pintura%%27");
		listaURIs.add("http://localhost:8080/API/subvencion/subvencion/groupBy?fields=areaTitle,sum(importe),count(title)&group=areaTitle&sort=-sum(importe)&where=title%20like%20%27%pintura%%27");
		listaURIs.add("http://localhost:8080/API/subvencion/subvencion");
		listaURIs.add("http://localhost:8080/API/subvencion/subvencion/");
		listaURIs.add("http://demoapi.localidata.com/OpenCitiesAPI/subvencion/subvencion.xml/groupBy.json?fields=title&group=title");
			
			
		
		
		for (String u:listaURIs)
		{
			String ext=getExtensionUri(u);
			System.out.println(u);
			System.out.println("\t"+ext);			
		}
	}
	
}
