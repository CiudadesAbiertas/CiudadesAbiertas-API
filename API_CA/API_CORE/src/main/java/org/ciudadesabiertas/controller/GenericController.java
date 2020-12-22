/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.config.multipe.MultipleConf;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.NotFoundException;
import org.ciudadesabiertas.model.IGeoModelXY;
import org.ciudadesabiertas.model.ICallejero;
import org.ciudadesabiertas.model.IGeoModelGeometry;
import org.ciudadesabiertas.model.ITramoIncidencia;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.CoordinateTransformer;
import org.ciudadesabiertas.utils.CubeQuerySearch;
import org.ciudadesabiertas.utils.DatasetSearch;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.GroupBySearch;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
public class GenericController<T> {

	

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(GenericController.class);

	private int numPage = Constants.defaultPage;
	private int numPageSize = StartVariables.defaultPageSize;
	
	public static final String VERSION_1 = "v1/";
	
	
	//Sin Geolocalización
	public static final String NO_HAY_SRID = "";
	
	@Autowired
	protected MultipleConf mConf;

	@Autowired
	protected Environment env;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public static boolean activeFK = StartVariables.activeFK;
	
	
	/**
	 * Para generar el registro HTML
	 * @param mv
	 * @param request
	 * @param srId
	 * @param id
	 * @param model_view_id
	 * @return
	 */
	public ModelAndView recordHTML(ModelAndView mv,HttpServletRequest request,String srId, String id, String model_view_id) {
		
		log.info("[recordHTML]");
		String referer = request.getHeader(Constants.HEADER_REFERER);
		if (referer==null)
		{
			mv.addObject("referer", "");
		}else {
			mv.addObject("referer", referer);
			
		}
		
		String ajaxURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, Constants.EXT_JSON);
		
		String tomcatHost=request.getServerName()+":"+request.getServerPort();
		String actualHost=StartVariables.serverPort;			
		
		String tomcatContext = applicationContext.getApplicationName();
		String newContext=StartVariables.context;
		
		if (tomcatHost.equals(actualHost)==false)
		{
		    ajaxURL=ajaxURL.replace(tomcatHost, actualHost);	
		}
		if (Util.validValue(newContext))
		{   
		    ajaxURL=ajaxURL.replace(tomcatContext, newContext);
		}
		if (StartVariables.uriBase.startsWith("https"))
		{
		    ajaxURL=ajaxURL.replace("http://", "https://");
		    ajaxURL=ajaxURL.replace("http://", "https://");	
		}
						
		if (Util.validValue(srId))
		{
			if (CoordinateTransformer.isValidSrId(srId))
			{				
				ajaxURL+="?"+Constants.SRID+"="+srId;
			}
		}

		mv.addObject(Constants.IDENTIFICADOR, id);
		mv.addObject("ajaxURL", ajaxURL);

		mv.setViewName(model_view_id);
		
		return mv;
	}
	
	/**
	 * 
	 * @param mv
	 * @param request
	 * @param srId
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @param params
	 * @param model_view_list
	 * @return
	 */
	public ModelAndView listHTML(ModelAndView mv,HttpServletRequest request,String srId,String page,String pageSize, String sort, String params,String model_view_list  ) {		
		
		
		log.info("[listHTML]");
		String theURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, Constants.EXT_JSON);
		String recordURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, "");
		
		String tomcatHost=request.getServerName()+":"+request.getServerPort();
		String actualHost=StartVariables.serverPort;			
		
		String tomcatContext = applicationContext.getApplicationName();
		String newContext=StartVariables.context;
		
		if (tomcatHost.equals(actualHost)==false)
		{		   
		    theURL=theURL.replace(tomcatHost, actualHost);
		    recordURL=recordURL.replace(tomcatHost, actualHost);
		   
		    theURL=theURL.replace(tomcatHost, actualHost);
		   
		}
		if (Util.validValue(newContext))
		{   
		    
		    theURL=theURL.replace(tomcatContext, newContext);
		    recordURL=recordURL.replace(tomcatContext, newContext);	
		}
		if (StartVariables.uriBase.startsWith("https"))
		{
		    theURL=theURL.replace("http://", "https://");
		    recordURL=recordURL.replace("http://", "https://");	
		}
		
		if (Util.validValue(srId))
		{
			if (CoordinateTransformer.isValidSrId(srId))
			{				
				params+="&"+Constants.SRID+"="+srId;
			}
		}
		
		if (Util.validValue(page)) {
			mv.addObject(Constants.PAGE, page);
		}
		
		if (Util.validValue(pageSize)) {			
			mv.addObject(Constants.PAGESIZE, pageSize);
		}
		
		if (Util.validValue(sort)) {			
			mv.addObject(Constants.SORT, sort);
		}
				

		if ("?".equals(params)) {
			params="";
		}else {
			params=params.replace("?&", "?");
		}
		
		mv.addObject("theURL", theURL+params);
		mv.addObject("recordURL", recordURL);
		mv.setViewName(model_view_list);
		return mv;
	}
	

	
		

	/**
	 * Metodo generico de los Controller para realizar el Alta de los objetos en la API
	 * @param obj  Objeto a dar de alta
	 * @param nameController Nombre del controlador del que procedemos, para tratamiento de Errores
	 * @param operacion llamada de la que venimos.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return ResponseEntity
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> add(T obj , String nameController,String operacion, DatasetService<T> dsService, String key)
	{
		

		log.debug("[parmam][dato:" + obj + "],"
				+"[nameController:" + nameController + "],[operacion:" + operacion +"]");
		//VBLES		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		boolean error=false;		
		Result<Object> resultObj = new Result<Object>();
		ResultError errorApi = new ResultError();		
		
		if (!error) {					
			
			List<String> errores = ((RDFModel) obj).validate();
			if (!errores.isEmpty()) {
				log.error("[add][" + operacion + "] [ERROR Validator:"+errores+"]");
				error = true;
				errorApi.setStatus(400);
				errorApi.setClassName(nameController);
				errorApi.setMethod("add");
				
				errorApi.setErrors(errores);
			}
			if (!error) {
				try {
					//Find id
					T objP= dsService.findById(key,(Class<T>) obj.getClass(), ((RDFModel) obj).getId());
					
					if (objP==null) {
						//POST
						dsService.save(key,obj);
					
						// Casteo a objetos para el parser
						List<T> records = new ArrayList<T>();
					
						records.add(obj);
					
						resultObj.setPage(1);
						resultObj.setPageSize(1);
						resultObj.setRecords((List<Object>) records);
						resultObj.setTotalRecords(1);
						resultObj.setStatus(201);
						
						responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.valueOf(resultObj.getStatus()));
						
					}else {
						error = true;
						//Conficto 409
						errorApi.setStatus(409);
						errorApi.setClassName(nameController);
						errorApi.setMethod("add");
						
						errorApi.setError("Error trying to Add. [id:"+((RDFModel) obj).getId()+"]  Found in BBDD.");											
					}
					
				} catch (Exception e)
				{
					responseEntity=ExceptionUtil.checkException(e);
				}	
				
			}
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}
		
		return responseEntity;

	}

	
	/**
	 * Metodo generico de los Controller para realizar el Alta de los objetos en la API
	 * @param obj  Objeto a dar de alta
	 * @param nameController Nombre del controlador del que procedemos, para tratamiento de Errores
	 * @param operacion llamada de la que venimos.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return ResponseEntity
	 */
	public  ResponseEntity<?> add(T obj , String nameController,String operacion, DatasetService<T> dsService, String key, List<String> erroresFK)
	{
		
		
		log.debug("[parmam][dato:" + obj + "],"
				+"[nameController:" + nameController + "],[operacion:" + operacion +"][activeFK:"+activeFK+"]");
		//VBLES		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);			
		ResultError errorApi = new ResultError();		
		
		//TODO control para las FK, pero se debe desactivar cuando se decida que no hay FK, necesario controlar PDTE
		//FK
		if (activeFK && erroresFK!=null && !erroresFK.isEmpty()) {
			log.debug("[add][" + operacion + "] [ERROR Validator:"+erroresFK+"]");
			errorApi.setStatus(409);
			errorApi.setClassName(nameController);
			errorApi.setMethod("add");
			errorApi.setErrors(erroresFK);
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			return responseEntity;
		}else {
			return add(obj, nameController, operacion, dsService, key);
		}
		
	}


	/**
	 * Metodo generico de los Controller para realizar la modificación de los objetos en la API
	 * @param id
	 * @param obj  Objeto a modificar
	 * @param nameController Nombre del controlador del que procedemos, para tratamiento de Errores
	 * @param operacion llamada de la que venimos.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return ResponseEntity
	 */
	public  ResponseEntity<?> update(
			 String id, T obj, String nameController,String operacion, DatasetService<T> dsService, String key, List<String> erroresFK)	
	{
		
		log.info("[update][" + operacion + "]");
		
		log.debug("[update][" + operacion + "] [nameController:" + nameController + "] [key:" + key + "] [activeFK:"+activeFK+"]");

		if (activeFK && (erroresFK.size()>0)) {
			log.debug("[update][" + operacion + "] [ERROR Validator:"+erroresFK+"]");
			ResultError errorApi = new ResultError ();
			errorApi.setStatus(409);
			errorApi.setClassName(nameController);
			errorApi.setMethod("update");				
			errorApi.setError(erroresFK.toString());	
			ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.CONFLICT);	
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			return responseEntity;
		}
		else {
			return update(id, obj, nameController, operacion, dsService, key);
		}
	}
	
	/**
	 * Metodo generico de los Controller para realizar la modificación de los objetos en la API
	 * @param id
	 * @param obj  Objeto a modificar
	 * @param nameController Nombre del controlador del que procedemos, para tratamiento de Errores
	 * @param operacion llamada de la que venimos.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return ResponseEntity
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> update(
			 String id, T obj, String nameController,String operacion, DatasetService<T> dsService, String key)			 
	{
		log.info("[update][" + operacion + "]");

		log.debug("[parmam][id:" + id + "] [dato:" + obj + "] [nameController:" + nameController + "] [key:" + key + "]");
		
		id=Util.decodeURL(id);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);	
		ResultError errorApi = new ResultError ();
		Result<Object> resultObj = new Result<Object>();
		boolean error=false;	
		
		if (Util.validValue(id)) {
						
			List<String> errores = ((RDFModel) obj).validate();
			//Necesitamos Generar el error 400
			if (errores.isEmpty()) {
				try {
					T objP= dsService.findById(key,(Class<T>) obj.getClass(), id);
			
					if (objP!=null ) {
					
						String ikey=((RDFModel) objP).getIkey();
						//NO Guardamos el objP sino el obj
						//objP=  (T) obj.getClass().getConstructor((Class<T>[]) obj);
						((RDFModel) obj).setId(id);
						((RDFModel) obj).setIkey(ikey);
						
						dsService.update(key,obj);
						
						// Casteo a objetos para el parser
						List<Object> records = new ArrayList<Object>();
						records.add(obj);
						
						resultObj.setPage(1);
						resultObj.setPageSize(1);
						resultObj.setRecords(records);
						resultObj.setTotalRecords(1);
						resultObj.setStatus(200);
						
						responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.OK);
						
					}else {
						//Fijamos ERROR		
						
						error = true;
						errorApi.setStatus(404);
						errorApi.setClassName(nameController);
						errorApi.setMethod("update");	
						errorApi.setErrors(errores);
						if (objP==null) {
							errorApi.setError("Error trying to modify.  [id:"+id+"] Not Found in BBDD");
						}
						
					}
				
				} catch (Exception e)
				{
					responseEntity=ExceptionUtil.checkException(e);
				}	
				
			}else {

				log.error("[update][" + operacion + "] [ERROR: Not Valid (Obj:"+obj+"]");				
				error = true;
				errorApi.setStatus(400);
				errorApi.setClassName(nameController);
				errorApi.setMethod("update");				
				errorApi.setErrors(errores);
				log.debug("[update][ERRORAPI:"+errorApi+"]");
				
			}//Fin Else cuando se valida PuntoInteresTuristico
		}//NO se ha introducido un id Valido
		else {
			error = true;
			errorApi.setStatus(404);
			errorApi.setClassName(nameController);
			errorApi.setMethod("update");				
			errorApi.setError("id is not valid [id:"+id+"]");			
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}
	
		return responseEntity;
	}
	
	/**
	 * Metodo para el borrado de objetos
	 * @param id
	 * @param obj
	 * @param dsService
	 * @param nameController
	 * @param operacion
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> delete(		 
			String id, 
			T obj, String nameController, String operacion, DatasetService<T> dsService, String key)
	{

		log.info("[delete][" + operacion + "]");

		log.debug("[parmam][id:" + id + "] [nameController:" + nameController + "] [key:" + key + "]");
		
		id=Util.decodeURL(id);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);		
		ResultError errorApi = new ResultError ();
		Result<Object> resultObj = new Result<Object>();
		boolean error=false;	
		
		
		if (Util.validValue(id)) {
		
			try {
				T objP= dsService.findById(key,(Class<T>) obj.getClass(), id);				
						
				if (objP==null) {
					//Fijamos ERROR	
					error = true;
					errorApi.setStatus(404);
					errorApi.setClassName(nameController);
					errorApi.setMethod("delete");	
					errorApi.setError("Error trying to delete. [id:"+id+"] Not Found in BBDD");
										
				}else {
										
					dsService.delete(key,objP);
					List<Object> records = new ArrayList<Object>();
					records.add(objP);
					
					resultObj.setPage(1);
					resultObj.setPageSize(1);
					resultObj.setRecords(records);
					resultObj.setTotalRecords(1);
					resultObj.setStatus(200);
					responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.valueOf(resultObj.getStatus()));
		
				}
				
			} catch (Exception e)
			{
				responseEntity=ExceptionUtil.checkException(e);
			}	
		}else {
			error = true;
			errorApi.setStatus(404);
			errorApi.setClassName(nameController);
			errorApi.setMethod("delete");				
			errorApi.setError("id is not valid [id:"+id+"]");			
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}

		return responseEntity;
	}
	
	/**
	 * Metodo para el borrado de objetos
	 * @param id
	 * @param obj
	 * @param dsService
	 * @param nameController
	 * @param operacion
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return
	 */
	public  ResponseEntity<?> delete(		 
			String id, 
			T obj, String nameController, String operacion, DatasetService<T> dsService, String key, List<String> erroresFK)
	{

		log.info("[delete][" + operacion + "]  [activeFK:"+activeFK+"]");

		log.debug("[parmam][" + operacion + "] [id:" + id + "] [nameController:" + nameController + "] [key:" + key + "] [activeFK:"+activeFK+"]");
		
		id=Util.decodeURL(id);		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);		
		ResultError errorApi = new ResultError ();
		
		//TODO control para las FK, pero se debe desactivar cuando se decida que no hay FK, necesario controlar PDTE
		//FK
		if (activeFK && erroresFK!=null && !erroresFK.isEmpty()) {
			log.debug("[delete][" + operacion + "] [ERROR Validator:"+erroresFK+"]");
			errorApi.setStatus(409);
			errorApi.setClassName(nameController);
			errorApi.setMethod("delete");
			errorApi.setErrors(erroresFK);
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			return responseEntity;
		}else {
			return delete(id, obj, nameController, operacion, dsService, key);
		}
		
	}
	
	/**
	 * Este metodo provoca un error al generar fichas de CSV (muy común...)
	 */
	@Deprecated 
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> record(HttpServletRequest request, 
									String id, 
									T obj, 
									String srId, 
									String nameController,
									String operacion,
									DatasetService<T> dsService,
									String key)
	{

		log.info("[record][" + operacion + "]");

		log.debug("[parmam][id:" + id + "]");
		
		Result<Object> resultObj = new Result<Object>();
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}
		
		ResponseEntity<?> responseEntity= null;
		
		if (Util.validValue(srId))
		{
			if (!CoordinateTransformer.isValidSrId(srId))
			{				
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong System reference identificator"));
				return responseEntity;
			}
		}
		
	
		List<T> listado=new ArrayList<T>();
		
	
		try {
			T objP= dsService.findById(key,(Class<T>) obj.getClass(), id);
						
			if (objP instanceof IGeoModelXY)
			{
				Util.generaCoordenadasAll(srId, (IGeoModelXY)objP);
			}			
			if (objP!=null)
			{	
				listado.add(objP);
				resultObj.setPage(1);
				resultObj.setPageRecords(1);
				resultObj.setPageSize(1);
				resultObj.setTotalRecords(1);
				resultObj.setRecords((List<Object>) listado);
				resultObj.setStatus(200);
				//MD5
				resultObj.setContentMD5(Util.generateHash( obj.toString() ));
				
				//Cabeceras no se estaban incluyendo
				HttpHeaders headers = Util.extractHeaders(resultObj);
				responseEntity = new ResponseEntity<Object>(resultObj,headers, HttpStatus.OK);
			}else {
				//Fijamos ERROR	
				ResultError errorApi = new ResultError ();
				errorApi.setStatus(404);
				errorApi.setClassName(nameController);
				errorApi.setMethod("record");	
				errorApi.setError("id is not valid [id:"+id+"] Not Found in BBDD");					
				responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			}		
			
			
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		} 

		return responseEntity;		
	}
	
	
	/**
	 * Metodo para obtener las fichas de los modulos
	 * @param request
	 * @param id
	 * @param obj
	 * @param resultObj
	 * @param srId
	 * @param nameController
	 * @param operacion
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> record(HttpServletRequest request, 
									String id, 
									T obj, 
									T resultObj,
									String srId, 
									String nameController,
									String operacion,
									DatasetService<T> dsService,
									String key)
	{

		log.info("[record][" + operacion + "]");

		log.debug("[parmam][id:" + id + "][nameController:" + nameController + "] [operacion:" + operacion + "]");
		
		id=Util.decodeURL(id);
		
		//Result<Object> resultObj = new Result<Object>();
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}
		
		ResponseEntity<?> responseEntity= null;
		
		if (Util.validValue(srId))
		{
			if (!CoordinateTransformer.isValidSrId(srId))
			{				
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong System reference identificator"));
				return responseEntity;
			}
		}
		
	
		List<T> listado=new ArrayList<T>();
		
		String selfRequest = Util.generateSelfRequest(request);		
		((Result<T>) resultObj).setSelf(selfRequest);
	
		try {
			T objP= dsService.findById(key,(Class<T>) obj.getClass(), id);
						
			if (objP instanceof IGeoModelXY)
			{
				Util.generaCoordenadasAll(srId, (IGeoModelXY)objP);
			}else if (objP instanceof IGeoModelGeometry)
			{
			  	Util.generaCoordenadasAll(srId, (IGeoModelGeometry)objP);
			}
			
			
			
			if (objP!=null)
			{	
				listado.add(objP);
				//CMG: Correccion rapida para los conjuntos de datos de Padron - 07/07/2020
				Util.generaDataCubeInfo(listado);
				
				((Result<?>) resultObj).setPage(1);
				((Result<?>) resultObj).setPageRecords(1);
				((Result<?>) resultObj).setPageSize(1);
				((Result<?>) resultObj).setTotalRecords(1);
				((Result<T>) resultObj).setRecords((List<T>) listado);
				((Result<T>) resultObj).setStatus(200);
				//MD5
				((Result<?>) resultObj).setContentMD5(Util.generateHash( obj.toString() ));
				
				
				//Cabeceras no se estaban incluyendo
				HttpHeaders headers = Util.extractHeaders((Result<?>) resultObj);
				responseEntity = new ResponseEntity<Object>(resultObj,headers, HttpStatus.OK);
			}else {
				
				if (Util.isSemanticPetition(request))
				{
					((Result<?>) resultObj).setPage(0);
					((Result<?>) resultObj).setPageRecords(0);
					((Result<?>) resultObj).setPageSize(0);
					((Result<?>) resultObj).setTotalRecords(0);
					((Result<T>) resultObj).setRecords(new ArrayList<T>());
					((Result<T>) resultObj).setStatus(404);
					
					//Cabeceras no se estaban incluyendo
					HttpHeaders headers = Util.extractHeaders((Result<?>) resultObj);
					responseEntity = new ResponseEntity<Object>(resultObj,headers, HttpStatus.NOT_FOUND);
				}
				else
				{
					//Fijamos ERROR	
					ResultError errorApi = new ResultError ();
					errorApi.setStatus(404);
					errorApi.setClassName(nameController);
					errorApi.setMethod("record");	
					errorApi.setError("id is not valid [id:"+id+"] Not Found in BBDD");					
					responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
					throw new NotFoundException("identifier '"+id+"'");
				}
			}		
			
			
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		} 

		return responseEntity;

	}
	
	
	/**
	 * Metodo para obtener las fichas de los modulos a partir de su título
	 * @param request
	 * @param id
	 * @param obj
	 * @param srId
	 * @param nameController
	 * @param operacion
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @param key nombre del fichero si se aplica configuracion propia
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> recordByIndentifier(HttpServletRequest request, 
									String identifier, 
									T obj, 
									T resultObj,
									String srId, 
									String nameController,
									String operacion,
									DatasetService<T> dsService,
									String key)
	{

		log.info("[record][" + operacion + "]");

		log.debug("[parmam][identifier:" + identifier + "]");
		
		identifier=Util.decodeURL(identifier);
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}
		
		ResponseEntity<?> responseEntity= null;
		
		if (Util.validValue(srId))
		{
			if (!CoordinateTransformer.isValidSrId(srId))
			{				
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong System reference identificator"));
				return responseEntity;
			}
		}
		
	
		List<T> listado=new ArrayList<T>();
		
	
		try {
			T objP= dsService.findByIdentifier(key,(Class<T>) obj.getClass(), identifier);
						
			if (objP instanceof IGeoModelXY)
			{
				Util.generaCoordenadasAll(srId, (IGeoModelXY)objP);
			}			
			if (objP!=null)
			{	
				listado.add(objP);
				((Result<?>) resultObj).setPage(1);
				((Result<?>) resultObj).setPageRecords(1);
				((Result<?>) resultObj).setPageSize(1);
				((Result<?>) resultObj).setTotalRecords(1);
				((Result<T>) resultObj).setRecords((List<T>) listado);
				((Result<T>) resultObj).setStatus(200);
				//MD5
				((Result<?>) resultObj).setContentMD5(Util.generateHash( obj.toString() ));
				
				
				
				//Cabeceras no se estaban incluyendo
				HttpHeaders headers = Util.extractHeaders((Result<?>) resultObj);
				responseEntity = new ResponseEntity<Object>(resultObj,headers, HttpStatus.OK);
			}else {
				//Fijamos ERROR	
				ResultError errorApi = new ResultError ();
				errorApi.setStatus(404);
				errorApi.setClassName(nameController);
				errorApi.setMethod("record");	
				errorApi.setError("title is not valid [identifier:"+identifier+"] Not Found in BBDD");					
				responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			}		
			
			
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		} 

		return responseEntity;

	}
	
	/**
	 * Metodo para realizar una transformación generica de un objeto no persistido.
	 * @param obj
	 * @param nameController
	 * @param operacion
	 * @return
	 */
	public ResponseEntity<?> transform(T obj,  String nameController, String operacion) {

		log.info("[transform]");

		ResponseEntity<?> responseEntity = null;
		List<T> listado = new ArrayList<T>();

		ResultError errorApi = new ResultError();
		Result<T> resultObj = new Result<T>();
		
		List<String> errores = ((RDFModel) obj).validate();

		if (errores.size() > 0) {
			log.error("[transform][" + operacion + "] [ERROR Validator:" + errores + "]");

			errorApi.setStatus(400);
			errorApi.setClassName(nameController);
			errorApi.setMethod("transform");

			errorApi.setErrors(errores);

			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		} else {
			listado.add(obj);

			resultObj.setRecords(listado);
			resultObj.setStatus(200);

			HttpHeaders headers = Util.extractHeaders(resultObj);

			responseEntity = new ResponseEntity<Object>(resultObj, headers, HttpStatus.OK);
		}

		return responseEntity;

	}
	
	/**
	 * Metodo para obtener el listado de cualquier controlador.
	 * @param request
	 * @param search Objeto con las propiedades de busqueda fijadas
	 * @param fields Campos que queremos que se muestren en las repuesta
	 * @param rsqlQ El campo q de la consulta RSQL
	 * @param page El campo page
	 * @param pageSize El campo pageSize
	 * @param sort Literales para la ordenación
	 * @param srId Campo para imlementar los diferentes formatos de geolocalización
	 * @param operacion campo con la llamada que se esta realizando
	 * @param objModel Campo con el objeto de persistencia (clase del paquete model) sobre la que se va actuar
	 * @param objResult Campo con el objeto resultado que se espera, es propia de cada modulo.
	 * @param availableFields Listado de campos disponibles en el Objeto Model
	 * @param key String con el key (nombre fichero a utilizar para cuando sea dependiente del módulo)
	 * @param visitor	Campo visitor que es necesario construir en el modulo propio ya que depende del model del mismo.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> list(HttpServletRequest request, T search, 
			 String fields, 
			 String rsqlQ, 
			 String page, 
			 String pageSize,
			 String sort,			
			 String srId,	
			 String operacion,
			 T objModel,
			 T objResult,
			 List<String> availableFields,
			 String key,
			 RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor,
			 DatasetService<T> dsService)
	{

		log.info("[list][" + operacion + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [sort:" + sort + "]");
				
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null){
			return negotiationResponseEntity;
		}
		
		//CMG CONTROL PARA LOS CAMPOS HORA
		String validarHora = ((RDFModel) search).validarParam();
		if (validarHora!=null) {
			BadRequestException e=new BadRequestException(validarHora);
			return ExceptionUtil.checkException(e);
		}
		
		//CMG: Verificacion de formato para las llamadas GEOS
		if (Util.isGeoLocationPetition(availableFields, request)) {
			//Validamos que tenga campos lat o long			
			boolean result = Util.contains("latitud", (ArrayList<String>) availableFields ) || 
					Util.contains("x", (ArrayList<String>) availableFields )	||
					Util.contains("hasGeometry", (ArrayList<String>) availableFields );
			if (!result) {
				BadRequestException e=new BadRequestException("Format not valid");
				return ExceptionUtil.checkException(e);
			}
		}
		//Fin Verificación
		
		//CMG: Proceso de Validación de Parametros ahora debemos pasar los campos por metodo ya que no seran igual para todos
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.add(Constants.FIELDS);
		allowedParams.add(Constants.RSQL_Q);
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.SORT);
		allowedParams.add(Constants.AJAX_PARAM);
		allowedParams.add(Constants.SRID);		
		
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}
		//FIN CONTROL
		
		//CMG Control del campo Sort
		ResponseEntity<?> responseErrorSort = Util.validateSort(sort, availableFields);
		if (responseErrorSort!=null) {
			return responseErrorSort;
		}
		//Fin del control
		
		//controlamos las coordenadas geo en RSQL
		
		if (Util.validValue(rsqlQ))
		{
			rsqlQ=rsqlQ.replace(Constants.XETRS89Fin, Constants.FinX);
			rsqlQ=rsqlQ.replace(Constants.YETRS89Fin, Constants.FinY);
			rsqlQ=rsqlQ.replace(Constants.XETRS89, Constants.X.toLowerCase());
			rsqlQ=rsqlQ.replace(Constants.YETRS89, Constants.Y.toLowerCase());
		}
		
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		
		
		try
		{
			setPagina(page, pageSize);
		}
		catch (NumberFormatException e)
		{
			log.error("Error parsing page numbers",e);
			responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong page information"));
			return responseEntity;
		}
		
		int numPage = getNumPage();
		int numPageSize = getNumPageSize();	

		if (Util.validValue(srId))
		{
			if (CoordinateTransformer.isValidSrId(srId)==false)
			{				
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong System reference identificator"));
				return responseEntity;
			}
		}

		List<Sort> orders = extractOrder(sort);
		
		List<String> fieldsQuery=new ArrayList<String>();
		if (Util.validValue(fields))
		{
			List<String> fieldsError=Util.checkMultiValuedParamInList(fields,availableFields);
			if (fieldsError.isEmpty())
			{
				//CMG 19-11-2020 Control de los campos field
				fieldsQuery=Util.controlFields(fields);
			}else {
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong field ["+fieldsError.get(0)+"] in fields param"));
				return responseEntity;
			}
		}		
		
		if (Util.validValue(rsqlQ)) {
		//Consulta RSQL			
			try
			{
				//Control para url
				if (rsqlQ.contains(Constants.FIELD_URL)) {										
					rsqlQ = Util.decodeURL(rsqlQ);	
				}
				
				
				Result<T> result =dsService.searchByRSQLQuery(visitor,key, rsqlQ, numPage, numPageSize, orders);				
				
				List<T> records = result.getRecords();				
				if (fieldsQuery.size()>0)
				{	
					for (int i=0;i<records.size();i++)					
					{
						records.set(i, ((RDFModel) objModel).cloneModel(records.get(i),fieldsQuery));						
					}
					
				}				

				responseEntity = guardarResult(result, srId, records, objResult, request)	;
				
			} catch (Exception e)
			{
				responseEntity=ExceptionUtil.checkException(e);
			} 
			
			
		}else {
		//Consulta estandar
			List<T> listado;
			try
			{
				listado = dsService.query(key,(Class<T>) objModel.getClass(),(DatasetSearch<T>) search, numPage, numPageSize,fieldsQuery, orders);														
				
				long total=dsService.rowcount(key,(Class<T>) objModel.getClass(),(DatasetSearch<T>) search);				
				
				responseEntity = guardarResult(srId, listado, total, objResult, request)	;				
						
				
			} catch (Exception e)
			{
				log.error("internal error",e);
				responseEntity=ExceptionUtil.checkException(e);
			}		
		}
		
		return responseEntity;
	}
	
	/**
	 * Metodo para obtener el listado geografico de cualquier controlador que soporte este tipo de busqueda.
	 * @param request
	 * @param search Objeto con las propiedades de busqueda fijadas
	 * @param fields Campos que queremos que se muestren en las repuesta
	 * @param meters Metros para establecer el radio de busqueda
	 * @param page El campo page
	 * @param pageSize El campo pageSize
	 * @param sort Literales para la ordenación
	 * @param srId Campo para imlementar los diferentes formatos de geolocalización
	 * @param operacion campo con la llamada que se esta realizando
	 * @param objModel Campo con el objeto de persistencia (clase del paquete model) sobre la que se va actuar
	 * @param objResult Campo con el objeto resultado que se espera, es propia de cada modulo.
	 * @param availableFields Listado de campos disponibles en el Objeto Model
	 * @param key String con el key (nombre fichero a utilizar para cuando sea dependiente del módulo)
	 * @param visitor	Campo visitor que es necesario construir en el modulo propio ya que depende del model del mismo.
	 * @param dsService DatasetService dependiente del modulo que lo invoque
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> geoList(
			 HttpServletRequest request, 
			 T search, 
			 String fields,
			 String meters,			 
			 String page, 
			 String pageSize,
			 String sort,
			 String srId,
			 String operacion,
			 T objModel,
			 T objResult,
			 List<String> availableFields,
			 String key,
			 DatasetService<T> dsService)
	{

		log.info("[geoList][" + operacion + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [srId:" + srId + "] [sort:" + sort + "]");
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null){
			return negotiationResponseEntity;
		}
		
		//CMG: Cambio el listado de parametros a validar
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.add(Constants.FIELDS);
		allowedParams.add(Constants.SORT);		
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.AJAX_PARAM);		
		allowedParams.add(Constants.XETRS89);
		allowedParams.add(Constants.YETRS89);
		allowedParams.add(Constants.METERS);
		allowedParams.add(Constants.SRID);
		
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}
		//FIN CMG
		
		//CMG Control del campo Sort
		ResponseEntity<?> responseErrorSort = Util.validateSort(sort, availableFields);
		if (responseErrorSort!=null) {
			return responseErrorSort;
		}
		//Fin del control
					
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		try
		{
			setPagina(page, pageSize);
			Integer.parseInt(meters);
		}
		catch (NumberFormatException e)
		{
			log.error("Error parsing meters or page numbers",e);
			responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong page information"));
			return responseEntity;
		}
		
		int numPage = getNumPage();
		int numPageSize = getNumPageSize();
		int numMeters= Integer.parseInt(meters);
		
		//CMG CONTROL PARA SRID
		if (Util.validValue(srId))
		{
			if (CoordinateTransformer.isValidSrId(srId)==false)
			{				
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong System reference identificator"));
				return responseEntity;
			}
		}

		//CMG: Incluimos el tratamiento especifico de GEO
		List<Sort> orders = extractOrder(sort);
		
		List<String> fieldsQuery=new ArrayList<String>();
		if (Util.validValue(fields))
		{
			List<String> fieldsError=Util.checkMultiValuedParamInList(fields,availableFields);
			if (fieldsError.isEmpty())
			{
				//CMG 19-11-2020 Control de los campos field
				fieldsQuery=Util.controlFields(fields);
			}else {
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong field ["+fieldsError.get(0)+"] in fields param"));
				return responseEntity;
			}
		}		
		
		
		List<T> listado;
		try
		{
			listado = dsService.geoQuery(key,(Class<T>) objModel.getClass(),numMeters,(DatasetSearch<T>) search, numPage, numPageSize,fieldsQuery, orders);														
			
			long total=dsService.geoRowcount(key,(Class<T>) objModel.getClass(),numMeters,(DatasetSearch<T>) search);				
			
			responseEntity = guardarResult(srId, listado, total, objResult, request)	;				
					
			
		} catch (Exception e)
		{
			log.error("internal error",e);
			responseEntity=ExceptionUtil.checkException(e);
		}		
		
		
		return responseEntity;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> groupBySearch(
			 HttpServletRequest request, 
			 GroupBySearch groupBySearch, 	 		 
			 String page, 
			 String pageSize,
			 String key,
			 String srId,
			 String operacion,
			 T objModel,
			 T objResult,			 
			 DatasetService<T> dsService) {
		
		log.info("[groupBySearch][operacion:" + operacion + "]");

		log.debug("[parmam][page:" + page + "] [pageSize:" + pageSize + "] [key:" + key + "] [srId:" + srId + "] [groupBySearch:" + groupBySearch + "] ");
		
		//CMG: Control para las peticiones semanticas en las Groupby no esta permitidas
		if (Util.isSemanticPetition(request)) {
			return ExceptionUtil.checkException(new BadRequestException("Format not Support"));
			
		}
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}		
		
	
		
		//CMG
		//Validación de parametros de entrada
		List<String> availableFields=Util.extractPropertiesFromBean(GroupBySearch.class);
		
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.add(Constants.FIELDS);		
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.AJAX_PARAM);		
										
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}	
		
		ResponseEntity<?> responseEntity= null;	
			
		setPaginaGroupBy(page,pageSize);
		
		
		if (objModel instanceof IGeoModelXY)
		{
			groupBySearch.checkCoordinatesETRS();
		}
		
		if (!Util.validValue(groupBySearch.getFields()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'fields' is required"));
			return responseEntity;
		}		
		
		if (!Util.validValue(groupBySearch.getGroup()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'group' is required"));
			return responseEntity;
		}

		List<T> records;
		try
		{
			//hacemos una copia por dentro del groupBySearch se modifica el where
			GroupBySearch copy=new GroupBySearch(groupBySearch);
			records = (List<T>) dsService.groupBySearch(key,(Class<T>) objModel.getClass(),groupBySearch, numPage, numPageSize);			
			long numtotalRecords=dsService.rowCountGroupBy(key,(Class<T>) objModel.getClass(),copy);
			
			responseEntity = guardarResult(srId, records, numtotalRecords, objResult, request);
			
					
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		}
		

		return responseEntity;
	}
	
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> distinctSearch(
			 HttpServletRequest request, 
			 DistinctSearch search, 	 		 
			 List<String> availableFields, 
			 String page, 
			 String pageSize,
			 String key,
			 String srId,
			 String operacion,
			 T objModel,
			 T objResult,			 
			 DatasetService<T> dsService) {
		
		log.info("[distinctSearch][operacion:" + operacion + "]");

		log.debug("[parmam][page:" + page + "] [pageSize:" + pageSize + "] [key:" + key + "] [srId:" + srId + "] [search:" + search + "] ");
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}
		//CMG:  PARAM
		//Validación de parametros de entrada
		List<String> availableFieldsSearch=Util.extractPropertiesFromBean(DistinctSearch.class);
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.add(Constants.FIELDS);		
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.AJAX_PARAM);
		
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFieldsSearch,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}
		//FIN CMG
		
		ResponseEntity<?> responseEntity= null;
		
		setPagina(page, pageSize);
		
		
		if (!Util.validValue(search.getField()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'field' is required"));
			return responseEntity;
		}
		
		
		if (availableFields.contains(search.getField())==false)
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("there isn't field "+search.getField()+" in "+objModel.getClass().getSimpleName()));
			return responseEntity;
		}	
		
		
		if (search.getField().equals(Constants.XETRS89))
		{
			search.setField(Constants.X.toLowerCase());
		}
		if (search.getField().equals(Constants.YETRS89))
		{
			search.setField(Constants.Y.toLowerCase());
		}
	
		List<T> records;
		try
		{			
			records = (List<T>) dsService.distinctSearch(key,(Class<T>) objModel.getClass(),search, numPage, numPageSize);
			long numtotalRecords=dsService.rowCountDistinct(key,(Class<T>) objModel.getClass(),search);
			
			responseEntity = guardarResult(srId, records, numtotalRecords, objResult, request);
			
					
		} catch (Exception e)
		{
			log.error("Error in distinct queries",e);
			responseEntity=ExceptionUtil.checkException(e);
		}
		

		return responseEntity;
	}
	
	/**
	 * Metodo para guardar los resultados 
	 * @param result Tipo del modelo de resultado para los módulos
	 * @param srId Control sobre las vbles de geolocalización formato para las transformacion de coordenadas
	 * @param listado Listado de objetos a encapsular en el resultado
	 * @param obj
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> ResponseEntity<Object> guardarResult(Result<T> result, String srId, List<T> listado, T obj, 
			HttpServletRequest request) throws Exception {

			
		Util.generaCoordenadasAll(StartVariables.SRID_XY_APP, srId, listado);	
		
		//CMG : Correccion para los cubos  07/07/2020
		Util.generaDataCubeInfo(listado);
				
		
		Map<String, String> pMCalculation = Util.pageMetadataCalculation(request,  result.getTotalRecords(), numPageSize, env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

		// Pagina actual
		((Result<?>) obj).setSelf(pMCalculation.get(Constants.SELF));
		if (result.getPage()!=Constants.NO_PAGINATION) {
			// Pagina inicial
			((Result<?>) obj).setFirst(pMCalculation.get(Constants.FIRST));
			// Ultima página
			((Result<?>) obj).setLast(pMCalculation.get(Constants.LAST));
			// Pagina siguiente
			((Result<?>) obj).setNext(pMCalculation.get(Constants.NEXT));
			// Pagina anterior
			((Result<?>) obj).setPrev(pMCalculation.get(Constants.PREV));
		}
		// MD5
		((Result<?>) obj).setContentMD5(Util.generateHash(Util.toString(listado)));

		((Result<T>) obj).setRecords(listado);
		if (result.getPage()!=Constants.NO_PAGINATION) {
			((Result<?>) obj).setPage(result.getPage());
		}
		if (result.getPage()!=Constants.NO_PAGINATION) {
			((Result<?>) obj).setPageSize(result.getPageSize());
		}
		((Result<?>) obj).setPageRecords(result.getRecords().size());
		((Result<?>) obj).setTotalRecords(result.getTotalRecords());

		((Result<?>) obj).setStatus(200);
		
		// CMG Control para mostrar las coordenadas
		Util.showCoordinates(((Result<?>) obj), srId);
		
		HttpHeaders headers = Util.extractHeaders((Result<?>) obj);

		return new ResponseEntity<Object>(obj, headers, HttpStatus.OK);
	}

	/**
	 * Guardar los resultados
	 * @param srId
	 * @param listado
	 * @param total
	 * @param obj
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> ResponseEntity<Object> guardarResult(String srId, List<T> listado, long total, T obj,
			HttpServletRequest request) throws Exception {

		//Control de coordenadas
		Util.generaCoordenadasAll( srId, listado);		
		
		Util.generaDataCubeInfo(listado);


		Map<String, String> pageMetadataCalculation = Util.pageMetadataCalculation(request, total, numPageSize,env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

		// Pagina actual
		((Result<?>) obj).setSelf(pageMetadataCalculation.get(Constants.SELF));
		
		if (numPage!=Constants.NO_PAGINATION) {
			// Pagina inicial
			((Result<?>) obj).setFirst(pageMetadataCalculation.get(Constants.FIRST));
			// Ultima página
			((Result<?>) obj).setLast(pageMetadataCalculation.get(Constants.LAST));
			// Pagina siguiente
			((Result<?>) obj).setNext(pageMetadataCalculation.get(Constants.NEXT));
			// Pagina anterior
			((Result<?>) obj).setPrev(pageMetadataCalculation.get(Constants.PREV));
		}
		// MD5
		((Result<?>) obj).setContentMD5(Util.generateHash(Util.toString(listado)));

		if (numPage!=Constants.NO_PAGINATION) {
			((Result<?>) obj).setPage(numPage);
		}else {
			((Result<?>) obj).setPage(1);
		}
		
		
		if (numPageSize!=Constants.NO_PAGINATION) {
			((Result<?>) obj).setPageSize(numPageSize);
		}	else {
			((Result<?>) obj).setPageSize((int)total);
		}		
		
		((Result<?>) obj).setPageRecords(listado.size());
		((Result<T>) obj).setRecords(listado);
		((Result<?>) obj).setTotalRecords(total);

		((Result<?>) obj).setStatus(200);

		//CMG Control para mostrar las coordenadas
		Util.showCoordinates(((Result<?>) obj), srId);
		
		HttpHeaders headers = Util.extractHeaders((Result<?>) obj);

		return new ResponseEntity<Object>(obj, headers, HttpStatus.OK);
	}
	

	/**
	 * Guardamos y controlamos los valores de paginación
	 * @param page
	 * @param pageSize
	 * @throws NumberFormatException
	 */
	public void setPagina(String page, String pageSize) throws NumberFormatException {
		
		if ((page == null) || (pageSize == null))
		{
			numPage=Constants.NO_PAGINATION;
			numPageSize=Constants.NO_PAGINATION;
			return;
		}

		if (pageSize.equals("")) {
			pageSize = Integer.toString(StartVariables.defaultPageSize);
		}

		numPage = Integer.parseInt(page);

		numPageSize = Integer.parseInt(pageSize);

		if (numPageSize < 0 || numPageSize > StartVariables.maxPageSize) {
			numPageSize = StartVariables.defaultPageSize;
		}

	}
	
	
	/**
	 * Guardamos y controlamos los valores de paginación para consulta GroupBy y Queries de Cubos de Datos
	 * @param page
	 * @param pageSize
	 * @throws NumberFormatException
	 */
	public void setPaginaGroupBy(String page, String pageSize) throws NumberFormatException {
		
		if ((page == null) || (pageSize == null))
		{
			numPage=Constants.NO_PAGINATION;
			numPageSize=Constants.NO_PAGINATION;
			return;
		}

		if (pageSize.equals("")) {
			pageSize = Integer.toString(Constants.defaultGroupByPageSize);
		}

		numPage = Integer.parseInt(page);

		numPageSize = Integer.parseInt(pageSize);

		if (numPageSize < 0 || numPageSize > Constants.maxGroupbyPageSize) {
			log.error("[setPaginaGroupBy] numPageSize ["+numPageSize+"] < 0 o > MaxGroupbyPageSize["+Constants.maxGroupbyPageSize+"]");
			numPageSize = Constants.defaultGroupByPageSize;
			log.info("[setPaginaGroupBy] [numPageSize:"+Constants.defaultGroupByPageSize+"] fijado al valor de por defecto");
		}

	}
	
	/**
	 * Metodo generico para extrae el orden de las busquedas
	 * @param sorts
	 * @return
	 */
	public List<Sort> extractOrder(String sorts) {
		List<Sort> result = new ArrayList<Sort>();
		if (sorts != null && !"".equals(sorts)) {
			sorts=sorts.replace(Constants.XETRS89,Constants.X.toLowerCase());
			sorts=sorts.replace(Constants.YETRS89,Constants.Y.toLowerCase());
			String[] listado = sorts.split(Constants.SORT_SEPARATOR);

			for (String obj : listado) {
				if (obj.startsWith(Constants.SORT_DESC)) {
					result.add(new Sort(obj.substring(1, obj.length()), true));

				} else if (obj.startsWith(Constants.SORT_ASC)) {
					result.add(new Sort(obj.substring(1, obj.length()), false));
				} else {
					result.add(new Sort(obj, false));
				}
			}
		} else {
			result.add(new Sort(Constants.IDENTIFICADOR, false));
		}

		return result;
	}
	
	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public int getNumPageSize() {
		return numPageSize;
	}

	public void setNumPageSize(int numPageSize) {
		this.numPageSize = numPageSize;
	}
	
	

		

	
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> datacubeSearch(
			 HttpServletRequest request, 
			 CubeQuerySearch cubeQuerySearch,		 
			 String page, 
			 String pageSize,
			 String key,
			 T objModel,
			 T objResult,			 
			 DatasetService<T> dsService) {
		
		log.info("[datacubeQuery]");

		log.debug("[parmam][page:" + page + "] [pageSize:" + pageSize + "]  [cubeQuerySearch:" + cubeQuerySearch + "] ");
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}		
		
		//CMG
		//Validación de parametros de entrada
		List<String> availableFields=Util.extractPropertiesFromBean(CubeQuerySearch.class);
		
		List<String> allowedParams=new ArrayList<String>();
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.AJAX_PARAM);		
										
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}	
		
		ResponseEntity<?> responseEntity= null;	
			
		setPaginaGroupBy(page,pageSize);
		
		
		int numPage = getNumPage();
		int numPageSize = getNumPageSize();
			
		
		if (!Util.validValue(cubeQuerySearch.getDimension()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'dimension' is required"));
			return responseEntity;
		}		
		
		if (!Util.validValue(cubeQuerySearch.getGroup()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'group' is required"));
			return responseEntity;
		}
		
		if (!Util.validValue(cubeQuerySearch.getMeasure()))
		{
			responseEntity=ExceptionUtil.checkException(new BadRequestException("the param 'measure' is required"));
			return responseEntity;
		}

		List<T> records;
		try
		{
			//hacemos una copia por dentro del groupBySearch se modifica el where
			CubeQuerySearch copy=new CubeQuerySearch(cubeQuerySearch);
			records = (List<T>) dsService.cubeQuery(key,(Class<T>)objModel.getClass(),cubeQuerySearch, numPage, numPageSize);			
			long numtotalRecords=dsService.rowCountCubeQuery(key,(Class<T>)objModel.getClass(),copy);
			
			responseEntity = guardarResult(NO_HAY_SRID, records, numtotalRecords,objResult, request);
			
					
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		}
		

		return responseEntity;
	}
	
	
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> integraCallejero(ResponseEntity<T> list,HttpServletRequest request){
		
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);				
			
			if (isSemantic) {
				
				Object body = list.getBody();
				
				Result<T> result=((Result<T>)body);
				
				List<T> records = result.getRecords();
				
				
				if (Util.isCallejeroIntegration()) {
					log.debug("[integraCallejero] isCallejeroIntegration ");
					for (T objCallejero :records) {	
						//CMG Controlamos en la integración con callejero, que tenga valor portalId
						if ( Util.validValue(((ICallejero) objCallejero).getPortalId())){
							((ICallejero) objCallejero).setStreetAddress(null);		
							((ICallejero) objCallejero).setPostalCode(null);
						}
					}
				}else {
					log.debug("[integraCallejero] Not isCallejeroIntegration ");
					for (T objCallejero:records) {	
						((ICallejero) objCallejero).setPortalIdIsolated(((ICallejero) objCallejero).getPortalId());
						((ICallejero) objCallejero).setPortalId(null);						
					}
				}
			}
		}
	
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public  ResponseEntity<?> integraTramoTrafico(ResponseEntity<T> list,HttpServletRequest request){
		
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);				
			
			if (isSemantic) {
				
				Object body = list.getBody();
				
				Result<T> result=((Result<T>)body);
				
				List<T> records = result.getRecords();
				
				
				if (Util.isTraficoIntegration()) {
					log.debug("[integraTrafico] isTraficoIntegration ");
					for (T objTramo :records) {							  
						if ( Util.validValue(((ITramoIncidencia) objTramo).getIncidenciaEnTramo())){
							((ITramoIncidencia) objTramo).setIncidenciaTramoDescription(null);
							
						}
					}
				}else {
					log.debug("[integraCallejero] Not isCallejeroIntegration ");
					for (T objTramo:records) {	
						((ITramoIncidencia) objTramo).setIncidenciaEnTramoIdIsolated(((ITramoIncidencia) objTramo).getIncidenciaEnTramo());
						((ITramoIncidencia) objTramo).setIncidenciaEnTramo(null);		
					}
				}
			}
		}
	
		return list;
	}
}



