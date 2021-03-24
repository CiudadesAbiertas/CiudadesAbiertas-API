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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.dataset.model.CubeDsdDimension;
import org.ciudadesabiertas.dataset.model.CubeDsdMeasure;
import org.ciudadesabiertas.dataset.service.CubeDsdDimensionService;
import org.ciudadesabiertas.dataset.service.CubeDsdMeasureService;
import org.ciudadesabiertas.dataset.utils.CubeDsdConstants;
import org.ciudadesabiertas.dataset.utils.CubeDsdDimensionResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdSearch;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.ExceptionUtil;
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
@Api(value="DSD",description = "Conjunto de operaciones relacionadas con las definiciones de estructuras de datos (DSD - Datacubes)"+SwaggerConstants.VOCABULARIO_A_HREF+CubeDsdConstants.dataStructureDefinitionVocabURL+SwaggerConstants.VOCABULARIO_A_HREF_END, tags= {"DSD - Cubo de Datos"})
public class CubeDsdController extends GenericController implements CiudadesAbiertasController 
{
	public static final String LIST = "/data-cube/data-structure-definition";
	public static final String RECORD = "/data-cube/data-structure-definition/{cubeId}";
	public static final String LIST_BY_DIMENSION = "/data-cube/data-structure-definition/dimension/{dimensionId}/list";
	public static final String LIST_BY_MEASURE = "/data-cube/data-structure-definition/measure/{measureId}/list";	
	
	public static final String MODEL_VIEW_LIST = "dsd/dsdList";
	public static final String MODEL_VIEW_ID = "dsd/dsdId";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = CubeDsdController.class.getName();
	
	private static Map<String,String> dimensionFromExtraValues=null;
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("DATACUBE_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("DATACUBE_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
	}
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(CubeDsd.class);

	private static final Logger log = LoggerFactory.getLogger(CubeDsdController.class);
	
	@Autowired
	protected DatasetService<CubeDsd> service;
	
	@Autowired
	private CubeDsdDimensionService cubeDsdDimensionService;
	
	@Autowired
	private CubeDsdMeasureService cubeDsdMeasureService;
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, 
													CubeDsdSearch search)
	{

		log.info("[list][" + LIST + "]");
		
		
		ResponseEntity list = list(request, search, "","", null, null, null, NO_HAY_SRID, LIST,new CubeDsd(), new CubeDsdResult(), 
				 availableFields, getKey(), null,service);
		
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = list.getBody();
			
			Result<CubeDsd> result=((Result<CubeDsd>)body);
			
			List<CubeDsd> records = result.getRecords();
			
			for (CubeDsd dsd:records) {	
				addDimensionAndMeasure(isSemantic, dsd);
			}
			
			
		}
		
		return list;
	}


	/*Metodo para añadir las dimensiones y medidas a un dsd*/
	private void addDimensionAndMeasure(boolean isSemantic, CubeDsd dsd) {
		List<CubeDsdDimension> dimensions = cubeDsdDimensionService.findByCubeDsd(getKey(), dsd);
		List<CubeDsdMeasure> measures = cubeDsdMeasureService.findByCubeDsd(getKey(), dsd);
		
		List<String> dim=new ArrayList<String>();			
		List<String> medida=new ArrayList<String>();
		
		for (CubeDsdDimension actualDim:dimensions)
		{
			if (isSemantic)
			{
				dim.add(CubeDsdDimensionController.LIST+"/"+actualDim.getId());
			}else {
				
				if (Util.validValue(actualDim.getMutipleField()))
				{
					String[] newDimensions = actualDim.getMutipleField().split(",");
					for (String newDim:newDimensions)
					{
						dim.add(newDim.trim());
					}
				}
				else
				{
					dim.add(actualDim.getId());
				}
			}
			
		}
		
		for (CubeDsdMeasure actualMeasure:measures)
		{
			if (isSemantic)
			{
				medida.add(CubeDsdMeasureController.LIST+"/"+actualMeasure.getId());
			}else {
				medida.add(actualMeasure.getId());
			}
		}
		
		dsd.setDimension(dim);
		dsd.setMeasure(medida);
	}
	

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, CubeDsdSearch search)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search);

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
			ModelAndView mv, HttpServletRequest request,CubeDsdSearch search)
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";
		if (search!=null){
			params+=search.toUrlParam();
		}		
		
		return listHTML(mv, request, NO_HAY_SRID, null, null, null, params, MODEL_VIEW_LIST);
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DSD) String cubeId)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][cubeId:" + cubeId + "]");
		
		cubeId=Util.decodeURL(cubeId);		
		
		ResponseEntity record = record(request, cubeId, new CubeDsd(), new CubeDsdResult(), NO_HAY_SRID, nameController, RECORD, service,getKey());
				
		HttpStatus statusCode = record.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = record.getBody();
			
			Result<CubeDsd> result=((Result<CubeDsd>)body);
			
			String selfRequest = Util.generateSelfRequest(request);		
			result.setSelf(selfRequest);
			
			List<CubeDsd> records = result.getRecords();
			
			for (CubeDsd dsd:records) {	
				addDimensionAndMeasure(isSemantic, dsd);
			}
		}
		
		
		return record;

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
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DSD) String cubeId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, cubeId);
		
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
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String cubeId, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, cubeId, MODEL_VIEW_ID);
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
		
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_CUBO_POR_DIMENSION, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_DIMENSION,  VERSION_1+LIST_BY_DIMENSION}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> listDataCubeByDimension(HttpServletRequest request,
			@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId,
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[list][" + LIST_BY_DIMENSION + "]");	
		
		loadDimensionsFromMultiplesValues();				
				
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
		

		
		
		boolean isSemantic=Util.isSemanticPetition(request);
		
		List<CubeDsd> listado;
		try
		{	
			CubeDsdDimension dim=cubeDsdDimensionService.findById(getKey(), CubeDsdDimension.class, dimensionId);
			
			if (dim==null)
			{
				String dimensionKey = dimensionFromExtraValues.get(dimensionId);
				if (dimensionKey!=null)
				{
					dim=cubeDsdDimensionService.findById(getKey(), CubeDsdDimension.class, dimensionKey);
				}
			}
			
			listado=cubeDsdDimensionService.findCubeDsdByDimension(getKey(), dim);
			
			for (CubeDsd dsd:listado) {	
				addDimensionAndMeasure(isSemantic, dsd);
			}			
			
			long total=cubeDsdDimensionService.rowCountCubeDsdByDimension(getKey(), dim);	
			
			
			
			
			responseEntity = guardarResult(NO_HAY_SRID, listado, total, getNumPageSize(),  new CubeDsdResult(), request);	
		} catch (Exception e)
		{
			log.error("internal error",e);
			responseEntity=ExceptionUtil.checkException(e);
		}		
		
		
		return responseEntity;
	}


	private void loadDimensionsFromMultiplesValues() {
		if (dimensionFromExtraValues==null)
		{
			dimensionFromExtraValues=new HashMap<String,String>();
			List<CubeDsdDimension> dimensionWithMultipesValues = cubeDsdDimensionService.findDimensionWithMultiplesValues(getKey());
			for (CubeDsdDimension dim:dimensionWithMultipesValues)
			{
				String multipleFields=dim.getMutipleField();
				String[] split = multipleFields.split(",");
				for (String newDim:split)
				{					
					dimensionFromExtraValues.put(newDim, dim.getId());
				}
			}
		}
	}
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_DIMENSION+Constants.EXT_HTML, VERSION_1+LIST_BY_DIMENSION+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listDataCubeByDimensionHTML(
			ModelAndView mv, HttpServletRequest request,@PathVariable String dimensionId)
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";
		
		log.info("[listHTML]");
		String theURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, Constants.EXT_JSON);
		String recordURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, "");
		
		mv.addObject("theURL", theURL+params);
		mv.addObject("recordURL", recordURL);
		mv.setViewName(MODEL_VIEW_LIST);
		
		return mv;
	}
	
	@ApiOperation(value = SwaggerConstants.LISTADO_CUBO_POR_DIMENSION, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_DIMENSION,  VERSION_1+LIST_BY_DIMENSION}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listDataCubeByDimensionHead(HttpServletRequest request,
																			@PathVariable @ApiParam(required=true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId,	
																			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listDimensionHead][" + LIST + "]");		
		return listDataCubeByDimension(request, dimensionId, headersRequest);

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

	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_CUBO_POR_MEDIDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_MEASURE,  VERSION_1+LIST_BY_MEASURE}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> listDataCubeByMeasure(HttpServletRequest request,
			@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_MEASURE_ID) String measureId,						
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[list][" + LIST_BY_MEASURE + "]");	
				
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
		
				
		boolean isSemantic=Util.isSemanticPetition(request);
		
		List<CubeDsd> listado;
		try
		{	
			CubeDsdMeasure measure=cubeDsdMeasureService.findById(getKey(), CubeDsdMeasure.class, measureId);
			
			listado=cubeDsdMeasureService.findCubeDsdByMeasure(getKey(), measure);
			
			for (CubeDsd dsd:listado) {	
				addDimensionAndMeasure(isSemantic, dsd);
			}
			
			long total=cubeDsdMeasureService.rowCountCubeDsdByMeasure(getKey(), measure);			
			
			responseEntity = guardarResult(NO_HAY_SRID, listado, total, getNumPageSize(), new CubeDsdResult(), request);	
		} catch (Exception e)
		{
			log.error("internal error",e);
			responseEntity=ExceptionUtil.checkException(e);
		}		
		
		
		return responseEntity;
	}
	
	
	
	@ApiOperation(value = SwaggerConstants.LISTADO_CUBO_POR_MEDIDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_MEASURE,  VERSION_1+LIST_BY_MEASURE}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listDataCubeByMeasureHead(HttpServletRequest request,@PathVariable @ApiParam(required=true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_MEASURE_ID) String measureId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listDataCubeByMeasureHead][" + LIST + "]");		
		return listDataCubeByMeasure(request, measureId, headersRequest);

	}
	
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_BY_MEASURE+Constants.EXT_HTML, VERSION_1+LIST_BY_MEASURE+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listDataCubeByMeasureHTML(
			ModelAndView mv, HttpServletRequest request,@PathVariable String measureId)
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";
		
		log.info("[listHTML]");
		String theURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, Constants.EXT_JSON);
		String recordURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, "");
		
		

		
		mv.addObject("theURL", theURL+params);
		mv.addObject("recordURL", recordURL);
		mv.setViewName(MODEL_VIEW_LIST);
		
		return mv;
	}

}
