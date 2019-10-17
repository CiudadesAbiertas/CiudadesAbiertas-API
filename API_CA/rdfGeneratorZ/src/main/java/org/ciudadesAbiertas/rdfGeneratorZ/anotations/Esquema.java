package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Esquema {
	
	public static final String AGENDA = "ACTIVIDADES";
	public static final String INTRA = "INTRA";
	public static final String NOTICIAS = "NOTICIAS";
	public static final String PARTICIPACION = "PARTICIPACION";
	public static final String MOVIL = "MOVIL";
	public static final String TURISMO = "TURISMO";
	public static final String TICKETING = "TICKETING";
	public static final String PERFILCONTRATANTE = "PERFILCONTRATANTE";
	public static final String OFERTA = "OFERTA";
	public static final String REGLIC = "REGLIC";
	public static final String SIU = "SIU";
	
	
	public static final String TMAGENDA = "transactionManagerActividades";
	public static final String TMINTRA = "transactionManagerIntra";
	public static final String TMNOTICIAS = "transactionManagerNoticias";
	public static final String TMPARTICIPACION = "transactionManagerParticipacion";
	public static final String TMMOVIL = "transactionManagerMovil";
	public static final String TMTURISMO = "transactionManagerTurismo";
	public static final String TMTICKETING = "transactionManagerTicketing";
	public static final String TMPERFILCONTRATANTE = "transactionManagerPerfilContratante";
	
	public static final String TMOFERTA = "transactionManagerOferta";
	public static final String TMREGLIC = "transactionManagerRegLic";
	public static final String TMSIU = "transactionManagerSiu";
	
	
	public String value();
}
