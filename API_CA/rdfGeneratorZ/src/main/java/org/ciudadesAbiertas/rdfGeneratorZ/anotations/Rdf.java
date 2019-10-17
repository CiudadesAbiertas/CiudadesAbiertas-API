package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Rdf {

	String uri() default "";
	String prefijo() default "";
	
	String contexto() default "";
	String propiedad() default "";

	String typeURI() default "";
	

	

}
