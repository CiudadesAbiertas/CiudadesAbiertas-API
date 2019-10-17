package org.ciudadesAbiertas.rdfGeneratorZ;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.SRSPorDefecto;




public class Peticion {

	private  String uri;
	private  String clientId;
	private  String hmac;
	
	private  String metodoHttp;
	
	private  Method metodo;
	private  String queryString;
	private  String tipoAcceso;
	
	private  Class<?> clase;
	
	private Class<?> claseRetorno;
	
	
	private  Map<String, String> pathParams = new HashMap<String, String>();
	private  Map<String, String[]> queryParams = new HashMap<String, String[]>();
	
	private  Map<String, String> selectedFields = new HashMap<String, String>();
	
	private  String cuerpoPeticion;
	
	private  List<String> permisosEnSeccion;
	
	private  Formato formato;
	
	private Date lastModified;
	
	private String password;
	
	private String referer;
	
	private String ip;
	
	private int responseStatus = 200;
	
	private String srsName = null;
	
	private boolean resultsOnly = false;
	
	private boolean debug = false;
	
	private String contentType;
	
	private String tipoEtiquetado;
	
	private boolean cargandoEnVirtuoso = false;
	
	private boolean commiteadaEnMetodo = false;
	
	public Class<?> getClase() {
		return clase;
	}
	public void setClase(Class<?> clase) {
		this.clase = clase;
	}
	
	public Class<?> getClaseRetorno() {
		return claseRetorno;
	}
	public void setClaseRetorno(Class<?> claseRetorno) {
		this.claseRetorno = claseRetorno;
	}
	public Method getMetodo() {
		return metodo;
	}
	public void setMetodo(Method metodo) {
		this.metodo = metodo;
	}
	public Map<String, String> getPathParams() {
		return pathParams;
	}
	public void setPathParams(Map<String, String> pathParams) {
		this.pathParams = pathParams;
	}
	public Map<String, String[]> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String[]> queryParams) {
		this.queryParams = queryParams;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	
	public void setCuerpoPeticion(String cuerpoPeticion) {
		this.cuerpoPeticion = cuerpoPeticion;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public List<String> getPermisosEnSeccion() {
		return permisosEnSeccion == null ? new ArrayList<String>() : permisosEnSeccion;
	}
	public void setPermisosEnSeccion(List<String> permisosEnSeccion) {
		this.permisosEnSeccion = permisosEnSeccion;
	}
	public String getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getMetodoHttp() {
		return metodoHttp;
	}
	public void setMetodoHttp(String metodoHttp) {
		this.metodoHttp = metodoHttp;
	}
	public Formato getFormato() {
		return formato;
	}
	public void setFormato(Formato formato) {
		this.formato = formato;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public Map<String, String> getSelectedFields() {
		return selectedFields;
	}



	

	private String necesitaBarra(String uri) {
		if (uri.indexOf(".") > 0) {
			return uri;
		} else if (uri.lastIndexOf("/") != uri.length()) {
			return uri + "/";
		} else {
			return uri;
		}
	}
	
	
	public boolean quiereVerCampo(String prefijo, String field, Map<String, String> selectedFields) {
		if ((selectedFields.size() == 0 || selectedFields.get(prefijo + field) != null) && !"pathInterno".equals(field)) {
			return true;
		} else {
			if (prefijo != null && prefijo.length() > 0 && selectedFields.get(prefijo.substring(0, prefijo.lastIndexOf("."))) != null) {
				return true;
			}
			return false;
		}
	}
	
	public void establecerLastModified(Object object) {
		try {
			Field field = object.getClass().getDeclaredField("lastUpdated");
			field.setAccessible(true);
			if (field.get(object) != null && (this.getLastModified() == null || this.getLastModified().before((Date) field.get(object)))) {
				this.setLastModified((Date) field.get(object));
			} else {
				field = object.getClass().getDeclaredField("creationDate");
				field.setAccessible(true);
				if (field.get(object) != null && (this.getLastModified() == null || this.getLastModified().before((Date) field.get(object)))) {
					this.setLastModified((Date) field.get(object));
				}
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		
		
	}
	

	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public boolean isResultsOnly() {
		return resultsOnly;
	}
	public void setResultsOnly(boolean resultsOnly) {
		this.resultsOnly = resultsOnly;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getPathInformes(String informe) {
//		 FIXME path para informes
//		String resource=(new ApiDocs().getClass().getResource("ApiDocs.class")).getPath();
//		resource = resource.substring(0, resource.indexOf("api/rest"));
//		return resource + "informes/" + informe;
		return null;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public boolean isCommiteadaEnMetodo() {
		return commiteadaEnMetodo;
	}
	public void setCommiteadaEnMetodo(boolean commiteadaEnMetodo) {
		this.commiteadaEnMetodo = commiteadaEnMetodo;
	}
	public boolean isCargandoEnVirtuoso() {
		return cargandoEnVirtuoso;
	}
	public void setCargandoEnVirtuoso(boolean cargandoEnVirtuoso) {
		this.cargandoEnVirtuoso = cargandoEnVirtuoso;
	}
	public String getTipoEtiquetado() {
		return tipoEtiquetado;
	}
	public void setTipoEtiquetado(String tipoEtiquetado) {
		this.tipoEtiquetado = tipoEtiquetado;
	}

	public String getSrsName() {
		if (this.srsName == null) {
			String srs = CheckeoParametros.SRSUTM30N;
			if (this.getMetodo().getDeclaringClass().isAnnotationPresent(SRSPorDefecto.class)) {
				srs = this.getMetodo().getDeclaringClass().getAnnotation(SRSPorDefecto.class).value();
			}
			return this.getQueryParams().get(CheckeoParametros.PARAMSRS) == null 
					? srs
					: (this.getQueryParams().get(CheckeoParametros.PARAMSRS)[0].equals(CheckeoParametros.SRSWGS84) 
							? CheckeoParametros.SRSWGS84
							: (this.getQueryParams().get(CheckeoParametros.PARAMSRS)[0].equals(CheckeoParametros.SRSETRS89) ?
								CheckeoParametros.SRSETRS89 : CheckeoParametros.SRSUTM30N));
		} else {
			return srsName; 
		}
	}
	
	
	public boolean puedeVerCampoEnSeccion(Field field, List<String> permisosEnSecc, Method metodoRespuesta) {
		return true;
	}
	
}
