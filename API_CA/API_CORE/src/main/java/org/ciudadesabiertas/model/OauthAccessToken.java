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

package org.ciudadesabiertas.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@Table(name = "oauth_access_token")
public class OauthAccessToken implements java.io.Serializable
{	
	private static final long serialVersionUID = -3950228012808677796L;
	
	private String authenticationId;
	private String tokenId;
	private byte[] token;
	private String userName;
	private String clientId;
	private byte[] authentication;
	private String refreshToken;

	public OauthAccessToken()
	{
	}

	public OauthAccessToken(String authenticationId)
	{
		this.authenticationId = authenticationId;
	}

	public OauthAccessToken(String authenticationId, String tokenId, byte[] token, String userName, String clientId, byte[] authentication, String refreshToken)
	{
		this.authenticationId = authenticationId;
		this.tokenId = tokenId;
		this.token = token;
		this.userName = userName;
		this.clientId = clientId;
		this.authentication = authentication;
		this.refreshToken = refreshToken;
	}

	@Id

	@Column(name = "authentication_id", unique = true, nullable = false)
	public String getAuthenticationId()
	{
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId)
	{
		this.authenticationId = authenticationId;
	}

	@Column(name = "token_id")
	public String getTokenId()
	{
		return this.tokenId;
	}

	public void setTokenId(String tokenId)
	{
		this.tokenId = tokenId;
	}

	@Column(name = "token")
	public byte[] getToken()
	{
		return this.token;
	}

	public void setToken(byte[] token)
	{
		this.token = token;
	}

	@Column(name = "user_name")
	public String getUserName()
	{
		return this.userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	@Column(name = "client_id")
	public String getClientId()
	{
		return this.clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	@Column(name = "authentication")
	public byte[] getAuthentication()
	{
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication)
	{
		this.authentication = authentication;
	}

	@Column(name = "refresh_token")
	public String getRefreshToken()
	{
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}

}
