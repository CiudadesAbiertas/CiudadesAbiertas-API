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

import org.apache.commons.lang3.StringUtils;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class PageInfo
{
	private String requestURL;
	private int pageSize;
	private long page=-1;
	private String fields;
	private String q;
	private String sort;
	private String extraParams;
	
	public PageInfo()
	{
		super();
		pageSize=StartVariables.defaultPageSize;
		page=Constants.defaultPage;
	}
	
	
	
	public PageInfo(PageInfo pageInfo)
	{
		super();
		this.requestURL = pageInfo.requestURL;
		this.pageSize = pageInfo.pageSize;
		this.page = pageInfo.page;
		this.fields = pageInfo.fields;
		this.q = pageInfo.q;
		this.sort = pageInfo.sort;
		this.extraParams = pageInfo.extraParams;
	}



	@Override
	public String toString()
	{
		String URL=requestURL+"?";
		
		boolean firstParam=true;
		
		if (Util.validValue(q))
		{
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+="q=" + q;
		}
		if (pageSize>0)
		{	
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+="pageSize=" + pageSize;
		}
		if (page>=0)
		{
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+="page=" + page;
		}
		if (Util.validValue(fields))
		{
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+="fields=" + fields;
		}		
		if (Util.validValue(sort))
		{
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+="sort=" + sort;
		}
		if (Util.validValue(extraParams))
		{
			if (firstParam)
			{
				firstParam=false;				
			}else {
				URL+="&";
			}
			URL+=extraParams;
		}
		
		if (firstParam)
		{
			URL=StringUtils.chop(URL);
		}
		
		return URL;
	}
	
	
	
	public String getExtraParams()
	{
		return extraParams;
	}



	public void setExtraParams(String extraParams)
	{
		this.extraParams = extraParams;
	}



	public String getRequestURL()
	{
		return requestURL;
	}
	public void setRequestURL(String requestURL)
	{
		this.requestURL = requestURL;
	}
	public int getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	public long getPage()
	{
		return page;
	}
	public void setPage(long page)
	{
		this.page = page;
	}
	public String getFields()
	{
		return fields;
	}
	public void setFields(String fields)
	{
		this.fields = fields;
	}
	public String getQ()
	{
		return q;
	}
	public void setQ(String q)
	{
		this.q = q;
	}
	public String getSort()
	{
		return sort;
	}
	public void setSort(String sort)
	{
		this.sort = sort;
	}
	
	
}
