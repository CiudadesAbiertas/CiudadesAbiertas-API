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

import org.ciudadesabiertas.dataset.model.Autonomia;
import org.ciudadesabiertas.dataset.model.Municipio;
import org.ciudadesabiertas.dataset.model.Pais;
import org.ciudadesabiertas.dataset.model.Provincia;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class DistritoSearchQuery extends DistritoSearch   {


	
	private static final long serialVersionUID = 1L;

	
	
	private Municipio municipioObject;
	
	
	private Provincia provinciaObject;		

	
	private Autonomia autonomiaObject;
	
	
	private Pais paisObject;
	

	
	public Municipio getMunicipioObject() {
		return municipioObject;
	}

	
	public void setMunicipioObject(Municipio municipioObject) {
		this.municipioObject = municipioObject;
	}

	
	public Provincia getProvinciaObject() {
		return provinciaObject;
	}
	
	public void setProvinciaObject(Provincia provinciaObject) {
		this.provinciaObject = provinciaObject;
	}
	
	public Autonomia getAutonomiaObject() {
		return autonomiaObject;
	}
	@JsonIgnore
	public void setAutonomiaObject(Autonomia autonomiaObject) {
		this.autonomiaObject = autonomiaObject;
	}
	
	public Pais getPaisObject() {
		return paisObject;
	}
	
	public void setPaisObject(Pais paisObject) {
		this.paisObject = paisObject;
	}	
	
	public DistritoSearchQuery toTransform(DistritoSearch obj) {
		this.setAutonomia(obj.getAutonomia());
		this.setId(obj.getId());
		this.setIdentifier(obj.getIdentifier());
		this.setMunicipioId(obj.getMunicipioId());
		this.setProvincia(obj.getProvincia());
		this.setPais(obj.getPais());
		this.setTitle(obj.getTitle());
		
		
		return this;		
		
	}
	
}
