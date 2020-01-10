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
import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.dataset.model.CubeDsdMeasure;
import org.ciudadesabiertas.dataset.service.CubeDsdMeasureService;
import org.ciudadesabiertas.dataset.utils.CubeDsdConstants;
import org.ciudadesabiertas.dataset.utils.CubeDsdDimensionResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdMeasureResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdMeasureSearch;
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
@Api(value="DSD",description = "Conjunto de operaciones relacionadas con las definiciones de estructuras de datos (DSD - Datacubes)", tags= {"DSD - Medida"})
public class CubeDsdMeasureController extends GenericController implements CiudadesAbiertasController 
{	
	
	
	public static final String LIST = "/data-cube/data-structure-definition/measure";
	public static final String RECORD = "/data-cube/data-structure-definition/measure/{measureId}";	
	public static final String LIST_DATACUBE = "/data-cube/data-structure-definition/{cubeId}/measure";
	
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = CubeDsdMeasureController.class.getName();
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));		
		listRequestType.add(new RequestType("RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("LIST_DATACUBE", LIST_DATACUBE, HttpMethod.GET,Constants.NO_AUTH));
	}
	
	public static final String MODEL_VIEW_LIST = "dsd/measureList";
	public static final String MODEL_VIEW_ID = "dsd/measureId";
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(CubeDsdMeasure.class);

	private static final Logger log = LoggerFactory.getLogger(CubeDsdMeasureController.class);

	@Autowired
	protected CubeDsdMeasureService cubeDsdMeasureService;
	
	@Autowired
	protected DatasetService<CubeDsd> cubeDsdservice;
	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(
			HttpServletRequest request,
			CubeDsdMeasureSearch search,			
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST_DATACUBE + "]");
		
		return list(request, search, "", "", null, null, null, NO_HAY_SRID, LIST_DATACUBE,new CubeDsdMeasure(), new CubeDsdMeasureResult(), 
					 availableFields, getKey(), null,cubeDsdMeasureService);
	}
	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, CubeDsdMeasureSearch search,
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[listHead][" + LIST + "]");		
		return list(request,search, headersRequest);

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
			ModelAndView mv, HttpServletRequest request)
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";		
		
		return listHTML(mv, request, NO_HAY_SRID, null, null, null, params, MODEL_VIEW_LIST);
	}
	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_DATACUBE,  VERSION_1+LIST_DATACUBE}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> listDataCube(HttpServletRequest request,
			@PathVariable String cubeId,
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[list][" + LIST_DATACUBE + "]");	
				
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
				
		List<CubeDsdMeasure> listado;
		try
		{				
			CubeDsd cubeDsd=cubeDsdservice.findById(getKey(), CubeDsd.class, cubeId);
			
			listado = cubeDsdMeasureService.findByCubeDsd(getKey(),cubeDsd);
			
			long total=cubeDsdMeasureService.rowCountByCubeDsd(getKey(), cubeDsd);			
			
			responseEntity = guardarResult(NO_HAY_SRID, listado, total, new CubeDsdMeasureResult(), request)	;	
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
	@RequestMapping(value= {LIST_DATACUBE+Constants.EXT_HTML, VERSION_1+LIST_DATACUBE+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listDataCubeHTML(
			ModelAndView mv, HttpServletRequest request)
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";		
		
		return listHTML(mv, request, NO_HAY_SRID, null, null,null,params, MODEL_VIEW_LIST);
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
		
	
	

	
	
	
	
	
	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST_DATACUBE,  VERSION_1+LIST_DATACUBE}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listDataCubeHead(HttpServletRequest request,
			@PathVariable String cubeId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST_DATACUBE + "]");		
		return listDataCube(request, cubeId, headersRequest);

	}
	
	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdMeasureResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable String measureId)
	{
		log.info("[record][" + RECORD + "]");
		log.debug("[parmam][measureId:" + measureId + "]");		
		
		return record(request, measureId, new CubeDsdMeasure(), new CubeDsdMeasureResult(), NO_HAY_SRID, nameController, RECORD, cubeDsdMeasureService,getKey());
	}
	
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, @PathVariable String measureId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, measureId);
		
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
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String measureId, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, measureId, MODEL_VIEW_ID);
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
