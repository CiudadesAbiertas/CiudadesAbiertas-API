package org.ciudadesabiertas.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "not found")
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8409588637918651241L;

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
