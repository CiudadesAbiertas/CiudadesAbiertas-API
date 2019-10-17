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

package org.ciudadesabiertas.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ciudadesabiertas.users.dao.UserDao;
import org.ciudadesabiertas.users.model.User;
import org.ciudadesabiertas.users.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

 
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Service("userService")
@Transactional
public class UserService {
	
	//private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;    
  
    
	@Transactional(readOnly = true)
	public List<String> getAllAppnames() {

		
		List<String> list = userDao.getAllAppnames();
		List<String> finalList = new ArrayList<String>();
		
		for (String temp:list)
		{			
			if (temp.contains(","))
			{
				String[] splittedTemp=temp.split(",");				
				for (String temp2:splittedTemp)
				{
					temp2=temp2.trim();
					if (!finalList.contains(temp2))
					{
						finalList.add(temp2);
					}
				}
			}
			else{
				temp=temp.trim();
				if (!finalList.contains(temp))
				{
					finalList.add(temp);
				}
			}
		}

		return finalList;
	}

	

	@Transactional(readOnly = true)
	public User findByName(String userName) {

		User user = userDao.findByUserName(userName);

		return user;
	}

	

	@Transactional(readOnly = true)
	public List<User> list() {

		List<User> list = userDao.findAll();
		
//		for (User obj: list) {
//			obj.getUserRole().size();
//		}

		return list;
	}
	
	

	
	@Transactional(readOnly = true)
	public List<User> findByEmail(String email) {

		List<User> users = userDao.findByEmail(email);

		return users;
	}

	@Transactional
	public void add(User user, Set<UserRole> userRole, List<String> urlGroups) {

		userDao.add(user, userRole, urlGroups);

	}
	
	@Transactional
	public void save(User user) {

		userDao.save(user);

	}

	@Transactional
	public void remove(User userName, List<UserRole> userRoles) {

		userDao.remove(userName, userRoles);

	}

	@Transactional
	public void updatePassword(User user) {

		userDao.updatePassword(user);

	}

	@Transactional
	public void updateUser(User user) {

		userDao.update(user);

	}
	
	@Transactional
	public void updateUser(User user, Set<UserRole> userRoles, List<String> urlGroups,List<User> sons) {

		userDao.update(user,userRoles, urlGroups,sons);

	}
	
	@Transactional(readOnly = true)
	public List<User> findFromRole(List<String> roles) {
			
		List<User> users = new ArrayList<User>();
		
		if (roles.size()==0)
			return users;
				
		
		users = userDao.listUsersFromRole(roles);

		return users;
		
	}
     

	@Transactional
	public void remove(User user) {

		userDao.remove(user);

	}
 
}