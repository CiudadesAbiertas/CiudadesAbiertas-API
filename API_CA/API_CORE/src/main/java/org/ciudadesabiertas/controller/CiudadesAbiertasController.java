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

package org.ciudadesabiertas.controller;

import java.util.ArrayList;

import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.SecurityURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public interface CiudadesAbiertasController
{	
	String getKey();

	ArrayList<SecurityURL> getURLsWithRoles();
	
	String getListURI();
	
	static final Logger log = LoggerFactory.getLogger(CiudadesAbiertasController.class);
	
	default String getName()
	{
		String name = this.getClass().getName();

		if (name.contains(".") && (name.length() > name.lastIndexOf(".")))
		{
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		return name;

	}
		
	


	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	default @ResponseBody ResponseEntity<?> handleJsonMappingException(Exception ex) {
		log.error("[handleJsonMappingException][ERROR:"+ex.getMessage()+"]");
		ResponseEntity<?> responseEntity=new ResponseEntity<Object>(new ResultError("Wrong request",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	@ExceptionHandler(value 
		      = { org.springframework.web.HttpMediaTypeNotAcceptableException.class })
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	default @ResponseBody ResponseEntity<?> notAcceptableException(Exception ex) {
		log.error("[notAcceptableException][ERROR:"+ex.getMessage()+"]");
		ResponseEntity<?> responseEntity=new ResponseEntity<Object>(new ResultError("Wrong request:"+ex.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()),HttpStatus.NOT_ACCEPTABLE);
		return responseEntity;
	}

}
