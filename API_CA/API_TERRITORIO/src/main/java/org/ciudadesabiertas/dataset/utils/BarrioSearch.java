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

import org.ciudadesabiertas.dataset.model.Barrio;
import org.ciudadesabiertas.utils.DatasetSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class BarrioSearch  implements  Serializable, DatasetSearch<Barrio> {

	@JsonIgnore
	private static final long serialVersionUID = -7462261144307876766L;

	private String id;

	private String title;
	
	private String identifier;
	
	private String pais;
	
	private String autonomia;
	
	private String municipioId;
	
	private String provincia;
	
	private String distritoId;
	
	
	public String getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(String distritoId) {
		this.distritoId = distritoId;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	public String getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	public BarrioSearch() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}	
	
	
}
