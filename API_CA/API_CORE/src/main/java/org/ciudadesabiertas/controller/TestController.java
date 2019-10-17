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
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.SecurityURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RestController
public class TestController implements CiudadesAbiertasController
{
	
	private static final String TEST4 = "/test4";
	private static final String TEST3 = "/test3";
	private static final String TEST2 = "/test2";
	private static final String TEST1 = "/test1";
	
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	
	@RequestMapping(TEST1)
	public @ResponseBody  String test1() {		
		log.info(TEST1);		
		return "This is for all the people";		
	}
	
	@RequestMapping(TEST2)
	public @ResponseBody  String test2() {		
		log.info(TEST2);		
		return "This is for logged people";		
	}
	
	@RequestMapping(TEST3)
	public @ResponseBody  String test3() {		
		log.info(TEST3);		
		return "This is for admin";		
	}
	
	@RequestMapping(TEST4)
	public @ResponseBody  String test4() {		
		log.info(TEST4);		
		return "This is for group Ciudades Abiertas users";		
	}

	

	@Override
	public ArrayList<SecurityURL> getURLsWithRoles()
	{	
		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();
		
		urlRoles.add(new SecurityURL(TEST1,HttpMethod.GET,Constants.NO_AUTH));
		urlRoles.add(new SecurityURL(TEST2,HttpMethod.GET,Constants.BASIC_AUTH));
		urlRoles.add(new SecurityURL(TEST3,HttpMethod.GET,Constants.ADMIN_AUTH));
		return urlRoles;
	}

	@Override
	public String getKey()
	{
		return "tests";
	}

	@Override
	public String getListURI()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
