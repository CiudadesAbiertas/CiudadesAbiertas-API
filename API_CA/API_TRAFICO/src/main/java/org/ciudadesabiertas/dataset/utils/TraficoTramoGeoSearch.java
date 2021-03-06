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

package org.ciudadesabiertas.dataset.utils;

import java.io.Serializable;
import java.math.BigDecimal;

import org.ciudadesabiertas.dataset.model.TraficoTramo;
import org.ciudadesabiertas.utils.DatasetSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 * @author Hugo Lafuente (Localidata)
 */
public class TraficoTramoGeoSearch extends TraficoTramo implements  Serializable, DatasetSearch<TraficoTramo> {

	@JsonIgnore
	private static final long serialVersionUID = -3773361021929583183L;	
	
	@ApiModelProperty(hidden = true)
	private BigDecimal latitud;
	
	@ApiModelProperty(hidden = true)
	private BigDecimal longitud;	
	
	@ApiModelProperty(hidden = true)
	private BigDecimal x;
	
	@ApiModelProperty(hidden = true)
	private BigDecimal y;

	@ApiModelProperty(hidden = true)
	private Double distance;
	
	@ApiModelProperty(required=true, value = "Coordenada X del inicio del tramo. Ejemplo: 440124.33000")
	private BigDecimal xETRS89;	
	
	@ApiModelProperty(required=true, value = "Coordenada Y del inicio del tramo. Ejemplo: 4474637.17000")
	private BigDecimal yETRS89;
	
	@ApiModelProperty(hidden = true)
	private BigDecimal finLatitud;
	
	@ApiModelProperty(hidden = true)
	private BigDecimal finLongitud;	
	
	@ApiModelProperty(hidden = true)
	private BigDecimal finX;
	
	@ApiModelProperty(hidden = true)
	private BigDecimal finY;
	
	@ApiModelProperty(required=true, value = "Coordenada X del fin del tramo. Ejemplo: 440124.43000")
	private BigDecimal xETRS89Fin;	
	
	@ApiModelProperty(required=true, value = "Coordenada Y del fin del tramo. Ejemplo: 4474637.27000")
	private BigDecimal yETRS89Fin;


	public BigDecimal getxETRS89()
	{
		return xETRS89;
	}


	public void setxETRS89(BigDecimal xETRS89)
	{	
		setX(xETRS89);
	}


	public BigDecimal getyETRS89()
	{
		return yETRS89;
	}


	public void setyETRS89(BigDecimal yETRS89)
	{		
		setY(yETRS89);
	}
	
	public BigDecimal getxETRS89Fin()
	{
		return xETRS89Fin;
	}


	public void setxETRS89Fin(BigDecimal xETRS89Fin)
	{	
		setFinX(xETRS89Fin);
	}


	public BigDecimal getyETRS89Fin()
	{
		return yETRS89Fin;
	}


	public void setyETRS89Fin(BigDecimal yETRS89Fin)
	{		
		setFinY(yETRS89Fin);
	}
	
}
