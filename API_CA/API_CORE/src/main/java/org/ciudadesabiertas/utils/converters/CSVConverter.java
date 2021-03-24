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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesabiertas.utils.ObjectResult;
import org.ciudadesabiertas.utils.Result;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 * @param <L>
 */
@Component
public class CSVConverter <T, L extends Result<T>> extends AbstractHttpMessageConverter<L> {

    
    private static final String TEXT_CSV = "text/csv";
    



    public CSVConverter() {
        super(MediaType.valueOf(TEXT_CSV));       
        
        
    }
  

    @Override
    protected boolean supports (Class<?> clazz) {
        return Result.class.isAssignableFrom(clazz);
    }
    
    @Override
    protected L readInternal (Class<? extends L> clazz, HttpInputMessage inputMessage)
              throws IOException, HttpMessageNotReadableException {
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        Class<T> t = toBeanType(clazz.getGenericSuperclass());
        strategy.setType(t);        
        CSVReader csv = new CSVReader(new InputStreamReader(inputMessage.getBody()));
        CsvToBean<T> csvToBean = new CsvToBean<>();
        List<T> beanList = csvToBean.parse(strategy, csv);
        try {
            L l = clazz.newInstance();
            l.setRecords(beanList);
            return l;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void writeInternal (L l, HttpOutputMessage outputMessage)
              throws IOException, HttpMessageNotWritableException {
        
    	
    	ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<T>();    	
        if (l instanceof ObjectResult)
        {        		
        	strategy = new ObjectMappingStrategy<T>();
        	strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));
        } else {
        	strategy = new CustomerMappingStrategy<T>();
        	strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));	
        }
    	   
        
		byte[] bomBytes = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		
		OutputStreamWriter outputStream = new OutputStreamWriter(outputMessage.getBody(),Charset.forName("UTF8"));       
		outputStream.write(new String(bomBytes));      
		
        StatefulBeanToCsv<T> beanToCsv = beanToCSVGenerator(strategy, outputStream);
        try {
          
          	List records = l.getRecords();
      		Iterator recordsIterator = records.iterator();
      		List recordsTranslated = new ArrayList();
      		boolean first=true;
      		while (recordsIterator.hasNext())
      		{ 	
      		  Object next = recordsIterator.next();
      		  //Si entra en este if es que viene de una select que no se mapea contra un modelo (GroupBy por ejemplo)
      		  if (next instanceof LinkedHashMap) {			  
    			  strategy = new DynamicMappingStrategy<T>();
    		      strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));
    		      
    		      beanToCsv = beanToCSVGenerator(strategy, outputStream);
      		  }else if (next instanceof String){
      			strategy = new DynamicMappingStrategy<T>();
      			strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));
  		      
      			beanToCsv = beanToCSVGenerator(strategy, outputStream);
      		  }
			  recordsTranslated.add(next);
			}
      		beanToCsv.write(recordsTranslated);
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


	private StatefulBeanToCsv<T> beanToCSVGenerator(ColumnPositionMappingStrategy<T> strategy, OutputStreamWriter outputStream) {
	  StatefulBeanToCsv<T> beanToCsv =
	            new StatefulBeanToCsvBuilder(outputStream)
	            			/*.withSeparator(';')*/
	                      .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                      .withMappingStrategy(strategy)
	                      .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
	                      .withLineEnd(CSVWriter.RFC4180_LINE_END)                            
	                      .build();
	  return beanToCsv;
	}
    
    @SuppressWarnings("unchecked")
    private Class<T> toBeanType (Type type) {
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

}