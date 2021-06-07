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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
public class FieldsObjectMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

	private String fields;
	
	public FieldsObjectMappingStrategy(String fields) {
		super();
		this.fields = fields;
	}


	@Override
	public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {

		//Este array es para tener en cuenta los campos que pueden o no existir (x e y)
		//Siempre en último lugar
				
		List<String> fieldList = Arrays.asList(fields.split(","));

		if (fieldList.size()<=0) {
			return super.generateHeader(bean);
		}
		
		super.setColumnMapping(new String[fieldList.size()]);	
		
		return fieldList.toArray(new String[0]);
	}
	
	@Override
	public String[] transmuteBean(T bean) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		List<String> transmutedBean = new ArrayList<String>();

		transmutedBean.add(bean.toString());

		return transmutedBean.toArray(new String[0]);
	}

    
	
    
    

	
	

	
    
	
}