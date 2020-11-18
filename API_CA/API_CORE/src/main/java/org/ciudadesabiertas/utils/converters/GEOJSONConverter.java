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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesabiertas.model.IGeoModelGeometry;
import org.ciudadesabiertas.model.IGeoModelXY;
import org.ciudadesabiertas.utils.BeanUtil;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Territorio;
import org.ciudadesabiertas.utils.Util;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonFeatureMultiline;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonFeatureMultipolygon;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonFeaturePoint;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonMultiline;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonMultipolygon;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonPoint;
import org.ciudadesabiertas.utils.converters.geojson.GeoJsonResult;
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



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 * @param <L>
 */
@Component
public class GEOJSONConverter <T, L extends Result<T>> extends AbstractHttpMessageConverter<L> {

    
    private static final String MULTI_POLYGON = "MultiPolygon";

	private static final String MULTI_LINE = "MultiLine";

	private static final String GEOJSON_MIME = Constants.mimeGEOJSON;
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    

	private static final Logger log = LoggerFactory.getLogger(GEOJSONConverter.class);

    public GEOJSONConverter() {
        super(MediaType.valueOf(GEOJSON_MIME));       
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
        
    	
		OutputStreamWriter outputStream = new OutputStreamWriter(outputMessage.getBody(),Charset.forName("UTF8"));       
		
 
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
			
			List newRecords=null;
			GeoJsonResult newResult=null;
			String typeInGeometry=null;
			
					
			String petitionSrId = Util.extractSrIdFromURL(l.getSelf());
			
			
			for (T record:records)
			{	
				if (record instanceof Territorio)
				{
					if (newRecords==null)
					{
						newRecords=new ArrayList<GeoJsonFeatureMultipolygon>();
						newResult=new GeoJsonResult<GeoJsonFeatureMultipolygon>();	
					}
					GeoJsonFeatureMultipolygon newRecord = transformToGeoJsonMultiPolygon(record,petitionSrId);				
					newRecords.add(newRecord);
				}
				else if (record instanceof IGeoModelXY)
				{
					if (newRecords==null)
					{
						newRecords=new ArrayList<GeoJsonFeaturePoint>();
						newResult=new GeoJsonResult<GeoJsonFeaturePoint>();	
					}
					GeoJsonFeaturePoint newRecord = transformToGeoJsonPoint(record,petitionSrId);	
					if (newRecord!=null)
					{
						newRecords.add(newRecord);
					}
				}
				else if (record instanceof IGeoModelGeometry)
				{   
				  Object hasGeometry = ((IGeoModelGeometry) record).getHasGeometry();
				  if (hasGeometry != null) {
					String geometry=hasGeometry.toString();
					if (newRecords == null) {
					  if (geometry.contains(MULTI_LINE)) {
						typeInGeometry = MULTI_LINE;
					  } else {
						typeInGeometry = MULTI_POLYGON;
					  }

					  if (typeInGeometry.equals(MULTI_LINE)) {
						newRecords = new ArrayList<GeoJsonFeatureMultiline>();
						newResult = new GeoJsonResult<GeoJsonFeatureMultiline>();
					  } else {
						newRecords = new ArrayList<GeoJsonFeatureMultipolygon>();
						newResult = new GeoJsonResult<GeoJsonFeatureMultipolygon>();
					  }

					}

					if ((!geometry.equals(""))&&(!geometry.equals("{}")))
					{
    					if (typeInGeometry.equals(MULTI_LINE)) {
    					  GeoJsonFeatureMultiline newRecord = transformToGeoJsonMultiline(record, petitionSrId);
    					  if (newRecord != null) {
    						newRecords.add(newRecord);
    					  }
    					} else {
    					  GeoJsonFeatureMultipolygon newRecord = transformToGeoJsonMultiPolygon(record, petitionSrId);
    					  if (newRecord != null) {
    						newRecords.add(newRecord);
    					  }
    					}
					}

				  }
				}
			}
			
			if (newRecords == null)
			{
			  newRecords = new ArrayList();			  
			}
			if (newResult == null)
			{
				newResult = new GeoJsonResult();			  
			}
					
			newResult.setFeatures(newRecords);	
			objectMapper.writeValue(outputStream, newResult);	
					
    		respuesta.append(outputStream);
    			
    		try
    		{	   
    			OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);    			
    			//No es necesario añadir la respuesta
    			//writer.append(respuesta);    			
    			writer.close();
    		}
    		catch (Exception e)
    		{
    			log.error("Error generating RDF",e);
    			throw e;
    		}
    			
        } catch (ClassCastException cce) {
            throw cce;
    	
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
    }


	



	
	private GeoJsonFeatureMultiline transformToGeoJsonMultiline(T record, String petitionSrId) throws Exception {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		GeoJsonFeatureMultiline newRecord=new GeoJsonFeatureMultiline();
		
		JSONObject geometry=null;
		
		for (BeanUtil bean:listData)
		{
			if (bean.getFieldName().equals("hasGeometry")&& (bean.getValue()!=null))
			{										
				geometry=(JSONObject) bean.getValue();				
			}
			if ((ConstantsGEO.ignoreFields.contains(bean.getFieldName().toLowerCase())==false)&&(bean.getFieldName().toLowerCase().endsWith("object")==false))
			{				
				Map<String, Object> properties = newRecord.getProperties();
				addField(bean, properties);
			}
			else 
			{
				log.debug(bean.getFieldName()+" ignored");
			}
		}
		
		if (geometry!=null)
		{			
			newRecord.setGeometry((JSONObject) geometry.get("geometry"));			
		}
		
		return newRecord;
	}


	private GeoJsonFeatureMultipolygon transformToGeoJsonMultiPolygon(T record, String petitionSrId) throws Exception {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		GeoJsonFeatureMultipolygon newRecord=new GeoJsonFeatureMultipolygon();
		
		JSONObject geometry=null;
		
		for (BeanUtil bean:listData)
		{
			if (bean.getFieldName().equals("hasGeometry")&& (bean.getValue()!=null))
			{										
				geometry=(JSONObject) bean.getValue();				
			}
			if ((ConstantsGEO.ignoreFields.contains(bean.getFieldName().toLowerCase())==false)&&(bean.getFieldName().toLowerCase().endsWith("object")==false))
			{				
				Map<String, Object> properties = newRecord.getProperties();
				addField(bean, properties);
			}
			else 
			{
				log.debug(bean.getFieldName()+" ignored");
			}
		}
		
		if (geometry!=null)
		{			
			newRecord.setGeometry((JSONObject) geometry.get("geometry"));			
		}
		
		return newRecord;
	}

	
	private GeoJsonFeaturePoint transformToGeoJsonPoint(T record, String petitionSrId) throws Exception {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		GeoJsonFeaturePoint newRecord=new GeoJsonFeaturePoint();
		
		
		BigDecimal lat=null;
		BigDecimal lon=null;		
		for (BeanUtil bean:listData)
		{		
			if (Constants.SUPPORTED_XY_SRIDS.contains(petitionSrId))
			{
				if (bean.getFieldName().equals("x")&& (bean.getValue()!=null))
				{
					lat=(BigDecimal) bean.getValue();
				}

				else if (bean.getFieldName().equals("y")&& (bean.getValue()!=null))
				{
					lon=(BigDecimal) bean.getValue();
				}
									
			}else {
				if (bean.getFieldName().equals("latitud")&& (bean.getValue()!=null))
				{
					lat=(BigDecimal) bean.getValue();
				}
				else if (bean.getFieldName().equals("longitud")&& (bean.getValue()!=null))
				{
					lon=(BigDecimal) bean.getValue();
				}
			}
								
			if ((ConstantsGEO.ignoreFields.contains(bean.getFieldName().toLowerCase())==false)&&(bean.getFieldName().toLowerCase().endsWith("object")==false))
			{				
				Map<String, Object> properties = newRecord.getProperties();
				addField(bean, properties);
			}
			else 
			{
				log.debug(bean.getFieldName()+" ignored");
			}
			
		}
		
		if ((lat!=null)&&(lon!=null))
		{
			GeoJsonPoint point=new GeoJsonPoint();
			List<BigDecimal> coordinates=new ArrayList<BigDecimal>();
			coordinates.add(lon);
			coordinates.add(lat);
			point.setCoordinates(coordinates);
			
			newRecord.setGeometry(point);
		}else {
			return null;
		}
		
		return newRecord;
	}

	private void addField(BeanUtil bean, Map<String, Object> properties) throws Exception {
		
		String stringValue = null;
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
		else
		{
			log.error("tipo sin controlar en geojson: "+bean.getTypeName());
			throw new Exception("tipo sin controlar en geojson: "+bean.getTypeName());			
		}
		
		properties.put(bean.getFieldName(),stringValue);
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