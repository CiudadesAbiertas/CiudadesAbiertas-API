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

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.ciudadesabiertas.model.OauthAccessToken;
import org.ciudadesabiertas.model.OauthClientDetails;
import org.ciudadesabiertas.service.OauthAccessTokenService;
import org.ciudadesabiertas.service.OauthClientDetailsService;
import org.ciudadesabiertas.utils.Constants;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{

	public static final String RESOURCE_ID = "restservice";

	private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment env;

	// Utilizamos este servicio para vaciar la table oauth_client_detail al inicio
	// de la app
	@Autowired
	private OauthClientDetailsService oauthClientDetailsService;
	
	// Utilizamos este servicio para vaciar la table oauth_access_token al inicio
	// de la app
	@Autowired
	private OauthAccessTokenService oauthAccessTokenService;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints.addInterceptor(new TokenInterceptor());
		
		List<OauthAccessToken> tokenList = oauthAccessTokenService.list();
		
		List<OauthClientDetails> clientDetails = oauthClientDetailsService.list();
		
		if (tokenList.size()>0)
		{
			oauthAccessTokenService.deleteAll();
		}

		boolean founded = false;
		OauthClientDetails clientDetail = null;
		for (OauthClientDetails details : clientDetails)
		{
			if (details.getClientId().equals(env.getProperty(Constants.APPNAME)))
			{
				clientDetail = details;
				founded = true;
				break;
			}
		}
		// Si no existe lo creo
		if (founded == false)
		{
			oauthClientDetailsService.deleteAll();

			OauthClientDetails newDetail = new OauthClientDetails();
			newDetail.setClientId(env.getProperty(Constants.APPNAME));
			newDetail.setResourceIds(RESOURCE_ID);
			newDetail.setClientSecret(env.getProperty(Constants.APPSECRET));
			newDetail.setScope("read,write");
			//Dejamos solo el password para evitar problemas con el refresh token
			//newDetail.setAuthorizedGrantTypes("password,refresh_token");
			newDetail.setAuthorizedGrantTypes("password");
			newDetail.setWebServerRedirectUri("");
			newDetail.setAuthorities("USER");
			newDetail.setAccessTokenValidity(Integer.parseInt(env.getProperty("tokenTime")));
			newDetail.setAutoapprove("");
			newDetail.setAdditionalInformation("{}");

			oauthClientDetailsService.add(newDetail);

		} else
		{
			// Si existe compruebo que el tiempo es el mismo que tengo seteado en el
			// properties
			String timeInBD = clientDetail.getAccessTokenValidity().toString();
			String timeInProperties = env.getProperty("tokenTime");
			if (!timeInBD.equals(timeInProperties))
			{
				log.info("Refresh token time will be updated");
				clientDetail.setAccessTokenValidity(Integer.parseInt(timeInProperties));
				oauthClientDetailsService.update(clientDetail);
				log.info("Refresh token time updated");
			}

		}

		endpoints.userDetailsService(userDetailsService).authorizationCodeServices(authorizationCodeServices()).authenticationManager(this.authenticationManager).tokenStore(tokenStore()).approvalStoreDisabled();

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
		oauthServer.checkTokenAccess("hasRole('CLIENT')");

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception
	{
		client.jdbc(dataSource);
	}

	@Bean
	public JdbcTokenStore tokenStore()
	{
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	protected AuthorizationCodeServices authorizationCodeServices()
	{
		return new JdbcAuthorizationCodeServices(dataSource);
	}

}