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

package org.ciudadesabiertas.users.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


import org.ciudadesabiertas.users.model.User;
import org.ciudadesabiertas.users.model.UserRole;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public interface UserDao {

	User findByUserName(String username);
	
	List<User> findByEmail(String email);
		
	List<User> findAll();

	void add(User user, Set<UserRole> userRol, List<String> urlGroups);
	
	void remove(User user, List<UserRole> userRoles);
	
	void updatePassword(User user);
	
	void update(User user);
	
	void save(User user);
	
	void remove(User user);

	void update(User user, Set<UserRole> userRoles,List<String> urlGroups,List<User> sons);

	List<String> getAllAppnames();

	List<User> listUsersFromApps(List<String> applications);

	HashMap<String, Long> getAPPCountUsers(ArrayList<String> apps);
	
	List<User> listUsersFromRole(List<String> roles);

}