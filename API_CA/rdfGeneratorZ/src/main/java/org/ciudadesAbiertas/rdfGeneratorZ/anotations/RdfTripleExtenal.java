package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RdfTripleExtenal {	
	
	String sujetoInicioURI() default "";
	String sujetoFinURI() default "";
	
	String propiedadURI() default "";
	
	String objetoInicioURI() default "";
	String objetoFinURI() default "";	
	
}
