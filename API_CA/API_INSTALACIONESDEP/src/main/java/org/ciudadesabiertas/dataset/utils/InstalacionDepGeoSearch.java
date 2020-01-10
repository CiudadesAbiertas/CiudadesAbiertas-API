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

import org.ciudadesabiertas.dataset.model.Equipamiento;
import org.ciudadesabiertas.utils.DatasetSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class InstalacionDepGeoSearch extends Equipamiento implements  Serializable, DatasetSearch<Equipamiento> {	
	
	
	
	@JsonIgnore
	private static final long serialVersionUID = 8907267270860072334L;
	
	@ApiModelProperty(hidden = true)
	private String portalIdIsolated;	
	
	
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
	
	@ApiModelProperty(required=true)
	private BigDecimal xETRS89;	
	
	@ApiModelProperty(required=true)
	private BigDecimal yETRS89;


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
	
}
