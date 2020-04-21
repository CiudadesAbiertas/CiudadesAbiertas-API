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

import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.CubeDsdDimension;
import org.ciudadesabiertas.dataset.model.CubeDsdDimensionValue;
import org.ciudadesabiertas.dataset.service.CubeDsdDimensionValueService;
import org.ciudadesabiertas.dataset.utils.CubeDsdConstants;
import org.ciudadesabiertas.dataset.utils.CubeDsdDimensionValueResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdResult;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.RequestType;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
@Api(value="DSD",description = "Conjunto de operaciones relacionadas con las definiciones de estructuras de datos (DSD - Datacubes)", tags= {"DSD - Valor de una dimensión"})
public class CubeDsdDimensionValueController extends GenericController implements CiudadesAbiertasController 
{	
	public static final String LIST = "/data-cube/data-structure-definition/dimension/{dimensionId}/value";	
	
	public static final String RECORD = "/data-cube/data-structure-definition/dimension/{dimensionId}/value/{id}";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = CubeDsdDimensionValueController.class.getName();
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("DIMENSION_VALUE_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));	
		listRequestType.add(new RequestType("DIMENSION_VALUE_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
	}
	
	public static final String MODEL_VIEW_LIST = "dsd/dimensionValueList";
	public static final String MODEL_VIEW_ID = "dsd/dimensionValueId";	
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(CubeDsdDimensionValue.class);

	private static final Logger log = LoggerFactory.getLogger(CubeDsdDimensionValueController.class);
		
	
	@Autowired
	protected CubeDsdDimensionValueService service;
	
	@Autowired
	protected DatasetService<CubeDsdDimension> cubeDsdDimensionService;
	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionValueResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> listDimension(HttpServletRequest request,
			@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId,
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[list][" + LIST + "]["+dimensionId+"]");	
				
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null){
			return negotiationResponseEntity;
		}
		
		//CMG: Proceso de Validación de Parametros ahora debemos pasar los campos por metodo ya que no seran igual para todos
		List<String> allowedParams=new ArrayList<String>();
		allowedParams.add(Constants.AJAX_PARAM);
		allowedParams.add(Constants.PAGESIZE);
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.SORT);
			
		
		ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
		if (responseErrorParams!=null) {
			return responseErrorParams;
		}
		//FIN CONTROL
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);	
		
		
		try
		{
			setPagina(null, null);
		}
		catch (NumberFormatException e)
		{
			log.error("Error parsing page numbers",e);
			responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong page information"));
			return responseEntity;
		}
		
			
		List<CubeDsdDimensionValue> listado;
		try
		{	
			CubeDsdDimension cubeDsdDimension=cubeDsdDimensionService.findById(getKey(), CubeDsdDimension.class, dimensionId);
			
			listado = service.findByDimension(getKey(), cubeDsdDimension);
			
			long total=service.rowCountByDimension(getKey(), cubeDsdDimension);
		
			
			responseEntity = guardarResult(NO_HAY_SRID, listado, total, new CubeDsdDimensionValueResult(), request)	;	
		} catch (Exception e)
		{
			log.error("internal error",e);
			responseEntity=ExceptionUtil.checkException(e);
		}		
		
		
		return responseEntity;
		
		
		
		
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
	public ModelAndView listDimensionHTML(
			ModelAndView mv, HttpServletRequest request )
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";		
		
		return listHTML(mv, request, NO_HAY_SRID, null, null, null, params, MODEL_VIEW_LIST);
	}
	


	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request,@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId,			
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return listDimension(request,dimensionId, headersRequest);

	}
	

	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdDimensionValueResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findByDimensionAndId(HttpServletRequest request, 
													@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId, 
													@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_VALUE) String id)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][dimensionId:" + dimensionId + "][id:" + id + "]");
		
		dimensionId=Util.decodeURL(dimensionId);
		id=Util.decodeURL(id);
				
		//Verifico la negociación de contenidos
				ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
				if (negotiationResponseEntity!=null){
					return negotiationResponseEntity;
				}
				
				//CMG: Proceso de Validación de Parametros ahora debemos pasar los campos por metodo ya que no seran igual para todos
				List<String> allowedParams=new ArrayList<String>();
				allowedParams.add(Constants.AJAX_PARAM);
						
				
				ResponseEntity<?> responseErrorParams = Util.validateParams(request.getParameterMap(), availableFields,allowedParams);
				if (responseErrorParams!=null) {
					return responseErrorParams;
				}
				//FIN CONTROL			
		
				ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);	
					
				//Result<CubeDsdDimensionValue> resultObj = new Result<CubeDsdDimensionValue>();
				
				CubeDsdDimensionValueResult resultObj= new CubeDsdDimensionValueResult();
				
				List<CubeDsdDimensionValue> listado=new ArrayList<CubeDsdDimensionValue>();
				
				
				try {
					
					CubeDsdDimension cubeDsdDimension=cubeDsdDimensionService.findById(getKey(), CubeDsdDimension.class, dimensionId);
					
					listado=service.findByDimensionAndId(getKey(), cubeDsdDimension, id);
							
					if (listado.size()>0)
					{							
						resultObj.setPage(1);
						resultObj.setPageRecords(1);
						resultObj.setPageSize(1);
						resultObj.setTotalRecords(1);
						resultObj.setRecords((List<CubeDsdDimensionValue>) listado);
						resultObj.setStatus(200);
						//MD5
						resultObj.setContentMD5(Util.generateHash( listado.get(0).toString() ));
						
						String selfRequest = Util.generateSelfRequest(request);		
						resultObj.setSelf(selfRequest);
						
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
	
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, 
													@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId,  
													@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_VALUE) String id)
	{
		log.info("[recordHead][" + RECORD + "]");
		return findByDimensionAndId(request, dimensionId, id);		
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
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String id, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, id, MODEL_VIEW_ID);
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
		return CubeDsdConstants.KEY;
	}

	@Override
	public String getListURI()
	{	
		return LIST;
	}

	

}
