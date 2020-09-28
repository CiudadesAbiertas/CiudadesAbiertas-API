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
import org.ciudadesabiertas.dataset.model.ConvRelFirmanteAyto;
import org.ciudadesabiertas.dataset.model.Convenio;
import org.ciudadesabiertas.dataset.model.ConvenioDocumento;
import org.ciudadesabiertas.dataset.model.ConvenioSuscEntidad;
import org.ciudadesabiertas.dataset.model.ConvenioOrganization;
import org.ciudadesabiertas.dataset.utils.ConvRelFirmanteAytoSearch;
import org.ciudadesabiertas.dataset.utils.ConvenioConstants;
import org.ciudadesabiertas.dataset.utils.ConvenioDocumentoSearch;
import org.ciudadesabiertas.dataset.utils.ConvenioResult;
import org.ciudadesabiertas.dataset.utils.ConvenioSearch;
import org.ciudadesabiertas.dataset.utils.ConvenioSuscEntidadSearch;
import org.ciudadesabiertas.dataset.utils.ConvenioOrganizationSearch;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DistinctSearch;
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
@Api(value = "Convenio - Convenio", description = "Conjunto de operaciones relacionadas con el conjunto de datos Convenio - Convenio", tags = {
		"Convenio - Convenio" })
public class ConvenioController extends GenericController implements CiudadesAbiertasController {
	public static final String LIST = "/convenio/convenio";

	public static final String GEO_LIST = LIST + "/geo";

	public static final String SEARCH_DISTINCT = LIST + "/distinct";

	public static final String RECORD = LIST + "/{id}";

	public static final String TRANSFORM = LIST + "/transform";

	public static final String ADD = LIST;
	public static final String UPDATE = LIST + "/{id}";
	public static final String DELETE = LIST + "/{id}";

	public static final String MODEL_VIEW_LIST = "convenio/convenio/list";
	public static final String MODEL_VIEW_ID = "convenio/convenio/id";

	private static List<RequestType> listRequestType = new ArrayList<RequestType>();

	private static String nameController = ConvenioController.class.getName();

	// Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("Convenio_LIST", LIST, HttpMethod.GET, Constants.NO_AUTH));
		listRequestType.add(new RequestType("Convenio_RECORD", RECORD, HttpMethod.GET, Constants.NO_AUTH));
		listRequestType.add(new RequestType("Convenio_TRANSFORM", TRANSFORM, HttpMethod.POST, Constants.NO_AUTH));

		listRequestType.add(new RequestType("Convenio_ADD", ADD, HttpMethod.POST, Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("Convenio_UPDATE", UPDATE, HttpMethod.PUT, Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("Convenio_DELETE", DELETE, HttpMethod.DELETE, Constants.BASIC_AUTH));

	}

	public static List<String> availableFields = Util.extractPropertiesFromBean(Convenio.class);

	private static final Logger log = LoggerFactory.getLogger(ConvenioController.class);

	@Autowired
	protected DatasetService<Convenio> service;

	@Autowired
	protected DatasetService<ConvRelFirmanteAyto> serviceFirmanteAyto;

	@Autowired
	protected DatasetService<ConvenioSuscEntidad> serviceSuscEntidad;

	@Autowired
	protected DatasetService<ConvenioDocumento> serviceDocumento;
	
	@Autowired
	protected DatasetService<ConvenioOrganization> serviceOrganizacion;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.BUSQUEDA_DISTINCT, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_DISTINCT, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_DISTINCT, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { SEARCH_DISTINCT, VERSION_1 + SEARCH_DISTINCT }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> distinctSearch(HttpServletRequest request, DistinctSearch search,
			@RequestParam(value = Constants.PAGE, defaultValue = Constants.defaultPage
					+ "", required = false) 
			@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page,
			@RequestParam(value = Constants.PAGESIZE, defaultValue = Constants.defaultGroupByPageSize
					+ "", required = false) 
			@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize) {

		log.info("[distinctSearch][" + SEARCH_DISTINCT + "]");

		log.debug("[parmam][field:" + search.getField() + "] ");

		return distinctSearch(request, search, availableFields, page, pageSize, getKey(), NO_HAY_SRID, SEARCH_DISTINCT,
				new Convenio(), new ConvenioResult(), service);

	}

	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({ @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { LIST + Constants.EXT_HTML,
			VERSION_1 + LIST + Constants.EXT_HTML }, method = RequestMethod.GET)
	public ModelAndView listHTML(ModelAndView mv, HttpServletRequest request, ConvenioSearch search,
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ,
			@RequestParam(value = Constants.PAGE, defaultValue = "", required = false) String page,
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,
			@RequestParam(value = Constants.SORT, defaultValue = "", required = false) String sort) {

		log.info("[listHTML][" + LIST + ".html]");

		String params = "?";
		if (Util.validValue(rsqlQ)) {
			params += "&q=" + rsqlQ;
		} else if (search != null) {
			params += search.toUrlParam();
		}

		return listHTML(mv, request, NO_HAY_SRID, page, pageSize, sort, params, MODEL_VIEW_LIST);
	}

	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.FICHA_HTML, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA, response = ModelAndView.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { RECORD + Constants.EXT_HTML,
			VERSION_1 + RECORD + Constants.EXT_HTML }, method = RequestMethod.GET)
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String id, HttpServletRequest request) {
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");

		return recordHTML(mv, request, NO_HAY_SRID, id, MODEL_VIEW_ID);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { LIST, VERSION_1 + LIST }, method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, ConvenioSearch search,
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
			@RequestHeader HttpHeaders headersRequest) {

		log.info("[list][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [rsqlQ:" + rsqlQ
				+ "] [sort:" + sort + "]");

		RSQLVisitor<CriteriaQuery<Convenio>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Convenio>();

		ResponseEntity<Convenio> list = list(request, search, fields, rsqlQ, page, pageSize, sort, NO_HAY_SRID, LIST,
				new Convenio(), new ConvenioResult(), availableFields, getKey(), visitor, service);

		// TODO esto se deberia realizar en un unico metodo y controlar todos los
		// propeties por interfaz
		return integraAll(list, request);

	}

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({ @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { LIST, VERSION_1 + LIST }, method = { RequestMethod.HEAD })
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, ConvenioSearch search,
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
			@RequestHeader HttpHeaders headersRequest) {

		log.info("[listHead][" + LIST + "]");
		return list(request, search, fields, rsqlQ, page, pageSize, sort, headersRequest);

	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = { ADD,
			VERSION_1 + ADD }, method = RequestMethod.POST, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.INSERCION, notes = SwaggerConstants.DESCRIPCION_INSERCION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_INSERCION, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	public @ResponseBody ResponseEntity<?> add(
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CONVENIO_TEXT) @RequestBody Convenio obj) {

		log.info("[add][" + ADD + "]");

		log.debug("[parmam][dato:" + obj + "] ");

		List<String> erroresFK = checkClavesExternas(obj.getId(), obj.getOrganizationId(), obj.getOrgIdObligadoPrestacion(), obj.getEsVariacionId(), Constants.BASIC_OPERATION_ADD);
		
		log.info("[add][" + ADD + "] [No hay erroresFK]["+erroresFK.isEmpty()+"]");
		
		return add(obj, nameController, ADD, service,getKey(), erroresFK);
				

	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.MODIFICACION, notes = SwaggerConstants.DESCRIPCION_MODIFICACION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_MODIFICACION, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { UPDATE,
			VERSION_1 + UPDATE }, method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8")
	public @ResponseBody ResponseEntity<?> update(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT) @PathVariable(Constants.IDENTIFICADOR) String id,
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CONVENIO_TEXT) @RequestBody Convenio obj) {

		log.info("[update][" + UPDATE + "]");

		log.debug("[parmam][id:" + id + "] [dato:" + obj + "] ");
		
		List<String> erroresFK = checkClavesExternas(obj.getId(), obj.getOrganizationId(), obj.getOrgIdObligadoPrestacion(), obj.getEsVariacionId(), Constants.BASIC_OPERATION_ADD);
		
		log.info("[update][" + UPDATE + "] [No hay erroresFK]["+erroresFK.isEmpty()+"]");
		
		return update(id, obj, nameController, UPDATE, service, getKey(), erroresFK);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.DELETE, notes = SwaggerConstants.DESCRIPCION_DELETE, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_DELETE, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { DELETE, VERSION_1 + DELETE }, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT) @PathVariable(Constants.IDENTIFICADOR) String id) {

		log.info("[delete][" + DELETE + "]");

		log.debug("[parmam][id:" + id + "] ");

		List<String> erroresFK = checkClavesExternas(id, null, null, null, Constants.BASIC_OPERATION_DELETE);
		
		log.info("[delete][" + DELETE + "] [No hay erroresFK]["+erroresFK.isEmpty()+"]");

		return delete(id, new Convenio(), nameController, DELETE, service, getKey(), erroresFK);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { RECORD, VERSION_1 + RECORD }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, 
				@PathVariable 
					@ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CONVENIO) String id) {

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][id:" + id + "]");

		ResponseEntity<Convenio> record = record(request, id, new Convenio(), new ConvenioResult(), NO_HAY_SRID,
				nameController, RECORD, service, getKey());
		// TODO mismo comentario que el list
		return integraAll(record, request);

	}

	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO, response = ResultError.class),
			@ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	@RequestMapping(value = { RECORD, VERSION_1 + RECORD }, method = RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request,
			@PathVariable 
				@ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_CONVENIO) String id) {

		log.info("[recordHead][" + RECORD + "]");
		return record(request, id);

	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = { TRANSFORM,
			VERSION_1 + TRANSFORM }, method = RequestMethod.POST, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.TRANSFORMACION, notes = SwaggerConstants.DESCRIPCION_TRANSFORMACION, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA, response = ConvenioResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	public @ResponseBody ResponseEntity<?> transform(
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_CONVENIO_TEXT) @RequestBody Convenio obj) {

		log.info("[transform]");

		log.debug("[parmam][obj:" + obj + "]");

		return transform(obj, nameController, TRANSFORM);

	}

	@Override
	public ArrayList<SecurityURL> getURLsWithRoles() {

		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();

		Properties properties = mConf.getDatabasesConfig().get(getKey());

		listRequestType = Util.getRequestType(properties, getKey(), listRequestType);

		for (RequestType rObj : listRequestType) {
			urlRoles.add(rObj.getSecurityURL());
		}

		return urlRoles;
	}

	@Override
	public String getKey() {
		return ConvenioConstants.KEY;
	}

	@Override
	public String getListURI() {
		return LIST;
	}

	/**
	 * *********************************************************** METODOS
	 * ESPECIFICOS PARA EL CONTROLADOR - NO GENERALIZABLES
	 * *********************************************************** || || \/ \/
	 */

	@SuppressWarnings("unchecked")
	private ResponseEntity<?> integraSubvencion(ResponseEntity<Convenio> list, HttpServletRequest request) {

		HttpStatus statusCode = list.getStatusCode();

		if (statusCode.is2xxSuccessful()) {
			boolean isSemantic = Util.isSemanticPetition(request);

			if (isSemantic) {

				Object body = list.getBody();

				Result<Convenio> result = ((Result<Convenio>) body);

				List<Convenio> records = result.getRecords();

				if (Util.isSubvencionIntegration()) {
					for (Convenio convenio : records) {
						// CMG Controlamos en la integración con subvencio, que tenga valor subvencioId
						if (Util.validValue(convenio.getSubvencionId())) {
							convenio.setFechaAdjudicacionSub(null);
							convenio.setImporteSubv(null);
							convenio.setAdjudicatarioTitleSub(null);
						}
					}
				} else {
					for (Convenio convenio : records) {
						convenio.setSubvencionIdIsolated(convenio.getSubvencionId());
						convenio.setSubvencionId(null);
					}
				}
			}
		}

		return list;
	}

	/**
	 * Lógica para controlar si el convenio se gestiona por una Organizacion o por un distrito
	 * @param list
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseEntity<?> gestionadoPor(ResponseEntity<Convenio> list,  HttpServletRequest request) {

		HttpStatus statusCode = list.getStatusCode();

		if (statusCode.is2xxSuccessful()) {
			boolean isSemantic = Util.isSemanticPetition(request);

			if (isSemantic) {

				Object body = list.getBody();

				Result<Convenio> result = ((Result<Convenio>) body);

				List<Convenio> records = result.getRecords();
				
				for (Convenio convenio : records) {
					// CMG Controlamos en la integración con subvencio, que tenga valor subvencioId
					if ( Util.validValue(convenio.getGestionadoPorOrganization()) && convenio.getGestionadoPorOrganization()) {
						convenio.setDistritoId(null);
						convenio.setDistritoTitle(null);
						convenio.setMunicipioId(null);
						convenio.setMunicipioTitle(null);
					}else if (Util.validValue(convenio.getGestionadoPorDistrito()) && convenio.getGestionadoPorDistrito()) {
						convenio.setOrganizationId(null);
					}else {
						//TODO Si viene las dos a false
//						convenio.setDistritoId(null);
//						convenio.setDistritoTitle(null);
//						convenio.setMunicipioId(null);
//						convenio.setMunicipioTitle(null);
//						convenio.setConvenioOrganizationId(null);
					}
			
				}
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<?> integraAll(ResponseEntity<Convenio> list, HttpServletRequest request) {

		if (list != null) {
			// TODO generar todas las integraciones
			list = (ResponseEntity<Convenio>) integraSubvencion(list, request);
			list = (ResponseEntity<Convenio>) gestionadoPor(list, request);
		}

		return list;
	}

	private List<String> checkClavesExternas(String id, String organizationId, String orgIdObligadoPrestacion, String esVariacionId, String operation) {
		List<String> errores = new ArrayList<String>();
		// Chequeamos los identificadores que apuntan a otras tablas
		if (Util.validValue(id) && activeFK) {
			try {
				
				if (operation.equals(Constants.BASIC_OPERATION_ADD)||(operation.equals(Constants.BASIC_OPERATION_UPDATE)))
				{
					
					if (organizationId!=null)
					{
						ConvenioOrganizationSearch orgSearch=new ConvenioOrganizationSearch();
						orgSearch.setId(organizationId);
						long rowcount = serviceOrganizacion.rowcount(getKey(), ConvenioOrganization.class, orgSearch);
						if (rowcount==0)			
						{
							errores.add("The organization '"+organizationId+"' does not exist");		
						}
					}
					
					if (orgIdObligadoPrestacion!=null)
					{
						ConvenioOrganizationSearch orgSearch=new ConvenioOrganizationSearch();
						orgSearch.setId(orgIdObligadoPrestacion);
						long rowcount = serviceOrganizacion.rowcount(getKey(), ConvenioOrganization.class, orgSearch);
						if (rowcount==0)			
						{
							errores.add("The organization '"+orgIdObligadoPrestacion+"' does not exist");		
						}
					}
					
					if (esVariacionId!=null)
					{
						ConvenioSearch convSearch=new ConvenioSearch();
						convSearch.setId(esVariacionId);
						long rowcount = service.rowcount(getKey(), Convenio.class, convSearch);
						if (rowcount==0)			
						{
							errores.add("The convenio '"+esVariacionId+"' does not exist");		
						}
					}
				
				}else if (operation.equals(Constants.BASIC_OPERATION_DELETE))
				{
					// 1 CHECK
					ConvenioDocumentoSearch documentSearch = new ConvenioDocumentoSearch();
					documentSearch.setConvenioId(id);
					long rowcount = serviceDocumento.rowcount(getKey(), ConvenioDocumento.class, documentSearch);
					if (rowcount > 0) {
						errores.add("This Convenio is used " + rowcount + " times in ConvenioDocumento");
					}
					// 2 CHECK
					ConvRelFirmanteAytoSearch convRelFirmAyto = new ConvRelFirmanteAytoSearch();
					convRelFirmAyto.setConvenioId(id);
					rowcount = serviceFirmanteAyto.rowcount(getKey(), ConvRelFirmanteAyto.class, convRelFirmAyto);
					if (rowcount > 0) {
						errores.add("This Convenio is used " + rowcount + " times in ConvRelFirmanteAyto");
					}
	
					// 3 CHECK
					ConvenioSuscEntidadSearch conveioSucEntidadSerach = new ConvenioSuscEntidadSearch();
					conveioSucEntidadSerach.setConvenioId(id);
					rowcount = serviceSuscEntidad.rowcount(getKey(), ConvenioSuscEntidad.class, conveioSucEntidadSerach);
					if (rowcount > 0) {
						errores.add("This Convenio is used " + rowcount + " times in ConvenioSuscEntidad");
					}
				}

			} catch (DAOException e) {
				log.error("[checkClavesExternasRowCount] [convenioId:" + id + "] [ERROR:" + e.getMessage() + "]");
				errores.add("[checkClavesExternasRowCount] [convenioId:" + id + "] [ERROR:" + e.getMessage() + "]");
			}

		}

		return errores;
	}

}
