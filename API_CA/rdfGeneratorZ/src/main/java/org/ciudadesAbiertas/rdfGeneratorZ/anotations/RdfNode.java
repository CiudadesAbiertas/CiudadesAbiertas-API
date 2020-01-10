package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RdfNode {
	String inicioURI() default "";
	String valorURI() default "";	
	String finURI() default "";	
	String propiedad() default "";
	String nodoType() default "";
	String nodoPropiedad() default "";
	String nodoPropiedadTipo() default "";
}
