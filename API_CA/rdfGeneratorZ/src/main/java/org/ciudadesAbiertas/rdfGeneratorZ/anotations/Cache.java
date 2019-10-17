package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
	
	public static final String DEFAULT_CACHE_NAME = "paginas";
	
	public static String DURACION_1MIN = "1minuto";
	public static String DURACION_5MIN = "5minuto";
	public static String DURACION_30MIN = "30minutos";
	public static String DURACION_1DIA = "1dia";
	
	public String value();
}
