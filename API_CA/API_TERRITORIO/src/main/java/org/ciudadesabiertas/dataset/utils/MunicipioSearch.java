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

import org.ciudadesabiertas.dataset.model.Municipio;
import org.ciudadesabiertas.utils.DatasetSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class MunicipioSearch  implements  Serializable, DatasetSearch<Municipio> {

	@JsonIgnore
	private static final long serialVersionUID = -7461261144397876766L;
	
	@ApiModelProperty(value = "Identificador del distrito. Ejemplo: 28006")
	private String id;
	
	@ApiModelProperty(value = "Nombre del distrito. Ejemplo: Alcobendas")
	private String title;
	
	@ApiModelProperty(value = "Cada una de las grandes divisiones de un territorio o Estado, sujeta por lo común a una autoridad administrativa. Ejemplo: Madrid")
	private String provincia;
	
	@ApiModelProperty(value = "Autonomía (Comunidad Autónoma o Ciudad Autónoma) a la que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: Comunidad-Madrid")
	private String autonomia;
	
	@ApiModelProperty(value = "País al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: España")
	private String pais;
	

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	public MunicipioSearch() {
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}	
	
	
	
}
