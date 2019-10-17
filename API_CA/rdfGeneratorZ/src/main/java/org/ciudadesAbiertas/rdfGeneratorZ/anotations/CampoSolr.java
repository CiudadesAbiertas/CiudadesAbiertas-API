package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CampoSolr {

	String value() default "";
	
	
}