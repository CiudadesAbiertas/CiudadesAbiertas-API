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

package org.ciudadesabiertas.config;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.InternalErrorException;
import org.ciudadesabiertas.exception.TooManyRequestException;
import org.ciudadesabiertas.model.Estadistica;
import org.ciudadesabiertas.service.EstadisticaService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.Util;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class APIFilter implements Filter
{
	private static final Logger log = LoggerFactory.getLogger(APIFilter.class);

	private EstadisticaService estadisticaService;
	
	

	public APIFilter()
	{

	}

	public APIFilter(EstadisticaService estadisticaService)
	{
		this.estadisticaService = estadisticaService;
	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication auth = securityContext.getAuthentication();
		
		HttpServletResponse hResponse = (HttpServletResponse) response;   
		HttpServletRequest hRequest = (HttpServletRequest) request; 
		
		//Metodo OPTIONs para peticiones AJAX
		if ("OPTIONS".equalsIgnoreCase(hRequest.getMethod())) {
			log.info("OPTIONS petition");
            hResponse.setStatus(HttpServletResponse.SC_OK);   
            hResponse.setHeader("Access-Control-Allow-Origin", "*"); 
            hResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            hResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");           
            return;
        }
		
		
		boolean continuar = true;
	
		String theURL=Util.getFullURL(hRequest);
		log.debug(theURL);
		
		if (theURL.length()>Constants.maxSizeURL)
		{
			sendTooLongURI(request, response);
			return;
		}
				
		
		//omitimos el control de recursos estaticos
		if (theURL.contains(Constants.RESOURCES) ||
			theURL.contains(Constants.SWAGGER)	|| 
			theURL.contains(Constants.API_JSON) ||
			theURL.contains(Constants.API_DOCS) ||
			theURL.contains(hRequest.getContextPath()) )
		{
			chain.doFilter(request, response);
		}
		else 
		{ 
			if (Util.validURL(hRequest)==false)
			{
				sendBadRequestError(request, response);
				return;
			}
			
			String extension=Util.getExtensionUri(theURL);
			
			if (!Util.isValidExtensionOfUri(extension) /* & validar */) {
				log.info("Extension not allowed: "+extension+" in "+Constants.FORMATOS_EXTENSIONES);
				sendBadRequestError(extension, request, response);
				return;
			}
			// anonymous petitions
			if (auth == null)
			{
				log.info("Anonymous petition or wrong context");
				continuar = estadisticaService.controlRequesPerSecondUser(Constants.ANONYMOUS_USER);	
				//Si queremos controlar que no acceda a BBDD para insertar la estadistica si se ha sobrepasado el limite por el if aqui
				statisticsControl(hRequest, true, Constants.ANONYMOUS_USER);
			// Si no es anonimo
			} else
			{
				UserDetails details = (UserDetails) auth.getPrincipal();
				log.info("Petition by " + details.getUsername());
				continuar = estadisticaService.controlRequesPerSecondUser(details.getUsername());	
				//Si queremos controlar que no acceda a BBDD para insertar la estadistica si se ha sobrepasado el limite por el if aqui				
				statisticsControl(hRequest, true, details.getUsername());		
			}
			
			if (continuar)	{
				try
				{ 
					chain.doFilter(request, response);
				}
				catch (RuntimeException runEx)
				{
					log.error("Error 500", runEx);					
					sendInternalError(request, response);
				}
				catch (Exception e)
				{
					log.error("Error 500",e);					
					sendInternalError(request, response);
				}
			}else {
				sendTooManyRequest(request, response);
			}
		}		
		return;
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub

	}

	protected void statisticsControl(HttpServletRequest request, boolean status, String userName)
	{
		Estadistica statData = genStatisticData(request, status, userName);
	
		try
		{
			estadisticaService.add(statData);
		} catch (org.springframework.dao.DataIntegrityViolationException e)
		{
			log.error("Clave repetida", e);
		}
	
	}

	public String convertObjectToJson(Object object) throws IOException
	{
		if (object == null)
		{
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();		
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}

	public String convertObjectToXML(Object object) throws IOException
	{
		if (object == null)
		{
			return null;
		}
		XmlMapper mapper = new XmlMapper();		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}

	public static Estadistica genStatisticData(HttpServletRequest request, boolean status, String userName)
	{
		String completeURL = Util.getFullURL(request);

		String ipAddress = request.getRemoteAddr();
		String userAgent = "no info";
		// is client behind something?
		if (request.getHeader("X-FORWARDED-FOR") == null)
		{
			ipAddress = request.getRemoteAddr();
		}

		if (request.getHeader("User-Agent") != null)
		{
			userAgent = request.getHeader("User-Agent");
			if (userAgent.length() > 200)
				userAgent = userAgent.substring(0, 199);
		}

		Estadistica statData = new Estadistica();
		statData.setOrigen(ipAddress);
		statData.setFechaPeticion(new Timestamp(new Date().getTime()));
		statData.setUrl(completeURL);
		statData.setUserAgent(userAgent);

		if (userName != null)
		{
			statData.setUsuario(userName);
		} else
		{
			statData.setUsuario(Constants.ANONYMOUS_USER);
		}
		return statData;
	}
	
	public void sendTooManyRequest(ServletRequest request, ServletResponse response) throws IOException {
	
		TooManyRequestException errorObj = new TooManyRequestException(" limit Exceeded: Maximum number of requests per second");
		
		createResponseError(request, response, errorObj);

	}
	
	
	public void sendTooLongURI(ServletRequest request, ServletResponse response) throws IOException {		
	
		BadRequestException errorObj = new BadRequestException("The URL is too long");
		
		createResponseError(request, response, errorObj);

	}
	
	
	public void sendInternalError(ServletRequest request, ServletResponse response) throws IOException {		
		
		InternalErrorException errorObj = new InternalErrorException("Internal Error");		
		createResponseError(request, response, errorObj);

	}

	public void sendBadRequestError(String ext, ServletRequest request, ServletResponse response) throws IOException {	
		
		BadRequestException errorObj = new BadRequestException("Bad Request Error. Extension '"+ext+"' not valid");		
		createResponseError(request, response, errorObj);

	}
	
	public void sendBadRequestError(ServletRequest request, ServletResponse response) throws IOException {	
		
		BadRequestException errorObj = new BadRequestException("Bad Request Error. Bad URL");
		createResponseError(request, response, errorObj);

	}
	
	public void sendSeeOther(ServletRequest request, ServletResponse response, String uri) throws IOException {
		
		createResponseError( request,  response, null,uri);
	}
	
	/**
	 * ***********************************************************
	 * 					METODOS PRIVADOS 
	 * ***********************************************************
	 *  ||												||
	 *  \/												\/
	 */

	//Función para cerrar el flujo de salida cuando devolvemos  error
	private void closeOutput(ServletResponse response) 
	{
		try
		{
			response.getWriter().flush();
			response.getWriter().close();
			log.debug("Output closed");
		}
		catch (Exception e1)
		{
			log.error("Error closing output in captured error",e1);
		}
	}

	private void createResponseError(ServletRequest request, ServletResponse response,Exception errorObj) throws IOException {
		createResponseError( request,  response, errorObj,null);
	}
	
	@SuppressWarnings("unchecked")
	private void createResponseError(ServletRequest request, ServletResponse response,Exception errorObj,String uri) throws IOException {
		
		HttpServletRequest hRequest=(HttpServletRequest) request;
		HttpServletResponse hResponse=(HttpServletResponse) response;
		String contextPath = hRequest.getServletPath();
		String contentType = "";
		if (hRequest.getHeader(Constants.HEADDER_ACCEPT) != null)
		{
			contentType = hRequest.getHeader(Constants.HEADDER_ACCEPT);
		}

		if (errorObj instanceof BadRequestException)
		{
			hResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);		
		}
		else if (errorObj instanceof InternalErrorException)
		{
			hResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);		
		}
		
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		ResponseEntity<Object> responseEntity= new ResponseEntity<>(HttpStatus.OK);
		if (errorObj!=null) {
			responseEntity=(ResponseEntity<Object>) ExceptionUtil.checkException(errorObj);
		}else{
			HttpHeaders headers = new HttpHeaders();
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			headers.add(Constants.HEADER_LOCATION, uri.replace(uri, uri+".html"));
			responseEntity=new ResponseEntity<Object>(headers,HttpStatus.SEE_OTHER);
		}
		// json by default
		if (contextPath.endsWith(Constants.EXT_JSON) || contentType.contains(Constants.FORMATO_JSON)) {
			response.setContentType(Constants.CONTENT_TYPE_JSON_UTF8);
			response.getWriter().println(convertObjectToJson(responseEntity));
		}
		else if (contextPath.endsWith(Constants.EXT_XML) || contentType.contains(Constants.FORMATO_XML)) {
			response.setContentType(Constants.CONTENT_TYPE_XML_UTF8);
			response.getWriter().println(convertObjectToXML(responseEntity));			
		}
		else {
			response.setContentType(Constants.CONTENT_TYPE_HTML_XML_UTF8);			
			response.getWriter().println(convertObjectToJson(responseEntity));
		}
		
		closeOutput(response);

	}


}
