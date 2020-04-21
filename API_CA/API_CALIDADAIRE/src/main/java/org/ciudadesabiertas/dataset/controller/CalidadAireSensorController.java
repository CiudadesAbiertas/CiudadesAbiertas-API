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

package org.ciudadesabiertas.dataset.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.CalidadAireEstacion;
import org.ciudadesabiertas.dataset.model.CalidadAireSensor;
import org.ciudadesabiertas.dataset.service.CalidadAireSensorService;
import org.ciudadesabiertas.dataset.util.CalidadAireConstants;
import org.ciudadesabiertas.dataset.util.CalidadAireSensorResult;
import org.ciudadesabiertas.dataset.util.CalidadAireSensorSearch;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.ObjectResult;
import org.ciudadesabiertas.utils.RequestType;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@SuppressWarnings("rawtypes")
@RestController
@Api(value="CalidadAireSensor",description = "Conjunto de operaciones sobre sensores del conjunto de datos Calidad del Aire"+SwaggerConstants.VOCABULARIO_A_HREF+CalidadAireConstants.airQualitySensorVocabURL+SwaggerConstants.VOCABULARIO_A_HREF_END, tags= {"Calidad del Aire - Sensor"})
public class CalidadAireSensorController extends GenericController implements CiudadesAbiertasController
{	

	public static final String LIST = "/calidad-aire/sensor";
	
	public static final String SEARCH_DISTINCT = LIST+"/distinct";
	
	public static final String RECORD = "/calidad-aire/estacion/{isHostedBy}/sensor/{observesId}";
	
	public static final String TRANSFORM = LIST+"/transform";
	
	public static final String ADD = LIST;
	public static final String UPDATE = "/calidad-aire/estacion/{isHostedBy}/sensor/{observesId}";
	public static final String CONTEXTO = "/calidad-aire";
	public static final String DELETE = "/calidad-aire/estacion/{isHostedBy}/sensor/{observesId}";
	
	public static final String MODEL_VIEW_LIST = "calidadAire/sensorList";
	public static final String MODEL_VIEW_ID = "calidadAire/sensorId";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("SENSOR_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("SENSOR_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("SENSOR_TRANSFORM", TRANSFORM, HttpMethod.POST,Constants.NO_AUTH));
		
		listRequestType.add(new RequestType("SENSOR_ADD", ADD, HttpMethod.POST,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("SENSOR_UPDATE", UPDATE, HttpMethod.PUT,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("SENSOR_DELETE", DELETE, HttpMethod.DELETE,Constants.BASIC_AUTH));
		
	}
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(CalidadAireSensor.class);

	private static final Logger log = LoggerFactory.getLogger(CalidadAireSensorController.class);
	

	@Autowired
	protected CalidadAireSensorService service;
	
	@Autowired
	protected DatasetService<CalidadAireEstacion> calidadAireEstacionService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.BUSQUEDA_DISTINCT, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_DISTINCT, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_DISTINCT,  response=ObjectResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {SEARCH_DISTINCT, VERSION_1+SEARCH_DISTINCT}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> distinctSearch(HttpServletRequest request, DistinctSearch search,															
															@RequestParam(value = Constants.PAGE, defaultValue = Constants.defaultPage+"", required = false) String page,
															@RequestParam(value = Constants.PAGESIZE, defaultValue = Constants.defaultGroupByPageSize+"", required = false) String pageSize)
	{

		log.info("[distinctSearch][" + SEARCH_DISTINCT + "]");

		log.debug("[parmam][field:" + search.getField() + "] ");
		

		return distinctSearch(request, search, availableFields, page, pageSize,getKey(),NO_HAY_SRID, SEARCH_DISTINCT, new CalidadAireSensor(), new ObjectResult(),  service);

	}
	
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST+Constants.EXT_HTML, VERSION_1+LIST+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listHTML(
			ModelAndView mv, HttpServletRequest request, CalidadAireSensorSearch search, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ,
			@RequestParam(value = Constants.PAGE, defaultValue = "", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue ="", required = false) String pageSize, 
			@RequestParam(value = Constants.SORT, defaultValue = "", required = false) String sort)
			
	{
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";
		if (Util.validValue(rsqlQ))	{
			params+="&q="+rsqlQ;
		}else if (search!=null){
			params+=search.toUrlParam();
		}		
		
		return listHTML(mv, request, NO_HAY_SRID, page, pageSize, sort, params, MODEL_VIEW_LIST);
	}
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.FICHA_HTML, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=ModelAndView.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD+Constants.EXT_HTML, VERSION_1+RECORD+Constants.EXT_HTML}, method = RequestMethod.GET)
	public ModelAndView recordHTML(ModelAndView mv, HttpServletRequest request, @PathVariable String isHostedBy, @PathVariable String observesId)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, observesId, MODEL_VIEW_ID);
	}
	
	

	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, CalidadAireSensorSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_FIELDS) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_Q) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SORT) String sort,						
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [rsqlQ:" + rsqlQ + "] [sort:" + sort + "]");
		
		RSQLVisitor<CriteriaQuery<CalidadAireSensor>, EntityManager> visitor = new JpaCriteriaQueryVisitor<CalidadAireSensor>();
		
		return list(request, search, fields, rsqlQ, page, pageSize, sort, NO_HAY_SRID, LIST,new CalidadAireSensor(), new CalidadAireSensorResult(), 
					 availableFields, getKey(), visitor,service);
	}	
	

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, CalidadAireSensorSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) String sort,			
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search, fields, rsqlQ, page, pageSize, sort, headersRequest);

	}
	
	
	//CMG
	@RequestMapping(value={ADD,  VERSION_1+ADD}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.INSERCION, notes = SwaggerConstants.DESCRIPCION_INSERCION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_INSERCION,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	public @ResponseBody ResponseEntity<?> add(			
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CALIDADAIRESENSOR_TEXT) 			
			@RequestBody CalidadAireSensor obj 
			)
	{

		log.info("[add][" + ADD + "]");

		log.debug("[parmam][dato:" + obj + "] ");
		//VBLES		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		boolean error=false;		
		CalidadAireSensorResult resultObj = new CalidadAireSensorResult();
		ResultError errorApi = new ResultError();
		
		
		if (!error) {
			
			
			CalidadAireSensor item= obj;
			
			//Validamos 
			List<String> errores = item.validate();
			
			
			if (!errores.isEmpty()) {
				log.error("[add][" + ADD + "] [ERROR Validator:"+errores+"]");
				error = true;
				errorApi.setStatus(400);
				errorApi.setClassName(CalidadAireSensorController.class.getName());
				errorApi.setMethod("add");
				
				errorApi.setErrors(errores);
			}
			if (!error) {
				try {
					//Find id					
					CalidadAireSensor itemP = service.findById(getKey(),item.getIsHostedBy(),item.getObservesId());
					
					if (itemP==null) {
						
						errores=checkClavesExternas(item);
							
						if (errores.size()>0)
						{
							error = true;
							//Conficto 409
							errorApi.setStatus(409);
							errorApi.setClassName(CalidadAireSensorController.class.getName());
							errorApi.setMethod("add");
							errorApi.setErrors(errores);
						}
						else 
						{
							//POST
							service.save(getKey(),item);
						
							// Casteo a objetos para el parser
							List<CalidadAireSensor> records = new ArrayList<CalidadAireSensor>();
						
							records.add(item);
						
							resultObj.setPage(1);
							resultObj.setPageSize(1);
							resultObj.setRecords(records);
							resultObj.setTotalRecords(1);
							resultObj.setStatus(201);
							
							responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.valueOf(resultObj.getStatus()));
						}
					}
					else 
					{
						error = true;
						//Conficto 409
						errorApi.setStatus(409);
						errorApi.setClassName(CalidadAireSensorController.class.getName());
						errorApi.setMethod("add");
						
						errorApi.setError("Error trying to Add. [IsHostedBy:"+item.getIsHostedBy()+"][ObservesId:"+item.getObservesId()+"]  Found in BBDD.");											
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
	
	

	@ApiOperation(value = SwaggerConstants.MODIFICACION, notes = SwaggerConstants.DESCRIPCION_MODIFICACION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_MODIFICACION,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={UPDATE,  VERSION_1+UPDATE}, method = RequestMethod.PUT, consumes="application/json;charset=UTF-8")
	public @ResponseBody ResponseEntity<?> update(			 
			@PathVariable(Constants.IS_HOSTED_BY) @ApiParam(required=true, value="Relación entre un sensor y una estación, una muestra o una plataforma, en la que está montado o alojado."+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_ESTACION) String isHostedBy,			 
			@PathVariable(Constants.OBSERVES_ID) @ApiParam(required=true, value="Identificador de la relación entre un sensor y una propiedad observable que es capaz de detectar."+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_SENSOR_OBS) String observesId, 
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CALIDADAIRESENSOR_TEXT) 
			@RequestBody CalidadAireSensor obj)
	{

		log.info("[update][" + UPDATE + "]");

		log.debug("[parmam][isHostedBy:" + isHostedBy + "][observesId:" + observesId + "] [dato:" + obj + "] ");
		
		isHostedBy=Util.decodeURL(isHostedBy);
		observesId=Util.decodeURL(observesId);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		Result<Object> resultObj = new Result<Object>();
		ResultError errorApi = new ResultError ();
		boolean error=false;	
		
		if ((Util.validValue(isHostedBy))&&(Util.validValue(observesId)))
		{	
			CalidadAireSensor item= obj;
			//Validamos 
			List<String> errores = item.validate();
			
			//Necesitamos Generar el error 400
			if (errores.isEmpty()) {
				try {
					CalidadAireSensor itemP=  service.findById(getKey(),isHostedBy,observesId);
			
					if (itemP!=null && errores.isEmpty()) {
											
						String ikey=itemP.getIkey();
						itemP=new CalidadAireSensor(item);
						itemP.setIkey(ikey);
						
						errores=checkClavesExternas(item);
						
						if (errores.size()>0)
						{
							error = true;
							//Conficto 409
							errorApi.setStatus(409);
							errorApi.setClassName(CalidadAireSensorController.class.getName());
							errorApi.setMethod("update");
							errorApi.setErrors(errores);
						}
						else 
						{
						
							service.update(getKey(),itemP);
							
							// Casteo a objetos para el parser
							List<Object> records = new ArrayList<Object>();
							records.add(itemP);
							
							resultObj.setPage(1);
							resultObj.setPageSize(1);
							resultObj.setRecords(records);
							resultObj.setTotalRecords(1);
							resultObj.setStatus(200);
							
							responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.OK);
						}
						
					}else {
						//Fijamos ERROR		
						
						error = true;
						errorApi.setStatus(404);
						errorApi.setClassName(CalidadAireSensorController.class.getName());
						errorApi.setMethod("update");	
						errorApi.setErrors(errores);
						if (itemP==null) {
							errorApi.setError("Error trying to modify.  [isHostedBy:" + isHostedBy + "][observesId:" + observesId + "] Not Found in BBDD");
						}
						
					}
				
				} catch (Exception e)
				{
					responseEntity=ExceptionUtil.checkException(e);
				}	
				
			}else {

				log.error("[update][" + UPDATE + "] [ERROR: Not Valid (Obj:"+obj+"]");				
				error = true;
				errorApi.setStatus(400);
				errorApi.setClassName(CalidadAireSensorController.class.getName());
				errorApi.setMethod("update");				
				errorApi.setErrors(errores);
				log.debug("[update][ERRORAPI:"+errorApi+"]");
				
			}//Fin Else cuando se valida
		}//NO se ha introducido un id Valido
		else {
			error = true;
			errorApi.setStatus(404);
			errorApi.setClassName(CalidadAireSensorController.class.getName());
			errorApi.setMethod("update");				
			errorApi.setError("id is not valid [isHostedBy:" + isHostedBy + "][observesId:" + observesId + "]");			
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}
	
		return responseEntity;
	}
	
	
	//CMG
	@ApiOperation(value = SwaggerConstants.DELETE, notes = SwaggerConstants.DESCRIPCION_DELETE, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_DELETE,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={DELETE,  VERSION_1+DELETE}, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(
			@ApiParam(required = true, name = Constants.IS_HOSTED_BY, value = SwaggerConstants.PARAM_IS_HOSTED_BY_TEXT) 
			@PathVariable(Constants.IS_HOSTED_BY) String isHostedBy,
			@ApiParam(required = true, name = Constants.OBSERVES_ID, value = SwaggerConstants.PARAM_OBSERVES_ID_TEXT+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_SENSOR_OBS) 
			@PathVariable(Constants.OBSERVES_ID) String observesId)
	{

		log.info("[delete][" + DELETE + "]");

		log.debug("[parmam][isHostedBy:" + isHostedBy + "][observesId:" + observesId + "]");
		
		isHostedBy=Util.decodeURL(isHostedBy);
		observesId=Util.decodeURL(observesId);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		Result<Object> resultObj = new Result<Object>();
		ResultError errorApi = new ResultError ();
		boolean error=false;	
		
		if ((Util.validValue(isHostedBy))&&(Util.validValue(observesId))) {
		
			try {
				CalidadAireSensor item= service.findById(getKey(),isHostedBy,observesId);
								
				if (item==null) {
					//Fijamos ERROR	
					error = true;
					errorApi.setStatus(404);
					errorApi.setClassName(CalidadAireSensorController.class.getName());
					errorApi.setMethod("delete");	
					errorApi.setError("Error trying to delete. [isHostedBy:" + isHostedBy + "][observesId:" + observesId + "]\" Not Found in BBDD");
										
				}else {
										
					service.delete(getKey(),item);
					List<Object> records = new ArrayList<Object>();
					records.add(item);
					
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
			errorApi.setClassName(CalidadAireSensorController.class.getName());
			errorApi.setMethod("delete");				
			errorApi.setError("id is not valid [isHostedBy:" + isHostedBy + "][observesId:" + observesId + "]");			
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}

		return responseEntity;
	}
	
	
	
	

	//CMG
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class),
	            
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, 
													@PathVariable @ApiParam(required = true, value= SwaggerConstants.PARAM_IS_HOSTED_BY_TEXT+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_ESTACION) String isHostedBy,
													@PathVariable @ApiParam(required = true, value= SwaggerConstants.PARAM_OBSERVES_ID_TEXT+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_SENSOR_OBS) String observesId)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][isHostedBy:" + isHostedBy + "][observesId:" + observesId + "]");
		
		isHostedBy=Util.decodeURL(isHostedBy);
		observesId=Util.decodeURL(observesId);
		
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null)
		{
			return negotiationResponseEntity;
		}
		
		ResponseEntity<?> responseEntity= null;
					
		CalidadAireSensorResult CalidadAireSensorResult=new CalidadAireSensorResult();
		List<CalidadAireSensor> listado=new ArrayList<CalidadAireSensor>();
		
	
		try {
			CalidadAireSensor obj = service.findById(getKey(),isHostedBy,observesId);
			
								
			if (obj!=null)
			{	
				listado.add(obj);
				CalidadAireSensorResult.setPageSize(1);
				CalidadAireSensorResult.setTotalRecords(1);
				CalidadAireSensorResult.setRecords(listado);
				CalidadAireSensorResult.setStatus(200);
				//MD5
				CalidadAireSensorResult.setContentMD5(Util.generateHash( obj.toString() ));
				
				//Cabeceras no se estaban incluyendo
				HttpHeaders headers = Util.extractHeaders(CalidadAireSensorResult);
				responseEntity = new ResponseEntity<Object>(CalidadAireSensorResult,headers, HttpStatus.OK);
			}else {
				//Fijamos ERROR	
				ResultError errorApi = new ResultError ();
				errorApi.setStatus(404);
				errorApi.setClassName(CalidadAireSensorController.class.getName());
				errorApi.setMethod("record");	
				errorApi.setError("id is not valid [isHostedBy:" + isHostedBy + "][observesId:" + observesId + "] Not Found in BBDD");					
				responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
			}
			
			
		} catch (Exception e)
		{
			responseEntity=ExceptionUtil.checkException(e);
		} 

		return responseEntity;

	}
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, 
														@PathVariable @ApiParam(required = true, value= SwaggerConstants.PARAM_IS_HOSTED_BY_TEXT+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_ESTACION) String isHostedBy,
														@PathVariable @ApiParam(required = true, value= SwaggerConstants.PARAM_OBSERVES_ID_TEXT+SwaggerConstants.PARAM_ID_CALIDAD_AIRE_SENSOR_OBS) String observesId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, isHostedBy, observesId);
		
	}



	@Override
	public ArrayList<SecurityURL> getURLsWithRoles()
	{		
		
		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();
		
		Properties properties = mConf.getDatabasesConfig().get(getKey());
				
		listRequestType = Util.getRequestType(properties,getKey(), listRequestType);
		
		for (RequestType rObj:listRequestType) {
			urlRoles.add(rObj.getSecurityURL());
		}		
		
		
		return urlRoles;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={TRANSFORM,  VERSION_1+TRANSFORM}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.TRANSFORMACION, notes = SwaggerConstants.DESCRIPCION_TRANSFORMACION, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CalidadAireSensorResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	public @ResponseBody ResponseEntity<?> transform(
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CALIDADAIRESENSOR_TEXT) @RequestBody CalidadAireSensor obj) {

		log.info("[transform]");

		log.debug("[parmam][obj:" + obj + "]");

		return transform(obj, CalidadAireSensorController.class.getName(), TRANSFORM);

	}
	
	
	
	@Override
	public String getKey()
	{
		return CalidadAireConstants.KEY;
	}

	
	private List<String> checkClavesExternas(CalidadAireSensor item)
	{
		List<String> errores=new ArrayList<String>();
		//Chequeamos los identificadores que apuntan a otras tablas	
		//CMG Se comprueba solo si estan activabas las FK
		if (Util.validValue(item.getIsHostedBy()) && activeFK)
		{
			CalidadAireEstacion findById = calidadAireEstacionService.findById(getKey(),CalidadAireEstacion.class,item.getIsHostedBy());
			if (findById==null)
			{
				errores.add("madeBySensorID not exists in entity: "+item.getIsHostedBy());
			}
		}
		
		
		return errores;
	}
	
	
	@Override
	public String getListURI()
	{
		return LIST;
	}
	
}
