package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RdfExternalURI {
	
	
	String inicioURI() default "";
	String finURI() default "";
	int urifyLevel() default 0;
	boolean capitalize() default false;
	String propiedad() default "";
	String tipo() default "";
	String separador() default "";
	
	
}
