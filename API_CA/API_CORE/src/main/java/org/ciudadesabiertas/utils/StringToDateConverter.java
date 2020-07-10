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


import org.springframework.core.convert.converter.Converter;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source)
	{		
		Date theDate=null;
		
		if (source.length()==19)
		{
			theDate=Util.getFecha(source, Constants.DATE_TIME_FORMAT);
			if (theDate==null)
			{
				theDate=Util.getFecha(source, Constants.DATE_TIME_FORMAT_B);
			}
		}
		
		if ((theDate==null)&&(source.length()==10))
		{
			if (Util.DATE_PATTERN.matcher(source).matches())
			{
				theDate=Util.getFecha(source, Constants.DATE_FORMAT);
			}
			else if (Util.DATE_PATTERN_B.matcher(source).matches())
			{
				theDate=Util.getFecha(source, Constants.DATE_FORMAT_B);
			}
			
		}
		
		return theDate;
	}
	
	

}


