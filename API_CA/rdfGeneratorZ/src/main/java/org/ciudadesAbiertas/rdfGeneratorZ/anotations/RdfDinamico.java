package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RdfDinamico {
	String inicioURI() default "";
	String finURI() default "";	
	String propiedad() default "";
}
