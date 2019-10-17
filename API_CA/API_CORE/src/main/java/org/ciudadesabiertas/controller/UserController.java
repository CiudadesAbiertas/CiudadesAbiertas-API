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
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.config.multipe.MultipleConf;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.service.UserService;
import org.ciudadesabiertas.users.model.User;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.ExceptionUtil;
import org.ciudadesabiertas.utils.RequestType;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RestController
public class UserController implements CiudadesAbiertasController
{

	
	public static final String LIST = "/user";
	
	public static final String ADD = "/user";
	
	public static final String UPDATE = "/user/{username}";
	
	public static final String DELETE = "/user/{username}";
	
	public static final String CHANGE_PASSWORD = "/user/{username}/{password}";

	public static final String VERSION_1 = "v1/";
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;	
	
	@Autowired
    public PasswordEncoder passwordEncoder;
	
	@Autowired
	protected MultipleConf mConf;
	
	@Autowired
	protected Environment env;
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	
	//Carga por defecto de las peticiones
		static {
			listRequestType.add(new RequestType("USER_LIST", LIST, HttpMethod.GET,Constants.ADMIN_AUTH));
			listRequestType.add(new RequestType("USER_ADD", ADD, HttpMethod.POST,Constants.ADMIN_AUTH));
			listRequestType.add(new RequestType("USER_UPDATE", UPDATE, HttpMethod.PUT,Constants.ADMIN_AUTH));
			listRequestType.add(new RequestType("USER_DELETE", DELETE, HttpMethod.DELETE,Constants.ADMIN_AUTH));
			listRequestType.add(new RequestType("USER_CHANGE_PASSWORD", CHANGE_PASSWORD, HttpMethod.PUT,Constants.ADMIN_AUTH));
		}

		
	@RequestMapping(LIST)
	public @ResponseBody ResponseEntity<?> listUser(HttpServletRequest request) 
	{

		log.info(LIST);
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);

		//Verifico la negociación de contenidos
//		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
//		if (negotiationResponseEntity!=null){
//			return negotiationResponseEntity;
//		}
		//invocamos al service
		List<User> findAll = userService.list();
		
		if (findAll==null || findAll.isEmpty()) {
			responseEntity=ExceptionUtil.checkException(new BadRequestException("Wrong Not Data found"));
		}else {
			responseEntity = guardarResult( findAll,  request);
		}
		
		
		return responseEntity;
	}
	
	@RequestMapping(value={ADD,  VERSION_1+ADD}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	public @ResponseBody ResponseEntity<?> addUser(	
			HttpServletRequest request,
			@RequestBody User obj 
			)
	{

		log.info("[userAdd][" + ADD + "]");

		log.debug("[parmam][dato:" + obj + "] ");
		
		@SuppressWarnings("unused")
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		//Verifico la negociación de contenidos
//		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
//		if (negotiationResponseEntity!=null){
//			return negotiationResponseEntity;
//		}
		
		List<String> validationUser = obj.validate();
		if (validationUser!=null && !validationUser.isEmpty() ) {
			String texto="";
			for (String aux:validationUser) {
				texto=""+aux+"\n";
			}
			return responseEntity=ExceptionUtil.checkException(new BadRequestException(texto));
		}else {
			//Verificamos que no exista el usuario
			User existe = userService.findByName(obj.getUsername());
			if (existe!=null) {
				return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to Add. [Username:"+obj.getUsername()+"]  Found in BBDD."));
			}			
			//invocamos al service
			obj.setPassword(passwordEncoder.encode(obj.getPassword()));
			userService.save(obj);
		}
								
		
		return listUser(request);
	}
	

	
	@RequestMapping(value={DELETE,  VERSION_1+DELETE}, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deleteUser(
			HttpServletRequest request,
			@PathVariable("username") String username)
	{

		log.info("[delete][" + DELETE + "]");

		log.debug("[parmam][username:" + username + "] ");			
		
		@SuppressWarnings("unused")
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		//Verifico la negociación de contenidos
//		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
//		if (negotiationResponseEntity!=null){
//			return negotiationResponseEntity;
//		}
		
		if (!Util.validValue(username) ) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to delete. [userName:"+username+"] Not Found in BBDD"));
		}
		
		//Verificamos que no exista el usuario
		User existe = userService.findByName(username);
		if (existe==null) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to DELETE . [Username:"+username+"] NOT Found in BBDD."));
		}	
				
		//invocamos al service
		userService.remove(existe);
		
		return listUser(request);
	}	
	
	@RequestMapping(value={UPDATE,  VERSION_1+UPDATE}, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateUser(
			HttpServletRequest request,
			@PathVariable("username") String username,
			@RequestBody User obj)
	{

		log.info("[updateUser][" + UPDATE + "]");

		log.debug("[parmam][userName:" + username + "] [User:" + obj + "]");			
		
		@SuppressWarnings("unused")
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		//Verifico la negociación de contenidos
//		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
//		if (negotiationResponseEntity!=null){
//			return negotiationResponseEntity;
//		}
		
		if (!Util.validValue(username) ) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to Update. [userName:"+username+"] Not Found in BBDD"));
		}
		
		//Verificamos que no exista el usuario
		User existe = userService.findByName(username);
		if (existe==null) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to Update. [Username:"+username+"]  NOT Found in BBDD."));
		}	
		
		obj.setUsername(username);
		
		
		List<String> validationUser = obj.validate();
		if (validationUser!=null && !validationUser.isEmpty() ) {
			String texto="";
			for (String aux:validationUser) {
				texto=""+aux+"\n";
			}
			return responseEntity=ExceptionUtil.checkException(new BadRequestException(texto));
		}
		
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
				
		//invocamos al service
		userService.updateUser(obj);
		
		return listUser(request);
	}	
	
	@SuppressWarnings("unused")
	@RequestMapping(value={CHANGE_PASSWORD,  VERSION_1+CHANGE_PASSWORD}, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> chagePasswordUser(
			HttpServletRequest request,
			@PathVariable("username") String username,
			@PathVariable("password") String password)
	{

		log.info("[updateUser][" + UPDATE + "]");

		log.debug("[parmam][userName:" + username + "] [password:" + password + "]");			
		
		
		ResponseEntity<?> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		
		//Verifico la negociación de contenidos
//		ResponseEntity<?> negotiationResponseEntity=Util.negotiationContent(request);
//		if (negotiationResponseEntity!=null){
//			return negotiationResponseEntity;
//		}
		
		if (!Util.validValue(username) ) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to change password. [userName:"+username+"] Not Found in BBDD"));
		}
		
		if (!Util.validValue(password) ) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to change password. [password:"+password+"] Not Valid"));
		}
		
		//Verificamos que no exista el usuario
		User existe = userService.findByName(username);
		if (existe==null) {
			return responseEntity=ExceptionUtil.checkException(new BadRequestException("Error trying to change password. [Username:"+username+"]  NOT Found in BBDD."));
		}	
		
		existe.setPassword(passwordEncoder.encode(password));
		//invocamos al service
		userService.updatePassword(existe);
		
		return listUser(request);
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

	
	private  ResponseEntity<Object> guardarResult( List<User> listado,  
			HttpServletRequest request)  {		
		
		Result<User> obj = new Result<User>();
		
		Map<String, String> pageMetadataCalculation = Util.pageMetadataCalculation(request, listado.size(), 1,env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

		// Pagina actual
		((Result<User>) obj).setSelf(pageMetadataCalculation.get(Constants.SELF));
		// Pagina inicial
		((Result<User>) obj).setFirst(pageMetadataCalculation.get(Constants.FIRST));
		// Ultima página
		((Result<User>) obj).setLast(pageMetadataCalculation.get(Constants.LAST));
		// Pagina siguiente
		((Result<User>) obj).setNext(pageMetadataCalculation.get(Constants.NEXT));
		// Pagina anterior
		((Result<User>) obj).setPrev(pageMetadataCalculation.get(Constants.PREV));
		// MD5
		((Result<User>) obj).setContentMD5(Util.generateHash(Util.toString(listado)));

		((Result<User>) obj).setPage(1);
		((Result<User>) obj).setPageSize(1);
		((Result<User>) obj).setPageRecords(listado.size());
		((Result<User>) obj).setRecords(listado);
		((Result<User>) obj).setTotalRecords(listado.size());

		((Result<?>) obj).setStatus(200);
		
		HttpHeaders headers = Util.extractHeaders((Result<?>) obj);

		return new ResponseEntity<Object>(obj, headers, HttpStatus.OK);
	} 

	@Override
	public String getKey()
	{	
		return "ciudad";
	}

	@Override
	//Aquí lo dejamos con nulo, para que en los test no devuelva un 406
	public String getListURI()
	{
		return null;
	}

}
