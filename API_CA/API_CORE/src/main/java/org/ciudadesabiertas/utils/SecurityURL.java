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

import org.springframework.http.HttpMethod;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class SecurityURL
{
	private String url;
	private HttpMethod verb;
	private String autentication;
	private boolean list;
	
	
	public SecurityURL(String url, HttpMethod verb, String autentication)
	{
		super();
		this.url = url;
		this.verb = verb;
		this.autentication = autentication;
	}
	
	public SecurityURL(String url, HttpMethod verb)
	{
		super();
		this.url = url;
		this.verb = verb;		
	}
	
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public HttpMethod getVerb()
	{
		return verb;
	}
	public void setVerb(HttpMethod verb)
	{
		this.verb = verb;
	}
	public String getAutentication()
	{
		return autentication;
	}
	public void setAutentication(String autentication)
	{
		this.autentication = autentication;
	}
	
	public boolean isList()
	{
		return list;
	}

	public void setList(boolean list)
	{
		this.list = list;
	}

	@Override
	public String toString()
	{
		return "SecurityURL [url=" + url + ", verb=" + verb + ", autentication=" + autentication + "]";
	}
	
	
	
	
}
