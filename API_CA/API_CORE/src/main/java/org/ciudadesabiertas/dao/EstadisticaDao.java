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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.ciudadesabiertas.model.Estadistica;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
public class EstadisticaDao
{

	private static final Logger log = LoggerFactory.getLogger(EstadisticaDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	

	@Deprecated
	public long count_with_APICriteria()
	{

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Estadistica.class).setProjection(Projections.count("id"));
		Long idCount = (Long) criteria.uniqueResult();
		if (idCount == null)
		{
			idCount = new Long(0);
		}

		return idCount;

	}
	
	public long count_with_JPA()
	{

		CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery( Long.class );
		Root<Estadistica> root = criteria.from( Estadistica.class );
		criteria.multiselect(builder.count(root.get("id")));
		
		Long idCount = sessionFactory.getCurrentSession().createQuery(criteria).getResultList().get(0);
		
		if (idCount == null)
		{
			idCount = Long.valueOf(0);
		}

		return idCount;

	}

	public void add(Estadistica estadistica)
	{

		Session session = null;		
		
//		long count_with_APICriteria = count_with_APICriteria();
//		log.debug("count_with_APICriteria:"+count_with_APICriteria);
		
		long count_with_JPA = count_with_JPA();
		log.debug("count_with_JPA:"+count_with_JPA);
		long numOfRows = count_with_JPA+1;		
		int randomNum=Util.genRandomNum(1000, 9999);		
		String id=numOfRows+""+ randomNum;			
		boolean withDate = true;				
		estadistica.setId(Util.generateID(id, StartVariables.NODO_PATTERN, withDate));		
		session = sessionFactory.getCurrentSession();
		session.save(estadistica);			
		
	}
	
	@SuppressWarnings("rawtypes")
	@Deprecated
	public int maxRequestPerSecond_with_APICriteria()
	{
		
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -1);
        Date oneSecondBack = cal.getTime();
        
        //Control mediante Criterios
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Estadistica.class);

		criteria.add(Restrictions.gt("fechaPeticion", oneSecondBack));		
		
		criteria.setProjection(Projections.rowCount());
		List listado = criteria.list();
		
	
//		  //Control mediante query directa
//        Query query = sessionFactory.getCurrentSession().createQuery("FROM Estadistica where fechaPeticion > :old");
//        query.setParameter("old", oneSecondBack);
//        
//
//        result = query.list();        
		
				
		
		int idCount = 0;
		try {
			if (listado!=null) {
			  idCount = Integer.valueOf(""+ (long) listado.get(0));
				//idCount = listado.size();
			}
		}catch (Exception e) {
			
			String msgError = "Error access BBDD maxRequestPerSecond: " + e.getMessage();
			log.error(msgError);
		}
		

		return idCount;

	}
	
	@SuppressWarnings("rawtypes")
	@Deprecated
	public int maxRequestPerSecondUser_with_APICriteria(String user)
	{
		
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -1);
        Date oneSecondBack = cal.getTime();
        
        //Control mediante Criterios
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Estadistica.class);

		criteria.add(Restrictions.gt("fechaPeticion", oneSecondBack));	
		criteria.add(Restrictions.eq("usuario", user));	
		
		criteria.setProjection(Projections.rowCount());
		List listado = criteria.list();
     
				
		int idCount = 0;
		try {
			if (listado!=null) {
			  idCount = Integer.valueOf(""+ (long) listado.get(0));
				//idCount = listado.size();
			}
		}catch (Exception e) {
			
			String msgError = "Error access BBDD maxRequestPerMinute: " + e.getMessage();
			log.error(msgError);
		}

		return idCount;

	}
	
	
	public int maxRequestPerSecondUser_with_JPA_Criteria(String user)
	{
		
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -1);
        Date oneSecondBack = cal.getTime();
        
        //Control mediante Criterios
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery( Long.class );
		Root<Estadistica> root = criteria.from( Estadistica.class );
		criteria.multiselect(builder.count(root.get("id")));
		Predicate condiciones = builder.and(
				builder.equal( root.get( "usuario" ), user ),
				builder.greaterThan( root.<Date>get("fechaPeticion"), oneSecondBack )
			);
				
		criteria.where( condiciones );
		
		Long rowcount = sessionFactory.getCurrentSession().createQuery(criteria).getResultList().get(0);
		
     
				
		int idCount = 0;
		try {
			if (rowcount!=null) {
			  idCount = Integer.valueOf(""+ (long) rowcount);
				//idCount = listado.size();
			}
		}catch (Exception e) {
			
			String msgError = "Error access BBDD maxRequestPerMinute: " + e.getMessage();
			log.error(msgError);
		}

		return idCount;

	}

}