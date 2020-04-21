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

import io.swagger.annotations.ApiParam;





/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class GroupBySearch {
	
	@ApiParam(required = true, name = "fields", value = SwaggerConstants.PARAM_FIELDS_GROUP_BY)
	private String fields;
	@ApiParam(value=SwaggerConstants.PARAM_WHERE)
	private String where;
	@ApiParam(value=SwaggerConstants.PARAM_SORT)
	private String sort;
	@ApiParam(required = true, name = "group", value = SwaggerConstants.PARAM_GROUP)
	private String group;
	@ApiParam(value=SwaggerConstants.PARAM_HAVING)
	private String having;

	
	public static final String SEPARATOR= ","; 
	public static final String ASC="+";
	public static final String DESC="-";
	
	public GroupBySearch()
	{
		super();
		this.fields = "";
		this.where = "";
		this.sort = "";
		this.group = "";
		this.having = "";
	}
	

	public GroupBySearch(GroupBySearch g)
	{
		super();
		this.fields = g.fields;
		this.where = g.where;
		this.sort = g.sort;
		this.group = g.group;
		this.having = g.having;
	}

	public String getFields() {
		return fields;
	}
	
	public void setFields(String fields) {
		this.fields = fields;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getHaving() {
		return having;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public List<String> extractField(){
		 List<String> result = new ArrayList<String>();
		if (this.fields!=null && !"".equals(this.fields)) {
			String[] listado= fields.split(SEPARATOR);
			for (String obj: listado) {
				result.add(obj);
			}
		}
			
		return result;
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
		boolean error = false;
		if (fields!=null && !"".equals(fields)) {
			result = "Select " + fields + " from " + tabla + " ";						
		}else {
			result = "[ERROR]";
			error=true;
		}
		if (!error) {
			if (where!=null && !"".equals(where)) {
				result+= " where " +  where + " ";
			}	
		}
		if (!error) {
			if (group!=null && !"".equals(group)) {
				result+= " group by " + group + " ";
			}	
		}
		if (!error) {
			if (having!=null && !"".equals(having)) {
				result+= " having " + having + " ";
			}	
		}
		if (!error) {
			if (sort!=null && !"".equals(sort)) {
				
				List<Sort> listadoOrden = extractOrder();
				if (!listadoOrden.isEmpty()) {
					result+= " Order by ";
					int count =1;
					for (Sort order: listadoOrden) {
						result += order.getProperty() + " " + (order.isDesc() ? " DESC " : " ASC ");
						if (count<listadoOrden.size()) {
							result += ", ";
						}
					}
				}
			}		
		}
		
		
		return result;
		
	}


	@Override
	public String toString()
	{
		return "GroupBySearch [fields=" + fields + ", where=" + where + ", sort=" + sort + ", group=" + group + ", having=" + having + "]";
	}
	
	
	public void checkCoordinatesETRS()
	{
		if (this.getFields().contains(Constants.XETRS89))
		{
			String fields=this.getFields();
			fields=fields.replace(Constants.XETRS89, Constants.X.toLowerCase());
			this.setFields(fields);
		}
		if (this.getFields().contains(Constants.YETRS89))
		{
			String fields=this.getFields();
			fields=fields.replace(Constants.YETRS89, Constants.Y.toLowerCase());
			this.setFields(fields);
		}	
		
		if (this.getGroup().contains(Constants.XETRS89))
		{
			String g=this.getFields();
			g=g.replace(Constants.XETRS89, Constants.X.toLowerCase());
			this.setGroup(g);
		}
		if (this.getGroup().contains(Constants.YETRS89))
		{
			String g=this.getFields();
			g=g.replace(Constants.YETRS89, Constants.Y.toLowerCase());
			this.setGroup(g);
		}	
		if (this.getHaving().contains(Constants.XETRS89))
		{
			String h=this.getHaving();
			h=h.replace(Constants.XETRS89, Constants.X.toLowerCase());
			this.setHaving(h);
		}
		if (this.getHaving().contains(Constants.YETRS89))
		{
			String h=this.getHaving();
			h=h.replace(Constants.YETRS89, Constants.Y.toLowerCase());
			this.setHaving(h);
		}
		if (this.getSort().contains(Constants.XETRS89))
		{
			String s=this.getSort();
			s=s.replace(Constants.XETRS89, Constants.X.toLowerCase());
			this.setSort(s);
		}
		if (this.getSort().contains(Constants.YETRS89))
		{
			String s=this.getSort();
			s=s.replace(Constants.YETRS89, Constants.Y.toLowerCase());
			this.setSort(s);
		}
		if (this.getWhere().contains(Constants.XETRS89))
		{
			String w=this.getWhere();
			w=w.replace(Constants.XETRS89, Constants.X.toLowerCase());
			this.setWhere(w);
		}
		if (this.getWhere().contains(Constants.YETRS89))
		{
			String w=this.getWhere();
			w=w.replace(Constants.YETRS89, Constants.Y.toLowerCase());
			this.setWhere(w);
		}
	}
	
	

}
