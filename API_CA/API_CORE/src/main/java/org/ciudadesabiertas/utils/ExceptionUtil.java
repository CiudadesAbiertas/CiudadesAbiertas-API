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

package org.ciudadesabiertas.utils;

import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.exception.NotFoundException;
import org.ciudadesabiertas.exception.TooManyRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ExceptionUtil
{
	private static final Logger log = LoggerFactory.getLogger(ExceptionUtil.class);
	
	
	public static ResponseEntity<?> checkException(Exception exception)
	{
		log.error(exception.getMessage());
		
		ResponseEntity<?> responseEntity=new ResponseEntity(new ResultError("Internal error: " +exception.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.INTERNAL_SERVER_ERROR);		
		
		if (exception instanceof BadRequestException)
		{
			responseEntity=new ResponseEntity(new ResultError("Wrong request: " +exception.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
		}else if (exception instanceof DAOException)
		{
			responseEntity=new ResponseEntity(new ResultError("Database error:"+exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR);
		}else if (exception instanceof TooManyRequestException) {
			responseEntity=new ResponseEntity(new ResultError("Too Many Request:"+exception.getMessage(),HttpStatus.TOO_MANY_REQUESTS.value()),HttpStatus.TOO_MANY_REQUESTS);
		}else if (exception instanceof NotFoundException) {
			responseEntity=new ResponseEntity(new ResultError("Not Found: "+exception.getMessage(),HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
	
	
	
}
