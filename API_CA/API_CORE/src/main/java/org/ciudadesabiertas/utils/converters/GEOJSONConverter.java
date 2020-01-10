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
import java.util.List;
import java.util.Map;

import org.ciudadesabiertas.utils.BeanUtil;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.GeoJsonFeaturePoint;
import org.ciudadesabiertas.utils.GeoJsonPoint;
import org.ciudadesabiertas.utils.GeoJsonResult;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
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
			
			List<GeoJsonFeaturePoint> newRecords=new ArrayList<GeoJsonFeaturePoint>();
			
			for (T record:records)
			{	
				GeoJsonFeaturePoint newRecord = transformToGeoJson(record);				
				newRecords.add(newRecord);
			}
			
			GeoJsonResult<GeoJsonFeaturePoint> newResult=new GeoJsonResult<GeoJsonFeaturePoint>();			
			newResult.setFeatures(newRecords);			
			objectMapper.writeValue(outputStream, newResult);			
    		respuesta.append(outputStream);
    			
    		try
    		{	   
    			OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);    			
    			writer.append(respuesta);
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


	private GeoJsonFeaturePoint transformToGeoJson(T record) {
		List<BeanUtil> listData = Util.obtenerBeanUtil(record);
		GeoJsonFeaturePoint newRecord=new GeoJsonFeaturePoint();
		
		BigDecimal lat=null;
		BigDecimal lon=null;		
		for (BeanUtil bean:listData)
		{		
			if (bean.getTypeName().equals("java.lang.String"))
			{				
				Map<String, Object> properties = newRecord.getProperties();
				properties.put(bean.getFieldName(), bean.getValue());
			}
			if (bean.getFieldName().equals("latitud"))
			{
				lat=(BigDecimal) bean.getValue();
			}
			if (bean.getFieldName().equals("longitud"))
			{
				lon=(BigDecimal) bean.getValue();
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
		}
		
		return newRecord;
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