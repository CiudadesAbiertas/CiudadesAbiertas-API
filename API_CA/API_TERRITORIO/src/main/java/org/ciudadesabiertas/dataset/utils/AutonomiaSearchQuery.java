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

import org.ciudadesabiertas.dataset.model.Pais;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class AutonomiaSearchQuery extends AutonomiaSearch {

	@JsonIgnore
	private static final long serialVersionUID = -7461261144397876766L;	
	
	
	@ApiModelProperty(hidden = true)
	private Pais paisObject;
	
	public Pais getPaisObject() {
		return paisObject;
	}

	public void setPaisObject(Pais paisObject) {
		this.paisObject = paisObject;
	}

	public AutonomiaSearchQuery() {
	}

	public AutonomiaSearchQuery toTransform(AutonomiaSearch obj) {		
		this.setId(obj.getId());
		this.setIdentifier(obj.getIdentifier());				
		this.setPais(obj.getPais());
		this.setTitle(obj.getTitle());				
		return this;		
		
	}
		
	
	
	
}
