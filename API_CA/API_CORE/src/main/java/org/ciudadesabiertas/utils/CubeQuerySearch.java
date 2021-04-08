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

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;





/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class CubeQuerySearch {
	
	@ApiParam(required = true, name = "dimension", value="Dimensión/es de la consulta. Ejemplo: refPeriod")
	private List<String> dimension;
	@ApiParam(required = true, name = "measure", value="Medida/s de la consulta. Ejemplo: numeroPersonas")
	private String measure;
	@ApiParam(required = true, name = "group", allowableValues=SwaggerConstants.AGRUPADAS_OPER_PERMITIDAS, value="Operación de agrupación de la consulta")
	private String group;
	@ApiModelProperty(value = "Filtro de la consulta. Ejemplo: refPeriod=2016")
	private String where;
	@ApiModelProperty(value = "Orden de la consulta. Ejemplo: refPeriod")
	private String sort;
	
	
	public static final String SEPARATOR= ","; 
	public static final String ASC="+";
	public static final String DESC="-";
	
	public CubeQuerySearch()
	{
		super();
		this.dimension = new ArrayList<String>();
		this.where = "";
		this.measure = "";
		this.group = "";	
		this.sort = "";
	}
	

	public CubeQuerySearch(CubeQuerySearch copy)
	{
		super();
		this.dimension = copy.dimension;
		this.where = copy.where;
		this.measure = copy.measure;
		this.group = copy.group;	
		this.sort = copy.sort;
	}

	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
	
	
	public List<String> getDimension() {
		return dimension;
	}


	public void setDimension(List<String> dimension) {
		this.dimension = new ArrayList<String>(); 
		this.dimension.addAll(dimension);
	}


	public String getWhere() {
		return where;
	}


	public void setWhere(String where) {
		this.where = where;
	}


	public String getMeasure() {
		return measure;
	}


	public void setMeasure(String measure) {
		this.measure = measure;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	public List<Sort> extractOrder(){
		 List<Sort> result = new ArrayList<Sort>();
		if (this.sort!=null && !"".equals(this.sort)) {
			String[] listado= sort.split(SEPARATOR);
			for (String obj: listado) {
				if (obj.startsWith(DESC)) {
					result.add( new Sort (obj.substring(1, obj.length()),true) );
						
				}else if (obj.startsWith(ASC)) {
					result.add( new Sort (obj.substring(1, obj.length()),false) );					
				}
				else {
					result.add( new Sort (obj,false) );
				}
			}
		}
			
		return result;
	}
	
	public String createQuery(String tabla) {
		String result = null;
		if ( (Util.validValue(dimension)==false)||
				//(Util.validValue(where)==false)||
				(Util.validValue(measure)==false)||
				(Util.validValue(group)==false) )
		{
			result = "[ERROR]";
		}else {
			String whereClause="";
			String dimensionClause="";
			String groupByClause="";
			
			if (dimension.size()==1)
			{
				dimensionClause=dimension.get(0);
				groupByClause=dimension.get(0);
				if (dimensionClause.toLowerCase().contains("as"))
			  	{
				  groupByClause=dimensionClause.substring(0, dimensionClause.toLowerCase().indexOf("as")).trim();
			  	}
			}
			else
			{
				for (String dim:dimension)
				{
				  	String groupByDim=dim;
				  	if (dim.toLowerCase().contains("as"))
				  	{
				  	  groupByDim=dim.substring(0, dim.toLowerCase().indexOf("as")).trim();
				  	}
					dimensionClause+=dim+",";
					groupByClause+=groupByDim+",";
				}
				dimensionClause=StringUtils.chop(dimensionClause);
				groupByClause=StringUtils.chop(groupByClause);
			}
			if (Util.validValue(where))
			{
				whereClause="where "+where+" ";
			}
			
			result="select "+dimensionClause+" , "+group+" ("+measure+")  as "+group+" from "+tabla+" "+whereClause+" group by  "+groupByClause ;
			
			if (Util.validValue(sort))
			{
				if (sort.startsWith(Constants.SORT_DESC)) {				
					result+=" order by "+sort.substring(1)+" desc";
				//Spring deja el + como ' '		
				} else if (sort.startsWith(Constants.SORT_ASC)||sort.startsWith(" ")) {
					result+=" order by "+sort.substring(1)+" asc";
				} else {
					result+=" order by "+sort+" desc";
				}
			}	
			
			
		}
		return result;
	}


	
	
	
	@Override
	public String toString() {
		return "CubeQuerySearch [dimensionX=" + dimension + ", where=" + where + ", measure=" + measure
				+ ", group=" + group + ", sort=" + sort + "]";
	}


	
	
	

}
