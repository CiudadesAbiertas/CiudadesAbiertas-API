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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.service.EstadisticaService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.StartVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;





/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	

	private static final String RESOURCE_ID = "restservice";
	
	private static final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ListableBeanFactory listableBeanFactory;

	@Autowired
	private EstadisticaService estadisticaService;
	
	@Autowired
	private MultipleSessionFactory multipleSessionFactory;
		
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		// @formatter:off
		resources
			.resourceId(RESOURCE_ID);
		// @formatter:on
		
		
		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//Array que contiene la lista de controladores de los que vamos a omitir sus urls y su documentación
		ArrayList<String> controllersToIgnore=new ArrayList<String>();
		controllersToIgnore.add("CiudadController".toLowerCase());
		controllersToIgnore.add("TestController".toLowerCase());
		

		ArrayList<SecurityURL> basicAuthUris=new ArrayList<SecurityURL>();
		ArrayList<SecurityURL> adminAuthUris=new ArrayList<SecurityURL>();
		
		String https=env.getProperty(Constants.STR_HTTPS);
		if (https!=null && https.toLowerCase().equals(Constants.STR_ACTIVE_ON))
		{
			//Only https access
			http.requiresChannel().anyRequest().requiresSecure();			
		}
		
		StartVariables.listURIs=new ArrayList<String>();
		//CMG Para el control de los controllers
		StartVariables.listControllers=new ArrayList<String>();
		log.info("Controllers detected from Ciudades Abiertas Libraries:");
		Map<String, Object> controllers = listableBeanFactory.getBeansWithAnnotation(RestController.class);
		for (Map.Entry<String, Object> entry : controllers.entrySet())
		{
			String key=entry.getKey();			
			if ((key!=null)&&(controllersToIgnore.contains(key.toLowerCase())==false))
			{
				CiudadesAbiertasController controller=(CiudadesAbiertasController)entry.getValue();
								
				if (controller.getListURI()!=null)
				{
					StartVariables.listURIs.add(controller.getListURI());
					//CMG Añadimos el nombre de los Controllers
					StartVariables.listControllers.add(controller.getName());
				}
				
				log.info("\t"+controller.getName());
				List<SecurityURL> urisAndRoles=controller.getURLsWithRoles();
				for (SecurityURL securityURL : urisAndRoles)
				{
					if (securityURL.getUrl().endsWith("*")==false)
					{
						securityURL.setUrl(securityURL.getUrl()+"*");
					}
					if (securityURL.getAutentication().equals(Constants.BASIC_AUTH))
					{
						log.info("\t\tBasic: "+securityURL.getUrl()+"("+securityURL.getVerb()+")");
						basicAuthUris.add(new SecurityURL(securityURL.getUrl(),securityURL.getVerb()));	
					}else if (securityURL.getAutentication().equals(Constants.ADMIN_AUTH))
					{
						log.info("\t\tAdmin :"+securityURL.getUrl()+"("+securityURL.getVerb()+")");					
						adminAuthUris.add(new SecurityURL(securityURL.getUrl(),securityURL.getVerb()));
					}else {
						log.info("\t\tPublic :"+securityURL.getUrl()+"("+securityURL.getVerb()+")");		
					}
				}
				
			}
		}
		log.info(StartVariables.listURIs.size()+" list URIs are available in all datasets");
		

		//Inicializo las session factory necesarias		
		for (Map.Entry<String, Object> entry : controllers.entrySet())
		{
			String key=((CiudadesAbiertasController)entry.getValue()).getKey();
			
			LocalSessionFactoryBuilder localSessionFactoryBuilder = multipleSessionFactory.getBuilders().get(key);
			
			if (localSessionFactoryBuilder!=null) {
				log.info("Generating custom sessionFactory for "+key+".properties");
				multipleSessionFactory.getFactories().put(key, localSessionFactoryBuilder.buildSessionFactory());
			}
		}
				
		//autenticación test
		http.authorizeRequests()				
			.antMatchers("/test2*").authenticated()
			.antMatchers("/test3*").authenticated()
			.antMatchers("/test3*").hasRole(Constants.NAME_ADMIN)
			.antMatchers("/test4*").hasAuthority(Constants.AUTORITHY_CONSULTA);
		
		boolean escritura=false;
		try
		{
			escritura=Boolean.parseBoolean(env.getProperty(Constants.OPERACIONES_ESCRITURA));
		}
		catch (Exception e)
		{
			log.error("Wrong value of "+Constants.OPERACIONES_ESCRITURA,e);
			log.info("Readonly configured by default");
		}
		
		if (escritura==false)
		{
			http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/**").denyAll();
			http.authorizeRequests().antMatchers(HttpMethod.POST,"/**").denyAll();
			http.authorizeRequests().antMatchers(HttpMethod.PUT,"/**").denyAll();	
		}
		else 
		{
			//autenticación operaciones
			for (SecurityURL url : basicAuthUris)
			{
				http.authorizeRequests().antMatchers(url.getVerb(), url.getUrl()).authenticated();			
			}
			for (SecurityURL url : adminAuthUris)
			{
				http.authorizeRequests()
				.antMatchers(url.getVerb(), url.getUrl()).authenticated()
				.antMatchers(url.getVerb(), url.getUrl()).hasRole(Constants.NAME_ADMIN);
			}
		}

		
				
						

		
		APIFilter locFilter=new APIFilter(estadisticaService);			
		http.addFilterAfter(locFilter, BasicAuthenticationFilter.class);
		
	}

	
	

}
