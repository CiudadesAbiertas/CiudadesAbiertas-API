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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.dataset.model.CubeDsdDimension;
import org.ciudadesabiertas.dataset.model.CubeDsdDimensionValue;
import org.ciudadesabiertas.dataset.service.CubeDsdDimensionService;
import org.ciudadesabiertas.dataset.service.CubeDsdDimensionValueService;
import org.ciudadesabiertas.dataset.utils.CubeDsdConstants;
import org.ciudadesabiertas.dataset.utils.CubeDsdDimensionResult;
import org.ciudadesabiertas.dataset.utils.CubeDsdDimensionSearch;
import org.ciudadesabiertas.dataset.utils.CubeDsdResult;
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
@Api(value="DSD",description = "Conjunto de operaciones relacionadas con las definiciones de estructuras de datos (DSD - Datacubes)"+SwaggerConstants.VOCABULARIO_A_HREF+CubeDsdConstants.dimensionPropertyVocabURL+SwaggerConstants.VOCABULARIO_A_HREF_END, tags= {"DSD - Dimensión"})
public class CubeDsdDimensionController extends GenericController implements CiudadesAbiertasController 
{	
	public static final String LIST = "/data-cube/data-structure-definition/dimension";	
	public static final String RECORD = "/data-cube/data-structure-definition/dimension/{dimensionId}";	
	public static final String DATACUBE_LIST = "/data-cube/data-structure-definition/{cubeId}/dimension";	
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = CubeDsdDimensionController.class.getName();
	
	private static Map<String,String> dimensionFromExtraValues=null;
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("DIMENSION_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("DIMENSION_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("DATACUBE_DIMENSION_LIST", DATACUBE_LIST, HttpMethod.GET,Constants.NO_AUTH));
		}
	
	public static final String MODEL_VIEW_LIST = "dsd/dimensionList";
	public static final String MODEL_VIEW_ID = "dsd/dimensionId";
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(CubeDsdDimension.class);

	private static final Logger log = LoggerFactory.getLogger(CubeDsdDimensionController.class);
		

	@Autowired
	protected CubeDsdDimensionService cubeDsdDimensionService;
	
	@Autowired
	protected DatasetService<CubeDsd> cubeDsdservice;
	
	@Autowired
	protected CubeDsdDimensionValueService cubeDsdDimensionValueService;

	
	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {DATACUBE_LIST,  VERSION_1+DATACUBE_LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> listDataCube(HttpServletRequest request,
			@PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DSD) String cubeId,
			@RequestHeader HttpHeaders headersRequest)
	{
		log.info("[list][" + LIST + "]");	
				
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
		
		List<CubeDsdDimension> listado;
		try
		{	
			
			CubeDsd cubeDsd=cubeDsdservice.findById(getKey(), CubeDsd.class, cubeId);
			
			listado = cubeDsdDimensionService.findByCubeDsd(getKey(),cubeDsd);
			
			for (CubeDsdDimension dimension:listado) {	
				addDimensionValues(isSemantic, dimension);
			}
			
			if (isSemantic==false)
			{
				manageDimensionFromMultipleValues(listado, false);	
			}
			
			
			long total=cubeDsdDimensionService.rowCountByCubeDsd(getKey(), cubeDsd);			
			
			//responseEntity = guardarResult(new CubeDsdDimensionResult(),"",listado, total, request);				
			
			if (total<listado.size())
			{
				total=listado.size();
			}
			
			
			responseEntity = guardarResult(NO_HAY_SRID, listado, total, new CubeDsdDimensionResult(), request)	;	
		} catch (Exception e)
		{
			log.error("internal error",e);
			responseEntity=ExceptionUtil.checkException(e);
		}		
		
		
		return responseEntity;
		
		
		
		
	}
	
	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {DATACUBE_LIST,  VERSION_1+DATACUBE_LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listDataCubeHead(HttpServletRequest request,
			@PathVariable @ApiParam(required=true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DSD) String cubeId,
			CubeDsdDimensionSearch search,			
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + DATACUBE_LIST + "]");		
		return listDataCube(request,cubeId, headersRequest);

	}
	
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {DATACUBE_LIST+Constants.EXT_HTML, VERSION_1+DATACUBE_LIST+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listDataCubeHTML(
			ModelAndView mv, HttpServletRequest request)	
	{
				
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";		
		
		return listHTML(mv, request, NO_HAY_SRID, null, null, null , params, MODEL_VIEW_LIST);
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
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, CubeDsdDimensionSearch search,						
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST + "]");
		
		
		
		RSQLVisitor<CriteriaQuery<CubeDsdDimension>, EntityManager> visitor = new JpaCriteriaQueryVisitor<CubeDsdDimension>();
		
		ResponseEntity list = list(request, search, "", "", null, null, null, NO_HAY_SRID, LIST,new CubeDsdDimension(), new CubeDsdDimensionResult(), 
				 availableFields, getKey(), visitor,cubeDsdservice); 
		
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = list.getBody();
			
			Result<CubeDsdDimension> result=((Result<CubeDsdDimension>)body);
			
			List<CubeDsdDimension> records = result.getRecords();
			
			for (CubeDsdDimension dimension:records) {	
				addDimensionValues(isSemantic, dimension);				
			}
			
			if (isSemantic==false)
			{
				manageDimensionFromMultipleValues(records, false);
				
				((Result<CubeDsdDimension>) result).setPageRecords(records.size());
				((Result<CubeDsdDimension>) result).setPageSize(records.size());
				((Result<CubeDsdDimension>) result).setTotalRecords(records.size());
			}
			
			
		}
		
		
		return list;
	}


	private void manageDimensionFromMultipleValues(List<CubeDsdDimension> records, boolean isRecord) {
		List<CubeDsdDimension> dimensionsToDelete=new ArrayList<CubeDsdDimension>();
		List<CubeDsdDimension> dimensionsToAdd=new ArrayList<CubeDsdDimension>();
		for (CubeDsdDimension dimension:records) {	
			if (Util.validValue(dimension.getMutipleField()))
			{
				dimensionsToDelete.add(dimension);
				String[] newDimensions = dimension.getMutipleField().split(",");
				for (String dim:newDimensions)
				{
					CubeDsdDimension newDimension=new CubeDsdDimension();
					newDimension.setId(dim);							
					dimensionsToAdd.add(newDimension);
				}
			}					
		}
		for (CubeDsdDimension dimension:dimensionsToDelete) {
			records.remove(dimension);
		}
		if (isRecord==false)
		{
			for (CubeDsdDimension dimension:dimensionsToAdd) {
				records.add(dimension);
			}
		}
	}

	
	private void addDimensionValues(boolean isSemantic, CubeDsdDimension dimension) {
		List<CubeDsdDimensionValue> dimensionValues = cubeDsdDimensionValueService.findByDimension(getKey(), dimension);
		
		List<String> values=new ArrayList<String>();		
		
		
		for (CubeDsdDimensionValue actualValue:dimensionValues)
		{
			if (isSemantic)
			{
				if (Util.validValue(dimension.getConceptScheme()))
				{
					values.add(dimension.getConceptScheme()+"/"+actualValue.getId());					
				}
				else
				{				
					values.add(CubeDsdDimensionValueController.RECORD.replace("{dimensionId}",dimension.getId()).replace("{id}",actualValue.getId()));
				}
			}else {
				values.add(actualValue.getId());
			}
		}
		
		dimension.setHasTopConcept(values);
	}
	
	
	
	


	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=CubeDsdDimensionResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId)
	{
		log.info("[record][" + RECORD + "]");
		log.debug("[parmam][id:" + dimensionId + "]");				
		
		dimensionId=Util.decodeURL(dimensionId);
		
		loadDimensionsFromMultiplesValues();
		
		boolean isSemantic=Util.isSemanticPetition(request);
		
		ResponseEntity record = record(request, dimensionId, new CubeDsdDimension(), new CubeDsdDimensionResult(), NO_HAY_SRID, nameController, RECORD, cubeDsdservice,getKey());
		
		HttpStatus statusCode = record.getStatusCode();
		
		if (statusCode.value()==404)
		{
			if (dimensionFromExtraValues.containsKey(dimensionId) && (isSemantic==false))
			{				
				CubeDsdDimensionResult resultObj=new CubeDsdDimensionResult();
				List<CubeDsdDimension> listado=new ArrayList<CubeDsdDimension>();
				CubeDsdDimension dim=new CubeDsdDimension();
				dim.setId(dimensionId);
				listado.add(dim);
				((Result<CubeDsdDimension>) resultObj).setPage(1);
				((Result<CubeDsdDimension>) resultObj).setPageRecords(1);
				((Result<CubeDsdDimension>) resultObj).setPageSize(1);
				((Result<CubeDsdDimension>) resultObj).setTotalRecords(1);
				((Result<CubeDsdDimension>) resultObj).setRecords((List<CubeDsdDimension>) listado);
				((Result<CubeDsdDimension>) resultObj).setStatus(200);
				//MD5
				((Result<CubeDsdDimension>) resultObj).setContentMD5(Util.generateHash( dim.toString() ));
				
				String selfRequest = Util.generateSelfRequest(request);		
				((Result<CubeDsdDimension>) resultObj).setSelf(selfRequest);
				
				//Cabeceras no se estaban incluyendo
				HttpHeaders headers = Util.extractHeaders((Result<CubeDsdDimension>) resultObj);
				ResponseEntity responseEntity = new ResponseEntity<Object>(resultObj,headers, HttpStatus.OK);
				return responseEntity;
				 
			}
		}		
	
		
		
		if (statusCode.is2xxSuccessful())
		{
			
			Object body = record.getBody();
			
			Result<CubeDsdDimension> result=((Result<CubeDsdDimension>)body);
			
			List<CubeDsdDimension> records = result.getRecords();
			
			for (CubeDsdDimension dimension:records) {	
				addDimensionValues(isSemantic, dimension);
			}
			
			if (isSemantic==false)
			{
				manageDimensionFromMultipleValues(records, true);
				
				((Result<CubeDsdDimension>)body).setPageRecords(records.size());
				((Result<CubeDsdDimension>)body).setPageSize(records.size());
				((Result<CubeDsdDimension>)body).setTotalRecords(records.size());
			}
			
		}
		
		return record;
		
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
	
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.FICHA_HTML, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=ModelAndView.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD+Constants.EXT_HTML, VERSION_1+RECORD+Constants.EXT_HTML}, method = RequestMethod.GET)
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String dimensionId, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, dimensionId, MODEL_VIEW_ID);
	}
	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, CubeDsdDimensionSearch search,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search, headersRequest);

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
	public @ResponseBody ResponseEntity<?> recordDataCubeHead(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CUBE_DIMENSION_ID) String dimensionId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, dimensionId);
		
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
	

}
