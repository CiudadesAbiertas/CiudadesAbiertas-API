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


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@Table(name = "estadistica")
public class Estadistica implements java.io.Serializable
{

	@JsonIgnore
	private static final long serialVersionUID = -4982323284395106957L;
	private String id;
	private String usuario;
	private String url;
	private Date fechaPeticion;
	private String origen;
	private String userAgent;

	public Estadistica()
	{
	}

	public Estadistica(String id, String usuario, String url, Date fechaPeticion, String origen, String userAgent)
	{
		this.id = id;
		this.usuario = usuario;
		this.url = url;
		this.fechaPeticion = fechaPeticion;
		this.origen = origen;
		this.userAgent = userAgent;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "usuario", nullable = false, length = 45)
	public String getUsuario()
	{
		return this.usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	@Column(name = "url", nullable = false, length = 2000)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_peticion", nullable = false, length = 19)
	public Date getFechaPeticion()
	{
		return this.fechaPeticion;
	}

	public void setFechaPeticion(Date fechaPeticion)
	{
		this.fechaPeticion = fechaPeticion;
	}

	@Column(name = "origen", nullable = false, length = 20)
	public String getOrigen()
	{
		return this.origen;
	}

	public void setOrigen(String origen)
	{
		this.origen = origen;
	}

	@Column(name = "user_agent", nullable = false, length = 200)
	public String getUserAgent()
	{
		return this.userAgent;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

}
