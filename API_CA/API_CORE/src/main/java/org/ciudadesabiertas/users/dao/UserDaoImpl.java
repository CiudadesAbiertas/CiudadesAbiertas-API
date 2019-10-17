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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesabiertas.users.model.User;
import org.ciudadesabiertas.users.model.UserRole;
import org.ciudadesabiertas.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
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
public class UserDaoImpl implements UserDao
{
	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User findByUserName(String username)
	{

		List<User> users = new ArrayList<User>();

		username = username.trim();
		try
		{
			String hql = "from User where username=:name";
			Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("name", username);
			users = q.list();
		} catch (Exception e)
		{
			log.error("Error finding user " + username, e);
		}
		if (users.size() > 0)
		{
			return users.get(0);
		} else
		{
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> findByAppname(String appname)
	{

		List<User> users = new ArrayList<User>();
		String[] apps = null;

		if (appname.indexOf(",") > 0)
		{
			apps = appname.split(",");

			for (int i = 0; i < apps.length; i++)
			{
				apps[i] = apps[i].trim();
			}

		}

		try
		{

			String hql = "from User where appname= :name";
			Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("name", appname);

			if (appname.equals("%"))
			{
				hql = "from User u where u.appname IS NOT NULL and u.appname <> ''";
				q = sessionFactory.getCurrentSession().createQuery(hql);
			}

			if ((apps != null) && (apps.length > 0))
			{
				hql = "from User where appname in (:apps)";
				q = sessionFactory.getCurrentSession().createQuery(hql).setParameterList("apps", apps);
			}

			users = q.list();

		} catch (Exception e)
		{
			log.error("Error finding appname " + appname, e);
		}

		return users;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> findByEmail(String email)
	{

		List<User> users = new ArrayList<User>();

		try
		{
			String hql = "from User where email= :email";
			Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("email", email);
			users = q.list();
		} catch (Exception e)
		{
			log.error("Error finding user by email " + email, e);
		}

		return users;

	}

	@SuppressWarnings("unchecked")
	public List<User> findAll()
	{

		List<User> roles = new ArrayList<User>();

		try
		{
			roles = sessionFactory.getCurrentSession().createQuery("from User order by username").list();

		} catch (Exception e)
		{
			log.error("Error on findAll method ", e);
		}

		return roles;

	}

	@Override
	public void add(User user, Set<UserRole> userRoles, List<String> urlGroups)
	{

		Session session = null;
		Transaction tx = null;

		int numOfRows = countUserRole_with_JPACriteria();
		for (UserRole tmp : userRoles)
		{
			tmp.setUserRoleId(++numOfRows);
		}

		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(user);
			for (UserRole tmp : userRoles)
			{
				session.save(tmp);
			}

			tx.commit();
		} catch (Exception e)
		{
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}

	@Deprecated
	public int countUserRole_with_APICriteria()
	{

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class).setProjection(Projections.max("id"));
		Integer idCount = (Integer) criteria.uniqueResult();
		if (idCount == null)
		{
			idCount = new Integer(0);
		}
		return idCount.intValue();

	}
	
	public int countUserRole_with_JPACriteria()
	{

		CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery( Long.class );
		Root<UserRole> root = criteria.from( UserRole.class );
		criteria.multiselect(builder.max(root.get("id")));
		
		Long idCount = sessionFactory.getCurrentSession().createQuery(criteria).getResultList().get(0);
		
		int result=0;

		if (idCount != null)
		{
			result = idCount.intValue();
		}

		return result;

	}
	
	

	@Override
	public void remove(User user, List<UserRole> userRoles)
	{

		Session session = null;
		Transaction tx = null;

		// session = sessionFactory.getCurrentSession();
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			for (UserRole userRole : userRoles)
				session.delete(userRole);

			session.delete(user);

			tx.commit();
		} catch (Exception e)
		{
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}

	@Override
	public void updatePassword(User user)
	{

		User bdUser = findByUserName(user.getUsername());
		bdUser.setPassword(user.getPassword());

		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.update(bdUser);

	}

	public void update(User user)
	{

		User bdUser = findByUserName(user.getUsername());

		bdUser.setEnabled(user.isEnabled());

		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.update(bdUser);

	}
	
	public void save(User user)
	{

		user.setEnabled(true);

		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.save(user);

	}

	@Override
	public void update(User user, Set<UserRole> userRoles, List<String> urlGroups, List<User> sons)
	{

		Session session = null;
		Transaction tx = null;

		ArrayList<String> previousRoles = new ArrayList<String>();

		for (UserRole newRole : userRoles)
		{
			for (UserRole oldRole : user.getUserRole())
			{
				if (newRole.getRole().equals(oldRole.getRole()))
				{
					if (!previousRoles.contains(newRole.getRole()))
					{
						previousRoles.add(newRole.getRole());
					}
				}
			}
		}

		int numOfRows = countUserRole_with_JPACriteria();
		
		for (UserRole tmp : userRoles)
		{
			if ((!tmp.getRole().equals(Constants.ROLE_USER)) && (!previousRoles.contains(tmp.getRole())))
			{
				tmp.setUserRoleId(++numOfRows);
			}
		}

		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			for (UserRole tmp : user.getUserRole())
			{
				if ((!tmp.getRole().equals(Constants.ROLE_USER)) && (!previousRoles.contains(tmp.getRole())))
				{
					session.delete(tmp);
				}
			}

			for (UserRole tmp : userRoles)
			{
				if ((!tmp.getRole().equals(Constants.ROLE_USER)) && (!previousRoles.contains(tmp.getRole())))
					session.save(tmp);
			}

			user.setUserRole(userRoles);

			session.update(user);

			tx.commit();
		} catch (Exception e)
		{
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> getAllAppnames()
	{
		List<String> appnames = new ArrayList<String>();

		String hql = "select distinct u.appname from User u Where u.appname IS NOT NULL";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		appnames = q.list();

		return appnames;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> listUsersFromApps(List<String> applications)
	{

		List<User> users = new ArrayList<User>();
		String hql = "from User where appname in :apps";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setParameterList("apps", applications);
		users = q.list();
		return users;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap<String, Long> getAPPCountUsers(ArrayList<String> apps)
	{

		HashMap<String, Long> appUser = new HashMap<String, Long>();

		if (apps.size() == 0)
			return appUser;

		String query = "select appname, count(*) from User where appname in :apps group by appname";

		Query q = sessionFactory.getCurrentSession().createQuery(query);
		q.setParameterList("apps", apps);
		List<Object[]> data = q.list();
		for (Object[] row : data)
		{
			appUser.put((String) row[0], (Long) row[1]);
		}

		return appUser;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> listUsersFromRole(List<String> roles)
	{

		List<User> users = new ArrayList<User>();
		String hql = "from User where username in ( select distinct(user.username) from UserRole where role in (:roles))";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setParameterList("roles", roles);
		users = q.list();
		return users;

	}
	
	public void remove(User user)
	{

		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.delete(user);

	}

}