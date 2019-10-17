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

package org.ciudadesabiertas.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.ciudadesabiertas.model.OauthClientDetails;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class OauthClientDetailsDao {
	
	private static final Logger log = LoggerFactory.getLogger(OauthClientDetailsDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	public void add(OauthClientDetails detail) 
	{
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.save(detail);
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	public void update(OauthClientDetails detail) 
	{
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			session.update(detail);
			tx.commit();			
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally
		{
			session.close();
		}

	}
	
	
	
	public void deleteAll() {
		try
		{
			String sql = "delete from oauth_client_details";
			//SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(sql);
			sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
			//q.executeUpdate();
		
		}
		catch (Exception e)
		{
			log.error("Error deleting previous client details");
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OauthClientDetails> list() {
		
		
		List<OauthClientDetails> result = new ArrayList<OauthClientDetails>();

		try
		{
		
		Query query = sessionFactory.getCurrentSession().createQuery("FROM OauthClientDetails");
		
		result = query.list();
		}
		catch (Exception e)
		{
			log.error("Error reading OauthClientDetails from BBDD",e);
		}
		
		return result;
		

		

	}
	
	
}