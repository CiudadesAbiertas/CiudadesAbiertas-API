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

package org.ciudadesabiertas.dataset.busquedaIndexada.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.dataset.busquedaIndexada.service.BusquedaIndexadaService;
import org.ciudadesabiertas.dataset.busquedaIndexada.util.BusquedaIndexadaResult;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RestController
@Api(value="BusquedaIndexada",description = "Búsqueda sobre conjuntos de datos indexados en Solr", tags= {"Búsqueda Indexada"})
public class BusquedaIndexadaController implements CiudadesAbiertasController
{
	public static final String SOLR_LIST = "/busquedaIndexada";
		
	public static final String VERSION_1 = "v1/";
	
	public static final String MODEL_VIEW_LIST = "busquedaIndexada/list";
	
	private static final Logger log = LoggerFactory.getLogger(BusquedaIndexadaController.class);

	@Autowired
	private BusquedaIndexadaService solrService;	
	
	
	//http://localhost:8080/API/solr.json?query=madrid
	//http://localhost:8080/API/solr.json?dataset=agenda&query=madrid	
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_INDEXADA_RESPONSE, authorizations = { @Authorization(value=Constants.APIKEY) })
		@ApiResponses({
		            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=BusquedaIndexadaResult.class),
		            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
		            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
		            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
		   })
		@RequestMapping(value= {SOLR_LIST,  VERSION_1+SOLR_LIST}, method = {RequestMethod.GET})	
		public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, 
				@RequestParam(value = "dataset", defaultValue = "", required = false) String dataset, 
				@RequestParam(value = "query", required = true) String query,
				@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, 
				@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,			
				@RequestHeader HttpHeaders headersRequest)
		{

			log.info("[list][" + SOLR_LIST + "]");

			log.info("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] ");
			
			//Verifico la negociación de contenidos
			/*
			ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
			if (negotiationResponseEntity!=null){
				return negotiationResponseEntity;
			}
			*/
			ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
			
			int numPage = Constants.defaultPage;
			int numPageSize =StartVariables.defaultPageSize;		
			if (pageSize.equals(""))
			{
				pageSize=Integer.toString(StartVariables.defaultPageSize);
			}
			try
			{
				numPage = Integer.parseInt(page);
		
				numPageSize = Integer.parseInt(pageSize);
		
				if (numPageSize < 0|| numPageSize > StartVariables.maxPageSize)
				{
					numPageSize = StartVariables.defaultPageSize;			
				}
			}
			catch (NumberFormatException e)
			{
				log.error("Error parsing page numbers",e);
				responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong page information"));
				return responseEntity;
			}
					
			try
			{
				BusquedaIndexadaResult solrResult = solrService.query(dataset, query, numPage, numPageSize);
			
				Map<String, String> pageMetadataCalculation = Util.pageMetadataCalculation(request,solrResult.getTotalRecords(),numPageSize);
				
				//Pagina actual
				solrResult.setSelf(pageMetadataCalculation.get(Constants.SELF));				
				//Pagina inicial				
				solrResult.setFirst(pageMetadataCalculation.get(Constants.FIRST));
				//Ultima página				
				solrResult.setLast(pageMetadataCalculation.get(Constants.LAST));
				//Pagina siguiente
				solrResult.setNext(pageMetadataCalculation.get(Constants.NEXT));				
				//Pagina anterior
				solrResult.setPrev(pageMetadataCalculation.get(Constants.PREV));
				//MD5
				solrResult.setContentMD5(Util.generateHash( Util.toString(solrResult.getRecords()) ));
				
				solrResult.setPage(numPage);
				solrResult.setPageSize(numPageSize);
				solrResult.setPageRecords(solrResult.getRecords().size());				
				solrResult.setTotalRecords(solrResult.getTotalRecords());
				
				solrResult.setStatus(200);
				
				HttpHeaders headers = Util.extractHeaders(solrResult);				
				
				responseEntity=new ResponseEntity<Object>(solrResult,headers,HttpStatus.OK);
				
				
				
				responseEntity=new ResponseEntity<Object>(solrResult,HttpStatus.OK);
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
	@RequestMapping(value= {SOLR_LIST+Constants.EXT_HTML,  VERSION_1+SOLR_LIST+Constants.EXT_HTML}, method = {RequestMethod.GET})
	public ModelAndView listHTML(HttpServletRequest request, ModelAndView mv, String dataset, String query,
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) String pageSize,			
			@RequestHeader HttpHeaders headersRequest)	
	{
		String theURL=request.getRequestURL().toString().replace(Constants.EXT_HTML, Constants.EXT_JSON);		
		
		log.info("[listHTML][" + SOLR_LIST + ".html]");
		
		String params="?";
		if (Util.validValue(dataset))	{
			params+="&dataset="+dataset;			
		}		
		
		if (Util.validValue(query))	{
			params+="&query="+query;			
		}		
			
		if (Util.validValue(page)) {			
			params+="&page="+page;						
		}
		
		if (Util.validValue(pageSize)) {			
			params+="&pageSize="+pageSize;			
		}
				
		if ("?".equals(params)) {
			params="";
		}else {
			params=params.replace("?&", "?");
		}
		
		mv.addObject("theURL", theURL+params);		
		mv.setViewName(MODEL_VIEW_LIST);
		return mv;
	}
	
	
	
	@Override
	public String getKey()
	{		
		return "busquedaIndexada";
	}


	@Override
	public ArrayList<SecurityURL> getURLsWithRoles()
	{
		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();		
		return urlRoles;
	}		
	
	@Override
	public String getListURI()
	{
		return SOLR_LIST;
	}
}
