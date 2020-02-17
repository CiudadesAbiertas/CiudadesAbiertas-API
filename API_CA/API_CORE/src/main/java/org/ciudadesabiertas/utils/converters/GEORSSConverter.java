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

package org.ciudadesabiertas.utils.converters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.BeanUtil;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Territorio;
import org.ciudadesabiertas.utils.Util;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rometools.modules.georss.GeoRSSModule;
import com.rometools.modules.georss.SimpleModuleImpl;
import com.rometools.modules.georss.geometries.LinearRing;
import com.rometools.modules.georss.geometries.Polygon;
import com.rometools.modules.georss.geometries.Position;
import com.rometools.modules.georss.geometries.PositionList;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.io.SyndFeedOutput;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 * @param <L>
 */
@Component
public class GEORSSConverter <T, L extends Result<T>> extends AbstractHttpMessageConverter<L> {

    
    private static final String GEORSS_MIME = Constants.mimeGEORSS;
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
	private static final Logger log = LoggerFactory.getLogger(GEORSSConverter.class);
	
	private static final Namespace NAMESPACE_CA = Namespace.getNamespace("ciudadesAbiertas", GEORSSParser.LINK_FEED);
	
	private static final Namespace NAMESPACE_ODATA = Namespace.getNamespace("d", GEORSSParser.ODATA_FEED);
	private static final Namespace NAMESPACE_METADATA_ODATA = Namespace.getNamespace("m", GEORSSParser.ODATA_METADATA_FEED);
	
	private static List<String> tratamientoId=new ArrayList<String>();
	
	static
	{
		tratamientoId.add("org.ciudadesabiertas.dataset.model.CubeDsdDimension");		
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Pais");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Autonomia");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Provincia");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Municipio");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Distrito");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.Barrio");
		tratamientoId.add("org.ciudadesabiertas.dataset.model.SeccionCensal");
		
	}
	
	private static Map<String,String> objectURIs=new HashMap<String,String>();
	
    public GEORSSConverter() {
        super(MediaType.valueOf(GEORSS_MIME));       
    }
  

    @Override
    protected boolean supports (Class<?> clazz) {
        return Result.class.isAssignableFrom(clazz);
    }
    
    @Override
    protected L readInternal (Class<? extends L> clazz, HttpInputMessage inputMessage)
              throws IOException, HttpMessageNotReadableException {
    	// TODO Auto-generated method stub
		return null;
    }
    
    
    @Override
    protected void writeInternal (L l, HttpOutputMessage outputMessage)
              throws IOException, HttpMessageNotWritableException {
        
    	
		//OutputStreamWriter outputStream = new OutputStreamWriter(outputMessage.getBody(),Charset.forName("UTF8"));       
		
    	boolean odataPetition=false;
 
        try {
        	
        	Charset charset = getCharset(outputMessage.getHeaders());
        	
    		StringBuilder respuesta=new StringBuilder();
    		
    		//ObjectMapper objectMapper = new ObjectMapper();
    		ObjectMapper objectMapper =StartVariables.jsonConverter.getObjectMapper();
    		
    		// Fechas sin timestamps
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			// Y con este formato
			objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
			objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
			
			List<T> records = l.getRecords();
			
			GEORSSParser geoRss = new GEORSSParser(l.getContentMD5(),
													l.getResponseDate(),
													l.getSelf());
			
			List <SyndEntry> entries = new ArrayList<SyndEntry>();
			String petitionSrId = Util.extractSrIdFromURL(l.getSelf());
			
			if (l.getSelf().contains(".odata"))
			{
				odataPetition=true;
			}
			
			if (odataPetition)
			{
				for (T record:records)
				{	
					{
						SyndEntry entry = transformOData(record,petitionSrId);	
						entries.add(entry);
					}
				}
			}
			else
			{
				for (T record:records)
				{	
					if (record instanceof Territorio)
					{
						List<SyndEntry> entriesFromPolygons = transformGeoRSSMultipolygon(record,petitionSrId);
						if (entriesFromPolygons!=null)
						{
							entries.addAll(entriesFromPolygons);
						}
					}
					else
					{
						SyndEntry entry = transformGeoRSS(record,petitionSrId);
						if (entry!=null)
						{													
							entries.add(entry);
						}
					}
				}
			}
			geoRss.getFeed().setEntries(entries);
			
    		try
    		{	   
    			SyndFeedOutput output = new SyndFeedOutput();
    			respuesta.append(output.outputString(geoRss.getFeed()));
    			OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);    			
    			writer.append(respuesta);
    			writer.close();
    		}
    		catch (Exception e)
    		{
    			log.error("Error generating GEORSS",e);
    			throw e;
    		}
    			
        } catch (ClassCastException cce) {
            throw cce;
    	
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
    }


	private SyndEntry transformGeoRSS(T record, String petitionSrId) throws Exception {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		SyndEntry entry = new SyndEntryImpl();
		
		BigDecimal lat=null;
		BigDecimal lon=null;	
	
		List <String> contentList = new ArrayList<String>();
						       
		String beanId="";
		String theURI="";
		
		theURI = generateURI(record, theURI);		
		
		for (BeanUtil bean:listData)
		{
			if (bean.getFieldName().equals("id"))
			{
				beanId=(String) bean.getValue();
			}
			
			//TRATAMIENTO GENERICO PARA GEORSS
			if (Constants.SUPPORTED_XY_SRIDS.contains(petitionSrId))
			{
				if (bean.getFieldName().equals("x"))
				{
					lat=(BigDecimal) bean.getValue();
				}
				else if (bean.getFieldName().equals("y"))
				{
					lon=(BigDecimal) bean.getValue();
				}
			}else {
				if (bean.getFieldName().equals("latitud"))
				{
					lat=(BigDecimal) bean.getValue();
				}
				else if (bean.getFieldName().equals("longitud"))
				{
					lon=(BigDecimal) bean.getValue();
				}
			}
										
			addFields(entry, contentList, bean);
		}
			
		entry.setLink(theURI+beanId);
		
		entry.setAuthor(GEORSSParser.AUTHOR_FEED_NAME);		
		
		if (Util.validValue(entry.getTitle())==false)
		{
			entry.setTitle(beanId);
		}
		
		if (Util.validValue(entry.getDescription())==false)
		{	
			SyndContent description = new SyndContentImpl();
	        description.setType("text/plain");
	        CDATA valueElement = new CDATA((String)GEORSSParser.DESCRIPTION+beanId);
	        description.setValue(valueElement.getText());
			entry.setDescription(description);
		}
		
		generaContent(entry, contentList);
		
		//Construimos el  geoRSSModule (punto con lat lon)
		if ((lat!=null)&&(lon!=null))
		{
			GeoRSSModule geoRSSModule = new SimpleModuleImpl();
	        geoRSSModule.setPosition(new Position(lat.doubleValue(), lon.doubleValue()));
	        entry.getModules().add(geoRSSModule);
		}else {
			return null;
		}
		
        
        
		return entry;
	}


	private String generateURI(T record, String theURI) {
		Class<? extends Object> recordClass = record.getClass();		
		Annotation[] annotations = recordClass.getAnnotations();		
		if (objectURIs.containsKey(recordClass.getName())==false)
		{			
			String contexto=StartVariables.context;
			String uriBase=StartVariables.uriBase;
			for (Annotation ann:annotations)
			{
				String anotationS = ann.toString();			
				if (anotationS.contains("anotations.PathId"))
				{
					theURI=ann.toString().substring(ann.toString().indexOf("value=")+7);
					//theURI=theURI.substring(0,theURI.lastIndexOf("\""));
					theURI=theURI.replace(")","");
					theURI=theURI.replace("\"","");
					
					theURI=(uriBase+contexto+theURI+"/");
					objectURIs.put(recordClass.getName(),theURI);
					break;
				}
			}
		}
		else
		{
			theURI=objectURIs.get(recordClass.getName());
		}
		return theURI;
	}
	
	
	private SyndEntry transformOData(T record, String petitionSrId) throws Exception {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		SyndEntry entry = new SyndEntryImpl();
		
		List <Element> listaElementos = new ArrayList<Element>();
						       
		for (BeanUtil bean:listData)
		{
			addFieldOdata(bean, listaElementos);
		}
		//listaElementos.add(element);
		//entry.setForeignMarkup(listaElementos);
		
		Element content = new Element("content");
		content.setAttribute(new Attribute("type","application/xml"));		
		content.addNamespaceDeclaration(Namespace.getNamespace("d",  GEORSSParser.ODATA_FEED));
		content.addNamespaceDeclaration(Namespace.getNamespace("m",  GEORSSParser.ODATA_METADATA_FEED));
		
		
		entry.setAuthor("CiudadesAbiertasAPI");
		
		Element properties = new Element("properties",NAMESPACE_METADATA_ODATA);
		content.addContent(properties);
		
		properties.addContent(listaElementos);
		
		
		List<Element> c=new ArrayList<Element>();
		c.add(content);
		
		entry.setForeignMarkup(c);
		
		
		return entry;
	}
	
	private List<SyndEntry> transformGeoRSSMultipolygon(T record, String petitionSrId) throws Exception 
	{	
		List<SyndEntry> listEntries = new ArrayList<SyndEntry>();
		
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		
		List<LinearRing> lineRingArray=new ArrayList<LinearRing>();	
		
		SyndEntry entry = new SyndEntryImpl();
	
		List <String> listaElementos = new ArrayList<String>();
		
		String beanId="";
		String beanIdentifier="";
		String theURI="";
		
		theURI = generateURI(record, theURI);
						       
		for (BeanUtil bean:listData)
		{			
			if (bean.getFieldName().equals("id"))
			{
				beanId=(String) bean.getValue();
			}
			
			if (bean.getFieldName().equals("identifier"))
			{
				beanIdentifier=(String) bean.getValue();
			}
			
			if (bean.getFieldName().equals("hasGeometry"))
			{
				JSONObject hasGeometry=(JSONObject)bean.getValue();
				if (hasGeometry!=null)
				{
					JSONObject geometry=(JSONObject) hasGeometry.get("geometry");
					if (geometry!=null)
					{
						JSONArray poligons=(JSONArray) geometry.get("coordinates");					
						for (int i=0;i<poligons.size();i++)
						{
							JSONArray actualPoligon = (JSONArray) poligons.get(i);
							log.info("poligono: "+i);					
							for (int j=0;j<actualPoligon.size();j++)
							{						
								JSONArray vertices=(JSONArray) actualPoligon.get(j);
								PositionList positionList = new PositionList();
								log.info("vertices: "+vertices.size());		
								for (int k=0;k<vertices.size();k++)
								{
									JSONArray punto=(JSONArray) vertices.get(k);							
									positionList.add((Double) punto.get(1), (Double)punto.get(0));
								}			
								lineRingArray.add(new LinearRing(positionList));
							}
						}
					}				
				}				
			}
			else
			{			
				addFields(entry, listaElementos, bean);
			}
			
		}
		
		if (lineRingArray.size()==0)
		{
			return null;
		}
		
		if (record instanceof Territorio)
		{
			if (!beanIdentifier.equals(""))
			{	
				entry.setLink(theURI+beanIdentifier);
			}
		}
		else
		{		
			entry.setLink(theURI+beanId);
		}
		
		entry.setAuthor(GEORSSParser.AUTHOR_FEED_NAME);		
		
		if (Util.validValue(entry.getTitle())==false)
		{
			entry.setTitle(beanId);
		}
		
		if (Util.validValue(entry.getDescription())==false)
		{	
			SyndContent description = new SyndContentImpl();
	        description.setType("text/plain");
	        CDATA valueElement = new CDATA((String)GEORSSParser.DESCRIPTION+beanId);
	        description.setValue(valueElement.getText());
			entry.setDescription(description);
		}
						
		for (LinearRing line:lineRingArray)
		{
			SyndEntry entryPolygon=null;
			try {
				entryPolygon = (SyndEntry) entry.clone();							
			} catch (CloneNotSupportedException e) {
				log.error("Clonable exception",e);
			}
			
			if (entryPolygon!=null)
			{
				Polygon poligon=null;
				poligon=new Polygon();
				poligon.setExterior(line);
				
				SimpleModuleImpl geoRSSModule = new SimpleModuleImpl();
			    geoRSSModule.setGeometry(poligon);		        
			    entryPolygon.getModules().add(geoRSSModule);
			     
			    generaContent(entry, listaElementos);
			    listEntries.add(entryPolygon);
			}
		}
		        
        
		return listEntries;
	}


	private void generaContent(SyndEntry entry, List<String> listaElementos) {
		String content="";
		for (String linea:listaElementos)
		{
			content+=linea+"</br>";
		}
		
		List<SyndContent> contentList= new ArrayList<SyndContent>();		
		SyndContent syndContent=new SyndContentImpl();
		syndContent.setValue(content);
		syndContent.setType("html");
		contentList.add(syndContent);
		entry.setContents(contentList);
	}


	private void addFields(SyndEntry entry, List<String> listaElementos, BeanUtil bean) throws Exception 
	{
		if (bean.getFieldName().equals("title")) {
			if (!"".equals(bean.getValue())) {
				entry.setTitle((String)bean.getValue());
			}
		}
		
		if (bean.getFieldName().equals("description")) {
			if (!"".equals(bean.getValue())) {
				SyndContent description = new SyndContentImpl();
		        description.setType("text/plain");
		        CDATA valueElement = new CDATA((String)bean.getValue());
		        description.setValue(valueElement.getText());
				entry.setDescription(description);
			}
		}
		
		if (ConstantsGEO.ignoreFields.contains(bean.getFieldName().toLowerCase())==false)
		{	
			addField(bean, listaElementos);
		}
		else 
		{
			log.debug(bean.getFieldName()+" ignored");
		}
				
	}
	
	private void addFieldOdata(BeanUtil bean, List <Element> listaElementos) throws Exception {
		
		Element element = new Element(bean.getFieldName(),NAMESPACE_ODATA);
				
		String stringValue = null;
		String type="";
		
				
		if (bean.getValue()==null)
		{
			return;
		}
		if (bean.getTypeName().equals("java.lang.Class"))
		{
			stringValue="";
		}
		else if (bean.getTypeName().equals("java.lang.String"))
		{				
			String value=(String) bean.getValue();
			stringValue=value.toString();
			type="Edm.String";			
		}
		else if (bean.getTypeName().equals("int"))
		{	
			stringValue=bean.getValue()+"";
			type="Edm.Int16";			
		}
		else if (bean.getTypeName().equals("java.util.Date"))
		{
			Date value=(Date) bean.getValue();
			stringValue=value.toString();
			type="Edm.Date";			
		}
		else if (bean.getTypeName().equals("java.lang.Boolean"))
		{
			Boolean value=(Boolean) bean.getValue();
			stringValue=value.toString();
			type="Edm.Boolean";			
		}
		else if (bean.getTypeName().equals("java.lang.Double"))
		{
			Double value=(Double) bean.getValue();
			stringValue=value.toString();
			type="Edm.Double";			
		}
		else if (bean.getTypeName().equals("java.math.BigDecimal"))
		{
			BigDecimal value=(BigDecimal) bean.getValue();
			stringValue=value.toString();
			type="Edm.Decimal";			
		}	
		else if (bean.getTypeName().equals("java.lang.Integer"))
		{
			Integer value=(Integer) bean.getValue();
			stringValue=value.toString();
			type="Edm.Int16";			
		}
		else if (bean.getTypeName().equals("java.util.List"))
		{
			List valueList=(List) bean.getValue();
			stringValue="";
			for (Object obj:valueList)
			{
				stringValue+=obj.toString()+",";	
			}
			stringValue=StringUtils.chop(stringValue);
			type="Edm.String";			
		}
		else if (tratamientoId.contains(bean.getTypeName()))
		{
			RDFModel model=(RDFModel) bean.getValue();
			stringValue=model.getId();
			type="Edm.String";
		}		
		else if (bean.getTypeName().equals("java.lang.Object")&&(bean.getFieldName().equals("hasGeometry")))
		{
			stringValue=(String) bean.getValue().toString();
			type="Edm.String";
		}
		else
		{
			log.error("tipo sin controlar en odata: "+bean.getTypeName());
			throw new Exception("tipo sin controlar en odata: "+bean.getTypeName());
		}
		
		if (Util.validValue(stringValue))
		{
			
			element.addContent(stringValue);
			element.setAttribute(new Attribute("type",type,NAMESPACE_METADATA_ODATA));
			listaElementos.add(element);
		}
	}
    
	private void addField(BeanUtil bean, List <String> listaElementos) throws Exception 
	{	
		String stringValue = null;
		if(bean.getFieldName().endsWith("Object"))
		{
			return;
		}
		if (bean.getValue()==null)
		{
			return;
		}
		if (bean.getTypeName().equals("java.lang.Class"))
		{
			stringValue="";
		}
		else if (bean.getTypeName().equals("java.lang.String"))
		{				
			String value=(String) bean.getValue();
			stringValue=value.toString();
		}
		else if (bean.getTypeName().equals("java.util.Date"))
		{
			Date value=(Date) bean.getValue();
			stringValue=value.toString();				
		}
		else if (bean.getTypeName().equals("java.lang.Boolean"))
		{
			Boolean value=(Boolean) bean.getValue();
			stringValue=value.toString();						
		}
		else if (bean.getTypeName().equals("java.lang.Double"))
		{
			Double value=(Double) bean.getValue();
			stringValue=value.toString();			
		}
		else if (bean.getTypeName().equals("java.math.BigDecimal"))
		{
			BigDecimal value=(BigDecimal) bean.getValue();
			stringValue=value.toString();			
		}	
		else if (bean.getTypeName().equals("java.lang.Integer"))
		{
			Integer value=(Integer) bean.getValue();
			stringValue=value.toString();			
		}
		else if (bean.getTypeName().equals("java.util.List"))
		{
			List valueList=(List) bean.getValue();
			stringValue="";
			for (Object obj:valueList)
			{
				stringValue+=obj.toString()+",";	
			}
			stringValue=StringUtils.chop(stringValue);						
		}
		else if (tratamientoId.contains(bean.getTypeName()))
		{
			RDFModel model=(RDFModel) bean.getValue();
			stringValue=model.getId();	
		}	
		else
		{
			log.error("tipo sin controlar en georss: "+bean.getTypeName());
			throw new Exception("tipo sin controlar en georss: "+bean.getTypeName());			
		}
		
		listaElementos.add("<b>"+bean.getFieldName()+"</b>: "+stringValue);	
		
		
	}
	
    private Charset getCharset(HttpHeaders headers) {
		if (headers == null || headers.getContentType() == null || headers.getContentType().getCharSet() == null) {
			return DEFAULT_CHARSET;
		}
		return headers.getContentType().getCharSet();
	}
    
    @SuppressWarnings("unchecked")
    private Class<T> toBeanType (Type type) {
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

}