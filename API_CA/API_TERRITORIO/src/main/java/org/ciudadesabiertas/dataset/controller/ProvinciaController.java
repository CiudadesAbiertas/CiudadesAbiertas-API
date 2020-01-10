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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.Autonomia;
import org.ciudadesabiertas.dataset.model.Pais;
import org.ciudadesabiertas.dataset.model.Provincia;
import org.ciudadesabiertas.dataset.utils.Geometry;
import org.ciudadesabiertas.dataset.utils.GeometryResult;
import org.ciudadesabiertas.dataset.utils.ProvinciaResult;
import org.ciudadesabiertas.dataset.utils.ProvinciaSearch;
import org.ciudadesabiertas.dataset.utils.ProvinciaSearchQuery;
import org.ciudadesabiertas.dataset.utils.TerritorioConstants;
import org.ciudadesabiertas.dataset.utils.TerritorioUtil;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.CoordinateTransformer;
import org.ciudadesabiertas.utils.DatasetSearch;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.ObjectResult;
import org.ciudadesabiertas.utils.RequestType;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONObject;
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
@Api(value="Territorio - Provincia",description = "Conjunto de operaciones relacionadas con el conjunto de datos Sección Censal", tags= {"Territorio - Provincia"})
public class ProvinciaController extends GenericController implements CiudadesAbiertasController 
{
	

	public static final String LIST = "/territorio/provincia";
	
	public static final String GEO_LIST = LIST+ "/geo";
	
	public static final String SEARCH_DISTINCT = LIST+"/distinct";
	
	public static final String RECORD = LIST+"/{id}";
	
	public static final String GEOMETRY = LIST+"/{id}/geometry";
	
	
	public static final String MODEL_VIEW_LIST = "territorio/provincia/list";
	public static final String MODEL_VIEW_ID = "territorio/provincia/id";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = ProvinciaController.class.getName();

	private static final String NAME_FIELD_GEOJSON = "CPRO";
	
	private static Map<String,JSONObject> geojson;
	
	

	static {		
		//Carga por defecto de las peticiones
		listRequestType.add(new RequestType("PROVINCIA_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("PROVINCIA_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("PROVINCIA_GEOMETRY", GEOMETRY, HttpMethod.GET,Constants.NO_AUTH));
		
		//Carga de las diferentes secciones en geojson
		geojson=TerritorioUtil.readGeoJSON(TerritorioConstants.provinciaFilePath, NAME_FIELD_GEOJSON);		
	}
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(Provincia.class);

	private static final Logger log = LoggerFactory.getLogger(ProvinciaController.class);
		

	@Autowired
	protected DatasetService<Provincia> service;
	
	@Autowired
	protected DatasetService<Pais> servicePais;
	
	@Autowired
	protected DatasetService<Autonomia> serviceAutonomia;


	
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
															@RequestParam(value = Constants.PAGE, defaultValue = Constants.defaultPage+"", required = false) String page,
															@RequestParam(value = Constants.PAGESIZE, defaultValue = Constants.defaultGroupByPageSize+"", required = false) String pageSize)
	{

		log.info("[distinctSearch][" + SEARCH_DISTINCT + "]");

		log.debug("[parmam][field:" + search.getField() + "] ");
		

		return distinctSearch(request, search, availableFields, page, pageSize,getKey(),NO_HAY_SRID, SEARCH_DISTINCT, new Provincia(), new ObjectResult(),  service);

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
			ModelAndView mv, HttpServletRequest request,ProvinciaSearch search, 
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
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String id, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, NO_HAY_SRID, id, MODEL_VIEW_ID);
	}
	
	
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=ProvinciaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, ProvinciaSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) String sort,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId,						
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [rsqlQ:" + rsqlQ + "] [sort:" + sort + "]");
		
		RSQLVisitor<CriteriaQuery<Provincia>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Provincia>();
		
		ResponseEntity list= listProvincia(request, search, fields, rsqlQ, page, pageSize, sort, srId, LIST,new Provincia(), new ProvinciaResult(), 
					 availableFields, getKey(), visitor,service);
		
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = list.getBody();
			
			Result<Provincia> result=((Result<Provincia>)body);
			
			List<Provincia> records = result.getRecords();
			
			if (srId.equals(""))
			{
				srId=StartVariables.SRID_XY_APP;
			}
			
			for (Provincia provincia:records) {	
				TerritorioUtil.addPolygon(isSemantic, srId, provincia, geojson);
			}
			
		
		}
		
		return list;

	}
	

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, ProvinciaSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) String sort,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search, fields, rsqlQ, page, pageSize, sort, srId, headersRequest);

	}
		
	

	
	
	
	
		

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=ProvinciaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable String id,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][id:" + id + "]");
				
		ResponseEntity record = recordByIndentifier(request, id, new Provincia(),new ProvinciaResult(), srId, nameController, RECORD, service,getKey());
		
		HttpStatus statusCode = record.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = record.getBody();
			
			Result<Provincia> result=((Result<Provincia>)body);
			
			List<Provincia> records = result.getRecords();
			
			if (srId.equals(""))
			{
				srId=StartVariables.SRID_XY_APP;
			}
			
			for (Provincia provincia:records) {	
				TerritorioUtil.addPolygon(isSemantic, srId, provincia, geojson);
			}
		}
		
		return record;

	}
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=ProvinciaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, @PathVariable String id,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, id, srId);
		
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.GEOMETRIA, notes = SwaggerConstants.GEOMETRIA_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=GeometryResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {GEOMETRY,  VERSION_1+GEOMETRY}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> geometry(HttpServletRequest request, 
													@PathVariable String id,
													@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) @ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId)
	{

		log.info("[record][" + GEOMETRY + "]");

		log.debug("[parmam][id:" + id + "]");
		
		ResponseEntity geometryRecord = null;
		
		ResponseEntity record = recordByIndentifier(request, id, new Provincia(), new ProvinciaResult(), srId, nameController, RECORD, service,getKey());
		
		HttpStatus statusCode = record.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
			
			Object body = record.getBody();
			
			Result<Provincia> result=((Result<Provincia>)body);
			Result<Object> resultObject=((Result<Object>)body);
			
			List<Provincia> records = result.getRecords();
			
			if (srId.equals(""))
			{
				srId=StartVariables.SRID_XY_APP;
			}
			
			Provincia provincia=records.get(0);
			TerritorioUtil.addPolygon(isSemantic, srId, provincia, geojson);
			
			List listado=new ArrayList();
			
			if (isSemantic)
			{
				Geometry g=new Geometry();
				g.setId(provincia.getTitle());
				g.setTypeForURI("Provincia");
				g.setGeometry(provincia.getHasGeometry());
				listado.add(g);
			}
			else
			{
				listado.add(provincia.getHasGeometry());
			}									
			
			resultObject.setRecords((List<Object>) listado);
			
			//MD5
			resultObject.setContentMD5(Util.generateHash( provincia.getHasGeometry().toString() ));
			
			//Cabeceras no se estaban incluyendo
			HttpHeaders headers = Util.extractHeaders(resultObject);
			geometryRecord = new ResponseEntity<Object>(resultObject,headers, HttpStatus.OK);
			
		
			return geometryRecord;
		}
		
		return record;
		
		

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
		return TerritorioConstants.KEY;
	}

	@Override
	public String getListURI()
	{	
		return LIST;
	}

	
/** METODOS PRIVADOS **/
	
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
	private  ResponseEntity<?> listProvincia(HttpServletRequest request, ProvinciaSearch search, 
			 String fields, 
			 String rsqlQ, 
			 String page, 
			 String pageSize,
			 String sort,			
			 String srId,	
			 String operacion,
			 Provincia objModel,
			 ProvinciaResult objResult,
			 List<String> availableFields,
			 String key,
			 RSQLVisitor<CriteriaQuery<Provincia>, EntityManager> visitor,
			 DatasetService<Provincia> dsService)
	{

		log.info("[list][" + operacion + "]");

		log.info("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [sort:" + sort + "]");
				
		//Verifico la negociación de contenidos
		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null){
			return negotiationResponseEntity;
		}
		
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
				fieldsQuery=Arrays.asList(fields.split(","));
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
				
				rsqlQ = TerritorioUtil.rsqlTerritorio(rsqlQ);
				
				Result<Provincia> result =dsService.searchByRSQLQuery(visitor,key, rsqlQ, numPage, numPageSize, orders);				
				
				List<Provincia> records = result.getRecords();				
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
			List<Provincia> listado;
			try
			{
				ProvinciaSearchQuery searchQuery = (new ProvinciaSearchQuery()).toTransform(search);	
				
				if (search.getAutonomia()!=null) {
					Autonomia objAutonomia=serviceAutonomia.findByIdentifier(key, (Class<Autonomia>) (new Autonomia()).getClass(), search.getAutonomia());
					if (objAutonomia!=null) {
						searchQuery.setAutonomiaObject(objAutonomia);	
						searchQuery.setAutonomia(null);
					}else {
						return responseEntity = guardarResult(srId, new ArrayList<Provincia>(), 0, objResult, request)	;
					}					
				}
				
				// control para Pais
				if (search.getPais()!=null) {
					Pais objPais=servicePais.findByIdentifier(key, (Class<Pais>) (new Pais()).getClass(), search.getPais());
					if (objPais!=null) {
						searchQuery.setPaisObject(objPais);	
						searchQuery.setPais(null);
						
					}else {
						return responseEntity = guardarResult(srId, new ArrayList<Provincia>(), 0, objResult, request)	;
					}
				}
				
				
			
				
				listado = dsService.query(key,(Class<Provincia>) objModel.getClass(),(DatasetSearch<Provincia>) searchQuery, numPage, numPageSize,fieldsQuery, orders);														
				
				long total=dsService.rowcount(key,(Class<Provincia>) objModel.getClass(),(DatasetSearch<Provincia>) searchQuery);				
				
				responseEntity = guardarResult(srId, listado, total, objResult, request)	;				
						
				
			} catch (Exception e)
			{
				log.error("internal error",e);
				responseEntity=ExceptionUtil.checkException(e);
			}		
		}
		
		return responseEntity;
	}

}
