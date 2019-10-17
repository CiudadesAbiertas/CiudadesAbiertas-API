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
import java.util.List;

import org.ciudadesabiertas.users.model.GroupAuthority;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class GroupAuthorityImpl implements GroupAuthorityDao{

	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<GroupAuthority> list() {
		
		List<GroupAuthority> roles = new ArrayList<GroupAuthority>();
		try
		{
			roles = sessionFactory.getCurrentSession().createQuery("from GroupAuthority order by groupId").list();
		}
		catch (Exception e)
		{
			log.error("Error listing GroupAuthority",e);
		}

		return roles;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<GroupAuthority> getAuthoritiesFromGroupId(long id)
	{
		List<GroupAuthority> roles = new ArrayList<GroupAuthority>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from GroupAuthority where groupId=:id");
			query.setParameter("id", id);	
			roles = query.list();		
		}
		catch (Exception e)
		{
			log.error("Error listing getAuthoritiesFromUsername",e);
		}

		return roles;
	}
	
}