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
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
public interface DatasetSearch<T>  {	
	

	static final Logger log = LoggerFactory.getLogger(DatasetSearch.class);

	static List<String> numbers=new ArrayList<String>(); 
	
	
	
	default List<Criterion> obtenerCriterios (String driver, String key) {
		
		List<Criterion> condiciones = new ArrayList<Criterion>();
		
		List<BeanUtil> listData = Util.obtenerBeanUtil(this);
		
		if (numbers.size()==0)
		{
			numbers.add("int");
			numbers.add("double");
			numbers.add("long");
			numbers.add("float");	
		}
		
		
		if (listData!=null && !listData.isEmpty()) {
			
			for (BeanUtil obj:listData) {
				
				if (Util.validValue(obj.getValue())) {		
					if (Constants.TYPE_STRING_CLASS.equals(obj.getTypeName())) {
						String valor = (String)obj.getValue();
						//Control para url
						if (obj.getFieldName().contains(Constants.FIELD_URL)) {
							if (Util.isUrlParam(valor)) {
								valor = Util.decodeURL(valor);
							}
						}
						Criterion c1 = new LikeNoAccents(obj.getFieldName(), valor.replace("*", "%"),Util.getDatabaseTypeFromDriver(driver), key);
						condiciones.add(c1);
					}else if (Constants.TYPE_CLASS_CLASS.equals(obj.getTypeName())) {
						//No hacemos nada
						log.debug("propertyName: "+obj.getFieldName()+ " / typeName: "+obj.getTypeName() + " / valor: "+obj.getValue());					
					}else if (numbers.contains(obj.getTypeName())) {
						//Si es un numero y es distinto de -1 es cuando contemplamos la condicion
						if (!obj.getValue().toString().contentEquals(Constants.defaultNumberValue+""))
						{
							Criterion c1 = Restrictions.eq(obj.getFieldName(),obj.getValue());
							condiciones.add(c1);
						}						
					}else {						
						Criterion c1 = Restrictions.eq(obj.getFieldName(),obj.getValue());
						condiciones.add(c1);
					}
				}
				
			}
			
		}
		
		return condiciones;
	}
	
	default String obtenerCondicionGeografica (String xColumn, String yColumn,long meters) {
		
		String formula="";
		
		List<BeanUtil> listData = Util.obtenerBeanUtil(this);
		
		String objX="";
		String objY="";
		
		if (listData!=null && !listData.isEmpty()) {
			
			for (BeanUtil obj:listData) {
				
				if (Util.validValue(obj.getValue())) {	
					
					if (obj.getFieldName().toLowerCase().contains(Constants.X.toLowerCase())) {
						objX=obj.getValue().toString();
					}
					if (obj.getFieldName().toLowerCase().contains(Constants.Y.toLowerCase())) {
						objY=obj.getValue().toString();
					}
					
					
					if (Util.validValue(objX)&&(Util.validValue(objY)))
					{
						//"SQRT((X-" + coords[1] + ")*(X-" + coords[1] + ")+(Y-" + coords[2] + ")*(Y-" + coords[2] + "))"					
						formula="SQRT(("+xColumn+"-" + objX + ")*("+xColumn+"-" + objX + ")+("+yColumn+"-" + objY + ")*("+yColumn+"-" + objY + "))<="+meters;					
						
						break;
					}
						
				}
				
			}
			
			
			
		}
		
		return formula;
	}

	
	default String toUrlParam()
	{
		StringBuffer urlParam=new StringBuffer();
		
		List<BeanUtil> listData = Util.obtenerBeanUtil(this);
		
		if (listData!=null && !listData.isEmpty()) {
			
			for (BeanUtil obj:listData) {
				
				if (Util.validValue(obj.getValue())) {		
					
					if  (!Constants.TYPE_CLASS_CLASS.equals(obj.getTypeName())) {
						if (Constants.TYPE_DATE_CLASS.equals(obj.getTypeName())) {
							urlParam.append("&"+obj.getFieldName()+"="+Util.formatearFechas((Date)obj.getValue()));
						}else {
							urlParam.append("&"+obj.getFieldName()+"="+obj.getValue());
						}
					}
				}
				
			}
		}
		
				
		return urlParam.toString();
	}




	


	
	
	
	
	
}
