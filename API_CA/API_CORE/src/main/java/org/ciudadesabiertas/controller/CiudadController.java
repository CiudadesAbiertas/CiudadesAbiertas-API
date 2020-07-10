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

package org.ciudadesabiertas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.ciudadesabiertas.model.Ciudad;
import org.ciudadesabiertas.service.CiudadService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.HttpUtil;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RestController
public class CiudadController implements CiudadesAbiertasController
{

	public static final String CIUDAD_LIST = "/ciudad/list";

	private static final String robotsTXT = "/robots.txt";

	private static final String sitemapXML = "/sitemap.xml";

	public static final String VERSION_1 = "v1/";

	private static final Logger log = LoggerFactory.getLogger(CiudadesAbiertasController.class);

	
	@Autowired
	private Environment env;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	public PasswordEncoder passwordEncoder;

	//JCBH here we store the api.json response, I used singleton pattern
	private static StringBuffer apiJsonResponse;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = Constants.API_JSON, method = RequestMethod.GET, produces = Constants.CONTENT_TYPE_JSON_UTF8)
	public @ResponseBody String API_JSON(HttpServletRequest request, 
										@RequestParam(value = "a", defaultValue = "2707", required = false) String aleat)
	{
		log.info(Constants.API_JSON+" "+aleat);
				
		if (apiJsonResponse == null)
		{
			
			log.info("[apiJsonResponse] [api.json] [LOADING...]");
			String json = "";
			String path = Util.getFullURL(request);
			path = path.replace(Constants.API_JSON, Constants.SWAGGER_V2_API_DOCS);

			Header hAccept = new BasicHeader(Constants.HEADDER_ACCEPT, Constants.CONTENT_TYPE_JSON_UTF8);
			Header hContent = new BasicHeader(Constants.HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_JSON_UTF8);
			List<Header> headers = new ArrayList<Header>();
			headers.add(hAccept);
			headers.add(hContent);

			boolean escritura = false;
			boolean head = false;
			boolean autorization = false;
			try
			{
				escritura = Boolean.parseBoolean(env.getProperty(Constants.OPERACIONES_ESCRITURA));
				head = Boolean.parseBoolean(env.getProperty(Constants.OPERACIONES_HEAD));
				autorization = Boolean.parseBoolean(env.getProperty(Constants.OPERACIONES_AUTORIZACION));
			} catch (Exception e)
			{
				log.info("Error reading " + Constants.OPERACIONES_ESCRITURA + " .Only read operations");
			}

			try
			{
				json = HttpUtil.getPetition(path, headers);
				
			} catch (Exception e)
			{
				log.error("Error reading documentation", e);
			}
			
			String tomcatHost=request.getServerName()+":"+request.getServerPort();
			String actualHost=StartVariables.serverPort;			
			
			String tomcatContext = applicationContext.getApplicationName();
			String newContext=StartVariables.context; 
			
			
			if (tomcatHost.equals(actualHost)==false)
			{
				json=json.replace(tomcatHost, actualHost);
			}
			if (Util.validValue(newContext))
			{
				json=json.replace(tomcatContext, newContext);
			}

			try
			{
				json=cleanAllowEmptyValue(json);
			}
			catch (Exception e)
			{
				log.error("Error cleaning attributes of AllowEmptyValue",e);
			}
			
			
			if (escritura && head && autorization)
			{
				json = Util.jsonPrettyPrint(json);
			} 
			else
			{
				Gson gson = new Gson();
				TreeMap<String, Object> map = gson.fromJson(json, TreeMap.class);
				
				if (autorization==false)
				{
					map.remove("securityDefinitions");
				}
				
				LinkedTreeMap paths = (LinkedTreeMap) map.get("paths");
				
				for (Object key : paths.keySet())
				{
					// based on you key types
					String keyStr = (String) key;
					LinkedTreeMap localPath = (LinkedTreeMap) paths.get(keyStr);
					
					if (escritura==false)
					{			
						
						localPath.remove("put");
						if (keyStr.endsWith("transform")==false)
						{
							localPath.remove("post");
						}
						localPath.remove("delete");
					}
					if (head==false)
					{					
						localPath.remove("head");						
					}
					
					if (autorization==false)
					{
						Set<String> operaciones = localPath.keySet();
						for (String keyOperaciones : operaciones)
						{
							((LinkedTreeMap)localPath.get(keyOperaciones)).remove("security");
							List parameters = (List) ((LinkedTreeMap)localPath.get(keyOperaciones)).get("parameters");
							List parametersWithouSecurity = new ArrayList();
							
							for (int i=0;i<parameters.size();i++)
							{
								LinkedTreeMap localParam = (LinkedTreeMap) parameters.get(i);
								if ((localParam.get("name")!=null)&&(((String)localParam.get("name")).equals("Authorization")==false))
								{
									parametersWithouSecurity.add(parameters.get(i));
								}
							}							
							((LinkedTreeMap)localPath.get(keyOperaciones)).put("parameters", parametersWithouSecurity);
							
						}
					}
					
					Set<String> operaciones = localPath.keySet();
					for (String keyOperaciones : operaciones)
					{
					
						List parameters = (List) ((LinkedTreeMap)localPath.get(keyOperaciones)).get("parameters");
						
						for (int i=0;i<parameters.size();i++)
						{
							LinkedTreeMap localParam = (LinkedTreeMap) parameters.get(i);
							if ((localParam.get("name")!=null)&&(((String)localParam.get("name")).equals(Constants.SRID)))
							{								
								localParam.put("default", Constants.SRID_SWAGGER);
							}
						}							
						
						
					}
					
					paths.put(keyStr, localPath);
				}			
								
				map.put("paths", paths);
				
				
				
				String sortedJson = gson.toJson(map);
				
				json = Util.jsonPrettyPrint(sortedJson.toString());
				
				
			}
				
			
			
			
			apiJsonResponse=new StringBuffer();
			apiJsonResponse.append(json);
			log.info("[apiJsonResponse] [api.json] [initialized]");
		}
		return apiJsonResponse.toString();
	}

	private String cleanAllowEmptyValue(String json) {
		Gson gson = new Gson();
		TreeMap<String, Object> map = gson.fromJson(json, TreeMap.class);
		
		LinkedTreeMap definitions = (LinkedTreeMap) map.get("definitions");
		
		
		for (Object key : definitions.keySet())
		{	
			String keyStr = (String) key;
			if (keyStr.equals("Map«string,object»")==false)
			{
				log.info("Cleaning "+key);
				LinkedTreeMap localDefinition = (LinkedTreeMap) definitions.get(keyStr);
				LinkedTreeMap properties = (LinkedTreeMap)localDefinition.get("properties");
				for (Object propertyKey : properties.keySet())
				{	
					String keyP = (String) propertyKey;
					LinkedTreeMap localProperty = (LinkedTreeMap) properties.get(keyP);
					localProperty.remove("allowEmptyValue");
					
					properties.put(keyP, localProperty);
				}
				localDefinition.put("properties",properties);
				definitions.put(keyStr,localDefinition);		
			}
		}			
						
		map.put("definitions", definitions);
		
		return gson.toJson(map);
	}

	@RequestMapping(value = "/swagger")
	public void swaggerDefault(HttpServletResponse response) throws IOException
	{
		log.info("/swagger");
		response.sendRedirect(Constants.SWAGGER_INDEX);
	}

	@RequestMapping(robotsTXT)
	public @ResponseBody String robots()
	{

		log.info(robotsTXT);

		StringBuffer text = new StringBuffer();
		text.append("user-agent: *" + System.getProperty("line.separator"));
		text.append("Disallow: /");

		return text.toString();
	}

	@RequestMapping(sitemapXML)
	public @ResponseBody String sitemap(HttpServletRequest request)
	{
		log.info(sitemapXML);

		String fullURL = Util.getFullURL(request);
		fullURL = fullURL.substring(0, fullURL.indexOf(sitemapXML));

		StringBuffer text = new StringBuffer();
		text.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.getProperty("line.separator"));
		text.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">" + System.getProperty("line.separator"));
		text.append("<url>" + System.getProperty("line.separator"));

		text.append("<loc>" + fullURL + "</loc>" + System.getProperty("line.separator"));
		text.append("</url>" + System.getProperty("line.separator"));
		text.append("</urlset>" + System.getProperty("line.separator"));

		return text.toString();
	}

	@RequestMapping(CIUDAD_LIST)
	public @ResponseBody List<Ciudad> list(HttpServletRequest request) throws Exception
	{

		log.info(CIUDAD_LIST);

		List<Ciudad> findAll = ciudadService.findAll();

		return findAll;
	}

	@Override
	public ArrayList<SecurityURL> getURLsWithRoles()
	{
		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();

		urlRoles.add(new SecurityURL(CIUDAD_LIST, HttpMethod.GET, Constants.NO_AUTH));

		return urlRoles;
	}

	@Override
	public String getKey()
	{
		return "ciudad";
	}

	@Override
	public String getListURI()
	{
		return null;
	}

}
