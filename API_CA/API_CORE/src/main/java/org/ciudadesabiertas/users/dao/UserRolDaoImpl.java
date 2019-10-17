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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesabiertas.users.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class UserRolDaoImpl implements UserRolDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserRole> findAll() {

		List<UserRole> roles = new ArrayList<UserRole>();
		roles = sessionFactory.getCurrentSession().createQuery("from UserRole order by username").list();
		return roles;

	}
	
	@Deprecated
	public int count_with_APICriteria () { 
		
		Criteria criteria = sessionFactory.getCurrentSession()
			    .createCriteria(UserRole.class)
			    .setProjection(Projections.max("id"));
		Integer idCount = (Integer)criteria.uniqueResult();
		if(idCount==null){
			idCount= new Integer(0);
		}
		return idCount.intValue();		

	}
	
	public int count() {
		return count_with_JPACriteria();
	}

	public int count_with_JPACriteria () { 
		
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

	@SuppressWarnings("rawtypes")
	@Override
	public void remove(int id) {

		String hql = "delete UserRole where userRoleId = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
		q.executeUpdate();

	}

	@Override
	public void add(UserRole userRole) {

		Session session = null;
		Transaction tx = null;
		
		int numOfRows=count();
		
		userRole.setUserRoleId(numOfRows+1);
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(userRole);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<UserRole> findByUserName(String username) {
		

		List<UserRole> userRoles = new ArrayList<UserRole>();

		String hql = "from UserRole where username= :name";
		Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("name", username);
		userRoles=q.list();

		return userRoles;	
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserRole findById(int id) {
		

		List<UserRole> userRoles = new ArrayList<UserRole>();

		String hql = "from UserRole where userRoleId= :id";
		Query q = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
		userRoles=q.list();

		if (userRoles.size()>0)		
			return userRoles.get(0);
					
		return new UserRole();
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<UserRole> find(UserRole userRole) {

		List<UserRole> userRoles = new ArrayList<UserRole>();
		
		String hql = "from UserRole where ";
		String conditions =" ";

		if (userRole.getRole()!=null)
		{
			conditions += " role= :role";
		}
		
		if (userRole.getUser().getUsername()!=null)
		{
			if (conditions.equals(""))
				conditions += " username= :username";
			else
				conditions += " and username= :username";
		}
		
		
		Query q = sessionFactory.getCurrentSession().createQuery(hql+conditions);
		q.setParameter("role", userRole.getRole());
		q.setParameter("username", userRole.getUser().getUsername());
		
		userRoles=q.list();
		
		return userRoles;
	
	}

	

	

}