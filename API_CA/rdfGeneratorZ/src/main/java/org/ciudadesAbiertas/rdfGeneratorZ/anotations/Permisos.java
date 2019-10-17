package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permisos {
	
	public static final String DET = "DET"; // Ver el registro
	public static final String DEL = "DEL"; // Borrar el registro
	public static final String MOD = "MOD"; // Modificar el registro
	public static final String NEW = "NEW"; // Crear registro
	public static final String PUB = "PUB"; // Publicar el registro
	
	public static final String OPEN = "OPEN"; // No se utiliza
	
	public static final String DELMULTIPLE = "DELMULTIPLE"; // Borrar varios registros
	public static final String MODMULTIPLE = "MODMULTIPLE"; // Modificar varios registro
	
	public static final String DOC = "DOC";
	public static final String ESTADISTICA = "ESTADISTICA";
	
	public static final String LOADVIRTUOSO = "LOADVIRTUOSO";
	
	public static final String ADMIN = "ADMIN"; // Operaciones de administrador
	
	public static final String ADMINOPERADOR = "ADMINOPERADOR"; // Operaciones de administrador
	
	public static final String OPERADOR = "OPERADOR";
	public static final String ANSWERREQUESTED = "ANSWERREQUESTED";
	
	public static final String CONTESTAR = "CONTESTAR";
	
	public static final String SENDINSPECTOR = "SENDINSPECTOR";
	public static final String INSPECTOR = "INSPECTOR";
	
	public static final String ZONAINSPECCION = "ZONAINSPECCION";
	
	public static final String CERRARSINCONTESTAR = "CERRARSINCONTESTAR";
	
	public static final String CATEGORY = "CATEGORY";
	
	public static final String CSV = "CSV";
	
	public static final String COMENTARIO = "COMENTARIO";
	
	public static final String LIDER = "LIDER";

	public static final String VAL = "VAL"; // Validación

    public String value();

}
