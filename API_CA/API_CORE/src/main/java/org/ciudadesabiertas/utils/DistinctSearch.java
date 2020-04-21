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
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class DistinctSearch {
	
	@ApiParam(required = true, name = "field", value = SwaggerConstants.PARAM_FIELD )
	private String field;	
	
	//@ApiModelProperty(value = SwaggerConstants.PARAM_CONDICION_ADICCIONAL)
	@ApiModelProperty(hidden = true)
	private String condicionAdicional;
	
	public DistinctSearch()
	{
		super();
		this.field = "";		
	}
	

	public DistinctSearch(DistinctSearch g)
	{
		super();
		this.field = g.field;		
	}

	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	
	public String getCondicionAdicional()
	{
		return condicionAdicional;
	}


	public void setCondicionAdicional(String condicionAdicional)
	{
		this.condicionAdicional = condicionAdicional;
	}


	public String createQuery(String table) {
		String query = "";			
		if (Util.validValue(table)) {
			query = "Select distinct " + field + " from " + table + " where "+field + " is not null";						
		}
		if (Util.validValue(condicionAdicional))
		{
			query+=" and "+condicionAdicional;
		}
		return query;		
	}

	public String createRowCountQuery(String table)
	{
		String query="";
		if (Util.validValue(table)) {			
			query = "select count(distinct " + field + ") from " + table+ " where "+ field + " is not null";
		}
		if (Util.validValue(condicionAdicional))
		{
			query+=" and "+condicionAdicional;
		}

		
		
		return query;
	}

	@Override
	public String toString()
	{
		return "DistinctSearch [field=" + field + "]";
	}



	
	
	

}
