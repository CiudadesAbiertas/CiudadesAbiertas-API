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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"id"})
public class Result<T> {
		
	
	private int page=0;
	private int pageSize=0;
	private long totalRecords=0;
	private long pageRecords=0;
	private int status=0;
	private Date responseDate;
	private String first="";
	private String last="";
	private String next="";
	private String prev="";
	private String self="";
	private String contentMD5="";
	private String projectedCoordinates="";
	private String geographicCoordinates="";
	
	


	@JacksonXmlElementWrapper(localName = "records")
	@JacksonXmlProperty(localName = "record")		
	private List<T> records = null;
	
	
	public Result() {
		super();	
		responseDate=new Date();
	}
	

	public Date getResponseDate()
	{
		return responseDate;
	}


	public void setResponseDate(Date responseDate)
	{
		this.responseDate = responseDate;
	}


	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	
	public List<T> getRecords()
	{
		return records;
	}


	public void setRecords(List<T> records)
	{
		this.records = records;
	}


	public long getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	


	public String getFirst()
	{
		return first;
	}


	public void setFirst(String first)
	{
		this.first = first;
	}


	public String getLast()
	{
		return last;
	}


	public void setLast(String last)
	{
		this.last = last;
	}


	public String getNext()
	{
		return next;
	}


	public void setNext(String next)
	{
		this.next = next;
	}


	public String getPrev()
	{
		return prev;
	}


	public void setPrev(String prev)
	{
		this.prev = prev;
	}


	public String getSelf()
	{
		return self;
	}


	public void setSelf(String self)
	{
		this.self = self;
	}
	
	
	
	public String getContentMD5() {
		return contentMD5;
	}


	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}


	public long getPageRecords()
	{
		return pageRecords;
	}


	public void setPageRecords(long pageRecords)
	{
		this.pageRecords = pageRecords;
	}


	public String getProjectedCoordinates() {
		return projectedCoordinates;
	}


	public void setProjectedCcoordinates(String projectedCoordinates) {
		this.projectedCoordinates = projectedCoordinates;
	}


	public String getGeographicCoordinates() {
		return geographicCoordinates;
	}


	public void setGeographicCoordinates(String geographicCoordinates) {
		this.geographicCoordinates = geographicCoordinates;
	}

	
	
	
}
