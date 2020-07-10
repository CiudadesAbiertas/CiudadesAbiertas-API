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
import org.ciudadesabiertas.dataset.model.AgrupacionComercial;
import org.ciudadesabiertas.dataset.model.LicenciaActividad;
import org.ciudadesabiertas.dataset.model.LocalComercial;
import org.ciudadesabiertas.dataset.model.Terraza;
import org.ciudadesabiertas.dataset.utils.LocalComercialConstants;
import org.ciudadesabiertas.dataset.utils.LocalComercialGeoSearch;
import org.ciudadesabiertas.dataset.utils.LocalComercialResult;
import org.ciudadesabiertas.dataset.utils.LocalComercialSearch;
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
@Api(value="LocalComercial",description = "Conjunto de operaciones relacionadas con el conjunto de datos Local Comercial."+SwaggerConstants.VOCABULARIO_A_HREF+LocalComercialConstants.localComercialVocabURL+SwaggerConstants.VOCABULARIO_A_HREF_END,  tags= {"Local Comercial - Local Comercial"})
public class LocalComercialController extends GenericController implements CiudadesAbiertasController
{
	public static final String LIST = "/local-comercial/local-comercial";
	
	public static final String SEARCH_DISTINCT = LIST+"/distinct";
	
	public static final String GEO_LIST = LIST+ "/geo";
	
	public static final String RECORD = LIST+"/{id}";
	
	public static final String TRANSFORM = LIST+"/transform";
	
	public static final String ADD = LIST;
	public static final String UPDATE = RECORD;
	public static final String DELETE = RECORD;
	
	
	public static final String MODEL_VIEW_LIST = "localComercial/list";
	public static final String MODEL_VIEW_ID = "localComercial/id";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("LC_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("LC_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("LC_TRANSFORM", TRANSFORM, HttpMethod.POST,Constants.NO_AUTH));
		
		listRequestType.add(new RequestType("LC_ADD", ADD, HttpMethod.POST,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("LC_UPDATE", UPDATE, HttpMethod.PUT,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("LC_DELETE", DELETE, HttpMethod.DELETE,Constants.BASIC_AUTH));
		
	}
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(LocalComercial.class);

	private static final Logger log = LoggerFactory.getLogger(LocalComercialController.class);
	
	@Autowired
	private DatasetService<LocalComercial> service;
	
	@Autowired
	private DatasetService<AgrupacionComercial> agrupacionComercialService;	
	
	@Autowired
	private DatasetService<Terraza> terrazaService;	
	
	@Autowired
	private DatasetService<LicenciaActividad> licenciaService;	
	

	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.BUSQUEDA_DISTINCT, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_DISTINCT, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_DISTINCT,  response=ObjectResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {SEARCH_DISTINCT, VERSION_1+SEARCH_DISTINCT}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> distinctSearch(HttpServletRequest request, DistinctSearch search,															
			@RequestParam(value = Constants.PAGE, defaultValue = Constants.defaultPage+"", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page,
			@RequestParam(value = Constants.PAGESIZE, defaultValue = Constants.defaultGroupByPageSize+"", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize)
	{

		log.info("[distinctSearch][" + SEARCH_DISTINCT + "]");

		log.debug("[parmam][field:" + search.getField() + "] ");
		

		return distinctSearch(request, search, availableFields, page, pageSize,getKey(),NO_HAY_SRID, SEARCH_DISTINCT, new LocalComercial(), new ObjectResult(),  service);

	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.BUSQUEDA_GEOGRAFICA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_GEOGRAFICA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {GEO_LIST,  VERSION_1+GEO_LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> geoList(HttpServletRequest request,	LocalComercialGeoSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_FIELDS) String fields,			
			@RequestParam(value = Constants.METERS, required = true) 
				@ApiParam(value=SwaggerConstants.PARAM_METERS, required = true) String meters,
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = Constants.DISTANCE, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SORT) String sort,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[geoList][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [sort:" + sort + "]");
		
		
		ResponseEntity list= geoList(request, search, fields, meters, page, pageSize, sort, LIST, new LocalComercial(), new LocalComercialResult(), availableFields, getKey(),service);
		
		return integraCallejero(list,request);
	}

	@ApiIgnore	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value = { LIST + Constants.EXT_HTML,
			VERSION_1 + LIST + Constants.EXT_HTML }, method = RequestMethod.GET)
	public ModelAndView listHTML(ModelAndView mv, HttpServletRequest request, LocalComercialSearch search,
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ,
			@RequestParam(value = Constants.PAGE, defaultValue = "", required = false) String page,
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = "", required = false) String sort,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId) {

		log.info("[listHTML][" + LIST + ".html]");

		String params = "?";
		if (Util.validValue(rsqlQ)) {
			params += "&q=" + rsqlQ;
		} else if (search != null) {
			params += search.toUrlParam();
		}

		return listHTML(mv, request, srId, page, pageSize, sort, params, MODEL_VIEW_LIST);
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
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String id, HttpServletRequest request,  @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) @ApiParam(value=SwaggerConstants.PARAM_SRID, allowableValues=Constants.SUPPORTED_SRIDS)  String srId)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, srId, id, MODEL_VIEW_ID);
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, LocalComercialSearch search, 
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
			@RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SRID, allowableValues=Constants.SUPPORTED_SRIDS) String srId,			
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [rsqlQ:" + rsqlQ + "] [sort:" + sort + "]");
		
		RSQLVisitor<CriteriaQuery<LocalComercial>, EntityManager> visitor = new JpaCriteriaQueryVisitor<LocalComercial>();
		
		ResponseEntity list= list(request, search, fields, rsqlQ, page, pageSize, sort, srId, LIST,new LocalComercial(), new LocalComercialResult(), 
					 availableFields, getKey(), visitor,service);
		
		
		return integraCallejero(list,request);
	}	
	

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, LocalComercialSearch search, 
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
			@RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SRID, allowableValues=Constants.SUPPORTED_SRIDS) String srId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search, fields, rsqlQ, page, pageSize, sort,srId, headersRequest);

	}
	
	
	@RequestMapping(value={ADD,  VERSION_1+ADD}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.INSERCION, notes = SwaggerConstants.DESCRIPCION_INSERCION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_INSERCION,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	public @ResponseBody ResponseEntity<?> add(			
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_LOCALCOMERCIAL_TEXT) 			
			@RequestBody LocalComercial obj 
			)
	{

		log.info("[add][" + ADD + "]");

		log.debug("[parmam][dato:" + obj + "] ");
		//VBLES		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		boolean error=false;		
		LocalComercialResult resultObj = new LocalComercialResult();
		ResultError errorApi = new ResultError();
		
		
			
		//LocalComercial LocalComercial= LocalComercialService.getLocalComercial(parsedBody);
		LocalComercial localComercial= obj;
		
		//Validamos LocalComercial
		List<String> errores = localComercial.validate();
		
		
		if (!errores.isEmpty()) {
			log.error("[add][" + ADD + "] [ERROR Validator:"+errores+"]");
			error = true;
			errorApi.setStatus(400);
			errorApi.setClassName(LocalComercialController.class.getName());
			errorApi.setMethod("add");
			
			errorApi.setErrors(errores);
		}
		if (!error) {
			try {
				//Find id
				LocalComercial LocalComercialP= service.findById(getKey(),LocalComercial.class,localComercial.getId());
				
				if (LocalComercialP==null) {
					
					errores=checkClavesExternas(localComercial);
					
					if (errores.size()>0)
					{
						error = true;
						//Conficto 409
						errorApi.setStatus(409);
						errorApi.setClassName(LocalComercialController.class.getName());
						errorApi.setMethod("add");
						errorApi.setErrors(errores);
					}
					else {
					
						//POST
						service.save(getKey(),localComercial);
					
						// Casteo a objetos para el parser
						List<LocalComercial> records = new ArrayList<LocalComercial>();
					
						records.add(localComercial);
					
						resultObj.setPage(1);
						resultObj.setPageSize(1);
						resultObj.setRecords(records);
						resultObj.setTotalRecords(1);
						resultObj.setStatus(201);
						
						responseEntity = new ResponseEntity<Object>(resultObj, HttpStatus.valueOf(resultObj.getStatus()));
					}
					
				}else {
					error = true;
					//Conficto 409
					errorApi.setStatus(409);
					errorApi.setClassName(LocalComercialController.class.getName());
					errorApi.setMethod("add");
					
					errorApi.setError("Error trying to Add. [id:"+localComercial.getId()+"]  Found in BBDD.");											
				}
				
			} catch (Exception e)
			{
				responseEntity=ExceptionUtil.checkException(e);
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
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_MODIFICACION,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={UPDATE,  VERSION_1+UPDATE}, method = RequestMethod.PUT, consumes="application/json;charset=UTF-8")
	public @ResponseBody ResponseEntity<?> update(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT+SwaggerConstants.PARAM_ID_LOCAL_COMERCIAL) 
			@PathVariable(Constants.IDENTIFICADOR) String id, 
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_LOCALCOMERCIAL_TEXT) 
			@RequestBody LocalComercial obj)
	{

		log.info("[update][" + UPDATE + "]");

		log.debug("[parmam][id:" + id + "] [dato:" + obj + "] ");
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		Result<Object> resultObj = new Result<Object>();
		ResultError errorApi = new ResultError ();
		boolean error=false;	
		
		if (Util.validValue(id)) {

	
			//LocalComercial LocalComercial= LocalComercialService.getLocalComercial(parsedBody);
			LocalComercial localComercial= obj;
			//Validamos LocalComercial
			List<String> errores = localComercial.validate();
			
			//Necesitamos Generar el error 400
			if (errores.isEmpty()) {
				try {
					LocalComercial localComercialP=  service.findById(getKey(),LocalComercial.class,id);
			
				
					if (localComercialP!=null && errores.isEmpty()) {
						
						String ikey=localComercialP.getIkey();
						localComercialP=new LocalComercial(localComercial);
						localComercialP.setId(id);
						localComercialP.setIkey(ikey);
						
						
						errores=checkClavesExternas(localComercial);
						
						if (errores.size()>0)
						{
							error = true;
							//Conficto 409
							errorApi.setStatus(409);
							errorApi.setClassName(LocalComercialController.class.getName());
							errorApi.setMethod("update");
							errorApi.setErrors(errores);
						}
						else 
						{
						
							service.update(getKey(),localComercialP);
							
							// Casteo a objetos para el parser
							List<Object> records = new ArrayList<Object>();
							records.add(localComercialP);
							
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
						errorApi.setClassName(LocalComercialController.class.getName());
						errorApi.setMethod("update");	
						errorApi.setErrors(errores);
						if (localComercialP==null) {
							errorApi.setError("Error trying to modify.  [id:"+id+"] Not Found in BBDD");
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
				errorApi.setClassName(LocalComercialController.class.getName());
				errorApi.setMethod("update");				
				errorApi.setErrors(errores);
				log.debug("[update][ERRORAPI:"+errorApi+"]");
				
			}//Fin Else cuando se valida LocalComercial
		}//NO se ha introducido un id Valido
		else {
			error = true;
			errorApi.setStatus(404);
			errorApi.setClassName(LocalComercialController.class.getName());
			errorApi.setMethod("update");				
			errorApi.setError("id is not valid [id:"+id+"]");			
		}
		
		//Con Errores 
		if (error) {
			responseEntity = new ResponseEntity<Object>(errorApi, HttpStatus.valueOf(errorApi.getStatus()));
		}
	
		return responseEntity;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.DELETE, notes = SwaggerConstants.DESCRIPCION_DELETE, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_DELETE,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={DELETE,  VERSION_1+DELETE}, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT+SwaggerConstants.PARAM_ID_LOCAL_COMERCIAL) 
			@PathVariable(Constants.IDENTIFICADOR) String id)
	{

		log.info("[delete][" + DELETE + "]");

		log.debug("[parmam][id:" + id + "] ");			
		
		return delete(id, new LocalComercial(), LocalComercialController.class.getName(), DELETE, service,getKey());
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_LOCAL_COMERCIAL) String id, @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) @ApiParam(value=SwaggerConstants.PARAM_SRID, allowableValues=Constants.SUPPORTED_SRIDS)  String srId)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][id:" + id + "]");
				
		ResponseEntity record = record(request, id, new LocalComercial(),new LocalComercialResult(), srId, LocalComercialController.class.getName(), RECORD, service,getKey());
	
		return integraCallejero(record,request);
	}
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_LOCAL_COMERCIAL) String id, @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) @ApiParam(value=SwaggerConstants.PARAM_SRID, allowableValues=Constants.SUPPORTED_SRIDS)  String srId	)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, id, srId);
		
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={TRANSFORM,  VERSION_1+TRANSFORM}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.TRANSFORMACION, notes = SwaggerConstants.DESCRIPCION_TRANSFORMACION, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=LocalComercialResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
		public @ResponseBody ResponseEntity<?> transform(			
				@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_LOCALCOMERCIAL_TEXT) 			
				@RequestBody LocalComercial obj 
				)
		{

		log.info("[transform]");

		log.debug("[parmam][obj:" + obj + "]");

		return transform(obj, LocalComercialController.class.getName(), TRANSFORM);

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
	
	

	@Override
	public String getKey()
	{
		return "localComercial";
	}


	private List<String> checkClavesExternas(LocalComercial localComercial)
	{
		List<String> errores=new ArrayList<String>();
		
		//CMG CONTROL PARA ACTIVAR LAS FK
		if (activeFK) {
			//Chequeamos los identificadores que apuntan a otras tablas					
			if (Util.validValue(localComercial.getAgrupacionComercial()))
			{
				AgrupacionComercial findById = agrupacionComercialService.findById(getKey(),AgrupacionComercial.class,localComercial.getAgrupacionComercial());
				if (findById==null)
				{
					errores.add("AgrupacionComercial ID not exists in entity: "+localComercial.getAgrupacionComercial());
				}
			}
			
			if (Util.validValue(localComercial.getTieneLicenciaApertura() ))
			{
				LicenciaActividad findById = licenciaService.findById(getKey(),LicenciaActividad.class,localComercial.getTieneLicenciaApertura());
				if (findById==null)
				{
					errores.add("TieneLicenciaApertura ID not exists in entity: "+localComercial.getTieneLicenciaApertura());
				}
			}
			
			if (Util.validValue(localComercial.getTieneTerraza() ))
			{
				Terraza findById = terrazaService.findById(getKey(),Terraza.class,localComercial.getTieneTerraza() );
				if (findById==null)
				{
					errores.add("TieneTerraza ID not exists in entity: "+localComercial.getTieneTerraza());
				}
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
