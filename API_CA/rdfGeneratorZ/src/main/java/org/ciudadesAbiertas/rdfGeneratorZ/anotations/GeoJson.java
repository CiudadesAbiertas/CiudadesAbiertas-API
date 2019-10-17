package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GeoJson {
	public String title();
	public String link();
	public String icon();
	public String description();
}
