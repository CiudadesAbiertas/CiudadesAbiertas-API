package org.ciudadesAbiertas.rdfGeneratorZ;

import java.lang.reflect.Field;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import org.ciudadesAbiertas.rdfGeneratorZ.exception.FormatoNoSoportadoException;
import org.ciudadesAbiertas.rdfGeneratorZ.exception.InvalidImplementationException;
import org.ciudadesAbiertas.rdfGeneratorZ.geo.Geometria;





public interface TransformadorGenerico  {
	
	public String getSeparador();
	
	public String getInicioObjeto(String nombreObjeto);
	
	public String getFinObjeto(String nombreObjeto);
	
	public String getInicioCampoObjeto(String nombreCampo);
	
	public String getInicioArray();
	
	public String getInicioArray(String nombreCampo);
	
	public String getFinArray(String nombreCampo);
	
	public String getFinCampoObjeto(String nombreCampo);
	
	public String escribirValorCampo(String nombreCampo, Object valor, boolean anotacionPresente, String formato);
	
	public void enviarError(HttpServletResponse response, int statusCode, String mensaje, boolean peticionSoloHead);

	public Object pasarAObjeto(String valor, boolean esArray, Class<?>... type) throws FormatoNoSoportadoException, InstantiationException, IllegalAccessException;

	public String getMensajeErrorValidacion(Set<ConstraintViolation<Object>> constraintViolations);

	public String getMensajeErrorConstraint(Set<ConstraintViolation<?>> constraintViolations);
	
	public String getInicioCampoObjetoNoListado(String nombreCampo);
	
	public String getFinCampoObjetoNoListado(String nombreCampo);

	public void transformarObjeto(StringBuilder respuesta, Object retorno, Peticion peticion, boolean primero, String prefijo) throws InvalidImplementationException;

	public String getIDPrefijo();

	public String transformarGeometria(Geometria valor, Peticion peticion);

	public String getInicioCampoObjetoConSub(String key);

	public String getFinCampoObjetoConSub(String key);

	public String getInicioCampo(String name);

	public String escribirValor(String name, Object object, boolean anotacionPresente, String formato);

	public String escribirValorObjecto(String key, String string, boolean anotacionPresente, String formato);

}
