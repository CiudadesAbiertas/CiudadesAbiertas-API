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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ciudadesabiertas.users.dao.GroupAuthorityDao;
import org.ciudadesabiertas.users.dao.GroupDao;
import org.ciudadesabiertas.users.dao.GroupMemberDao;
import org.ciudadesabiertas.users.dao.UserDao;
import org.ciudadesabiertas.users.model.Group;
import org.ciudadesabiertas.users.model.GroupAuthority;
import org.ciudadesabiertas.users.model.GroupMember;
import org.ciudadesabiertas.users.model.UserRole;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Service("userDetailsService")
public class CiudadesAbiertasUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupAuthorityDao groupAuthorityDao;
	
	@Autowired
	private GroupMemberDao groupMemberDao;
	
	@Autowired
	private GroupDao groupDao;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	
		org.ciudadesabiertas.users.model.User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		List<GrantedAuthority> autoritiesGroup = buildGroupAuthority(user.getUsername());
		
		authorities.addAll(autoritiesGroup);		

		return buildUserForAuthentication(user, authorities);
		
	}

	

	// Converts com.localidata.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(org.ciudadesabiertas.users.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	

	public List<Group> getGroups()
	{
		return groupDao.list();
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
	
	
	private List<GrantedAuthority> buildGroupAuthority(String username)
	{		
		
		List<GroupMember> groupMember=groupMemberDao.listFromUserName(username);
		
		List<GroupAuthority> authoritiesFromUsername =new ArrayList<GroupAuthority>();
		
		for (GroupMember gm:groupMember)
		{
			List<GroupAuthority> authorities = groupAuthorityDao.getAuthoritiesFromGroupId(gm.getGroupId());
			authoritiesFromUsername.addAll(authorities);
		}
				
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (GroupAuthority a : authoritiesFromUsername) {
			setAuths.add(new SimpleGrantedAuthority(a.getAuthority()));
		}		
		
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		
		return Result;
	}

}