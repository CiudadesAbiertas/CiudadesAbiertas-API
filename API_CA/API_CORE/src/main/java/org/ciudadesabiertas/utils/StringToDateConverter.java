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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class StringToDateConverter implements Converter<String, Date> {


	private static final Logger log = LoggerFactory.getLogger(StringToDateConverter.class);


	@Override
	public Date convert(String source)
	{		
	  	log.debug("transforming string to date/time: "+source);
	  
		Date theDate=null;
		
//		//TIME
//		if (source.length()==8)
//		{
//			theDate=Util.getFecha(source, Constants.TIME_FORMAT);
//		}
//		else if (source.length()==5)
//		{
//			theDate=Util.getFecha(source, Constants.TIME_FORMAT_SHORT);
//		}
		
		//FECHAS
		if (source.length()==19)
		{
			theDate=Util.getFecha(source, Constants.DATE_TIME_FORMAT);
			if (theDate==null)
			{
				theDate=Util.getFecha(source, Constants.DATE_TIME_FORMAT_B);
			}
		}
		
		else if ((theDate==null)&&(source.length()==10))
		{
			if (Util.DATE_PATTERN.matcher(source).matches())
			{
				theDate=Util.getFecha(source, Constants.DATE_FORMAT);
			}
			else if (Util.DATE_PATTERN_B.matcher(source).matches())
			{
				theDate=Util.getFecha(source, Constants.DATE_FORMAT_B);
			}			
		}else {
		  log.error("Wrong Size, returning actual date");
		  //theDate=new Date();
		}
		
		return theDate;
	}
	
	

}


