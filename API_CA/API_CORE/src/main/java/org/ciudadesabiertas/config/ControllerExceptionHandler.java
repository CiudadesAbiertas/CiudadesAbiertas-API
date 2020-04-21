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

import org.ciudadesabiertas.utils.ResultError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler
{

	private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(value = {org.springframework.web.servlet.NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public  @ResponseBody ResponseEntity<?> processMethodNotFoundException(Exception exception)
	{
		log.error("[handleNotFoundException][ERROR:"+exception.getMessage()+"]");
		ResponseEntity<?> responseEntity=new ResponseEntity<Object>(new ResultError("NOT FOUND",HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
		return responseEntity;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView processMethodNotSupportedException(Exception exception)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", exception);		
		mv.setViewName("error");		
		log.error("Generic exception",exception);
		return mv;
	}

}
